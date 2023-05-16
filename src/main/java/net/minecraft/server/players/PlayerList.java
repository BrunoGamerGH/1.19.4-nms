package net.minecraft.server.players;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import io.netty.buffer.Unpooled;
import java.io.File;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.EnumChatFormat;
import net.minecraft.FileUtils;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.core.RegistrySynchronization;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.chat.ChatMessageType;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatMutableComponent;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundInitializeBorderPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoRemovePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderCenterPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderLerpSizePacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderSizePacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderWarningDelayPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderWarningDistancePacket;
import net.minecraft.network.protocol.game.ClientboundSetSimulationDistancePacket;
import net.minecraft.network.protocol.game.ClientboundUpdateEnabledFeaturesPacket;
import net.minecraft.network.protocol.game.PacketPlayOutAbilities;
import net.minecraft.network.protocol.game.PacketPlayOutCustomPayload;
import net.minecraft.network.protocol.game.PacketPlayOutEntityEffect;
import net.minecraft.network.protocol.game.PacketPlayOutEntityStatus;
import net.minecraft.network.protocol.game.PacketPlayOutExperience;
import net.minecraft.network.protocol.game.PacketPlayOutGameStateChange;
import net.minecraft.network.protocol.game.PacketPlayOutHeldItemSlot;
import net.minecraft.network.protocol.game.PacketPlayOutLogin;
import net.minecraft.network.protocol.game.PacketPlayOutNamedSoundEffect;
import net.minecraft.network.protocol.game.PacketPlayOutRecipeUpdate;
import net.minecraft.network.protocol.game.PacketPlayOutRespawn;
import net.minecraft.network.protocol.game.PacketPlayOutScoreboardTeam;
import net.minecraft.network.protocol.game.PacketPlayOutServerDifficulty;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnPosition;
import net.minecraft.network.protocol.game.PacketPlayOutTags;
import net.minecraft.network.protocol.game.PacketPlayOutUpdateTime;
import net.minecraft.network.protocol.game.PacketPlayOutViewDistance;
import net.minecraft.network.protocol.status.ServerPing;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.AdvancementDataPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.RegistryLayer;
import net.minecraft.server.ScoreboardServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.server.network.LoginListener;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.stats.ServerStatisticManager;
import net.minecraft.stats.StatisticList;
import net.minecraft.tags.TagNetworkSerialization;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.MathHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.World;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.border.IWorldBorderListener;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.dimension.DimensionManager;
import net.minecraft.world.level.storage.SavedFile;
import net.minecraft.world.level.storage.WorldData;
import net.minecraft.world.level.storage.WorldNBTStorage;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.scores.ScoreboardObjective;
import net.minecraft.world.scores.ScoreboardTeam;
import net.minecraft.world.scores.ScoreboardTeamBase;
import org.bukkit.Location;
import org.bukkit.WeatherType;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R3.command.ColouredConsoleSender;
import org.bukkit.craftbukkit.v1_19_R3.command.ConsoleCommandCompleter;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftChatMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.slf4j.Logger;
import org.spigotmc.SpigotConfig;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

public abstract class PlayerList {
   public static final File b = new File("banned-players.json");
   public static final File c = new File("banned-ips.json");
   public static final File d = new File("ops.json");
   public static final File e = new File("whitelist.json");
   public static final IChatBaseComponent f = IChatBaseComponent.c("chat.filtered_full");
   private static final Logger a = LogUtils.getLogger();
   private static final int h = 600;
   private static final SimpleDateFormat i = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
   private final MinecraftServer j;
   public final List<EntityPlayer> k = new CopyOnWriteArrayList<>();
   private final Map<UUID, EntityPlayer> l = Maps.newHashMap();
   private final GameProfileBanList m;
   private final IpBanList n;
   private final OpList o;
   private final WhiteList p;
   public final WorldNBTStorage s;
   private boolean t;
   private final LayeredRegistryAccess<RegistryLayer> u;
   private final IRegistryCustom.Dimension v;
   protected final int g;
   private int w;
   private int x;
   private boolean y;
   private static final boolean z = false;
   private int A;
   private CraftServer cserver;
   private final Map<String, EntityPlayer> playersByName = new HashMap<>();

   public PlayerList(MinecraftServer minecraftserver, LayeredRegistryAccess<RegistryLayer> layeredregistryaccess, WorldNBTStorage worldnbtstorage, int i) {
      this.cserver = minecraftserver.server = new CraftServer((DedicatedServer)minecraftserver, this);
      minecraftserver.console = ColouredConsoleSender.getInstance();
      minecraftserver.reader.addCompleter(new ConsoleCommandCompleter(minecraftserver.server));
      this.m = new GameProfileBanList(b);
      this.n = new IpBanList(c);
      this.o = new OpList(d);
      this.p = new WhiteList(e);
      this.j = minecraftserver;
      this.u = layeredregistryaccess;
      this.v = new IRegistryCustom.c(RegistrySynchronization.a(layeredregistryaccess)).c();
      this.g = i;
      this.s = worldnbtstorage;
   }

