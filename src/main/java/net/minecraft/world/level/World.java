package net.minecraft.world.level;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportSystemDetails;
import net.minecraft.ReportedException;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.SectionPosition;
import net.minecraft.core.particles.ParticleParam;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSetBorderCenterPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderLerpSizePacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderSizePacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderWarningDelayPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderWarningDistancePacket;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.PlayerChunk;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.util.AbortableIterationConsumer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.GameProfilerFiller;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.EntityComplexPart;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderDragon;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingManager;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockFireAbstract;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TickingBlockEntity;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.border.IWorldBorderListener;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.chunk.Chunk;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.IChunkAccess;
import net.minecraft.world.level.chunk.IChunkProvider;
import net.minecraft.world.level.dimension.DimensionManager;
import net.minecraft.world.level.dimension.WorldDimension;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.entity.LevelEntityGetter;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.lighting.LightEngine;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.level.redstone.CollectingNeighborUpdater;
import net.minecraft.world.level.redstone.NeighborUpdater;
import net.minecraft.world.level.saveddata.maps.WorldMap;
import net.minecraft.world.level.storage.WorldData;
import net.minecraft.world.level.storage.WorldDataMutable;
import net.minecraft.world.level.storage.WorldDataServer;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.scores.Scoreboard;
import org.bukkit.Bukkit;
import org.bukkit.World.Environment;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R3.SpigotTimings;
import org.bukkit.craftbukkit.v1_19_R3.block.CapturedBlockState;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftSpawnCategory;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.spigotmc.SpigotWorldConfig;
import org.spigotmc.TickLimiter;

public abstract class World implements GeneratorAccess, AutoCloseable {
   public static final Codec<ResourceKey<World>> g = ResourceKey.a(Registries.aF);
   public static final ResourceKey<World> h = ResourceKey.a(Registries.aF, new MinecraftKey("overworld"));
   public static final ResourceKey<World> i = ResourceKey.a(Registries.aF, new MinecraftKey("the_nether"));
   public static final ResourceKey<World> j = ResourceKey.a(Registries.aF, new MinecraftKey("the_end"));
   public static final int k = 30000000;
   public static final int l = 512;
   public static final int m = 32;
   private static final EnumDirection[] a = EnumDirection.values();
   public static final int n = 15;
   public static final int o = 24000;
   public static final int p = 20000000;
   public static final int q = -20000000;
   protected final List<TickingBlockEntity> r = Lists.newArrayList();
   protected final NeighborUpdater s;
   private final List<TickingBlockEntity> b = Lists.newArrayList();
   private boolean c;
   public final Thread d;
   private final boolean e;
   private int f;
   protected int t = RandomSource.a().f();
   protected final int u = 1013904223;
   protected float v;
   public float w;
   protected float x;
   public float y;
   public final RandomSource z = RandomSource.a();
   @Deprecated
   private final RandomSource C = RandomSource.b();
   private final ResourceKey<DimensionManager> D;
   private final Holder<DimensionManager> E;
   public final WorldDataMutable A;
   private final Supplier<GameProfilerFiller> F;
   public final boolean B;
   private final WorldBorder G;
   private final BiomeManager H;
   private final ResourceKey<World> I;
   private final IRegistryCustom J;
   private final DamageSources K;
   private long L;
   private final CraftWorld world;
   public boolean pvpMode;
   public boolean keepSpawnInMemory = true;
   public ChunkGenerator generator;
   public boolean preventPoiUpdated = false;
   public boolean captureBlockStates = false;
   public boolean captureTreeGeneration = false;
   public Map<BlockPosition, CapturedBlockState> capturedBlockStates = new LinkedHashMap<>();
   public Map<BlockPosition, TileEntity> capturedTileEntities = new HashMap<>();
   public List<EntityItem> captureDrops;
   public final Object2LongOpenHashMap<SpawnCategory> ticksPerSpawnCategory = new Object2LongOpenHashMap();
   public boolean populating;
   public final SpigotWorldConfig spigotConfig;
   public final SpigotTimings.WorldTimingsHandler timings;
   public static BlockPosition lastPhysicsProblem;
   private TickLimiter entityLimiter;
   private TickLimiter tileLimiter;
   private int tileTickPosition;

   public CraftWorld getWorld() {
      return this.world;
   }

   public CraftServer getCraftServer() {
      return (CraftServer)Bukkit.getServer();
   }

   public abstract ResourceKey<WorldDimension> getTypeKey();

