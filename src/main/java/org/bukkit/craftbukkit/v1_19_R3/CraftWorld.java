package org.bukkit.craftbukkit.v1_19_R3;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.network.protocol.game.PacketPlayOutEntitySound;
import net.minecraft.network.protocol.game.PacketPlayOutNamedSoundEffect;
import net.minecraft.network.protocol.game.PacketPlayOutUpdateTime;
import net.minecraft.network.protocol.game.PacketPlayOutWorldEvent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.ChunkMapDistance;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.PlayerChunk;
import net.minecraft.server.level.PlayerChunkMap;
import net.minecraft.server.level.Ticket;
import net.minecraft.server.level.TicketType;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.util.ArraySetSorted;
import net.minecraft.util.Unit;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.entity.EntityLightning;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.item.EntityFallingBlock;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.EntityArrow;
import net.minecraft.world.entity.projectile.EntityTippedArrow;
import net.minecraft.world.entity.raid.PersistentRaid;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.RayTrace;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.IChunkAccess;
import net.minecraft.world.level.chunk.ProtoChunkExtension;
import net.minecraft.world.level.storage.SavedFile;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.MovingObjectPosition;
import net.minecraft.world.phys.Vec3D;
import org.apache.commons.lang.Validate;
import org.bukkit.BlockChangeDelegate;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Difficulty;
import org.bukkit.Effect;
import org.bukkit.FluidCollisionMode;
import org.bukkit.GameRule;
import org.bukkit.HeightMap;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Raid;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.StructureType;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.WorldType;
import org.bukkit.World.Environment;
import org.bukkit.World.Spigot;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.boss.DragonBattle;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlockState;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_19_R3.boss.CraftDragonBattle;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R3.generator.strucutre.CraftStructure;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_19_R3.metadata.BlockMetadataStore;
import org.bukkit.craftbukkit.v1_19_R3.persistence.CraftPersistentDataContainer;
import org.bukkit.craftbukkit.v1_19_R3.persistence.CraftPersistentDataTypeRegistry;
import org.bukkit.craftbukkit.v1_19_R3.potion.CraftPotionUtil;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftMagicNumbers;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftRayTraceResult;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftSpawnCategory;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftStructureSearchResult;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.entity.TippedArrow;
import org.bukkit.entity.Trident;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.weather.LightningStrikeEvent.Cause;
import org.bukkit.event.world.SpawnChangeEvent;
import org.bukkit.event.world.TimeSkipEvent;
import org.bukkit.event.world.TimeSkipEvent.SkipReason;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.structure.Structure;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.StandardMessenger;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Consumer;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.StructureSearchResult;
import org.bukkit.util.Vector;
import org.spigotmc.AsyncCatcher;

public class CraftWorld extends CraftRegionAccessor implements World {
   public static final int CUSTOM_DIMENSION_OFFSET = 10;
   private static final CraftPersistentDataTypeRegistry DATA_TYPE_REGISTRY = new CraftPersistentDataTypeRegistry();
   private final WorldServer world;
   private WorldBorder worldBorder;
   private Environment environment;
   private final CraftServer server = (CraftServer)Bukkit.getServer();
   private final ChunkGenerator generator;
   private final BiomeProvider biomeProvider;
   private final List<BlockPopulator> populators = new ArrayList();
   private final BlockMetadataStore blockMetadata = new BlockMetadataStore(this);
   private final Object2IntOpenHashMap<SpawnCategory> spawnCategoryLimit = new Object2IntOpenHashMap();
   private final CraftPersistentDataContainer persistentDataContainer = new CraftPersistentDataContainer(DATA_TYPE_REGISTRY);
   private static final Random rand = new Random();
   private static Map<String, GameRules.GameRuleKey<?>> gamerules;
   private static Map<String, GameRules.GameRuleDefinition<?>> gameruleDefinitions;
   private final Spigot spigot = new Spigot() {
      public LightningStrike strikeLightning(Location loc, boolean isSilent) {
         EntityLightning lightning = EntityTypes.ai.a((net.minecraft.world.level.World)CraftWorld.this.world);
         lightning.d(loc.getX(), loc.getY(), loc.getZ());
         lightning.isSilent = isSilent;
         CraftWorld.this.world.strikeLightning(lightning, Cause.CUSTOM);
         return (LightningStrike)lightning.getBukkitEntity();
      }

      public LightningStrike strikeLightningEffect(Location loc, boolean isSilent) {
         EntityLightning lightning = EntityTypes.ai.a((net.minecraft.world.level.World)CraftWorld.this.world);
         lightning.d(loc.getX(), loc.getY(), loc.getZ());
         lightning.h = true;
         lightning.isSilent = isSilent;
         CraftWorld.this.world.strikeLightning(lightning, Cause.CUSTOM);
         return (LightningStrike)lightning.getBukkitEntity();
      }
   };

   public CraftWorld(WorldServer world, ChunkGenerator gen, BiomeProvider biomeProvider, Environment env) {
      this.world = world;
      this.generator = gen;
      this.biomeProvider = biomeProvider;
      this.environment = env;
   }

   public Block getBlockAt(int x, int y, int z) {
      return CraftBlock.at(this.world, new BlockPosition(x, y, z));
   }

   public int getHighestBlockYAt(int x, int z) {
      return this.getHighestBlockYAt(x, z, HeightMap.MOTION_BLOCKING);
   }

   public Location getSpawnLocation() {
      BlockPosition spawn = this.world.Q();
      float yaw = this.world.R();
      return new Location(this, (double)spawn.u(), (double)spawn.v(), (double)spawn.w(), yaw, 0.0F);
   }

   public boolean setSpawnLocation(Location location) {
      Preconditions.checkArgument(location != null, "location");
      return this.equals(location.getWorld())
         ? this.setSpawnLocation(location.getBlockX(), location.getBlockY(), location.getBlockZ(), location.getYaw())
         : false;
   }

   public boolean setSpawnLocation(int x, int y, int z, float angle) {
      try {
         Location previousLocation = this.getSpawnLocation();
         this.world.A.a(new BlockPosition(x, y, z), angle);
         SpawnChangeEvent event = new SpawnChangeEvent(this, previousLocation);
         this.server.getPluginManager().callEvent(event);
         return true;
      } catch (Exception var7) {
         return false;
      }
   }

   public boolean setSpawnLocation(int x, int y, int z) {
      return this.setSpawnLocation(x, y, z, 0.0F);
   }

   public Chunk getChunkAt(int x, int z) {
      return this.world.k().a(x, z, true).bukkitChunk;
   }

   public Chunk getChunkAt(Block block) {
      Preconditions.checkArgument(block != null, "null block");
      return this.getChunkAt(block.getX() >> 4, block.getZ() >> 4);
   }

   public boolean isChunkLoaded(int x, int z) {
      return this.world.k().isChunkLoaded(x, z);
   }

   public boolean isChunkGenerated(int x, int z) {
      try {
         return this.isChunkLoaded(x, z) || this.world.k().a.f(new ChunkCoordIntPair(x, z)).get().isPresent();
      } catch (ExecutionException | InterruptedException var4) {
         throw new RuntimeException(var4);
      }
   }

   public Chunk[] getLoadedChunks() {
      Long2ObjectLinkedOpenHashMap<PlayerChunk> chunks = this.world.k().a.o;
      return chunks.values()
         .stream()
         .map(PlayerChunk::getFullChunkNow)
         .filter(Objects::nonNull)
         .map(net.minecraft.world.level.chunk.Chunk::getBukkitChunk)
         .toArray(var0 -> new Chunk[var0]);
   }

   public void loadChunk(int x, int z) {
      this.loadChunk(x, z, true);
   }

   public boolean unloadChunk(Chunk chunk) {
      return this.unloadChunk(chunk.getX(), chunk.getZ());
   }

   public boolean unloadChunk(int x, int z) {
      return this.unloadChunk(x, z, true);
   }

   public boolean unloadChunk(int x, int z, boolean save) {
      return this.unloadChunk0(x, z, save);
   }

   public boolean unloadChunkRequest(int x, int z) {
      AsyncCatcher.catchOp("chunk unload");
      if (this.isChunkLoaded(x, z)) {
         this.world.k().b(TicketType.PLUGIN, new ChunkCoordIntPair(x, z), 1, Unit.a);
      }

      return true;
   }