   public void a(NetworkManager networkmanager, EntityPlayer entityplayer) {
      GameProfile gameprofile = entityplayer.fI();
      UserCache usercache = this.j.ap();
      Optional<GameProfile> optional = usercache.a(gameprofile.getId());
      String s = optional.<String>map(GameProfile::getName).orElse(gameprofile.getName());
      usercache.a(gameprofile);
      NBTTagCompound nbttagcompound = this.a(entityplayer);
      if (nbttagcompound != null && nbttagcompound.e("bukkit")) {
         NBTTagCompound bukkit = nbttagcompound.p("bukkit");
         s = bukkit.b("lastKnownName", 8) ? bukkit.l("lastKnownName") : s;
      }

      ResourceKey resourcekey;
      if (nbttagcompound != null) {
         DataResult<ResourceKey<World>> dataresult = DimensionManager.a(new Dynamic(DynamicOpsNBT.a, nbttagcompound.c("Dimension")));
         Logger logger = a;
         resourcekey = dataresult.resultOrPartial(logger::error).orElse(World.h);
      } else {
         resourcekey = World.h;
      }

      WorldServer worldserver = this.j.a(resourcekey);
      WorldServer worldserver1;
      if (worldserver == null) {
         a.warn("Unknown respawn dimension {}, defaulting to overworld", resourcekey);
         worldserver1 = this.j.D();
      } else {
         worldserver1 = worldserver;
      }

      entityplayer.c(worldserver1);
      String s1 = "local";
      if (networkmanager.c() != null) {
         s1 = networkmanager.c().toString();
      }

      Player spawnPlayer = entityplayer.getBukkitEntity();
      PlayerSpawnLocationEvent ev = new PlayerSpawnLocationEvent(spawnPlayer, spawnPlayer.getLocation());
      this.cserver.getPluginManager().callEvent(ev);
      Location loc = ev.getSpawnLocation();
      worldserver1 = ((CraftWorld)loc.getWorld()).getHandle();
      entityplayer.spawnIn(worldserver1);
      entityplayer.d.a((WorldServer)entityplayer.H);
      entityplayer.a(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
      WorldData worlddata = worldserver1.n_();
      entityplayer.c(nbttagcompound);
      PlayerConnection playerconnection = new PlayerConnection(this.j, networkmanager, entityplayer);
      GameRules gamerules = worldserver1.W();
      boolean flag = gamerules.b(GameRules.B);
      boolean flag1 = gamerules.b(GameRules.p);
      playerconnection.a(
         new PacketPlayOutLogin(
            entityplayer.af(),
            worlddata.n(),
            entityplayer.d.b(),
            entityplayer.d.c(),
            this.j.E(),
            this.v,
            worldserver1.Z(),
            worldserver1.ab(),
            BiomeManager.a(worldserver1.A()),
            this.n(),
            worldserver1.spigotConfig.viewDistance,
            worldserver1.spigotConfig.simulationDistance,
            flag1,
            !flag,
            worldserver1.ae(),
            worldserver1.z(),
            entityplayer.gi()
         )
      );
      entityplayer.getBukkitEntity().sendSupportedChannels();
      playerconnection.a(new ClientboundUpdateEnabledFeaturesPacket(FeatureFlags.d.b(worldserver1.G())));
      playerconnection.a(
         new PacketPlayOutCustomPayload(PacketPlayOutCustomPayload.a, new PacketDataSerializer(Unpooled.buffer()).a(this.c().getServerModName()))
      );
      playerconnection.a(new PacketPlayOutServerDifficulty(worlddata.s(), worlddata.t()));
      playerconnection.a(new PacketPlayOutAbilities(entityplayer.fK()));
      playerconnection.a(new PacketPlayOutHeldItemSlot(entityplayer.fJ().l));
      playerconnection.a(new PacketPlayOutRecipeUpdate(this.j.aE().b()));
      playerconnection.a(new PacketPlayOutTags(TagNetworkSerialization.a(this.u)));
      this.d(entityplayer);
      entityplayer.D().c();
      entityplayer.E().a(entityplayer);
      this.a(worldserver1.f(), entityplayer);
      this.j.ar();
      IChatMutableComponent ichatmutablecomponent;
      if (entityplayer.fI().getName().equalsIgnoreCase(s)) {
         ichatmutablecomponent = IChatBaseComponent.a("multiplayer.player.joined", entityplayer.G_());
      } else {
         ichatmutablecomponent = IChatBaseComponent.a("multiplayer.player.joined.renamed", entityplayer.G_(), s);
      }

      ichatmutablecomponent.a(EnumChatFormat.o);
      String joinMessage = CraftChatMessage.fromComponent(ichatmutablecomponent);
      playerconnection.a(entityplayer.dl(), entityplayer.dn(), entityplayer.dr(), entityplayer.dw(), entityplayer.dy());
      ServerPing serverping = this.j.aq();
      if (serverping != null) {
         entityplayer.a(serverping);
      }

      entityplayer.b.a(ClientboundPlayerInfoUpdatePacket.a(this.k));
      this.k.add(entityplayer);
      this.playersByName.put(entityplayer.cu().toLowerCase(Locale.ROOT), entityplayer);
      this.l.put(entityplayer.cs(), entityplayer);
      CraftPlayer bukkitPlayer = entityplayer.getBukkitEntity();
      entityplayer.bP.transferTo(entityplayer.bP, bukkitPlayer);
      PlayerJoinEvent playerJoinEvent = new PlayerJoinEvent(bukkitPlayer, joinMessage);
      this.cserver.getPluginManager().callEvent(playerJoinEvent);
      if (entityplayer.b.a()) {
         joinMessage = playerJoinEvent.getJoinMessage();
         if (joinMessage != null && joinMessage.length() > 0) {
            IChatBaseComponent[] finalWorldServer;
            for(IChatBaseComponent line : finalWorldServer = CraftChatMessage.fromString(joinMessage)) {
               this.j.ac().a(line, false);
            }
         }

         ClientboundPlayerInfoUpdatePacket packet = ClientboundPlayerInfoUpdatePacket.a(List.of(entityplayer));

         for(int i = 0; i < this.k.size(); ++i) {
            EntityPlayer entityplayer1 = this.k.get(i);
            if (entityplayer1.getBukkitEntity().canSee((Player)bukkitPlayer)) {
               entityplayer1.b.a(packet);
            }

            if (bukkitPlayer.canSee((Player)entityplayer1.getBukkitEntity())) {
               entityplayer.b.a(ClientboundPlayerInfoUpdatePacket.a(List.of(entityplayer1)));
            }
         }

         entityplayer.sentListPacket = true;
         entityplayer.aj().refresh(entityplayer);
         this.a(entityplayer, worldserver1);
         if (entityplayer.H == worldserver1 && !worldserver1.v().contains(entityplayer)) {
            worldserver1.c(entityplayer);
            this.j.aL().a(entityplayer);
         }

         worldserver1 = entityplayer.x();
         this.j
            .S()
            .ifPresent(
               minecraftserver_serverresourcepackinfo -> entityplayer.a(
                     minecraftserver_serverresourcepackinfo.a(),
                     minecraftserver_serverresourcepackinfo.b(),
                     minecraftserver_serverresourcepackinfo.c(),
                     minecraftserver_serverresourcepackinfo.d()
                  )
            );

         for(MobEffect mobeffect : entityplayer.el()) {
            playerconnection.a(new PacketPlayOutEntityEffect(entityplayer.af(), mobeffect));
         }

         if (nbttagcompound != null && nbttagcompound.b("RootVehicle", 10)) {
            NBTTagCompound nbttagcompound1 = nbttagcompound.p("RootVehicle");
            WorldServer finalWorldServer = worldserver1;
            Entity entity = EntityTypes.a(nbttagcompound1.p("Entity"), finalWorldServer, entity1x -> !finalWorldServer.c(entity1x) ? null : entity1x);
            if (entity != null) {
               UUID uuid;
               if (nbttagcompound1.b("Attach")) {
                  uuid = nbttagcompound1.a("Attach");
               } else {
                  uuid = null;
               }

               if (entity.cs().equals(uuid)) {
                  entityplayer.a(entity, true);
               } else {
                  for(Entity entity1 : entity.cQ()) {
                     if (entity1.cs().equals(uuid)) {
                        entityplayer.a(entity1, true);
                        break;
                     }
                  }
               }

               if (!entityplayer.bL()) {
                  a.warn("Couldn't reattach entity to player");
                  entity.ai();

                  for(Entity entity1 : entity.cQ()) {
                     entity1.ai();
                  }
               }
            }
         }

         entityplayer.h();
         a.info(
            "{}[{}] logged in with entity id {} at ([{}]{}, {}, {})",
            new Object[]{entityplayer.Z().getString(), s1, entityplayer.af(), worldserver1.J.g(), entityplayer.dl(), entityplayer.dn(), entityplayer.dr()}
         );
      }
   }

   public void a(ScoreboardServer scoreboardserver, EntityPlayer entityplayer) {
      Set<ScoreboardObjective> set = Sets.newHashSet();

      for(ScoreboardTeam scoreboardteam : scoreboardserver.g()) {
         entityplayer.b.a(PacketPlayOutScoreboardTeam.a(scoreboardteam, true));
      }

      for(int i = 0; i < 19; ++i) {
         ScoreboardObjective scoreboardobjective = scoreboardserver.a(i);
         if (scoreboardobjective != null && !set.contains(scoreboardobjective)) {
            for(Packet<?> packet : scoreboardserver.d(scoreboardobjective)) {
               entityplayer.b.a(packet);
            }

            set.add(scoreboardobjective);
         }
      }
   }

   public void a(WorldServer worldserver) {
      if (this.s == null) {
         worldserver.p_().a(new IWorldBorderListener() {
            @Override
            public void a(WorldBorder worldborder, double d0) {
               PlayerList.this.broadcastAll(new ClientboundSetBorderSizePacket(worldborder), worldborder.world);
            }

            @Override
            public void a(WorldBorder worldborder, double d0, double d1, long i) {
               PlayerList.this.broadcastAll(new ClientboundSetBorderLerpSizePacket(worldborder), worldborder.world);
            }

            @Override
            public void a(WorldBorder worldborder, double d0, double d1) {
               PlayerList.this.broadcastAll(new ClientboundSetBorderCenterPacket(worldborder), worldborder.world);
            }

            @Override
            public void a(WorldBorder worldborder, int i) {
               PlayerList.this.broadcastAll(new ClientboundSetBorderWarningDelayPacket(worldborder), worldborder.world);
            }

            @Override
            public void b(WorldBorder worldborder, int i) {
               PlayerList.this.broadcastAll(new ClientboundSetBorderWarningDistancePacket(worldborder), worldborder.world);
            }

            @Override
            public void b(WorldBorder worldborder, double d0) {
            }

            @Override
            public void c(WorldBorder worldborder, double d0) {
            }
         });
      }
   }

   @Nullable
   public NBTTagCompound a(EntityPlayer entityplayer) {
      NBTTagCompound nbttagcompound = this.j.aW().y();
      NBTTagCompound nbttagcompound1;
      if (this.j.a(entityplayer.fI()) && nbttagcompound != null) {
         nbttagcompound1 = nbttagcompound;
         entityplayer.g(nbttagcompound);
         a.debug("loading single player");
      } else {
         nbttagcompound1 = this.s.b(entityplayer);
      }

      return nbttagcompound1;
   }

   protected void b(EntityPlayer entityplayer) {
      if (entityplayer.getBukkitEntity().isPersistent()) {
         this.s.a(entityplayer);
         ServerStatisticManager serverstatisticmanager = entityplayer.D();
         if (serverstatisticmanager != null) {
            serverstatisticmanager.a();
         }

         AdvancementDataPlayer advancementdataplayer = entityplayer.M();
         if (advancementdataplayer != null) {
            advancementdataplayer.b();
         }
      }
   }

   public String remove(EntityPlayer entityplayer) {
      WorldServer worldserver = entityplayer.x();
      entityplayer.a(StatisticList.j);
      if (entityplayer.bP != entityplayer.bO) {
         entityplayer.q();
      }

      PlayerQuitEvent playerQuitEvent = new PlayerQuitEvent(
         entityplayer.getBukkitEntity(), entityplayer.kickLeaveMessage != null ? entityplayer.kickLeaveMessage : "§e" + entityplayer.cu() + " left the game"
      );
      this.cserver.getPluginManager().callEvent(playerQuitEvent);
      entityplayer.getBukkitEntity().disconnect(playerQuitEvent.getQuitMessage());
      entityplayer.m();
      this.b(entityplayer);
      if (entityplayer.bL()) {
         Entity entity = entityplayer.cS();
         if (entity.cR()) {
            a.debug("Removing player mount");
            entityplayer.bz();
            entity.cP().forEach(entity1 -> entity1.b(Entity.RemovalReason.d));
         }
      }

      entityplayer.ac();
      worldserver.a(entityplayer, Entity.RemovalReason.d);
      entityplayer.M().a();
      this.k.remove(entityplayer);
      this.playersByName.remove(entityplayer.cu().toLowerCase(Locale.ROOT));
      this.j.aL().b(entityplayer);
      UUID uuid = entityplayer.cs();
      EntityPlayer entityplayer1 = this.l.get(uuid);
      if (entityplayer1 == entityplayer) {
         this.l.remove(uuid);
      }

      ClientboundPlayerInfoRemovePacket packet = new ClientboundPlayerInfoRemovePacket(List.of(entityplayer.cs()));

      for(int i = 0; i < this.k.size(); ++i) {
         EntityPlayer entityplayer2 = this.k.get(i);
         if (entityplayer2.getBukkitEntity().canSee((Player)entityplayer.getBukkitEntity())) {
            entityplayer2.b.a(packet);
         } else {
            entityplayer2.getBukkitEntity().onEntityRemove(entityplayer);
         }
      }

      this.cserver.getScoreboardManager().removePlayer(entityplayer.getBukkitEntity());
      return playerQuitEvent.getQuitMessage();
   }

   public EntityPlayer canPlayerLogin(LoginListener loginlistener, GameProfile gameprofile) {
      UUID uuid = UUIDUtil.a(gameprofile);
      List<EntityPlayer> list = Lists.newArrayList();

      for(int i = 0; i < this.k.size(); ++i) {
         EntityPlayer entityplayer = this.k.get(i);
         if (entityplayer.cs().equals(uuid)) {
            list.add(entityplayer);
         }
      }

      for(EntityPlayer entityplayer : list) {
         this.b(entityplayer);
         entityplayer.b.b(IChatBaseComponent.c("multiplayer.disconnect.duplicate_login"));
      }

      SocketAddress socketaddress = loginlistener.g.c();
      EntityPlayer entity = new EntityPlayer(this.j, this.j.a(World.h), gameprofile);
      Player player = entity.getBukkitEntity();
      PlayerLoginEvent event = new PlayerLoginEvent(
         player,
         loginlistener.g.hostname,
         ((InetSocketAddress)socketaddress).getAddress(),
         ((InetSocketAddress)loginlistener.g.m.remoteAddress()).getAddress()
      );
      if (this.f().a(gameprofile) && !this.f().b(gameprofile).f()) {
         GameProfileBanEntry gameprofilebanentry = this.m.b(gameprofile);
         IChatMutableComponent ichatmutablecomponent = IChatBaseComponent.a("multiplayer.disconnect.banned.reason", gameprofilebanentry.d());
         if (gameprofilebanentry.c() != null) {
            ichatmutablecomponent.b(IChatBaseComponent.a("multiplayer.disconnect.banned.expiration", PlayerList.i.format(gameprofilebanentry.c())));
         }

         event.disallow(Result.KICK_BANNED, CraftChatMessage.fromComponent(ichatmutablecomponent));
      } else if (!this.c(gameprofile)) {
         IChatMutableComponent ichatmutablecomponent = IChatBaseComponent.c("multiplayer.disconnect.not_whitelisted");
         event.disallow(Result.KICK_WHITELIST, SpigotConfig.whitelistMessage);
      } else if (this.g().a(socketaddress) && !this.g().b(socketaddress).f()) {
         IpBanEntry ipbanentry = this.n.b(socketaddress);
         IChatMutableComponent ichatmutablecomponent = IChatBaseComponent.a("multiplayer.disconnect.banned_ip.reason", ipbanentry.d());
         if (ipbanentry.c() != null) {
            ichatmutablecomponent.b(IChatBaseComponent.a("multiplayer.disconnect.banned_ip.expiration", PlayerList.i.format(ipbanentry.c())));
         }

         event.disallow(Result.KICK_BANNED, CraftChatMessage.fromComponent(ichatmutablecomponent));
      } else if (this.k.size() >= this.g && !this.d(gameprofile)) {
         event.disallow(Result.KICK_FULL, SpigotConfig.serverFullMessage);
      }

      this.cserver.getPluginManager().callEvent(event);
      if (event.getResult() != Result.ALLOWED) {
         loginlistener.disconnect(event.getKickMessage());
         return null;
      } else {
         return entity;
      }
   }

   public EntityPlayer getPlayerForLogin(GameProfile gameprofile, EntityPlayer player) {
      return player;
   }

   public EntityPlayer a(EntityPlayer entityplayer, boolean flag) {
      return this.respawn(entityplayer, this.j.a(entityplayer.P()), flag, null, true);
   }

   public EntityPlayer respawn(EntityPlayer entityplayer, WorldServer worldserver, boolean flag, Location location, boolean avoidSuffocation) {
      entityplayer.bz();
      this.k.remove(entityplayer);
      this.playersByName.remove(entityplayer.cu().toLowerCase(Locale.ROOT));
      entityplayer.x().a(entityplayer, Entity.RemovalReason.b);
      BlockPosition blockposition = entityplayer.N();
      float f = entityplayer.O();
      boolean flag1 = entityplayer.Q();
      EntityPlayer entityplayer1 = entityplayer;
      org.bukkit.World fromWorld = entityplayer.getBukkitEntity().getWorld();
      entityplayer.f = false;
      entityplayer.b = entityplayer.b;
      entityplayer.a(entityplayer, flag);
      entityplayer.e(entityplayer.af());
      entityplayer.a(entityplayer.fd());

      for(String s : entityplayer.ag()) {
         entityplayer1.a(s);
      }

      boolean flag2 = false;
      if (location == null) {
         boolean isBedSpawn = false;
         WorldServer worldserver1 = this.j.a(entityplayer.P());
         if (worldserver1 != null) {
            Optional optional;
            if (blockposition != null) {
               optional = EntityHuman.a(worldserver1, blockposition, f, flag1, flag);
            } else {
               optional = Optional.empty();
            }

            if (!optional.isPresent()) {
               if (blockposition != null) {
                  entityplayer1.b.a(new PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.a, 0.0F));
                  entityplayer1.a(null, null, 0.0F, false, false);
               }
            } else {
               IBlockData iblockdata = worldserver1.a_(blockposition);
               boolean flag3 = iblockdata.a(Blocks.ph);
               Vec3D vec3d = (Vec3D)optional.get();
               float f1;
               if (!iblockdata.a(TagsBlock.Q) && !flag3) {
                  f1 = f;
               } else {
                  Vec3D vec3d1 = Vec3D.c(blockposition).d(vec3d).d();
                  f1 = (float)MathHelper.d(MathHelper.d(vec3d1.e, vec3d1.c) * 180.0F / (float)Math.PI - 90.0);
               }

               entityplayer1.a(worldserver1.ab(), blockposition, f, flag1, false);
               flag2 = !flag && flag3;
               isBedSpawn = true;
               location = new Location(worldserver1.getWorld(), vec3d.c, vec3d.d, vec3d.e, f1, 0.0F);
            }
         }

         if (location == null) {
            worldserver1 = this.j.a(World.h);
            blockposition = entityplayer1.getSpawnPoint(worldserver1);
            location = new Location(
               worldserver1.getWorld(),
               (double)((float)blockposition.u() + 0.5F),
               (double)((float)blockposition.v() + 0.1F),
               (double)((float)blockposition.w() + 0.5F)
            );
         }

         Player respawnPlayer = entityplayer1.getBukkitEntity();
         PlayerRespawnEvent respawnEvent = new PlayerRespawnEvent(respawnPlayer, location, isBedSpawn && !flag2, flag2);
         this.cserver.getPluginManager().callEvent(respawnEvent);
         if (entityplayer.b.isDisconnected()) {
            return entityplayer;
         }

         location = respawnEvent.getRespawnLocation();
         if (!flag) {
            entityplayer.reset();
         }
      } else {
         location.setWorld(worldserver.getWorld());
      }

      WorldServer worldserver1 = ((CraftWorld)location.getWorld()).getHandle();
      entityplayer1.forceSetPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());

