package net.minecraft.world.level.chunk;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.shorts.ShortList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistry;
import net.minecraft.core.SectionPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.BelowZeroRetrogen;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.WorldGenStage;
import net.minecraft.world.level.levelgen.blending.BlendingData;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.lighting.LightEngine;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidType;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.ticks.LevelChunkTicks;
import net.minecraft.world.ticks.ProtoChunkTickList;
import net.minecraft.world.ticks.TickContainerAccess;

public class ProtoChunk extends IChunkAccess {
   @Nullable
   private volatile LightEngine l;
   private volatile ChunkStatus m = ChunkStatus.c;
   private final List<NBTTagCompound> n = Lists.newArrayList();
   private final List<BlockPosition> o = Lists.newArrayList();
   private final Map<WorldGenStage.Features, CarvingMask> p = new Object2ObjectArrayMap();
   @Nullable
   private BelowZeroRetrogen q;
   private final ProtoChunkTickList<Block> r;
   private final ProtoChunkTickList<FluidType> s;

   public ProtoChunk(ChunkCoordIntPair var0, ChunkConverter var1, LevelHeightAccessor var2, IRegistry<BiomeBase> var3, @Nullable BlendingData var4) {
      this(var0, var1, null, new ProtoChunkTickList<>(), new ProtoChunkTickList<>(), var2, var3, var4);
   }

   public ProtoChunk(
      ChunkCoordIntPair var0,
      ChunkConverter var1,
      @Nullable ChunkSection[] var2,
      ProtoChunkTickList<Block> var3,
      ProtoChunkTickList<FluidType> var4,
      LevelHeightAccessor var5,
      IRegistry<BiomeBase> var6,
      @Nullable BlendingData var7
   ) {
      super(var0, var1, var5, var6, 0L, var2, var7);
      this.r = var3;
      this.s = var4;
   }

   @Override
   public TickContainerAccess<Block> o() {
      return this.r;
   }

   @Override
   public TickContainerAccess<FluidType> p() {
      return this.s;
   }

   @Override
   public IChunkAccess.a q() {
      return new IChunkAccess.a(this.r, this.s);
   }

   @Override
   public IBlockData a_(BlockPosition var0) {
      int var1 = var0.v();
      if (this.d(var1)) {
         return Blocks.mX.o();
      } else {
         ChunkSection var2 = this.b(this.e(var1));
         return var2.c() ? Blocks.a.o() : var2.a(var0.u() & 15, var1 & 15, var0.w() & 15);
      }
   }

   @Override
   public Fluid b_(BlockPosition var0) {
      int var1 = var0.v();
      if (this.d(var1)) {
         return FluidTypes.a.g();
      } else {
         ChunkSection var2 = this.b(this.e(var1));
         return var2.c() ? FluidTypes.a.g() : var2.b(var0.u() & 15, var1 & 15, var0.w() & 15);
      }
   }

   @Override
   public Stream<BlockPosition> n() {
      return this.o.stream();
   }

   public ShortList[] B() {
      ShortList[] var0 = new ShortList[this.aj()];

      for(BlockPosition var2 : this.o) {
         IChunkAccess.a(var0, this.e(var2.v())).add(k(var2));
      }

      return var0;
   }

   public void b(short var0, int var1) {
      this.j(a(var0, this.g(var1), this.c));
   }

   public void j(BlockPosition var0) {
      this.o.add(var0.i());
   }

   @Nullable
   @Override
   public IBlockData a(BlockPosition var0, IBlockData var1, boolean var2) {
      int var3 = var0.u();
      int var4 = var0.v();
      int var5 = var0.w();
      if (var4 >= this.v_() && var4 < this.ai()) {
         int var6 = this.e(var4);
         if (this.k[var6].c() && var1.a(Blocks.a)) {
            return var1;
         } else {
            if (var1.g() > 0) {
               this.o.add(new BlockPosition((var3 & 15) + this.f().d(), var4, (var5 & 15) + this.f().e()));
            }

            ChunkSection var7 = this.b(var6);
            IBlockData var8 = var7.a(var3 & 15, var4 & 15, var5 & 15, var1);
            if (this.m.b(ChunkStatus.k) && var1 != var8 && (var1.b(this, var0) != var8.b(this, var0) || var1.g() != var8.g() || var1.f() || var8.f())) {
               this.l.a(var0);
            }

            EnumSet<HeightMap.Type> var9 = this.j().h();
            EnumSet<HeightMap.Type> var10 = null;

            for(HeightMap.Type var12 : var9) {
               HeightMap var13 = this.g.get(var12);
               if (var13 == null) {
                  if (var10 == null) {
                     var10 = EnumSet.noneOf(HeightMap.Type.class);
                  }

                  var10.add(var12);
               }
            }

            if (var10 != null) {
               HeightMap.a(this, var10);
            }

            for(HeightMap.Type var12 : var9) {
               this.g.get(var12).a(var3 & 15, var4, var5 & 15, var1);
            }

            return var8;
         }
      } else {
         return Blocks.mX.o();
      }
   }