   private boolean unloadChunk0(int x, int z, boolean save) {
      AsyncCatcher.catchOp("chunk unload");
      if (!this.isChunkLoaded(x, z)) {
         return true;
      } else {
         net.minecraft.world.level.chunk.Chunk chunk = this.world.d(x, z);
         chunk.mustNotSave = !save;
         this.unloadChunkRequest(x, z);
         this.world.k().purgeUnload();
         return !this.isChunkLoaded(x, z);
      }
   }

   public boolean regenerateChunk(int x, int z) {
      AsyncCatcher.catchOp("chunk regenerate");
      throw new UnsupportedOperationException("Not supported in this Minecraft version! Unless you can fix it, this is not a bug :)");
   }

   public boolean refreshChunk(int x, int z) {
      PlayerChunk playerChunk = (PlayerChunk)this.world.k().a.o.get(ChunkCoordIntPair.c(x, z));
      if (playerChunk == null) {
         return false;
      } else {
         playerChunk.a().thenAccept(either -> either.left().ifPresent(chunk -> {
               List<EntityPlayer> playersInRange = playerChunk.z.a(playerChunk.j(), false);
               if (!playersInRange.isEmpty()) {
                  ClientboundLevelChunkWithLightPacket refreshPacket = new ClientboundLevelChunkWithLightPacket(chunk, this.world.l_(), null, null, true);

                  for(EntityPlayer player : playersInRange) {
                     if (player.b != null) {
                        player.b.a(refreshPacket);
                     }
                  }
               }
            }));
         return true;
      }
   }

   public boolean isChunkInUse(int x, int z) {
      return this.isChunkLoaded(x, z);
   }

   public boolean loadChunk(int x, int z, boolean generate) {
      AsyncCatcher.catchOp("chunk load");
      IChunkAccess chunk = this.world.k().a(x, z, generate ? ChunkStatus.o : ChunkStatus.c, true);
      if (chunk instanceof ProtoChunkExtension) {
         chunk = this.world.k().a(x, z, ChunkStatus.o, true);
      }

      if (chunk instanceof net.minecraft.world.level.chunk.Chunk) {
         this.world.k().a(TicketType.PLUGIN, new ChunkCoordIntPair(x, z), 1, Unit.a);
         return true;
      } else {
         return false;
      }
   }

   public boolean isChunkLoaded(Chunk chunk) {
      Preconditions.checkArgument(chunk != null, "null chunk");
      return this.isChunkLoaded(chunk.getX(), chunk.getZ());
   }

   public void loadChunk(Chunk chunk) {
      Preconditions.checkArgument(chunk != null, "null chunk");
      this.loadChunk(chunk.getX(), chunk.getZ());
      ((CraftChunk)this.getChunkAt(chunk.getX(), chunk.getZ())).getHandle().bukkitChunk = chunk;
   }

   public boolean addPluginChunkTicket(int x, int z, Plugin plugin) {
      Preconditions.checkArgument(plugin != null, "null plugin");
      Preconditions.checkArgument(plugin.isEnabled(), "plugin is not enabled");
      ChunkMapDistance chunkDistanceManager = this.world.k().a.G;
      if (chunkDistanceManager.addRegionTicketAtDistance(TicketType.PLUGIN_TICKET, new ChunkCoordIntPair(x, z), 2, plugin)) {
         this.getChunkAt(x, z);
         return true;
      } else {
         return false;
      }
   }

   public boolean removePluginChunkTicket(int x, int z, Plugin plugin) {
      Preconditions.checkNotNull(plugin, "null plugin");
      ChunkMapDistance chunkDistanceManager = this.world.k().a.G;
      return chunkDistanceManager.removeRegionTicketAtDistance(TicketType.PLUGIN_TICKET, new ChunkCoordIntPair(x, z), 2, plugin);
   }

   public void removePluginChunkTickets(Plugin plugin) {
      Preconditions.checkNotNull(plugin, "null plugin");
      ChunkMapDistance chunkDistanceManager = this.world.k().a.G;
      chunkDistanceManager.removeAllTicketsFor(TicketType.PLUGIN_TICKET, 31, plugin);
   }

   public Collection<Plugin> getPluginChunkTickets(int x, int z) {
      ChunkMapDistance chunkDistanceManager = this.world.k().a.G;
      ArraySetSorted<Ticket<?>> tickets = (ArraySetSorted)chunkDistanceManager.h.get(ChunkCoordIntPair.c(x, z));
      if (tickets == null) {
         return Collections.emptyList();
      } else {
         Builder<Plugin> ret = ImmutableList.builder();

         for(Ticket<?> ticket : tickets) {
            if (ticket.a() == TicketType.PLUGIN_TICKET) {
               ret.add((Plugin)ticket.c);
            }
         }

         return ret.build();
      }
   }

   public Map<Plugin, Collection<Chunk>> getPluginChunkTickets() {
      Map<Plugin, Builder<Chunk>> ret = new HashMap();
      ChunkMapDistance chunkDistanceManager = this.world.k().a.G;

      for(Entry<ArraySetSorted<Ticket<?>>> chunkTickets : chunkDistanceManager.h.long2ObjectEntrySet()) {
         long chunkKey = chunkTickets.getLongKey();
         ArraySetSorted<Ticket<?>> tickets = (ArraySetSorted)chunkTickets.getValue();
         Chunk chunk = null;

         for(Ticket<?> ticket : tickets) {
            if (ticket.a() == TicketType.PLUGIN_TICKET) {
               if (chunk == null) {
                  chunk = this.getChunkAt(ChunkCoordIntPair.a(chunkKey), ChunkCoordIntPair.b(chunkKey));
               }

               ((Builder)ret.computeIfAbsent((Plugin)ticket.c, key -> ImmutableList.builder())).add(chunk);
            }
         }
      }

      return ret.entrySet().stream().collect(ImmutableMap.toImmutableMap(java.util.Map.Entry::getKey, entry -> ((Builder)entry.getValue()).build()));
   }

   public boolean isChunkForceLoaded(int x, int z) {
      return this.getHandle().u().contains(ChunkCoordIntPair.c(x, z));
   }

   public void setChunkForceLoaded(int x, int z, boolean forced) {
      this.getHandle().a(x, z, forced);
   }

   public Collection<Chunk> getForceLoadedChunks() {
      Set<Chunk> chunks = new HashSet();

      for(long coord : this.getHandle().u()) {
         chunks.add(this.getChunkAt(ChunkCoordIntPair.a(coord), ChunkCoordIntPair.b(coord)));
      }

      return Collections.unmodifiableCollection(chunks);
   }

   public WorldServer getHandle() {
      return this.world;
   }

   public Item dropItem(Location loc, ItemStack item) {
      return this.dropItem(loc, item, null);
   }

   public Item dropItem(Location loc, ItemStack item, Consumer<Item> function) {
      Validate.notNull(item, "Cannot drop a Null item.");
      EntityItem entity = new EntityItem(this.world, loc.getX(), loc.getY(), loc.getZ(), CraftItemStack.asNMSCopy(item));
      entity.h = 10;
      if (function != null) {
         function.accept((Item)entity.getBukkitEntity());
      }

      this.world.addFreshEntity(entity, SpawnReason.CUSTOM);
      return (Item)entity.getBukkitEntity();
   }

   public Item dropItemNaturally(Location loc, ItemStack item) {
      return this.dropItemNaturally(loc, item, null);
   }

   public Item dropItemNaturally(Location loc, ItemStack item, Consumer<Item> function) {
      double xs = (double)(this.world.z.i() * 0.5F) + 0.25;
      double ys = (double)(this.world.z.i() * 0.5F) + 0.25;
      double zs = (double)(this.world.z.i() * 0.5F) + 0.25;
      loc = loc.clone();
      loc.setX(loc.getX() + xs);
      loc.setY(loc.getY() + ys);
      loc.setZ(loc.getZ() + zs);
      return this.dropItem(loc, item, function);
   }

   public Arrow spawnArrow(Location loc, Vector velocity, float speed, float spread) {
      return this.spawnArrow(loc, velocity, speed, spread, Arrow.class);
   }

