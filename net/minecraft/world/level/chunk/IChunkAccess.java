package net.minecraft.world.level.chunk;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.shorts.ShortArrayList;
import it.unimi.dsi.fastutil.shorts.ShortList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportSystemDetails;
import net.minecraft.ReportedException;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistry;
import net.minecraft.core.QuartPos;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeResolver;
import net.minecraft.world.level.biome.BiomeSettingsGeneration;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEventListenerRegistry;
import net.minecraft.world.level.levelgen.BelowZeroRetrogen;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.blending.BlendingData;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.material.FluidType;
import net.minecraft.world.ticks.SerializableTickContainer;
import net.minecraft.world.ticks.TickContainerAccess;
import org.bukkit.craftbukkit.v1_19_R3.persistence.CraftPersistentDataTypeRegistry;
import org.bukkit.craftbukkit.v1_19_R3.persistence.DirtyCraftPersistentDataContainer;
import org.slf4j.Logger;

public abstract class IChunkAccess implements IBlockAccess, BiomeManager.Provider, StructureAccess {
   private static final Logger l = LogUtils.getLogger();
   private static final LongSet m = new LongOpenHashSet();
   protected final ShortList[] a;
   protected volatile boolean b;
   private volatile boolean n;
   protected final ChunkCoordIntPair c;
   private long o;
   @Nullable
   @Deprecated
   private BiomeSettingsGeneration p;
   @Nullable
   protected NoiseChunk d;
   protected final ChunkConverter e;
   @Nullable
   protected BlendingData f;
   public final Map<HeightMap.Type, HeightMap> g = Maps.newEnumMap(HeightMap.Type.class);
   private final Map<Structure, StructureStart> q = Maps.newHashMap();
   private final Map<Structure, LongSet> r = Maps.newHashMap();
   protected final Map<BlockPosition, NBTTagCompound> h = Maps.newHashMap();
   public final Map<BlockPosition, TileEntity> i = Maps.newHashMap();
   protected final LevelHeightAccessor j;
   protected final ChunkSection[] k;
   private static final CraftPersistentDataTypeRegistry DATA_TYPE_REGISTRY = new CraftPersistentDataTypeRegistry();
   public DirtyCraftPersistentDataContainer persistentDataContainer = new DirtyCraftPersistentDataContainer(DATA_TYPE_REGISTRY);
   public final IRegistry<BiomeBase> biomeRegistry;

   public IChunkAccess(
      ChunkCoordIntPair chunkcoordintpair,
      ChunkConverter chunkconverter,
      LevelHeightAccessor levelheightaccessor,
      IRegistry<BiomeBase> iregistry,
      long i,
      @Nullable ChunkSection[] achunksection,
      @Nullable BlendingData blendingdata
   ) {
      this.c = chunkcoordintpair;
      this.e = chunkconverter;
      this.j = levelheightaccessor;
      this.k = new ChunkSection[levelheightaccessor.aj()];
      this.o = i;
      this.a = new ShortList[levelheightaccessor.aj()];
      this.f = blendingdata;
      if (achunksection != null) {
         if (this.k.length == achunksection.length) {
            System.arraycopy(achunksection, 0, this.k, 0, this.k.length);
         } else {
            l.warn("Could not set level chunk sections, array length is {} instead of {}", achunksection.length, this.k.length);
         }
      }

      a(levelheightaccessor, iregistry, this.k);
      this.biomeRegistry = iregistry;
   }

   private static void a(LevelHeightAccessor levelheightaccessor, IRegistry<BiomeBase> iregistry, ChunkSection[] achunksection) {
      for(int i = 0; i < achunksection.length; ++i) {
         if (achunksection[i] == null) {
            achunksection[i] = new ChunkSection(levelheightaccessor.g(i), iregistry);
         }
      }
   }

   public GameEventListenerRegistry a(int i) {
      return GameEventListenerRegistry.a;
   }

   @Nullable
   public abstract IBlockData a(BlockPosition var1, IBlockData var2, boolean var3);

   public abstract void a(TileEntity var1);

   public abstract void a(Entity var1);