   protected World(
      WorldDataMutable worlddatamutable,
      ResourceKey<World> resourcekey,
      IRegistryCustom iregistrycustom,
      Holder<DimensionManager> holder,
      Supplier<GameProfilerFiller> supplier,
      boolean flag,
      boolean flag1,
      long i,
      int j,
      ChunkGenerator gen,
      BiomeProvider biomeProvider,
      Environment env
   ) {
      this.spigotConfig = new SpigotWorldConfig(((WorldDataServer)worlddatamutable).g());
      this.generator = gen;
      this.world = new CraftWorld((WorldServer)this, gen, biomeProvider, env);

      SpawnCategory[] var17;
      for(SpawnCategory spawnCategory : var17 = SpawnCategory.values()) {
         if (CraftSpawnCategory.isValidForLimits(spawnCategory)) {
            this.ticksPerSpawnCategory.put(spawnCategory, (long)this.getCraftServer().getTicksPerSpawns(spawnCategory));
         }
      }

      this.F = supplier;
      this.A = worlddatamutable;
      this.E = holder;
      this.D = holder.e().orElseThrow(() -> new IllegalArgumentException("Dimension must be registered, got " + holder));
      DimensionManager dimensionmanager = holder.a();
      this.I = resourcekey;
      this.B = flag;
      if (dimensionmanager.k() != 1.0) {
         this.G = new WorldBorder() {
            @Override
            public double a() {
               return super.a();
            }

            @Override
            public double b() {
               return super.b();
            }
         };
      } else {
         this.G = new WorldBorder();
      }

      this.d = Thread.currentThread();
      this.H = new BiomeManager(this, i);
      this.e = flag1;
      this.s = new CollectingNeighborUpdater(this, j);
      this.J = iregistrycustom;
      this.K = new DamageSources(iregistrycustom);
      this.p_().world = (WorldServer)this;
      this.p_().a(new IWorldBorderListener() {
         @Override
         public void a(WorldBorder worldborder, double d0) {
            World.this.getCraftServer().getHandle().broadcastAll(new ClientboundSetBorderSizePacket(worldborder), worldborder.world);
         }

         @Override
         public void a(WorldBorder worldborder, double d0, double d1, long i) {
            World.this.getCraftServer().getHandle().broadcastAll(new ClientboundSetBorderLerpSizePacket(worldborder), worldborder.world);
         }

         @Override
         public void a(WorldBorder worldborder, double d0, double d1) {
            World.this.getCraftServer().getHandle().broadcastAll(new ClientboundSetBorderCenterPacket(worldborder), worldborder.world);
         }

         @Override
         public void a(WorldBorder worldborder, int i) {
            World.this.getCraftServer().getHandle().broadcastAll(new ClientboundSetBorderWarningDelayPacket(worldborder), worldborder.world);
         }

         @Override
         public void b(WorldBorder worldborder, int i) {
            World.this.getCraftServer().getHandle().broadcastAll(new ClientboundSetBorderWarningDistancePacket(worldborder), worldborder.world);
         }

         @Override
         public void b(WorldBorder worldborder, double d0) {
         }

         @Override
         public void c(WorldBorder worldborder, double d0) {
         }
      });
      this.timings = new SpigotTimings.WorldTimingsHandler(this);
      this.entityLimiter = new TickLimiter(this.spigotConfig.entityMaxTickTime);
      this.tileLimiter = new TickLimiter(this.spigotConfig.tileMaxTickTime);
   }

   @Override
   public boolean k_() {
      return this.B;
   }

   @Nullable
   @Override
   public MinecraftServer n() {
      return null;
   }

   public boolean j(BlockPosition blockposition) {
      return !this.u(blockposition) && E(blockposition);
   }

   public static boolean k(BlockPosition blockposition) {
      return !b(blockposition.v()) && E(blockposition);
   }

   private static boolean E(BlockPosition blockposition) {
      return blockposition.u() >= -30000000 && blockposition.w() >= -30000000 && blockposition.u() < 30000000 && blockposition.w() < 30000000;
   }

   private static boolean b(int i) {
      return i < -20000000 || i >= 20000000;
   }

   public Chunk l(BlockPosition blockposition) {
      return this.d(SectionPosition.a(blockposition.u()), SectionPosition.a(blockposition.w()));
   }

   public Chunk d(int i, int j) {
      return (Chunk)this.a(i, j, ChunkStatus.o);
   }

   @Nullable
   @Override
   public IChunkAccess a(int i, int j, ChunkStatus chunkstatus, boolean flag) {
      IChunkAccess ichunkaccess = this.I().a(i, j, chunkstatus, flag);
      if (ichunkaccess == null && flag) {
         throw new IllegalStateException("Should always be able to create a chunk!");
      } else {
         return ichunkaccess;
      }
   }

   @Override
   public boolean a(BlockPosition blockposition, IBlockData iblockdata, int i) {
      return this.a(blockposition, iblockdata, i, 512);
   }