   public <T extends AbstractArrow> T spawnArrow(Location loc, Vector velocity, float speed, float spread, Class<T> clazz) {
      Validate.notNull(loc, "Can not spawn arrow with a null location");
      Validate.notNull(velocity, "Can not spawn arrow with a null velocity");
      Validate.notNull(clazz, "Can not spawn an arrow with no class");
      EntityArrow arrow;
      if (TippedArrow.class.isAssignableFrom(clazz)) {
         arrow = EntityTypes.e.a((net.minecraft.world.level.World)this.world);
         ((EntityTippedArrow)arrow).setPotionType(CraftPotionUtil.fromBukkit(new PotionData(PotionType.WATER, false, false)));
      } else if (SpectralArrow.class.isAssignableFrom(clazz)) {
         arrow = EntityTypes.aR.a((net.minecraft.world.level.World)this.world);
      } else if (Trident.class.isAssignableFrom(clazz)) {
         arrow = EntityTypes.bb.a((net.minecraft.world.level.World)this.world);
      } else {
         arrow = EntityTypes.e.a((net.minecraft.world.level.World)this.world);
      }

      arrow.b(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
      arrow.c(velocity.getX(), velocity.getY(), velocity.getZ(), speed, spread);
      this.world.b(arrow);
      return (T)arrow.getBukkitEntity();
   }

   public LightningStrike strikeLightning(Location loc) {
      EntityLightning lightning = EntityTypes.ai.a((net.minecraft.world.level.World)this.world);
      lightning.d(loc.getX(), loc.getY(), loc.getZ());
      this.world.strikeLightning(lightning, Cause.CUSTOM);
      return (LightningStrike)lightning.getBukkitEntity();
   }

   public LightningStrike strikeLightningEffect(Location loc) {
      EntityLightning lightning = EntityTypes.ai.a((net.minecraft.world.level.World)this.world);
      lightning.d(loc.getX(), loc.getY(), loc.getZ());
      lightning.a(true);
      this.world.strikeLightning(lightning, Cause.CUSTOM);
      return (LightningStrike)lightning.getBukkitEntity();
   }

   public boolean generateTree(Location loc, TreeType type) {
      return this.generateTree(loc, rand, type);
   }

   public boolean generateTree(Location loc, TreeType type, BlockChangeDelegate delegate) {
      this.world.captureTreeGeneration = true;
      this.world.captureBlockStates = true;
      boolean grownTree = this.generateTree(loc, type);
      this.world.captureBlockStates = false;
      this.world.captureTreeGeneration = false;
      if (!grownTree) {
         this.world.capturedBlockStates.clear();
         return false;
      } else {
         for(BlockState blockstate : this.world.capturedBlockStates.values()) {
            BlockPosition position = ((CraftBlockState)blockstate).getPosition();
            IBlockData oldBlock = this.world.a_(position);
            int flag = ((CraftBlockState)blockstate).getFlag();
            delegate.setBlockData(blockstate.getX(), blockstate.getY(), blockstate.getZ(), blockstate.getBlockData());
            IBlockData newBlock = this.world.a_(position);
            this.world.notifyAndUpdatePhysics(position, null, oldBlock, newBlock, newBlock, flag, 512);
         }

         this.world.capturedBlockStates.clear();
         return true;
      }
   }

   public String getName() {
      return this.world.J.g();
   }

   public UUID getUID() {
      return this.world.uuid;
   }

   public NamespacedKey getKey() {
      return CraftNamespacedKey.fromMinecraft(this.world.ab().a());
   }

   @Override
   public String toString() {
      return "CraftWorld{name=" + this.getName() + 125;
   }

   public long getTime() {
      long time = this.getFullTime() % 24000L;
      if (time < 0L) {
         time += 24000L;
      }

      return time;
   }

   public void setTime(long time) {
      long margin = (time - this.getFullTime()) % 24000L;
      if (margin < 0L) {
         margin += 24000L;
      }

      this.setFullTime(this.getFullTime() + margin);
   }

   public long getFullTime() {
      return this.world.V();
   }

   public void setFullTime(long time) {
      TimeSkipEvent event = new TimeSkipEvent(this, SkipReason.CUSTOM, time - this.world.V());
      this.server.getPluginManager().callEvent(event);
      if (!event.isCancelled()) {
         this.world.b(this.world.V() + event.getSkipAmount());

         for(Player p : this.getPlayers()) {
            CraftPlayer cp = (CraftPlayer)p;
            if (cp.getHandle().b != null) {
               cp.getHandle().b.a(new PacketPlayOutUpdateTime(cp.getHandle().H.U(), cp.getHandle().getPlayerTime(), cp.getHandle().H.W().b(GameRules.k)));
            }
         }
      }
   }

   public long getGameTime() {
      return this.world.A.e();
   }

   public boolean createExplosion(double x, double y, double z, float power) {
      return this.createExplosion(x, y, z, power, false, true);
   }

   public boolean createExplosion(double x, double y, double z, float power, boolean setFire) {
      return this.createExplosion(x, y, z, power, setFire, true);
   }

   public boolean createExplosion(double x, double y, double z, float power, boolean setFire, boolean breakBlocks) {
      return this.createExplosion(x, y, z, power, setFire, breakBlocks, null);
   }

   public boolean createExplosion(double x, double y, double z, float power, boolean setFire, boolean breakBlocks, Entity source) {
      return !this.world
         .a(
            source == null ? null : ((CraftEntity)source).getHandle(),
            x,
            y,
            z,
            power,
            setFire,
            breakBlocks ? net.minecraft.world.level.World.a.c : net.minecraft.world.level.World.a.a
         )
         .wasCanceled;
   }

   public boolean createExplosion(Location loc, float power) {
      return this.createExplosion(loc, power, false);
   }

   public boolean createExplosion(Location loc, float power, boolean setFire) {
      return this.createExplosion(loc, power, setFire, true);
   }

   public boolean createExplosion(Location loc, float power, boolean setFire, boolean breakBlocks) {
      return this.createExplosion(loc, power, setFire, breakBlocks, null);
   }

   public boolean createExplosion(Location loc, float power, boolean setFire, boolean breakBlocks, Entity source) {
      Preconditions.checkArgument(loc != null, "Location is null");
      Preconditions.checkArgument(this.equals(loc.getWorld()), "Location not in world");
      return this.createExplosion(loc.getX(), loc.getY(), loc.getZ(), power, setFire, breakBlocks, source);
   }

   public Environment getEnvironment() {
      return this.environment;
   }

   public Block getBlockAt(Location location) {
      return this.getBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());
   }

   public int getHighestBlockYAt(Location location) {
      return this.getHighestBlockYAt(location.getBlockX(), location.getBlockZ());
   }

   public Chunk getChunkAt(Location location) {
      return this.getChunkAt(location.getBlockX() >> 4, location.getBlockZ() >> 4);
   }

   public ChunkGenerator getGenerator() {
      return this.generator;
   }

   public BiomeProvider getBiomeProvider() {
      return this.biomeProvider;
   }

   public List<BlockPopulator> getPopulators() {
      return this.populators;
   }

   public Block getHighestBlockAt(int x, int z) {
      return this.getBlockAt(x, this.getHighestBlockYAt(x, z), z);
   }

   public Block getHighestBlockAt(Location location) {
      return this.getHighestBlockAt(location.getBlockX(), location.getBlockZ());
   }

   public int getHighestBlockYAt(int x, int z, HeightMap heightMap) {
      return this.world.d(x >> 4, z >> 4).a(CraftHeightMap.toNMS(heightMap), x, z);
   }

   public int getHighestBlockYAt(Location location, HeightMap heightMap) {
      return this.getHighestBlockYAt(location.getBlockX(), location.getBlockZ(), heightMap);
   }

   public Block getHighestBlockAt(int x, int z, HeightMap heightMap) {
      return this.getBlockAt(x, this.getHighestBlockYAt(x, z, heightMap), z);
   }

   public Block getHighestBlockAt(Location location, HeightMap heightMap) {
      return this.getHighestBlockAt(location.getBlockX(), location.getBlockZ(), heightMap);
   }

   public Biome getBiome(int x, int z) {
      return this.getBiome(x, 0, z);
   }

   public void setBiome(int x, int z, Biome bio) {
      for(int y = this.getMinHeight(); y < this.getMaxHeight(); ++y) {
         this.setBiome(x, y, z, bio);
      }
   }

