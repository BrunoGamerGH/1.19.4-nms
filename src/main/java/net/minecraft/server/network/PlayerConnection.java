package net.minecraft.server.network;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.primitives.Floats;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.net.SocketAddress;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportSystemDetails;
import net.minecraft.EnumChatFormat;
import net.minecraft.ReportedException;
import net.minecraft.SharedConstants;
import net.minecraft.SystemUtils;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.CommandSigningContext;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.TickablePacketListener;
import net.minecraft.network.chat.ChatMessageType;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatMutableComponent;
import net.minecraft.network.chat.LastSeenMessages;
import net.minecraft.network.chat.LastSeenMessagesValidator;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.network.chat.MessageSignatureCache;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.network.chat.RemoteChatSession;
import net.minecraft.network.chat.SignableCommand;
import net.minecraft.network.chat.SignedMessageBody;
import net.minecraft.network.chat.SignedMessageChain;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PlayerConnectionUtils;
import net.minecraft.network.protocol.game.ClientboundBlockChangedAckPacket;
import net.minecraft.network.protocol.game.ClientboundDisguisedChatPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerChatPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import net.minecraft.network.protocol.game.PacketListenerPlayIn;
import net.minecraft.network.protocol.game.PacketPlayInAbilities;
import net.minecraft.network.protocol.game.PacketPlayInAdvancements;
import net.minecraft.network.protocol.game.PacketPlayInArmAnimation;
import net.minecraft.network.protocol.game.PacketPlayInAutoRecipe;
import net.minecraft.network.protocol.game.PacketPlayInBEdit;
import net.minecraft.network.protocol.game.PacketPlayInBeacon;
import net.minecraft.network.protocol.game.PacketPlayInBlockDig;
import net.minecraft.network.protocol.game.PacketPlayInBlockPlace;
import net.minecraft.network.protocol.game.PacketPlayInBoatMove;
import net.minecraft.network.protocol.game.PacketPlayInChat;
import net.minecraft.network.protocol.game.PacketPlayInClientCommand;
import net.minecraft.network.protocol.game.PacketPlayInCloseWindow;
import net.minecraft.network.protocol.game.PacketPlayInCustomPayload;
import net.minecraft.network.protocol.game.PacketPlayInDifficultyChange;
import net.minecraft.network.protocol.game.PacketPlayInDifficultyLock;
import net.minecraft.network.protocol.game.PacketPlayInEnchantItem;
import net.minecraft.network.protocol.game.PacketPlayInEntityAction;
import net.minecraft.network.protocol.game.PacketPlayInEntityNBTQuery;
import net.minecraft.network.protocol.game.PacketPlayInFlying;
import net.minecraft.network.protocol.game.PacketPlayInHeldItemSlot;
import net.minecraft.network.protocol.game.PacketPlayInItemName;
import net.minecraft.network.protocol.game.PacketPlayInJigsawGenerate;
import net.minecraft.network.protocol.game.PacketPlayInKeepAlive;
import net.minecraft.network.protocol.game.PacketPlayInPickItem;
import net.minecraft.network.protocol.game.PacketPlayInRecipeDisplayed;
import net.minecraft.network.protocol.game.PacketPlayInRecipeSettings;
import net.minecraft.network.protocol.game.PacketPlayInResourcePackStatus;
import net.minecraft.network.protocol.game.PacketPlayInSetCommandBlock;
import net.minecraft.network.protocol.game.PacketPlayInSetCommandMinecart;
import net.minecraft.network.protocol.game.PacketPlayInSetCreativeSlot;
import net.minecraft.network.protocol.game.PacketPlayInSetJigsaw;
import net.minecraft.network.protocol.game.PacketPlayInSettings;
import net.minecraft.network.protocol.game.PacketPlayInSpectate;
import net.minecraft.network.protocol.game.PacketPlayInSteerVehicle;
import net.minecraft.network.protocol.game.PacketPlayInStruct;
import net.minecraft.network.protocol.game.PacketPlayInTabComplete;
import net.minecraft.network.protocol.game.PacketPlayInTeleportAccept;
import net.minecraft.network.protocol.game.PacketPlayInTileNBTQuery;
import net.minecraft.network.protocol.game.PacketPlayInTrSel;
import net.minecraft.network.protocol.game.PacketPlayInUpdateSign;
import net.minecraft.network.protocol.game.PacketPlayInUseEntity;
import net.minecraft.network.protocol.game.PacketPlayInUseItem;
import net.minecraft.network.protocol.game.PacketPlayInVehicleMove;
import net.minecraft.network.protocol.game.PacketPlayInWindowClick;
import net.minecraft.network.protocol.game.PacketPlayOutAttachEntity;
import net.minecraft.network.protocol.game.PacketPlayOutBlockChange;
import net.minecraft.network.protocol.game.PacketPlayOutEntityEquipment;
import net.minecraft.network.protocol.game.PacketPlayOutHeldItemSlot;
import net.minecraft.network.protocol.game.PacketPlayOutKeepAlive;
import net.minecraft.network.protocol.game.PacketPlayOutKickDisconnect;
import net.minecraft.network.protocol.game.PacketPlayOutNBTQuery;
import net.minecraft.network.protocol.game.PacketPlayOutPosition;
import net.minecraft.network.protocol.game.PacketPlayOutSetSlot;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnPosition;
import net.minecraft.network.protocol.game.PacketPlayOutTabComplete;
import net.minecraft.network.protocol.game.PacketPlayOutVehicleMove;
import net.minecraft.network.protocol.game.ServerboundChatAckPacket;
import net.minecraft.network.protocol.game.ServerboundChatCommandPacket;
import net.minecraft.network.protocol.game.ServerboundChatSessionUpdatePacket;
import net.minecraft.network.protocol.game.ServerboundPongPacket;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.stats.RecipeBookServer;
import net.minecraft.util.FutureChain;
import net.minecraft.util.MathHelper;
import net.minecraft.util.SignatureValidator;
import net.minecraft.util.UtilColor;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityExperienceOrb;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.EnumMoveType;
import net.minecraft.world.entity.HasCustomInventoryScreen;
import net.minecraft.world.entity.IJumpable;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.EnumChatVisibility;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.entity.player.ProfilePublicKey;
import net.minecraft.world.entity.projectile.EntityArrow;
import net.minecraft.world.entity.vehicle.EntityBoat;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.inventory.ContainerAnvil;
import net.minecraft.world.inventory.ContainerBeacon;
import net.minecraft.world.inventory.ContainerMerchant;
import net.minecraft.world.inventory.ContainerRecipeBook;
import net.minecraft.world.inventory.InventoryClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemBlock;
import net.minecraft.world.item.ItemBucket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.IRecipe;
import net.minecraft.world.level.CommandBlockListenerAbstract;
import net.minecraft.world.level.EnumGamemode;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.RayTrace;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.BlockCommand;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityCommand;
import net.minecraft.world.level.block.entity.TileEntityJigsaw;
import net.minecraft.world.level.block.entity.TileEntitySign;
import net.minecraft.world.level.block.entity.TileEntityStructure;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.MovingObjectPosition;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.phys.shapes.OperatorBoolean;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.command.CommandException;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.SpigotTimings;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftSign;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftChatMessage;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftMagicNumbers;
import org.bukkit.craftbukkit.v1_19_R3.util.LazyPlayerSet;
import org.bukkit.craftbukkit.v1_19_R3.util.Waitable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.SmithItemEvent;
import org.bukkit.event.inventory.TradeSelectEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent.Status;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.SmithingInventory;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.slf4j.Logger;
import org.spigotmc.SpigotConfig;

public class PlayerConnection implements ServerPlayerConnection, TickablePacketListener, PacketListenerPlayIn {
   static final Logger c = LogUtils.getLogger();
   private static final int d = 15000;
   public static final double a = MathHelper.k(6.0);
   private static final int e = -1;
   private static final int f = 4096;
   private static final IChatBaseComponent g = IChatBaseComponent.c("multiplayer.disconnect.chat_validation_failed");
   private final NetworkManager h;
   private final MinecraftServer i;
   public EntityPlayer b;
   private int j;
   private int k = -1;
   private long l;
   private boolean m;
   private long n;
   private final AtomicInteger o = new AtomicInteger();
   private int p;
   private double q;
   private double r;
   private double s;
   private double t;
   private double u;
   private double v;
   @Nullable
   private Entity w;
   private double x;
   private double y;
   private double z;
   private double A;
   private double B;
   private double C;
   @Nullable
   private Vec3D D;
   private int E;
   private int F;
   private boolean G;
   private int H;
   private boolean I;
   private int J;
   private int K;
   private int L;
   private final AtomicReference<Instant> M;
   @Nullable
   private RemoteChatSession N;
   private SignedMessageChain.b O;
   private final LastSeenMessagesValidator P;
   private final MessageSignatureCache Q;
   private final FutureChain R;
   private final CraftServer cserver;
   public boolean processedDisconnect;
   private int lastTick = MinecraftServer.currentTick;
   private int allowedPlayerTicks = 1;
   private int lastDropTick = MinecraftServer.currentTick;
   private int lastBookTick = MinecraftServer.currentTick;
   private int dropCount = 0;
   private double lastPosX = Double.MAX_VALUE;
   private double lastPosY = Double.MAX_VALUE;
   private double lastPosZ = Double.MAX_VALUE;
   private float lastPitch = Float.MAX_VALUE;
   private float lastYaw = Float.MAX_VALUE;
   private boolean justTeleported = false;
   private boolean hasMoved;
   private int limitedPackets;
   private long lastLimitedPacket = -1L;
   private static final MinecraftKey CUSTOM_REGISTER = new MinecraftKey("register");
   private static final MinecraftKey CUSTOM_UNREGISTER = new MinecraftKey("unregister");

   public PlayerConnection(MinecraftServer minecraftserver, NetworkManager networkmanager, EntityPlayer entityplayer) {
      this.M = new AtomicReference<>(Instant.EPOCH);
      this.P = new LastSeenMessagesValidator(20);
      this.Q = MessageSignatureCache.a();
      this.i = minecraftserver;
      this.h = networkmanager;
      networkmanager.a(this);
      this.b = entityplayer;
      entityplayer.b = this;
      this.l = SystemUtils.b();
      entityplayer.T().a();
      this.O = minecraftserver.aw() ? SignedMessageChain.b.a : SignedMessageChain.b.unsigned(entityplayer.cs());
      this.R = new FutureChain(minecraftserver.chatExecutor);
      this.cserver = minecraftserver.server;
   }

   public CraftPlayer getCraftPlayer() {
      return this.b == null ? null : this.b.getBukkitEntity();
   }

   @Override
   public void c() {
      SpigotTimings.playerConnectionTimer.startTiming();
      if (this.k > -1) {
         this.a(new ClientboundBlockChangedAckPacket(this.k));
         this.k = -1;
      }

      this.d();
      this.b.I = this.b.dl();
      this.b.J = this.b.dn();
      this.b.K = this.b.dr();
      this.b.m();
      this.b.a(this.q, this.r, this.s, this.b.dw(), this.b.dy());
      ++this.j;
      this.L = this.K;
      if (this.G && !this.b.fu() && !this.b.bL() && !this.b.ep()) {
         if (++this.H > 80) {
            c.warn("{} was kicked for floating too long!", this.b.Z().getString());
            this.b(IChatBaseComponent.c("multiplayer.disconnect.flying"));
            return;
         }
      } else {
         this.G = false;
         this.H = 0;
      }

      this.w = this.b.cS();
      if (this.w != this.b && this.w.cK() == this.b) {
         this.x = this.w.dl();
         this.y = this.w.dn();
         this.z = this.w.dr();
         this.A = this.w.dl();
         this.B = this.w.dn();
         this.C = this.w.dr();
         if (this.I && this.b.cS().cK() == this.b) {
            if (++this.J > 80) {
               c.warn("{} was kicked for floating a vehicle too long!", this.b.Z().getString());
               this.b(IChatBaseComponent.c("multiplayer.disconnect.flying"));
               return;
            }
         } else {
            this.I = false;
            this.J = 0;
         }
      } else {
         this.w = null;
         this.I = false;
         this.J = 0;
      }

      this.i.aP().a("keepAlive");
      long i = SystemUtils.b();
      if (i - this.l >= 25000L) {
         if (this.m) {
            this.b(IChatBaseComponent.c("disconnect.timeout"));
         } else {
            this.m = true;
            this.l = i;
            this.n = i;
            this.a(new PacketPlayOutKeepAlive(this.n));
         }
      }

      this.i.aP().c();

      int spam;
      while((spam = this.o.get()) > 0 && !this.o.compareAndSet(spam, spam - 1)) {
      }

      if (this.p > 0) {
         --this.p;
      }

      if (this.b.I() > 0L && this.i.al() > 0 && SystemUtils.b() - this.b.I() > (long)(this.i.al() * 1000 * 60)) {
         this.b.C();
         this.b(IChatBaseComponent.c("multiplayer.disconnect.idling"));
      }

      SpigotTimings.playerConnectionTimer.stopTiming();
   }

   public void d() {
      this.q = this.b.dl();
      this.r = this.b.dn();
      this.s = this.b.dr();
      this.t = this.b.dl();
      this.u = this.b.dn();
      this.v = this.b.dr();
   }

   @Override
   public boolean a() {
      return this.h.h();
   }

   private boolean g() {
      return this.i.a(this.b.fI());
   }

   @Deprecated
   public void b(IChatBaseComponent ichatbasecomponent) {
      this.disconnect(CraftChatMessage.fromComponent(ichatbasecomponent));
   }

   public void disconnect(final String s) {
      if (!this.processedDisconnect) {
         if (!this.cserver.isPrimaryThread()) {
            Waitable waitable = new Waitable() {
               @Override
               protected Object evaluate() {
                  PlayerConnection.this.disconnect(s);
                  return null;
               }
            };
            this.i.processQueue.add(waitable);

            try {
               waitable.get();
            } catch (InterruptedException var7) {
               Thread.currentThread().interrupt();
            } catch (ExecutionException var8) {
               throw new RuntimeException(var8);
            }
         } else {
            String leaveMessage = EnumChatFormat.o + this.b.cu() + " left the game.";
            PlayerKickEvent event = new PlayerKickEvent(this.b.getBukkitEntity(), s, leaveMessage);
            if (this.cserver.getServer().v()) {
               this.cserver.getPluginManager().callEvent(event);
            }

            if (!event.isCancelled()) {
               this.b.kickLeaveMessage = event.getLeaveMessage();
               IChatBaseComponent ichatbasecomponent = CraftChatMessage.fromString(event.getReason(), true)[0];
               this.h.a(new PacketPlayOutKickDisconnect(ichatbasecomponent), PacketSendListener.a(() -> this.h.a(ichatbasecomponent)));
               this.a(ichatbasecomponent);
               this.h.l();
               MinecraftServer minecraftserver = this.i;
               NetworkManager networkmanager = this.h;
               minecraftserver.a(networkmanager::m);
            }
         }
      }
   }