   @Override
   public boolean a(BlockPosition blockposition, IBlockData iblockdata, int i, int j) {
      if (this.captureTreeGeneration) {
         CapturedBlockState blockstate = this.capturedBlockStates.get(blockposition);
         if (blockstate == null) {
            blockstate = CapturedBlockState.getTreeBlockState(this, blockposition, i);
            this.capturedBlockStates.put(blockposition.i(), blockstate);
         }

         blockstate.setData(iblockdata);
         return true;
      } else if (this.u(blockposition)) {
         return false;
      } else if (!this.B && this.ae()) {
         return false;
      } else {
         Chunk chunk = this.l(blockposition);
         Block block = iblockdata.b();
         boolean captured = false;
         if (this.captureBlockStates && !this.capturedBlockStates.containsKey(blockposition)) {
            CapturedBlockState blockstate = CapturedBlockState.getBlockState(this, blockposition, i);
            this.capturedBlockStates.put(blockposition.i(), blockstate);
            captured = true;
         }

         IBlockData iblockdata1 = chunk.setBlockState(blockposition, iblockdata, (i & 64) != 0, (i & 1024) == 0);
         if (iblockdata1 == null) {
            if (this.captureBlockStates && captured) {
               this.capturedBlockStates.remove(blockposition);
            }

            return false;
         } else {
            IBlockData iblockdata2 = this.a_(blockposition);
            if ((i & 128) == 0
               && iblockdata2 != iblockdata1
               && (
                  iblockdata2.b((IBlockAccess)this, blockposition) != iblockdata1.b((IBlockAccess)this, blockposition)
                     || iblockdata2.g() != iblockdata1.g()
                     || iblockdata2.f()
                     || iblockdata1.f()
               )) {
               this.ac().a("queueCheckLight");
               this.I().p().a(blockposition);
               this.ac().c();
            }

            if (!this.captureBlockStates) {
               try {
                  this.notifyAndUpdatePhysics(blockposition, chunk, iblockdata1, iblockdata, iblockdata2, i, j);
               } catch (StackOverflowError var11) {
                  lastPhysicsProblem = new BlockPosition(blockposition);
               }
            }

            return true;
         }
      }
   }

   public void notifyAndUpdatePhysics(BlockPosition blockposition, Chunk chunk, IBlockData oldBlock, IBlockData newBlock, IBlockData actualBlock, int i, int j) {
      if (actualBlock == newBlock) {
         if (oldBlock != actualBlock) {
            this.b(blockposition, oldBlock, actualBlock);
         }

         if ((i & 2) != 0 && (!this.B || (i & 4) == 0) && (this.B || chunk == null || chunk.B() != null && chunk.B().a(PlayerChunk.State.c))) {
            this.a(blockposition, oldBlock, newBlock, i);
         }

         if ((i & 1) != 0) {
            this.b(blockposition, oldBlock.b());
            if (!this.B && newBlock.k()) {
               this.c(blockposition, newBlock.b());
            }
         }

         if ((i & 16) == 0 && j > 0) {
            int k = i & -34;
            oldBlock.b(this, blockposition, k, j - 1);
            CraftWorld world = ((WorldServer)this).getWorld();
            if (world != null) {
               BlockPhysicsEvent event = new BlockPhysicsEvent(
                  world.getBlockAt(blockposition.u(), blockposition.v(), blockposition.w()), CraftBlockData.fromData(newBlock)
               );
               this.getCraftServer().getPluginManager().callEvent(event);
               if (event.isCancelled()) {
                  return;
               }
            }

            newBlock.a((GeneratorAccess)this, blockposition, k, j - 1);
            newBlock.b(this, blockposition, k, j - 1);
         }

         if (!this.preventPoiUpdated) {
            this.a(blockposition, oldBlock, actualBlock);
         }
      }
   }

   public void a(BlockPosition blockposition, IBlockData iblockdata, IBlockData iblockdata1) {
   }

   @Override
   public boolean a(BlockPosition blockposition, boolean flag) {
      Fluid fluid = this.b_(blockposition);
      return this.a(blockposition, fluid.g(), 3 | (flag ? 64 : 0));
   }

   @Override
   public boolean a(BlockPosition blockposition, boolean flag, @Nullable Entity entity, int i) {
      IBlockData iblockdata = this.a_(blockposition);
      if (iblockdata.h()) {
         return false;
      } else {
         Fluid fluid = this.b_(blockposition);
         if (!(iblockdata.b() instanceof BlockFireAbstract)) {
            this.c(2001, blockposition, Block.i(iblockdata));
         }

         if (flag) {
            TileEntity tileentity = iblockdata.q() ? this.c_(blockposition) : null;
            Block.a(iblockdata, this, blockposition, tileentity, entity, ItemStack.b);
         }

         boolean flag1 = this.a(blockposition, fluid.g(), 3, i);
         if (flag1) {
            this.a(GameEvent.f, blockposition, GameEvent.a.a(entity, iblockdata));
         }

         return flag1;
      }
   }

   public void a(BlockPosition blockposition, IBlockData iblockdata) {
   }

