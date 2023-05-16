package net.minecraft.world.entity;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.Object2DoubleArrayMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.BlockUtil;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportSystemDetails;
import net.minecraft.ReportedException;
import net.minecraft.SystemUtils;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.ICommandListener;
import net.minecraft.commands.arguments.ArgumentAnchor;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.IPosition;
import net.minecraft.core.Position;
import net.minecraft.core.SectionPosition;
import net.minecraft.core.particles.ParticleParamBlock;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.chat.ChatHoverable;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatMutableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketListenerPlayOut;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity;
import net.minecraft.network.protocol.game.VecDeltaCodec;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.TagsBlock;
import net.minecraft.tags.TagsEntity;
import net.minecraft.tags.TagsFluid;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.INamableTileEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.vehicle.EntityBoat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.item.enchantment.EnchantmentProtection;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IMaterial;
import net.minecraft.world.level.RayTrace;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.BlockFenceGate;
import net.minecraft.world.level.block.BlockHoney;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EnumBlockMirror;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.block.EnumRenderType;
import net.minecraft.world.level.block.SoundEffectType;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.dimension.DimensionManager;
import net.minecraft.world.level.dimension.WorldDimension;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.level.entity.EntityInLevelCallback;
import net.minecraft.world.level.gameevent.DynamicGameEventListener;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.material.EnumPistonReaction;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidType;
import net.minecraft.world.level.portal.BlockPortalShape;
import net.minecraft.world.level.portal.ShapeDetectorShape;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.MovingObjectPosition;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.Vec2F;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.phys.shapes.OperatorBoolean;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;
import net.minecraft.world.scores.ScoreboardTeam;
import net.minecraft.world.scores.ScoreboardTeamBase;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R3.SpigotTimings;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftPortalEvent;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.entity.Hanging;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pose;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.entity.EntityAirChangeEvent;
import org.bukkit.event.entity.EntityCombustByBlockEvent;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.entity.EntityPoseChangeEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.vehicle.VehicleBlockCollisionEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.projectiles.ProjectileSource;
import org.slf4j.Logger;
import org.spigotmc.ActivationRange;
import org.spigotmc.CustomTimingsHandler;
import org.spigotmc.event.entity.EntityDismountEvent;
import org.spigotmc.event.entity.EntityMountEvent;

public abstract class Entity implements INamableTileEntity, EntityAccess, ICommandListener {
   private static final int CURRENT_LEVEL = 2;
   private CraftEntity bukkitEntity;
   private static final Logger c = LogUtils.getLogger();
   public static final String v = "id";
   public static final String w = "Passengers";
   private static final AtomicInteger d = new AtomicInteger();
   private static final List<ItemStack> e = Collections.emptyList();
   public static final int x = 60;
   public static final int y = 300;
   public static final int z = 1024;
   public static final double A = 0.5000001;
   public static final float B = 0.11111111F;
   public static final int C = 140;
   public static final int D = 40;
   private static final AxisAlignedBB k = new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
   private static final double l = 0.014;
   private static final double m = 0.007;
   private static final double n = 0.0023333333333333335;
   public static final String E = "UUID";
   private static double o = 1.0;
   private final EntityTypes<?> p;
   private int q;
   public boolean F;
   public ImmutableList<Entity> r;
   protected int G;
   @Nullable
   private Entity s;
   public World H;
   public double I;
   public double J;
   public double K;
   private Vec3D t;
   private BlockPosition u;
   private ChunkCoordIntPair aC;
   private Vec3D aD;
   private float aE;
   private float aF;
   public float L;
   public float M;
   private AxisAlignedBB aG;
   public boolean N;
   public boolean O;
   public boolean P;
   public boolean Q;
   public boolean R;
   public boolean S;
   protected Vec3D T;
   @Nullable
   private Entity.RemovalReason aH;
   public static final float U = 0.6F;
   public static final float V = 1.8F;
   public float W;
   public float X;
   public float Y;
   public float Z;
   public float aa;
   private float aI;
   public double ab;
   public double ac;
   public double ad;
   private float aJ;
   public boolean ae;
   protected final RandomSource af;
   public int ag;
   public int aK;
   public boolean ah;
   protected Object2DoubleMap<TagKey<FluidType>> ai;
   protected boolean aj;
   private final Set<TagKey<FluidType>> aL;
   public int ak;
   protected boolean al;
   protected final DataWatcher am;
   protected static final DataWatcherObject<Byte> an = DataWatcher.a(Entity.class, DataWatcherRegistry.a);
   protected static final int ao = 0;
   private static final int aM = 1;
   private static final int aN = 3;
   private static final int aO = 4;
   private static final int aP = 5;
   protected static final int ap = 6;
   protected static final int aq = 7;
   private static final DataWatcherObject<Integer> aQ = DataWatcher.a(Entity.class, DataWatcherRegistry.b);
   private static final DataWatcherObject<Optional<IChatBaseComponent>> aR = DataWatcher.a(Entity.class, DataWatcherRegistry.g);
   private static final DataWatcherObject<Boolean> aS = DataWatcher.a(Entity.class, DataWatcherRegistry.k);
   private static final DataWatcherObject<Boolean> aT = DataWatcher.a(Entity.class, DataWatcherRegistry.k);
   private static final DataWatcherObject<Boolean> aU = DataWatcher.a(Entity.class, DataWatcherRegistry.k);
   protected static final DataWatcherObject<EntityPose> ar = DataWatcher.a(Entity.class, DataWatcherRegistry.v);
   private static final DataWatcherObject<Integer> aV = DataWatcher.a(Entity.class, DataWatcherRegistry.b);
   private EntityInLevelCallback aW;
   private final VecDeltaCodec aX;
   public boolean as;
   public boolean at;
   public int aY;
   protected boolean au;
   protected int av;
   protected BlockPosition aw;
   private boolean aZ;
   protected UUID ax;
   protected String ay;
   private boolean ba;
   private final Set<String> bb;
   private final double[] bc;
   private long bd;
   private EntitySize be;
   private float bf;
   public boolean az;
   public boolean aA;
   public boolean aB;
   private float bg;
   private int bh;
   public boolean bi;
   @Nullable
   private IBlockData bj;
   public boolean persist = true;
   public boolean visibleByDefault = true;
   public boolean valid;
   public boolean generation;
   public int maxAirTicks = this.getDefaultMaxAirSupply();
   public ProjectileSource projectileSource;
   public boolean lastDamageCancelled;
   public boolean persistentInvisibility = false;
   public BlockPosition lastLavaContact;
   public CustomTimingsHandler tickTimer = SpigotTimings.getEntityTimings(this);
   public final ActivationRange.ActivationType activationType = ActivationRange.initializeEntityActivationType(this);
   public final boolean defaultActivationState;
   public long activatedTick = -2147483648L;

   static boolean isLevelAtLeast(NBTTagCompound tag, int level) {
      return tag.e("Bukkit.updateLevel") && tag.h("Bukkit.updateLevel") >= level;
   }

   public CraftEntity getBukkitEntity() {
      if (this.bukkitEntity == null) {
         this.bukkitEntity = CraftEntity.getEntity(this.H.getCraftServer(), this);
      }

      return this.bukkitEntity;
   }

   @Override
   public CommandSender getBukkitSender(CommandListenerWrapper wrapper) {
      return this.getBukkitEntity();
   }

   public int getDefaultMaxAirSupply() {
      return 300;
   }

   public void inactiveTick() {
   }

   public float getBukkitYaw() {
      return this.aE;
   }

   public boolean isChunkLoaded() {
      return this.H.b((int)Math.floor(this.dl()) >> 4, (int)Math.floor(this.dr()) >> 4);
   }

   public Entity(EntityTypes<?> entitytypes, World world) {
      this.q = d.incrementAndGet();
      this.r = ImmutableList.of();
      this.aD = Vec3D.b;
      this.aG = k;
      this.T = Vec3D.b;
      this.aI = 1.0F;
      this.af = RandomSource.a();
      this.aK = -this.cY();
      this.ai = new Object2DoubleArrayMap(2);
      this.aL = new HashSet<>();
      this.al = true;
      this.aW = EntityInLevelCallback.a;
      this.aX = new VecDeltaCodec();
      this.ax = MathHelper.a(this.af);
      this.ay = this.ax.toString();
      this.bb = Sets.newHashSet();
      this.bc = new double[]{0.0, 0.0, 0.0};
      this.bj = null;
      this.p = entitytypes;
      this.H = world;
      this.be = entitytypes.n();
      this.t = Vec3D.b;
      this.u = BlockPosition.b;
      this.aC = ChunkCoordIntPair.b;
      if (world != null) {
         this.defaultActivationState = ActivationRange.initializeEntityActivationState(this, world.spigotConfig);
      } else {
         this.defaultActivationState = false;
      }

      this.am = new DataWatcher(this);
      this.am.a(an, (byte)0);
      this.am.a(aQ, this.cc());
      this.am.a(aS, false);
      this.am.a(aR, Optional.empty());
      this.am.a(aT, false);
      this.am.a(aU, false);
      this.am.a(ar, EntityPose.a);
      this.am.a(aV, 0);
      this.a_();
      this.aj().registrationLocked = true;
      this.e(0.0, 0.0, 0.0);
      this.bf = this.a(EntityPose.a, this.be);
   }

   public boolean a(BlockPosition blockposition, IBlockData iblockdata) {
      VoxelShape voxelshape = iblockdata.b(this.H, blockposition, VoxelShapeCollision.a(this));
      VoxelShape voxelshape1 = voxelshape.a((double)blockposition.u(), (double)blockposition.v(), (double)blockposition.w());
      return VoxelShapes.c(voxelshape1, VoxelShapes.a(this.cD()), OperatorBoolean.i);
   }

   public int B_() {
      ScoreboardTeamBase scoreboardteambase = this.cb();
      return scoreboardteambase != null && scoreboardteambase.n().f() != null ? scoreboardteambase.n().f() : 16777215;
   }

   public boolean F_() {
      return false;
   }

   public final void ac() {
      if (this.bM()) {
         this.bx();
      }

      if (this.bL()) {
         this.bz();
      }
   }

   public void f(double d0, double d1, double d2) {
      this.aX.e(new Vec3D(d0, d1, d2));
   }

   public VecDeltaCodec ad() {
      return this.aX;
   }

   public EntityTypes<?> ae() {
      return this.p;
   }

   @Override
   public int af() {
      return this.q;
   }

   public void e(int i) {
      this.q = i;
   }

   public Set<String> ag() {
      return this.bb;
   }

   public boolean a(String s) {
      return this.bb.size() >= 1024 ? false : this.bb.add(s);
   }

   public boolean b(String s) {
      return this.bb.remove(s);
   }

   public void ah() {
      this.a(Entity.RemovalReason.a);
      this.a(GameEvent.q);
   }

   public final void ai() {
      this.a(Entity.RemovalReason.b);
   }

   protected abstract void a_();

   public DataWatcher aj() {
      return this.am;
   }

   @Override
   public boolean equals(Object object) {
      return object instanceof Entity ? ((Entity)object).q == this.q : false;
   }

   @Override
   public int hashCode() {
      return this.q;
   }

   public void a(Entity.RemovalReason entity_removalreason) {
      this.b(entity_removalreason);
   }

   public void ak() {
   }

   public void b(EntityPose entitypose) {
      if (entitypose != this.al()) {
         this.H.getCraftServer().getPluginManager().callEvent(new EntityPoseChangeEvent(this.getBukkitEntity(), Pose.values()[entitypose.ordinal()]));
         this.am.b(ar, entitypose);
      }
   }

   public EntityPose al() {
      return this.am.a(ar);
   }

   public boolean c(EntityPose entitypose) {
      return this.al() == entitypose;
   }

   public boolean a(Entity entity, double d0) {
      return this.de().a((IPosition)entity.de(), d0);
   }

   public boolean a(Entity entity, double d0, double d1) {
      double d2 = entity.dl() - this.dl();
      double d3 = entity.dn() - this.dn();
      double d4 = entity.dr() - this.dr();
      return MathHelper.e(d2, d4) < MathHelper.k(d0) && MathHelper.k(d3) < MathHelper.k(d1);
   }

   protected void a(float f, float f1) {
      if (Float.isNaN(f)) {
         f = 0.0F;
      }

      if (f == Float.POSITIVE_INFINITY || f == Float.NEGATIVE_INFINITY) {
         if (this instanceof EntityPlayer) {
            this.H.getCraftServer().getLogger().warning(this.cu() + " was caught trying to crash the server with an invalid yaw");
            ((CraftPlayer)this.getBukkitEntity()).kickPlayer("Infinite yaw (Hacking?)");
         }

         f = 0.0F;
      }

      if (Float.isNaN(f1)) {
         f1 = 0.0F;
      }

      if (f1 == Float.POSITIVE_INFINITY || f1 == Float.NEGATIVE_INFINITY) {
         if (this instanceof EntityPlayer) {
            this.H.getCraftServer().getLogger().warning(this.cu() + " was caught trying to crash the server with an invalid pitch");
            ((CraftPlayer)this.getBukkitEntity()).kickPlayer("Infinite pitch (Hacking?)");
         }

         f1 = 0.0F;
      }

      this.f(f % 360.0F);
      this.e(f1 % 360.0F);
   }

   public final void a(Vec3D vec3d) {
      this.e(vec3d.a(), vec3d.b(), vec3d.c());
   }

   public void e(double d0, double d1, double d2) {
      this.p(d0, d1, d2);
      this.a(this.am());
   }

   protected AxisAlignedBB am() {
      return this.be.a(this.t);
   }

   protected void an() {
      this.e(this.t.c, this.t.d, this.t.e);
   }

   public void b(double d0, double d1) {
      float f = (float)d1 * 0.15F;
      float f1 = (float)d0 * 0.15F;
      this.e(this.dy() + f);
      this.f(this.dw() + f1);
      this.e(MathHelper.a(this.dy(), -90.0F, 90.0F));
      this.M += f;
      this.L += f1;
      this.M = MathHelper.a(this.M, -90.0F, 90.0F);
      if (this.s != null) {
         this.s.j(this);
      }
   }

