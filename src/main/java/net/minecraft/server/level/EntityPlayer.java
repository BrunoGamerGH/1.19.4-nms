package net.minecraft.server.level;

import com.google.common.collect.Lists;
import com.google.common.net.InetAddresses;
import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Either;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.BlockUtil;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportSystemDetails;
import net.minecraft.EnumChatFormat;
import net.minecraft.ReportedException;
import net.minecraft.SystemUtils;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.commands.arguments.ArgumentAnchor;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Position;
import net.minecraft.core.SectionPosition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.chat.ChatHoverable;
import net.minecraft.network.chat.ChatMessageType;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatMutableComponent;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.RemoteChatSession;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundHurtAnimationPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatEndPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatEnterPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatKillPacket;
import net.minecraft.network.protocol.game.ClientboundServerDataPacket;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import net.minecraft.network.protocol.game.PacketListenerPlayOut;
import net.minecraft.network.protocol.game.PacketPlayInSettings;
import net.minecraft.network.protocol.game.PacketPlayOutAbilities;
import net.minecraft.network.protocol.game.PacketPlayOutAnimation;
import net.minecraft.network.protocol.game.PacketPlayOutBlockChange;
import net.minecraft.network.protocol.game.PacketPlayOutCamera;
import net.minecraft.network.protocol.game.PacketPlayOutCloseWindow;
import net.minecraft.network.protocol.game.PacketPlayOutEntityEffect;
import net.minecraft.network.protocol.game.PacketPlayOutEntityStatus;
import net.minecraft.network.protocol.game.PacketPlayOutExperience;
import net.minecraft.network.protocol.game.PacketPlayOutGameStateChange;
import net.minecraft.network.protocol.game.PacketPlayOutLookAt;
import net.minecraft.network.protocol.game.PacketPlayOutNamedEntitySpawn;
import net.minecraft.network.protocol.game.PacketPlayOutNamedSoundEffect;
import net.minecraft.network.protocol.game.PacketPlayOutOpenBook;
import net.minecraft.network.protocol.game.PacketPlayOutOpenSignEditor;
import net.minecraft.network.protocol.game.PacketPlayOutOpenWindow;
import net.minecraft.network.protocol.game.PacketPlayOutOpenWindowHorse;
import net.minecraft.network.protocol.game.PacketPlayOutOpenWindowMerchant;
import net.minecraft.network.protocol.game.PacketPlayOutRemoveEntityEffect;
import net.minecraft.network.protocol.game.PacketPlayOutResourcePackSend;
import net.minecraft.network.protocol.game.PacketPlayOutRespawn;
import net.minecraft.network.protocol.game.PacketPlayOutServerDifficulty;
import net.minecraft.network.protocol.game.PacketPlayOutSetSlot;
import net.minecraft.network.protocol.game.PacketPlayOutTileEntityData;
import net.minecraft.network.protocol.game.PacketPlayOutUnloadChunk;
import net.minecraft.network.protocol.game.PacketPlayOutUpdateHealth;
import net.minecraft.network.protocol.game.PacketPlayOutWindowData;
import net.minecraft.network.protocol.game.PacketPlayOutWindowItems;
import net.minecraft.network.protocol.game.PacketPlayOutWorldEvent;
import net.minecraft.network.protocol.status.ServerPing;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.AdvancementDataPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ITextFilter;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.server.players.PlayerList;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.stats.RecipeBookServer;
import net.minecraft.stats.ServerStatisticManager;
import net.minecraft.stats.Statistic;
import net.minecraft.stats.StatisticList;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Unit;
import net.minecraft.world.EnumHand;
import net.minecraft.world.IInventory;
import net.minecraft.world.ITileInventory;
import net.minecraft.world.damagesource.CombatTracker;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EnumMainHand;
import net.minecraft.world.entity.IEntityAngerable;
import net.minecraft.world.entity.IEntitySelector;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.entity.animal.horse.EntityHorseAbstract;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.monster.EntityMonster;
import net.minecraft.world.entity.monster.warden.WardenSpawnTracker;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.EnumChatVisibility;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.entity.projectile.EntityArrow;
import net.minecraft.world.food.FoodMetaData;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.inventory.ContainerHorse;
import net.minecraft.world.inventory.ContainerSynchronizer;
import net.minecraft.world.inventory.ICrafting;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.SlotResult;
import net.minecraft.world.item.ItemCooldown;
import net.minecraft.world.item.ItemCooldownPlayer;
import net.minecraft.world.item.ItemWorldMapBase;
import net.minecraft.world.item.ItemWrittenBook;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.IRecipe;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.item.trading.MerchantRecipeList;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.EnumGamemode;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.World;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.BlockChest;
import net.minecraft.world.level.block.BlockFacingHorizontal;
import net.minecraft.world.level.block.BlockPortal;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityCommand;
import net.minecraft.world.level.block.entity.TileEntitySign;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.dimension.WorldDimension;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.portal.ShapeDetectorShape;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.ScoreboardScore;
import net.minecraft.world.scores.ScoreboardTeam;
import net.minecraft.world.scores.ScoreboardTeamBase;
import net.minecraft.world.scores.criteria.IScoreboardCriteria;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WeatherType;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorldBorder;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftPortalEvent;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_19_R3.util.BlockStateListPopulator;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftChatMessage;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftDimensionUtil;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerChangedMainHandEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerLocaleChangeEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.PortalCreateEvent.CreateReason;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MainHand;
import org.slf4j.Logger;

public class EntityPlayer extends EntityHuman {
   private static final Logger cj = LogUtils.getLogger();
   private static final int ck = 32;
   private static final int cl = 10;
   public PlayerConnection b;
   public final MinecraftServer c;
   public final PlayerInteractManager d;
   private final AdvancementDataPlayer cm;
   private final ServerStatisticManager cn;
   private float co = Float.MIN_VALUE;
   private int cp = Integer.MIN_VALUE;
   private int cq = Integer.MIN_VALUE;
   private int cr = Integer.MIN_VALUE;
   private int cs = Integer.MIN_VALUE;
   private int ct = Integer.MIN_VALUE;
   private float cu = -1.0E8F;
   private int cv = -99999999;
   private boolean cw = true;
   public int cx = -99999999;
   public int cy = 60;
   private EnumChatVisibility cz;
   private boolean cA;
   private long cB;
   @Nullable
   private Entity cC;
   public boolean cD;
   private boolean cE;
   private final RecipeBookServer cF;
   @Nullable
   private Vec3D cG;
   private int cH;
   private boolean cI;
   @Nullable
   private Vec3D cJ;
   @Nullable
   private Vec3D cK;
   @Nullable
   private Vec3D cL;
   private SectionPosition cM;
   private ResourceKey<World> cN;
   @Nullable
   private BlockPosition cO;
   private boolean cP;
   private float cQ;
   private final ITextFilter cR;
   private boolean cS;
   private boolean cT;
   private WardenSpawnTracker cU;
   private final ContainerSynchronizer cV;
   private final ICrafting cW;
   @Nullable
   private RemoteChatSession cX;
   private int cY;
   public int e;
   public boolean f;
   public String displayName;
   public IChatBaseComponent listName;
   public Location compassTarget;
   public int newExp = 0;
   public int newLevel = 0;
   public int newTotalExp = 0;
   public boolean keepLevel = false;
   public double maxHealthCache;
   public boolean joining = true;
   public boolean sentListPacket = false;
   public Integer clientViewDistance;
   public String kickLeaveMessage = null;
   public String locale = "en_us";
   public long timeOffset = 0L;
   public boolean relativeTime = true;
   public WeatherType weather = null;
   private float pluginRainPosition;
   private float pluginRainPositionPrevious;