   public boolean b(BlockPosition blockposition, IBlockData iblockdata) {
      return this.a(blockposition, iblockdata, 3);
   }

   public abstract void a(BlockPosition var1, IBlockData var2, IBlockData var3, int var4);

   public void b(BlockPosition blockposition, IBlockData iblockdata, IBlockData iblockdata1) {
   }

   public void a(BlockPosition blockposition, Block block) {
   }

   public void a(BlockPosition blockposition, Block block, EnumDirection enumdirection) {
   }

   public void a(BlockPosition blockposition, Block block, BlockPosition blockposition1) {
   }

   public void a(IBlockData iblockdata, BlockPosition blockposition, Block block, BlockPosition blockposition1, boolean flag) {
   }

   @Override
   public void a(EnumDirection enumdirection, IBlockData iblockdata, BlockPosition blockposition, BlockPosition blockposition1, int i, int j) {
      this.s.a(enumdirection, iblockdata, blockposition, blockposition1, i, j);
   }

   @Override
   public int a(HeightMap.Type heightmap_type, int i, int j) {
      int k;
      if (i >= -30000000 && j >= -30000000 && i < 30000000 && j < 30000000) {
         if (this.b(SectionPosition.a(i), SectionPosition.a(j))) {
            k = this.d(SectionPosition.a(i), SectionPosition.a(j)).a(heightmap_type, i & 15, j & 15) + 1;
         } else {
            k = this.v_();
         }
      } else {
         k = this.m_() + 1;
      }

      return k;
   }

   @Override
   public LightEngine l_() {
      return this.I().p();
   }

   @Override
   public IBlockData a_(BlockPosition blockposition) {
      if (this.captureTreeGeneration) {
         CapturedBlockState previous = this.capturedBlockStates.get(blockposition);
         if (previous != null) {
            return previous.getHandle();
         }
      }

      if (this.u(blockposition)) {
         return Blocks.mX.o();
      } else {
         Chunk chunk = this.d(SectionPosition.a(blockposition.u()), SectionPosition.a(blockposition.w()));
         return chunk.a_(blockposition);
      }
   }

   @Override
   public Fluid b_(BlockPosition blockposition) {
      if (this.u(blockposition)) {
         return FluidTypes.a.g();
      } else {
         Chunk chunk = this.l(blockposition);
         return chunk.b_(blockposition);
      }
   }

   public boolean M() {
      return !this.q_().a() && this.f < 4;
   }

   public boolean N() {
      return !this.q_().a() && !this.M();
   }

   public void a(@Nullable Entity entity, BlockPosition blockposition, SoundEffect soundeffect, SoundCategory soundcategory, float f, float f1) {
      EntityHuman entityhuman;
      if (entity instanceof EntityHuman entityhuman1) {
         entityhuman = entityhuman1;
      } else {
         entityhuman = null;
      }

      this.a(entityhuman, blockposition, soundeffect, soundcategory, f, f1);
   }

   @Override
   public void a(@Nullable EntityHuman entityhuman, BlockPosition blockposition, SoundEffect soundeffect, SoundCategory soundcategory, float f, float f1) {
      this.a(entityhuman, (double)blockposition.u() + 0.5, (double)blockposition.v() + 0.5, (double)blockposition.w() + 0.5, soundeffect, soundcategory, f, f1);
   }

   public abstract void a(
      @Nullable EntityHuman var1, double var2, double var4, double var6, Holder<SoundEffect> var8, SoundCategory var9, float var10, float var11, long var12
   );

   public void a(
      @Nullable EntityHuman entityhuman, double d0, double d1, double d2, SoundEffect soundeffect, SoundCategory soundcategory, float f, float f1, long i
   ) {
      this.a(entityhuman, d0, d1, d2, BuiltInRegistries.c.d(soundeffect), soundcategory, f, f1, i);
   }

   public abstract void a(@Nullable EntityHuman var1, Entity var2, Holder<SoundEffect> var3, SoundCategory var4, float var5, float var6, long var7);

   public void a(@Nullable EntityHuman entityhuman, double d0, double d1, double d2, SoundEffect soundeffect, SoundCategory soundcategory, float f, float f1) {
      this.a(entityhuman, d0, d1, d2, soundeffect, soundcategory, f, f1, this.C.g());
   }

   public void a(@Nullable EntityHuman entityhuman, Entity entity, SoundEffect soundeffect, SoundCategory soundcategory, float f, float f1) {
      this.a(entityhuman, entity, BuiltInRegistries.c.d(soundeffect), soundcategory, f, f1, this.C.g());
   }

   public void a(BlockPosition blockposition, SoundEffect soundeffect, SoundCategory soundcategory, float f, float f1, boolean flag) {
      this.a((double)blockposition.u() + 0.5, (double)blockposition.v() + 0.5, (double)blockposition.w() + 0.5, soundeffect, soundcategory, f, f1, flag);
   }