   @Override
   public void setBiome(int x, int y, int z, Holder<BiomeBase> bb) {
      BlockPosition pos = new BlockPosition(x, 0, z);
      if (this.world.D(pos)) {
         net.minecraft.world.level.chunk.Chunk chunk = this.world.l(pos);
         if (chunk != null) {
            chunk.setBiome(x >> 2, y >> 2, z >> 2, bb);
            chunk.a(true);
         }
      }
   }

   public double getTemperature(int x, int z) {
      return this.getTemperature(x, 0, z);
   }

   public double getTemperature(int x, int y, int z) {
      BlockPosition pos = new BlockPosition(x, y, z);
      return (double)this.world.getNoiseBiome(x >> 2, y >> 2, z >> 2).a().f(pos);
   }

   public double getHumidity(int x, int z) {
      return this.getHumidity(x, 0, z);
   }

   public double getHumidity(int x, int y, int z) {
      return (double)this.world.getNoiseBiome(x >> 2, y >> 2, z >> 2).a().i.d();
   }

   @Deprecated
   public <T extends Entity> Collection<T> getEntitiesByClass(Class<T>... classes) {
      return this.getEntitiesByClasses(classes);
   }

   @Override
   public Iterable<net.minecraft.world.entity.Entity> getNMSEntities() {
      return this.getHandle().E().a();
   }

   @Override
   public void addEntityToWorld(net.minecraft.world.entity.Entity entity, SpawnReason reason) {
      this.getHandle().addFreshEntity(entity, reason);
   }

   public Collection<Entity> getNearbyEntities(Location location, double x, double y, double z) {
      return this.getNearbyEntities(location, x, y, z, null);
   }

   public Collection<Entity> getNearbyEntities(Location location, double x, double y, double z, Predicate<Entity> filter) {
      Validate.notNull(location, "Location is null!");
      Validate.isTrue(this.equals(location.getWorld()), "Location is from different world!");
      BoundingBox aabb = BoundingBox.of(location, x, y, z);
      return this.getNearbyEntities(aabb, filter);
   }

   public Collection<Entity> getNearbyEntities(BoundingBox boundingBox) {
      return this.getNearbyEntities(boundingBox, null);
   }

   public Collection<Entity> getNearbyEntities(BoundingBox boundingBox, Predicate<Entity> filter) {
      AsyncCatcher.catchOp("getNearbyEntities");
      Validate.notNull(boundingBox, "Bounding box is null!");
      AxisAlignedBB bb = new AxisAlignedBB(
         boundingBox.getMinX(), boundingBox.getMinY(), boundingBox.getMinZ(), boundingBox.getMaxX(), boundingBox.getMaxY(), boundingBox.getMaxZ()
      );
      List<net.minecraft.world.entity.Entity> entityList = this.getHandle().a(null, bb, Predicates.alwaysTrue());
      List<Entity> bukkitEntityList = new ArrayList(entityList.size());

      for(net.minecraft.world.entity.Entity entity : entityList) {
         Entity bukkitEntity = entity.getBukkitEntity();
         if (filter == null || filter.test(bukkitEntity)) {
            bukkitEntityList.add(bukkitEntity);
         }
      }

      return bukkitEntityList;
   }

   public RayTraceResult rayTraceEntities(Location start, Vector direction, double maxDistance) {
      return this.rayTraceEntities(start, direction, maxDistance, null);
   }

   public RayTraceResult rayTraceEntities(Location start, Vector direction, double maxDistance, double raySize) {
      return this.rayTraceEntities(start, direction, maxDistance, raySize, null);
   }

   public RayTraceResult rayTraceEntities(Location start, Vector direction, double maxDistance, Predicate<Entity> filter) {
      return this.rayTraceEntities(start, direction, maxDistance, 0.0, filter);
   }

   public RayTraceResult rayTraceEntities(Location start, Vector direction, double maxDistance, double raySize, Predicate<Entity> filter) {
      Validate.notNull(start, "Start location is null!");
      Validate.isTrue(this.equals(start.getWorld()), "Start location is from different world!");
      start.checkFinite();
      Validate.notNull(direction, "Direction is null!");
      direction.checkFinite();
      Validate.isTrue(direction.lengthSquared() > 0.0, "Direction's magnitude is 0!");
      if (maxDistance < 0.0) {
         return null;
      } else {
         Vector startPos = start.toVector();
         Vector dir = direction.clone().normalize().multiply(maxDistance);
         BoundingBox aabb = BoundingBox.of(startPos, startPos).expandDirectional(dir).expand(raySize);
         Collection<Entity> entities = this.getNearbyEntities(aabb, filter);
         Entity nearestHitEntity = null;
         RayTraceResult nearestHitResult = null;
         double nearestDistanceSq = Double.MAX_VALUE;

         for(Entity entity : entities) {
            BoundingBox boundingBox = entity.getBoundingBox().expand(raySize);
            RayTraceResult hitResult = boundingBox.rayTrace(startPos, direction, maxDistance);
            if (hitResult != null) {
               double distanceSq = startPos.distanceSquared(hitResult.getHitPosition());
               if (distanceSq < nearestDistanceSq) {
                  nearestHitEntity = entity;
                  nearestHitResult = hitResult;
                  nearestDistanceSq = distanceSq;
               }
            }
         }

         return nearestHitEntity == null ? null : new RayTraceResult(nearestHitResult.getHitPosition(), nearestHitEntity, nearestHitResult.getHitBlockFace());
      }
   }

   public RayTraceResult rayTraceBlocks(Location start, Vector direction, double maxDistance) {
      return this.rayTraceBlocks(start, direction, maxDistance, FluidCollisionMode.NEVER, false);
   }

   public RayTraceResult rayTraceBlocks(Location start, Vector direction, double maxDistance, FluidCollisionMode fluidCollisionMode) {
      return this.rayTraceBlocks(start, direction, maxDistance, fluidCollisionMode, false);
   }

   public RayTraceResult rayTraceBlocks(
      Location start, Vector direction, double maxDistance, FluidCollisionMode fluidCollisionMode, boolean ignorePassableBlocks
   ) {
      Validate.notNull(start, "Start location is null!");
      Validate.isTrue(this.equals(start.getWorld()), "Start location is from different world!");
      start.checkFinite();
      Validate.notNull(direction, "Direction is null!");
      direction.checkFinite();
      Validate.isTrue(direction.lengthSquared() > 0.0, "Direction's magnitude is 0!");
      Validate.notNull(fluidCollisionMode, "Fluid collision mode is null!");
      if (maxDistance < 0.0) {
         return null;
      } else {
         Vector dir = direction.clone().normalize().multiply(maxDistance);
         Vec3D startPos = new Vec3D(start.getX(), start.getY(), start.getZ());
         Vec3D endPos = new Vec3D(start.getX() + dir.getX(), start.getY() + dir.getY(), start.getZ() + dir.getZ());
         MovingObjectPosition nmsHitResult = this.getHandle()
            .a(
               new RayTrace(
                  startPos,
                  endPos,
                  ignorePassableBlocks ? RayTrace.BlockCollisionOption.a : RayTrace.BlockCollisionOption.b,
                  CraftFluidCollisionMode.toNMS(fluidCollisionMode),
                  null
               )
            );
         return CraftRayTraceResult.fromNMS(this, nmsHitResult);
      }
   }

   public RayTraceResult rayTrace(
      Location start,
      Vector direction,
      double maxDistance,
      FluidCollisionMode fluidCollisionMode,
      boolean ignorePassableBlocks,
      double raySize,
      Predicate<Entity> filter
   ) {
      RayTraceResult blockHit = this.rayTraceBlocks(start, direction, maxDistance, fluidCollisionMode, ignorePassableBlocks);
      Vector startVec = null;
      double blockHitDistance = maxDistance;
      if (blockHit != null) {
         startVec = start.toVector();
         blockHitDistance = startVec.distance(blockHit.getHitPosition());
      }

      RayTraceResult entityHit = this.rayTraceEntities(start, direction, blockHitDistance, raySize, filter);
      if (blockHit == null) {
         return entityHit;
      } else if (entityHit == null) {
         return blockHit;
      } else {
         double entityHitDistanceSquared = startVec.distanceSquared(entityHit.getHitPosition());
         return entityHitDistanceSquared < blockHitDistance * blockHitDistance ? entityHit : blockHit;
      }
   }