   public void l() {
      this.ao();
   }

   public void postTick() {
      if (!(this instanceof EntityPlayer)) {
         this.bF();
      }
   }

   public void ao() {
      this.H.ac().a("entityBaseTick");
      this.bj = null;
      if (this.bL() && this.cV().dB()) {
         this.bz();
      }

      if (this.G > 0) {
         --this.G;
      }

      this.W = this.X;
      this.M = this.dy();
      this.L = this.dw();
      if (this instanceof EntityPlayer) {
         this.bF();
      }

      if (this.be()) {
         this.bf();
      }

      this.aA = this.az;
      this.az = false;
      this.aZ();
      this.o();
      this.aY();
      if (this.H.B) {
         this.av();
      } else if (this.aK > 0) {
         if (this.aS()) {
            this.g(this.aK - 4);
            if (this.aK < 0) {
               this.av();
            }
         } else {
            if (this.aK % 20 == 0 && !this.bg()) {
               this.a(this.dG().c(), 1.0F);
            }

            this.g(this.aK - 1);
         }

         if (this.ce() > 0) {
            this.j(0);
            this.H.a(null, 1009, this.u, 1);
         }
      }

      if (this.bg()) {
         this.at();
         this.aa *= 0.5F;
      } else {
         this.lastLavaContact = null;
      }

      this.ap();
      if (!this.H.B) {
         this.a_(this.aK > 0);
      }

      this.al = false;
      this.H.ac().c();
   }

   public void a_(boolean flag) {
      this.b(0, flag || this.bi);
   }

   public void ap() {
      if (this.dn() < (double)(this.H.v_() - 64)) {
         this.aw();
      }
   }

   public void aq() {
      this.aY = this.bG();
   }

   public boolean ar() {
      return this.aY > 0;
   }

   protected void H() {
      if (this.ar()) {
         --this.aY;
      }
   }

   public int as() {
      return 0;
   }

   public void at() {
      if (!this.aS()) {
         if (this instanceof EntityLiving && this.aK <= 0) {
            Block damager = this.lastLavaContact == null ? null : CraftBlock.at(this.H, this.lastLavaContact);
            org.bukkit.entity.Entity damagee = this.getBukkitEntity();
            EntityCombustEvent combustEvent = new EntityCombustByBlockEvent(damager, damagee, 15);
            this.H.getCraftServer().getPluginManager().callEvent(combustEvent);
            if (!combustEvent.isCancelled()) {
               this.setSecondsOnFire(combustEvent.getDuration(), false);
            }
         } else {
            this.setSecondsOnFire(15, false);
         }

         CraftEventFactory.blockDamage = this.lastLavaContact == null ? null : CraftBlock.at(this.H, this.lastLavaContact);
         if (this.a(this.dG().d(), 4.0F)) {
            this.a(SoundEffects.iK, 0.4F, 2.0F + this.af.i() * 0.4F);
         }

         CraftEventFactory.blockDamage = null;
      }
   }

   public void f(int i) {
      this.setSecondsOnFire(i, true);
   }

   public void setSecondsOnFire(int i, boolean callEvent) {
      if (callEvent) {
         EntityCombustEvent event = new EntityCombustEvent(this.getBukkitEntity(), i);
         this.H.getCraftServer().getPluginManager().callEvent(event);
         if (event.isCancelled()) {
            return;
         }

         i = event.getDuration();
      }

      int j = i * 20;
      if (this instanceof EntityLiving) {
         j = EnchantmentProtection.a((EntityLiving)this, j);
      }

      if (this.aK < j) {
         this.g(j);
      }
   }

   public void g(int i) {
      this.aK = i;
   }

   public int au() {
      return this.aK;
   }

   public void av() {
      this.g(0);
   }

   protected void aw() {
      this.ai();
   }

   public boolean g(double d0, double d1, double d2) {
      return this.b(this.cD().d(d0, d1, d2));
   }

   private boolean b(AxisAlignedBB axisalignedbb) {
      return this.H.a(this, axisalignedbb) && !this.H.d(axisalignedbb);
   }

   public void c(boolean flag) {
      this.N = flag;
   }

   public boolean ax() {
      return this.N;
   }

   public void a(EnumMoveType enummovetype, Vec3D vec3d) {
      SpigotTimings.entityMoveTimer.startTiming();
      if (this.ae) {
         this.e(this.dl() + vec3d.c, this.dn() + vec3d.d, this.dr() + vec3d.e);
      } else {
         this.aB = this.bK();
         if (enummovetype == EnumMoveType.c) {
            vec3d = this.c(vec3d);
            if (vec3d.equals(Vec3D.b)) {
               return;
            }
         }

         this.H.ac().a("move");
         if (this.T.g() > 1.0E-7) {
            vec3d = vec3d.h(this.T);
            this.T = Vec3D.b;
            this.f(Vec3D.b);
         }

         vec3d = this.a(vec3d, enummovetype);
         Vec3D vec3d1 = this.h(vec3d);
         double d0 = vec3d1.g();
         if (d0 > 1.0E-7) {
            if (this.aa != 0.0F && d0 >= 1.0) {
               MovingObjectPositionBlock movingobjectpositionblock = this.H
                  .a(new RayTrace(this.de(), this.de().e(vec3d1), RayTrace.BlockCollisionOption.d, RayTrace.FluidCollisionOption.d, this));
               if (movingobjectpositionblock.c() != MovingObjectPosition.EnumMovingObjectType.a) {
                  this.n();
               }
            }

            this.e(this.dl() + vec3d1.c, this.dn() + vec3d1.d, this.dr() + vec3d1.e);
         }

         this.H.ac().c();
         this.H.ac().a("rest");
         boolean flag = !MathHelper.b(vec3d.c, vec3d1.c);
         boolean flag1 = !MathHelper.b(vec3d.e, vec3d1.e);
         this.O = flag || flag1;
         this.P = vec3d.d != vec3d1.d;
         this.Q = this.P && vec3d.d < 0.0;
         if (this.O) {
            this.R = this.b(vec3d1);
         } else {
            this.R = false;
         }

         this.N = this.P && vec3d.d < 0.0;
         BlockPosition blockposition = this.aC();
         IBlockData iblockdata = this.H.a_(blockposition);
         this.a(vec3d1.d, this.N, iblockdata, blockposition);
         if (this.dB()) {
            this.H.ac().c();
         } else {
            if (this.O) {
               Vec3D vec3d2 = this.dj();
               this.o(flag ? 0.0 : vec3d2.c, vec3d2.d, flag1 ? 0.0 : vec3d2.e);
            }

            net.minecraft.world.level.block.Block block = iblockdata.b();
            if (vec3d.d != vec3d1.d) {
               block.a(this.H, this);
            }

            if (this.O && this.getBukkitEntity() instanceof Vehicle vehicle) {
               Block bl = this.H.getWorld().getBlockAt(MathHelper.a(this.dl()), MathHelper.a(this.dn()), MathHelper.a(this.dr()));
               if (vec3d.c > vec3d1.c) {
                  bl = bl.getRelative(BlockFace.EAST);
               } else if (vec3d.c < vec3d1.c) {
                  bl = bl.getRelative(BlockFace.WEST);
               } else if (vec3d.e > vec3d1.e) {
                  bl = bl.getRelative(BlockFace.SOUTH);
               } else if (vec3d.e < vec3d1.e) {
                  bl = bl.getRelative(BlockFace.NORTH);
               }

               if (!bl.getType().isAir()) {
                  VehicleBlockCollisionEvent event = new VehicleBlockCollisionEvent(vehicle, bl);
                  this.H.getCraftServer().getPluginManager().callEvent(event);
               }
            }

            if (this.N) {
               block.a(this.H, blockposition, iblockdata, this);
            }

            Entity.MovementEmission entity_movementemission = this.aQ();
            if (entity_movementemission.a() && !this.bL()) {
               double d1 = vec3d1.c;
               double d2 = vec3d1.d;
               double d3 = vec3d1.e;
               this.Z += (float)(vec3d1.f() * 0.6);
               boolean flag2 = iblockdata.a(TagsBlock.aM) || iblockdata.a(Blocks.qy);
               if (!flag2) {
                  d2 = 0.0;
               }

               this.X += (float)vec3d1.h() * 0.6F;
               this.Y += (float)Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3) * 0.6F;
               if (this.Y > this.aI && !iblockdata.h()) {
                  this.aI = this.aH();
                  if (!this.aT()) {
                     if (entity_movementemission.c()) {
                        this.b(iblockdata);
                        this.b(blockposition, iblockdata);
                     }

                     if (entity_movementemission.b() && (this.N || vec3d.d == 0.0 || this.az || flag2)) {
                        this.H.a(GameEvent.T, this.t, GameEvent.a.a(this, this.bd()));
                     }
                  } else {
                     if (entity_movementemission.c()) {
                        Object object = this.bM() && this.cK() != null ? this.cK() : this;
                        float f = object == this ? 0.35F : 0.4F;
                        Vec3D vec3d3 = ((Entity)object).dj();
                        float f1 = Math.min(1.0F, (float)Math.sqrt(vec3d3.c * vec3d3.c * 0.2F + vec3d3.d * vec3d3.d + vec3d3.e * vec3d3.e * 0.2F) * f);
                        this.i(f1);
                     }

                     if (entity_movementemission.b()) {
                        this.a(GameEvent.U);
                     }
                  }
               } else if (iblockdata.h()) {
                  this.aB();
               }
            }

            this.ay();
            float f2 = this.aF();
            this.f(this.dj().d((double)f2, 1.0, (double)f2));
            if (this.H.c(this.cD().h(1.0E-6)).noneMatch(iblockdata1 -> iblockdata1.a(TagsBlock.aH) || iblockdata1.a(Blocks.H))) {
               if (this.aK <= 0) {
                  this.g(-this.cY());
               }

               if (this.aB && (this.az || this.aV())) {
                  this.az();
               }
            }

            if (this.bK() && (this.az || this.aV())) {
               this.g(-this.cY());
            }

            this.H.ac().c();
         }
      }