   public void a(double d0, double d1, double d2, SoundEffect soundeffect, SoundCategory soundcategory, float f, float f1, boolean flag) {
   }

   @Override
   public void a(ParticleParam particleparam, double d0, double d1, double d2, double d3, double d4, double d5) {
   }

   public void a(ParticleParam particleparam, boolean flag, double d0, double d1, double d2, double d3, double d4, double d5) {
   }

   public void b(ParticleParam particleparam, double d0, double d1, double d2, double d3, double d4, double d5) {
   }

   public void b(ParticleParam particleparam, boolean flag, double d0, double d1, double d2, double d3, double d4, double d5) {
   }

   public float a(float f) {
      float f1 = this.f(f);
      return f1 * (float) (Math.PI * 2);
   }

   public void a(TickingBlockEntity tickingblockentity) {
      (this.c ? this.b : this.r).add(tickingblockentity);
   }

   protected void O() {
      GameProfilerFiller gameprofilerfiller = this.ac();
      gameprofilerfiller.a("blockEntities");
      this.timings.tileEntityPending.startTiming();
      this.c = true;
      if (!this.b.isEmpty()) {
         this.r.addAll(this.b);
         this.b.clear();
      }

      this.timings.tileEntityPending.stopTiming();
      this.timings.tileEntityTick.startTiming();
      int tilesThisCycle = 0;
      this.tileLimiter.initTick();

      while(tilesThisCycle < this.r.size() && (tilesThisCycle % 10 != 0 || this.tileLimiter.shouldContinue())) {
         this.tileTickPosition = this.tileTickPosition < this.r.size() ? this.tileTickPosition : 0;
         TickingBlockEntity tickingblockentity = this.r.get(this.tileTickPosition);
         if (tickingblockentity == null) {
            this.getCraftServer().getLogger().severe("Spigot has detected a null entity and has removed it, preventing a crash");
            --tilesThisCycle;
            this.r.remove(this.tileTickPosition--);
         } else if (tickingblockentity.b()) {
            --tilesThisCycle;
            this.r.remove(this.tileTickPosition--);
         } else if (this.m(tickingblockentity.c())) {
            tickingblockentity.a();
         }

         ++this.tileTickPosition;
         ++tilesThisCycle;
      }

      this.timings.tileEntityTick.stopTiming();
      this.c = false;
      gameprofilerfiller.c();
      this.spigotConfig.currentPrimedTnt = 0;
   }

   public <T extends Entity> void a(Consumer<T> consumer, T t0) {
      try {
         SpigotTimings.tickEntityTimer.startTiming();
         consumer.accept(t0);
         SpigotTimings.tickEntityTimer.stopTiming();
      } catch (Throwable var6) {
         CrashReport crashreport = CrashReport.a(var6, "Ticking entity");
         CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Entity being ticked");
         t0.a(crashreportsystemdetails);
         throw new ReportedException(crashreport);
      }
   }

   public boolean h(Entity entity) {
      return true;
   }

   public boolean a(long i) {
      return true;
   }

   public boolean m(BlockPosition blockposition) {
      return this.a(ChunkCoordIntPair.a(blockposition));
   }

   public Explosion a(@Nullable Entity entity, double d0, double d1, double d2, float f, World.a world_a) {
      return this.a(entity, null, null, d0, d1, d2, f, false, world_a);
   }

   public Explosion a(@Nullable Entity entity, double d0, double d1, double d2, float f, boolean flag, World.a world_a) {
      return this.a(entity, null, null, d0, d1, d2, f, flag, world_a);
   }

   public Explosion a(
      @Nullable Entity entity,
      @Nullable DamageSource damagesource,
      @Nullable ExplosionDamageCalculator explosiondamagecalculator,
      Vec3D vec3d,
      float f,
      boolean flag,
      World.a world_a
   ) {
      return this.a(entity, damagesource, explosiondamagecalculator, vec3d.a(), vec3d.b(), vec3d.c(), f, flag, world_a);
   }

   public Explosion a(
      @Nullable Entity entity,
      @Nullable DamageSource damagesource,
      @Nullable ExplosionDamageCalculator explosiondamagecalculator,
      double d0,
      double d1,
      double d2,
      float f,
      boolean flag,
      World.a world_a
   ) {
      return this.a(entity, damagesource, explosiondamagecalculator, d0, d1, d2, f, flag, world_a, true);
   }