   public List<Player> getPlayers() {
      List<Player> list = new ArrayList(this.world.v().size());

      for(EntityHuman human : this.world.v()) {
         HumanEntity bukkitEntity = human.getBukkitEntity();
         if (bukkitEntity != null && bukkitEntity instanceof Player) {
            list.add((Player)bukkitEntity);
         }
      }

      return list;
   }

   public void save() {
      AsyncCatcher.catchOp("world save");
      this.server.checkSaveState();
      boolean oldSave = this.world.e;
      this.world.e = false;
      this.world.a(null, false, false);
      this.world.e = oldSave;
   }

   public boolean isAutoSave() {
      return !this.world.e;
   }

   public void setAutoSave(boolean value) {
      this.world.e = !value;
   }

   public void setDifficulty(Difficulty difficulty) {
      this.getHandle().J.a(EnumDifficulty.a(difficulty.getValue()));
   }

   public Difficulty getDifficulty() {
      return Difficulty.getByValue(this.getHandle().ah().ordinal());
   }

   public BlockMetadataStore getBlockMetadata() {
      return this.blockMetadata;
   }

   public boolean hasStorm() {
      return this.world.A.k();
   }

   public void setStorm(boolean hasStorm) {
      this.world.A.b(hasStorm);
      this.setWeatherDuration(0);
      this.setClearWeatherDuration(0);
   }

   public int getWeatherDuration() {
      return this.world.J.l();
   }

   public void setWeatherDuration(int duration) {
      this.world.J.f(duration);
   }

   public boolean isThundering() {
      return this.world.A.i();
   }

   public void setThundering(boolean thundering) {
      this.world.J.a(thundering);
      this.setThunderDuration(0);
      this.setClearWeatherDuration(0);
   }

   public int getThunderDuration() {
      return this.world.J.j();
   }

   public void setThunderDuration(int duration) {
      this.world.J.e(duration);
   }

   public boolean isClearWeather() {
      return !this.hasStorm() && !this.isThundering();
   }

   public void setClearWeatherDuration(int duration) {
      this.world.J.a(duration);
   }

   public int getClearWeatherDuration() {
      return this.world.J.h();
   }

   public long getSeed() {
      return this.world.A();
   }

   public boolean getPVP() {
      return this.world.pvpMode;
   }

   public void setPVP(boolean pvp) {
      this.world.pvpMode = pvp;
   }

   public void playEffect(Player player, Effect effect, int data) {
      this.playEffect(player.getLocation(), effect, data, 0);
   }

   public void playEffect(Location location, Effect effect, int data) {
      this.playEffect(location, effect, data, 64);
   }

   public <T> void playEffect(Location loc, Effect effect, T data) {
      this.playEffect(loc, effect, data, 64);
   }

   public <T> void playEffect(Location loc, Effect effect, T data, int radius) {
      if (data != null) {
         Validate.isTrue(effect.getData() != null && effect.getData().isAssignableFrom(data.getClass()), "Wrong kind of data for this effect!");
      } else {
         Validate.isTrue(effect.getData() == null || effect == Effect.ELECTRIC_SPARK, "Wrong kind of data for this effect!");
      }

      int datavalue = CraftEffect.getDataValue(effect, data);
      this.playEffect(loc, effect, datavalue, radius);
   }

   public void playEffect(Location location, Effect effect, int data, int radius) {
      Validate.notNull(location, "Location cannot be null");
      Validate.notNull(effect, "Effect cannot be null");
      Validate.notNull(location.getWorld(), "World cannot be null");
      int packetData = effect.getId();
      PacketPlayOutWorldEvent packet = new PacketPlayOutWorldEvent(
         packetData, new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()), data, false
      );
      radius *= radius;

