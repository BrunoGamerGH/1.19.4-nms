package net.minecraft.world.level;

import com.google.common.base.Suppliers;
import java.util.List;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.SectionPosition;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.profiling.GameProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.chunk.ChunkEmpty;
import net.minecraft.world.level.chunk.IChunkAccess;
import net.minecraft.world.level.chunk.IChunkProvider;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ChunkCache implements IBlockAccess, ICollisionAccess {
   protected final int a;
   protected final int b;
   protected final IChunkAccess[][] c;
   protected boolean d;
   protected final World e;
   private final Supplier<Holder<BiomeBase>> f;

   public ChunkCache(World var0, BlockPosition var1, BlockPosition var2) {
      this.e = var0;
      this.f = Suppliers.memoize(() -> var0.u_().d(Registries.an).f(Biomes.b));
      this.a = SectionPosition.a(var1.u());
      this.b = SectionPosition.a(var1.w());
      int var3 = SectionPosition.a(var2.u());
      int var4 = SectionPosition.a(var2.w());
      this.c = new IChunkAccess[var3 - this.a + 1][var4 - this.b + 1];
      IChunkProvider var5 = var0.I();
      this.d = true;

      for(int var6 = this.a; var6 <= var3; ++var6) {
         for(int var7 = this.b; var7 <= var4; ++var7) {
            this.c[var6 - this.a][var7 - this.b] = var5.a(var6, var7);
         }
      }

      for(int var6 = SectionPosition.a(var1.u()); var6 <= SectionPosition.a(var2.u()); ++var6) {
         for(int var7 = SectionPosition.a(var1.w()); var7 <= SectionPosition.a(var2.w()); ++var7) {
            IChunkAccess var8 = this.c[var6 - this.a][var7 - this.b];
            if (var8 != null && !var8.a(var1.v(), var2.v())) {
               this.d = false;
               return;
            }
         }
      }
   }

   private IChunkAccess d(BlockPosition var0) {
      return this.a(SectionPosition.a(var0.u()), SectionPosition.a(var0.w()));
   }

   private IChunkAccess a(int var0, int var1) {
      int var2 = var0 - this.a;
      int var3 = var1 - this.b;
      if (var2 >= 0 && var2 < this.c.length && var3 >= 0 && var3 < this.c[var2].length) {
         IChunkAccess var4 = this.c[var2][var3];
         return (IChunkAccess)(var4 != null ? var4 : new ChunkEmpty(this.e, new ChunkCoordIntPair(var0, var1), this.f.get()));
      } else {
         return new ChunkEmpty(this.e, new ChunkCoordIntPair(var0, var1), this.f.get());
      }
   }

   @Override
   public WorldBorder p_() {
      return this.e.p_();
   }

   @Override
   public IBlockAccess c(int var0, int var1) {
      return this.a(var0, var1);
   }

   @Override
   public List<VoxelShape> b(@Nullable Entity var0, AxisAlignedBB var1) {
      return List.of();
   }

   @Nullable
   @Override
   public TileEntity c_(BlockPosition var0) {
      IChunkAccess var1 = this.d(var0);
      return var1.c_(var0);
   }

   @Override
   public IBlockData a_(BlockPosition var0) {
      if (this.u(var0)) {
         return Blocks.a.o();
      } else {
         IChunkAccess var1 = this.d(var0);
         return var1.a_(var0);
      }
   }

   @Override
   public Fluid b_(BlockPosition var0) {
      if (this.u(var0)) {
         return FluidTypes.a.g();
      } else {
         IChunkAccess var1 = this.d(var0);
         return var1.b_(var0);
      }
   }

   @Override
   public int v_() {
      return this.e.v_();
   }

   @Override
   public int w_() {
      return this.e.w_();
   }

   public GameProfilerFiller a() {
      return this.e.ac();
   }
}