   public Explosion a(
      @Nullable Entity entity,
      @Nullable DamageSource damagesource,
      @Nullable ExplosionDamageCalculator explosiondamagecalculator,
      double d0,
      double d1,
      double d2,
      float f,
      boolean flag,
      World.a world_a,
      boolean flag1
   ) {
      Explosion explosion = new Explosion(this, entity, damagesource, explosiondamagecalculator, d0, d1, d2, f, flag, switch(world_a) {
         case a -> Explosion.Effect.a;
         case b -> this.a(GameRules.M);
         case c -> this.W().b(GameRules.c) ? this.a(GameRules.N) : Explosion.Effect.a;
         case d -> this.a(GameRules.O);
      });
      explosion.a();
      explosion.a(flag1);
      return explosion;
   }

   private Explosion.Effect a(GameRules.GameRuleKey<GameRules.GameRuleBoolean> gamerules_gamerulekey) {
      return this.W().b(gamerules_gamerulekey) ? Explosion.Effect.c : Explosion.Effect.b;
   }

   public abstract String F();

   @Nullable
   @Override
   public TileEntity c_(BlockPosition blockposition) {
      return this.getBlockEntity(blockposition, true);
   }

   @Nullable
   public TileEntity getBlockEntity(BlockPosition blockposition, boolean validate) {
      if (this.capturedTileEntities.containsKey(blockposition)) {
         return this.capturedTileEntities.get(blockposition);
      } else {
         return this.u(blockposition)
            ? null
            : (!this.B && Thread.currentThread() != this.d ? null : this.l(blockposition).a(blockposition, Chunk.EnumTileEntityState.a));
      }
   }

   public void a(TileEntity tileentity) {
      BlockPosition blockposition = tileentity.p();
      if (!this.u(blockposition)) {
         if (this.captureBlockStates) {
            this.capturedTileEntities.put(blockposition.i(), tileentity);
            return;
         }

         this.l(blockposition).b(tileentity);
      }
   }

   public void n(BlockPosition blockposition) {
      if (!this.u(blockposition)) {
         this.l(blockposition).d(blockposition);
      }
   }

   public boolean o(BlockPosition blockposition) {
      return this.u(blockposition) ? false : this.I().b(SectionPosition.a(blockposition.u()), SectionPosition.a(blockposition.w()));
   }

   public boolean a(BlockPosition blockposition, Entity entity, EnumDirection enumdirection) {
      if (this.u(blockposition)) {
         return false;
      } else {
         IChunkAccess ichunkaccess = this.a(SectionPosition.a(blockposition.u()), SectionPosition.a(blockposition.w()), ChunkStatus.o, false);
         return ichunkaccess == null ? false : ichunkaccess.a_(blockposition).a(this, blockposition, entity, enumdirection);
      }
   }

   public boolean a(BlockPosition blockposition, Entity entity) {
      return this.a(blockposition, entity, EnumDirection.b);
   }

   public void P() {
      double d0 = 1.0 - (double)(this.d(1.0F) * 5.0F) / 16.0;
      double d1 = 1.0 - (double)(this.b(1.0F) * 5.0F) / 16.0;
      double d2 = 0.5 + 2.0 * MathHelper.a((double)MathHelper.b(this.f(1.0F) * (float) (Math.PI * 2)), -0.25, 0.25);
      this.f = (int)((1.0 - d2 * d0 * d1) * 11.0);
   }

   public void b(boolean flag, boolean flag1) {
      this.I().a(flag, flag1);
   }

   public BlockPosition Q() {
      BlockPosition blockposition = new BlockPosition(this.A.a(), this.A.b(), this.A.c());
      if (!this.p_().a(blockposition)) {
         blockposition = this.a(HeightMap.Type.e, BlockPosition.a(this.p_().a(), 0.0, this.p_().b()));
      }

      return blockposition;
   }

   public float R() {
      return this.A.d();
   }

   protected void S() {
      if (this.A.k()) {
         this.w = 1.0F;
         if (this.A.i()) {
            this.y = 1.0F;
         }
      }
   }

   @Override
   public void close() throws IOException {
      this.I().close();
   }

   @Nullable
   @Override
   public IBlockAccess c(int i, int j) {
      return this.a(i, j, ChunkStatus.o, false);
   }

   @Override
   public List<Entity> a(@Nullable Entity entity, AxisAlignedBB axisalignedbb, Predicate<? super Entity> predicate) {
      this.ac().d("getEntities");
      List<Entity> list = Lists.newArrayList();
      this.E().a(axisalignedbb, entity1 -> {
         if (entity1 != entity && predicate.test(entity1)) {
            list.add(entity1);
         }

         if (entity1 instanceof EntityEnderDragon) {
            for(EntityComplexPart entitycomplexpart : ((EntityEnderDragon)entity1).w()) {
               if (entity1 != entity && predicate.test(entitycomplexpart)) {
                  list.add(entitycomplexpart);
               }
            }
         }
      });
      return list;
   }

   @Override
   public <T extends Entity> List<T> a(EntityTypeTest<Entity, T> entitytypetest, AxisAlignedBB axisalignedbb, Predicate<? super T> predicate) {
      List<T> list = Lists.newArrayList();
      this.a(entitytypetest, axisalignedbb, predicate, list);
      return list;
   }

