package net.minecraft.world.level.chunk;

import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.BiomeResolver;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.WorldGenStage;
import net.minecraft.world.level.levelgen.blending.BlendingData;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidType;
import net.minecraft.world.ticks.TickContainerAccess;
import net.minecraft.world.ticks.TickListEmpty;

public class ProtoChunkExtension extends ProtoChunk {
   private final Chunk l;
   private final boolean m;

   public ProtoChunkExtension(Chunk var0, boolean var1) {
      super(var0.f(), ChunkConverter.a, var0.j, var0.D().u_().d(Registries.an), var0.t());
      this.l = var0;
      this.m = var1;
   }

   @Nullable
   @Override
   public TileEntity c_(BlockPosition var0) {
      return this.l.c_(var0);
   }

   @Override
   public IBlockData a_(BlockPosition var0) {
      return this.l.a_(var0);
   }

   @Override
   public Fluid b_(BlockPosition var0) {
      return this.l.b_(var0);
   }

   @Override
   public int L() {
      return this.l.L();
   }

   @Override
   public ChunkSection b(int var0) {
      return this.m ? this.l.b(var0) : super.b(var0);
   }

   @Nullable
   @Override
   public IBlockData a(BlockPosition var0, IBlockData var1, boolean var2) {
      return this.m ? this.l.a(var0, var1, var2) : null;
   }

   @Override
   public void a(TileEntity var0) {
      if (this.m) {
         this.l.a(var0);
      }
   }

   @Override
   public void a(Entity var0) {
      if (this.m) {
         this.l.a(var0);
      }
   }

   @Override
   public void a(ChunkStatus var0) {
      if (this.m) {
         super.a(var0);
      }
   }

   @Override
   public ChunkSection[] d() {
      return this.l.d();
   }

   @Override
   public void a(HeightMap.Type var0, long[] var1) {
   }

   private HeightMap.Type c(HeightMap.Type var0) {
      if (var0 == HeightMap.Type.a) {
         return HeightMap.Type.b;
      } else {
         return var0 == HeightMap.Type.c ? HeightMap.Type.d : var0;
      }
   }

   @Override
   public HeightMap a(HeightMap.Type var0) {
      return this.l.a(var0);
   }

   @Override
   public int a(HeightMap.Type var0, int var1, int var2) {
      return this.l.a(this.c(var0), var1, var2);
   }

   @Override
   public Holder<BiomeBase> getNoiseBiome(int var0, int var1, int var2) {
      return this.l.getNoiseBiome(var0, var1, var2);
   }

   @Override
   public ChunkCoordIntPair f() {
      return this.l.f();
   }

   @Nullable
   @Override
   public StructureStart a(Structure var0) {
      return this.l.a(var0);
   }

   @Override
   public void a(Structure var0, StructureStart var1) {
   }

   @Override
   public Map<Structure, StructureStart> g() {
      return this.l.g();
   }

   @Override
   public void a(Map<Structure, StructureStart> var0) {
   }

   @Override
   public LongSet b(Structure var0) {
      return this.l.b(var0);
   }

   @Override
   public void a(Structure var0, long var1) {
   }

   @Override
   public Map<Structure, LongSet> h() {
      return this.l.h();
   }

   @Override
   public void b(Map<Structure, LongSet> var0) {
   }

   @Override
   public void a(boolean var0) {
      this.l.a(var0);
   }

   @Override
   public boolean i() {
      return false;
   }

   @Override
   public ChunkStatus j() {
      return this.l.j();
   }

   @Override
   public void d(BlockPosition var0) {
   }

   @Override
   public void e(BlockPosition var0) {
   }

   @Override
   public void a(NBTTagCompound var0) {
   }

   @Nullable
   @Override
   public NBTTagCompound f(BlockPosition var0) {
      return this.l.f(var0);
   }

   @Nullable
   @Override
   public NBTTagCompound g(BlockPosition var0) {
      return this.l.g(var0);
   }

   @Override
   public Stream<BlockPosition> n() {
      return this.l.n();
   }

   @Override
   public TickContainerAccess<Block> o() {
      return this.m ? this.l.o() : TickListEmpty.a();
   }

   @Override
   public TickContainerAccess<FluidType> p() {
      return this.m ? this.l.p() : TickListEmpty.a();
   }

   @Override
   public IChunkAccess.a q() {
      return this.l.q();
   }

   @Nullable
   @Override
   public BlendingData t() {
      return this.l.t();
   }

   @Override
   public void a(BlendingData var0) {
      this.l.a(var0);
   }

   @Override
   public CarvingMask a(WorldGenStage.Features var0) {
      if (this.m) {
         return super.a(var0);
      } else {
         throw (UnsupportedOperationException)SystemUtils.b(new UnsupportedOperationException("Meaningless in this context"));
      }
   }

   @Override
   public CarvingMask b(WorldGenStage.Features var0) {
      if (this.m) {
         return super.b(var0);
      } else {
         throw (UnsupportedOperationException)SystemUtils.b(new UnsupportedOperationException("Meaningless in this context"));
      }
   }

   public Chunk A() {
      return this.l;
   }

   @Override
   public boolean v() {
      return this.l.v();
   }

   @Override
   public void b(boolean var0) {
      this.l.b(var0);
   }

   @Override
   public void a(BiomeResolver var0, Climate.Sampler var1) {
      if (this.m) {
         this.l.a(var0, var1);
      }
   }
}