   @Override
   public void a(TileEntity var0) {
      this.i.put(var0.p(), var0);
   }

   @Nullable
   @Override
   public TileEntity c_(BlockPosition var0) {
      return this.i.get(var0);
   }

   public Map<BlockPosition, TileEntity> C() {
      return this.i;
   }

   public void b(NBTTagCompound var0) {
      this.n.add(var0);
   }

   @Override
   public void a(Entity var0) {
      if (!var0.bL()) {
         NBTTagCompound var1 = new NBTTagCompound();
         var0.e(var1);
         this.b(var1);
      }
   }

   @Override
   public void a(Structure var0, StructureStart var1) {
      BelowZeroRetrogen var2 = this.x();
      if (var2 != null && var1.b()) {
         StructureBoundingBox var3 = var1.a();
         LevelHeightAccessor var4 = this.z();
         if (var3.h() < var4.v_() || var3.k() >= var4.ai()) {
            return;
         }
      }

      super.a(var0, var1);
   }

   public List<NBTTagCompound> D() {
      return this.n;
   }

   @Override
   public ChunkStatus j() {
      return this.m;
   }

   public void a(ChunkStatus var0) {
      this.m = var0;
      if (this.q != null && var0.b(this.q.a())) {
         this.a(null);
      }

      this.a(true);
   }

   @Override
   public Holder<BiomeBase> getNoiseBiome(int var0, int var1, int var2) {
      if (!this.j().b(ChunkStatus.f) && (this.q == null || !this.q.a().b(ChunkStatus.f))) {
         throw new IllegalStateException("Asking for biomes before we have biomes");
      } else {
         return super.getNoiseBiome(var0, var1, var2);
      }
   }

   public static short k(BlockPosition var0) {
      int var1 = var0.u();
      int var2 = var0.v();
      int var3 = var0.w();
      int var4 = var1 & 15;
      int var5 = var2 & 15;
      int var6 = var3 & 15;
      return (short)(var4 | var5 << 4 | var6 << 8);
   }

   public static BlockPosition a(short var0, int var1, ChunkCoordIntPair var2) {
      int var3 = SectionPosition.a(var2.e, var0 & 15);
      int var4 = SectionPosition.a(var1, var0 >>> 4 & 15);
      int var5 = SectionPosition.a(var2.f, var0 >>> 8 & 15);
      return new BlockPosition(var3, var4, var5);
   }

   @Override
   public void e(BlockPosition var0) {
      if (!this.u(var0)) {
         IChunkAccess.a(this.a, this.e(var0.v())).add(k(var0));
      }
   }

   @Override
   public void a(short var0, int var1) {
      IChunkAccess.a(this.a, var1).add(var0);
   }

   public Map<BlockPosition, NBTTagCompound> E() {
      return Collections.unmodifiableMap(this.h);
   }

   @Nullable
   @Override
   public NBTTagCompound g(BlockPosition var0) {
      TileEntity var1 = this.c_(var0);
      return var1 != null ? var1.m() : this.h.get(var0);
   }

   @Override
   public void d(BlockPosition var0) {
      this.i.remove(var0);
      this.h.remove(var0);
   }

   @Nullable
   public CarvingMask a(WorldGenStage.Features var0) {
      return this.p.get(var0);
   }

   public CarvingMask b(WorldGenStage.Features var0) {
      return this.p.computeIfAbsent(var0, var0x -> new CarvingMask(this.w_(), this.v_()));
   }

   public void a(WorldGenStage.Features var0, CarvingMask var1) {
      this.p.put(var0, var1);
   }

   public void a(LightEngine var0) {
      this.l = var0;
   }

   public void a(@Nullable BelowZeroRetrogen var0) {
      this.q = var0;
   }

   @Nullable
   @Override
   public BelowZeroRetrogen x() {
      return this.q;
   }

   private static <T> LevelChunkTicks<T> a(ProtoChunkTickList<T> var0) {
      return new LevelChunkTicks<>(var0.b());
   }

   public LevelChunkTicks<Block> F() {
      return a(this.r);
   }

   public LevelChunkTicks<FluidType> G() {
      return a(this.s);
   }

   @Override
   public LevelHeightAccessor z() {
      return (LevelHeightAccessor)(this.y() ? BelowZeroRetrogen.b : this);
   }
}