      while(avoidSuffocation && !worldserver1.g(entityplayer1) && entityplayer1.dn() < (double)worldserver1.ai()) {
         entityplayer1.e(entityplayer1.dl(), entityplayer1.dn() + 1.0, entityplayer1.dr());
      }

      int i = flag ? 1 : 0;
      WorldData worlddata = worldserver1.n_();
      entityplayer1.b
         .a(
            new PacketPlayOutRespawn(
               worldserver1.Z(),
               worldserver1.ab(),
               BiomeManager.a(worldserver1.A()),
               entityplayer1.d.b(),
               entityplayer1.d.c(),
               worldserver1.ae(),
               worldserver1.z(),
               (byte)i,
               entityplayer1.gi()
            )
         );
      entityplayer1.b.a(new PacketPlayOutViewDistance(worldserver1.spigotConfig.viewDistance));
      entityplayer1.b.a(new ClientboundSetSimulationDistancePacket(worldserver1.spigotConfig.simulationDistance));
      entityplayer1.spawnIn(worldserver1);
      entityplayer1.dD();
      entityplayer1.b
         .teleport(new Location(worldserver1.getWorld(), entityplayer1.dl(), entityplayer1.dn(), entityplayer1.dr(), entityplayer1.dw(), entityplayer1.dy()));
      entityplayer1.f(false);
      entityplayer1.b.a(new PacketPlayOutSpawnPosition(worldserver1.Q(), worldserver1.R()));
      entityplayer1.b.a(new PacketPlayOutServerDifficulty(worlddata.s(), worlddata.t()));
      entityplayer1.b.a(new PacketPlayOutExperience(entityplayer1.ce, entityplayer1.cd, entityplayer1.cc));
      this.a(entityplayer1, worldserver1);
      this.d(entityplayer1);
      if (!entityplayer.b.isDisconnected()) {
         worldserver1.d(entityplayer1);
         this.k.add(entityplayer1);
         this.playersByName.put(entityplayer1.cu().toLowerCase(Locale.ROOT), entityplayer1);
         this.l.put(entityplayer1.cs(), entityplayer1);
      }