   private <T, R> CompletableFuture<R> a(T t0, BiFunction<ITextFilter, T, CompletableFuture<R>> bifunction) {
      return bifunction.apply(this.b.T(), t0).thenApply(object -> {
         if (!this.a()) {
            c.debug("Ignoring packet due to disconnection");
            throw new CancellationException("disconnected");
         } else {
            return object;
         }
      });
   }

   private CompletableFuture<FilteredText> a(String s) {
      return this.a(s, ITextFilter::a);
   }

   private CompletableFuture<List<FilteredText>> a(List<String> list) {
      return this.a(list, ITextFilter::a);
   }

   @Override
   public void a(PacketPlayInSteerVehicle packetplayinsteervehicle) {
      PlayerConnectionUtils.a(packetplayinsteervehicle, this, this.b.x());
      this.b.a(packetplayinsteervehicle.a(), packetplayinsteervehicle.c(), packetplayinsteervehicle.d(), packetplayinsteervehicle.e());
   }

   private static boolean b(double d0, double d1, double d2, float f, float f1) {
      return Double.isNaN(d0) || Double.isNaN(d1) || Double.isNaN(d2) || !Floats.isFinite(f1) || !Floats.isFinite(f);
   }

   private static double a(double d0) {
      return MathHelper.a(d0, -3.0E7, 3.0E7);
   }

   private static double b(double d0) {
      return MathHelper.a(d0, -2.0E7, 2.0E7);
   }

   @Override
   public void a(PacketPlayInVehicleMove packetplayinvehiclemove) {
      PlayerConnectionUtils.a(packetplayinvehiclemove, this, this.b.x());
      if (b(packetplayinvehiclemove.a(), packetplayinvehiclemove.c(), packetplayinvehiclemove.d(), packetplayinvehiclemove.e(), packetplayinvehiclemove.f())) {
         this.b(IChatBaseComponent.c("multiplayer.disconnect.invalid_vehicle_movement"));
      } else {
         Entity entity = this.b.cS();
         if (entity != this.b && entity.cK() == this.b && entity == this.w) {
            WorldServer worldserver = this.b.x();
            double d0 = entity.dl();
            double d1 = entity.dn();
            double d2 = entity.dr();
            double d3 = a(packetplayinvehiclemove.a());
            double d4 = b(packetplayinvehiclemove.c());
            double d5 = a(packetplayinvehiclemove.d());
            float f = MathHelper.g(packetplayinvehiclemove.e());
            float f1 = MathHelper.g(packetplayinvehiclemove.f());
            double d6 = d3 - this.x;
            double d7 = d4 - this.y;
            double d8 = d5 - this.z;
            double d9 = entity.dj().g();
            double d10 = d6 * d6 + d7 * d7 + d8 * d8;
            this.allowedPlayerTicks = (int)((long)this.allowedPlayerTicks + (System.currentTimeMillis() / 50L - (long)this.lastTick));
            this.allowedPlayerTicks = Math.max(this.allowedPlayerTicks, 1);
            this.lastTick = (int)(System.currentTimeMillis() / 50L);
            ++this.K;
            int i = this.K - this.L;
            if (i > Math.max(this.allowedPlayerTicks, 5)) {
               c.debug(this.b.cu() + " is sending move packets too frequently (" + i + " packets since last tick)");
               i = 1;
            }

            if (d10 > 0.0) {
               --this.allowedPlayerTicks;
            } else {
               this.allowedPlayerTicks = 20;
            }

            double speed;
            if (this.b.fK().b) {
               speed = (double)(this.b.fK().f * 20.0F);
            } else {
               speed = (double)(this.b.fK().g * 10.0F);
            }

            speed *= 2.0;
            if (d10 - d9 > Math.max(100.0, Math.pow(SpigotConfig.movedTooQuicklyMultiplier * (double)((float)i) * speed, 2.0)) && !this.g()) {
               c.warn("{} (vehicle of {}) moved too quickly! {},{},{}", new Object[]{entity.Z().getString(), this.b.Z().getString(), d6, d7, d8});
               this.h.a(new PacketPlayOutVehicleMove(entity));
               return;
            }

            boolean flag = worldserver.a(entity, entity.cD().h(0.0625));
            d6 = d3 - this.A;
            d7 = d4 - this.B - 1.0E-6;
            d8 = d5 - this.C;
            boolean flag1 = entity.Q;
            entity.a(EnumMoveType.b, new Vec3D(d6, d7, d8));
            d6 = d3 - entity.dl();
            d7 = d4 - entity.dn();
            if (d7 > -0.5 || d7 < 0.5) {
               d7 = 0.0;
            }

            d8 = d5 - entity.dr();
            d10 = d6 * d6 + d7 * d7 + d8 * d8;
            boolean flag2 = false;
            if (d10 > SpigotConfig.movedWronglyThreshold) {
               flag2 = true;
               c.warn("{} (vehicle of {}) moved wrongly! {}", new Object[]{entity.Z().getString(), this.b.Z().getString(), Math.sqrt(d10)});
            }

            Location curPos = this.getCraftPlayer().getLocation();
            entity.a(d3, d4, d5, f, f1);
            this.b.a(d3, d4, d5, this.b.dw(), this.b.dy());
            boolean flag3 = worldserver.a(entity, entity.cD().h(0.0625));
            if (flag && (flag2 || !flag3)) {
               entity.a(d0, d1, d2, f, f1);
               this.b.a(d0, d1, d2, this.b.dw(), this.b.dy());
               this.h.a(new PacketPlayOutVehicleMove(entity));
               return;
            }

            Player player = this.getCraftPlayer();
            if (!this.hasMoved) {
               this.lastPosX = curPos.getX();
               this.lastPosY = curPos.getY();
               this.lastPosZ = curPos.getZ();
               this.lastYaw = curPos.getYaw();
               this.lastPitch = curPos.getPitch();
               this.hasMoved = true;
            }

            Location from = new Location(player.getWorld(), this.lastPosX, this.lastPosY, this.lastPosZ, this.lastYaw, this.lastPitch);
            Location to = player.getLocation().clone();
            to.setX(packetplayinvehiclemove.a());
            to.setY(packetplayinvehiclemove.c());
            to.setZ(packetplayinvehiclemove.d());
            to.setYaw(packetplayinvehiclemove.e());
            to.setPitch(packetplayinvehiclemove.f());
            double delta = Math.pow(this.lastPosX - to.getX(), 2.0) + Math.pow(this.lastPosY - to.getY(), 2.0) + Math.pow(this.lastPosZ - to.getZ(), 2.0);
            float deltaAngle = Math.abs(this.lastYaw - to.getYaw()) + Math.abs(this.lastPitch - to.getPitch());
            if ((delta > 0.00390625 || deltaAngle > 10.0F) && !this.b.eP()) {
               this.lastPosX = to.getX();
               this.lastPosY = to.getY();
               this.lastPosZ = to.getZ();
               this.lastYaw = to.getYaw();
               this.lastPitch = to.getPitch();
               Location oldTo = to.clone();
               PlayerMoveEvent event = new PlayerMoveEvent(player, from, to);
               this.cserver.getPluginManager().callEvent(event);
               if (event.isCancelled()) {
                  this.teleport(from);
                  return;
               }

               if (!oldTo.equals(event.getTo()) && !event.isCancelled()) {
                  this.b.getBukkitEntity().teleport(event.getTo(), TeleportCause.PLUGIN);
                  return;
               }

               if (!from.equals(this.getCraftPlayer().getLocation()) && this.justTeleported) {
                  this.justTeleported = false;
                  return;
               }
            }

            this.b.x().k().a(this.b);
            this.b.r(this.b.dl() - d0, this.b.dn() - d1, this.b.dr() - d2);
            this.I = d7 >= -0.03125 && !flag1 && !this.i.Z() && !entity.aP() && this.a(entity);
            this.A = entity.dl();
            this.B = entity.dn();
            this.C = entity.dr();
         }
      }
   }

   private boolean a(Entity entity) {
      return entity.H.a(entity.cD().g(0.0625).b(0.0, -0.55, 0.0)).allMatch(BlockBase.BlockData::h);
   }

   @Override
   public void a(PacketPlayInTeleportAccept packetplayinteleportaccept) {
      PlayerConnectionUtils.a(packetplayinteleportaccept, this, this.b.x());
      if (packetplayinteleportaccept.a() == this.E) {
         if (this.D == null) {
            this.b(IChatBaseComponent.c("multiplayer.disconnect.invalid_player_movement"));
            return;
         }

         this.b.a(this.D.c, this.D.d, this.D.e, this.b.dw(), this.b.dy());
         this.t = this.D.c;
         this.u = this.D.d;
         this.v = this.D.e;
         if (this.b.K()) {
            this.b.L();
         }

         this.D = null;
         this.b.x().k().a(this.b);
      }
   }

   @Override
   public void a(PacketPlayInRecipeDisplayed packetplayinrecipedisplayed) {
      PlayerConnectionUtils.a(packetplayinrecipedisplayed, this, this.b.x());
      Optional<? extends IRecipe<?>> optional = this.i.aE().a(packetplayinrecipedisplayed.a());
      RecipeBookServer recipebookserver = this.b.E();
      optional.ifPresent(recipebookserver::e);
   }

   @Override
   public void a(PacketPlayInRecipeSettings packetplayinrecipesettings) {
      PlayerConnectionUtils.a(packetplayinrecipesettings, this, this.b.x());
      this.b.E().a(packetplayinrecipesettings.a(), packetplayinrecipesettings.c(), packetplayinrecipesettings.d());
   }

   @Override
   public void a(PacketPlayInAdvancements packetplayinadvancements) {
      PlayerConnectionUtils.a(packetplayinadvancements, this, this.b.x());
      if (packetplayinadvancements.c() == PacketPlayInAdvancements.Status.a) {
         MinecraftKey minecraftkey = packetplayinadvancements.d();
         Advancement advancement = this.i.az().a(minecraftkey);
         if (advancement != null) {
            this.b.M().a(advancement);
         }
      }
   }

   @Override
   public void a(PacketPlayInTabComplete packetplayintabcomplete) {
      PlayerConnectionUtils.a(packetplayintabcomplete, this, this.b.x());
      if (this.o.addAndGet(1) > 500 && !this.i.ac().f(this.b.fI())) {
         this.b(IChatBaseComponent.c("disconnect.spam"));
      } else {
         StringReader stringreader = new StringReader(packetplayintabcomplete.c());
         if (stringreader.canRead() && stringreader.peek() == '/') {
            stringreader.skip();
         }

         ParseResults<CommandListenerWrapper> parseresults = this.i.aC().a().parse(stringreader, this.b.cZ());
         this.i.aC().a().getCompletionSuggestions(parseresults).thenAccept(suggestions -> {
            if (!suggestions.isEmpty()) {
               this.h.a(new PacketPlayOutTabComplete(packetplayintabcomplete.a(), suggestions));
            }
         });
      }
   }

   @Override
   public void a(PacketPlayInSetCommandBlock packetplayinsetcommandblock) {
      PlayerConnectionUtils.a(packetplayinsetcommandblock, this, this.b.x());
      if (!this.i.o()) {
         this.b.a(IChatBaseComponent.c("advMode.notEnabled"));
      } else if (!this.b.gg()) {
         this.b.a(IChatBaseComponent.c("advMode.notAllowed"));
      } else {
         CommandBlockListenerAbstract commandblocklistenerabstract = null;
         TileEntityCommand tileentitycommand = null;
         BlockPosition blockposition = packetplayinsetcommandblock.a();
         TileEntity tileentity = this.b.H.c_(blockposition);
         if (tileentity instanceof TileEntityCommand) {
            tileentitycommand = (TileEntityCommand)tileentity;
            commandblocklistenerabstract = tileentitycommand.c();
         }

         String s = packetplayinsetcommandblock.c();
         boolean flag = packetplayinsetcommandblock.d();
         if (commandblocklistenerabstract != null) {
            TileEntityCommand.Type tileentitycommand_type = tileentitycommand.v();
            IBlockData iblockdata = this.b.H.a_(blockposition);
            EnumDirection enumdirection = iblockdata.c(BlockCommand.a);

            IBlockData iblockdata2 = (switch(packetplayinsetcommandblock.g()) {
               case a -> Blocks.kE.o();
               case b -> Blocks.kD.o();
               default -> Blocks.fM.o();
            }).a(BlockCommand.a, enumdirection).a(BlockCommand.b, Boolean.valueOf(packetplayinsetcommandblock.e()));
            if (iblockdata2 != iblockdata) {
               this.b.H.a(blockposition, iblockdata2, 2);
               tileentity.b(iblockdata2);
               this.b.H.l(blockposition).a(tileentity);
            }

            commandblocklistenerabstract.a(s);
            commandblocklistenerabstract.a(flag);
            if (!flag) {
               commandblocklistenerabstract.c(null);
            }

            tileentitycommand.b(packetplayinsetcommandblock.f());
            if (tileentitycommand_type != packetplayinsetcommandblock.g()) {
               tileentitycommand.g();
            }

            commandblocklistenerabstract.f();
            if (!UtilColor.b(s)) {
               this.b.a(IChatBaseComponent.a("advMode.setCommand.success", s));
            }
         }
      }
   }

   @Override
   public void a(PacketPlayInSetCommandMinecart packetplayinsetcommandminecart) {
      PlayerConnectionUtils.a(packetplayinsetcommandminecart, this, this.b.x());
      if (!this.i.o()) {
         this.b.a(IChatBaseComponent.c("advMode.notEnabled"));
      } else if (!this.b.gg()) {
         this.b.a(IChatBaseComponent.c("advMode.notAllowed"));
      } else {
         CommandBlockListenerAbstract commandblocklistenerabstract = packetplayinsetcommandminecart.a(this.b.H);
         if (commandblocklistenerabstract != null) {
            commandblocklistenerabstract.a(packetplayinsetcommandminecart.a());
            commandblocklistenerabstract.a(packetplayinsetcommandminecart.c());
            if (!packetplayinsetcommandminecart.c()) {
               commandblocklistenerabstract.c(null);
            }

            commandblocklistenerabstract.f();
            this.b.a(IChatBaseComponent.a("advMode.setCommand.success", packetplayinsetcommandminecart.a()));
         }
      }
   }

