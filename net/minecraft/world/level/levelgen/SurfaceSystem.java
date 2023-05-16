package net.minecraft.world.level.levelgen;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.BlockColumn;
import net.minecraft.world.level.chunk.IChunkAccess;
import net.minecraft.world.level.dimension.DimensionManager;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.synth.NoiseGeneratorNormal;
import net.minecraft.world.level.material.Material;

public class SurfaceSystem {
   private static final IBlockData a = Blocks.hi.o();
   private static final IBlockData b = Blocks.hj.o();
   private static final IBlockData c = Blocks.iz.o();
   private static final IBlockData d = Blocks.hm.o();
   private static final IBlockData e = Blocks.hu.o();
   private static final IBlockData f = Blocks.hw.o();
   private static final IBlockData g = Blocks.hq.o();
   private static final IBlockData h = Blocks.iB.o();
   private static final IBlockData i = Blocks.dO.o();
   private final IBlockData j;
   private final int k;
   private final IBlockData[] l;
   private final NoiseGeneratorNormal m;
   private final NoiseGeneratorNormal n;
   private final NoiseGeneratorNormal o;
   private final NoiseGeneratorNormal p;
   private final NoiseGeneratorNormal q;
   private final NoiseGeneratorNormal r;
   private final NoiseGeneratorNormal s;
   private final PositionalRandomFactory t;
   private final NoiseGeneratorNormal u;
   private final NoiseGeneratorNormal v;

   public SurfaceSystem(RandomState var0, IBlockData var1, int var2, PositionalRandomFactory var3) {
      this.j = var1;
      this.k = var2;
      this.t = var3;
      this.m = var0.a(Noises.P);
      this.l = a(var3.a(new MinecraftKey("clay_bands")));
      this.u = var0.a(Noises.N);
      this.v = var0.a(Noises.O);
      this.n = var0.a(Noises.Q);
      this.o = var0.a(Noises.R);
      this.p = var0.a(Noises.S);
      this.q = var0.a(Noises.T);
      this.r = var0.a(Noises.U);
      this.s = var0.a(Noises.V);
   }

   public void a(
      RandomState var0,
      BiomeManager var1,
      IRegistry<BiomeBase> var2,
      boolean var3,
      WorldGenerationContext var4,
      final IChunkAccess var5,
      NoiseChunk var6,
      SurfaceRules.o var7
   ) {
      final BlockPosition.MutableBlockPosition var8 = new BlockPosition.MutableBlockPosition();
      final ChunkCoordIntPair var9 = var5.f();
      int var10 = var9.d();
      int var11 = var9.e();
      BlockColumn var12 = new BlockColumn() {
         @Override
         public IBlockData a(int var0) {
            return var5.a_(var8.q(var0));
         }

         @Override
         public void a(int var0, IBlockData var1) {
            LevelHeightAccessor var2 = var5.z();
            if (var0 >= var2.v_() && var0 < var2.ai()) {
               var5.a(var8.q(var0), var1, false);
               if (!var1.r().c()) {
                  var5.e(var8);
               }
            }
         }

         @Override
         public String toString() {
            return "ChunkBlockColumn " + var9;
         }
      };
      SurfaceRules.g var13 = new SurfaceRules.g(this, var0, var5, var6, var1::a, var2, var4);
      SurfaceRules.u var14 = var7.apply(var13);
      BlockPosition.MutableBlockPosition var15 = new BlockPosition.MutableBlockPosition();

      for(int var16 = 0; var16 < 16; ++var16) {
         for(int var17 = 0; var17 < 16; ++var17) {
            int var18 = var10 + var16;
            int var19 = var11 + var17;
            int var20 = var5.a(HeightMap.Type.a, var16, var17) + 1;
            var8.p(var18).r(var19);
            Holder<BiomeBase> var21 = var1.a(var15.d(var18, var3 ? 0 : var20, var19));
            if (var21.a(Biomes.B)) {
               this.a(var12, var18, var19, var20, var5);
            }

            int var22 = var5.a(HeightMap.Type.a, var16, var17) + 1;
            var13.a(var18, var19);
            int var23 = 0;
            int var24 = Integer.MIN_VALUE;
            int var25 = Integer.MAX_VALUE;
            int var26 = var5.v_();

            for(int var27 = var22; var27 >= var26; --var27) {
               IBlockData var28 = var12.a(var27);
               if (var28.h()) {
                  var23 = 0;
                  var24 = Integer.MIN_VALUE;
               } else if (!var28.r().c()) {
                  if (var24 == Integer.MIN_VALUE) {
                     var24 = var27 + 1;
                  }
               } else {
                  if (var25 >= var27) {
                     var25 = DimensionManager.g;

                     for(int var29 = var27 - 1; var29 >= var26 - 1; --var29) {
                        IBlockData var30 = var12.a(var29);
                        if (!this.a(var30)) {
                           var25 = var29 + 1;
                           break;
                        }
                     }
                  }

                  ++var23;
                  int var29 = var27 - var25 + 1;
                  var13.a(var23, var29, var24, var18, var27, var19);
                  if (var28 == this.j) {
                     IBlockData var30 = var14.tryApply(var18, var27, var19);
                     if (var30 != null) {
                        var12.a(var27, var30);
                     }
                  }
               }
            }

            if (var21.a(Biomes.W) || var21.a(Biomes.X)) {
               this.a(var13.b(), var21.a(), var12, var15, var18, var19, var20);
            }
         }
      }
   }

   protected int a(int var0, int var1) {
      double var2 = this.u.a((double)var0, 0.0, (double)var1);
      return (int)(var2 * 2.75 + 3.0 + this.t.a(var0, 0, var1).j() * 0.25);
   }