   @Nullable
   public ChunkSection a() {
      ChunkSection[] achunksection = this.d();

      for(int i = achunksection.length - 1; i >= 0; --i) {
         ChunkSection chunksection = achunksection[i];
         if (!chunksection.c()) {
            return chunksection;
         }
      }

      return null;
   }

   public int b() {
      ChunkSection chunksection = this.a();
      return chunksection == null ? this.v_() : chunksection.g();
   }

   public Set<BlockPosition> c() {
      Set<BlockPosition> set = Sets.newHashSet(this.h.keySet());
      set.addAll(this.i.keySet());
      return set;
   }

   public ChunkSection[] d() {
      return this.k;
   }

   public ChunkSection b(int i) {
      return this.d()[i];
   }

   public Collection<Entry<HeightMap.Type, HeightMap>> e() {
      return Collections.unmodifiableSet(this.g.entrySet());
   }

   public void a(HeightMap.Type heightmap_type, long[] along) {
      this.a(heightmap_type).a(this, heightmap_type, along);
   }

   public HeightMap a(HeightMap.Type heightmap_type) {
      return this.g.computeIfAbsent(heightmap_type, heightmap_type1 -> new HeightMap(this, heightmap_type1));
   }

   public boolean b(HeightMap.Type heightmap_type) {
      return this.g.get(heightmap_type) != null;
   }

   public int a(HeightMap.Type heightmap_type, int i, int j) {
      HeightMap heightmap = this.g.get(heightmap_type);
      if (heightmap == null) {
         if (SharedConstants.aO && this instanceof Chunk) {
            l.error("Unprimed heightmap: " + heightmap_type + " " + i + " " + j);
         }

         HeightMap.a(this, EnumSet.of(heightmap_type));
         heightmap = this.g.get(heightmap_type);
      }

      return heightmap.a(i & 15, j & 15) - 1;
   }

   public ChunkCoordIntPair f() {
      return this.c;
   }

   @Nullable
   @Override
   public StructureStart a(Structure structure) {
      return this.q.get(structure);
   }

   @Override
   public void a(Structure structure, StructureStart structurestart) {
      this.q.put(structure, structurestart);
      this.b = true;
   }

   public Map<Structure, StructureStart> g() {
      return Collections.unmodifiableMap(this.q);
   }

   public void a(Map<Structure, StructureStart> map) {
      this.q.clear();
      this.q.putAll(map);
      this.b = true;
   }

   @Override
   public LongSet b(Structure structure) {
      return (LongSet)this.r.getOrDefault(structure, m);
   }

   @Override
   public void a(Structure structure, long i) {
      ((LongSet)this.r.computeIfAbsent(structure, structure1 -> new LongOpenHashSet())).add(i);
      this.b = true;
   }

   @Override
   public Map<Structure, LongSet> h() {
      return Collections.unmodifiableMap(this.r);
   }

   @Override
   public void b(Map<Structure, LongSet> map) {
      this.r.clear();
      this.r.putAll(map);
      this.b = true;
   }

   public boolean a(int i, int j) {
      if (i < this.v_()) {
         i = this.v_();
      }

      if (j >= this.ai()) {
         j = this.ai() - 1;
      }

      for(int k = i; k <= j; k += 16) {
         if (!this.b(this.e(k)).c()) {
            return false;
         }
      }

      return true;
   }

   public void a(boolean flag) {
      this.b = flag;
      if (!flag) {
         this.persistentDataContainer.dirty(false);
      }
   }

   public boolean i() {
      return this.b || this.persistentDataContainer.dirty();
   }

   public abstract ChunkStatus j();

   public abstract void d(BlockPosition var1);

   public void e(BlockPosition blockposition) {
      l.warn("Trying to mark a block for PostProcessing @ {}, but this operation is not supported.", blockposition);
   }

   public ShortList[] k() {
      return this.a;
   }

   public void a(short short0, int i) {
      a(this.k(), i).add(short0);
   }

   public void a(NBTTagCompound nbttagcompound) {
      this.h.put(TileEntity.c(nbttagcompound), nbttagcompound);
   }

   @Nullable
   public NBTTagCompound f(BlockPosition blockposition) {
      return this.h.get(blockposition);
   }