   @Override
   public void a(PacketPlayInPickItem packetplayinpickitem) {
      PlayerConnectionUtils.a(packetplayinpickitem, this, this.b.x());
      this.b.fJ().c(packetplayinpickitem.a());
      this.b.b.a(new PacketPlayOutSetSlot(-2, 0, this.b.fJ().l, this.b.fJ().a(this.b.fJ().l)));
      this.b.b.a(new PacketPlayOutSetSlot(-2, 0, packetplayinpickitem.a(), this.b.fJ().a(packetplayinpickitem.a())));
      this.b.b.a(new PacketPlayOutHeldItemSlot(this.b.fJ().l));
   }

   @Override
   public void a(PacketPlayInItemName packetplayinitemname) {
      PlayerConnectionUtils.a(packetplayinitemname, this, this.b.x());
      Container container = this.b.bP;
      if (container instanceof ContainerAnvil containeranvil) {
         if (!containeranvil.a(this.b)) {
            c.debug("Player {} interacted with invalid menu {}", this.b, containeranvil);
            return;
         }

         String s = SharedConstants.a(packetplayinitemname.a());
         if (s.length() <= 50) {
            containeranvil.a(s);
         }
      }
   }

   @Override
   public void a(PacketPlayInBeacon packetplayinbeacon) {
      PlayerConnectionUtils.a(packetplayinbeacon, this, this.b.x());
      Container container = this.b.bP;
      if (container instanceof ContainerBeacon containerbeacon) {
         if (!this.b.bP.a(this.b)) {
            c.debug("Player {} interacted with invalid menu {}", this.b, this.b.bP);
            return;
         }

         containerbeacon.a(packetplayinbeacon.a(), packetplayinbeacon.c());
      }
   }

   @Override
   public void a(PacketPlayInStruct packetplayinstruct) {
      PlayerConnectionUtils.a(packetplayinstruct, this, this.b.x());
      if (this.b.gg()) {
         BlockPosition blockposition = packetplayinstruct.a();
         IBlockData iblockdata = this.b.H.a_(blockposition);
         TileEntity tileentity = this.b.H.c_(blockposition);
         if (tileentity instanceof TileEntityStructure tileentitystructure) {
            tileentitystructure.a(packetplayinstruct.d());
            tileentitystructure.a(packetplayinstruct.e());
            tileentitystructure.a(packetplayinstruct.f());
            tileentitystructure.a(packetplayinstruct.g());
            tileentitystructure.a(packetplayinstruct.h());
            tileentitystructure.a(packetplayinstruct.i());
            tileentitystructure.b(packetplayinstruct.j());
            tileentitystructure.a(packetplayinstruct.k());
            tileentitystructure.d(packetplayinstruct.l());
            tileentitystructure.e(packetplayinstruct.m());
            tileentitystructure.a(packetplayinstruct.n());
            tileentitystructure.a(packetplayinstruct.o());
            if (tileentitystructure.g()) {
               String s = tileentitystructure.d();
               if (packetplayinstruct.c() == TileEntityStructure.UpdateType.b) {
                  if (tileentitystructure.D()) {
                     this.b.a(IChatBaseComponent.a("structure_block.save_success", s), false);
                  } else {
                     this.b.a(IChatBaseComponent.a("structure_block.save_failure", s), false);
                  }
               } else if (packetplayinstruct.c() == TileEntityStructure.UpdateType.c) {
                  if (!tileentitystructure.F()) {
                     this.b.a(IChatBaseComponent.a("structure_block.load_not_found", s), false);
                  } else if (tileentitystructure.a(this.b.x())) {
                     this.b.a(IChatBaseComponent.a("structure_block.load_success", s), false);
                  } else {
                     this.b.a(IChatBaseComponent.a("structure_block.load_prepare", s), false);
                  }
               } else if (packetplayinstruct.c() == TileEntityStructure.UpdateType.d) {
                  if (tileentitystructure.C()) {
                     this.b.a(IChatBaseComponent.a("structure_block.size_success", s), false);
                  } else {
                     this.b.a(IChatBaseComponent.c("structure_block.size_failure"), false);
                  }
               }
            } else {
               this.b.a(IChatBaseComponent.a("structure_block.invalid_structure_name", packetplayinstruct.e()), false);
            }

            tileentitystructure.e();
            this.b.H.a(blockposition, iblockdata, iblockdata, 3);
         }
      }
   }

   @Override
   public void a(PacketPlayInSetJigsaw packetplayinsetjigsaw) {
      PlayerConnectionUtils.a(packetplayinsetjigsaw, this, this.b.x());
      if (this.b.gg()) {
         BlockPosition blockposition = packetplayinsetjigsaw.a();
         IBlockData iblockdata = this.b.H.a_(blockposition);
         TileEntity tileentity = this.b.H.c_(blockposition);
         if (tileentity instanceof TileEntityJigsaw tileentityjigsaw) {
            tileentityjigsaw.a(packetplayinsetjigsaw.c());
            tileentityjigsaw.b(packetplayinsetjigsaw.d());
            tileentityjigsaw.a(ResourceKey.a(Registries.aA, packetplayinsetjigsaw.e()));
            tileentityjigsaw.a(packetplayinsetjigsaw.f());
            tileentityjigsaw.a(packetplayinsetjigsaw.g());
            tileentityjigsaw.e();
            this.b.H.a(blockposition, iblockdata, iblockdata, 3);
         }
      }
   }

   @Override
   public void a(PacketPlayInJigsawGenerate packetplayinjigsawgenerate) {
      PlayerConnectionUtils.a(packetplayinjigsawgenerate, this, this.b.x());
      if (this.b.gg()) {
         BlockPosition blockposition = packetplayinjigsawgenerate.a();
         TileEntity tileentity = this.b.H.c_(blockposition);
         if (tileentity instanceof TileEntityJigsaw tileentityjigsaw) {
            tileentityjigsaw.a(this.b.x(), packetplayinjigsawgenerate.c(), packetplayinjigsawgenerate.d());
         }
      }
   }

   @Override
   public void a(PacketPlayInTrSel packetplayintrsel) {
      PlayerConnectionUtils.a(packetplayintrsel, this, this.b.x());
      int i = packetplayintrsel.a();
      Container container = this.b.bP;
      if (container instanceof ContainerMerchant containermerchant) {
         TradeSelectEvent tradeSelectEvent = CraftEventFactory.callTradeSelectEvent(this.b, i, containermerchant);
         if (tradeSelectEvent.isCancelled()) {
            this.b.getBukkitEntity().updateInventory();
            return;
         }

         if (!containermerchant.a(this.b)) {
            c.debug("Player {} interacted with invalid menu {}", this.b, containermerchant);
            return;
         }

         containermerchant.e(i);
         containermerchant.h(i);
      }
   }

   @Override
   public void a(PacketPlayInBEdit packetplayinbedit) {
      if (this.lastBookTick + 20 > MinecraftServer.currentTick) {
         this.disconnect("Book edited too quickly!");
      } else {
         this.lastBookTick = MinecraftServer.currentTick;
         int i = packetplayinbedit.d();
         if (PlayerInventory.d(i) || i == 40) {
            List<String> list = Lists.newArrayList();
            Optional<String> optional = packetplayinbedit.c();
            optional.ifPresent(list::add);
            Stream<String> stream = packetplayinbedit.a().stream().limit(100L);
            stream.forEach(list::add);
            Consumer<List<FilteredText>> consumer = optional.isPresent()
               ? list1 -> this.a(list1.get(0), list1.subList(1, list1.size()), i)
               : list1 -> this.a(list1, i);
            this.a(list).thenAcceptAsync(consumer, this.i);
         }
      }
   }

   private void a(List<FilteredText> list, int i) {
      ItemStack itemstack = this.b.fJ().a(i);
      if (itemstack.a(Items.tc)) {
         this.updateBookPages(list, UnaryOperator.identity(), itemstack.o(), i, itemstack);
      }
   }

   private void a(FilteredText filteredtext, List<FilteredText> list, int i) {
      ItemStack itemstack = this.b.fJ().a(i);
      if (itemstack.a(Items.tc)) {
         ItemStack itemstack1 = new ItemStack(Items.td);
         NBTTagCompound nbttagcompound = itemstack.u();
         if (nbttagcompound != null) {
            itemstack1.c(nbttagcompound.h());
         }

         itemstack1.a("author", NBTTagString.a(this.b.Z().getString()));
         if (this.b.U()) {
            itemstack1.a("title", NBTTagString.a(filteredtext.b()));
         } else {
            itemstack1.a("filtered_title", NBTTagString.a(filteredtext.b()));
            itemstack1.a("title", NBTTagString.a(filteredtext.d()));
         }

         this.updateBookPages(list, s -> IChatBaseComponent.ChatSerializer.a(IChatBaseComponent.b(s)), itemstack1, i, itemstack);
         this.b.fJ().a(i, itemstack);
      }
   }

   private void updateBookPages(List<FilteredText> list, UnaryOperator<String> unaryoperator, ItemStack itemstack, int slot, ItemStack handItem) {
      NBTTagList nbttaglist = new NBTTagList();
      if (this.b.U()) {
         Stream<NBTTagString> stream = list.stream().map(filteredtextx -> NBTTagString.a(unaryoperator.apply(filteredtextx.b())));
         stream.forEach(nbttaglist::add);
      } else {
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         int i = 0;

         for(int j = list.size(); i < j; ++i) {
            FilteredText filteredtext = list.get(i);
            String s = filteredtext.d();
            nbttaglist.add(NBTTagString.a(unaryoperator.apply(s)));
            if (filteredtext.c()) {
               nbttagcompound.a(String.valueOf(i), unaryoperator.apply(filteredtext.b()));
            }
         }

         if (!nbttagcompound.g()) {
            itemstack.a("filtered_pages", nbttagcompound);
         }
      }

      itemstack.a("pages", nbttaglist);
      CraftEventFactory.handleEditBookEvent(this.b, slot, handItem, itemstack);
   }

   @Override
   public void a(PacketPlayInEntityNBTQuery packetplayinentitynbtquery) {
      PlayerConnectionUtils.a(packetplayinentitynbtquery, this, this.b.x());
      if (this.b.k(2)) {
         Entity entity = this.b.x().a(packetplayinentitynbtquery.c());
         if (entity != null) {
            NBTTagCompound nbttagcompound = entity.f(new NBTTagCompound());
            this.b.b.a(new PacketPlayOutNBTQuery(packetplayinentitynbtquery.a(), nbttagcompound));
         }
      }
   }

   @Override
   public void a(PacketPlayInTileNBTQuery packetplayintilenbtquery) {
      PlayerConnectionUtils.a(packetplayintilenbtquery, this, this.b.x());
      if (this.b.k(2)) {
         TileEntity tileentity = this.b.x().c_(packetplayintilenbtquery.c());
         NBTTagCompound nbttagcompound = tileentity != null ? tileentity.o() : null;
         this.b.b.a(new PacketPlayOutNBTQuery(packetplayintilenbtquery.a(), nbttagcompound));
      }
   }