   protected double b(int var0, int var1) {
      return this.v.a((double)var0, 0.0, (double)var1);
   }

   private boolean a(IBlockData var0) {
      return !var0.h() && var0.r().c();
   }

   @Deprecated
   public Optional<IBlockData> a(
      SurfaceRules.o var0,
      CarvingContext var1,
      Function<BlockPosition, Holder<BiomeBase>> var2,
      IChunkAccess var3,
      NoiseChunk var4,
      BlockPosition var5,
      boolean var6
   ) {
      SurfaceRules.g var7 = new SurfaceRules.g(this, var1.d(), var3, var4, var2, var1.c().d(Registries.an), var1);
      SurfaceRules.u var8 = var0.apply(var7);
      int var9 = var5.u();
      int var10 = var5.v();
      int var11 = var5.w();
      var7.a(var9, var11);
      var7.a(1, 1, var6 ? var10 + 1 : Integer.MIN_VALUE, var9, var10, var11);
      IBlockData var12 = var8.tryApply(var9, var10, var11);
      return Optional.ofNullable(var12);
   }

   private void a(BlockColumn var0, int var1, int var2, int var3, LevelHeightAccessor var4) {
      double var5 = 0.2;
      double var7 = Math.min(Math.abs(this.p.a((double)var1, 0.0, (double)var2) * 8.25), this.n.a((double)var1 * 0.2, 0.0, (double)var2 * 0.2) * 15.0);
      if (!(var7 <= 0.0)) {
         double var9 = 0.75;
         double var11 = 1.5;
         double var13 = Math.abs(this.o.a((double)var1 * 0.75, 0.0, (double)var2 * 0.75) * 1.5);
         double var15 = 64.0 + Math.min(var7 * var7 * 2.5, Math.ceil(var13 * 50.0) + 24.0);
         int var17 = MathHelper.a(var15);
         if (var3 <= var17) {
            for(int var18 = var17; var18 >= var4.v_(); --var18) {
               IBlockData var19 = var0.a(var18);
               if (var19.a(this.j.b())) {
                  break;
               }

               if (var19.a(Blocks.G)) {
                  return;
               }
            }

            for(int var18 = var17; var18 >= var4.v_() && var0.a(var18).h(); --var18) {
               var0.a(var18, this.j);
            }
         }
      }
   }

   private void a(int var0, BiomeBase var1, BlockColumn var2, BlockPosition.MutableBlockPosition var3, int var4, int var5, int var6) {
      double var7 = 1.28;
      double var9 = Math.min(Math.abs(this.s.a((double)var4, 0.0, (double)var5) * 8.25), this.q.a((double)var4 * 1.28, 0.0, (double)var5 * 1.28) * 15.0);
      if (!(var9 <= 1.8)) {
         double var13 = 1.17;
         double var15 = 1.5;
         double var17 = Math.abs(this.r.a((double)var4 * 1.17, 0.0, (double)var5 * 1.17) * 1.5);
         double var19 = Math.min(var9 * var9 * 1.2, Math.ceil(var17 * 40.0) + 14.0);
         if (var1.d(var3.d(var4, 63, var5))) {
            var19 -= 2.0;
         }

         double var11;
         if (var19 > 2.0) {
            var11 = (double)this.k - var19 - 7.0;
            var19 += (double)this.k;
         } else {
            var19 = 0.0;
            var11 = 0.0;
         }

         double var21 = var19;
         RandomSource var23 = this.t.a(var4, 0, var5);
         int var24 = 2 + var23.a(4);
         int var25 = this.k + 18 + var23.a(10);
         int var26 = 0;

         for(int var27 = Math.max(var6, (int)var19 + 1); var27 >= var0; --var27) {
            if (var2.a(var27).h() && var27 < (int)var21 && var23.j() > 0.01
               || var2.a(var27).d() == Material.j && var27 > (int)var11 && var27 < this.k && var11 != 0.0 && var23.j() > 0.15) {
               if (var26 <= var24 && var27 > var25) {
                  var2.a(var27, i);
                  ++var26;
               } else {
                  var2.a(var27, h);
               }
            }
         }
      }
   }

   private static IBlockData[] a(RandomSource var0) {
      IBlockData[] var1 = new IBlockData[192];
      Arrays.fill(var1, c);

      for(int var2 = 0; var2 < var1.length; ++var2) {
         var2 += var0.a(5) + 1;
         if (var2 < var1.length) {
            var1[var2] = b;
         }
      }

      a(var0, var1, 1, d);
      a(var0, var1, 2, e);
      a(var0, var1, 1, f);
      int var2 = var0.a(9, 15);
      int var3 = 0;

      for(int var4 = 0; var3 < var2 && var4 < var1.length; var4 += var0.a(16) + 4) {
         var1[var4] = a;
         if (var4 - 1 > 0 && var0.h()) {
            var1[var4 - 1] = g;
         }

         if (var4 + 1 < var1.length && var0.h()) {
            var1[var4 + 1] = g;
         }

         ++var3;
      }

      return var1;
   }

   private static void a(RandomSource var0, IBlockData[] var1, int var2, IBlockData var3) {
      int var4 = var0.a(6, 15);

      for(int var5 = 0; var5 < var4; ++var5) {
         int var6 = var2 + var0.a(3);
         int var7 = var0.a(var1.length);

         for(int var8 = 0; var7 + var8 < var1.length && var8 < var6; ++var8) {
            var1[var7 + var8] = var3;
         }
      }
   }

   protected IBlockData a(int var0, int var1, int var2) {
      int var3 = (int)Math.round(this.m.a((double)var0, 0.0, (double)var2) * 4.0);
      return this.l[(var1 + var3 + this.l.length) % this.l.length];
   }
}