   public EntityPlayer(MinecraftServer minecraftserver, WorldServer worldserver, GameProfile gameprofile) {
      super(worldserver, worldserver.Q(), worldserver.R(), gameprofile);
      this.cz = EnumChatVisibility.a;
      this.cA = true;
      this.cB = SystemUtils.b();
      this.cF = new RecipeBookServer();
      this.cM = SectionPosition.a(0, 0, 0);
      this.cN = World.h;
      this.cT = true;
      this.cU = new WardenSpawnTracker(0, 0, 0);
      this.cV = new ContainerSynchronizer() {
         @Override
         public void a(
            Container container, NonNullList<net.minecraft.world.item.ItemStack> nonnulllist, net.minecraft.world.item.ItemStack itemstack, int[] aint
         ) {
            EntityPlayer.this.b.a(new PacketPlayOutWindowItems(container.j, container.k(), nonnulllist, itemstack));

            for(int i = 0; i < aint.length; ++i) {
               this.b(container, i, aint[i]);
            }
         }

         @Override
         public void a(Container container, int i, net.minecraft.world.item.ItemStack itemstack) {
            EntityPlayer.this.b.a(new PacketPlayOutSetSlot(container.j, container.k(), i, itemstack));
         }

         @Override
         public void a(Container container, net.minecraft.world.item.ItemStack itemstack) {
            EntityPlayer.this.b.a(new PacketPlayOutSetSlot(-1, container.k(), -1, itemstack));
         }

         @Override
         public void a(Container container, int i, int j) {
            this.b(container, i, j);
         }

         private void b(Container container, int i, int j) {
            EntityPlayer.this.b.a(new PacketPlayOutWindowData(container.j, i, j));
         }
      };
      this.cW = new ICrafting() {
         @Override
         public void a(Container container, int i, net.minecraft.world.item.ItemStack itemstack) {
            Slot slot = container.b(i);
            if (!(slot instanceof SlotResult) && slot.d == EntityPlayer.this.fJ()) {
               CriterionTriggers.e.a(EntityPlayer.this, EntityPlayer.this.fJ(), itemstack);
            }
         }

         @Override
         public void a(Container container, int i, int j) {
         }
      };
      this.cR = minecraftserver.a(this);
      this.d = minecraftserver.b(this);
      this.c = minecraftserver;
      this.cn = minecraftserver.ac().getPlayerStats(this);
      this.cm = minecraftserver.ac().f(this);
      this.v(1.0F);
      this.d(worldserver);
      this.displayName = this.cu();
      this.bukkitPickUpLoot = true;
      this.maxHealthCache = (double)this.eE();
   }

   public final BlockPosition getSpawnPoint(WorldServer worldserver) {
      BlockPosition blockposition = worldserver.Q();
      if (worldserver.q_().g() && worldserver.J.m() != EnumGamemode.c) {
         int i = Math.max(0, this.c.a(worldserver));
         int j = MathHelper.a(worldserver.p_().b((double)blockposition.u(), (double)blockposition.w()));
         if (j < i) {
            i = j;
         }

         if (j <= 1) {
            i = 1;
         }

         long k = (long)(i * 2 + 1);
         long l = k * k;
         int i1 = l > 2147483647L ? Integer.MAX_VALUE : (int)l;
         int j1 = this.t(i1);
         int k1 = RandomSource.a().a(i1);

         for(int l1 = 0; l1 < i1; ++l1) {
            int i2 = (k1 + j1 * l1) % i1;
            int j2 = i2 % (i * 2 + 1);
            int k2 = i2 / (i * 2 + 1);
            BlockPosition blockposition1 = WorldProviderNormal.a(worldserver, blockposition.u() + j2 - i, blockposition.w() + k2 - i);
            if (blockposition1 != null) {
               return blockposition1;
            }
         }
      }

      return blockposition;
   }

   private void d(WorldServer worldserver) {
      BlockPosition blockposition = worldserver.Q();
      if (worldserver.q_().g() && worldserver.J.m() != EnumGamemode.c) {
         int i = Math.max(0, this.c.a(worldserver));
         int j = MathHelper.a(worldserver.p_().b((double)blockposition.u(), (double)blockposition.w()));
         if (j < i) {
            i = j;
         }

         if (j <= 1) {
            i = 1;
         }

         long k = (long)(i * 2 + 1);
         long l = k * k;
         int i1 = l > 2147483647L ? Integer.MAX_VALUE : (int)l;
         int j1 = this.t(i1);
         int k1 = RandomSource.a().a(i1);

         for(int l1 = 0; l1 < i1; ++l1) {
            int i2 = (k1 + j1 * l1) % i1;
            int j2 = i2 % (i * 2 + 1);
            int k2 = i2 / (i * 2 + 1);
            BlockPosition blockposition1 = WorldProviderNormal.a(worldserver, blockposition.u() + j2 - i, blockposition.w() + k2 - i);
            if (blockposition1 != null) {
               this.a(blockposition1, 0.0F, 0.0F);
               if (worldserver.g(this)) {
                  break;
               }
            }
         }
      } else {
         this.a(blockposition, 0.0F, 0.0F);

         while(!worldserver.g(this) && this.dn() < (double)(worldserver.ai() - 1)) {
            this.e(this.dl(), this.dn() + 1.0, this.dr());
         }
      }
   }

   private int t(int i) {
      return i <= 16 ? i - 1 : 17;
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      if (nbttagcompound.b("warden_spawn_tracker", 10)) {
         DataResult<WardenSpawnTracker> dataresult = WardenSpawnTracker.a.parse(new Dynamic(DynamicOpsNBT.a, nbttagcompound.c("warden_spawn_tracker")));
         Logger logger = cj;
         dataresult.resultOrPartial(logger::error).ifPresent(wardenspawntracker -> this.cU = wardenspawntracker);
      }

      if (nbttagcompound.b("enteredNetherPosition", 10)) {
         NBTTagCompound nbttagcompound1 = nbttagcompound.p("enteredNetherPosition");
         this.cK = new Vec3D(nbttagcompound1.k("x"), nbttagcompound1.k("y"), nbttagcompound1.k("z"));
      }

      this.cE = nbttagcompound.q("seenCredits");
      if (nbttagcompound.b("recipeBook", 10)) {
         this.cF.a(nbttagcompound.p("recipeBook"), this.c.aE());
      }

      this.getBukkitEntity().readExtraData(nbttagcompound);
      if (this.fu()) {
         this.fv();
      }

      String spawnWorld = nbttagcompound.l("SpawnWorld");
      CraftWorld oldWorld = (CraftWorld)Bukkit.getWorld(spawnWorld);
      if (oldWorld != null) {
         this.cN = oldWorld.getHandle().ab();
      }

      if (nbttagcompound.b("SpawnX", 99) && nbttagcompound.b("SpawnY", 99) && nbttagcompound.b("SpawnZ", 99)) {
         this.cO = new BlockPosition(nbttagcompound.h("SpawnX"), nbttagcompound.h("SpawnY"), nbttagcompound.h("SpawnZ"));
         this.cP = nbttagcompound.q("SpawnForced");
         this.cQ = nbttagcompound.j("SpawnAngle");
         if (nbttagcompound.e("SpawnDimension")) {
            DataResult<ResourceKey<World>> dataresult1 = World.g.parse(DynamicOpsNBT.a, nbttagcompound.c("SpawnDimension"));
            Logger logger1 = cj;
            this.cN = dataresult1.resultOrPartial(logger1::error).orElse(World.h);
         }
      }
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      DataResult<NBTBase> dataresult = WardenSpawnTracker.a.encodeStart(DynamicOpsNBT.a, this.cU);
      Logger logger = cj;
      dataresult.resultOrPartial(logger::error).ifPresent(nbtbase -> nbttagcompound.a("warden_spawn_tracker", nbtbase));
      this.k(nbttagcompound);
      nbttagcompound.a("seenCredits", this.cE);
      if (this.cK != null) {
         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
         nbttagcompound1.a("x", this.cK.c);
         nbttagcompound1.a("y", this.cK.d);
         nbttagcompound1.a("z", this.cK.e);
         nbttagcompound.a("enteredNetherPosition", nbttagcompound1);
      }

      Entity entity = this.cS();
      Entity entity1 = this.cV();
      boolean persistVehicle = true;
      if (entity1 != null) {
         for(Entity vehicle = entity1; vehicle != null; vehicle = vehicle.cV()) {
            if (!vehicle.persist) {
               persistVehicle = false;
               break;
            }
         }
      }

      if (persistVehicle && entity1 != null && entity != this && entity.cR()) {
         NBTTagCompound nbttagcompound2 = new NBTTagCompound();
         NBTTagCompound nbttagcompound3 = new NBTTagCompound();
         entity.e(nbttagcompound3);
         nbttagcompound2.a("Attach", entity1.cs());
         nbttagcompound2.a("Entity", nbttagcompound3);
         nbttagcompound.a("RootVehicle", nbttagcompound2);
      }

      nbttagcompound.a("recipeBook", this.cF.b());
      nbttagcompound.a("Dimension", this.H.ab().a().toString());
      if (this.cO != null) {
         nbttagcompound.a("SpawnX", this.cO.u());
         nbttagcompound.a("SpawnY", this.cO.v());
         nbttagcompound.a("SpawnZ", this.cO.w());
         nbttagcompound.a("SpawnForced", this.cP);
         nbttagcompound.a("SpawnAngle", this.cQ);
         dataresult = MinecraftKey.a.encodeStart(DynamicOpsNBT.a, this.cN.a());
         logger = cj;
         dataresult.resultOrPartial(logger::error).ifPresent(nbtbase -> nbttagcompound.a("SpawnDimension", nbtbase));
      }

      this.getBukkitEntity().setExtraData(nbttagcompound);
   }