   @Override
   public void a(PacketPlayInFlying packetplayinflying) {
      PlayerConnectionUtils.a(packetplayinflying, this, this.b.x());
      if (b(packetplayinflying.a(0.0), packetplayinflying.b(0.0), packetplayinflying.c(0.0), packetplayinflying.a(0.0F), packetplayinflying.b(0.0F))) {
         this.b(IChatBaseComponent.c("multiplayer.disconnect.invalid_player_movement"));
      } else {
         WorldServer worldserver = this.b.x();
         if (!this.b.f && !this.b.eP()) {
            if (this.j == 0) {
               this.d();
            }

            if (this.D != null) {
               if (this.j - this.F > 20) {
                  this.F = this.j;
                  this.a(this.D.c, this.D.d, this.D.e, this.b.dw(), this.b.dy());
               }

               this.allowedPlayerTicks = 20;
            } else {
               this.F = this.j;
               double d0 = a(packetplayinflying.a(this.b.dl()));
               double d1 = b(packetplayinflying.b(this.b.dn()));
               double d2 = a(packetplayinflying.c(this.b.dr()));
               float f = MathHelper.g(packetplayinflying.a(this.b.dw()));
               float f1 = MathHelper.g(packetplayinflying.b(this.b.dy()));
               if (this.b.bL()) {
                  this.b.a(this.b.dl(), this.b.dn(), this.b.dr(), f, f1);
                  this.b.x().k().a(this.b);
                  this.allowedPlayerTicks = 20;
               } else {
                  double prevX = this.b.dl();
                  double prevY = this.b.dn();
                  double prevZ = this.b.dr();
                  float prevYaw = this.b.dw();
                  float prevPitch = this.b.dy();
                  double d3 = this.b.dl();
                  double d4 = this.b.dn();
                  double d5 = this.b.dr();
                  double d6 = this.b.dn();
                  double d7 = d0 - this.q;
                  double d8 = d1 - this.r;
                  double d9 = d2 - this.s;
                  double d10 = this.b.dj().g();
                  double d11 = d7 * d7 + d8 * d8 + d9 * d9;
                  if (this.b.fu()) {
                     if (d11 > 1.0) {
                        this.a(this.b.dl(), this.b.dn(), this.b.dr(), f, f1);
                     }
                  } else {
                     ++this.K;
                     int i = this.K - this.L;
                     this.allowedPlayerTicks = (int)((long)this.allowedPlayerTicks + (System.currentTimeMillis() / 50L - (long)this.lastTick));
                     this.allowedPlayerTicks = Math.max(this.allowedPlayerTicks, 1);
                     this.lastTick = (int)(System.currentTimeMillis() / 50L);
                     if (i > Math.max(this.allowedPlayerTicks, 5)) {
                        c.debug("{} is sending move packets too frequently ({} packets since last tick)", this.b.Z().getString(), i);
                        i = 1;
                     }

                     if (!packetplayinflying.h && !(d11 > 0.0)) {
                        this.allowedPlayerTicks = 20;
                     } else {
                        --this.allowedPlayerTicks;
                     }

                     double speed;
                     if (this.b.fK().b) {
                        speed = (double)(this.b.fK().f * 20.0F);
                     } else {
                        speed = (double)(this.b.fK().g * 10.0F);
                     }

                     if (!this.b.K() && (!this.b.x().W().b(GameRules.s) || !this.b.fn())) {
                        float f2 = this.b.fn() ? 300.0F : 100.0F;
                        if (d11 - d10 > Math.max((double)f2, Math.pow(SpigotConfig.movedTooQuicklyMultiplier * (double)((float)i) * speed, 2.0)) && !this.g()) {
                           c.warn("{} moved too quickly! {},{},{}", new Object[]{this.b.Z().getString(), d7, d8, d9});
                           this.a(this.b.dl(), this.b.dn(), this.b.dr(), this.b.dw(), this.b.dy());
                           return;
                        }
                     }

                     AxisAlignedBB axisalignedbb = this.b.cD();
                     d7 = d0 - this.t;
                     d8 = d1 - this.u;
                     d9 = d2 - this.v;
                     boolean flag = d8 > 0.0;
                     if (this.b.ax() && !packetplayinflying.a() && flag) {
                        this.b.eS();
                     }

                     boolean flag1 = this.b.Q;
                     this.b.a(EnumMoveType.b, new Vec3D(d7, d8, d9));
                     this.b.N = packetplayinflying.a();
                     d7 = d0 - this.b.dl();
                     d8 = d1 - this.b.dn();
                     if (d8 > -0.5 || d8 < 0.5) {
                        d8 = 0.0;
                     }

                     d9 = d2 - this.b.dr();
                     d11 = d7 * d7 + d8 * d8 + d9 * d9;
                     boolean flag2 = false;
                     if (!this.b.K() && d11 > SpigotConfig.movedWronglyThreshold && !this.b.fu() && !this.b.d.e() && this.b.d.b() != EnumGamemode.d) {
                        flag2 = true;
                        c.warn("{} moved wrongly!", this.b.Z().getString());
                     }

                     this.b.a(d0, d1, d2, f, f1);
                     if (this.b.ae || this.b.fu() || (!flag2 || !worldserver.a(this.b, axisalignedbb)) && !this.a(worldserver, axisalignedbb)) {
                        this.b.a(prevX, prevY, prevZ, prevYaw, prevPitch);
                        Player player = this.getCraftPlayer();
                        Location from = new Location(player.getWorld(), this.lastPosX, this.lastPosY, this.lastPosZ, this.lastYaw, this.lastPitch);
                        Location to = player.getLocation().clone();
                        if (packetplayinflying.g) {
                           to.setX(packetplayinflying.a);
                           to.setY(packetplayinflying.b);
                           to.setZ(packetplayinflying.c);
                        }

                        if (packetplayinflying.h) {
                           to.setYaw(packetplayinflying.d);
                           to.setPitch(packetplayinflying.e);
                        }

                        double delta = Math.pow(this.lastPosX - to.getX(), 2.0)
                           + Math.pow(this.lastPosY - to.getY(), 2.0)
                           + Math.pow(this.lastPosZ - to.getZ(), 2.0);
                        float deltaAngle = Math.abs(this.lastYaw - to.getYaw()) + Math.abs(this.lastPitch - to.getPitch());
                        if ((delta > 0.00390625 || deltaAngle > 10.0F) && !this.b.eP()) {
                           this.lastPosX = to.getX();
                           this.lastPosY = to.getY();
                           this.lastPosZ = to.getZ();
                           this.lastYaw = to.getYaw();
                           this.lastPitch = to.getPitch();
                           if (from.getX() != Double.MAX_VALUE) {
                              Location oldTo = to.clone();
                              PlayerMoveEvent event = new PlayerMoveEvent(player, from, to);
                              this.cserver.getPluginManager().callEvent(event);
                              if (event.isCancelled()) {
                                 this.teleport(from);
                                 return;
                              }

                              if (!oldTo.equals(event.getTo()) && !event.isCancelled()) {
                                 this.b.getBukkitEntity().teleport(event.getTo(), TeleportCause.PLUGIN);
                                 return;
                              }

                              if (!from.equals(this.getCraftPlayer().getLocation()) && this.justTeleported) {
                                 this.justTeleported = false;
                                 return;
                              }
                           }
                        }

                        this.b.a(d0, d1, d2, f, f1);
                        this.G = d8 >= -0.03125
                           && !flag1
                           && this.b.d.b() != EnumGamemode.d
                           && !this.i.Z()
                           && !this.b.fK().c
                           && !this.b.a(MobEffects.y)
                           && !this.b.fn()
                           && !this.b.fa()
                           && this.a(this.b);
                        this.b.x().k().a(this.b);
                        this.b.a(this.b.dn() - d6, packetplayinflying.a());
                        this.b.c(packetplayinflying.a());
                        if (flag) {
                           this.b.n();
                        }

                        this.b.r(this.b.dl() - d3, this.b.dn() - d4, this.b.dr() - d5);
                        this.t = this.b.dl();
                        this.u = this.b.dn();
                        this.v = this.b.dr();
                     } else {
                        this.internalTeleport(d3, d4, d5, f, f1, Collections.emptySet());
                        this.b.a(this.b.dn() - d6, packetplayinflying.a());
                     }
                  }
               }
            }
         }
      }
   }

   private boolean a(IWorldReader iworldreader, AxisAlignedBB axisalignedbb) {
      Iterable<VoxelShape> iterable = iworldreader.c(this.b, this.b.cD().h(1.0E-5F));
      VoxelShape voxelshape = VoxelShapes.a(axisalignedbb.h(1.0E-5F));

      for(VoxelShape voxelshape1 : iterable) {
         if (!VoxelShapes.c(voxelshape1, voxelshape, OperatorBoolean.i)) {
            return true;
         }
      }

      return false;
   }

   public void a(double d0, double d1, double d2, float f, float f1) {
      this.teleport(d0, d1, d2, f, f1, TeleportCause.UNKNOWN);
   }

   public void teleport(double d0, double d1, double d2, float f, float f1, TeleportCause cause) {
      this.teleport(d0, d1, d2, f, f1, Collections.emptySet(), cause);
   }

   public void a(double d0, double d1, double d2, float f, float f1, Set<RelativeMovement> set) {
      this.teleport(d0, d1, d2, f, f1, set, TeleportCause.UNKNOWN);
   }

   public boolean teleport(double d0, double d1, double d2, float f, float f1, Set<RelativeMovement> set, TeleportCause cause) {
      Player player = this.getCraftPlayer();
      Location from = player.getLocation();
      Location to = new Location(this.getCraftPlayer().getWorld(), d0, d1, d2, f, f1);
      if (from.equals(to)) {
         this.internalTeleport(d0, d1, d2, f, f1, set);
         return false;
      } else {
         PlayerTeleportEvent event = new PlayerTeleportEvent(player, from.clone(), to.clone(), cause);
         this.cserver.getPluginManager().callEvent(event);
         if (event.isCancelled() || !to.equals(event.getTo())) {
            set.clear();
            to = event.isCancelled() ? event.getFrom() : event.getTo();
            d0 = to.getX();
            d1 = to.getY();
            d2 = to.getZ();
            f = to.getYaw();
            f1 = to.getPitch();
         }

         this.internalTeleport(d0, d1, d2, f, f1, set);
         return event.isCancelled();
      }
   }

   public void teleport(Location dest) {
      this.internalTeleport(dest.getX(), dest.getY(), dest.getZ(), dest.getYaw(), dest.getPitch(), Collections.emptySet());
   }

   private void internalTeleport(double d0, double d1, double d2, float f, float f1, Set<RelativeMovement> set) {
      if (Float.isNaN(f)) {
         f = 0.0F;
      }

      if (Float.isNaN(f1)) {
         f1 = 0.0F;
      }

      this.justTeleported = true;
      double d3 = set.contains(RelativeMovement.a) ? this.b.dl() : 0.0;
      double d4 = set.contains(RelativeMovement.b) ? this.b.dn() : 0.0;
      double d5 = set.contains(RelativeMovement.c) ? this.b.dr() : 0.0;
      float f2 = set.contains(RelativeMovement.d) ? this.b.dw() : 0.0F;
      float f3 = set.contains(RelativeMovement.e) ? this.b.dy() : 0.0F;
      this.D = new Vec3D(d0, d1, d2);
      if (++this.E == Integer.MAX_VALUE) {
         this.E = 0;
      }

      this.lastPosX = this.D.c;
      this.lastPosY = this.D.d;
      this.lastPosZ = this.D.e;
      this.lastYaw = f;
      this.lastPitch = f1;
      this.F = this.j;
      this.b.a(d0, d1, d2, f, f1);
      this.b.b.a(new PacketPlayOutPosition(d0 - d3, d1 - d4, d2 - d5, f - f2, f1 - f3, set, this.E));
   }

   @Override
   public void a(PacketPlayInBlockDig packetplayinblockdig) {
      PlayerConnectionUtils.a(packetplayinblockdig, this, this.b.x());
      if (!this.b.eP()) {
         BlockPosition blockposition = packetplayinblockdig.a();
         this.b.C();
         PacketPlayInBlockDig.EnumPlayerDigType packetplayinblockdig_enumplayerdigtype = packetplayinblockdig.d();
         switch(packetplayinblockdig_enumplayerdigtype) {
            case a:
            case b:
            case c:
               this.b.d.a(blockposition, packetplayinblockdig_enumplayerdigtype, packetplayinblockdig.c(), this.b.H.ai(), packetplayinblockdig.e());
               this.b.b.a(packetplayinblockdig.e());
               return;
            case d:
               if (!this.b.F_()) {
                  this.b.a(true);
               }

               return;
            case e:
               if (!this.b.F_()) {
                  if (this.lastDropTick != MinecraftServer.currentTick) {
                     this.dropCount = 0;
                     this.lastDropTick = MinecraftServer.currentTick;
                  } else {
                     ++this.dropCount;
                     if (this.dropCount >= 20) {
                        c.warn(this.b.cu() + " dropped their items too quickly!");
                        this.disconnect("You dropped your items too quickly (Hacking?)");
                        return;
                     }
                  }

                  this.b.a(false);
               }

               return;
            case f:
               this.b.fj();
               return;
            case g:
               if (!this.b.F_()) {
                  ItemStack itemstack = this.b.b(EnumHand.b);
                  CraftItemStack mainHand = CraftItemStack.asCraftMirror(itemstack);
                  CraftItemStack offHand = CraftItemStack.asCraftMirror(this.b.b(EnumHand.a));
                  PlayerSwapHandItemsEvent swapItemsEvent = new PlayerSwapHandItemsEvent(this.getCraftPlayer(), mainHand.clone(), offHand.clone());
                  this.cserver.getPluginManager().callEvent(swapItemsEvent);
                  if (swapItemsEvent.isCancelled()) {
                     return;
                  }

                  if (swapItemsEvent.getOffHandItem().equals(offHand)) {
                     this.b.a(EnumHand.b, this.b.b(EnumHand.a));
                  } else {
                     this.b.a(EnumHand.b, CraftItemStack.asNMSCopy(swapItemsEvent.getOffHandItem()));
                  }

                  if (swapItemsEvent.getMainHandItem().equals(mainHand)) {
                     this.b.a(EnumHand.a, itemstack);
                  } else {
                     this.b.a(EnumHand.a, CraftItemStack.asNMSCopy(swapItemsEvent.getMainHandItem()));
                  }

                  this.b.fk();
               }

               return;
            default:
               throw new IllegalArgumentException("Invalid player action");
         }
      }
   }

   private static boolean a(EntityPlayer entityplayer, ItemStack itemstack) {
      if (itemstack.b()) {
         return false;
      } else {
         Item item = itemstack.c();
         return (item instanceof ItemBlock || item instanceof ItemBucket) && !entityplayer.ge().a(item);
      }
   }

   private boolean checkLimit(long timestamp) {
      if (this.lastLimitedPacket != -1L && timestamp - this.lastLimitedPacket < 30L && this.limitedPackets++ >= 4) {
         return false;
      } else if (this.lastLimitedPacket != -1L && timestamp - this.lastLimitedPacket < 30L) {
         return true;
      } else {
         this.lastLimitedPacket = timestamp;
         this.limitedPackets = 0;
         return true;
      }
   }

   @Override
   public void a(PacketPlayInUseItem packetplayinuseitem) {
      PlayerConnectionUtils.a(packetplayinuseitem, this, this.b.x());
      if (!this.b.eP()) {
         if (this.checkLimit(packetplayinuseitem.timestamp)) {
            this.b.b.a(packetplayinuseitem.d());
            WorldServer worldserver = this.b.x();
            EnumHand enumhand = packetplayinuseitem.a();
            ItemStack itemstack = this.b.b(enumhand);
            if (itemstack.a(worldserver.G())) {
               MovingObjectPositionBlock movingobjectpositionblock = packetplayinuseitem.c();
               Vec3D vec3d = movingobjectpositionblock.e();
               BlockPosition blockposition = movingobjectpositionblock.a();
               Vec3D vec3d1 = Vec3D.b(blockposition);
               if (this.b.bk().g(vec3d1) <= a) {
                  Vec3D vec3d2 = vec3d.d(vec3d1);
                  double d0 = 1.0000001;
                  if (Math.abs(vec3d2.a()) < 1.0000001 && Math.abs(vec3d2.b()) < 1.0000001 && Math.abs(vec3d2.c()) < 1.0000001) {
                     EnumDirection enumdirection = movingobjectpositionblock.b();
                     this.b.C();
                     int i = this.b.H.ai();
                     if (blockposition.v() < i) {
                        if (this.D == null
                           && this.b.i((double)blockposition.u() + 0.5, (double)blockposition.v() + 0.5, (double)blockposition.w() + 0.5) < 64.0
                           && worldserver.a(this.b, blockposition)) {
                           this.b.fk();
                           EnumInteractionResult enuminteractionresult = this.b.d.a(this.b, worldserver, itemstack, enumhand, movingobjectpositionblock);
                           if (enumdirection == EnumDirection.b && !enuminteractionresult.a() && blockposition.v() >= i - 1 && a(this.b, itemstack)) {
                              IChatMutableComponent ichatmutablecomponent = IChatBaseComponent.a("build.tooHigh", i - 1).a(EnumChatFormat.m);
                              this.b.b(ichatmutablecomponent, true);
                           } else if (enuminteractionresult.b()) {
                              this.b.a(enumhand, true);
                           }
                        }
                     } else {
                        IChatMutableComponent ichatmutablecomponent1 = IChatBaseComponent.a("build.tooHigh", i - 1).a(EnumChatFormat.m);
                        this.b.b(ichatmutablecomponent1, true);
                     }

                     this.b.b.a(new PacketPlayOutBlockChange(worldserver, blockposition));
                     this.b.b.a(new PacketPlayOutBlockChange(worldserver, blockposition.a(enumdirection)));
                  } else {
                     c.warn(
                        "Rejecting UseItemOnPacket from {}: Location {} too far away from hit block {}.",
                        new Object[]{this.b.fI().getName(), vec3d, blockposition}
                     );
                  }
               }
            }
         }
      }
   }