   public <T extends Entity> void a(
      EntityTypeTest<Entity, T> entitytypetest, AxisAlignedBB axisalignedbb, Predicate<? super T> predicate, List<? super T> list
   ) {
      this.a(entitytypetest, axisalignedbb, predicate, list, Integer.MAX_VALUE);
   }

   public <T extends Entity> void a(
      EntityTypeTest<Entity, T> entitytypetest, AxisAlignedBB axisalignedbb, Predicate<? super T> predicate, List<? super T> list, int i
   ) {
      this.ac().d("getEntities");
      this.E().a(entitytypetest, axisalignedbb, entity -> {
         if (predicate.test(entity)) {
            list.add(entity);
            if (list.size() >= i) {
               return AbortableIterationConsumer.a.b;
            }
         }

         if (entity instanceof EntityEnderDragon entityenderdragon) {
            for(EntityComplexPart entitycomplexpart : entityenderdragon.w()) {
               T t0 = entitytypetest.a(entitycomplexpart);
               if (t0 != null && predicate.test(t0)) {
                  list.add(t0);
                  if (list.size() >= i) {
                     return AbortableIterationConsumer.a.b;
                  }
               }
            }
         }

         return AbortableIterationConsumer.a.a;
      });
   }

   @Nullable
   public abstract Entity a(int var1);

   public void p(BlockPosition blockposition) {
      if (this.D(blockposition)) {
         this.l(blockposition).a(true);
      }
   }

   @Override
   public int m_() {
      return 63;
   }

   public int q(BlockPosition blockposition) {
      byte b0 = 0;
      int i = Math.max(b0, this.c(blockposition.d(), EnumDirection.a));
      if (i >= 15) {
         return i;
      } else {
         i = Math.max(i, this.c(blockposition.c(), EnumDirection.b));
         if (i >= 15) {
            return i;
         } else {
            i = Math.max(i, this.c(blockposition.e(), EnumDirection.c));
            if (i >= 15) {
               return i;
            } else {
               i = Math.max(i, this.c(blockposition.f(), EnumDirection.d));
               if (i >= 15) {
                  return i;
               } else {
                  i = Math.max(i, this.c(blockposition.g(), EnumDirection.e));
                  if (i >= 15) {
                     return i;
                  } else {
                     i = Math.max(i, this.c(blockposition.h(), EnumDirection.f));
                     return i >= 15 ? i : i;
                  }
               }
            }
         }
      }
   }

   public boolean a(BlockPosition blockposition, EnumDirection enumdirection) {
      return this.b(blockposition, enumdirection) > 0;
   }

   public int b(BlockPosition blockposition, EnumDirection enumdirection) {
      IBlockData iblockdata = this.a_(blockposition);
      int i = iblockdata.b(this, blockposition, enumdirection);
      return iblockdata.g(this, blockposition) ? Math.max(i, this.q(blockposition)) : i;
   }

   public boolean r(BlockPosition blockposition) {
      return this.b(blockposition.d(), EnumDirection.a) > 0
         ? true
         : (
            this.b(blockposition.c(), EnumDirection.b) > 0
               ? true
               : (
                  this.b(blockposition.e(), EnumDirection.c) > 0
                     ? true
                     : (
                        this.b(blockposition.f(), EnumDirection.d) > 0
                           ? true
                           : (this.b(blockposition.g(), EnumDirection.e) > 0 ? true : this.b(blockposition.h(), EnumDirection.f) > 0)
                     )
               )
         );
   }

   public int s(BlockPosition blockposition) {
      int i = 0;

      for(EnumDirection enumdirection : a) {
         int l = this.b(blockposition.a(enumdirection), enumdirection);
         if (l >= 15) {
            return 15;
         }

         if (l > i) {
            i = l;
         }
      }

      return i;
   }

   public void T() {
   }

   public long U() {
      return this.A.e();
   }

   public long V() {
      return this.A.f();
   }

   public boolean a(EntityHuman entityhuman, BlockPosition blockposition) {
      return true;
   }

   public void a(Entity entity, byte b0) {
   }

   public void a(Entity entity, DamageSource damagesource) {
   }

   public void a(BlockPosition blockposition, Block block, int i, int j) {
      this.a_(blockposition).a(this, blockposition, i, j);
   }

   @Override
   public WorldData n_() {
      return this.A;
   }

   public GameRules W() {
      return this.A.q();
   }

   public float b(float f) {
      return MathHelper.i(f, this.x, this.y) * this.d(f);
   }

   public void c(float f) {
      float f1 = MathHelper.a(f, 0.0F, 1.0F);
      this.x = f1;
      this.y = f1;
   }

   public float d(float f) {
      return MathHelper.i(f, this.v, this.w);
   }