   public void spawnIn(World world) {
      this.H = world;
      if (world == null) {
         this.dD();
         Vec3D position = null;
         if (this.cN != null) {
            world = this.x().getCraftServer().getHandle().b().a(this.cN);
            if (world != null && this.N() != null) {
               position = EntityHuman.a((WorldServer)world, this.N(), this.O(), false, false).orElse(null);
            }
         }

         if (world == null || position == null) {
            world = ((CraftWorld)Bukkit.getServer().getWorlds().get(0)).getHandle();
            position = Vec3D.b(((WorldServer)world).Q());
         }

         this.H = world;
         this.e(position.a(), position.b(), position.c());
      }

      this.d.a((WorldServer)world);
   }

   public void a(int i) {
      float f = (float)this.fS();
      float f1 = (f - 1.0F) / f;
      this.ce = MathHelper.a((float)i / f, 0.0F, f1);
      this.cx = -1;
   }

   public void b(int i) {
      this.cc = i;
      this.cx = -1;
   }

   @Override
   public void c(int i) {
      super.c(i);
      this.cx = -1;
   }

   @Override
   public void a(net.minecraft.world.item.ItemStack itemstack, int i) {
      super.a(itemstack, i);
      this.cx = -1;
   }

   public void a(Container container) {
      container.a(this.cW);
      container.a(this.cV);
   }

   public void h() {
      this.a(this.bO);
   }

   @Override
   public void E_() {
      super.E_();
      this.b.a(new ClientboundPlayerCombatEnterPacket());
   }

   @Override
   public void j() {
      super.j();
      this.b.a(new ClientboundPlayerCombatEndPacket(this.eC()));
   }

   @Override
   protected void a(IBlockData iblockdata) {
      CriterionTriggers.d.a(this, iblockdata);
   }

   @Override
   protected ItemCooldown k() {
      return new ItemCooldownPlayer(this);
   }

   @Override
   public void l() {
      if (this.joining) {
         this.joining = false;
      }

      this.d.a();
      this.cU.a();
      --this.cy;
      if (this.ak > 0) {
         --this.ak;
      }

      this.bP.d();
      if (!this.H.B && !this.bP.a(this)) {
         this.q();
         this.bP = this.bO;
      }

      Entity entity = this.G();
      if (entity != this) {
         if (entity.bq()) {
            this.a(entity.dl(), entity.dn(), entity.dr(), entity.dw(), entity.dy());
            this.x().k().a(this);
            if (this.fA()) {
               this.c(this);
            }
         } else {
            this.c(this);
         }
      }

      CriterionTriggers.w.a(this);
      if (this.cG != null) {
         CriterionTriggers.u.a(this, this.cG, this.ag - this.cH);
      }

      this.o();
      this.p();
      this.cm.b(this);
   }

   public void m() {
      try {
         if (!this.F_() || !this.da()) {
            super.l();
         }

         for(int i = 0; i < this.fJ().b(); ++i) {
            net.minecraft.world.item.ItemStack itemstack = this.fJ().a(i);
            if (itemstack.c().ac_()) {
               Packet<?> packet = ((ItemWorldMapBase)itemstack.c()).a(itemstack, this.H, this);
               if (packet != null) {
                  this.b.a(packet);
               }
            }
         }

         if (this.eo() != this.cu || this.cv != this.bQ.a() || this.bQ.e() == 0.0F != this.cw) {
            this.b.a(new PacketPlayOutUpdateHealth(this.getBukkitEntity().getScaledHealth(), this.bQ.a(), this.bQ.e()));
            this.cu = this.eo();
            this.cv = this.bQ.a();
            this.cw = this.bQ.e() == 0.0F;
         }

         if (this.eo() + this.fb() != this.co) {
            this.co = this.eo() + this.fb();
            this.a(IScoreboardCriteria.f, MathHelper.f(this.co));
         }

         if (this.bQ.a() != this.cp) {
            this.cp = this.bQ.a();
            this.a(IScoreboardCriteria.g, MathHelper.f((float)this.cp));
         }

         if (this.cd() != this.cq) {
            this.cq = this.cd();
            this.a(IScoreboardCriteria.h, MathHelper.f((float)this.cq));
         }

         if (this.eB() != this.cr) {
            this.cr = this.eB();
            this.a(IScoreboardCriteria.i, MathHelper.f((float)this.cr));
         }

         if (this.cd != this.ct) {
            this.ct = this.cd;
            this.a(IScoreboardCriteria.j, MathHelper.f((float)this.ct));
         }

         if (this.maxHealthCache != (double)this.eE()) {
            this.getBukkitEntity().updateScaledHealth();
         }

         if (this.cc != this.cs) {
            this.cs = this.cc;
            this.a(IScoreboardCriteria.k, MathHelper.f((float)this.cs));
         }

         if (this.cd != this.cx) {
            this.cx = this.cd;
            this.b.a(new PacketPlayOutExperience(this.ce, this.cd, this.cc));
         }

         if (this.ag % 20 == 0) {
            CriterionTriggers.p.a(this);
         }

         if (this.oldLevel == -1) {
            this.oldLevel = this.cc;
         }

         if (this.oldLevel != this.cc) {
            CraftEventFactory.callPlayerLevelChangeEvent(this.getBukkitEntity(), this.oldLevel, this.cc);
            this.oldLevel = this.cc;
         }

         if (this.getBukkitEntity().hasClientWorldBorder()) {
            ((CraftWorldBorder)this.getBukkitEntity().getWorldBorder()).getHandle().s();
         }
      } catch (Throwable var4) {
         CrashReport crashreport = CrashReport.a(var4, "Ticking player");
         CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Player being ticked");
         this.a(crashreportsystemdetails);
         throw new ReportedException(crashreport);
      }
   }

   @Override
   public void n() {
      if (this.eo() > 0.0F && this.cJ != null) {
         CriterionTriggers.U.a(this, this.cJ);
      }

      this.cJ = null;
      super.n();
   }

   @Override
   public void o() {
      if (this.aa > 0.0F && this.cJ == null) {
         this.cJ = this.de();
      }
   }

   public void p() {
      if (this.cV() != null && this.cV().bg()) {
         if (this.cL == null) {
            this.cL = this.de();
         } else {
            CriterionTriggers.V.a(this, this.cL);
         }
      }

      if (this.cL != null && (this.cV() == null || !this.cV().bg())) {
         this.cL = null;
      }
   }

   private void a(IScoreboardCriteria iscoreboardcriteria, int i) {
      this.H.getCraftServer().getScoreboardManager().getScoreboardScores(iscoreboardcriteria, this.cu(), scoreboardscore -> scoreboardscore.b(i));
   }

   @Override
   public void a(DamageSource damagesource) {
      this.a(GameEvent.q);
      boolean flag = this.H.W().b(GameRules.m);
      if (!this.dB()) {
         List<ItemStack> loot = new ArrayList(this.fJ().b());
         boolean keepInventory = this.H.W().b(GameRules.d) || this.F_();
         if (!keepInventory) {
            for(net.minecraft.world.item.ItemStack item : this.fJ().getContents()) {
               if (!item.b() && !EnchantmentManager.e(item)) {
                  loot.add(CraftItemStack.asCraftMirror(item));
               }
            }
         }

         this.a(damagesource, this.aY > 0);

         for(ItemStack item : this.drops) {
            loot.add(item);
         }

         this.drops.clear();
         IChatBaseComponent defaultMessage = this.eC().b();
         String deathmessage = defaultMessage.getString();
         this.keepLevel = keepInventory;
         PlayerDeathEvent event = CraftEventFactory.callPlayerDeathEvent(this, loot, deathmessage, keepInventory);
         if (this.bP != this.bO) {
            this.q();
         }

         String deathMessage = event.getDeathMessage();
         if (deathMessage != null && deathMessage.length() > 0 && flag) {
            IChatBaseComponent ichatbasecomponent;
            if (deathMessage.equals(deathmessage)) {
               ichatbasecomponent = this.eC().b();
            } else {
               ichatbasecomponent = CraftChatMessage.fromStringOrNull(deathMessage);
            }

            this.b
               .a(
                  new ClientboundPlayerCombatKillPacket(this.eC(), ichatbasecomponent),
                  PacketSendListener.a(
                     () -> {
                        boolean flag1 = true;
                        String s = ichatbasecomponent.a(256);
                        IChatMutableComponent ichatmutablecomponent = IChatBaseComponent.a(
                           "death.attack.message_too_long", IChatBaseComponent.b(s).a(EnumChatFormat.o)
                        );
                        IChatMutableComponent ichatmutablecomponent1 = IChatBaseComponent.a("death.attack.even_more_magic", this.G_())
                           .a(chatmodifier -> chatmodifier.a(new ChatHoverable(ChatHoverable.EnumHoverAction.a, ichatmutablecomponent)));
                        return new ClientboundPlayerCombatKillPacket(this.eC(), ichatmutablecomponent1);
                     }
                  )
               );
            ScoreboardTeamBase scoreboardteambase = this.cb();
            if (scoreboardteambase == null || scoreboardteambase.k() == ScoreboardTeamBase.EnumNameTagVisibility.a) {
               this.c.ac().a(ichatbasecomponent, false);
            } else if (scoreboardteambase.k() == ScoreboardTeamBase.EnumNameTagVisibility.c) {
               this.c.ac().a(this, ichatbasecomponent);
            } else if (scoreboardteambase.k() == ScoreboardTeamBase.EnumNameTagVisibility.d) {
               this.c.ac().b(this, ichatbasecomponent);
            }
         } else {
            this.b.a(new ClientboundPlayerCombatKillPacket(this.eC(), CommonComponents.a));
         }

         this.fX();
         if (this.H.W().b(GameRules.J)) {
            this.gj();
         }

         this.es();
         if (!event.getKeepInventory()) {
            this.fJ().a();
         }

         this.c(this);
         this.H.getCraftServer().getScoreboardManager().getScoreboardScores(IScoreboardCriteria.c, this.cu(), ScoreboardScore::a);
         EntityLiving entityliving = this.eD();
         if (entityliving != null) {
            this.b(StatisticList.h.b(entityliving.ae()));
            entityliving.a(this, this.bg, damagesource);
            this.f(entityliving);
         }

         this.H.a(this, (byte)3);
         this.a(StatisticList.N);
         this.a(StatisticList.i.b(StatisticList.m));
         this.a(StatisticList.i.b(StatisticList.n));
         this.av();
         this.j(0);
         this.a_(false);
         this.eC().g();
         this.a(Optional.of(GlobalPos.a(this.H.ab(), this.dg())));
      }
   }