   @Override
   public void a(PacketPlayInBlockPlace packetplayinblockplace) {
      PlayerConnectionUtils.a(packetplayinblockplace, this, this.b.x());
      if (!this.b.eP()) {
         if (this.checkLimit(packetplayinblockplace.timestamp)) {
            this.a(packetplayinblockplace.c());
            WorldServer worldserver = this.b.x();
            EnumHand enumhand = packetplayinblockplace.a();
            ItemStack itemstack = this.b.b(enumhand);
            this.b.C();
            if (!itemstack.b() && itemstack.a(worldserver.G())) {
               float f1 = this.b.dy();
               float f2 = this.b.dw();
               double d0 = this.b.dl();
               double d1 = this.b.dn() + (double)this.b.cE();
               double d2 = this.b.dr();
               Vec3D vec3d = new Vec3D(d0, d1, d2);
               float f3 = MathHelper.b(-f2 * (float) (Math.PI / 180.0) - (float) Math.PI);
               float f4 = MathHelper.a(-f2 * (float) (Math.PI / 180.0) - (float) Math.PI);
               float f5 = -MathHelper.b(-f1 * (float) (Math.PI / 180.0));
               float f6 = MathHelper.a(-f1 * (float) (Math.PI / 180.0));
               float f7 = f4 * f5;
               float f8 = f3 * f5;
               double d3 = this.b.d.b() == EnumGamemode.b ? 5.0 : 4.5;
               Vec3D vec3d1 = vec3d.b((double)f7 * d3, (double)f6 * d3, (double)f8 * d3);
               MovingObjectPosition movingobjectposition = this.b
                  .H
                  .a(new RayTrace(vec3d, vec3d1, RayTrace.BlockCollisionOption.b, RayTrace.FluidCollisionOption.a, this.b));
               boolean cancelled;
               if (movingobjectposition != null && movingobjectposition.c() == MovingObjectPosition.EnumMovingObjectType.b) {
                  MovingObjectPositionBlock movingobjectpositionblock = (MovingObjectPositionBlock)movingobjectposition;
                  if (this.b.d.firedInteract
                     && this.b.d.interactPosition.equals(movingobjectpositionblock.a())
                     && this.b.d.interactHand == enumhand
                     && ItemStack.a(this.b.d.interactItemStack, itemstack)) {
                     cancelled = this.b.d.interactResult;
                  } else {
                     PlayerInteractEvent event = CraftEventFactory.callPlayerInteractEvent(
                        this.b, Action.RIGHT_CLICK_BLOCK, movingobjectpositionblock.a(), movingobjectpositionblock.b(), itemstack, true, enumhand
                     );
                     cancelled = event.useItemInHand() == Result.DENY;
                  }

                  this.b.d.firedInteract = false;
               } else {
                  PlayerInteractEvent event = CraftEventFactory.callPlayerInteractEvent(this.b, Action.RIGHT_CLICK_AIR, itemstack, enumhand);
                  cancelled = event.useItemInHand() == Result.DENY;
               }

               if (cancelled) {
                  this.b.getBukkitEntity().updateInventory();
                  return;
               }

               itemstack = this.b.b(enumhand);
               if (itemstack.b()) {
                  return;
               }

               EnumInteractionResult enuminteractionresult = this.b.d.a(this.b, worldserver, itemstack, enumhand);
               if (enuminteractionresult.b()) {
                  this.b.a(enumhand, true);
               }
            }
         }
      }
   }

   @Override
   public void a(PacketPlayInSpectate packetplayinspectate) {
      PlayerConnectionUtils.a(packetplayinspectate, this, this.b.x());
      if (this.b.F_()) {
         for(WorldServer worldserver : this.i.F()) {
            Entity entity = packetplayinspectate.a(worldserver);
            if (entity != null) {
               this.b.teleportTo(worldserver, entity.dl(), entity.dn(), entity.dr(), entity.dw(), entity.dy(), TeleportCause.SPECTATE);
               return;
            }
         }
      }
   }

   @Override
   public void a(PacketPlayInResourcePackStatus packetplayinresourcepackstatus) {
      PlayerConnectionUtils.a(packetplayinresourcepackstatus, this, this.b.x());
      if (packetplayinresourcepackstatus.a() == PacketPlayInResourcePackStatus.EnumResourcePackStatus.b && this.i.T()) {
         c.info("Disconnecting {} due to resource pack rejection", this.b.Z());
         this.b(IChatBaseComponent.c("multiplayer.requiredTexturePrompt.disconnect"));
      }

      this.cserver
         .getPluginManager()
         .callEvent(new PlayerResourcePackStatusEvent(this.getCraftPlayer(), Status.values()[packetplayinresourcepackstatus.a.ordinal()]));
   }

   @Override
   public void a(PacketPlayInBoatMove packetplayinboatmove) {
      PlayerConnectionUtils.a(packetplayinboatmove, this, this.b.x());
      Entity entity = this.b.cW();
      if (entity instanceof EntityBoat entityboat) {
         entityboat.a(packetplayinboatmove.a(), packetplayinboatmove.c());
      }
   }

   @Override
   public void a(ServerboundPongPacket serverboundpongpacket) {
   }

   @Override
   public void a(IChatBaseComponent ichatbasecomponent) {
      if (!this.processedDisconnect) {
         this.processedDisconnect = true;
         this.R.close();
         c.info("{} lost connection: {}", this.b.Z().getString(), ichatbasecomponent.getString());
         this.b.s();
         String quitMessage = this.i.ac().remove(this.b);
         if (quitMessage != null && quitMessage.length() > 0) {
            this.i.ac().broadcastMessage(CraftChatMessage.fromString(quitMessage));
         }

         this.b.T().b();
         if (this.g()) {
            c.info("Stopping singleplayer server as player logged out");
            this.i.a(false);
         }
      }
   }

   public void a(int i) {
      if (i < 0) {
         throw new IllegalArgumentException("Expected packet sequence nr >= 0");
      } else {
         this.k = Math.max(i, this.k);
      }
   }

   @Override
   public void a(Packet<?> packet) {
      this.a(packet, null);
   }

   public void a(Packet<?> packet, @Nullable PacketSendListener packetsendlistener) {
      if (packet != null && !this.processedDisconnect) {
         if (packet instanceof PacketPlayOutSpawnPosition packet6) {
            this.b.compassTarget = new Location(this.getCraftPlayer().getWorld(), (double)packet6.a.u(), (double)packet6.a.v(), (double)packet6.a.w());
         }

         try {
            this.h.a(packet, packetsendlistener);
         } catch (Throwable var6) {
            CrashReport crashreport = CrashReport.a(var6, "Sending packet");
            CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Packet being sent");
            crashreportsystemdetails.a("Packet class", () -> packet.getClass().getCanonicalName());
            throw new ReportedException(crashreport);
         }
      }
   }

   @Override
   public void a(PacketPlayInHeldItemSlot packetplayinhelditemslot) {
      PlayerConnectionUtils.a(packetplayinhelditemslot, this, this.b.x());
      if (!this.b.eP()) {
         if (packetplayinhelditemslot.a() >= 0 && packetplayinhelditemslot.a() < PlayerInventory.g()) {
            PlayerItemHeldEvent event = new PlayerItemHeldEvent(this.getCraftPlayer(), this.b.fJ().l, packetplayinhelditemslot.a());
            this.cserver.getPluginManager().callEvent(event);
            if (event.isCancelled()) {
               this.a(new PacketPlayOutHeldItemSlot(this.b.fJ().l));
               this.b.C();
               return;
            }

            if (this.b.fJ().l != packetplayinhelditemslot.a() && this.b.ff() == EnumHand.a) {
               this.b.fk();
            }

            this.b.fJ().l = packetplayinhelditemslot.a();
            this.b.C();
         } else {
            c.warn("{} tried to set an invalid carried item", this.b.Z().getString());
            this.disconnect("Invalid hotbar selection (Hacking?)");
         }
      }
   }

   @Override
   public void a(PacketPlayInChat packetplayinchat) {
      if (!this.i.ab()) {
         if (c(packetplayinchat.a())) {
            this.b(IChatBaseComponent.c("multiplayer.disconnect.illegal_characters"));
         } else {
            Optional<LastSeenMessages> optional = this.a(packetplayinchat.a(), packetplayinchat.c(), packetplayinchat.f());
            if (optional.isPresent()) {
               PlayerChatMessage playerchatmessage;
               try {
                  playerchatmessage = this.a(packetplayinchat, optional.get());
               } catch (SignedMessageChain.a var6) {
                  this.a(var6);
                  return;
               }

               CompletableFuture<FilteredText> completablefuture = this.a(playerchatmessage.b());
               CompletableFuture<IChatBaseComponent> completablefuture1 = this.i.bg().decorate(this.b, playerchatmessage.c());
               this.R.append(executor -> CompletableFuture.allOf(completablefuture, completablefuture1).thenAcceptAsync(ovoid -> {
                     PlayerChatMessage playerchatmessage1 = playerchatmessage.a(completablefuture1.join()).a(completablefuture.join().e());
                     this.b(playerchatmessage1);
                  }, this.i.chatExecutor));
            }
         }
      }
   }

   @Override
   public void a(ServerboundChatCommandPacket serverboundchatcommandpacket) {
      if (c(serverboundchatcommandpacket.a())) {
         this.b(IChatBaseComponent.c("multiplayer.disconnect.illegal_characters"));
      } else {
         Optional<LastSeenMessages> optional = this.a(serverboundchatcommandpacket.a(), serverboundchatcommandpacket.c(), serverboundchatcommandpacket.f());
         if (optional.isPresent()) {
            this.i.g(() -> {
               this.a(serverboundchatcommandpacket, optional.get());
               this.detectRateSpam("/" + serverboundchatcommandpacket.a());
            });
         }
      }
   }

   private void a(ServerboundChatCommandPacket serverboundchatcommandpacket, LastSeenMessages lastseenmessages) {
      String command = "/" + serverboundchatcommandpacket.a();
      c.info(this.b.cu() + " issued server command: " + command);
      PlayerCommandPreprocessEvent event = new PlayerCommandPreprocessEvent(this.getCraftPlayer(), command, new LazyPlayerSet(this.i));
      this.cserver.getPluginManager().callEvent(event);
      if (!event.isCancelled()) {
         command = event.getMessage().substring(1);
         ParseResults parseresults = this.b(command);

         Map map;
         try {
            map = serverboundchatcommandpacket.a().equals(command)
               ? this.a(serverboundchatcommandpacket, SignableCommand.a(parseresults), lastseenmessages)
               : Collections.emptyMap();
         } catch (SignedMessageChain.a var8) {
            this.a(var8);
            return;
         }

         CommandSigningContext.a commandsigningcontext_a = new CommandSigningContext.a(map);
         parseresults = net.minecraft.commands.CommandDispatcher.a(parseresults, commandlistenerwrapper -> commandlistenerwrapper.a(commandsigningcontext_a));
         this.i.aC().a(parseresults, command);
      }
   }

   private void a(SignedMessageChain.a signedmessagechain_a) {
      if (signedmessagechain_a.a()) {
         this.b(signedmessagechain_a.b());
      } else {
         this.b.a(signedmessagechain_a.b().e().a(EnumChatFormat.m));
      }
   }

   private Map<String, PlayerChatMessage> a(
      ServerboundChatCommandPacket serverboundchatcommandpacket, SignableCommand<?> signablecommand, LastSeenMessages lastseenmessages
   ) throws SignedMessageChain.a {
      Map<String, PlayerChatMessage> map = new Object2ObjectOpenHashMap();

      for(SignableCommand.a<?> signablecommand_a : signablecommand.a()) {
         MessageSignature messagesignature = serverboundchatcommandpacket.e().a(signablecommand_a.a());
         SignedMessageBody signedmessagebody = new SignedMessageBody(
            signablecommand_a.c(), serverboundchatcommandpacket.c(), serverboundchatcommandpacket.d(), lastseenmessages
         );
         map.put(signablecommand_a.a(), this.O.unpack(messagesignature, signedmessagebody));
      }

      return map;
   }

   private ParseResults<CommandListenerWrapper> b(String s) {
      CommandDispatcher<CommandListenerWrapper> com_mojang_brigadier_commanddispatcher = this.i.aC().a();
      return com_mojang_brigadier_commanddispatcher.parse(s, this.b.cZ());
   }

   private Optional<LastSeenMessages> a(String s, Instant instant, LastSeenMessages.b lastseenmessages_b) {
      if (!this.a(instant)) {
         c.warn("{} sent out-of-order chat: '{}'", this.b.Z().getString(), s);
         this.b(IChatBaseComponent.c("multiplayer.disconnect.out_of_order_chat"));
         return Optional.empty();
      } else {
         Optional<LastSeenMessages> optional = this.a(lastseenmessages_b);
         if (!this.b.dB() && this.b.A() != EnumChatVisibility.c) {
            this.b.C();
            return optional;
         } else {
            this.a(new ClientboundSystemChatPacket(IChatBaseComponent.c("chat.disabled.options").a(EnumChatFormat.m), false));
            return Optional.empty();
         }
      }
   }

   private Optional<LastSeenMessages> a(LastSeenMessages.b lastseenmessages_b) {
      LastSeenMessagesValidator var3 = this.P;
      synchronized(this.P) {
         Optional<LastSeenMessages> optional = this.P.a(lastseenmessages_b);
         if (optional.isEmpty()) {
            c.warn("Failed to validate message acknowledgements from {}", this.b.Z().getString());
            this.b(g);
         }

         return optional;
      }
   }

   private boolean a(Instant instant) {
      Instant instant1;
      do {
         instant1 = this.M.get();
         if (instant.isBefore(instant1)) {
            return false;
         }
      } while(!this.M.compareAndSet(instant1, instant));

      return true;
   }

   private static boolean c(String s) {
      for(int i = 0; i < s.length(); ++i) {
         if (!SharedConstants.a(s.charAt(i))) {
            return true;
         }
      }

      return false;
   }