   public void e(float f) {
      float f1 = MathHelper.a(f, 0.0F, 1.0F);
      this.v = f1;
      this.w = f1;
   }

   public boolean X() {
      return this.q_().g() && !this.q_().h() ? (double)this.b(1.0F) > 0.9 : false;
   }

   public boolean Y() {
      return (double)this.d(1.0F) > 0.2;
   }

   public boolean t(BlockPosition blockposition) {
      if (!this.Y()) {
         return false;
      } else if (!this.g(blockposition)) {
         return false;
      } else if (this.a(HeightMap.Type.e, blockposition).v() > blockposition.v()) {
         return false;
      } else {
         BiomeBase biomebase = this.v(blockposition).a();
         return biomebase.a(blockposition) == BiomeBase.Precipitation.b;
      }
   }

   @Nullable
   public abstract WorldMap a(String var1);

   public abstract void a(String var1, WorldMap var2);

   public abstract int t();

   public void b(int i, BlockPosition blockposition, int j) {
   }

   public CrashReportSystemDetails a(CrashReport crashreport) {
      CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Affected level", 1);
      crashreportsystemdetails.a("All players", () -> {
         int i = this.v().size();
         return i + " total; " + this.v();
      });
      IChunkProvider ichunkprovider = this.I();
      crashreportsystemdetails.a("Chunk stats", ichunkprovider::e);
      crashreportsystemdetails.a("Level dimension", () -> this.ab().a().toString());

      try {
         this.A.a(crashreportsystemdetails, this);
      } catch (Throwable var6) {
         crashreportsystemdetails.a("Level Data Unobtainable", var6);
      }

      return crashreportsystemdetails;
   }

   public abstract void a(int var1, BlockPosition var2, int var3);

   public void a(double d0, double d1, double d2, double d3, double d4, double d5, @Nullable NBTTagCompound nbttagcompound) {
   }

   public abstract Scoreboard H();

   public void c(BlockPosition blockposition, Block block) {
      for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.a) {
         BlockPosition blockposition1 = blockposition.a(enumdirection);
         if (this.D(blockposition1)) {
            IBlockData iblockdata = this.a_(blockposition1);
            if (iblockdata.a(Blocks.gX)) {
               this.a(iblockdata, blockposition1, block, blockposition, false);
            } else if (iblockdata.g(this, blockposition1)) {
               blockposition1 = blockposition1.a(enumdirection);
               iblockdata = this.a_(blockposition1);
               if (iblockdata.a(Blocks.gX)) {
                  this.a(iblockdata, blockposition1, block, blockposition, false);
               }
            }
         }
      }
   }

   @Override
   public DifficultyDamageScaler d_(BlockPosition blockposition) {
      long i = 0L;
      float f = 0.0F;
      if (this.D(blockposition)) {
         f = this.am();
         i = this.l(blockposition).u();
      }

      return new DifficultyDamageScaler(this.ah(), this.V(), i, f);
   }

   @Override
   public int o_() {
      return this.f;
   }

   public void c(int i) {
   }

   @Override
   public WorldBorder p_() {
      return this.G;
   }

   public void a(Packet<?> packet) {
      throw new UnsupportedOperationException("Can't send packets to server unless you're on the client.");
   }

   @Override
   public DimensionManager q_() {
      return this.E.a();
   }

   public ResourceKey<DimensionManager> Z() {
      return this.D;
   }

   public Holder<DimensionManager> aa() {
      return this.E;
   }

   public ResourceKey<World> ab() {
      return this.I;
   }

   @Override
   public RandomSource r_() {
      return this.z;
   }

   @Override
   public boolean a(BlockPosition blockposition, Predicate<IBlockData> predicate) {
      return predicate.test(this.a_(blockposition));
   }

   @Override
   public boolean b(BlockPosition blockposition, Predicate<Fluid> predicate) {
      return predicate.test(this.b_(blockposition));
   }

   public abstract CraftingManager q();

   public BlockPosition a(int i, int j, int k, int l) {
      this.t = this.t * 3 + 1013904223;
      int i1 = this.t >> 2;
      return new BlockPosition(i + (i1 & 15), j + (i1 >> 16 & l), k + (i1 >> 8 & 15));
   }

   public boolean r() {
      return false;
   }

   public GameProfilerFiller ac() {
      return this.F.get();
   }

   public Supplier<GameProfilerFiller> ad() {
      return this.F;
   }

   @Override
   public BiomeManager s_() {
      return this.H;
   }

   public final boolean ae() {
      return this.e;
   }

   public abstract LevelEntityGetter<Entity> E();

   @Override
   public long t_() {
      return (long)(this.L++);
   }

   @Override
   public IRegistryCustom u_() {
      return this.J;
   }

   public DamageSources af() {
      return this.K;
   }

   public static enum a {
      a,
      b,
      c,
      d;
   }
}