      SpigotTimings.entityMoveTimer.stopTiming();
   }

   protected boolean b(Vec3D vec3d) {
      return false;
   }

   protected void ay() {
      try {
         this.aL();
      } catch (Throwable var4) {
         CrashReport crashreport = CrashReport.a(var4, "Checking entity block collision");
         CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Entity being checked for collision");
         this.a(crashreportsystemdetails);
         throw new ReportedException(crashreport);
      }
   }

   protected void az() {
      this.a(SoundEffects.iP, 0.7F, 1.6F + (this.af.i() - this.af.i()) * 0.4F);
   }

   public void aA() {
      if (!this.H.B && this.aB) {
         this.az();
      }

      this.av();
   }

   protected void aB() {
      if (this.aN()) {
         this.aM();
         if (this.aQ().b()) {
            this.a(GameEvent.z);
         }
      }
   }

   @Deprecated
   public BlockPosition aC() {
      return this.a(0.2F);
   }

   public BlockPosition aD() {
      return this.a(1.0E-5F);
   }

   private BlockPosition a(float f) {
      int i = MathHelper.a(this.t.c);
      int j = MathHelper.a(this.t.d - (double)f);
      int k = MathHelper.a(this.t.e);
      BlockPosition blockposition = new BlockPosition(i, j, k);
      if (this.H.a_(blockposition).h()) {
         BlockPosition blockposition1 = blockposition.d();
         IBlockData iblockdata = this.H.a_(blockposition1);
         if (iblockdata.a(TagsBlock.R) || iblockdata.a(TagsBlock.K) || iblockdata.b() instanceof BlockFenceGate) {
            return blockposition1;
         }
      }

      return blockposition;
   }

   protected float aE() {
      float f = this.H.a_(this.dg()).b().l();
      float f1 = this.H.a_(this.aG()).b().l();
      return (double)f == 1.0 ? f1 : f;
   }

   protected float aF() {
      IBlockData iblockdata = this.H.a_(this.dg());
      float f = iblockdata.b().j();
      return !iblockdata.a(Blocks.G) && !iblockdata.a(Blocks.mZ) ? ((double)f == 1.0 ? this.H.a_(this.aG()).b().j() : f) : f;
   }

   protected BlockPosition aG() {
      return BlockPosition.a(this.t.c, this.cD().b - 0.5000001, this.t.e);
   }

   protected Vec3D a(Vec3D vec3d, EnumMoveType enummovetype) {
      return vec3d;
   }

   protected Vec3D c(Vec3D vec3d) {
      if (vec3d.g() <= 1.0E-7) {
         return vec3d;
      } else {
         long i = this.H.U();
         if (i != this.bd) {
            Arrays.fill(this.bc, 0.0);
            this.bd = i;
         }

         if (vec3d.c != 0.0) {
            double d0 = this.a(EnumDirection.EnumAxis.a, vec3d.c);
            return Math.abs(d0) <= 1.0E-5F ? Vec3D.b : new Vec3D(d0, 0.0, 0.0);
         } else if (vec3d.d != 0.0) {
            double d0 = this.a(EnumDirection.EnumAxis.b, vec3d.d);
            return Math.abs(d0) <= 1.0E-5F ? Vec3D.b : new Vec3D(0.0, d0, 0.0);
         } else if (vec3d.e != 0.0) {
            double d0 = this.a(EnumDirection.EnumAxis.c, vec3d.e);
            return Math.abs(d0) <= 1.0E-5F ? Vec3D.b : new Vec3D(0.0, 0.0, d0);
         } else {
            return Vec3D.b;
         }
      }
   }

   private double a(EnumDirection.EnumAxis enumdirection_enumaxis, double d0) {
      int i = enumdirection_enumaxis.ordinal();
      double d1 = MathHelper.a(d0 + this.bc[i], -0.51, 0.51);
      d0 = d1 - this.bc[i];
      this.bc[i] = d1;
      return d0;
   }

   private Vec3D h(Vec3D vec3d) {
      AxisAlignedBB axisalignedbb = this.cD();
      List<VoxelShape> list = this.H.b(this, axisalignedbb.b(vec3d));
      Vec3D vec3d1 = vec3d.g() == 0.0 ? vec3d : a(this, vec3d, axisalignedbb, this.H, list);
      boolean flag = vec3d.c != vec3d1.c;
      boolean flag1 = vec3d.d != vec3d1.d;
      boolean flag2 = vec3d.e != vec3d1.e;
      boolean flag3 = this.N || flag1 && vec3d.d < 0.0;
      if (this.dA() > 0.0F && flag3 && (flag || flag2)) {
         Vec3D vec3d2 = a(this, new Vec3D(vec3d.c, (double)this.dA(), vec3d.e), axisalignedbb, this.H, list);
         Vec3D vec3d3 = a(this, new Vec3D(0.0, (double)this.dA(), 0.0), axisalignedbb.b(vec3d.c, 0.0, vec3d.e), this.H, list);
         if (vec3d3.d < (double)this.dA()) {
            Vec3D vec3d4 = a(this, new Vec3D(vec3d.c, 0.0, vec3d.e), axisalignedbb.c(vec3d3), this.H, list).e(vec3d3);
            if (vec3d4.i() > vec3d2.i()) {
               vec3d2 = vec3d4;
            }
         }

         if (vec3d2.i() > vec3d1.i()) {
            return vec3d2.e(a(this, new Vec3D(0.0, -vec3d2.d + vec3d.d, 0.0), axisalignedbb.c(vec3d2), this.H, list));
         }
      }

      return vec3d1;
   }

   public static Vec3D a(@Nullable Entity entity, Vec3D vec3d, AxisAlignedBB axisalignedbb, World world, List<VoxelShape> list) {
      Builder<VoxelShape> builder = ImmutableList.builderWithExpectedSize(list.size() + 1);
      if (!list.isEmpty()) {
         builder.addAll(list);
      }

      WorldBorder worldborder = world.p_();
      boolean flag = entity != null && worldborder.a(entity, axisalignedbb.b(vec3d));
      if (flag) {
         builder.add(worldborder.c());
      }

      builder.addAll(world.d(entity, axisalignedbb.b(vec3d)));
      return a(vec3d, axisalignedbb, builder.build());
   }

   private static Vec3D a(Vec3D vec3d, AxisAlignedBB axisalignedbb, List<VoxelShape> list) {
      if (list.isEmpty()) {
         return vec3d;
      } else {
         double d0 = vec3d.c;
         double d1 = vec3d.d;
         double d2 = vec3d.e;
         if (d1 != 0.0) {
            d1 = VoxelShapes.a(EnumDirection.EnumAxis.b, axisalignedbb, list, d1);
            if (d1 != 0.0) {
               axisalignedbb = axisalignedbb.d(0.0, d1, 0.0);
            }
         }

         boolean flag = Math.abs(d0) < Math.abs(d2);
         if (flag && d2 != 0.0) {
            d2 = VoxelShapes.a(EnumDirection.EnumAxis.c, axisalignedbb, list, d2);
            if (d2 != 0.0) {
               axisalignedbb = axisalignedbb.d(0.0, 0.0, d2);
            }
         }

         if (d0 != 0.0) {
            d0 = VoxelShapes.a(EnumDirection.EnumAxis.a, axisalignedbb, list, d0);
            if (!flag && d0 != 0.0) {
               axisalignedbb = axisalignedbb.d(d0, 0.0, 0.0);
            }
         }

         if (!flag && d2 != 0.0) {
            d2 = VoxelShapes.a(EnumDirection.EnumAxis.c, axisalignedbb, list, d2);
         }

         return new Vec3D(d0, d1, d2);
      }
   }

   protected float aH() {
      return (float)((int)this.Y + 1);
   }

   protected SoundEffect aI() {
      return SoundEffects.iT;
   }

   protected SoundEffect aJ() {
      return SoundEffects.iS;
   }

   protected SoundEffect aK() {
      return SoundEffects.iS;
   }

   public SoundEffect getSwimSound0() {
      return this.aI();
   }

   public SoundEffect getSwimSplashSound0() {
      return this.aJ();
   }

   public SoundEffect getSwimHighSpeedSplashSound0() {
      return this.aK();
   }

   protected void aL() {
      AxisAlignedBB axisalignedbb = this.cD();
      BlockPosition blockposition = BlockPosition.a(axisalignedbb.a + 1.0E-7, axisalignedbb.b + 1.0E-7, axisalignedbb.c + 1.0E-7);
      BlockPosition blockposition1 = BlockPosition.a(axisalignedbb.d - 1.0E-7, axisalignedbb.e - 1.0E-7, axisalignedbb.f - 1.0E-7);
      if (this.H.a(blockposition, blockposition1)) {
         BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();

         for(int i = blockposition.u(); i <= blockposition1.u(); ++i) {
            for(int j = blockposition.v(); j <= blockposition1.v(); ++j) {
               for(int k = blockposition.w(); k <= blockposition1.w(); ++k) {
                  blockposition_mutableblockposition.d(i, j, k);
                  IBlockData iblockdata = this.H.a_(blockposition_mutableblockposition);

                  try {
                     iblockdata.a(this.H, blockposition_mutableblockposition, this);
                     this.a(iblockdata);
                  } catch (Throwable var12) {
                     CrashReport crashreport = CrashReport.a(var12, "Colliding entity with block");
                     CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Block being collided with");
                     CrashReportSystemDetails.a(crashreportsystemdetails, this.H, blockposition_mutableblockposition, iblockdata);
                     throw new ReportedException(crashreport);
                  }
               }
            }
         }
      }
   }

   protected void a(IBlockData iblockdata) {
   }

   public void a(GameEvent gameevent, @Nullable Entity entity) {
      this.H.a(entity, gameevent, this.t);
   }

   public void a(GameEvent gameevent) {
      this.a(gameevent, this);
   }

   protected void b(BlockPosition blockposition, IBlockData iblockdata) {
      IBlockData iblockdata1 = this.H.a_(blockposition.c());
      boolean flag = iblockdata1.a(TagsBlock.bk);
      if (flag || !iblockdata.d().a()) {
         SoundEffectType soundeffecttype = flag ? iblockdata1.t() : iblockdata.t();
         this.a(soundeffecttype.d(), soundeffecttype.a() * 0.15F, soundeffecttype.b());
      }
   }

   private void b(IBlockData iblockdata) {
      if (iblockdata.a(TagsBlock.bj) && this.ag >= this.bh + 20) {
         this.bg *= (float)Math.pow(0.997, (double)(this.ag - this.bh));
         this.bg = Math.min(1.0F, this.bg + 0.07F);
         float f = 0.5F + this.bg * this.af.i() * 1.2F;
         float f1 = 0.1F + this.bg * 1.2F;
         this.a(SoundEffects.E, f1, f);
         this.bh = this.ag;
      }
   }

   protected void i(float f) {
      this.a(this.aI(), f, 1.0F + (this.af.i() - this.af.i()) * 0.4F);
   }

   protected void aM() {
   }

   protected boolean aN() {
      return false;
   }

   public void a(SoundEffect soundeffect, float f, float f1) {
      if (!this.aO()) {
         this.H.a(null, this.dl(), this.dn(), this.dr(), soundeffect, this.cX(), f, f1);
      }
   }

   public void a(SoundEffect soundeffect) {
      if (!this.aO()) {
         this.a(soundeffect, 1.0F, 1.0F);
      }
   }

   public boolean aO() {
      return this.am.a(aT);
   }

   public void d(boolean flag) {
      this.am.b(aT, flag);
   }

   public boolean aP() {
      return this.am.a(aU);
   }

   public void e(boolean flag) {
      this.am.b(aU, flag);
   }

   protected Entity.MovementEmission aQ() {
      return Entity.MovementEmission.d;
   }

   public boolean aR() {
      return false;
   }

   protected void a(double d0, boolean flag, IBlockData iblockdata, BlockPosition blockposition) {
      if (flag) {
         if (this.aa > 0.0F) {
            iblockdata.b().a(this.H, iblockdata, blockposition, this, this.aa);
            this.H.a(GameEvent.C, this.t, GameEvent.a.a(this, this.bd()));
         }

         this.n();
      } else if (d0 < 0.0) {
         this.aa -= (float)d0;
      }
   }

   public boolean aS() {
      return this.ae().d();
   }

   public boolean a(float f, float f1, DamageSource damagesource) {
      if (this.p.a(TagsEntity.l)) {
         return false;
      } else {
         if (this.bM()) {
            for(Entity entity : this.cM()) {
               entity.a(f, f1, damagesource);
            }
         }

         return false;
      }
   }

   public boolean aT() {
      return this.ah;
   }

   private boolean j() {
      BlockPosition blockposition = this.dg();
      return this.H.t(blockposition) || this.H.t(BlockPosition.a((double)blockposition.u(), this.cD().e, (double)blockposition.w()));
   }

   private boolean k() {
      return this.H.a_(this.dg()).a(Blocks.mZ);
   }

   public boolean aU() {
      return this.aT() || this.j();
   }

   public boolean aV() {
      return this.aT() || this.j() || this.k();
   }

   public boolean aW() {
      return this.aT() || this.k();
   }

   public boolean aX() {
      return this.aj && this.aT();
   }

   public void aY() {
      if (this.bV()) {
         this.h(this.bU() && this.aT() && !this.bL());
      } else {
         this.h(this.bU() && this.aX() && !this.bL() && this.H.b_(this.u).a(TagsFluid.a));
      }
   }

   protected boolean aZ() {
      this.ai.clear();
      this.ba();
      double d0 = this.H.q_().i() ? 0.007 : 0.0023333333333333335;
      boolean flag = this.a(TagsFluid.b, d0);
      return this.aT() || flag;
   }

   void ba() {
      Entity entity = this.cV();
      if (entity instanceof EntityBoat entityboat && !entityboat.aX()) {
         this.ah = false;
         return;
      }

      if (this.a(TagsFluid.a, 0.014)) {
         if (!this.ah && !this.al) {
            this.bb();
         }

         this.n();
         this.ah = true;
         this.av();
      } else {
         this.ah = false;
      }
   }

   private void o() {
      this.aj = this.a(TagsFluid.a);
      this.aL.clear();
      double d0 = this.dp() - 0.11111111F;
      Entity entity = this.cV();
      if (entity instanceof EntityBoat entityboat && !entityboat.aX() && entityboat.cD().e >= d0 && entityboat.cD().b <= d0) {
         return;
      }

      BlockPosition blockposition = BlockPosition.a(this.dl(), d0, this.dr());
      Fluid fluid = this.H.b_(blockposition);
      double d1 = (double)((float)blockposition.v() + fluid.a((IBlockAccess)this.H, blockposition));
      if (d1 > d0) {
         Stream stream = fluid.k();
         Set set = this.aL;
         stream.forEach(set::add);
      }
   }

   protected void bb() {
      Object object = this.bM() && this.cK() != null ? this.cK() : this;
      float f = object == this ? 0.2F : 0.9F;
      Vec3D vec3d = ((Entity)object).dj();
      float f1 = Math.min(1.0F, (float)Math.sqrt(vec3d.c * vec3d.c * 0.2F + vec3d.d * vec3d.d + vec3d.e * vec3d.e * 0.2F) * f);
      if (f1 < 0.25F) {
         this.a(this.aJ(), f1, 1.0F + (this.af.i() - this.af.i()) * 0.4F);
      } else {
         this.a(this.aK(), f1, 1.0F + (this.af.i() - this.af.i()) * 0.4F);
      }

      float f2 = (float)MathHelper.a(this.dn());

      for(int i = 0; (float)i < 1.0F + this.be.a * 20.0F; ++i) {
         double d0 = (this.af.j() * 2.0 - 1.0) * (double)this.be.a;
         double d1 = (this.af.j() * 2.0 - 1.0) * (double)this.be.a;
         this.H.a(Particles.e, this.dl() + d0, (double)(f2 + 1.0F), this.dr() + d1, vec3d.c, vec3d.d - this.af.j() * 0.2F, vec3d.e);
      }

      for(int var13 = 0; (float)var13 < 1.0F + this.be.a * 20.0F; ++var13) {
         double d0 = (this.af.j() * 2.0 - 1.0) * (double)this.be.a;
         double d1 = (this.af.j() * 2.0 - 1.0) * (double)this.be.a;
         this.H.a(Particles.ai, this.dl() + d0, (double)(f2 + 1.0F), this.dr() + d1, vec3d.c, vec3d.d, vec3d.e);
      }

      this.a(GameEvent.S);
   }

   @Deprecated
   protected IBlockData bc() {
      return this.H.a_(this.aC());
   }

   public IBlockData bd() {
      return this.H.a_(this.aD());
   }

   public boolean be() {
      return this.bU() && !this.aT() && !this.F_() && !this.bT() && !this.bg() && this.bq();
   }

   protected void bf() {
      int i = MathHelper.a(this.dl());
      int j = MathHelper.a(this.dn() - 0.2F);
      int k = MathHelper.a(this.dr());
      BlockPosition blockposition = new BlockPosition(i, j, k);
      IBlockData iblockdata = this.H.a_(blockposition);
      if (iblockdata.i() != EnumRenderType.a) {
         Vec3D vec3d = this.dj();
         this.H
            .a(
               new ParticleParamBlock(Particles.c, iblockdata),
               this.dl() + (this.af.j() - 0.5) * (double)this.be.a,
               this.dn() + 0.1,
               this.dr() + (this.af.j() - 0.5) * (double)this.be.a,
               vec3d.c * -4.0,
               1.5,
               vec3d.e * -4.0
            );
      }
   }

   public boolean a(TagKey<FluidType> tagkey) {
      return this.aL.contains(tagkey);
   }

   public boolean bg() {
      return !this.al && this.ai.getDouble(TagsFluid.b) > 0.0;
   }

   public void a(float f, Vec3D vec3d) {
      Vec3D vec3d1 = a(vec3d, f, this.dw());
      this.f(this.dj().e(vec3d1));
   }

   private static Vec3D a(Vec3D vec3d, float f, float f1) {
      double d0 = vec3d.g();
      if (d0 < 1.0E-7) {
         return Vec3D.b;
      } else {
         Vec3D vec3d1 = (d0 > 1.0 ? vec3d.d() : vec3d).a((double)f);
         float f2 = MathHelper.a(f1 * (float) (Math.PI / 180.0));
         float f3 = MathHelper.b(f1 * (float) (Math.PI / 180.0));
         return new Vec3D(vec3d1.c * (double)f3 - vec3d1.e * (double)f2, vec3d1.d, vec3d1.e * (double)f3 + vec3d1.c * (double)f2);
      }
   }

   @Deprecated
   public float bh() {
      return this.H.f(this.dk(), this.dq()) ? this.H.z(BlockPosition.a(this.dl(), this.dp(), this.dr())) : 0.0F;
   }

   public void a(double d0, double d1, double d2, float f, float f1) {
      this.h(d0, d1, d2);
      this.f(f % 360.0F);
      this.e(MathHelper.a(f1, -90.0F, 90.0F) % 360.0F);
      this.L = this.dw();
      this.M = this.dy();
   }

   public void h(double d0, double d1, double d2) {
      double d3 = MathHelper.a(d0, -3.0E7, 3.0E7);
      double d4 = MathHelper.a(d2, -3.0E7, 3.0E7);
      this.I = d3;
      this.J = d1;
      this.K = d4;
      this.e(d3, d1, d4);
      if (this.valid) {
         this.H.d((int)Math.floor(this.dl()) >> 4, (int)Math.floor(this.dr()) >> 4);
      }
   }

   public void d(Vec3D vec3d) {
      this.d(vec3d.c, vec3d.d, vec3d.e);
   }

   public void d(double d0, double d1, double d2) {
      this.b(d0, d1, d2, this.dw(), this.dy());
   }

   public void a(BlockPosition blockposition, float f, float f1) {
      this.b((double)blockposition.u() + 0.5, (double)blockposition.v(), (double)blockposition.w() + 0.5, f, f1);
   }

   public void b(double d0, double d1, double d2, float f, float f1) {
      this.p(d0, d1, d2);
      this.f(f);
      this.e(f1);
      this.bi();
      this.an();
   }

   public final void bi() {
      double d0 = this.dl();
      double d1 = this.dn();
      double d2 = this.dr();
      this.I = d0;
      this.J = d1;
      this.K = d2;
      this.ab = d0;
      this.ac = d1;
      this.ad = d2;
      this.L = this.dw();
      this.M = this.dy();
   }

   public float e(Entity entity) {
      float f = (float)(this.dl() - entity.dl());
      float f1 = (float)(this.dn() - entity.dn());
      float f2 = (float)(this.dr() - entity.dr());
      return MathHelper.c(f * f + f1 * f1 + f2 * f2);
   }

   public double i(double d0, double d1, double d2) {
      double d3 = this.dl() - d0;
      double d4 = this.dn() - d1;
      double d5 = this.dr() - d2;
      return d3 * d3 + d4 * d4 + d5 * d5;
   }

   public double f(Entity entity) {
      return this.e(entity.de());
   }

   public double e(Vec3D vec3d) {
      double d0 = this.dl() - vec3d.c;
      double d1 = this.dn() - vec3d.d;
      double d2 = this.dr() - vec3d.e;
      return d0 * d0 + d1 * d1 + d2 * d2;
   }

   public void b_(EntityHuman entityhuman) {
   }

   public void g(Entity entity) {
      if (!this.v(entity) && !entity.ae && !this.ae) {
         double d0 = entity.dl() - this.dl();
         double d1 = entity.dr() - this.dr();
         double d2 = MathHelper.a(d0, d1);
         if (d2 >= 0.01F) {
            d2 = Math.sqrt(d2);
            d0 /= d2;
            d1 /= d2;
            double d3 = 1.0 / d2;
            if (d3 > 1.0) {
               d3 = 1.0;
            }

            d0 *= d3;
            d1 *= d3;
            d0 *= 0.05F;
            d1 *= 0.05F;
            if (!this.bM() && this.bn()) {
               this.j(-d0, 0.0, -d1);
            }

            if (!entity.bM() && entity.bn()) {
               entity.j(d0, 0.0, d1);
            }
         }
      }
   }

   public void j(double d0, double d1, double d2) {
      this.f(this.dj().b(d0, d1, d2));
      this.at = true;
   }

   protected void bj() {
      this.S = true;
   }

   public boolean a(DamageSource damagesource, float f) {
      if (this.b(damagesource)) {
         return false;
      } else {
         this.bj();
         return false;
      }
   }

   public final Vec3D j(float f) {
      return this.b(this.k(f), this.l(f));
   }

   public float k(float f) {
      return f == 1.0F ? this.dy() : MathHelper.i(f, this.M, this.dy());
   }

   public float l(float f) {
      return f == 1.0F ? this.dw() : MathHelper.i(f, this.L, this.dw());
   }

   protected final Vec3D b(float f, float f1) {
      float f2 = f * (float) (Math.PI / 180.0);
      float f3 = -f1 * (float) (Math.PI / 180.0);
      float f4 = MathHelper.b(f3);
      float f5 = MathHelper.a(f3);
      float f6 = MathHelper.b(f2);
      float f7 = MathHelper.a(f2);
      return new Vec3D((double)(f5 * f6), (double)(-f7), (double)(f4 * f6));
   }

   public final Vec3D m(float f) {
      return this.c(this.k(f), this.l(f));
   }

   protected final Vec3D c(float f, float f1) {
      return this.b(f - 90.0F, f1);
   }

   public final Vec3D bk() {
      return new Vec3D(this.dl(), this.dp(), this.dr());
   }

   public final Vec3D n(float f) {
      double d0 = MathHelper.d((double)f, this.I, this.dl());
      double d1 = MathHelper.d((double)f, this.J, this.dn()) + (double)this.cE();
      double d2 = MathHelper.d((double)f, this.K, this.dr());
      return new Vec3D(d0, d1, d2);
   }

   public Vec3D o(float f) {
      return this.n(f);
   }

   public final Vec3D p(float f) {
      double d0 = MathHelper.d((double)f, this.I, this.dl());
      double d1 = MathHelper.d((double)f, this.J, this.dn());
      double d2 = MathHelper.d((double)f, this.K, this.dr());
      return new Vec3D(d0, d1, d2);
   }

   public MovingObjectPosition a(double d0, float f, boolean flag) {
      Vec3D vec3d = this.n(f);
      Vec3D vec3d1 = this.j(f);
      Vec3D vec3d2 = vec3d.b(vec3d1.c * d0, vec3d1.d * d0, vec3d1.e * d0);
      return this.H
         .a(new RayTrace(vec3d, vec3d2, RayTrace.BlockCollisionOption.b, flag ? RayTrace.FluidCollisionOption.c : RayTrace.FluidCollisionOption.a, this));
   }

   public boolean bl() {
      return this.bq() && this.bm();
   }

   public boolean bm() {
      return false;
   }

   public boolean bn() {
      return false;
   }

   public boolean canCollideWithBukkit(Entity entity) {
      return this.bn();
   }

   public void a(Entity entity, int i, DamageSource damagesource) {
      if (entity instanceof EntityPlayer) {
         CriterionTriggers.c.a((EntityPlayer)entity, this, damagesource);
      }
   }

   public boolean k(double d0, double d1, double d2) {
      double d3 = this.dl() - d0;
      double d4 = this.dn() - d1;
      double d5 = this.dr() - d2;
      double d6 = d3 * d3 + d4 * d4 + d5 * d5;
      return this.a(d6);
   }

   public boolean a(double d0) {
      double d1 = this.cD().a();
      if (Double.isNaN(d1)) {
         d1 = 1.0;
      }

      d1 *= 64.0 * o;
      return d0 < d1 * d1;
   }

   public boolean d(NBTTagCompound nbttagcompound) {
      if (this.aH != null && !this.aH.b()) {
         return false;
      } else {
         String s = this.bp();
         if (this.persist && s != null) {
            nbttagcompound.a("id", s);
            this.f(nbttagcompound);
            return true;
         } else {
            return false;
         }
      }
   }

   public boolean e(NBTTagCompound nbttagcompound) {
      return this.bL() ? false : this.d(nbttagcompound);
   }

   public NBTTagCompound f(NBTTagCompound nbttagcompound) {
      try {
         if (this.s != null) {
            nbttagcompound.a("Pos", this.a(this.s.dl(), this.dn(), this.s.dr()));
         } else {
            nbttagcompound.a("Pos", this.a(this.dl(), this.dn(), this.dr()));
         }

         Vec3D vec3d = this.dj();
         nbttagcompound.a("Motion", this.a(vec3d.c, vec3d.d, vec3d.e));
         if (Float.isNaN(this.aE)) {
            this.aE = 0.0F;
         }

         if (Float.isNaN(this.aF)) {
            this.aF = 0.0F;
         }

         nbttagcompound.a("Rotation", this.a(this.dw(), this.dy()));
         nbttagcompound.a("FallDistance", this.aa);
         nbttagcompound.a("Fire", (short)this.aK);
         nbttagcompound.a("Air", (short)this.cd());
         nbttagcompound.a("OnGround", this.N);
         nbttagcompound.a("Invulnerable", this.aZ);
         nbttagcompound.a("PortalCooldown", this.aY);
         nbttagcompound.a("UUID", this.cs());
         nbttagcompound.a("WorldUUIDLeast", ((WorldServer)this.H).getWorld().getUID().getLeastSignificantBits());
         nbttagcompound.a("WorldUUIDMost", ((WorldServer)this.H).getWorld().getUID().getMostSignificantBits());
         nbttagcompound.a("Bukkit.updateLevel", 2);
         if (!this.persist) {
            nbttagcompound.a("Bukkit.persist", this.persist);
         }

         if (!this.visibleByDefault) {
            nbttagcompound.a("Bukkit.visibleByDefault", this.visibleByDefault);
         }

         if (this.persistentInvisibility) {
            nbttagcompound.a("Bukkit.invisible", this.persistentInvisibility);
         }

         if (this.maxAirTicks != this.getDefaultMaxAirSupply()) {
            nbttagcompound.a("Bukkit.MaxAirSupply", this.cc());
         }

         nbttagcompound.a("Spigot.ticksLived", this.ag);
         IChatBaseComponent ichatbasecomponent = this.ab();
         if (ichatbasecomponent != null) {
            nbttagcompound.a("CustomName", IChatBaseComponent.ChatSerializer.a(ichatbasecomponent));
         }

         if (this.cx()) {
            nbttagcompound.a("CustomNameVisible", this.cx());
         }

         if (this.aO()) {
            nbttagcompound.a("Silent", this.aO());
         }

         if (this.aP()) {
            nbttagcompound.a("NoGravity", this.aP());
         }

         if (this.ba) {
            nbttagcompound.a("Glowing", true);
         }

         int i = this.ce();
         if (i > 0) {
            nbttagcompound.a("TicksFrozen", this.ce());
         }

         if (this.bi) {
            nbttagcompound.a("HasVisualFire", this.bi);
         }

         if (!this.bb.isEmpty()) {
            NBTTagList nbttaglist = new NBTTagList();

            for(String s : this.bb) {
               nbttaglist.add(NBTTagString.a(s));
            }

            nbttagcompound.a("Tags", nbttaglist);
         }

         this.b(nbttagcompound);
         if (this.bM()) {
            NBTTagList nbttaglist = new NBTTagList();

            for(Entity entity : this.cM()) {
               NBTTagCompound nbttagcompound1 = new NBTTagCompound();
               if (entity.d(nbttagcompound1)) {
                  nbttaglist.add(nbttagcompound1);
               }
            }

            if (!nbttaglist.isEmpty()) {
               nbttagcompound.a("Passengers", nbttaglist);
            }
         }

         if (this.bukkitEntity != null) {
            this.bukkitEntity.storeBukkitValues(nbttagcompound);
         }

         return nbttagcompound;
      } catch (Throwable var9) {
         CrashReport crashreport = CrashReport.a(var9, "Saving entity NBT");
         CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Entity being saved");
         this.a(crashreportsystemdetails);
         throw new ReportedException(crashreport);
      }
   }

   public void g(NBTTagCompound nbttagcompound) {
      try {
         NBTTagList nbttaglist = nbttagcompound.c("Pos", 6);
         NBTTagList nbttaglist1 = nbttagcompound.c("Motion", 6);
         NBTTagList nbttaglist2 = nbttagcompound.c("Rotation", 5);
         double d0 = nbttaglist1.h(0);
         double d1 = nbttaglist1.h(1);
         double d2 = nbttaglist1.h(2);
         this.o(Math.abs(d0) > 10.0 ? 0.0 : d0, Math.abs(d1) > 10.0 ? 0.0 : d1, Math.abs(d2) > 10.0 ? 0.0 : d2);
         double d3 = 3.0000512E7;
         this.p(
            MathHelper.a(nbttaglist.h(0), -3.0000512E7, 3.0000512E7),
            MathHelper.a(nbttaglist.h(1), -2.0E7, 2.0E7),
            MathHelper.a(nbttaglist.h(2), -3.0000512E7, 3.0000512E7)
         );
         this.f(nbttaglist2.i(0));
         this.e(nbttaglist2.i(1));
         this.bi();
         this.r(this.dw());
         this.s(this.dw());
         this.aa = nbttagcompound.j("FallDistance");
         this.aK = nbttagcompound.g("Fire");
         if (nbttagcompound.e("Air")) {
            this.i(nbttagcompound.g("Air"));
         }

         this.N = nbttagcompound.q("OnGround");
         this.aZ = nbttagcompound.q("Invulnerable");
         this.aY = nbttagcompound.h("PortalCooldown");
         if (nbttagcompound.b("UUID")) {
            this.ax = nbttagcompound.a("UUID");
            this.ay = this.ax.toString();
         }

         if (!Double.isFinite(this.dl()) || !Double.isFinite(this.dn()) || !Double.isFinite(this.dr())) {
            throw new IllegalStateException("Entity has invalid position");
         } else if (Double.isFinite((double)this.dw()) && Double.isFinite((double)this.dy())) {
            this.an();
            this.a(this.dw(), this.dy());
            if (nbttagcompound.b("CustomName", 8)) {
               String s = nbttagcompound.l("CustomName");

               try {
                  this.b(IChatBaseComponent.ChatSerializer.a(s));
               } catch (Exception var17) {
                  c.warn("Failed to parse entity custom name {}", s, var17);
               }
            }

            this.n(nbttagcompound.q("CustomNameVisible"));
            this.d(nbttagcompound.q("Silent"));
            this.e(nbttagcompound.q("NoGravity"));
            this.i(nbttagcompound.q("Glowing"));
            this.j(nbttagcompound.h("TicksFrozen"));
            this.bi = nbttagcompound.q("HasVisualFire");
            if (nbttagcompound.b("Tags", 9)) {
               this.bb.clear();
               NBTTagList nbttaglist3 = nbttagcompound.c("Tags", 8);
               int i = Math.min(nbttaglist3.size(), 1024);

               for(int j = 0; j < i; ++j) {
                  this.bb.add(nbttaglist3.j(j));
               }
            }

            this.a(nbttagcompound);
            if (this.bo()) {
               this.an();
            }

            if (this instanceof EntityLiving) {
               this.ag = nbttagcompound.h("Spigot.ticksLived");
            }

            this.persist = !nbttagcompound.e("Bukkit.persist") || nbttagcompound.q("Bukkit.persist");
            this.visibleByDefault = !nbttagcompound.e("Bukkit.visibleByDefault") || nbttagcompound.q("Bukkit.visibleByDefault");
            if (nbttagcompound.e("Bukkit.MaxAirSupply")) {
               this.maxAirTicks = nbttagcompound.h("Bukkit.MaxAirSupply");
            }

            if (this instanceof EntityPlayer) {
               Server server = Bukkit.getServer();
               org.bukkit.World bworld = null;
               String worldName = nbttagcompound.l("world");
               Object var25;
               if (nbttagcompound.e("WorldUUIDMost") && nbttagcompound.e("WorldUUIDLeast")) {
                  UUID uid = new UUID(nbttagcompound.i("WorldUUIDMost"), nbttagcompound.i("WorldUUIDLeast"));
                  var25 = server.getWorld(uid);
               } else {
                  var25 = server.getWorld(worldName);
               }

               if (var25 == null) {
                  var25 = ((CraftServer)server).getServer().a(World.h).getWorld();
               }

               ((EntityPlayer)this).c(var25 == null ? null : ((CraftWorld)var25).getHandle());
            }

            this.getBukkitEntity().readBukkitValues(nbttagcompound);
            if (nbttagcompound.e("Bukkit.invisible")) {
               boolean bukkitInvisible = nbttagcompound.q("Bukkit.invisible");
               this.j(bukkitInvisible);
               this.persistentInvisibility = bukkitInvisible;
            }
         } else {
            throw new IllegalStateException("Entity has invalid rotation");
         }
      } catch (Throwable var18) {
         CrashReport crashreport = CrashReport.a(var18, "Loading entity NBT");
         CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Entity being loaded");
         this.a(crashreportsystemdetails);
         throw new ReportedException(crashreport);
      }
   }

   protected boolean bo() {
      return true;
   }

   @Nullable
   public final String bp() {
      EntityTypes<?> entitytypes = this.ae();
      MinecraftKey minecraftkey = EntityTypes.a(entitytypes);
      return entitytypes.b() && minecraftkey != null ? minecraftkey.toString() : null;
   }

   protected abstract void a(NBTTagCompound var1);

   protected abstract void b(NBTTagCompound var1);

   protected NBTTagList a(double... adouble) {
      NBTTagList nbttaglist = new NBTTagList();

      for(double d0 : adouble) {
         nbttaglist.add(NBTTagDouble.a(d0));
      }

      return nbttaglist;
   }

   protected NBTTagList a(float... afloat) {
      NBTTagList nbttaglist = new NBTTagList();

      for(float f : afloat) {
         nbttaglist.add(NBTTagFloat.a(f));
      }

      return nbttaglist;
   }

   @Nullable
   public EntityItem a(IMaterial imaterial) {
      return this.a(imaterial, 0);
   }

   @Nullable
   public EntityItem a(IMaterial imaterial, int i) {
      return this.a(new ItemStack(imaterial), (float)i);
   }

   @Nullable
   public EntityItem b(ItemStack itemstack) {
      return this.a(itemstack, 0.0F);
   }

   @Nullable
   public EntityItem a(ItemStack itemstack, float f) {
      if (itemstack.b()) {
         return null;
      } else if (this.H.B) {
         return null;
      } else if (this instanceof EntityLiving && !((EntityLiving)this).forceDrops) {
         ((EntityLiving)this).drops.add(CraftItemStack.asBukkitCopy(itemstack));
         return null;
      } else {
         EntityItem entityitem = new EntityItem(this.H, this.dl(), this.dn() + (double)f, this.dr(), itemstack);
         entityitem.k();
         EntityDropItemEvent event = new EntityDropItemEvent(this.getBukkitEntity(), (Item)entityitem.getBukkitEntity());
         Bukkit.getPluginManager().callEvent(event);
         if (event.isCancelled()) {
            return null;
         } else {
            this.H.b(entityitem);
            return entityitem;
         }
      }
   }

   public boolean bq() {
      return !this.dB();
   }

   public boolean br() {
      if (this.ae) {
         return false;
      } else {
         float f = this.be.a * 0.8F;
         AxisAlignedBB axisalignedbb = AxisAlignedBB.a(this.bk(), (double)f, 1.0E-6, (double)f);
         return BlockPosition.a(axisalignedbb)
            .anyMatch(
               blockposition -> {
                  IBlockData iblockdata = this.H.a_(blockposition);
                  return !iblockdata.h()
                     && iblockdata.o(this.H, blockposition)
                     && VoxelShapes.c(
                        iblockdata.k(this.H, blockposition).a((double)blockposition.u(), (double)blockposition.v(), (double)blockposition.w()),
                        VoxelShapes.a(axisalignedbb),
                        OperatorBoolean.i
                     );
               }
            );
      }
   }

   public EnumInteractionResult a(EntityHuman entityhuman, EnumHand enumhand) {
      return EnumInteractionResult.d;
   }

   public boolean h(Entity entity) {
      return entity.bs() && !this.v(entity);
   }

   public boolean bs() {
      return false;
   }

   public void bt() {
      this.f(Vec3D.b);
      this.l();
      if (this.bL()) {
         this.cV().i(this);
      }
   }

   public void i(Entity entity) {
      this.a(entity, Entity::e);
   }

   private void a(Entity entity, Entity.MoveFunction entity_movefunction) {
      if (this.u(entity)) {
         double d0 = this.dn() + this.bv() + entity.bu();
         entity_movefunction.accept(entity, this.dl(), d0, this.dr());
      }
   }

   public void j(Entity entity) {
   }

   public double bu() {
      return 0.0;
   }

   public double bv() {
      return (double)this.be.b * 0.75;
   }

   public boolean k(Entity entity) {
      return this.a(entity, false);
   }

   public boolean bw() {
      return this instanceof EntityLiving;
   }

   public boolean a(Entity entity, boolean flag) {
      if (entity == this.s) {
         return false;
      } else if (!entity.bA()) {
         return false;
      } else {
         for(Entity entity1 = entity; entity1.s != null; entity1 = entity1.s) {
            if (entity1.s == this) {
               return false;
            }
         }

         if (flag || this.l(entity) && entity.o(this)) {
            if (this.bL()) {
               this.bz();
            }

            this.b(EntityPose.a);
            this.s = entity;
            if (!this.s.addPassenger(this)) {
               this.s = null;
            }

            entity.p().filter(entity2 -> entity2 instanceof EntityPlayer).forEach(entity2 -> CriterionTriggers.R.a((EntityPlayer)entity2));
            return true;
         } else {
            return false;
         }
      }
   }

   protected boolean l(Entity entity) {
      return !this.bO() && this.G <= 0;
   }

   protected boolean d(EntityPose entitypose) {
      return this.H.a(this, this.e(entitypose).h(1.0E-7));
   }

   public void bx() {
      for(int i = this.r.size() - 1; i >= 0; --i) {
         ((Entity)this.r.get(i)).bz();
      }
   }

   public void by() {
      if (this.s != null) {
         Entity entity = this.s;
         this.s = null;
         if (!entity.removePassenger(this)) {
            this.s = entity;
         }
      }
   }

   public void bz() {
      this.by();
   }

   protected boolean addPassenger(Entity entity) {
      if (entity.cV() != this) {
         throw new IllegalStateException("Use x.startRiding(y), not y.addPassenger(x)");
      } else {
         Preconditions.checkState(!entity.r.contains(this), "Circular entity riding! %s %s", this, entity);
         CraftEntity craft = (CraftEntity)entity.getBukkitEntity().getVehicle();
         Entity orig = craft == null ? null : craft.getHandle();
         if (this.getBukkitEntity() instanceof Vehicle && entity.getBukkitEntity() instanceof LivingEntity) {
            VehicleEnterEvent event = new VehicleEnterEvent((Vehicle)this.getBukkitEntity(), entity.getBukkitEntity());
            if (this.valid) {
               Bukkit.getPluginManager().callEvent(event);
            }

            CraftEntity craftn = (CraftEntity)entity.getBukkitEntity().getVehicle();
            Entity n = craftn == null ? null : craftn.getHandle();
            if (event.isCancelled() || n != orig) {
               return false;
            }
         }

         EntityMountEvent event = new EntityMountEvent(entity.getBukkitEntity(), this.getBukkitEntity());
         if (this.valid) {
            Bukkit.getPluginManager().callEvent(event);
         }

         if (event.isCancelled()) {
            return false;
         } else {
            if (this.r.isEmpty()) {
               this.r = ImmutableList.of(entity);
            } else {
               List<Entity> list = Lists.newArrayList(this.r);
               if (!this.H.B && entity instanceof EntityHuman && !(this.cN() instanceof EntityHuman)) {
                  list.add(0, entity);
               } else {
                  list.add(entity);
               }

               this.r = ImmutableList.copyOf(list);
            }

            this.a(GameEvent.t, entity);
            return true;
         }
      }
   }

   protected boolean removePassenger(Entity entity) {
      if (entity.cV() == this) {
         throw new IllegalStateException("Use x.stopRiding(y), not y.removePassenger(x)");
      } else {
         CraftEntity craft = (CraftEntity)entity.getBukkitEntity().getVehicle();
         Entity orig = craft == null ? null : craft.getHandle();
         if (this.getBukkitEntity() instanceof Vehicle && entity.getBukkitEntity() instanceof LivingEntity) {
            VehicleExitEvent event = new VehicleExitEvent((Vehicle)this.getBukkitEntity(), (LivingEntity)entity.getBukkitEntity());
            if (this.valid) {
               Bukkit.getPluginManager().callEvent(event);
            }

            CraftEntity craftn = (CraftEntity)entity.getBukkitEntity().getVehicle();
            Entity n = craftn == null ? null : craftn.getHandle();
            if (event.isCancelled() || n != orig) {
               return false;
            }
         }

         EntityDismountEvent event = new EntityDismountEvent(entity.getBukkitEntity(), this.getBukkitEntity());
         if (this.valid) {
            Bukkit.getPluginManager().callEvent(event);
         }

         if (event.isCancelled()) {
            return false;
         } else {
            if (this.r.size() == 1 && this.r.get(0) == entity) {
               this.r = ImmutableList.of();
            } else {
               this.r = this.r.stream().filter(entity1 -> entity1 != entity).collect(ImmutableList.toImmutableList());
            }

            entity.G = 60;
            this.a(GameEvent.r, entity);
            return true;
         }
      }
   }

   protected boolean o(Entity entity) {
      return this.r.isEmpty();
   }

   protected boolean bA() {
      return true;
   }

   public void a(double d0, double d1, double d2, float f, float f1, int i, boolean flag) {
      this.e(d0, d1, d2);
      this.a(f, f1);
   }

   public void a(float f, int i) {
      this.r(f);
   }

   public float bB() {
      return 0.0F;
   }

   public Vec3D bC() {
      return this.b(this.dy(), this.dw());
   }

   public Vec3D a(net.minecraft.world.item.Item item) {
      if (!(this instanceof EntityHuman)) {
         return Vec3D.b;
      } else {
         EntityHuman entityhuman = (EntityHuman)this;
         boolean flag = entityhuman.eL().a(item) && !entityhuman.eK().a(item);
         EnumMainHand enummainhand = flag ? entityhuman.fd().d() : entityhuman.fd();
         return this.b(0.0F, this.dw() + (float)(enummainhand == EnumMainHand.b ? 80 : -80)).a(0.5);
      }
   }

   public Vec2F bD() {
      return new Vec2F(this.dy(), this.dw());
   }

   public Vec3D bE() {
      return Vec3D.a(this.bD());
   }

   public void d(BlockPosition blockposition) {
      if (this.ar()) {
         this.aq();
      } else {
         if (!this.H.B && !blockposition.equals(this.aw)) {
            this.aw = blockposition.i();
         }

         this.au = true;
      }
   }

   protected void bF() {
      if (this.H instanceof WorldServer) {
         int i = this.as();
         WorldServer worldserver = (WorldServer)this.H;
         if (this.au) {
            MinecraftServer minecraftserver = worldserver.n();
            ResourceKey<World> resourcekey = this.H.getTypeKey() == WorldDimension.c ? World.h : World.i;
            WorldServer worldserver1 = minecraftserver.a(resourcekey);
            if (!this.bL() && this.av++ >= i) {
               this.H.ac().a("portal");
               this.av = i;
               this.aq();
               if (this instanceof EntityPlayer) {
                  ((EntityPlayer)this).changeDimension(worldserver1, TeleportCause.NETHER_PORTAL);
               } else {
                  this.b(worldserver1);
               }

               this.H.ac().c();
            }

            this.au = false;
         } else {
            if (this.av > 0) {
               this.av -= 4;
            }

            if (this.av < 0) {
               this.av = 0;
            }
         }

         this.H();
      }
   }

   public int bG() {
      return 300;
   }

   public void l(double d0, double d1, double d2) {
      this.o(d0, d1, d2);
   }

   public void c(DamageSource damagesource) {
   }

   public void b(byte b0) {
      switch(b0) {
         case 53:
            BlockHoney.a(this);
      }
   }

   public void q(float f) {
   }

   public Iterable<ItemStack> bH() {
      return e;
   }

   public Iterable<ItemStack> bI() {
      return e;
   }

   public Iterable<ItemStack> bJ() {
      return Iterables.concat(this.bH(), this.bI());
   }

   public void a(EnumItemSlot enumitemslot, ItemStack itemstack) {
   }

   public boolean bK() {
      boolean flag = this.H != null && this.H.B;
      return !this.aS() && (this.aK > 0 || flag && this.h(0));
   }

   public boolean bL() {
      return this.cV() != null;
   }

   public boolean bM() {
      return !this.r.isEmpty();
   }

   public boolean bN() {
      return this.ae().a(TagsEntity.m);
   }

   public void f(boolean flag) {
      this.b(1, flag);
   }

   public boolean bO() {
      return this.h(1);
   }

   public boolean bP() {
      return this.bO();
   }

   public boolean bQ() {
      return this.bO();
   }

   public boolean bR() {
      return this.bO();
   }

   public boolean bS() {
      return this.bO();
   }

   public boolean bT() {
      return this.c(EntityPose.f);
   }

   public boolean bU() {
      return this.h(3);
   }

   public void g(boolean flag) {
      this.b(3, flag);
   }

   public boolean bV() {
      return this.h(4);
   }

   public boolean bW() {
      return this.c(EntityPose.d);
   }

   public boolean bX() {
      return this.bW() && !this.aT();
   }

   public void h(boolean flag) {
      if (!this.valid
         || this.bV() == flag
         || !(this instanceof EntityLiving)
         || !CraftEventFactory.callToggleSwimEvent((EntityLiving)this, flag).isCancelled()) {
         this.b(4, flag);
      }
   }

   public final boolean bY() {
      return this.ba;
   }

   public final void i(boolean flag) {
      this.ba = flag;
      this.b(6, this.bZ());
   }

   public boolean bZ() {
      return this.H.k_() ? this.h(6) : this.ba;
   }

   public boolean ca() {
      return this.h(5);
   }

   public boolean d(EntityHuman entityhuman) {
      if (entityhuman.F_()) {
         return false;
      } else {
         ScoreboardTeamBase scoreboardteambase = this.cb();
         return scoreboardteambase != null && entityhuman != null && entityhuman.cb() == scoreboardteambase && scoreboardteambase.i() ? false : this.ca();
      }
   }

   public void a(BiConsumer<DynamicGameEventListener<?>, WorldServer> biconsumer) {
   }

   @Nullable
   public ScoreboardTeamBase cb() {
      return this.H.H().i(this.cu());
   }

   public boolean p(Entity entity) {
      return this.a(entity.cb());
   }

   public boolean a(ScoreboardTeamBase scoreboardteambase) {
      return this.cb() != null ? this.cb().a(scoreboardteambase) : false;
   }

   public void j(boolean flag) {
      if (!this.persistentInvisibility) {
         this.b(5, flag);
      }
   }

   public boolean h(int i) {
      return (this.am.a(an) & 1 << i) != 0;
   }

   public void b(int i, boolean flag) {
      byte b0 = this.am.a(an);
      if (flag) {
         this.am.b(an, (byte)(b0 | 1 << i));
      } else {
         this.am.b(an, (byte)(b0 & ~(1 << i)));
      }
   }

   public int cc() {
      return this.maxAirTicks;
   }

   public int cd() {
      return this.am.a(aQ);
   }

   public void i(int i) {
      EntityAirChangeEvent event = new EntityAirChangeEvent(this.getBukkitEntity(), i);
      if (this.valid) {
         event.getEntity().getServer().getPluginManager().callEvent(event);
      }

      if (event.isCancelled() && this.cd() != i) {
         this.am.markDirty(aQ);
      } else {
         this.am.b(aQ, event.getAmount());
      }
   }

   public int ce() {
      return this.am.a(aV);
   }

   public void j(int i) {
      this.am.b(aV, i);
   }

   public float cf() {
      int i = this.ch();
      return (float)Math.min(this.ce(), i) / (float)i;
   }

   public boolean cg() {
      return this.ce() >= this.ch();
   }

   public int ch() {
      return 140;
   }

   public void a(WorldServer worldserver, EntityLightning entitylightning) {
      this.g(this.aK + 1);
      org.bukkit.entity.Entity thisBukkitEntity = this.getBukkitEntity();
      org.bukkit.entity.Entity stormBukkitEntity = entitylightning.getBukkitEntity();
      PluginManager pluginManager = Bukkit.getPluginManager();
      if (this.aK == 0) {
         EntityCombustByEntityEvent entityCombustEvent = new EntityCombustByEntityEvent(stormBukkitEntity, thisBukkitEntity, 8);
         pluginManager.callEvent(entityCombustEvent);
         if (!entityCombustEvent.isCancelled()) {
            this.setSecondsOnFire(entityCombustEvent.getDuration(), false);
         }
      }

      if (thisBukkitEntity instanceof Hanging) {
         HangingBreakByEntityEvent hangingEvent = new HangingBreakByEntityEvent((Hanging)thisBukkitEntity, stormBukkitEntity);
         pluginManager.callEvent(hangingEvent);
         if (hangingEvent.isCancelled()) {
            return;
         }
      }

      if (!this.aS()) {
         CraftEventFactory.entityDamage = entitylightning;
         if (!this.a(this.dG().b(), 5.0F)) {
            CraftEventFactory.entityDamage = null;
         }
      }
   }

   public void k(boolean flag) {
      Vec3D vec3d = this.dj();
      double d0;
      if (flag) {
         d0 = Math.max(-0.9, vec3d.d - 0.03);
      } else {
         d0 = Math.min(1.8, vec3d.d + 0.1);
      }

      this.o(vec3d.c, d0, vec3d.e);
   }

   public void l(boolean flag) {
      Vec3D vec3d = this.dj();
      double d0;
      if (flag) {
         d0 = Math.max(-0.3, vec3d.d - 0.03);
      } else {
         d0 = Math.min(0.7, vec3d.d + 0.06);
      }

      this.o(vec3d.c, d0, vec3d.e);
      this.n();
   }

   public boolean a(WorldServer worldserver, EntityLiving entityliving) {
      return true;
   }

   public void ci() {
      if (this.dj().b() > -0.5 && this.aa > 1.0F) {
         this.aa = 1.0F;
      }
   }

   public void n() {
      this.aa = 0.0F;
   }

   protected void m(double d0, double d1, double d2) {
      BlockPosition blockposition = BlockPosition.a(d0, d1, d2);
      Vec3D vec3d = new Vec3D(d0 - (double)blockposition.u(), d1 - (double)blockposition.v(), d2 - (double)blockposition.w());
      BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();
      EnumDirection enumdirection = EnumDirection.b;
      double d3 = Double.MAX_VALUE;

      for(EnumDirection enumdirection1 : new EnumDirection[]{EnumDirection.c, EnumDirection.d, EnumDirection.e, EnumDirection.f, EnumDirection.b}) {
         blockposition_mutableblockposition.a(blockposition, enumdirection1);
         if (!this.H.a_(blockposition_mutableblockposition).r(this.H, blockposition_mutableblockposition)) {
            double d4 = vec3d.a(enumdirection1.o());
            double d5 = enumdirection1.f() == EnumDirection.EnumAxisDirection.a ? 1.0 - d4 : d4;
            if (d5 < d3) {
               d3 = d5;
               enumdirection = enumdirection1;
            }
         }
      }

      float f = this.af.i() * 0.2F + 0.1F;
      float f1 = (float)enumdirection.f().a();
      Vec3D vec3d1 = this.dj().a(0.75);
      if (enumdirection.o() == EnumDirection.EnumAxis.a) {
         this.o((double)(f1 * f), vec3d1.d, vec3d1.e);
      } else if (enumdirection.o() == EnumDirection.EnumAxis.b) {
         this.o(vec3d1.c, (double)(f1 * f), vec3d1.e);
      } else if (enumdirection.o() == EnumDirection.EnumAxis.c) {
         this.o(vec3d1.c, vec3d1.d, (double)(f1 * f));
      }
   }

   public void a(IBlockData iblockdata, Vec3D vec3d) {
      this.n();
      this.T = vec3d;
   }

   private static IChatBaseComponent c(IChatBaseComponent ichatbasecomponent) {
      IChatMutableComponent ichatmutablecomponent = ichatbasecomponent.d().b(ichatbasecomponent.a().a(null));

      for(IChatBaseComponent ichatbasecomponent1 : ichatbasecomponent.c()) {
         ichatmutablecomponent.b(c(ichatbasecomponent1));
      }

      return ichatmutablecomponent;
   }

   @Override
   public IChatBaseComponent Z() {
      IChatBaseComponent ichatbasecomponent = this.ab();
      return ichatbasecomponent != null ? c(ichatbasecomponent) : this.cj();
   }

   protected IChatBaseComponent cj() {
      return this.p.h();
   }

   public boolean q(Entity entity) {
      return this == entity;
   }

   public float ck() {
      return 0.0F;
   }

   public void r(float f) {
   }

   public void s(float f) {
   }

   public boolean cl() {
      return true;
   }

   public boolean r(Entity entity) {
      return false;
   }

   @Override
   public String toString() {
      String s = this.H == null ? "~NULL~" : this.H.toString();
      return this.aH != null
         ? String.format(
            Locale.ROOT,
            "%s['%s'/%d, l='%s', x=%.2f, y=%.2f, z=%.2f, removed=%s]",
            this.getClass().getSimpleName(),
            this.Z().getString(),
            this.q,
            s,
            this.dl(),
            this.dn(),
            this.dr(),
            this.aH
         )
         : String.format(
            Locale.ROOT,
            "%s['%s'/%d, l='%s', x=%.2f, y=%.2f, z=%.2f]",
            this.getClass().getSimpleName(),
            this.Z().getString(),
            this.q,
            s,
            this.dl(),
            this.dn(),
            this.dr()
         );
   }

   public boolean b(DamageSource damagesource) {
      return this.dB()
         || this.aZ && !damagesource.a(DamageTypeTags.d) && !damagesource.g()
         || damagesource.a(DamageTypeTags.i) && this.aS()
         || damagesource.a(DamageTypeTags.m) && this.ae().a(TagsEntity.l);
   }

   public boolean cm() {
      return this.aZ;
   }

   public void m(boolean flag) {
      this.aZ = flag;
   }

   public void s(Entity entity) {
      this.b(entity.dl(), entity.dn(), entity.dr(), entity.dw(), entity.dy());
   }

   public void t(Entity entity) {
      NBTTagCompound nbttagcompound = entity.f(new NBTTagCompound());
      nbttagcompound.r("Dimension");
      this.g(nbttagcompound);
      this.aY = entity.aY;
      this.aw = entity.aw;
   }

   @Nullable
   public Entity b(WorldServer worldserver) {
      return this.teleportTo(worldserver, null);
   }

   @Nullable
   public Entity teleportTo(WorldServer worldserver, Position location) {
      if (this.H instanceof WorldServer && !this.dB()) {
         this.H.ac().a("changeDimension");
         if (worldserver == null) {
            return null;
         } else {
            this.H.ac().a("reposition");
            ShapeDetectorShape shapedetectorshape = location == null
               ? this.a(worldserver)
               : new ShapeDetectorShape(new Vec3D(location.a(), location.b(), location.c()), Vec3D.b, this.aE, this.aF, worldserver, null);
            if (shapedetectorshape == null) {
               return null;
            } else {
               worldserver = shapedetectorshape.world;
               if (worldserver == this.H) {
                  this.b(shapedetectorshape.a.c, shapedetectorshape.a.d, shapedetectorshape.a.e, shapedetectorshape.c, shapedetectorshape.d);
                  this.f(shapedetectorshape.b);
                  return this;
               } else {
                  this.ac();
                  this.H.ac().b("reloading");
                  Entity entity = this.ae().a((World)worldserver);
                  if (entity != null) {
                     entity.t(this);
                     entity.b(shapedetectorshape.a.c, shapedetectorshape.a.d, shapedetectorshape.a.e, shapedetectorshape.c, entity.dy());
                     entity.f(shapedetectorshape.b);
                     worldserver.d(entity);
                     if (worldserver.getTypeKey() == WorldDimension.d) {
                        WorldServer.makeObsidianPlatform(worldserver, this);
                     }

                     this.getBukkitEntity().setHandle(entity);
                     entity.bukkitEntity = this.getBukkitEntity();
                     if (this instanceof EntityInsentient) {
                        ((EntityInsentient)this).a(true, false);
                     }
                  }

                  this.cn();
                  this.H.ac().c();
                  ((WorldServer)this.H).g();
                  worldserver.g();
                  this.H.ac().c();
                  return entity;
               }
            }
         }
      } else {
         return null;
      }
   }

   protected void cn() {
      this.b(Entity.RemovalReason.e);
   }

   @Nullable
   protected ShapeDetectorShape a(WorldServer worldserver) {
      if (worldserver == null) {
         return null;
      } else {
         boolean flag = this.H.getTypeKey() == WorldDimension.d && worldserver.getTypeKey() == WorldDimension.b;
         boolean flag1 = worldserver.getTypeKey() == WorldDimension.d;
         if (!flag && !flag1) {
            boolean flag2 = worldserver.getTypeKey() == WorldDimension.c;
            if (this.H.getTypeKey() != WorldDimension.c && !flag2) {
               return null;
            } else {
               WorldBorder worldborder = worldserver.p_();
               double d0 = DimensionManager.a(this.H.q_(), worldserver.q_());
               BlockPosition blockposition = worldborder.b(this.dl() * d0, this.dn(), this.dr() * d0);
               CraftPortalEvent event = this.callPortalEvent(
                  this,
                  worldserver,
                  new Position((double)blockposition.u(), (double)blockposition.v(), (double)blockposition.w()),
                  TeleportCause.NETHER_PORTAL,
                  flag2 ? 16 : 128,
                  16
               );
               if (event == null) {
                  return null;
               } else {
                  WorldServer worldserverFinal = worldserver = ((CraftWorld)event.getTo().getWorld()).getHandle();
                  worldborder = worldserverFinal.p_();
                  blockposition = worldborder.b(event.getTo().getX(), event.getTo().getY(), event.getTo().getZ());
                  return this.getExitPortal(
                        worldserver, blockposition, flag2, worldborder, event.getSearchRadius(), event.getCanCreatePortal(), event.getCreationRadius()
                     )
                     .map(
                        blockutil_rectangle -> {
                           IBlockData iblockdata = this.H.a_(this.aw);
                           EnumDirection.EnumAxis enumdirection_enumaxis;
                           Vec3D vec3d;
                           if (iblockdata.b(BlockProperties.H)) {
                              enumdirection_enumaxis = iblockdata.c(BlockProperties.H);
                              BlockUtil.Rectangle blockutil_rectangle1 = BlockUtil.a(
                                 this.aw,
                                 enumdirection_enumaxis,
                                 21,
                                 EnumDirection.EnumAxis.b,
                                 21,
                                 blockposition1x -> this.H.a_(blockposition1x) == iblockdata
                              );
                              vec3d = this.a(enumdirection_enumaxis, blockutil_rectangle1);
                           } else {
                              enumdirection_enumaxis = EnumDirection.EnumAxis.a;
                              vec3d = new Vec3D(0.5, 0.0, 0.0);
                           }
      
                           return BlockPortalShape.createPortalInfo(
                              worldserverFinal, blockutil_rectangle, enumdirection_enumaxis, vec3d, this, this.dj(), this.dw(), this.dy(), event
                           );
                        }
                     )
                     .orElse(null);
               }
            }
         } else {
            BlockPosition blockposition1;
            if (flag1) {
               blockposition1 = WorldServer.a;
            } else {
               blockposition1 = worldserver.a(HeightMap.Type.f, worldserver.Q());
            }

            CraftPortalEvent event = this.callPortalEvent(
               this,
               worldserver,
               new Position((double)blockposition1.u() + 0.5, (double)blockposition1.v(), (double)blockposition1.w() + 0.5),
               TeleportCause.END_PORTAL,
               0,
               0
            );
            return event == null
               ? null
               : new ShapeDetectorShape(
                  new Vec3D(event.getTo().getX(), event.getTo().getY(), event.getTo().getZ()),
                  this.dj(),
                  this.dw(),
                  this.dy(),
                  ((CraftWorld)event.getTo().getWorld()).getHandle(),
                  event
               );
         }
      }
   }

   protected Vec3D a(EnumDirection.EnumAxis enumdirection_enumaxis, BlockUtil.Rectangle blockutil_rectangle) {
      return BlockPortalShape.a(blockutil_rectangle, enumdirection_enumaxis, this.de(), this.a(this.al()));
   }

   protected CraftPortalEvent callPortalEvent(
      Entity entity, WorldServer exitWorldServer, Position exitPosition, TeleportCause cause, int searchRadius, int creationRadius
   ) {
      org.bukkit.entity.Entity bukkitEntity = entity.getBukkitEntity();
      Location enter = bukkitEntity.getLocation();
      Location exit = new Location(exitWorldServer.getWorld(), exitPosition.a(), exitPosition.b(), exitPosition.c());
      EntityPortalEvent event = new EntityPortalEvent(bukkitEntity, enter, exit, searchRadius);
      event.getEntity().getServer().getPluginManager().callEvent(event);
      return !event.isCancelled() && event.getTo() != null && event.getTo().getWorld() != null && entity.bq() ? new CraftPortalEvent(event) : null;
   }

   protected Optional<BlockUtil.Rectangle> getExitPortal(
      WorldServer worldserver, BlockPosition blockposition, boolean flag, WorldBorder worldborder, int searchRadius, boolean canCreatePortal, int createRadius
   ) {
      return worldserver.o().findPortalAround(blockposition, worldborder, searchRadius);
   }

   public boolean co() {
      return !this.bL() && !this.bM();
   }

   public float a(Explosion explosion, IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata, Fluid fluid, float f) {
      return f;
   }

   public boolean a(Explosion explosion, IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata, float f) {
      return true;
   }

   public int cp() {
      return 3;
   }

   public boolean cq() {
      return false;
   }

   public void a(CrashReportSystemDetails crashreportsystemdetails) {
      crashreportsystemdetails.a("Entity Type", () -> {
         MinecraftKey minecraftkey = EntityTypes.a(this.ae());
         return minecraftkey + " (" + this.getClass().getCanonicalName() + ")";
      });
      crashreportsystemdetails.a("Entity ID", this.q);
      crashreportsystemdetails.a("Entity Name", () -> this.Z().getString());
      crashreportsystemdetails.a("Entity's Exact location", String.format(Locale.ROOT, "%.2f, %.2f, %.2f", this.dl(), this.dn(), this.dr()));
      crashreportsystemdetails.a(
         "Entity's Block location", CrashReportSystemDetails.a(this.H, MathHelper.a(this.dl()), MathHelper.a(this.dn()), MathHelper.a(this.dr()))
      );
      Vec3D vec3d = this.dj();
      crashreportsystemdetails.a("Entity's Momentum", String.format(Locale.ROOT, "%.2f, %.2f, %.2f", vec3d.c, vec3d.d, vec3d.e));
      crashreportsystemdetails.a("Entity's Passengers", () -> this.cM().toString());
      crashreportsystemdetails.a("Entity's Vehicle", () -> String.valueOf(this.cV()));
   }

   public boolean cr() {
      return this.bK() && !this.F_();
   }

   public void a_(UUID uuid) {
      this.ax = uuid;
      this.ay = this.ax.toString();
   }

   @Override
   public UUID cs() {
      return this.ax;
   }

   public String ct() {
      return this.ay;
   }

   public String cu() {
      return this.ay;
   }

   public boolean cv() {
      return true;
   }

   public static double cw() {
      return o;
   }

   public static void b(double d0) {
      o = d0;
   }

   @Override
   public IChatBaseComponent G_() {
      return ScoreboardTeam.a(this.cb(), this.Z()).a(chatmodifier -> chatmodifier.a(this.cC()).a(this.ct()));
   }

   public void b(@Nullable IChatBaseComponent ichatbasecomponent) {
      this.am.b(aR, Optional.ofNullable(ichatbasecomponent));
   }

   @Nullable
   @Override
   public IChatBaseComponent ab() {
      return this.am.a(aR).orElse(null);
   }

   @Override
   public boolean aa() {
      return this.am.a(aR).isPresent();
   }

   public void n(boolean flag) {
      this.am.b(aS, flag);
   }

   public boolean cx() {
      return this.am.a(aS);
   }

   public final void n(double d0, double d1, double d2) {
      if (this.H instanceof WorldServer) {
         ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(BlockPosition.a(d0, d1, d2));
         ((WorldServer)this.H).k().a(TicketType.g, chunkcoordintpair, 0, this.af());
         this.H.d(chunkcoordintpair.e, chunkcoordintpair.f);
         this.b(d0, d1, d2);
      }
   }

   public boolean teleportTo(WorldServer worldserver, double d0, double d1, double d2, Set<RelativeMovement> set, float f, float f1, TeleportCause cause) {
      return this.a(worldserver, d0, d1, d2, set, f, f1);
   }

   public boolean a(WorldServer worldserver, double d0, double d1, double d2, Set<RelativeMovement> set, float f, float f1) {
      float f2 = MathHelper.a(f1, -90.0F, 90.0F);
      if (worldserver == this.H) {
         this.b(d0, d1, d2, f, f2);
         this.r(f);
      } else {
         this.ac();
         Entity entity = this.ae().a((World)worldserver);
         if (entity == null) {
            return false;
         }

         entity.t(this);
         entity.b(d0, d1, d2, f, f2);
         entity.r(f);
         this.b(Entity.RemovalReason.e);
         worldserver.d(entity);
      }

      return true;
   }

   public void a(double d0, double d1, double d2) {
      this.b(d0, d1, d2);
   }

   public void b(double d0, double d1, double d2) {
      if (this.H instanceof WorldServer) {
         this.b(d0, d1, d2, this.dw(), this.dy());
         this.cO().forEach(entity -> {
            UnmodifiableIterator unmodifiableiterator = entity.r.iterator();

            while(unmodifiableiterator.hasNext()) {
               Entity entity1 = (Entity)unmodifiableiterator.next();
               entity.a(entity1, Entity::d);
            }
         });
      }
   }

   public void c(double d0, double d1, double d2) {
      this.b(this.dl() + d0, this.dn() + d1, this.dr() + d2);
   }

   public boolean cy() {
      return this.cx();
   }

   public void a(List<DataWatcher.b<?>> list) {
   }

   public void a(DataWatcherObject<?> datawatcherobject) {
      if (ar.equals(datawatcherobject)) {
         this.c_();
      }
   }

   @Deprecated
   protected void cz() {
      EntityPose entitypose = this.al();
      EntitySize entitysize = this.a(entitypose);
      this.be = entitysize;
      this.bf = this.a(entitypose, entitysize);
   }

   public void c_() {
      EntitySize entitysize = this.be;
      EntityPose entitypose = this.al();
      EntitySize entitysize1 = this.a(entitypose);
      this.be = entitysize1;
      this.bf = this.a(entitypose, entitysize1);
      this.an();
      boolean flag = (double)entitysize1.a <= 4.0 && (double)entitysize1.b <= 4.0;
      if (!this.H.B && !this.al && !this.ae && flag && (entitysize1.a > entitysize.a || entitysize1.b > entitysize.b) && !(this instanceof EntityHuman)) {
         Vec3D vec3d = this.de().b(0.0, (double)entitysize.b / 2.0, 0.0);
         double d0 = (double)Math.max(0.0F, entitysize1.a - entitysize.a) + 1.0E-6;
         double d1 = (double)Math.max(0.0F, entitysize1.b - entitysize.b) + 1.0E-6;
         VoxelShape voxelshape = VoxelShapes.a(AxisAlignedBB.a(vec3d, d0, d1, d0));
         this.H
            .a(this, voxelshape, vec3d, (double)entitysize1.a, (double)entitysize1.b, (double)entitysize1.a)
            .ifPresent(vec3d1 -> this.a(vec3d1.b(0.0, (double)(-entitysize1.b) / 2.0, 0.0)));
      }
   }

   public EnumDirection cA() {
      return EnumDirection.a((double)this.dw());
   }

   public EnumDirection cB() {
      return this.cA();
   }

   protected ChatHoverable cC() {
      return new ChatHoverable(ChatHoverable.EnumHoverAction.c, new ChatHoverable.b(this.ae(), this.cs(), this.Z()));
   }

   public boolean a(EntityPlayer entityplayer) {
      return true;
   }

   @Override
   public final AxisAlignedBB cD() {
      return this.aG;
   }

   public AxisAlignedBB A_() {
      return this.cD();
   }

   protected AxisAlignedBB e(EntityPose entitypose) {
      EntitySize entitysize = this.a(entitypose);
      float f = entitysize.a / 2.0F;
      Vec3D vec3d = new Vec3D(this.dl() - (double)f, this.dn(), this.dr() - (double)f);
      Vec3D vec3d1 = new Vec3D(this.dl() + (double)f, this.dn() + (double)entitysize.b, this.dr() + (double)f);
      return new AxisAlignedBB(vec3d, vec3d1);
   }

   public final void a(AxisAlignedBB axisalignedbb) {
      double minX = axisalignedbb.a;
      double minY = axisalignedbb.b;
      double minZ = axisalignedbb.c;
      double maxX = axisalignedbb.d;
      double maxY = axisalignedbb.e;
      double maxZ = axisalignedbb.f;
      double len = axisalignedbb.d - axisalignedbb.a;
      if (len < 0.0) {
         maxX = minX;
      }

      if (len > 64.0) {
         maxX = minX + 64.0;
      }

      len = axisalignedbb.e - axisalignedbb.b;
      if (len < 0.0) {
         maxY = minY;
      }

      if (len > 64.0) {
         maxY = minY + 64.0;
      }

      len = axisalignedbb.f - axisalignedbb.c;
      if (len < 0.0) {
         maxZ = minZ;
      }

      if (len > 64.0) {
         maxZ = minZ + 64.0;
      }

      this.aG = new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
   }

   protected float a(EntityPose entitypose, EntitySize entitysize) {
      return entitysize.b * 0.85F;
   }

   public float f(EntityPose entitypose) {
      return this.a(entitypose, this.a(entitypose));
   }

   public final float cE() {
      return this.bf;
   }

   public Vec3D t(float f) {
      return this.cF();
   }

   protected Vec3D cF() {
      return new Vec3D(0.0, (double)this.cE(), (double)(this.dc() * 0.4F));
   }

   public SlotAccess a_(int i) {
      return SlotAccess.b;
   }

   @Override
   public void a(IChatBaseComponent ichatbasecomponent) {
   }

   public World cG() {
      return this.H;
   }

   @Nullable
   public MinecraftServer cH() {
      return this.H.n();
   }

   public EnumInteractionResult a(EntityHuman entityhuman, Vec3D vec3d, EnumHand enumhand) {
      return EnumInteractionResult.d;
   }

   public boolean cI() {
      return false;
   }

   public void a(EntityLiving entityliving, Entity entity) {
      if (entity instanceof EntityLiving) {
         EnchantmentManager.a((EntityLiving)entity, entityliving);
      }

      EnchantmentManager.b(entityliving, entity);
   }

   public void c(EntityPlayer entityplayer) {
   }

   public void d(EntityPlayer entityplayer) {
   }

   public float a(EnumBlockRotation enumblockrotation) {
      float f = MathHelper.g(this.dw());
      switch(enumblockrotation) {
         case b:
            return f + 90.0F;
         case c:
            return f + 180.0F;
         case d:
            return f + 270.0F;
         default:
            return f;
      }
   }

   public float a(EnumBlockMirror enumblockmirror) {
      float f = MathHelper.g(this.dw());
      switch(enumblockmirror) {
         case b:
            return 180.0F - f;
         case c:
            return -f;
         default:
            return f;
      }
   }

   public boolean cJ() {
      return false;
   }

   @Nullable
   public EntityLiving cK() {
      return null;
   }

   public final boolean cL() {
      return this.cK() != null;
   }

   public final List<Entity> cM() {
      return this.r;
   }

   @Nullable
   public Entity cN() {
      return this.r.isEmpty() ? null : (Entity)this.r.get(0);
   }

   public boolean u(Entity entity) {
      return this.r.contains(entity);
   }

   public boolean a(Predicate<Entity> predicate) {
      UnmodifiableIterator unmodifiableiterator = this.r.iterator();

      while(unmodifiableiterator.hasNext()) {
         Entity entity = (Entity)unmodifiableiterator.next();
         if (predicate.test(entity)) {
            return true;
         }
      }

      return false;
   }

   private Stream<Entity> p() {
      return this.r.stream().flatMap(Entity::cO);
   }

   @Override
   public Stream<Entity> cO() {
      return Stream.concat(Stream.of(this), this.p());
   }

   @Override
   public Stream<Entity> cP() {
      return Stream.concat(this.r.stream().flatMap(Entity::cP), Stream.of(this));
   }

   public Iterable<Entity> cQ() {
      return () -> this.p().iterator();
   }

   public boolean cR() {
      return this.p().filter(entity -> entity instanceof EntityHuman).count() == 1L;
   }

   public Entity cS() {
      Entity entity = this;

      while(entity.bL()) {
         entity = entity.cV();
      }

      return entity;
   }

   public boolean v(Entity entity) {
      return this.cS() == entity.cS();
   }

   public boolean w(Entity entity) {
      if (!entity.bL()) {
         return false;
      } else {
         Entity entity1 = entity.cV();
         return entity1 == this ? true : this.w(entity1);
      }
   }

   public boolean cT() {
      EntityLiving entityliving = this.cK();
      return entityliving instanceof EntityHuman entityhuman ? entityhuman.g() : this.cU();
   }

   public boolean cU() {
      return !this.H.B;
   }

   protected static Vec3D a(double d0, double d1, float f) {
      double d2 = (d0 + d1 + 1.0E-5F) / 2.0;
      float f1 = -MathHelper.a(f * (float) (Math.PI / 180.0));
      float f2 = MathHelper.b(f * (float) (Math.PI / 180.0));
      float f3 = Math.max(Math.abs(f1), Math.abs(f2));
      return new Vec3D((double)f1 * d2 / (double)f3, 0.0, (double)f2 * d2 / (double)f3);
   }

   public Vec3D b(EntityLiving entityliving) {
      return new Vec3D(this.dl(), this.cD().e, this.dr());
   }

   @Nullable
   public Entity cV() {
      return this.s;
   }

   @Nullable
   public Entity cW() {
      return this.s != null && this.s.cK() == this ? this.s : null;
   }

   public EnumPistonReaction C_() {
      return EnumPistonReaction.a;
   }

   public SoundCategory cX() {
      return SoundCategory.g;
   }

   public int cY() {
      return 1;
   }

   public CommandListenerWrapper cZ() {
      return new CommandListenerWrapper(
         this, this.de(), this.bD(), this.H instanceof WorldServer ? (WorldServer)this.H : null, this.B(), this.Z().getString(), this.G_(), this.H.n(), this
      );
   }

   protected int B() {
      return 0;
   }

   public boolean k(int i) {
      return this.B() >= i;
   }

   @Override
   public boolean d_() {
      return this.H.W().b(GameRules.o);
   }

   @Override
   public boolean j_() {
      return true;
   }

   @Override
   public boolean M_() {
      return true;
   }

   public void a(ArgumentAnchor.Anchor argumentanchor_anchor, Vec3D vec3d) {
      Vec3D vec3d1 = argumentanchor_anchor.a(this);
      double d0 = vec3d.c - vec3d1.c;
      double d1 = vec3d.d - vec3d1.d;
      double d2 = vec3d.e - vec3d1.e;
      double d3 = Math.sqrt(d0 * d0 + d2 * d2);
      this.e(MathHelper.g((float)(-(MathHelper.d(d1, d3) * 180.0F / (float)Math.PI))));
      this.f(MathHelper.g((float)(MathHelper.d(d2, d0) * 180.0F / (float)Math.PI) - 90.0F));
      this.r(this.dw());
      this.M = this.dy();
      this.L = this.dw();
   }

   public boolean a(TagKey<FluidType> tagkey, double d0) {
      if (this.da()) {
         return false;
      } else {
         AxisAlignedBB axisalignedbb = this.cD().h(0.001);
         int i = MathHelper.a(axisalignedbb.a);
         int j = MathHelper.c(axisalignedbb.d);
         int k = MathHelper.a(axisalignedbb.b);
         int l = MathHelper.c(axisalignedbb.e);
         int i1 = MathHelper.a(axisalignedbb.c);
         int j1 = MathHelper.c(axisalignedbb.f);
         double d1 = 0.0;
         boolean flag = this.cv();
         boolean flag1 = false;
         Vec3D vec3d = Vec3D.b;
         int k1 = 0;
         BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();

         for(int l1 = i; l1 < j; ++l1) {
            for(int i2 = k; i2 < l; ++i2) {
               for(int j2 = i1; j2 < j1; ++j2) {
                  blockposition_mutableblockposition.d(l1, i2, j2);
                  Fluid fluid = this.H.b_(blockposition_mutableblockposition);
                  if (fluid.a(tagkey)) {
                     double d2 = (double)((float)i2 + fluid.a((IBlockAccess)this.H, blockposition_mutableblockposition));
                     if (d2 >= axisalignedbb.b) {
                        flag1 = true;
                        d1 = Math.max(d2 - axisalignedbb.b, d1);
                        if (flag) {
                           Vec3D vec3d1 = fluid.c(this.H, blockposition_mutableblockposition);
                           if (d1 < 0.4) {
                              vec3d1 = vec3d1.a(d1);
                           }

                           vec3d = vec3d.e(vec3d1);
                           ++k1;
                        }

                        if (tagkey == TagsFluid.b) {
                           this.lastLavaContact = blockposition_mutableblockposition.i();
                        }
                     }
                  }
               }
            }
         }

         if (vec3d.f() > 0.0) {
            if (k1 > 0) {
               vec3d = vec3d.a(1.0 / (double)k1);
            }

            if (!(this instanceof EntityHuman)) {
               vec3d = vec3d.d();
            }

            Vec3D vec3d2 = this.dj();
            vec3d = vec3d.a(d0 * 1.0);
            double d3 = 0.003;
            if (Math.abs(vec3d2.c) < 0.003 && Math.abs(vec3d2.e) < 0.003 && vec3d.f() < 0.0045000000000000005) {
               vec3d = vec3d.d().a(0.0045000000000000005);
            }

            this.f(this.dj().e(vec3d));
         }

         this.ai.put(tagkey, d1);
         return flag1;
      }
   }

   public boolean da() {
      AxisAlignedBB axisalignedbb = this.cD().g(1.0);
      int i = MathHelper.a(axisalignedbb.a);
      int j = MathHelper.c(axisalignedbb.d);
      int k = MathHelper.a(axisalignedbb.c);
      int l = MathHelper.c(axisalignedbb.f);
      return !this.H.b(i, k, j, l);
   }

   public double b(TagKey<FluidType> tagkey) {
      return this.ai.getDouble(tagkey);
   }

   public double db() {
      return (double)this.cE() < 0.4 ? 0.0 : 0.4;
   }

   public final float dc() {
      return this.be.a;
   }

   public final float dd() {
      return this.be.b;
   }

   public Packet<PacketListenerPlayOut> S() {
      return new PacketPlayOutSpawnEntity(this);
   }

   public EntitySize a(EntityPose entitypose) {
      return this.p.n();
   }

   public Vec3D de() {
      return this.t;
   }

   public Vec3D df() {
      return this.de();
   }

   @Override
   public BlockPosition dg() {
      return this.u;
   }

   public IBlockData dh() {
      if (this.bj == null) {
         this.bj = this.H.a_(this.dg());
      }

      return this.bj;
   }

   public ChunkCoordIntPair di() {
      return this.aC;
   }

   public Vec3D dj() {
      return this.aD;
   }

   public void f(Vec3D vec3d) {
      this.aD = vec3d;
   }

   public void g(Vec3D vec3d) {
      this.f(this.dj().e(vec3d));
   }

   public void o(double d0, double d1, double d2) {
      this.f(new Vec3D(d0, d1, d2));
   }

   public final int dk() {
      return this.u.u();
   }

   public final double dl() {
      return this.t.c;
   }

   public double c(double d0) {
      return this.t.c + (double)this.dc() * d0;
   }

   public double d(double d0) {
      return this.c((2.0 * this.af.j() - 1.0) * d0);
   }

   public final int dm() {
      return this.u.v();
   }

   public final double dn() {
      return this.t.d;
   }

   public double e(double d0) {
      return this.t.d + (double)this.dd() * d0;
   }

   public double do() {
      return this.e(this.af.j());
   }

   public double dp() {
      return this.t.d + (double)this.bf;
   }

   public final int dq() {
      return this.u.w();
   }

   public final double dr() {
      return this.t.e;
   }

   public double f(double d0) {
      return this.t.e + (double)this.dc() * d0;
   }

   public double g(double d0) {
      return this.f((2.0 * this.af.j() - 1.0) * d0);
   }

   public final void p(double d0, double d1, double d2) {
      if (this.t.c != d0 || this.t.d != d1 || this.t.e != d2) {
         this.t = new Vec3D(d0, d1, d2);
         int i = MathHelper.a(d0);
         int j = MathHelper.a(d1);
         int k = MathHelper.a(d2);
         if (i != this.u.u() || j != this.u.v() || k != this.u.w()) {
            this.u = new BlockPosition(i, j, k);
            this.bj = null;
            if (SectionPosition.a(i) != this.aC.e || SectionPosition.a(k) != this.aC.f) {
               this.aC = new ChunkCoordIntPair(this.u);
            }
         }

         this.aW.a();
      }
   }

   public void ds() {
   }

   public Vec3D u(float f) {
      return this.p(f).b(0.0, (double)this.bf * 0.7, 0.0);
   }

   public void a(PacketPlayOutSpawnEntity packetplayoutspawnentity) {
      int i = packetplayoutspawnentity.a();
      double d0 = packetplayoutspawnentity.e();
      double d1 = packetplayoutspawnentity.f();
      double d2 = packetplayoutspawnentity.g();
      this.f(d0, d1, d2);
      this.d(d0, d1, d2);
      this.e(packetplayoutspawnentity.k());
      this.f(packetplayoutspawnentity.l());
      this.e(i);
      this.a_(packetplayoutspawnentity.c());
   }

   @Nullable
   public ItemStack dt() {
      return null;
   }

   public void o(boolean flag) {
      this.az = flag;
   }

   public boolean du() {
      return !this.ae().a(TagsEntity.i);
   }

   public boolean dv() {
      return (this.az || this.aA) && this.du();
   }

   public float dw() {
      return this.aE;
   }

   public float dx() {
      return this.dw();
   }

   public void f(float f) {
      if (!Float.isFinite(f)) {
         SystemUtils.a("Invalid entity rotation: " + f + ", discarding.");
      } else {
         this.aE = f;
      }
   }

   public float dy() {
      return this.aF;
   }

   public void e(float f) {
      if (!Float.isFinite(f)) {
         SystemUtils.a("Invalid entity rotation: " + f + ", discarding.");
      } else {
         this.aF = f;
      }
   }

   public boolean dz() {
      return false;
   }

   public float dA() {
      return this.aJ;
   }

   public void v(float f) {
      this.aJ = f;
   }

   public final boolean dB() {
      return this.aH != null;
   }

   @Nullable
   public Entity.RemovalReason dC() {
      return this.aH;
   }

   @Override
   public final void b(Entity.RemovalReason entity_removalreason) {
      if (this.aH == null) {
         this.aH = entity_removalreason;
      }

      if (this.aH.a()) {
         this.bz();
      }

      this.cM().forEach(Entity::bz);
      this.aW.a(entity_removalreason);
   }

   public void dD() {
      this.aH = null;
   }

   @Override
   public void a(EntityInLevelCallback entityinlevelcallback) {
      this.aW = entityinlevelcallback;
   }

   @Override
   public boolean dE() {
      return this.aH != null && !this.aH.b() ? false : (this.bL() ? false : !this.bM() || !this.cR());
   }

   @Override
   public boolean dF() {
      return false;
   }

   public boolean a(World world, BlockPosition blockposition) {
      return true;
   }

   public World Y() {
      return this.H;
   }

   public DamageSources dG() {
      return this.H.af();
   }

   @FunctionalInterface
   public interface MoveFunction {
      void accept(Entity var1, double var2, double var4, double var6);
   }

   public static enum MovementEmission {
      a(false, false),
      b(true, false),
      c(false, true),
      d(true, true);

      final boolean e;
      final boolean f;

      private MovementEmission(boolean flag, boolean flag1) {
         this.e = flag;
         this.f = flag1;
      }

      public boolean a() {
         return this.f || this.e;
      }

      public boolean b() {
         return this.f;
      }

      public boolean c() {
         return this.e;
      }
   }

   public static enum RemovalReason {
      a(true, false),
      b(true, false),
      c(false, true),
      d(false, false),
      e(false, false);

      private final boolean f;
      private final boolean g;

      private RemovalReason(boolean flag, boolean flag1) {
         this.f = flag;
         this.g = flag1;
      }

      public boolean a() {
         return this.f;
      }

      public boolean b() {
         return this.g;
      }
   }
}