   public void chat(String s, final PlayerChatMessage original, boolean async) {
      if (!s.isEmpty() && this.b.A() != EnumChatVisibility.c) {
         OutgoingChatMessage outgoing = OutgoingChatMessage.a(original);
         if (!async && s.startsWith("/")) {
            this.handleCommand(s);
         } else if (this.b.A() != EnumChatVisibility.b) {
            Player player = this.getCraftPlayer();
            AsyncPlayerChatEvent event = new AsyncPlayerChatEvent(async, player, s, new LazyPlayerSet(this.i));
            final String originalFormat = event.getFormat();
            final String originalMessage = event.getMessage();
            this.cserver.getPluginManager().callEvent(event);
            if (PlayerChatEvent.getHandlerList().getRegisteredListeners().length != 0) {
               final PlayerChatEvent queueEvent = new PlayerChatEvent(player, event.getMessage(), event.getFormat(), event.getRecipients());
               queueEvent.setCancelled(event.isCancelled());
               Waitable waitable = new Waitable() {
                  @Override
                  protected Object evaluate() {
                     Bukkit.getPluginManager().callEvent(queueEvent);
                     if (queueEvent.isCancelled()) {
                        return null;
                     } else {
                        String message = String.format(queueEvent.getFormat(), queueEvent.getPlayer().getDisplayName(), queueEvent.getMessage());
                        if (((LazyPlayerSet)queueEvent.getRecipients()).isLazy()) {
                           if (!SpigotConfig.bungee
                              && originalFormat.equals(queueEvent.getFormat())
                              && originalMessage.equals(queueEvent.getMessage())
                              && queueEvent.getPlayer().getName().equalsIgnoreCase(queueEvent.getPlayer().getDisplayName())) {
                              PlayerConnection.this.i.ac().a(original, PlayerConnection.this.b, ChatMessageType.a(ChatMessageType.c, PlayerConnection.this.b));
                              return null;
                           }

                           if (!SpigotConfig.bungee && CraftChatMessage.fromComponent(original.c()).equals(message)) {
                              PlayerConnection.this.i
                                 .ac()
                                 .a(original, PlayerConnection.this.b, ChatMessageType.a(ChatMessageType.RAW, PlayerConnection.this.b));
                              return null;
                           }

                           for(EntityPlayer recipient : PlayerConnection.this.i.ac().k) {
                              recipient.getBukkitEntity().sendMessage(PlayerConnection.this.b.cs(), message);
                           }
                        } else {
                           for(Player player : queueEvent.getRecipients()) {
                              player.sendMessage(PlayerConnection.this.b.cs(), message);
                           }
                        }

                        PlayerConnection.this.i.console.sendMessage(message);
                        return null;
                     }
                  }
               };
               if (async) {
                  this.i.processQueue.add(waitable);
               } else {
                  waitable.run();
               }

               try {
                  waitable.get();
               } catch (InterruptedException var12) {
                  Thread.currentThread().interrupt();
               } catch (ExecutionException var13) {
                  throw new RuntimeException("Exception processing chat event", var13.getCause());
               }
            } else {
               if (event.isCancelled()) {
                  return;
               }

               s = String.format(event.getFormat(), event.getPlayer().getDisplayName(), event.getMessage());
               if (((LazyPlayerSet)event.getRecipients()).isLazy()) {
                  if (!SpigotConfig.bungee
                     && originalFormat.equals(event.getFormat())
                     && originalMessage.equals(event.getMessage())
                     && event.getPlayer().getName().equalsIgnoreCase(event.getPlayer().getDisplayName())) {
                     this.i.ac().a(original, this.b, ChatMessageType.a(ChatMessageType.c, this.b));
                     return;
                  }

                  if (!SpigotConfig.bungee && CraftChatMessage.fromComponent(original.c()).equals(s)) {
                     this.i.ac().a(original, this.b, ChatMessageType.a(ChatMessageType.RAW, this.b));
                     return;
                  }

                  for(EntityPlayer recipient : this.i.ac().k) {
                     recipient.getBukkitEntity().sendMessage(this.b.cs(), s);
                  }
               } else {
                  for(Player recipient : event.getRecipients()) {
                     recipient.sendMessage(this.b.cs(), s);
                  }
               }

               this.i.console.sendMessage(s);
            }
         }
      }
   }

   private void handleCommand(String s) {
      SpigotTimings.playerCommandTimer.startTiming();
      if (SpigotConfig.logCommands) {
         c.info(this.b.cu() + " issued server command: " + s);
      }

      CraftPlayer player = this.getCraftPlayer();
      PlayerCommandPreprocessEvent event = new PlayerCommandPreprocessEvent(player, s, new LazyPlayerSet(this.i));
      this.cserver.getPluginManager().callEvent(event);
      if (event.isCancelled()) {
         SpigotTimings.playerCommandTimer.stopTiming();
      } else {
         try {
            if (!this.cserver.dispatchCommand(event.getPlayer(), event.getMessage().substring(1))) {
               return;
            }
         } catch (CommandException var8) {
            player.sendMessage(ChatColor.RED + "An internal error occurred while attempting to perform this command");
            java.util.logging.Logger.getLogger(PlayerConnection.class.getName()).log(Level.SEVERE, null, var8);
            return;
         } finally {
            SpigotTimings.playerCommandTimer.stopTiming();
         }
      }
   }

   private PlayerChatMessage a(PacketPlayInChat packetplayinchat, LastSeenMessages lastseenmessages) throws SignedMessageChain.a {
      SignedMessageBody signedmessagebody = new SignedMessageBody(packetplayinchat.a(), packetplayinchat.c(), packetplayinchat.d(), lastseenmessages);
      return this.O.unpack(packetplayinchat.e(), signedmessagebody);
   }

   private void b(PlayerChatMessage playerchatmessage) {
      final String s = playerchatmessage.b();
      if (s.isEmpty()) {
         c.warn(this.b.cu() + " tried to send an empty message");
      } else if (this.getCraftPlayer().isConversing()) {
         this.i.processQueue.add(new Runnable() {
            @Override
            public void run() {
               PlayerConnection.this.getCraftPlayer().acceptConversationInput(s);
            }
         });
      } else if (this.b.A() == EnumChatVisibility.b) {
         this.a(new ClientboundSystemChatPacket(IChatBaseComponent.c("chat.cannotSend").a(EnumChatFormat.m), false));
      } else {
         this.chat(s, playerchatmessage, true);
      }

      this.detectRateSpam(s);
   }

   private void detectRateSpam(String s) {
      boolean counted = true;

      for(String exclude : SpigotConfig.spamExclusions) {
         if (exclude != null && s.startsWith(exclude)) {
            counted = false;
            break;
         }
      }

      if (this.o.addAndGet(20) > 200 && !this.i.ac().f(this.b.fI())) {
         this.b(IChatBaseComponent.c("disconnect.spam"));
      }
   }

   @Override
   public void a(ServerboundChatAckPacket serverboundchatackpacket) {
      LastSeenMessagesValidator var3 = this.P;
      synchronized(this.P) {
         if (!this.P.a(serverboundchatackpacket.a())) {
            c.warn("Failed to validate message acknowledgements from {}", this.b.Z().getString());
            this.b(g);
         }
      }
   }

   @Override
   public void a(PacketPlayInArmAnimation packetplayinarmanimation) {
      PlayerConnectionUtils.a(packetplayinarmanimation, this, this.b.x());
      if (!this.b.eP()) {
         this.b.C();
         float f1 = this.b.dy();
         float f2 = this.b.dw();
         double d0 = this.b.dl();
         double d1 = this.b.dn() + (double)this.b.cE();
         double d2 = this.b.dr();
         Location origin = new Location(this.b.H.getWorld(), d0, d1, d2, f2, f1);
         double d3 = this.b.d.b() == EnumGamemode.b ? 5.0 : 4.5;
         RayTraceResult result = this.b
            .H
            .getWorld()
            .rayTrace(
               origin,
               origin.getDirection(),
               d3,
               FluidCollisionMode.NEVER,
               false,
               0.1,
               entity -> entity != this.b.getBukkitEntity() && this.b.getBukkitEntity().canSee(entity)
            );
         if (result == null) {
            CraftEventFactory.callPlayerInteractEvent(this.b, Action.LEFT_CLICK_AIR, this.b.fJ().f(), EnumHand.a);
         }

         PlayerAnimationEvent event = new PlayerAnimationEvent(
            this.getCraftPlayer(), packetplayinarmanimation.a() == EnumHand.a ? PlayerAnimationType.ARM_SWING : PlayerAnimationType.OFF_ARM_SWING
         );
         this.cserver.getPluginManager().callEvent(event);
         if (!event.isCancelled()) {
            this.b.a(packetplayinarmanimation.a());
         }
      }
   }

   @Override
   public void a(PacketPlayInEntityAction packetplayinentityaction) {
      PlayerConnectionUtils.a(packetplayinentityaction, this, this.b.x());
      if (!this.b.dB()) {
         switch(packetplayinentityaction.c()) {
            case a:
            case b:
               PlayerToggleSneakEvent event = new PlayerToggleSneakEvent(
                  this.getCraftPlayer(), packetplayinentityaction.c() == PacketPlayInEntityAction.EnumPlayerAction.a
               );
               this.cserver.getPluginManager().callEvent(event);
               if (event.isCancelled()) {
                  return;
               }
            case c:
            default:
               break;
            case d:
            case e:
               PlayerToggleSprintEvent e2 = new PlayerToggleSprintEvent(
                  this.getCraftPlayer(), packetplayinentityaction.c() == PacketPlayInEntityAction.EnumPlayerAction.d
               );
               this.cserver.getPluginManager().callEvent(e2);
               if (e2.isCancelled()) {
                  return;
               }
         }

         this.b.C();
         switch(packetplayinentityaction.c()) {
            case a:
               this.b.f(true);
               break;
            case b:
               this.b.f(false);
               break;
            case c:
               if (this.b.fu()) {
                  this.b.a(false, true);
                  this.D = this.b.de();
               }
               break;
            case d:
               this.b.g(true);
               break;
            case e:
               this.b.g(false);
               break;
            case f:
               Entity entity = this.b.cW();
               if (entity instanceof IJumpable ijumpable) {
                  int i = packetplayinentityaction.d();
                  if (ijumpable.a() && i > 0) {
                     ijumpable.c(i);
                  }
               }
               break;
            case g:
               Entity entity = this.b.cW();
               if (entity instanceof IJumpable ijumpable) {
                  ijumpable.b();
               }
               break;
            case h:
               Entity entity = this.b.cV();
               if (entity instanceof HasCustomInventoryScreen hascustominventoryscreen) {
                  hascustominventoryscreen.b(this.b);
               }
               break;
            case i:
               if (!this.b.fO()) {
                  this.b.fQ();
               }
               break;
            default:
               throw new IllegalArgumentException("Invalid client command!");
         }
      }
   }

   public void a(PlayerChatMessage playerchatmessage) {
      MessageSignature messagesignature = playerchatmessage.k();
      if (messagesignature != null) {
         this.Q.a(playerchatmessage);
         LastSeenMessagesValidator var5 = this.P;
         int i;
         synchronized(this.P) {
            this.P.a(messagesignature);
            i = this.P.a();
         }

         if (i > 4096) {
            this.b(IChatBaseComponent.c("multiplayer.disconnect.too_many_pending_chats"));
         }
      }
   }

   public void a(PlayerChatMessage playerchatmessage, ChatMessageType.a chatmessagetype_a) {
      if (!this.getCraftPlayer().canSee(playerchatmessage.j().c())) {
         this.a(playerchatmessage.c(), chatmessagetype_a);
      } else {
         this.a(
            new ClientboundPlayerChatPacket(
               playerchatmessage.j().c(),
               playerchatmessage.j().b(),
               playerchatmessage.k(),
               playerchatmessage.l().a(this.Q),
               playerchatmessage.m(),
               playerchatmessage.n(),
               chatmessagetype_a.a(this.b.H.u_())
            )
         );
         this.a(playerchatmessage);
      }
   }

   public void a(IChatBaseComponent ichatbasecomponent, ChatMessageType.a chatmessagetype_a) {
      this.a(new ClientboundDisguisedChatPacket(ichatbasecomponent, chatmessagetype_a.a(this.b.H.u_())));
   }

   public SocketAddress e() {
      return this.h.c();
   }

   public SocketAddress getRawAddress() {
      return this.h.m.remoteAddress();
   }

   @Override
   public void a(PacketPlayInUseEntity packetplayinuseentity) {
      PlayerConnectionUtils.a(packetplayinuseentity, this, this.b.x());
      if (!this.b.eP()) {
         final WorldServer worldserver = this.b.x();
         final Entity entity = packetplayinuseentity.a(worldserver);
         if (entity == this.b && !this.b.F_()) {
            this.disconnect("Cannot interact with self!");
         } else {
            this.b.C();
            this.b.f(packetplayinuseentity.a());
            if (entity != null) {
               if (!worldserver.p_().a(entity.dg())) {
                  return;
               }

               AxisAlignedBB axisalignedbb = entity.cD();
               if (axisalignedbb.e(this.b.bk()) < a) {
                  packetplayinuseentity.a(
                     new PacketPlayInUseEntity.c() {
                        private void performInteraction(EnumHand enumhand, PlayerConnection.a playerconnection_a, PlayerInteractEntityEvent event) {
                           ItemStack itemstack = PlayerConnection.this.b.b(enumhand);
                           if (itemstack.a(worldserver.G())) {
                              ItemStack itemstack1 = itemstack.o();
                              ItemStack itemInHand = PlayerConnection.this.b.b(enumhand);
                              boolean triggerLeashUpdate = itemInHand != null && itemInHand.c() == Items.tM && entity instanceof EntityInsentient;
                              Item origItem = PlayerConnection.this.b.fJ().f() == null ? null : PlayerConnection.this.b.fJ().f().c();
                              PlayerConnection.this.cserver.getPluginManager().callEvent(event);
                              if (entity instanceof Bucketable
                                 && entity instanceof EntityLiving
                                 && origItem != null
                                 && origItem.k() == Items.pH
                                 && (event.isCancelled() || PlayerConnection.this.b.fJ().f() == null || PlayerConnection.this.b.fJ().f().c() != origItem)) {
                                 PlayerConnection.this.a(new PacketPlayOutSpawnEntity(entity));
                                 PlayerConnection.this.b.bP.b();
                              }
   
                              if (triggerLeashUpdate
                                 && (event.isCancelled() || PlayerConnection.this.b.fJ().f() == null || PlayerConnection.this.b.fJ().f().c() != origItem)) {
                                 PlayerConnection.this.a(new PacketPlayOutAttachEntity(entity, ((EntityInsentient)entity).fJ()));
                              }
   
                              if (event.isCancelled() || PlayerConnection.this.b.fJ().f() == null || PlayerConnection.this.b.fJ().f().c() != origItem) {
                                 entity.aj().refresh(PlayerConnection.this.b);
                                 if (entity instanceof Allay) {
                                    PlayerConnection.this.a(
                                       new PacketPlayOutEntityEquipment(
                                          entity.af(),
                                          Arrays.stream(EnumItemSlot.values())
                                             .map(slot -> Pair.of(slot, ((EntityLiving)entity).c(slot).o()))
                                             .collect(Collectors.toList())
                                       )
                                    );
                                    PlayerConnection.this.b.bP.b();
                                 }
                              }
   
                              if (event.isCancelled()) {
                                 return;
                              }
   
                              EnumInteractionResult enuminteractionresult = playerconnection_a.run(PlayerConnection.this.b, entity, enumhand);
                              if (!itemInHand.b() && itemInHand.K() <= -1) {
                                 PlayerConnection.this.b.bP.b();
                              }
   
                              if (enuminteractionresult.a()) {
                                 CriterionTriggers.Q.a(PlayerConnection.this.b, itemstack1, entity);
                                 if (enuminteractionresult.b()) {
                                    PlayerConnection.this.b.a(enumhand, true);
                                 }
                              }
                           }
                        }
   
                        @Override
                        public void a(EnumHand enumhand) {
                           this.performInteraction(
                              enumhand,
                              EntityHuman::a,
                              new PlayerInteractEntityEvent(
                                 PlayerConnection.this.getCraftPlayer(),
                                 entity.getBukkitEntity(),
                                 enumhand == EnumHand.b ? EquipmentSlot.OFF_HAND : EquipmentSlot.HAND
                              )
                           );
                        }
   
                        @Override
                        public void a(EnumHand enumhand, Vec3D vec3d) {
                           this.performInteraction(
                              enumhand,
                              (entityplayer, entity1, enumhand1) -> entity1.a(entityplayer, vec3d, enumhand1),
                              new PlayerInteractAtEntityEvent(
                                 PlayerConnection.this.getCraftPlayer(),
                                 entity.getBukkitEntity(),
                                 new Vector(vec3d.c, vec3d.d, vec3d.e),
                                 enumhand == EnumHand.b ? EquipmentSlot.OFF_HAND : EquipmentSlot.HAND
                              )
                           );
                        }
   
                        @Override
                        public void a() {
                           if (!(entity instanceof EntityItem)
                              && !(entity instanceof EntityExperienceOrb)
                              && !(entity instanceof EntityArrow)
                              && (entity != PlayerConnection.this.b || PlayerConnection.this.b.F_())) {
                              ItemStack itemstack = PlayerConnection.this.b.b(EnumHand.a);
                              if (itemstack.a(worldserver.G())) {
                                 PlayerConnection.this.b.d(entity);
                                 if (!itemstack.b() && itemstack.K() <= -1) {
                                    PlayerConnection.this.b.bP.b();
                                 }
                              }
                           } else {
                              PlayerConnection.this.b(IChatBaseComponent.c("multiplayer.disconnect.invalid_entity_attacked"));
                              PlayerConnection.c.warn("Player {} tried to attack an invalid entity", PlayerConnection.this.b.Z().getString());
                           }
                        }
                     }
                  );
               }
            }
         }
      }
   }