   private void gj() {
      AxisAlignedBB axisalignedbb = new AxisAlignedBB(this.dg()).c(32.0, 10.0, 32.0);
      this.H
         .a(EntityInsentient.class, axisalignedbb, IEntitySelector.f)
         .stream()
         .filter(entityinsentient -> entityinsentient instanceof IEntityAngerable)
         .forEach(entityinsentient -> ((IEntityAngerable)entityinsentient).a_((EntityHuman)this));
   }

   @Override
   public void a(Entity entity, int i, DamageSource damagesource) {
      if (entity != this) {
         super.a(entity, i, damagesource);
         this.r(i);
         String s = this.cu();
         String s1 = entity.cu();
         this.H.getCraftServer().getScoreboardManager().getScoreboardScores(IScoreboardCriteria.e, s, ScoreboardScore::a);
         if (entity instanceof EntityHuman) {
            this.a(StatisticList.Q);
            this.H.getCraftServer().getScoreboardManager().getScoreboardScores(IScoreboardCriteria.d, s, ScoreboardScore::a);
         } else {
            this.a(StatisticList.O);
         }

         this.a(s, s1, IScoreboardCriteria.l);
         this.a(s1, s, IScoreboardCriteria.m);
         CriterionTriggers.b.a(this, entity, damagesource);
      }
   }

   private void a(String s, String s1, IScoreboardCriteria[] aiscoreboardcriteria) {
      ScoreboardTeam scoreboardteam = this.fY().i(s1);
      if (scoreboardteam != null) {
         int i = scoreboardteam.n().b();
         if (i >= 0 && i < aiscoreboardcriteria.length) {
            this.H.getCraftServer().getScoreboardManager().getScoreboardScores(aiscoreboardcriteria[i], s, ScoreboardScore::a);
         }
      }
   }

   @Override
   public boolean a(DamageSource damagesource, float f) {
      if (this.b(damagesource)) {
         return false;
      } else {
         boolean flag = this.c.l() && this.gk() && damagesource.a(DamageTypeTags.m);
         if (!flag && this.cy > 0 && !damagesource.a(DamageTypeTags.d)) {
            return false;
         } else {
            Entity entity = damagesource.d();
            if (entity instanceof EntityHuman entityhuman && !this.a(entityhuman)) {
               return false;
            }

            if (entity instanceof EntityArrow entityarrow) {
               Entity entity1 = entityarrow.v();
               if (entity1 instanceof EntityHuman entityhuman1 && !this.a(entityhuman1)) {
                  return false;
               }
            }

            return super.a(damagesource, f);
         }
      }
   }

   @Override
   public boolean a(EntityHuman entityhuman) {
      return !this.gk() ? false : super.a(entityhuman);
   }

   private boolean gk() {
      return this.H.pvpMode;
   }

   @Nullable
   @Override
   protected ShapeDetectorShape a(WorldServer worldserver) {
      ShapeDetectorShape shapedetectorshape = super.a(worldserver);
      worldserver = shapedetectorshape == null ? worldserver : shapedetectorshape.world;
      if (shapedetectorshape != null && this.H.getTypeKey() == WorldDimension.b && worldserver != null && worldserver.getTypeKey() == WorldDimension.d) {
         Vec3D vec3d = shapedetectorshape.a.b(0.0, -1.0, 0.0);
         return new ShapeDetectorShape(vec3d, Vec3D.b, 90.0F, 0.0F, worldserver, shapedetectorshape.portalEventInfo);
      } else {
         return shapedetectorshape;
      }
   }

   @Nullable
   @Override
   public Entity b(WorldServer worldserver) {
      return this.changeDimension(worldserver, TeleportCause.UNKNOWN);
   }