   @Nullable
   public abstract NBTTagCompound g(BlockPosition var1);

   public abstract Stream<BlockPosition> n();

   public abstract TickContainerAccess<Block> o();

   public abstract TickContainerAccess<FluidType> p();

   public abstract IChunkAccess.a q();

   public ChunkConverter r() {
      return this.e;
   }

   public boolean s() {
      return this.f != null;
   }

   @Nullable
   public BlendingData t() {
      return this.f;
   }

   public void a(BlendingData blendingdata) {
      this.f = blendingdata;
   }

   public long u() {
      return this.o;
   }

   public void a(long i) {
      this.o += i;
   }

   public void b(long i) {
      this.o = i;
   }

   public static ShortList a(ShortList[] ashortlist, int i) {
      if (ashortlist[i] == null) {
         ashortlist[i] = new ShortArrayList();
      }

      return ashortlist[i];
   }

   public boolean v() {
      return this.n;
   }

   public void b(boolean flag) {
      this.n = flag;
      this.a(true);
   }

   @Override
   public int v_() {
      return this.j.v_();
   }

   @Override
   public int w_() {
      return this.j.w_();
   }

   public NoiseChunk a(Function<IChunkAccess, NoiseChunk> function) {
      if (this.d == null) {
         this.d = function.apply(this);
      }

      return this.d;
   }

   @Deprecated
   public BiomeSettingsGeneration a(Supplier<BiomeSettingsGeneration> supplier) {
      if (this.p == null) {
         this.p = supplier.get();
      }

      return this.p;
   }

   @Override
   public Holder<BiomeBase> getNoiseBiome(int i, int j, int k) {
      try {
         int l = QuartPos.a(this.v_());
         int i1 = l + QuartPos.a(this.w_()) - 1;
         int j1 = MathHelper.a(j, l, i1);
         int k1 = this.e(QuartPos.c(j1));
         return this.k[k1].c(i & 3, j1 & 3, k & 3);
      } catch (Throwable var8) {
         CrashReport crashreport = CrashReport.a(var8, "Getting biome");
         CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Biome being got");
         crashreportsystemdetails.a("Location", () -> CrashReportSystemDetails.a(this, i, j, k));
         throw new ReportedException(crashreport);
      }
   }

   public void setBiome(int i, int j, int k, Holder<BiomeBase> biome) {
      try {
         int l = QuartPos.a(this.v_());
         int i1 = l + QuartPos.a(this.w_()) - 1;
         int j1 = MathHelper.a(j, l, i1);
         int k1 = this.e(QuartPos.c(j1));
         this.k[k1].setBiome(i & 3, j1 & 3, k & 3, biome);
      } catch (Throwable var9) {
         CrashReport crashreport = CrashReport.a(var9, "Setting biome");
         CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Biome being set");
         crashreportsystemdetails.a("Location", () -> CrashReportSystemDetails.a(this, i, j, k));
         throw new ReportedException(crashreport);
      }
   }

   public void a(BiomeResolver biomeresolver, Climate.Sampler climate_sampler) {
      ChunkCoordIntPair chunkcoordintpair = this.f();
      int i = QuartPos.a(chunkcoordintpair.d());
      int j = QuartPos.a(chunkcoordintpair.e());
      LevelHeightAccessor levelheightaccessor = this.z();

      for(int k = levelheightaccessor.ak(); k < levelheightaccessor.al(); ++k) {
         ChunkSection chunksection = this.b(this.f(k));
         chunksection.a(biomeresolver, climate_sampler, i, j);
      }
   }

   public boolean w() {
      return !this.h().isEmpty();
   }

   @Nullable
   public BelowZeroRetrogen x() {
      return null;
   }

   public boolean y() {
      return this.x() != null;
   }

   public LevelHeightAccessor z() {
      return this;
   }

   public static record a(SerializableTickContainer<Block> blocks, SerializableTickContainer<FluidType> fluids) {
      private final SerializableTickContainer<Block> a;
      private final SerializableTickContainer<FluidType> b;

      public a(SerializableTickContainer<Block> blocks, SerializableTickContainer<FluidType> fluids) {
         this.a = blocks;
         this.b = fluids;
      }
   }
}