      for(Player player : this.getPlayers()) {
         if (((CraftPlayer)player).getHandle().b != null && location.getWorld().equals(player.getWorld())) {
            int distance = (int)player.getLocation().distanceSquared(location);
            if (distance <= radius) {
               ((CraftPlayer)player).getHandle().b.a(packet);
            }
         }
      }
   }

   public FallingBlock spawnFallingBlock(Location location, MaterialData data) throws IllegalArgumentException {
      Validate.notNull(data, "MaterialData cannot be null");
      return this.spawnFallingBlock(location, data.getItemType(), data.getData());
   }

   public FallingBlock spawnFallingBlock(Location location, Material material, byte data) throws IllegalArgumentException {
      Validate.notNull(location, "Location cannot be null");
      Validate.notNull(material, "Material cannot be null");
      Validate.isTrue(material.isBlock(), "Material must be a block");
      EntityFallingBlock entity = EntityFallingBlock.fall(
         this.world, BlockPosition.a(location.getX(), location.getY(), location.getZ()), CraftMagicNumbers.getBlock(material).o(), SpawnReason.CUSTOM
      );
      return (FallingBlock)entity.getBukkitEntity();
   }

   public FallingBlock spawnFallingBlock(Location location, BlockData data) throws IllegalArgumentException {
      Validate.notNull(location, "Location cannot be null");
      Validate.notNull(data, "BlockData cannot be null");
      EntityFallingBlock entity = EntityFallingBlock.fall(
         this.world, BlockPosition.a(location.getX(), location.getY(), location.getZ()), ((CraftBlockData)data).getState(), SpawnReason.CUSTOM
      );
      return (FallingBlock)entity.getBukkitEntity();
   }

   public ChunkSnapshot getEmptyChunkSnapshot(int x, int z, boolean includeBiome, boolean includeBiomeTempRain) {
      return CraftChunk.getEmptyChunkSnapshot(x, z, this, includeBiome, includeBiomeTempRain);
   }

   public void setSpawnFlags(boolean allowMonsters, boolean allowAnimals) {
      this.world.b(allowMonsters, allowAnimals);
   }

   public boolean getAllowAnimals() {
      return this.world.k().k;
   }

   public boolean getAllowMonsters() {
      return this.world.k().j;
   }

   public int getMinHeight() {
      return this.world.v_();
   }

   public int getMaxHeight() {
      return this.world.ai();
   }

   public int getLogicalHeight() {
      return this.world.q_().p();
   }

   public boolean isNatural() {
      return this.world.q_().j();
   }

   public boolean isBedWorks() {
      return this.world.q_().l();
   }

   public boolean hasSkyLight() {
      return this.world.q_().g();
   }

   public boolean hasCeiling() {
      return this.world.q_().h();
   }

   public boolean isPiglinSafe() {
      return this.world.q_().b();
   }

   public boolean isRespawnAnchorWorks() {
      return this.world.q_().m();
   }

   public boolean hasRaids() {
      return this.world.q_().c();
   }

   public boolean isUltraWarm() {
      return this.world.q_().i();
   }

   public int getSeaLevel() {
      return this.world.m_();
   }

   public boolean getKeepSpawnInMemory() {
      return this.world.keepSpawnInMemory;
   }

   public void setKeepSpawnInMemory(boolean keepLoaded) {
      this.world.keepSpawnInMemory = keepLoaded;
      BlockPosition chunkcoordinates = this.world.Q();
      if (keepLoaded) {
         this.world.k().a(TicketType.a, new ChunkCoordIntPair(chunkcoordinates), 11, Unit.a);
      } else {
         this.world.k().b(TicketType.a, new ChunkCoordIntPair(chunkcoordinates), 11, Unit.a);
      }
   }

   @Override
   public int hashCode() {
      return this.getUID().hashCode();
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         CraftWorld other = (CraftWorld)obj;
         return this.getUID() == other.getUID();
      }
   }

   public File getWorldFolder() {
      return this.world.convertable.a(SavedFile.l).toFile().getParentFile();
   }

   public void sendPluginMessage(Plugin source, String channel, byte[] message) {
      StandardMessenger.validatePluginMessage(this.server.getMessenger(), source, channel, message);

      for(Player player : this.getPlayers()) {
         player.sendPluginMessage(source, channel, message);
      }
   }

   public Set<String> getListeningPluginChannels() {
      Set<String> result = new HashSet<>();

      for(Player player : this.getPlayers()) {
         result.addAll(player.getListeningPluginChannels());
      }

      return result;
   }

   public WorldType getWorldType() {
      return this.world.z() ? WorldType.FLAT : WorldType.NORMAL;
   }

   public boolean canGenerateStructures() {
      return this.world.J.A().c();
   }

   public boolean isHardcore() {
      return this.world.n_().n();
   }

   public void setHardcore(boolean hardcore) {
      this.world.J.f.c = hardcore;
   }

   @Deprecated
   public long getTicksPerAnimalSpawns() {
      return this.getTicksPerSpawns(SpawnCategory.ANIMAL);
   }

   @Deprecated
   public void setTicksPerAnimalSpawns(int ticksPerAnimalSpawns) {
      this.setTicksPerSpawns(SpawnCategory.ANIMAL, ticksPerAnimalSpawns);
   }

   @Deprecated
   public long getTicksPerMonsterSpawns() {
      return this.getTicksPerSpawns(SpawnCategory.MONSTER);
   }

   @Deprecated
   public void setTicksPerMonsterSpawns(int ticksPerMonsterSpawns) {
      this.setTicksPerSpawns(SpawnCategory.MONSTER, ticksPerMonsterSpawns);
   }

   @Deprecated
   public long getTicksPerWaterSpawns() {
      return this.getTicksPerSpawns(SpawnCategory.WATER_ANIMAL);
   }

   @Deprecated
   public void setTicksPerWaterSpawns(int ticksPerWaterSpawns) {
      this.setTicksPerSpawns(SpawnCategory.WATER_ANIMAL, ticksPerWaterSpawns);
   }

   @Deprecated
   public long getTicksPerWaterAmbientSpawns() {
      return this.getTicksPerSpawns(SpawnCategory.WATER_AMBIENT);
   }

   @Deprecated
   public void setTicksPerWaterAmbientSpawns(int ticksPerWaterAmbientSpawns) {
      this.setTicksPerSpawns(SpawnCategory.WATER_AMBIENT, ticksPerWaterAmbientSpawns);
   }

   @Deprecated
   public long getTicksPerWaterUndergroundCreatureSpawns() {
      return this.getTicksPerSpawns(SpawnCategory.WATER_UNDERGROUND_CREATURE);
   }

   @Deprecated
   public void setTicksPerWaterUndergroundCreatureSpawns(int ticksPerWaterUndergroundCreatureSpawns) {
      this.setTicksPerSpawns(SpawnCategory.WATER_UNDERGROUND_CREATURE, ticksPerWaterUndergroundCreatureSpawns);
   }

   @Deprecated
   public long getTicksPerAmbientSpawns() {
      return this.getTicksPerSpawns(SpawnCategory.AMBIENT);
   }

   @Deprecated
   public void setTicksPerAmbientSpawns(int ticksPerAmbientSpawns) {
      this.setTicksPerSpawns(SpawnCategory.AMBIENT, ticksPerAmbientSpawns);
   }

   public void setTicksPerSpawns(SpawnCategory spawnCategory, int ticksPerCategorySpawn) {
      Validate.notNull(spawnCategory, "SpawnCategory cannot be null");
      Validate.isTrue(CraftSpawnCategory.isValidForLimits(spawnCategory), "SpawnCategory." + spawnCategory + " are not supported.");
      this.world.ticksPerSpawnCategory.put(spawnCategory, (long)ticksPerCategorySpawn);
   }

   public long getTicksPerSpawns(SpawnCategory spawnCategory) {
      Validate.notNull(spawnCategory, "SpawnCategory cannot be null");
      Validate.isTrue(CraftSpawnCategory.isValidForLimits(spawnCategory), "SpawnCategory." + spawnCategory + " are not supported.");
      return this.world.ticksPerSpawnCategory.getLong(spawnCategory);
   }

   public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
      this.server.getWorldMetadata().setMetadata(this, metadataKey, newMetadataValue);
   }

   public List<MetadataValue> getMetadata(String metadataKey) {
      return this.server.getWorldMetadata().getMetadata(this, metadataKey);
   }

   public boolean hasMetadata(String metadataKey) {
      return this.server.getWorldMetadata().hasMetadata(this, metadataKey);
   }

   public void removeMetadata(String metadataKey, Plugin owningPlugin) {
      this.server.getWorldMetadata().removeMetadata(this, metadataKey, owningPlugin);
   }

   @Deprecated
   public int getMonsterSpawnLimit() {
      return this.getSpawnLimit(SpawnCategory.MONSTER);
   }

   @Deprecated
   public void setMonsterSpawnLimit(int limit) {
      this.setSpawnLimit(SpawnCategory.MONSTER, limit);
   }

   @Deprecated
   public int getAnimalSpawnLimit() {
      return this.getSpawnLimit(SpawnCategory.ANIMAL);
   }

   @Deprecated
   public void setAnimalSpawnLimit(int limit) {
      this.setSpawnLimit(SpawnCategory.ANIMAL, limit);
   }

   @Deprecated
   public int getWaterAnimalSpawnLimit() {
      return this.getSpawnLimit(SpawnCategory.WATER_ANIMAL);
   }

   @Deprecated
   public void setWaterAnimalSpawnLimit(int limit) {
      this.setSpawnLimit(SpawnCategory.WATER_ANIMAL, limit);
   }

   @Deprecated
   public int getWaterAmbientSpawnLimit() {
      return this.getSpawnLimit(SpawnCategory.WATER_AMBIENT);
   }

   @Deprecated
   public void setWaterAmbientSpawnLimit(int limit) {
      this.setSpawnLimit(SpawnCategory.WATER_AMBIENT, limit);
   }

   @Deprecated
   public int getWaterUndergroundCreatureSpawnLimit() {
      return this.getSpawnLimit(SpawnCategory.WATER_UNDERGROUND_CREATURE);
   }

   @Deprecated
   public void setWaterUndergroundCreatureSpawnLimit(int limit) {
      this.setSpawnLimit(SpawnCategory.WATER_UNDERGROUND_CREATURE, limit);
   }

   @Deprecated
   public int getAmbientSpawnLimit() {
      return this.getSpawnLimit(SpawnCategory.AMBIENT);
   }

   @Deprecated
   public void setAmbientSpawnLimit(int limit) {
      this.setSpawnLimit(SpawnCategory.AMBIENT, limit);
   }

   public int getSpawnLimit(SpawnCategory spawnCategory) {
      Validate.notNull(spawnCategory, "SpawnCategory cannot be null");
      Validate.isTrue(CraftSpawnCategory.isValidForLimits(spawnCategory), "SpawnCategory." + spawnCategory + " are not supported.");
      int limit = this.spawnCategoryLimit.getOrDefault(spawnCategory, -1);
      if (limit < 0) {
         limit = this.server.getSpawnLimit(spawnCategory);
      }

      return limit;
   }

   public void setSpawnLimit(SpawnCategory spawnCategory, int limit) {
      Validate.notNull(spawnCategory, "SpawnCategory cannot be null");
      Validate.isTrue(CraftSpawnCategory.isValidForLimits(spawnCategory), "SpawnCategory." + spawnCategory + " are not supported.");
      this.spawnCategoryLimit.put(spawnCategory, limit);
   }

   public void playSound(Location loc, Sound sound, float volume, float pitch) {
      this.playSound(loc, sound, SoundCategory.MASTER, volume, pitch);
   }

   public void playSound(Location loc, String sound, float volume, float pitch) {
      this.playSound(loc, sound, SoundCategory.MASTER, volume, pitch);
   }

   public void playSound(Location loc, Sound sound, SoundCategory category, float volume, float pitch) {
      if (loc != null && sound != null && category != null) {
         double x = loc.getX();
         double y = loc.getY();
         double z = loc.getZ();
         this.getHandle().a(null, x, y, z, CraftSound.getSoundEffect(sound), net.minecraft.sounds.SoundCategory.valueOf(category.name()), volume, pitch);
      }
   }

   public void playSound(Location loc, String sound, SoundCategory category, float volume, float pitch) {
      if (loc != null && sound != null && category != null) {
         double x = loc.getX();
         double y = loc.getY();
         double z = loc.getZ();
         PacketPlayOutNamedSoundEffect packet = new PacketPlayOutNamedSoundEffect(
            Holder.a(SoundEffect.a(new MinecraftKey(sound))),
            net.minecraft.sounds.SoundCategory.valueOf(category.name()),
            x,
            y,
            z,
            volume,
            pitch,
            this.getHandle().r_().g()
         );
         this.world.n().ac().a(null, x, y, z, volume > 1.0F ? (double)(16.0F * volume) : 16.0, this.world.ab(), packet);
      }
   }

   public void playSound(Entity entity, Sound sound, float volume, float pitch) {
      this.playSound(entity, sound, SoundCategory.MASTER, volume, pitch);
   }

   public void playSound(Entity entity, String sound, float volume, float pitch) {
      this.playSound(entity, sound, SoundCategory.MASTER, volume, pitch);
   }

   public void playSound(Entity entity, Sound sound, SoundCategory category, float volume, float pitch) {
      CraftEntity craftEntity;
      if (entity instanceof CraftEntity
         && (craftEntity = (CraftEntity)entity) == (CraftEntity)entity
         && entity.getWorld() == this
         && sound != null
         && category != null) {
         PacketPlayOutEntitySound packet = new PacketPlayOutEntitySound(
            BuiltInRegistries.c.d(CraftSound.getSoundEffect(sound)),
            net.minecraft.sounds.SoundCategory.valueOf(category.name()),
            craftEntity.getHandle(),
            volume,
            pitch,
            this.getHandle().r_().g()
         );
         PlayerChunkMap.EntityTracker entityTracker = (PlayerChunkMap.EntityTracker)this.getHandle().k().a.L.get(entity.getEntityId());
         if (entityTracker != null) {
            entityTracker.b(packet);
         }
      }
   }

   public void playSound(Entity entity, String sound, SoundCategory category, float volume, float pitch) {
      CraftEntity craftEntity;
      if (entity instanceof CraftEntity
         && (craftEntity = (CraftEntity)entity) == (CraftEntity)entity
         && entity.getWorld() == this
         && sound != null
         && category != null) {
         PacketPlayOutEntitySound packet = new PacketPlayOutEntitySound(
            Holder.a(SoundEffect.a(new MinecraftKey(sound))),
            net.minecraft.sounds.SoundCategory.valueOf(category.name()),
            craftEntity.getHandle(),
            volume,
            pitch,
            this.getHandle().r_().g()
         );
         PlayerChunkMap.EntityTracker entityTracker = (PlayerChunkMap.EntityTracker)this.getHandle().k().a.L.get(entity.getEntityId());
         if (entityTracker != null) {
            entityTracker.b(packet);
         }
      }
   }

   public static synchronized Map<String, GameRules.GameRuleKey<?>> getGameRulesNMS() {
      if (CraftWorld.gamerules != null) {
         return CraftWorld.gamerules;
      } else {
         final Map<String, GameRules.GameRuleKey<?>> gamerules = new HashMap<>();
         GameRules.a(
            new GameRules.GameRuleVisitor() {
               @Override
               public <T extends GameRules.GameRuleValue<T>> void a(
                  GameRules.GameRuleKey<T> gamerules_gamerulekey, GameRules.GameRuleDefinition<T> gamerules_gameruledefinition
               ) {
                  gamerules.put(gamerules_gamerulekey.a(), gamerules_gamerulekey);
               }
            }
         );
         CraftWorld.gamerules = gamerules;
         return gamerules;
      }
   }

   public static synchronized Map<String, GameRules.GameRuleDefinition<?>> getGameRuleDefinitions() {
      if (CraftWorld.gameruleDefinitions != null) {
         return CraftWorld.gameruleDefinitions;
      } else {
         final Map<String, GameRules.GameRuleDefinition<?>> gameruleDefinitions = new HashMap<>();
         GameRules.a(
            new GameRules.GameRuleVisitor() {
               @Override
               public <T extends GameRules.GameRuleValue<T>> void a(
                  GameRules.GameRuleKey<T> gamerules_gamerulekey, GameRules.GameRuleDefinition<T> gamerules_gameruledefinition
               ) {
                  gameruleDefinitions.put(gamerules_gamerulekey.a(), gamerules_gameruledefinition);
               }
            }
         );
         CraftWorld.gameruleDefinitions = gameruleDefinitions;
         return gameruleDefinitions;
      }
   }

   public String getGameRuleValue(String rule) {
      if (rule == null) {
         return null;
      } else {
         GameRules.GameRuleValue<?> value = this.getHandle().W().a((GameRules.GameRuleKey<GameRules.GameRuleValue<?>>)getGameRulesNMS().get(rule));
         return value != null ? value.toString() : "";
      }
   }

   public boolean setGameRuleValue(String rule, String value) {
      if (rule == null || value == null) {
         return false;
      } else if (!this.isGameRule(rule)) {
         return false;
      } else {
         GameRules.GameRuleValue<?> handle = this.getHandle().W().a((GameRules.GameRuleKey<GameRules.GameRuleValue<?>>)getGameRulesNMS().get(rule));
         handle.a(value);
         handle.a(this.getHandle().n());
         return true;
      }
   }

   public String[] getGameRules() {
      return getGameRulesNMS().keySet().toArray(new String[getGameRulesNMS().size()]);
   }

   public boolean isGameRule(String rule) {
      Validate.isTrue(rule != null && !rule.isEmpty(), "Rule cannot be null nor empty");
      return getGameRulesNMS().containsKey(rule);
   }

   public <T> T getGameRuleValue(GameRule<T> rule) {
      Validate.notNull(rule, "GameRule cannot be null");
      return this.convert(rule, this.getHandle().W().a((GameRules.GameRuleKey<GameRules.GameRuleValue<?>>)getGameRulesNMS().get(rule.getName())));
   }

   public <T> T getGameRuleDefault(GameRule<T> rule) {
      Validate.notNull(rule, "GameRule cannot be null");
      return this.convert(rule, getGameRuleDefinitions().get(rule.getName()).a());
   }

   public <T> boolean setGameRule(GameRule<T> rule, T newValue) {
      Validate.notNull(rule, "GameRule cannot be null");
      Validate.notNull(newValue, "GameRule value cannot be null");
      if (!this.isGameRule(rule.getName())) {
         return false;
      } else {
         GameRules.GameRuleValue<?> handle = this.getHandle().W().a((GameRules.GameRuleKey<GameRules.GameRuleValue<?>>)getGameRulesNMS().get(rule.getName()));
         handle.a(newValue.toString());
         handle.a(this.getHandle().n());
         return true;
      }
   }

   private <T> T convert(GameRule<T> rule, GameRules.GameRuleValue<?> value) {
      if (value == null) {
         return null;
      } else if (value instanceof GameRules.GameRuleBoolean) {
         return (T)rule.getType().cast(((GameRules.GameRuleBoolean)value).a());
      } else if (value instanceof GameRules.GameRuleInt) {
         return (T)rule.getType().cast(value.c());
      } else {
         throw new IllegalArgumentException("Invalid GameRule type (" + value + ") for GameRule " + rule.getName());
      }
   }

   public WorldBorder getWorldBorder() {
      if (this.worldBorder == null) {
         this.worldBorder = new CraftWorldBorder(this);
      }

      return this.worldBorder;
   }

   public void spawnParticle(Particle particle, Location location, int count) {
      this.spawnParticle(particle, location.getX(), location.getY(), location.getZ(), count);
   }

   public void spawnParticle(Particle particle, double x, double y, double z, int count) {
      this.spawnParticle(particle, x, y, z, count, null);
   }

   public <T> void spawnParticle(Particle particle, Location location, int count, T data) {
      this.spawnParticle(particle, location.getX(), location.getY(), location.getZ(), count, data);
   }

   public <T> void spawnParticle(Particle particle, double x, double y, double z, int count, T data) {
      this.spawnParticle(particle, x, y, z, count, 0.0, 0.0, 0.0, data);
   }

   public void spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY, double offsetZ) {
      this.spawnParticle(particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ);
   }

   public void spawnParticle(Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ) {
      this.spawnParticle(particle, x, y, z, count, offsetX, offsetY, offsetZ, null);
   }

   public <T> void spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY, double offsetZ, T data) {
      this.spawnParticle(particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ, data);
   }

   public <T> void spawnParticle(Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, T data) {
      this.spawnParticle(particle, x, y, z, count, offsetX, offsetY, offsetZ, 1.0, data);
   }

   public void spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY, double offsetZ, double extra) {
      this.spawnParticle(particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ, extra);
   }

   public void spawnParticle(Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, double extra) {
      this.spawnParticle(particle, x, y, z, count, offsetX, offsetY, offsetZ, extra, null);
   }

   public <T> void spawnParticle(Particle particle, Location location, int count, double offsetX, double offsetY, double offsetZ, double extra, T data) {
      this.spawnParticle(particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ, extra, data);
   }

   public <T> void spawnParticle(
      Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, double extra, T data
   ) {
      this.spawnParticle(particle, x, y, z, count, offsetX, offsetY, offsetZ, extra, data, false);
   }

   public <T> void spawnParticle(
      Particle particle, Location location, int count, double offsetX, double offsetY, double offsetZ, double extra, T data, boolean force
   ) {
      this.spawnParticle(particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ, extra, data, force);
   }

   public <T> void spawnParticle(
      Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, double extra, T data, boolean force
   ) {
      if (data != null && !particle.getDataType().isInstance(data)) {
         throw new IllegalArgumentException("data should be " + particle.getDataType() + " got " + data.getClass());
      } else {
         this.getHandle().sendParticles(null, CraftParticle.toNMS(particle, data), x, y, z, count, offsetX, offsetY, offsetZ, extra, force);
      }
   }

   @Deprecated
   public Location locateNearestStructure(Location origin, StructureType structureType, int radius, boolean findUnexplored) {
      StructureSearchResult result = null;
      if (StructureType.MINESHAFT == structureType) {
         result = this.locateNearestStructure(origin, org.bukkit.generator.structure.StructureType.MINESHAFT, radius, findUnexplored);
      } else if (StructureType.VILLAGE == structureType) {
         result = this.locateNearestStructure(
            origin,
            List.of(Structure.VILLAGE_DESERT, Structure.VILLAGE_PLAINS, Structure.VILLAGE_SAVANNA, Structure.VILLAGE_SNOWY, Structure.VILLAGE_TAIGA),
            radius,
            findUnexplored
         );
      } else if (StructureType.NETHER_FORTRESS == structureType) {
         result = this.locateNearestStructure(origin, org.bukkit.generator.structure.StructureType.FORTRESS, radius, findUnexplored);
      } else if (StructureType.STRONGHOLD == structureType) {
         result = this.locateNearestStructure(origin, org.bukkit.generator.structure.StructureType.STRONGHOLD, radius, findUnexplored);
      } else if (StructureType.JUNGLE_PYRAMID == structureType) {
         result = this.locateNearestStructure(origin, org.bukkit.generator.structure.StructureType.JUNGLE_TEMPLE, radius, findUnexplored);
      } else if (StructureType.OCEAN_RUIN == structureType) {
         result = this.locateNearestStructure(origin, org.bukkit.generator.structure.StructureType.OCEAN_RUIN, radius, findUnexplored);
      } else if (StructureType.DESERT_PYRAMID == structureType) {
         result = this.locateNearestStructure(origin, org.bukkit.generator.structure.StructureType.DESERT_PYRAMID, radius, findUnexplored);
      } else if (StructureType.IGLOO == structureType) {
         result = this.locateNearestStructure(origin, org.bukkit.generator.structure.StructureType.IGLOO, radius, findUnexplored);
      } else if (StructureType.SWAMP_HUT == structureType) {
         result = this.locateNearestStructure(origin, org.bukkit.generator.structure.StructureType.SWAMP_HUT, radius, findUnexplored);
      } else if (StructureType.OCEAN_MONUMENT == structureType) {
         result = this.locateNearestStructure(origin, org.bukkit.generator.structure.StructureType.OCEAN_MONUMENT, radius, findUnexplored);
      } else if (StructureType.END_CITY == structureType) {
         result = this.locateNearestStructure(origin, org.bukkit.generator.structure.StructureType.END_CITY, radius, findUnexplored);
      } else if (StructureType.WOODLAND_MANSION == structureType) {
         result = this.locateNearestStructure(origin, org.bukkit.generator.structure.StructureType.WOODLAND_MANSION, radius, findUnexplored);
      } else if (StructureType.BURIED_TREASURE == structureType) {
         result = this.locateNearestStructure(origin, org.bukkit.generator.structure.StructureType.BURIED_TREASURE, radius, findUnexplored);
      } else if (StructureType.SHIPWRECK == structureType) {
         result = this.locateNearestStructure(origin, org.bukkit.generator.structure.StructureType.SHIPWRECK, radius, findUnexplored);
      } else if (StructureType.PILLAGER_OUTPOST == structureType) {
         result = this.locateNearestStructure(origin, Structure.PILLAGER_OUTPOST, radius, findUnexplored);
      } else if (StructureType.NETHER_FOSSIL == structureType) {
         result = this.locateNearestStructure(origin, org.bukkit.generator.structure.StructureType.NETHER_FOSSIL, radius, findUnexplored);
      } else if (StructureType.RUINED_PORTAL == structureType) {
         result = this.locateNearestStructure(origin, org.bukkit.generator.structure.StructureType.RUINED_PORTAL, radius, findUnexplored);
      } else if (StructureType.BASTION_REMNANT == structureType) {
         result = this.locateNearestStructure(origin, Structure.BASTION_REMNANT, radius, findUnexplored);
      }

      return result == null ? null : result.getLocation();
   }

   public StructureSearchResult locateNearestStructure(
      Location origin, org.bukkit.generator.structure.StructureType structureType, int radius, boolean findUnexplored
   ) {
      List<Structure> structures = new ArrayList();

      for(Structure structure : Registry.STRUCTURE) {
         if (structure.getStructureType() == structureType) {
            structures.add(structure);
         }
      }

      return this.locateNearestStructure(origin, structures, radius, findUnexplored);
   }

   public StructureSearchResult locateNearestStructure(Location origin, Structure structure, int radius, boolean findUnexplored) {
      return this.locateNearestStructure(origin, List.of(structure), radius, findUnexplored);
   }

   public StructureSearchResult locateNearestStructure(Location origin, List<Structure> structures, int radius, boolean findUnexplored) {
      BlockPosition originPos = BlockPosition.a(origin.getX(), origin.getY(), origin.getZ());
      List<Holder<net.minecraft.world.level.levelgen.structure.Structure>> holders = new ArrayList<>();

      for(Structure structure : structures) {
         holders.add(Holder.a(CraftStructure.bukkitToMinecraft(structure)));
      }

      Pair<BlockPosition, Holder<net.minecraft.world.level.levelgen.structure.Structure>> found = this.getHandle()
         .k()
         .g()
         .a(this.getHandle(), HolderSet.a(holders), originPos, radius, findUnexplored);
      return found == null
         ? null
         : new CraftStructureSearchResult(
            CraftStructure.minecraftToBukkit((net.minecraft.world.level.levelgen.structure.Structure)((Holder)found.getSecond()).a(), this.getHandle().u_()),
            new Location(
               this,
               (double)((BlockPosition)found.getFirst()).u(),
               (double)((BlockPosition)found.getFirst()).v(),
               (double)((BlockPosition)found.getFirst()).w()
            )
         );
   }

   public Raid locateNearestRaid(Location location, int radius) {
      Validate.notNull(location, "Location cannot be null");
      Validate.isTrue(radius >= 0, "Radius cannot be negative");
      PersistentRaid persistentRaid = this.world.x();
      net.minecraft.world.entity.raid.Raid raid = persistentRaid.a(
         new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()), radius * radius
      );
      return raid == null ? null : new CraftRaid(raid);
   }

   public List<Raid> getRaids() {
      PersistentRaid persistentRaid = this.world.x();
      return persistentRaid.b.values().stream().map(CraftRaid::new).collect(Collectors.toList());
   }

   public DragonBattle getEnderDragonBattle() {
      return this.getHandle().B() == null ? null : new CraftDragonBattle(this.getHandle().B());
   }

   public PersistentDataContainer getPersistentDataContainer() {
      return this.persistentDataContainer;
   }

   public void storeBukkitValues(NBTTagCompound c) {
      if (!this.persistentDataContainer.isEmpty()) {
         c.a("BukkitValues", this.persistentDataContainer.toTagCompound());
      }
   }

   public void readBukkitValues(NBTBase c) {
      if (c instanceof NBTTagCompound) {
         this.persistentDataContainer.putAll((NBTTagCompound)c);
      }
   }

   public int getViewDistance() {
      return this.world.spigotConfig.viewDistance;
   }

   public int getSimulationDistance() {
      return this.world.spigotConfig.simulationDistance;
   }

   public Spigot spigot() {
      return this.spigot;
   }
}