   @Override
   public void a(PacketPlayInClientCommand packetplayinclientcommand) {
      PlayerConnectionUtils.a(packetplayinclientcommand, this, this.b.x());
      this.b.C();
      PacketPlayInClientCommand.EnumClientCommand packetplayinclientcommand_enumclientcommand = packetplayinclientcommand.a();
      switch(packetplayinclientcommand_enumclientcommand) {
         case a:
            if (this.b.f) {
               this.b.f = false;
               this.b = this.i.ac().a(this.b, true);
               CriterionTriggers.v.a(this.b, World.j, World.h);
            } else {
               if (this.b.eo() > 0.0F) {
                  return;
               }

               this.b = this.i.ac().a(this.b, false);
               if (this.i.h()) {
                  this.b.a(EnumGamemode.d);
                  this.b.x().W().a(GameRules.q).a(false, this.i);
               }
            }
            break;
         case b:
            this.b.D().a(this.b);
      }
   }

   @Override
   public void a(PacketPlayInCloseWindow packetplayinclosewindow) {
      PlayerConnectionUtils.a(packetplayinclosewindow, this, this.b.x());
      if (!this.b.eP()) {
         CraftEventFactory.handleInventoryCloseEvent(this.b);
         this.b.r();
      }
   }

   @Override
   public void a(PacketPlayInWindowClick packetplayinwindowclick) {
      PlayerConnectionUtils.a(packetplayinwindowclick, this, this.b.x());
      if (!this.b.eP()) {
         this.b.C();
         if (this.b.bP.j == packetplayinwindowclick.a() && this.b.bP.a(this.b)) {
            boolean cancelled = this.b.F_();
            if (!this.b.bP.a(this.b)) {
               c.debug("Player {} interacted with invalid menu {}", this.b, this.b.bP);
            } else {
               int i = packetplayinwindowclick.c();
               if (!this.b.bP.a(i)) {
                  c.debug("Player {} clicked invalid slot index: {}, available slots: {}", new Object[]{this.b.Z(), i, this.b.bP.i.size()});
               } else {
                  boolean flag = packetplayinwindowclick.h() != this.b.bP.j();
                  this.b.bP.h();
                  if (packetplayinwindowclick.c() < -1 && packetplayinwindowclick.c() != -999) {
                     return;
                  }

                  InventoryView inventory = this.b.bP.getBukkitView();
                  SlotType type = inventory.getSlotType(packetplayinwindowclick.c());
                  ClickType click = ClickType.UNKNOWN;
                  InventoryAction action = InventoryAction.UNKNOWN;
                  ItemStack itemstack = ItemStack.b;
                  switch(packetplayinwindowclick.g()) {
                     case a:
                        if (packetplayinwindowclick.d() == 0) {
                           click = ClickType.LEFT;
                        } else if (packetplayinwindowclick.d() == 1) {
                           click = ClickType.RIGHT;
                        }

                        if (packetplayinwindowclick.d() == 0 || packetplayinwindowclick.d() == 1) {
                           action = InventoryAction.NOTHING;
                           if (packetplayinwindowclick.c() == -999) {
                              if (!this.b.bP.g().b()) {
                                 action = packetplayinwindowclick.d() == 0 ? InventoryAction.DROP_ALL_CURSOR : InventoryAction.DROP_ONE_CURSOR;
                              }
                           } else if (packetplayinwindowclick.c() < 0) {
                              action = InventoryAction.NOTHING;
                           } else {
                              Slot slot = this.b.bP.b(packetplayinwindowclick.c());
                              if (slot != null) {
                                 ItemStack clickedItem = slot.e();
                                 ItemStack cursor = this.b.bP.g();
                                 if (clickedItem.b()) {
                                    if (!cursor.b()) {
                                       action = packetplayinwindowclick.d() == 0 ? InventoryAction.PLACE_ALL : InventoryAction.PLACE_ONE;
                                    }
                                 } else if (slot.a(this.b)) {
                                    if (cursor.b()) {
                                       action = packetplayinwindowclick.d() == 0 ? InventoryAction.PICKUP_ALL : InventoryAction.PICKUP_HALF;
                                    } else if (slot.a(cursor)) {
                                       if (clickedItem.a(cursor) && ItemStack.a(clickedItem, cursor)) {
                                          int toPlace = packetplayinwindowclick.d() == 0 ? cursor.K() : 1;
                                          toPlace = Math.min(toPlace, clickedItem.f() - clickedItem.K());
                                          toPlace = Math.min(toPlace, slot.d.ab_() - clickedItem.K());
                                          if (toPlace == 1) {
                                             action = InventoryAction.PLACE_ONE;
                                          } else if (toPlace == cursor.K()) {
                                             action = InventoryAction.PLACE_ALL;
                                          } else if (toPlace < 0) {
                                             action = toPlace != -1 ? InventoryAction.PICKUP_SOME : InventoryAction.PICKUP_ONE;
                                          } else if (toPlace != 0) {
                                             action = InventoryAction.PLACE_SOME;
                                          }
                                       } else if (cursor.K() <= slot.a()) {
                                          action = InventoryAction.SWAP_WITH_CURSOR;
                                       }
                                    } else if (cursor.c() == clickedItem.c()
                                       && ItemStack.a(cursor, clickedItem)
                                       && clickedItem.K() >= 0
                                       && clickedItem.K() + cursor.K() <= cursor.f()) {
                                       action = InventoryAction.PICKUP_ALL;
                                    }
                                 }
                              }
                           }
                        }
                        break;
                     case b:
                        if (packetplayinwindowclick.d() == 0) {
                           click = ClickType.SHIFT_LEFT;
                        } else if (packetplayinwindowclick.d() == 1) {
                           click = ClickType.SHIFT_RIGHT;
                        }

                        if (packetplayinwindowclick.d() == 0 || packetplayinwindowclick.d() == 1) {
                           if (packetplayinwindowclick.c() < 0) {
                              action = InventoryAction.NOTHING;
                           } else {
                              Slot slot = this.b.bP.b(packetplayinwindowclick.c());
                              if (slot != null && slot.a(this.b) && slot.f()) {
                                 action = InventoryAction.MOVE_TO_OTHER_INVENTORY;
                              } else {
                                 action = InventoryAction.NOTHING;
                              }
                           }
                        }
                        break;
                     case c:
                        if (packetplayinwindowclick.d() >= 0 && packetplayinwindowclick.d() < 9 || packetplayinwindowclick.d() == 40) {
                           click = packetplayinwindowclick.d() == 40 ? ClickType.SWAP_OFFHAND : ClickType.NUMBER_KEY;
                           Slot clickedSlot = this.b.bP.b(packetplayinwindowclick.c());
                           if (clickedSlot.a(this.b)) {
                              ItemStack hotbar = this.b.fJ().a(packetplayinwindowclick.d());
                              boolean canCleanSwap = hotbar.b() || clickedSlot.d == this.b.fJ() && clickedSlot.a(hotbar);
                              if (clickedSlot.f()) {
                                 if (canCleanSwap) {
                                    action = InventoryAction.HOTBAR_SWAP;
                                 } else {
                                    action = InventoryAction.HOTBAR_MOVE_AND_READD;
                                 }
                              } else if (!clickedSlot.f() && !hotbar.b() && clickedSlot.a(hotbar)) {
                                 action = InventoryAction.HOTBAR_SWAP;
                              } else {
                                 action = InventoryAction.NOTHING;
                              }
                           } else {
                              action = InventoryAction.NOTHING;
                           }
                        }
                        break;
                     case d:
                        if (packetplayinwindowclick.d() == 2) {
                           click = ClickType.MIDDLE;
                           if (packetplayinwindowclick.c() < 0) {
                              action = InventoryAction.NOTHING;
                           } else {
                              Slot slot = this.b.bP.b(packetplayinwindowclick.c());
                              if (slot != null && slot.f() && this.b.fK().d && this.b.bP.g().b()) {
                                 action = InventoryAction.CLONE_STACK;
                              } else {
                                 action = InventoryAction.NOTHING;
                              }
                           }
                        } else {
                           click = ClickType.UNKNOWN;
                           action = InventoryAction.UNKNOWN;
                        }
                        break;
                     case e:
                        if (packetplayinwindowclick.c() >= 0) {
                           if (packetplayinwindowclick.d() == 0) {
                              click = ClickType.DROP;
                              Slot slot = this.b.bP.b(packetplayinwindowclick.c());
                              if (slot != null && slot.f() && slot.a(this.b) && !slot.e().b() && slot.e().c() != Item.a(Blocks.a)) {
                                 action = InventoryAction.DROP_ONE_SLOT;
                              } else {
                                 action = InventoryAction.NOTHING;
                              }
                           } else if (packetplayinwindowclick.d() == 1) {
                              click = ClickType.CONTROL_DROP;
                              Slot slot = this.b.bP.b(packetplayinwindowclick.c());
                              if (slot != null && slot.f() && slot.a(this.b) && !slot.e().b() && slot.e().c() != Item.a(Blocks.a)) {
                                 action = InventoryAction.DROP_ALL_SLOT;
                              } else {
                                 action = InventoryAction.NOTHING;
                              }
                           }
                        } else {
                           click = ClickType.LEFT;
                           if (packetplayinwindowclick.d() == 1) {
                              click = ClickType.RIGHT;
                           }

                           action = InventoryAction.NOTHING;
                        }
                        break;
                     case f:
                        this.b.bP.a(packetplayinwindowclick.c(), packetplayinwindowclick.d(), packetplayinwindowclick.g(), this.b);
                        break;
                     case g:
                        click = ClickType.DOUBLE_CLICK;
                        action = InventoryAction.NOTHING;
                        if (packetplayinwindowclick.c() >= 0 && !this.b.bP.g().b()) {
                           ItemStack cursor = this.b.bP.g();
                           action = InventoryAction.NOTHING;
                           if (inventory.getTopInventory().contains(CraftMagicNumbers.getMaterial(cursor.c()))
                              || inventory.getBottomInventory().contains(CraftMagicNumbers.getMaterial(cursor.c()))) {
                              action = InventoryAction.COLLECT_TO_CURSOR;
                           }
                        }
                  }

                  if (packetplayinwindowclick.g() != InventoryClickType.f) {
                     InventoryClickEvent event;
                     if (click == ClickType.NUMBER_KEY) {
                        event = new InventoryClickEvent(inventory, type, packetplayinwindowclick.c(), click, action, packetplayinwindowclick.d());
                     } else {
                        event = new InventoryClickEvent(inventory, type, packetplayinwindowclick.c(), click, action);
                     }

                     Inventory top = inventory.getTopInventory();
                     if (packetplayinwindowclick.c() == 0 && top instanceof CraftingInventory) {
                        Recipe recipe = ((CraftingInventory)top).getRecipe();
                        if (recipe != null) {
                           if (click == ClickType.NUMBER_KEY) {
                              event = new CraftItemEvent(recipe, inventory, type, packetplayinwindowclick.c(), click, action, packetplayinwindowclick.d());
                           } else {
                              event = new CraftItemEvent(recipe, inventory, type, packetplayinwindowclick.c(), click, action);
                           }
                        }
                     }

                     if (packetplayinwindowclick.c() == 2 && top instanceof SmithingInventory) {
                        org.bukkit.inventory.ItemStack result = ((SmithingInventory)top).getResult();
                        if (result != null) {
                           if (click == ClickType.NUMBER_KEY) {
                              event = new SmithItemEvent(inventory, type, packetplayinwindowclick.c(), click, action, packetplayinwindowclick.d());
                           } else {
                              event = new SmithItemEvent(inventory, type, packetplayinwindowclick.c(), click, action);
                           }
                        }
                     }

                     event.setCancelled(cancelled);
                     Container oldContainer = this.b.bP;
                     this.cserver.getPluginManager().callEvent(event);
                     if (this.b.bP != oldContainer) {
                        return;
                     }

                     label179:
                     switch(event.getResult()) {
                        case DENY:
                           switch(action) {
                              case NOTHING:
                              default:
                                 break label179;
                              case PICKUP_ALL:
                              case MOVE_TO_OTHER_INVENTORY:
                              case HOTBAR_MOVE_AND_READD:
                              case HOTBAR_SWAP:
                              case COLLECT_TO_CURSOR:
                              case UNKNOWN:
                                 this.b.bP.b();
                                 break label179;
                              case PICKUP_SOME:
                              case PICKUP_HALF:
                              case PICKUP_ONE:
                              case PLACE_ALL:
                              case PLACE_SOME:
                              case PLACE_ONE:
                              case SWAP_WITH_CURSOR:
                                 this.b.b.a(new PacketPlayOutSetSlot(-1, -1, this.b.bO.k(), this.b.bP.g()));
                                 this.b
                                    .b
                                    .a(
                                       new PacketPlayOutSetSlot(
                                          this.b.bP.j, this.b.bO.k(), packetplayinwindowclick.c(), this.b.bP.b(packetplayinwindowclick.c()).e()
                                       )
                                    );
                                 break label179;
                              case DROP_ALL_CURSOR:
                              case DROP_ONE_CURSOR:
                              case CLONE_STACK:
                                 this.b.b.a(new PacketPlayOutSetSlot(-1, -1, this.b.bO.k(), this.b.bP.g()));
                                 break label179;
                              case DROP_ALL_SLOT:
                              case DROP_ONE_SLOT:
                                 this.b
                                    .b
                                    .a(
                                       new PacketPlayOutSetSlot(
                                          this.b.bP.j, this.b.bO.k(), packetplayinwindowclick.c(), this.b.bP.b(packetplayinwindowclick.c()).e()
                                       )
                                    );
                                 break label179;
                           }
                        case DEFAULT:
                        case ALLOW:
                           this.b.bP.a(i, packetplayinwindowclick.d(), packetplayinwindowclick.g(), this.b);
                     }

                     if (event instanceof CraftItemEvent || event instanceof SmithItemEvent) {
                        this.b.bP.b();
                     }
                  }

                  ObjectIterator objectiterator = Int2ObjectMaps.fastIterable(packetplayinwindowclick.f()).iterator();

                  while(objectiterator.hasNext()) {
                     Entry<ItemStack> entry = (Entry)objectiterator.next();
                     this.b.bP.b(entry.getIntKey(), (ItemStack)entry.getValue());
                  }

                  this.b.bP.a(packetplayinwindowclick.e());
                  this.b.bP.i();
                  if (flag) {
                     this.b.bP.e();
                  } else {
                     this.b.bP.d();
                  }
               }
            }
         }
      }
   }