      entityplayer1.c(entityplayer1.eo());
      if (flag2) {
         entityplayer1.b
            .a(
               new PacketPlayOutNamedSoundEffect(
                  SoundEffects.tv,
                  SoundCategory.e,
                  (double)blockposition.u(),
                  (double)blockposition.v(),
                  (double)blockposition.w(),
                  1.0F,
                  1.0F,
                  worldserver1.r_().g()
               )
            );
      }

      this.e(entityplayer);
      entityplayer.w();

      for(MobEffect mobEffect : entityplayer.el()) {
         entityplayer.b.a(new PacketPlayOutEntityEffect(entityplayer.af(), mobEffect));
      }

      entityplayer.e(((CraftWorld)fromWorld).getHandle());
      if (fromWorld != location.getWorld()) {
         PlayerChangedWorldEvent event = new PlayerChangedWorldEvent(entityplayer.getBukkitEntity(), fromWorld);
         this.j.server.getPluginManager().callEvent(event);
      }

      if (entityplayer.b.isDisconnected()) {
         this.b(entityplayer);
      }

      return entityplayer1;
   }

   public void d(EntityPlayer entityplayer) {
      GameProfile gameprofile = entityplayer.fI();
      int i = this.j.c(gameprofile);
      this.a(entityplayer, i);
   }

   public void d() {
      if (++this.A > 600) {
         for(int i = 0; i < this.k.size(); ++i) {
            final EntityPlayer target = this.k.get(i);
            target.b
               .a(
                  new ClientboundPlayerInfoUpdatePacket(
                     EnumSet.of(ClientboundPlayerInfoUpdatePacket.a.e), this.k.stream().filter(new Predicate<EntityPlayer>() {
                        public boolean test(EntityPlayer input) {
                           return target.getBukkitEntity().canSee((Player)input.getBukkitEntity());
                        }
                     }).collect(Collectors.toList())
                  )
               );
         }

         this.A = 0;
      }
   }

   public void a(Packet<?> packet) {
      for(EntityPlayer entityplayer : this.k) {
         entityplayer.b.a(packet);
      }
   }

   public void broadcastAll(Packet packet, EntityHuman entityhuman) {
      for(int i = 0; i < this.k.size(); ++i) {
         EntityPlayer entityplayer = this.k.get(i);
         if (entityhuman == null || entityplayer.getBukkitEntity().canSee(entityhuman.getBukkitEntity())) {
            this.k.get(i).b.a(packet);
         }
      }
   }

   public void broadcastAll(Packet packet, World world) {
      for(int i = 0; i < world.v().size(); ++i) {
         ((EntityPlayer)world.v().get(i)).b.a(packet);
      }
   }

   public void a(Packet<?> packet, ResourceKey<World> resourcekey) {
      for(EntityPlayer entityplayer : this.k) {
         if (entityplayer.H.ab() == resourcekey) {
            entityplayer.b.a(packet);
         }
      }
   }

   public void a(EntityHuman entityhuman, IChatBaseComponent ichatbasecomponent) {
      ScoreboardTeamBase scoreboardteambase = entityhuman.cb();
      if (scoreboardteambase != null) {
         for(String s : scoreboardteambase.g()) {
            EntityPlayer entityplayer = this.a(s);
            if (entityplayer != null && entityplayer != entityhuman) {
               entityplayer.a(ichatbasecomponent);
            }
         }
      }
   }

   public void b(EntityHuman entityhuman, IChatBaseComponent ichatbasecomponent) {
      ScoreboardTeamBase scoreboardteambase = entityhuman.cb();
      if (scoreboardteambase == null) {
         this.a(ichatbasecomponent, false);
      } else {
         for(int i = 0; i < this.k.size(); ++i) {
            EntityPlayer entityplayer = this.k.get(i);
            if (entityplayer.cb() != scoreboardteambase) {
               entityplayer.a(ichatbasecomponent);
            }
         }
      }
   }

   public String[] e() {
      String[] astring = new String[this.k.size()];

      for(int i = 0; i < this.k.size(); ++i) {
         astring[i] = this.k.get(i).fI().getName();
      }

      return astring;
   }

   public GameProfileBanList f() {
      return this.m;
   }

   public IpBanList g() {
      return this.n;
   }

   public void a(GameProfile gameprofile) {
      this.o.a(new OpListEntry(gameprofile, this.j.i(), this.o.a(gameprofile)));
      EntityPlayer entityplayer = this.a(gameprofile.getId());
      if (entityplayer != null) {
         this.d(entityplayer);
      }
   }

   public void b(GameProfile gameprofile) {
      this.o.c(gameprofile);
      EntityPlayer entityplayer = this.a(gameprofile.getId());
      if (entityplayer != null) {
         this.d(entityplayer);
      }
   }

   private void a(EntityPlayer entityplayer, int i) {
      if (entityplayer.b != null) {
         byte b0;
         if (i <= 0) {
            b0 = 24;
         } else if (i >= 4) {
            b0 = 28;
         } else {
            b0 = (byte)(24 + i);
         }

         entityplayer.b.a(new PacketPlayOutEntityStatus(entityplayer, b0));
      }

      entityplayer.getBukkitEntity().recalculatePermissions();
      this.j.aC().a(entityplayer);
   }

   public boolean c(GameProfile gameprofile) {
      return !this.t || this.o.d(gameprofile) || this.p.d(gameprofile);
   }

   public boolean f(GameProfile gameprofile) {
      return this.o.d(gameprofile) || this.j.a(gameprofile) && this.j.aW().o() || this.y;
   }

   @Nullable
   public EntityPlayer a(String s) {
      return this.playersByName.get(s.toLowerCase(Locale.ROOT));
   }

   public void a(@Nullable EntityHuman entityhuman, double d0, double d1, double d2, double d3, ResourceKey<World> resourcekey, Packet<?> packet) {
      for(int i = 0; i < this.k.size(); ++i) {
         EntityPlayer entityplayer = this.k.get(i);
         if ((entityhuman == null || entityplayer.getBukkitEntity().canSee(entityhuman.getBukkitEntity()))
            && entityplayer != entityhuman
            && entityplayer.H.ab() == resourcekey) {
            double d4 = d0 - entityplayer.dl();
            double d5 = d1 - entityplayer.dn();
            double d6 = d2 - entityplayer.dr();
            if (d4 * d4 + d5 * d5 + d6 * d6 < d3 * d3) {
               entityplayer.b.a(packet);
            }
         }
      }
   }

   public void h() {
      for(int i = 0; i < this.k.size(); ++i) {
         this.b(this.k.get(i));
      }
   }

   public WhiteList i() {
      return this.p;
   }

   public String[] j() {
      return this.p.a();
   }

   public OpList k() {
      return this.o;
   }

   public String[] l() {
      return this.o.a();
   }

   public void a() {
   }

   public void a(EntityPlayer entityplayer, WorldServer worldserver) {
      WorldBorder worldborder = entityplayer.H.p_();
      entityplayer.b.a(new ClientboundInitializeBorderPacket(worldborder));
      entityplayer.b.a(new PacketPlayOutUpdateTime(worldserver.U(), worldserver.V(), worldserver.W().b(GameRules.k)));
      entityplayer.b.a(new PacketPlayOutSpawnPosition(worldserver.Q(), worldserver.R()));
      if (worldserver.Y()) {
         entityplayer.setPlayerWeather(WeatherType.DOWNFALL, false);
         entityplayer.updateWeather(-worldserver.w, worldserver.w, -worldserver.y, worldserver.y);
      }
   }

   public void e(EntityPlayer entityplayer) {
      entityplayer.bO.b();
      entityplayer.getBukkitEntity().updateScaledHealth();
      entityplayer.aj().refresh(entityplayer);
      entityplayer.b.a(new PacketPlayOutHeldItemSlot(entityplayer.fJ().l));
      int i = entityplayer.H.W().b(GameRules.p) ? 22 : 23;
      entityplayer.b.a(new PacketPlayOutEntityStatus(entityplayer, (byte)i));
      float immediateRespawn = entityplayer.H.W().b(GameRules.B) ? 1.0F : 0.0F;
      entityplayer.b.a(new PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.l, immediateRespawn));
   }

   public int m() {
      return this.k.size();
   }

   public int n() {
      return this.g;
   }

   public boolean o() {
      return this.t;
   }

   public void a(boolean flag) {
      this.t = flag;
   }

   public List<EntityPlayer> b(String s) {
      List<EntityPlayer> list = Lists.newArrayList();

      for(EntityPlayer entityplayer : this.k) {
         if (entityplayer.y().equals(s)) {
            list.add(entityplayer);
         }
      }

      return list;
   }

   public int p() {
      return this.w;
   }

   public int q() {
      return this.x;
   }

   public MinecraftServer c() {
      return this.j;
   }

   @Nullable
   public NBTTagCompound r() {
      return null;
   }

   public void b(boolean flag) {
      this.y = flag;
   }

   public void s() {
      for(EntityPlayer player : this.k) {
         player.b.disconnect(this.j.server.getShutdownMessage());
      }
   }

   public void broadcastMessage(IChatBaseComponent[] iChatBaseComponents) {
      for(IChatBaseComponent component : iChatBaseComponents) {
         this.a(component, false);
      }
   }

   public void a(IChatBaseComponent ichatbasecomponent, boolean flag) {
      this.a(ichatbasecomponent, entityplayer -> ichatbasecomponent, flag);
   }

   public void a(IChatBaseComponent ichatbasecomponent, Function<EntityPlayer, IChatBaseComponent> function, boolean flag) {
      this.j.a(ichatbasecomponent);

      for(EntityPlayer entityplayer : this.k) {
         IChatBaseComponent ichatbasecomponent1 = function.apply(entityplayer);
         if (ichatbasecomponent1 != null) {
            entityplayer.b(ichatbasecomponent1, flag);
         }
      }
   }

   public void a(PlayerChatMessage playerchatmessage, CommandListenerWrapper commandlistenerwrapper, ChatMessageType.a chatmessagetype_a) {
      this.a(playerchatmessage, commandlistenerwrapper::a, commandlistenerwrapper.i(), chatmessagetype_a);
   }

   public void a(PlayerChatMessage playerchatmessage, EntityPlayer entityplayer, ChatMessageType.a chatmessagetype_a) {
      this.a(playerchatmessage, entityplayer::b, entityplayer, chatmessagetype_a);
   }

   private void a(
      PlayerChatMessage playerchatmessage, Predicate<EntityPlayer> predicate, @Nullable EntityPlayer entityplayer, ChatMessageType.a chatmessagetype_a
   ) {
      boolean flag = this.a(playerchatmessage);
      this.j.a(playerchatmessage.c(), chatmessagetype_a, flag ? null : "Not Secure");
      OutgoingChatMessage outgoingchatmessage = OutgoingChatMessage.a(playerchatmessage);
      boolean flag1 = false;

      for(EntityPlayer entityplayer1 : this.k) {
         boolean flag2 = predicate.test(entityplayer1);
         entityplayer1.a(outgoingchatmessage, flag2, chatmessagetype_a);
         flag1 |= flag2 && playerchatmessage.i();
      }

      if (flag1 && entityplayer != null) {
         entityplayer.a(f);
      }
   }

   private boolean a(PlayerChatMessage playerchatmessage) {
      return playerchatmessage.h() && !playerchatmessage.a(Instant.now());
   }

   public ServerStatisticManager getPlayerStats(EntityPlayer entityhuman) {
      ServerStatisticManager serverstatisticmanager = entityhuman.D();
      return serverstatisticmanager == null ? this.getPlayerStats(entityhuman.cs(), entityhuman.G_().getString()) : serverstatisticmanager;
   }

   public ServerStatisticManager getPlayerStats(UUID uuid, String displayName) {
      EntityPlayer entityhuman = this.a(uuid);
      ServerStatisticManager serverstatisticmanager = entityhuman == null ? null : entityhuman.D();
      if (serverstatisticmanager == null) {
         File file = this.j.a(SavedFile.b).toFile();
         File file1 = new File(file, uuid + ".json");
         if (!file1.exists()) {
            File file2 = new File(file, displayName + ".json");
            Path path = file2.toPath();
            if (FileUtils.a(path) && FileUtils.b(path) && path.startsWith(file.getPath()) && file2.isFile()) {
               file2.renameTo(file1);
            }
         }

         serverstatisticmanager = new ServerStatisticManager(this.j, file1);
      }

      return serverstatisticmanager;
   }

   public AdvancementDataPlayer f(EntityPlayer entityplayer) {
      UUID uuid = entityplayer.cs();
      AdvancementDataPlayer advancementdataplayer = entityplayer.M();
      if (advancementdataplayer == null) {
         Path path = this.j.a(SavedFile.a).resolve(uuid + ".json");
         advancementdataplayer = new AdvancementDataPlayer(this.j.ay(), this, this.j.az(), path, entityplayer);
      }

      advancementdataplayer.a(entityplayer);
      return advancementdataplayer;
   }

   public void a(int i) {
      this.w = i;
      this.a(new PacketPlayOutViewDistance(i));

      for(WorldServer worldserver : this.j.F()) {
         if (worldserver != null) {
            worldserver.k().a(i);
         }
      }
   }

   public void b(int i) {
      this.x = i;
      this.a(new ClientboundSetSimulationDistancePacket(i));

      for(WorldServer worldserver : this.j.F()) {
         if (worldserver != null) {
            worldserver.k().b(i);
         }
      }
   }

   public List<EntityPlayer> t() {
      return this.k;
   }

   @Nullable
   public EntityPlayer a(UUID uuid) {
      return this.l.get(uuid);
   }

   public boolean d(GameProfile gameprofile) {
      return false;
   }

   public void u() {
      for(EntityPlayer player : this.k) {
         player.M().a(this.j.az());
         player.M().b(player);
      }

      this.a(new PacketPlayOutTags(TagNetworkSerialization.a(this.u)));
      PacketPlayOutRecipeUpdate packetplayoutrecipeupdate = new PacketPlayOutRecipeUpdate(this.j.aE().b());

      for(EntityPlayer entityplayer : this.k) {
         entityplayer.b.a(packetplayoutrecipeupdate);
         entityplayer.E().a(entityplayer);
      }
   }

   public boolean v() {
      return this.y;
   }
}