   @Nullable
   public Entity changeDimension(WorldServer worldserver, TeleportCause cause) {
      if (this.fu()) {
         return this;
      } else {
         WorldServer worldserver1 = this.x();
         ResourceKey<WorldDimension> resourcekey = worldserver1.getTypeKey();
         if (resourcekey == WorldDimension.d && worldserver != null && worldserver.getTypeKey() == WorldDimension.b) {
            this.cD = true;
            this.ac();
            this.x().a(this, Entity.RemovalReason.e);
            if (!this.f) {
               this.f = true;
               this.b.a(new PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.e, this.cE ? 0.0F : 1.0F));
               this.cE = true;
            }

            return this;
         } else {
            ShapeDetectorShape shapedetectorshape = this.a(worldserver);
            if (shapedetectorshape == null) {
               return null;
            } else {
               worldserver1.ac().a("moving");
               worldserver = shapedetectorshape.world;
               if (worldserver != null) {
                  if (resourcekey == WorldDimension.b && worldserver.getTypeKey() == WorldDimension.c) {
                     this.cK = this.de();
                  } else if (worldserver.getTypeKey() == WorldDimension.d
                     && shapedetectorshape.portalEventInfo != null
                     && shapedetectorshape.portalEventInfo.getCanCreatePortal()) {
                     this.a(worldserver, BlockPosition.a(shapedetectorshape.a));
                  }
               }

               Location enter = this.getBukkitEntity().getLocation();
               Location exit = worldserver == null
                  ? null
                  : new Location(
                     worldserver.getWorld(),
                     shapedetectorshape.a.c,
                     shapedetectorshape.a.d,
                     shapedetectorshape.a.e,
                     shapedetectorshape.c,
                     shapedetectorshape.d
                  );
               PlayerTeleportEvent tpEvent = new PlayerTeleportEvent(this.getBukkitEntity(), enter, exit, cause);
               Bukkit.getServer().getPluginManager().callEvent(tpEvent);
               if (!tpEvent.isCancelled() && tpEvent.getTo() != null) {
                  exit = tpEvent.getTo();
                  worldserver = ((CraftWorld)exit.getWorld()).getHandle();
                  worldserver1.ac().c();
                  worldserver1.ac().a("placing");
                  this.cD = true;
                  this.b
                     .a(
                        new PacketPlayOutRespawn(
                           worldserver.Z(),
                           worldserver.ab(),
                           BiomeManager.a(worldserver.A()),
                           this.d.b(),
                           this.d.c(),
                           worldserver.ae(),
                           worldserver.z(),
                           (byte)3,
                           this.gi()
                        )
                     );
                  this.b.a(new PacketPlayOutServerDifficulty(this.H.ah(), this.H.n_().t()));
                  PlayerList playerlist = this.c.ac();
                  playerlist.d(this);
                  worldserver1.a(this, Entity.RemovalReason.e);
                  this.dD();
                  this.c(worldserver);
                  this.b.teleport(exit);
                  this.b.d();
                  worldserver.b(this);
                  worldserver1.ac().c();
                  this.e(worldserver1);
                  this.b.a(new PacketPlayOutAbilities(this.fK()));
                  playerlist.a(this, worldserver);
                  playerlist.e(this);

                  for(MobEffect mobeffect : this.el()) {
                     this.b.a(new PacketPlayOutEntityEffect(this.af(), mobeffect));
                  }

                  this.b.a(new PacketPlayOutWorldEvent(1032, BlockPosition.b, 0, false));
                  this.cx = -1;
                  this.cu = -1.0F;
                  this.cv = -1;
                  PlayerChangedWorldEvent changeEvent = new PlayerChangedWorldEvent(this.getBukkitEntity(), worldserver1.getWorld());
                  this.H.getCraftServer().getPluginManager().callEvent(changeEvent);
                  return this;
               } else {
                  return null;
               }
            }
         }
      }
   }

   @Override
   protected CraftPortalEvent callPortalEvent(
      Entity entity, WorldServer exitWorldServer, Position exitPosition, TeleportCause cause, int searchRadius, int creationRadius
   ) {
      Location enter = this.getBukkitEntity().getLocation();
      Location exit = new Location(exitWorldServer.getWorld(), exitPosition.a(), exitPosition.b(), exitPosition.c(), this.dw(), this.dy());
      PlayerPortalEvent event = new PlayerPortalEvent(this.getBukkitEntity(), enter, exit, cause, searchRadius, true, creationRadius);
      Bukkit.getServer().getPluginManager().callEvent(event);
      return !event.isCancelled() && event.getTo() != null && event.getTo().getWorld() != null ? new CraftPortalEvent(event) : null;
   }

   private void a(WorldServer worldserver, BlockPosition blockposition) {
      BlockPosition.MutableBlockPosition blockposition_mutableblockposition = blockposition.j();
      BlockStateListPopulator blockList = new BlockStateListPopulator(worldserver);

      for(int i = -2; i <= 2; ++i) {
         for(int j = -2; j <= 2; ++j) {
            for(int k = -1; k < 3; ++k) {
               IBlockData iblockdata = k == -1 ? Blocks.cn.o() : Blocks.a.o();
               blockList.a(blockposition_mutableblockposition.g(blockposition).e(j, k, i), iblockdata, 3);
            }
         }
      }

      PortalCreateEvent portalEvent = new PortalCreateEvent(blockList.getList(), worldserver.getWorld(), this.getBukkitEntity(), CreateReason.END_PLATFORM);
      worldserver.getCraftServer().getPluginManager().callEvent(portalEvent);
      if (!portalEvent.isCancelled()) {
         blockList.updateList();
      }
   }

   @Override
   protected Optional<BlockUtil.Rectangle> getExitPortal(
      WorldServer worldserver, BlockPosition blockposition, boolean flag, WorldBorder worldborder, int searchRadius, boolean canCreatePortal, int createRadius
   ) {
      Optional<BlockUtil.Rectangle> optional = super.getExitPortal(worldserver, blockposition, flag, worldborder, searchRadius, canCreatePortal, createRadius);
      if (!optional.isPresent() && canCreatePortal) {
         EnumDirection.EnumAxis enumdirection_enumaxis = this.H.a_(this.aw).d(BlockPortal.a).orElse(EnumDirection.EnumAxis.a);
         Optional<BlockUtil.Rectangle> optional1 = worldserver.o().createPortal(blockposition, enumdirection_enumaxis, this, createRadius);
         optional1.isPresent();
         return optional1;
      } else {
         return optional;
      }
   }

   public void e(WorldServer worldserver) {
      ResourceKey<World> resourcekey = worldserver.ab();
      ResourceKey<World> resourcekey1 = this.H.ab();
      ResourceKey<World> maindimensionkey = CraftDimensionUtil.getMainDimensionKey(worldserver);
      ResourceKey<World> maindimensionkey1 = CraftDimensionUtil.getMainDimensionKey(this.H);
      CriterionTriggers.v.a(this, maindimensionkey, maindimensionkey1);
      if (maindimensionkey != resourcekey || maindimensionkey1 != resourcekey1) {
         CriterionTriggers.v.a(this, resourcekey, resourcekey1);
      }

      if (maindimensionkey == World.i && maindimensionkey1 == World.h && this.cK != null) {
         CriterionTriggers.C.a(this, this.cK);
      }

      if (maindimensionkey1 != World.i) {
         this.cK = null;
      }
   }

   @Override
   public boolean a(EntityPlayer entityplayer) {
      return entityplayer.F_() ? this.G() == this : (this.F_() ? false : super.a(entityplayer));
   }

   @Override
   public void a(Entity entity, int i) {
      super.a(entity, i);
      this.bP.d();
   }

   private Either<EntityHuman.EnumBedResult, Unit> getBedResult(BlockPosition blockposition, EnumDirection enumdirection) {
      if (this.fu() || !this.bq()) {
         return Either.left(EntityHuman.EnumBedResult.e);
      } else if (!this.H.q_().j() || !this.H.q_().l()) {
         return Either.left(EntityHuman.EnumBedResult.a);
      } else if (!this.a(blockposition, enumdirection)) {
         return Either.left(EntityHuman.EnumBedResult.c);
      } else if (this.b(blockposition, enumdirection)) {
         return Either.left(EntityHuman.EnumBedResult.d);
      } else {
         this.a(this.H.ab(), blockposition, this.dw(), false, true);
         if (this.H.M()) {
            return Either.left(EntityHuman.EnumBedResult.b);
         } else {
            if (!this.f()) {
               double d0 = 8.0;
               double d1 = 5.0;
               Vec3D vec3d = Vec3D.c(blockposition);
               List<EntityMonster> list = this.H
                  .a(
                     EntityMonster.class,
                     new AxisAlignedBB(vec3d.a() - 8.0, vec3d.b() - 5.0, vec3d.c() - 8.0, vec3d.a() + 8.0, vec3d.b() + 5.0, vec3d.c() + 8.0),
                     entitymonster -> entitymonster.e(this)
                  );
               if (!list.isEmpty()) {
                  return Either.left(EntityHuman.EnumBedResult.f);
               }
            }

            return Either.right(Unit.a);
         }
      }
   }

   @Override
   public Either<EntityHuman.EnumBedResult, Unit> startSleepInBed(BlockPosition blockposition, boolean force) {
      EnumDirection enumdirection = this.H.a_(blockposition).c(BlockFacingHorizontal.aD);
      Either<EntityHuman.EnumBedResult, Unit> bedResult = this.getBedResult(blockposition, enumdirection);
      if (bedResult.left().orElse(null) == EntityHuman.EnumBedResult.e) {
         return bedResult;
      } else {
         if (force) {
            bedResult = Either.right(Unit.a);
         }

         bedResult = CraftEventFactory.callPlayerBedEnterEvent(this, blockposition, bedResult);
         if (bedResult.left().isPresent()) {
            return bedResult;
         } else {
            Either<EntityHuman.EnumBedResult, Unit> either = super.startSleepInBed(blockposition, force).ifRight(unit -> {
               this.a(StatisticList.ap);
               CriterionTriggers.q.a(this);
            });
            if (!this.x().d()) {
               this.a(IChatBaseComponent.c("sleep.not_possible"), true);
            }

            ((WorldServer)this.H).e();
            return either;
         }
      }
   }

   @Override
   public void b(BlockPosition blockposition) {
      this.a(StatisticList.i.b(StatisticList.n));
      super.b(blockposition);
   }

   private boolean a(BlockPosition blockposition, EnumDirection enumdirection) {
      return this.g(blockposition) || this.g(blockposition.a(enumdirection.g()));
   }

   private boolean g(BlockPosition blockposition) {
      Vec3D vec3d = Vec3D.c(blockposition);
      return Math.abs(this.dl() - vec3d.a()) <= 3.0 && Math.abs(this.dn() - vec3d.b()) <= 2.0 && Math.abs(this.dr() - vec3d.c()) <= 3.0;
   }

   private boolean b(BlockPosition blockposition, EnumDirection enumdirection) {
      BlockPosition blockposition1 = blockposition.c();
      return !this.f(blockposition1) || !this.f(blockposition1.a(enumdirection.g()));
   }

   @Override
   public void a(boolean flag, boolean flag1) {
      if (this.fu()) {
         CraftPlayer player = this.getBukkitEntity();
         BlockPosition bedPosition = this.fs().orElse(null);
         Block bed;
         if (bedPosition != null) {
            bed = this.H.getWorld().getBlockAt(bedPosition.u(), bedPosition.v(), bedPosition.w());
         } else {
            bed = this.H.getWorld().getBlockAt(player.getLocation());
         }

         PlayerBedLeaveEvent event = new PlayerBedLeaveEvent(player, bed, true);
         this.H.getCraftServer().getPluginManager().callEvent(event);
         if (!event.isCancelled()) {
            if (this.fu()) {
               this.x().k().a(this, new PacketPlayOutAnimation(this, 2));
            }

            super.a(flag, flag1);
            if (this.b != null) {
               this.b.a(this.dl(), this.dn(), this.dr(), this.dw(), this.dy());
            }
         }
      }
   }

   @Override
   public void a(double d0, double d1, double d2) {
      this.by();
      this.e(d0, d1, d2);
   }

   @Override
   public boolean b(DamageSource damagesource) {
      return super.b(damagesource) || this.K();
   }

   @Override
   protected void a(double d0, boolean flag, IBlockData iblockdata, BlockPosition blockposition) {
   }

   @Override
   protected void c(BlockPosition blockposition) {
      if (!this.F_()) {
         super.c(blockposition);
      }
   }

   public void a(double d0, boolean flag) {
      if (!this.da()) {
         BlockPosition blockposition = this.aC();
         super.a(d0, flag, this.H.a_(blockposition), blockposition);
      }
   }

   @Override
   public void a(TileEntitySign tileentitysign) {
      tileentitysign.a(this.cs());
      this.b.a(new PacketPlayOutBlockChange(this.H, tileentitysign.p()));
      this.b.a(new PacketPlayOutOpenSignEditor(tileentitysign.p()));
   }

   public int nextContainerCounter() {
      this.cY = this.cY % 100 + 1;
      return this.cY;
   }

   @Override
   public OptionalInt a(@Nullable ITileInventory itileinventory) {
      if (itileinventory == null) {
         return OptionalInt.empty();
      } else {
         this.nextContainerCounter();
         Container container = itileinventory.createMenu(this.cY, this.fJ(), this);
         if (container != null) {
            container.setTitle(itileinventory.G_());
            boolean cancelled = false;
            container = CraftEventFactory.callInventoryOpenEvent(this, container, cancelled);
            if (container == null && !cancelled) {
               if (itileinventory instanceof IInventory) {
                  ((IInventory)itileinventory).c(this);
               } else if (itileinventory instanceof BlockChest.DoubleInventory) {
                  ((BlockChest.DoubleInventory)itileinventory).inventorylargechest.c(this);
               }

               return OptionalInt.empty();
            }
         }

         if (container == null) {
            if (this.F_()) {
               this.a(IChatBaseComponent.c("container.spectatorCantOpen").a(EnumChatFormat.m), true);
            }

            return OptionalInt.empty();
         } else {
            this.bP = container;
            this.b.a(new PacketPlayOutOpenWindow(container.j, container.a(), container.getTitle()));
            this.a(container);
            return OptionalInt.of(this.cY);
         }
      }
   }

   @Override
   public void a(int i, MerchantRecipeList merchantrecipelist, int j, int k, boolean flag, boolean flag1) {
      this.b.a(new PacketPlayOutOpenWindowMerchant(i, merchantrecipelist, j, k, flag, flag1));
   }

   @Override
   public void a(EntityHorseAbstract entityhorseabstract, IInventory iinventory) {
      this.nextContainerCounter();
      Container container = new ContainerHorse(this.cY, this.fJ(), iinventory, entityhorseabstract);
      container.setTitle(entityhorseabstract.G_());
      container = CraftEventFactory.callInventoryOpenEvent(this, container);
      if (container == null) {
         iinventory.c(this);
      } else {
         if (this.bP != this.bO) {
            this.q();
         }

         this.b.a(new PacketPlayOutOpenWindowHorse(this.cY, iinventory.b(), entityhorseabstract.af()));
         this.bP = container;
         this.a(this.bP);
      }
   }

   @Override
   public void a(net.minecraft.world.item.ItemStack itemstack, EnumHand enumhand) {
      if (itemstack.a(Items.td)) {
         if (ItemWrittenBook.a(itemstack, this.cZ(), this)) {
            this.bP.d();
         }

         this.b.a(new PacketPlayOutOpenBook(enumhand));
      }
   }

   @Override
   public void a(TileEntityCommand tileentitycommand) {
      this.b.a(PacketPlayOutTileEntityData.a(tileentitycommand, TileEntity::o));
   }

   @Override
   public void q() {
      CraftEventFactory.handleInventoryCloseEvent(this);
      this.b.a(new PacketPlayOutCloseWindow(this.bP.j));
      this.r();
   }

   @Override
   public void r() {
      this.bP.b(this);
      this.bO.a(this.bP);
      this.bP = this.bO;
   }

   public void a(float f, float f1, boolean flag, boolean flag1) {
      if (this.bL()) {
         if (f >= -1.0F && f <= 1.0F) {
            this.bj = f;
         }

         if (f1 >= -1.0F && f1 <= 1.0F) {
            this.bl = f1;
         }

         this.bi = flag;
         if (flag1 != this.bO()) {
            PlayerToggleSneakEvent event = new PlayerToggleSneakEvent(this.getBukkitEntity(), flag1);
            this.c.server.getPluginManager().callEvent(event);
            if (event.isCancelled()) {
               return;
            }
         }

         this.f(flag1);
      }
   }

   @Override
   public void a(Statistic<?> statistic, int i) {
      this.cn.b(this, statistic, i);
      this.H.getCraftServer().getScoreboardManager().getScoreboardScores(statistic, this.cu(), scoreboardscore -> scoreboardscore.a(i));
   }

   @Override
   public void a(Statistic<?> statistic) {
      this.cn.a(this, statistic, 0);
      this.H.getCraftServer().getScoreboardManager().getScoreboardScores(statistic, this.cu(), ScoreboardScore::c);
   }

   @Override
   public int a(Collection<IRecipe<?>> collection) {
      return this.cF.a(collection, this);
   }

   @Override
   public void a(MinecraftKey[] aminecraftkey) {
      List<IRecipe<?>> list = Lists.newArrayList();

      for(MinecraftKey minecraftkey : aminecraftkey) {
         Optional<? extends IRecipe<?>> optional = this.c.aE().a(minecraftkey);
         optional.ifPresent(list::add);
      }

      this.a(list);
   }

   @Override
   public int b(Collection<IRecipe<?>> collection) {
      return this.cF.b(collection, this);
   }

   @Override
   public void d(int i) {
      super.d(i);
      this.cx = -1;
   }

   @Override
   public void s() {
      this.cI = true;
      this.bx();
      if (this.fu()) {
         this.a(true, false);
      }
   }

   public boolean t() {
      return this.cI;
   }

   public void u() {
      this.cu = -1.0E8F;
      this.cx = -1;
   }

   @Override
   public void a(IChatBaseComponent ichatbasecomponent, boolean flag) {
      this.b(ichatbasecomponent, flag);
   }

   @Override
   protected void Y_() {
      if (!this.bu.b() && this.fe()) {
         this.b.a(new PacketPlayOutEntityStatus(this, (byte)9));
         super.Y_();
      }
   }

   @Override
   public void a(ArgumentAnchor.Anchor argumentanchor_anchor, Vec3D vec3d) {
      super.a(argumentanchor_anchor, vec3d);
      this.b.a(new PacketPlayOutLookAt(argumentanchor_anchor, vec3d.c, vec3d.d, vec3d.e));
   }

   public void a(ArgumentAnchor.Anchor argumentanchor_anchor, Entity entity, ArgumentAnchor.Anchor argumentanchor_anchor1) {
      Vec3D vec3d = argumentanchor_anchor1.a(entity);
      super.a(argumentanchor_anchor, vec3d);
      this.b.a(new PacketPlayOutLookAt(argumentanchor_anchor, entity, argumentanchor_anchor1));
   }

   public void a(EntityPlayer entityplayer, boolean flag) {
      this.cU = entityplayer.cU;
      this.cS = entityplayer.cS;
      this.cX = entityplayer.cX;
      this.d.a(entityplayer.d.b(), entityplayer.d.c());
      this.w();
      if (flag) {
         this.fJ().a(entityplayer.fJ());
         this.c(entityplayer.eo());
         this.bQ = entityplayer.bQ;
         this.cc = entityplayer.cc;
         this.cd = entityplayer.cd;
         this.ce = entityplayer.ce;
         this.q(entityplayer.fE());
         this.aw = entityplayer.aw;
      } else if (this.H.W().b(GameRules.d) || entityplayer.F_()) {
         this.fJ().a(entityplayer.fJ());
         this.cc = entityplayer.cc;
         this.cd = entityplayer.cd;
         this.ce = entityplayer.ce;
         this.q(entityplayer.fE());
      }

      this.cf = entityplayer.cf;
      this.bN = entityplayer.bN;
      this.aj().b(bJ, entityplayer.aj().a(bJ));
      this.cx = -1;
      this.cu = -1.0F;
      this.cv = -1;
      this.cE = entityplayer.cE;
      this.cK = entityplayer.cK;
      this.i(entityplayer.ga());
      this.j(entityplayer.gb());
      this.a(entityplayer.gi());
   }

   @Override
   protected void a(MobEffect mobeffect, @Nullable Entity entity) {
      super.a(mobeffect, entity);
      this.b.a(new PacketPlayOutEntityEffect(this.af(), mobeffect));
      if (mobeffect.c() == MobEffects.y) {
         this.cH = this.ag;
         this.cG = this.de();
      }

      CriterionTriggers.A.a(this, entity);
   }

   @Override
   protected void a(MobEffect mobeffect, boolean flag, @Nullable Entity entity) {
      super.a(mobeffect, flag, entity);
      this.b.a(new PacketPlayOutEntityEffect(this.af(), mobeffect));
      CriterionTriggers.A.a(this, entity);
   }

   @Override
   protected void a(MobEffect mobeffect) {
      super.a(mobeffect);
      this.b.a(new PacketPlayOutRemoveEntityEffect(this.af(), mobeffect.c()));
      if (mobeffect.c() == MobEffects.y) {
         this.cG = null;
      }

      CriterionTriggers.A.a(this, null);
   }

   @Override
   public void b(double d0, double d1, double d2) {
      this.b.a(d0, d1, d2, this.dw(), this.dy(), RelativeMovement.g);
   }

   @Override
   public void c(double d0, double d1, double d2) {
      this.b.a(this.dl() + d0, this.dn() + d1, this.dr() + d2, this.dw(), this.dy(), RelativeMovement.f);
   }

   @Override
   public boolean a(WorldServer worldserver, double d0, double d1, double d2, Set<RelativeMovement> set, float f, float f1) {
      return this.teleportTo(worldserver, d0, d1, d2, set, f, f1, TeleportCause.UNKNOWN);
   }

   @Override
   public boolean teleportTo(WorldServer worldserver, double d0, double d1, double d2, Set<RelativeMovement> set, float f, float f1, TeleportCause cause) {
      ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(BlockPosition.a(d0, d1, d2));
      worldserver.k().a(TicketType.g, chunkcoordintpair, 1, this.af());
      this.bz();
      if (this.fu()) {
         this.a(true, true);
      }

      if (worldserver == this.H) {
         this.b.teleport(d0, d1, d2, f, f1, set, cause);
      } else {
         this.teleportTo(worldserver, d0, d1, d2, f, f1, cause);
      }

      this.r(f);
      return true;
   }

   @Override
   public void d(double d0, double d1, double d2) {
      this.b(d0, d1, d2);
      this.b.d();
   }

   @Override
   public void a(Entity entity) {
      this.x().k().a(this, new PacketPlayOutAnimation(entity, 4));
   }

   @Override
   public void b(Entity entity) {
      this.x().k().a(this, new PacketPlayOutAnimation(entity, 5));
   }

   @Override
   public void w() {
      if (this.b != null) {
         this.b.a(new PacketPlayOutAbilities(this.fK()));
         this.F();
      }
   }

   public WorldServer x() {
      return (WorldServer)this.H;
   }

   public boolean a(EnumGamemode enumgamemode) {
      if (!this.d.a(enumgamemode)) {
         return false;
      } else {
         this.b.a(new PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.d, (float)enumgamemode.a()));
         if (enumgamemode == EnumGamemode.d) {
            this.fX();
            this.bz();
         } else {
            this.c(this);
         }

         this.w();
         this.fc();
         return true;
      }
   }

   @Override
   public boolean F_() {
      return this.d.b() == EnumGamemode.d;
   }

   @Override
   public boolean f() {
      return this.d.b() == EnumGamemode.b;
   }

   @Override
   public void a(IChatBaseComponent ichatbasecomponent) {
      this.b(ichatbasecomponent, false);
   }

   public void b(IChatBaseComponent ichatbasecomponent, boolean flag) {
      if (this.v(flag)) {
         this.b
            .a(
               new ClientboundSystemChatPacket(ichatbasecomponent, flag),
               PacketSendListener.a(
                  () -> {
                     if (this.v(false)) {
                        boolean flag1 = true;
                        String s = ichatbasecomponent.a(256);
                        IChatMutableComponent ichatmutablecomponent = IChatBaseComponent.b(s).a(EnumChatFormat.o);
                        return new ClientboundSystemChatPacket(
                           IChatBaseComponent.a("multiplayer.message_not_delivered", ichatmutablecomponent).a(EnumChatFormat.m), false
                        );
                     } else {
                        return null;
                     }
                  }
               )
            );
      }
   }

   public void a(OutgoingChatMessage outgoingchatmessage, boolean flag, ChatMessageType.a chatmessagetype_a) {
      if (this.gm()) {
         outgoingchatmessage.a(this, flag, chatmessagetype_a);
      }
   }

   public String y() {
      SocketAddress socketaddress = this.b.e();
      return socketaddress instanceof InetSocketAddress inetsocketaddress ? InetAddresses.toAddrString(inetsocketaddress.getAddress()) : "<unknown>";
   }

   public void a(PacketPlayInSettings packetplayinsettings) {
      if (this.fd() != packetplayinsettings.g()) {
         PlayerChangedMainHandEvent event = new PlayerChangedMainHandEvent(
            this.getBukkitEntity(), this.fd() == EnumMainHand.a ? MainHand.LEFT : MainHand.RIGHT
         );
         this.c.server.getPluginManager().callEvent(event);
      }

      if (!this.locale.equals(packetplayinsettings.b)) {
         PlayerLocaleChangeEvent event = new PlayerLocaleChangeEvent(this.getBukkitEntity(), packetplayinsettings.b);
         this.c.server.getPluginManager().callEvent(event);
      }

      this.locale = packetplayinsettings.b;
      this.clientViewDistance = packetplayinsettings.c;
      this.cz = packetplayinsettings.d();
      this.cA = packetplayinsettings.e();
      this.cS = packetplayinsettings.h();
      this.cT = packetplayinsettings.i();
      this.aj().b(bJ, (byte)packetplayinsettings.f());
      this.aj().b(bK, (byte)(packetplayinsettings.g() == EnumMainHand.a ? 0 : 1));
   }

   public boolean z() {
      return this.cA;
   }

   public EnumChatVisibility A() {
      return this.cz;
   }

   private boolean v(boolean flag) {
      return this.cz == EnumChatVisibility.c ? flag : true;
   }

   private boolean gm() {
      return this.cz == EnumChatVisibility.a;
   }

   public void a(String s, String s1, boolean flag, @Nullable IChatBaseComponent ichatbasecomponent) {
      this.b.a(new PacketPlayOutResourcePackSend(s, s1, flag, ichatbasecomponent));
   }

   public void a(ServerPing serverping) {
      this.b.a(new ClientboundServerDataPacket(serverping.a(), serverping.d().map(ServerPing.a::a), serverping.e()));
   }

   @Override
   protected int B() {
      return this.c.c(this.fI());
   }

   @Override
   public void C() {
      this.cB = SystemUtils.b();
   }

   public ServerStatisticManager D() {
      return this.cn;
   }

   public RecipeBookServer E() {
      return this.cF;
   }

   @Override
   protected void F() {
      if (this.F_()) {
         this.ej();
         this.j(true);
      } else {
         super.F();
      }
   }

   public Entity G() {
      return (Entity)(this.cC == null ? this : this.cC);
   }

   @Override
   public void c(@Nullable Entity entity) {
      Entity entity1 = this.G();
      this.cC = (Entity)(entity == null ? this : entity);
      if (entity1 != this.cC) {
         World world = this.cC.Y();
         if (world instanceof WorldServer worldserver) {
            this.teleportTo(worldserver, this.cC.dl(), this.cC.dn(), this.cC.dr(), Set.of(), this.dw(), this.dy(), TeleportCause.SPECTATE);
         }

         if (entity != null) {
            this.x().k().a(this);
         }

         this.b.a(new PacketPlayOutCamera(this.cC));
         this.b.d();
      }
   }

   @Override
   protected void H() {
      if (!this.cD) {
         super.H();
      }
   }

   @Override
   public void d(Entity entity) {
      if (this.d.b() == EnumGamemode.d) {
         this.c(entity);
      } else {
         super.d(entity);
      }
   }

   public long I() {
      return this.cB;
   }

   @Nullable
   public IChatBaseComponent J() {
      return this.listName;
   }

   @Override
   public void a(EnumHand enumhand) {
      super.a(enumhand);
      this.gd();
   }

   public boolean K() {
      return this.cD;
   }

   public void L() {
      this.cD = false;
   }

   public AdvancementDataPlayer M() {
      return this.cm;
   }

   public void a(WorldServer worldserver, double d0, double d1, double d2, float f, float f1) {
      this.teleportTo(worldserver, d0, d1, d2, f, f1, TeleportCause.UNKNOWN);
   }

   public void teleportTo(WorldServer worldserver, double d0, double d1, double d2, float f, float f1, TeleportCause cause) {
      this.c(this);
      this.bz();
      this.getBukkitEntity().teleport(new Location(worldserver.getWorld(), d0, d1, d2, f, f1), cause);
   }

   @Nullable
   public BlockPosition N() {
      return this.cO;
   }

   public float O() {
      return this.cQ;
   }

   public ResourceKey<World> P() {
      return this.cN;
   }

   public boolean Q() {
      return this.cP;
   }

   public void a(ResourceKey<World> resourcekey, @Nullable BlockPosition blockposition, float f, boolean flag, boolean flag1) {
      if (blockposition != null) {
         boolean flag2 = blockposition.equals(this.cO) && resourcekey.equals(this.cN);
         if (flag1 && !flag2) {
            this.a(IChatBaseComponent.c("block.minecraft.set_spawn"));
         }

         this.cO = blockposition;
         this.cN = resourcekey;
         this.cQ = f;
         this.cP = flag;
      } else {
         this.cO = null;
         this.cN = World.h;
         this.cQ = 0.0F;
         this.cP = false;
      }
   }

   public void a(ChunkCoordIntPair chunkcoordintpair, Packet<?> packet) {
      this.b.a(packet);
   }

   public void a(ChunkCoordIntPair chunkcoordintpair) {
      if (this.bq()) {
         this.b.a(new PacketPlayOutUnloadChunk(chunkcoordintpair.e, chunkcoordintpair.f));
      }
   }

   public SectionPosition R() {
      return this.cM;
   }

   public void a(SectionPosition sectionposition) {
      this.cM = sectionposition;
   }

   @Override
   public void a(SoundEffect soundeffect, SoundCategory soundcategory, float f, float f1) {
      this.b.a(new PacketPlayOutNamedSoundEffect(BuiltInRegistries.c.d(soundeffect), soundcategory, this.dl(), this.dn(), this.dr(), f, f1, this.af.g()));
   }

   @Override
   public Packet<PacketListenerPlayOut> S() {
      return new PacketPlayOutNamedEntitySpawn(this);
   }

   @Override
   public EntityItem a(net.minecraft.world.item.ItemStack itemstack, boolean flag, boolean flag1) {
      EntityItem entityitem = super.a(itemstack, flag, flag1);
      if (entityitem == null) {
         return null;
      } else {
         this.H.b(entityitem);
         net.minecraft.world.item.ItemStack itemstack1 = entityitem.i();
         if (flag1) {
            if (!itemstack1.b()) {
               this.a(StatisticList.f.b(itemstack1.c()), itemstack.K());
            }

            this.a(StatisticList.F);
         }

         return entityitem;
      }
   }

   public ITextFilter T() {
      return this.cR;
   }

   public void c(WorldServer worldserver) {
      this.H = worldserver;
      this.d.a(worldserver);
   }

   @Nullable
   private static EnumGamemode a(@Nullable NBTTagCompound nbttagcompound, String s) {
      return nbttagcompound != null && nbttagcompound.b(s, 99) ? EnumGamemode.a(nbttagcompound.h(s)) : null;
   }

   private EnumGamemode b(@Nullable EnumGamemode enumgamemode) {
      EnumGamemode enumgamemode1 = this.c.aZ();
      return enumgamemode1 != null ? enumgamemode1 : (enumgamemode != null ? enumgamemode : this.c.h_());
   }

   @Override
   public void c(@Nullable NBTTagCompound nbttagcompound) {
      this.d.a(this.b(a(nbttagcompound, "playerGameType")), a(nbttagcompound, "previousPlayerGameType"));
   }

   private void k(NBTTagCompound nbttagcompound) {
      nbttagcompound.a("playerGameType", this.d.b().a());
      EnumGamemode enumgamemode = this.d.c();
      if (enumgamemode != null) {
         nbttagcompound.a("previousPlayerGameType", enumgamemode.a());
      }
   }

   @Override
   public boolean U() {
      return this.cS;
   }

   public boolean b(EntityPlayer entityplayer) {
      return entityplayer == this ? false : this.cS || entityplayer.cS;
   }

   @Override
   public boolean a(World world, BlockPosition blockposition) {
      return super.a(world, blockposition) && world.a(this, blockposition);
   }

   @Override
   protected void a(net.minecraft.world.item.ItemStack itemstack) {
      CriterionTriggers.T.a(this, itemstack);
      super.a(itemstack);
   }

   public boolean a(boolean flag) {
      PlayerInventory playerinventory = this.fJ();
      net.minecraft.world.item.ItemStack itemstack = playerinventory.a(flag);
      this.bP.b(playerinventory, playerinventory.l).ifPresent(i -> this.bP.a(i, playerinventory.f()));
      return this.a(itemstack, false, true) != null;
   }

   public boolean V() {
      return this.cT;
   }

   @Override
   public Optional<WardenSpawnTracker> W() {
      return Optional.of(this.cU);
   }

   @Override
   public void a(EntityItem entityitem) {
      super.a(entityitem);
      Entity entity = entityitem.v();
      if (entity != null) {
         CriterionTriggers.P.a(this, entityitem.i(), entity);
      }
   }

   public void a(RemoteChatSession remotechatsession) {
      this.cX = remotechatsession;
   }

   @Nullable
   public RemoteChatSession X() {
      return this.cX;
   }

   @Override
   public void a(double d0, double d1) {
      this.ci = (float)(MathHelper.d(d1, d0) * 180.0F / (float)Math.PI - (double)this.dw());
      this.b.a(new ClientboundHurtAnimationPacket(this));
   }

   public long getPlayerTime() {
      return this.relativeTime ? this.H.V() + this.timeOffset : this.H.V() - this.H.V() % 24000L + this.timeOffset;
   }

   public WeatherType getPlayerWeather() {
      return this.weather;
   }

   public void setPlayerWeather(WeatherType type, boolean plugin) {
      if (plugin || this.weather == null) {
         if (plugin) {
            this.weather = type;
         }

         if (type == WeatherType.DOWNFALL) {
            this.b.a(new PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.c, 0.0F));
         } else {
            this.b.a(new PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.b, 0.0F));
         }
      }
   }

   public void updateWeather(float oldRain, float newRain, float oldThunder, float newThunder) {
      if (this.weather == null) {
         if (oldRain != newRain) {
            this.b.a(new PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.h, newRain));
         }
      } else if (this.pluginRainPositionPrevious != this.pluginRainPosition) {
         this.b.a(new PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.h, this.pluginRainPosition));
      }

      if (oldThunder != newThunder) {
         if (this.weather != WeatherType.DOWNFALL && this.weather != null) {
            this.b.a(new PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.i, 0.0F));
         } else {
            this.b.a(new PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.i, newThunder));
         }
      }
   }

   public void tickWeather() {
      if (this.weather != null) {
         this.pluginRainPositionPrevious = this.pluginRainPosition;
         if (this.weather == WeatherType.DOWNFALL) {
            this.pluginRainPosition = (float)((double)this.pluginRainPosition + 0.01);
         } else {
            this.pluginRainPosition = (float)((double)this.pluginRainPosition - 0.01);
         }

         this.pluginRainPosition = MathHelper.a(this.pluginRainPosition, 0.0F, 1.0F);
      }
   }

   public void resetPlayerWeather() {
      this.weather = null;
      this.setPlayerWeather(this.H.n_().k() ? WeatherType.DOWNFALL : WeatherType.CLEAR, false);
   }

   @Override
   public String toString() {
      return super.toString() + "(" + this.cu() + " at " + this.dl() + "," + this.dn() + "," + this.dr() + ")";
   }

   public void forceSetPositionRotation(double x, double y, double z, float yaw, float pitch) {
      this.b(x, y, z, yaw, pitch);
      this.b.d();
   }

   @Override
   public boolean eP() {
      return super.eP() || !this.getBukkitEntity().isOnline();
   }

   @Override
   public Scoreboard fY() {
      return this.getBukkitEntity().getScoreboard().getHandle();
   }

   public void reset() {
      float exp = 0.0F;
      boolean keepInventory = this.H.W().b(GameRules.d);
      if (this.keepLevel) {
         exp = this.ce;
         this.newTotalExp = this.cd;
         this.newLevel = this.cc;
      }

      this.c(this.eE());
      this.fk();
      this.aK = 0;
      this.aa = 0.0F;
      this.bQ = new FoodMetaData(this);
      this.cc = this.newLevel;
      this.cd = this.newTotalExp;
      this.ce = 0.0F;
      this.aL = 0;
      this.setArrowCount(0, true);
      this.removeAllEffects(Cause.DEATH);
      this.bS = true;
      this.bP = this.bO;
      this.aX = null;
      this.bT = null;
      this.bN = new CombatTracker(this);
      this.cx = -1;
      if (this.keepLevel) {
         this.ce = exp;
      } else {
         this.d(this.newExp);
      }

      this.keepLevel = false;
      this.o(0.0, 0.0, 0.0);
   }

   public CraftPlayer getBukkitEntity() {
      return (CraftPlayer)super.getBukkitEntity();
   }
}