   @Override
   public void a(PacketPlayInAutoRecipe packetplayinautorecipe) {
      PlayerConnectionUtils.a(packetplayinautorecipe, this, this.b.x());
      this.b.C();
      if (!this.b.F_() && this.b.bP.j == packetplayinautorecipe.a() && this.b.bP instanceof ContainerRecipeBook) {
         if (!this.b.bP.a(this.b)) {
            c.debug("Player {} interacted with invalid menu {}", this.b, this.b.bP);
         } else {
            this.i.aE().a(packetplayinautorecipe.c()).ifPresent(irecipe -> ((ContainerRecipeBook)this.b.bP).a(packetplayinautorecipe.d(), irecipe, this.b));
         }
      }
   }

   @Override
   public void a(PacketPlayInEnchantItem packetplayinenchantitem) {
      PlayerConnectionUtils.a(packetplayinenchantitem, this, this.b.x());
      if (!this.b.eP()) {
         this.b.C();
         if (this.b.bP.j == packetplayinenchantitem.a() && !this.b.F_()) {
            if (!this.b.bP.a(this.b)) {
               c.debug("Player {} interacted with invalid menu {}", this.b, this.b.bP);
            } else {
               boolean flag = this.b.bP.b(this.b, packetplayinenchantitem.c());
               if (flag) {
                  this.b.bP.d();
               }
            }
         }
      }
   }

   @Override
   public void a(PacketPlayInSetCreativeSlot packetplayinsetcreativeslot) {
      PlayerConnectionUtils.a(packetplayinsetcreativeslot, this, this.b.x());
      if (this.b.d.e()) {
         boolean flag = packetplayinsetcreativeslot.a() < 0;
         ItemStack itemstack = packetplayinsetcreativeslot.c();
         if (!itemstack.a(this.b.x().G())) {
            return;
         }

         NBTTagCompound nbttagcompound = ItemBlock.a(itemstack);
         if (!itemstack.b()
            && nbttagcompound != null
            && nbttagcompound.e("x")
            && nbttagcompound.e("y")
            && nbttagcompound.e("z")
            && this.b.getBukkitEntity().hasPermission("minecraft.nbt.copy")) {
            BlockPosition blockposition = TileEntity.c(nbttagcompound);
            if (this.b.H.o(blockposition)) {
               TileEntity tileentity = this.b.H.c_(blockposition);
               if (tileentity != null) {
                  tileentity.e(itemstack);
               }
            }
         }

         boolean flag1 = packetplayinsetcreativeslot.a() >= 1 && packetplayinsetcreativeslot.a() <= 45;
         boolean flag2 = itemstack.b() || itemstack.j() >= 0 && itemstack.K() <= 64 && !itemstack.b();
         if (flag || flag1 && !ItemStack.b(this.b.bO.b(packetplayinsetcreativeslot.a()).e(), packetplayinsetcreativeslot.c())) {
            InventoryView inventory = this.b.bO.getBukkitView();
            org.bukkit.inventory.ItemStack item = CraftItemStack.asBukkitCopy(packetplayinsetcreativeslot.c());
            SlotType type = SlotType.QUICKBAR;
            if (flag) {
               type = SlotType.OUTSIDE;
            } else if (packetplayinsetcreativeslot.a() < 36) {
               if (packetplayinsetcreativeslot.a() >= 5 && packetplayinsetcreativeslot.a() < 9) {
                  type = SlotType.ARMOR;
               } else {
                  type = SlotType.CONTAINER;
               }
            }

            InventoryCreativeEvent event = new InventoryCreativeEvent(inventory, type, flag ? -999 : packetplayinsetcreativeslot.a(), item);
            this.cserver.getPluginManager().callEvent(event);
            itemstack = CraftItemStack.asNMSCopy(event.getCursor());
            switch(event.getResult()) {
               case DENY:
                  if (packetplayinsetcreativeslot.a() >= 0) {
                     this.b
                        .b
                        .a(
                           new PacketPlayOutSetSlot(
                              this.b.bO.j, this.b.bO.k(), packetplayinsetcreativeslot.a(), this.b.bO.b(packetplayinsetcreativeslot.a()).e()
                           )
                        );
                     this.b.b.a(new PacketPlayOutSetSlot(-1, this.b.bO.k(), -1, ItemStack.b));
                  }

                  return;
               case DEFAULT:
               default:
                  break;
               case ALLOW:
                  flag2 = true;
            }
         }

         if (flag1 && flag2) {
            this.b.bO.b(packetplayinsetcreativeslot.a()).d(itemstack);
            this.b.bO.d();
         } else if (flag && flag2 && this.p < 200) {
            this.p += 20;
            this.b.a(itemstack, true);
         }
      }
   }

   @Override
   public void a(PacketPlayInUpdateSign packetplayinupdatesign) {
      List<String> list = Stream.of(packetplayinupdatesign.c()).map(EnumChatFormat::a).collect(Collectors.toList());
      this.a(list).thenAcceptAsync(list1 -> this.a(packetplayinupdatesign, list1), this.i);
   }

   private void a(PacketPlayInUpdateSign packetplayinupdatesign, List<FilteredText> list) {
      if (!this.b.eP()) {
         this.b.C();
         WorldServer worldserver = this.b.x();
         BlockPosition blockposition = packetplayinupdatesign.a();
         if (worldserver.D(blockposition)) {
            IBlockData iblockdata = worldserver.a_(blockposition);
            TileEntity tileentity = worldserver.c_(blockposition);
            if (!(tileentity instanceof TileEntitySign)) {
               return;
            }

            TileEntitySign tileentitysign = (TileEntitySign)tileentity;
            if (!tileentitysign.g() || !this.b.cs().equals(tileentitysign.i())) {
               c.warn("Player {} just tried to change non-editable sign", this.b.Z().getString());
               this.a(tileentity.h());
               return;
            }

            Player player = this.b.getBukkitEntity();
            int x = packetplayinupdatesign.a().u();
            int y = packetplayinupdatesign.a().v();
            int z = packetplayinupdatesign.a().w();
            String[] lines = new String[4];

            for(int i = 0; i < list.size(); ++i) {
               FilteredText filteredtext = list.get(i);
               if (this.b.U()) {
                  lines[i] = EnumChatFormat.a(filteredtext.b());
               } else {
                  lines[i] = EnumChatFormat.a(filteredtext.d());
               }
            }

            SignChangeEvent event = new SignChangeEvent((CraftBlock)player.getWorld().getBlockAt(x, y, z), this.b.getBukkitEntity(), lines);
            this.cserver.getPluginManager().callEvent(event);
            if (!event.isCancelled()) {
               IChatBaseComponent[] components = CraftSign.sanitizeLines(event.getLines());

               for(int i = 0; i < components.length; ++i) {
                  tileentitysign.a(i, components[i]);
               }

               tileentitysign.h = false;
            }

            tileentitysign.e();
            worldserver.a(blockposition, iblockdata, iblockdata, 3);
         }
      }
   }

   @Override
   public void a(PacketPlayInKeepAlive packetplayinkeepalive) {
      PlayerConnectionUtils.a(packetplayinkeepalive, this, this.b.x());
      if (this.m && packetplayinkeepalive.a() == this.n) {
         int i = (int)(SystemUtils.b() - this.l);
         this.b.e = (this.b.e * 3 + i) / 4;
         this.m = false;
      } else if (!this.g()) {
         this.b(IChatBaseComponent.c("disconnect.timeout"));
      }
   }

   @Override
   public void a(PacketPlayInAbilities packetplayinabilities) {
      PlayerConnectionUtils.a(packetplayinabilities, this, this.b.x());
      if (this.b.fK().c && this.b.fK().b != packetplayinabilities.a()) {
         PlayerToggleFlightEvent event = new PlayerToggleFlightEvent(this.b.getBukkitEntity(), packetplayinabilities.a());
         this.cserver.getPluginManager().callEvent(event);
         if (!event.isCancelled()) {
            this.b.fK().b = packetplayinabilities.a();
         } else {
            this.b.w();
         }
      }
   }

   @Override
   public void a(PacketPlayInSettings packetplayinsettings) {
      PlayerConnectionUtils.a(packetplayinsettings, this, this.b.x());
      this.b.a(packetplayinsettings);
   }

   @Override
   public void a(PacketPlayInCustomPayload packetplayincustompayload) {
      PlayerConnectionUtils.a(packetplayincustompayload, this, this.b.x());
      if (packetplayincustompayload.c.equals(CUSTOM_REGISTER)) {
         try {
            String channels = packetplayincustompayload.d.toString(Charsets.UTF_8);

            String[] var6;
            for(String channel : var6 = channels.split("\u0000")) {
               this.getCraftPlayer().addChannel(channel);
            }
         } catch (Exception var9) {
            c.error("Couldn't register custom payload", var9);
            this.disconnect("Invalid payload REGISTER!");
         }
      } else if (packetplayincustompayload.c.equals(CUSTOM_UNREGISTER)) {
         try {
            String channels = packetplayincustompayload.d.toString(Charsets.UTF_8);

            String[] var15;
            for(String channel : var15 = channels.split("\u0000")) {
               this.getCraftPlayer().removeChannel(channel);
            }
         } catch (Exception var8) {
            c.error("Couldn't unregister custom payload", var8);
            this.disconnect("Invalid payload UNREGISTER!");
         }
      } else {
         try {
            byte[] data = new byte[packetplayincustompayload.d.readableBytes()];
            packetplayincustompayload.d.readBytes(data);
            this.cserver.getMessenger().dispatchIncomingMessage(this.b.getBukkitEntity(), packetplayincustompayload.c.toString(), data);
         } catch (Exception var7) {
            c.error("Couldn't dispatch custom payload", var7);
            this.disconnect("Invalid custom payload!");
         }
      }
   }

   public final boolean isDisconnected() {
      return !this.b.joining && !this.h.h();
   }

   @Override
   public void a(PacketPlayInDifficultyChange packetplayindifficultychange) {
      PlayerConnectionUtils.a(packetplayindifficultychange, this, this.b.x());
      if (this.b.k(2) || this.g()) {
         this.i.a(packetplayindifficultychange.a(), false);
      }
   }

   @Override
   public void a(PacketPlayInDifficultyLock packetplayindifficultylock) {
      PlayerConnectionUtils.a(packetplayindifficultylock, this, this.b.x());
      if (this.b.k(2) || this.g()) {
         this.i.b(packetplayindifficultylock.a());
      }
   }

   @Override
   public void a(ServerboundChatSessionUpdatePacket serverboundchatsessionupdatepacket) {
      PlayerConnectionUtils.a(serverboundchatsessionupdatepacket, this, this.b.x());
      RemoteChatSession.a remotechatsession_a = serverboundchatsessionupdatepacket.a();
      ProfilePublicKey.a profilepublickey_a = this.N != null ? this.N.d().b() : null;
      ProfilePublicKey.a profilepublickey_a1 = remotechatsession_a.b();
      if (!Objects.equals(profilepublickey_a, profilepublickey_a1)) {
         if (profilepublickey_a != null && profilepublickey_a1.b().isBefore(profilepublickey_a.b())) {
            this.b(ProfilePublicKey.a);
         } else {
            try {
               SignatureValidator signaturevalidator = this.i.an();
               this.a(remotechatsession_a.a(this.b.fI(), signaturevalidator, Duration.ZERO));
            } catch (ProfilePublicKey.b var6) {
               c.error("Failed to validate profile key: {}", var6.getMessage());
               this.b(var6.b());
            }
         }
      }
   }

   private void a(RemoteChatSession remotechatsession) {
      this.N = remotechatsession;
      this.O = remotechatsession.a(this.b.cs());
      this.R.append(executor -> {
         this.b.a(remotechatsession);
         this.i.ac().a(new ClientboundPlayerInfoUpdatePacket(EnumSet.of(ClientboundPlayerInfoUpdatePacket.a.b), List.of(this.b)));
         return CompletableFuture.completedFuture(null);
      });
   }

   @Override
   public EntityPlayer f() {
      return this.b;
   }

   @FunctionalInterface
   private interface a {
      EnumInteractionResult run(EntityPlayer var1, Entity var2, EnumHand var3);
   }
}
