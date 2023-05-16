package net.minecraft.world.level.levelgen;

import java.util.Arrays;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.SectionPosition;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.dimension.DimensionManager;
import org.apache.commons.lang3.mutable.MutableDouble;

public interface Aquifer {
   static Aquifer a(NoiseChunk var0, ChunkCoordIntPair var1, NoiseRouter var2, PositionalRandomFactory var3, int var4, int var5, Aquifer.a var6) {
      return new Aquifer.c(var0, var1, var2, var3, var4, var5, var6);
   }

   static Aquifer a(final Aquifer.a var0) {
      return new Aquifer() {
         @Nullable
         @Override
         public IBlockData a(DensityFunction.b var0x, double var1) {
            return var1 > 0.0 ? null : var0.computeFluid(var0.a(), var0.b(), var0.c()).a(var0.b());
         }

         @Override
         public boolean a() {
            return false;
         }
      };
   }

   @Nullable
   IBlockData a(DensityFunction.b var1, double var2);

   boolean a();

   public interface a {
      Aquifer.b computeFluid(int var1, int var2, int var3);
   }

   public static final class b {
      final int a;
      final IBlockData b;

      public b(int var0, IBlockData var1) {
         this.a = var0;
         this.b = var1;
      }

      public IBlockData a(int var0) {
         return var0 < this.a ? this.b : Blocks.a.o();
      }
   }

   public static class c implements Aquifer {
      private static final int a = 10;
      private static final int b = 9;
      private static final int c = 10;
      private static final int d = 6;
      private static final int e = 3;
      private static final int f = 6;
      private static final int g = 16;
      private static final int h = 12;
      private static final int i = 16;
      private static final int j = 11;
      private static final double k = a(MathHelper.h(10), MathHelper.h(12));
      private final NoiseChunk l;
      private final DensityFunction m;
      private final DensityFunction n;
      private final DensityFunction o;
      private final DensityFunction p;
      private final PositionalRandomFactory q;
      private final Aquifer.b[] r;
      private final long[] s;
      private final Aquifer.a t;
      private final DensityFunction u;
      private final DensityFunction v;
      private boolean w;
      private final int x;
      private final int y;
      private final int z;
      private final int A;
      private final int B;
      private static final int[][] C = new int[][]{
         {0, 0}, {-2, -1}, {-1, -1}, {0, -1}, {1, -1}, {-3, 0}, {-2, 0}, {-1, 0}, {1, 0}, {-2, 1}, {-1, 1}, {0, 1}, {1, 1}
      };

      c(NoiseChunk var0, ChunkCoordIntPair var1, NoiseRouter var2, PositionalRandomFactory var3, int var4, int var5, Aquifer.a var6) {
         this.l = var0;
         this.m = var2.a();
         this.n = var2.b();
         this.o = var2.c();
         this.p = var2.d();
         this.u = var2.h();
         this.v = var2.i();
         this.q = var3;
         this.x = this.a(var1.d()) - 1;
         this.t = var6;
         int var7 = this.a(var1.f()) + 1;
         this.A = var7 - this.x + 1;
         this.y = this.b(var4) - 1;
         int var8 = this.b(var4 + var5) + 1;
         int var9 = var8 - this.y + 1;
         this.z = this.c(var1.e()) - 1;
         int var10 = this.c(var1.g()) + 1;
         this.B = var10 - this.z + 1;
         int var11 = this.A * var9 * this.B;
         this.r = new Aquifer.b[var11];
         this.s = new long[var11];
         Arrays.fill(this.s, Long.MAX_VALUE);
      }

      private int a(int var0, int var1, int var2) {
         int var3 = var0 - this.x;
         int var4 = var1 - this.y;
         int var5 = var2 - this.z;
         return (var4 * this.B + var5) * this.A + var3;
      }

      @Nullable
      @Override
      public IBlockData a(DensityFunction.b var0, double var1) {
         int var3 = var0.a();
         int var4 = var0.b();
         int var5 = var0.c();
         if (var1 > 0.0) {
            this.w = false;
            return null;
         } else {
            Aquifer.b var6 = this.t.computeFluid(var3, var4, var5);
            if (var6.a(var4).a(Blocks.H)) {
               this.w = false;
               return Blocks.H.o();
            } else {
               int var7 = Math.floorDiv(var3 - 5, 16);
               int var8 = Math.floorDiv(var4 + 1, 12);
               int var9 = Math.floorDiv(var5 - 5, 16);
               int var10 = Integer.MAX_VALUE;
               int var11 = Integer.MAX_VALUE;
               int var12 = Integer.MAX_VALUE;
               long var13 = 0L;
               long var15 = 0L;
               long var17 = 0L;

               for(int var19 = 0; var19 <= 1; ++var19) {
                  for(int var20 = -1; var20 <= 1; ++var20) {
                     for(int var21 = 0; var21 <= 1; ++var21) {
                        int var22 = var7 + var19;
                        int var23 = var8 + var20;
                        int var24 = var9 + var21;
                        int var25 = this.a(var22, var23, var24);
                        long var28 = this.s[var25];
                        long var26;
                        if (var28 != Long.MAX_VALUE) {
                           var26 = var28;
                        } else {
                           RandomSource var30 = this.q.a(var22, var23, var24);
                           var26 = BlockPosition.a(var22 * 16 + var30.a(10), var23 * 12 + var30.a(9), var24 * 16 + var30.a(10));
                           this.s[var25] = var26;
                        }

                        int var30 = BlockPosition.a(var26) - var3;
                        int var31 = BlockPosition.b(var26) - var4;
                        int var32 = BlockPosition.c(var26) - var5;
                        int var33 = var30 * var30 + var31 * var31 + var32 * var32;
                        if (var10 >= var33) {
                           var17 = var15;
                           var15 = var13;
                           var13 = var26;
                           var12 = var11;
                           var11 = var10;
                           var10 = var33;
                        } else if (var11 >= var33) {
                           var17 = var15;
                           var15 = var26;
                           var12 = var11;
                           var11 = var33;
                        } else if (var12 >= var33) {
                           var17 = var26;
                           var12 = var33;
                        }
                     }
                  }
               }

               Aquifer.b var19 = this.a(var13);
               double var20 = a(var10, var11);
               IBlockData var22 = var19.a(var4);
               if (var20 <= 0.0) {
                  this.w = var20 >= k;
                  return var22;
               } else if (var22.a(Blocks.G) && this.t.computeFluid(var3, var4 - 1, var5).a(var4 - 1).a(Blocks.H)) {
                  this.w = true;
                  return var22;
               } else {
                  MutableDouble var24 = new MutableDouble(Double.NaN);
                  Aquifer.b var25 = this.a(var15);
                  double var26 = var20 * this.a(var0, var24, var19, var25);
                  if (var1 + var26 > 0.0) {
                     this.w = false;
                     return null;
                  } else {
                     Aquifer.b var28 = this.a(var17);
                     double var29 = a(var10, var12);
                     if (var29 > 0.0) {
                        double var31 = var20 * var29 * this.a(var0, var24, var19, var28);
                        if (var1 + var31 > 0.0) {
                           this.w = false;
                           return null;
                        }
                     }

                     double var31 = a(var11, var12);
                     if (var31 > 0.0) {
                        double var33 = var20 * var31 * this.a(var0, var24, var25, var28);
                        if (var1 + var33 > 0.0) {
                           this.w = false;
                           return null;
                        }
                     }

                     this.w = true;
                     return var22;
                  }
               }
            }
         }
      }

      @Override
      public boolean a() {
         return this.w;
      }

      private static double a(int var0, int var1) {
         double var2 = 25.0;
         return 1.0 - (double)Math.abs(var1 - var0) / 25.0;
      }

      private double a(DensityFunction.b var0, MutableDouble var1, Aquifer.b var2, Aquifer.b var3) {
         int var4 = var0.b();
         IBlockData var5 = var2.a(var4);
         IBlockData var6 = var3.a(var4);
         if ((!var5.a(Blocks.H) || !var6.a(Blocks.G)) && (!var5.a(Blocks.G) || !var6.a(Blocks.H))) {
            int var7 = Math.abs(var2.a - var3.a);
            if (var7 == 0) {
               return 0.0;
            } else {
               double var8 = 0.5 * (double)(var2.a + var3.a);
               double var10 = (double)var4 + 0.5 - var8;
               double var12 = (double)var7 / 2.0;
               double var14 = 0.0;
               double var16 = 2.5;
               double var18 = 1.5;
               double var20 = 3.0;
               double var22 = 10.0;
               double var24 = 3.0;
               double var26 = var12 - Math.abs(var10);
               double var28;
               if (var10 > 0.0) {
                  double var30 = 0.0 + var26;
                  if (var30 > 0.0) {
                     var28 = var30 / 1.5;
                  } else {
                     var28 = var30 / 2.5;
                  }
               } else {
                  double var30 = 3.0 + var26;
                  if (var30 > 0.0) {
                     var28 = var30 / 3.0;
                  } else {
                     var28 = var30 / 10.0;
                  }
               }

               double var30 = 2.0;
               double var32;
               if (!(var28 < -2.0) && !(var28 > 2.0)) {
                  double var34 = var1.getValue();
                  if (Double.isNaN(var34)) {
                     double var36 = this.m.a(var0);
                     var1.setValue(var36);
                     var32 = var36;
                  } else {
                     var32 = var34;
                  }
               } else {
                  var32 = 0.0;
               }

               return 2.0 * (var32 + var28);
            }
         } else {
            return 2.0;
         }
      }

      private int a(int var0) {
         return Math.floorDiv(var0, 16);
      }

      private int b(int var0) {
         return Math.floorDiv(var0, 12);
      }

      private int c(int var0) {
         return Math.floorDiv(var0, 16);
      }

      private Aquifer.b a(long var0) {
         int var2 = BlockPosition.a(var0);
         int var3 = BlockPosition.b(var0);
         int var4 = BlockPosition.c(var0);
         int var5 = this.a(var2);
         int var6 = this.b(var3);
         int var7 = this.c(var4);
         int var8 = this.a(var5, var6, var7);
         Aquifer.b var9 = this.r[var8];
         if (var9 != null) {
            return var9;
         } else {
            Aquifer.b var10 = this.b(var2, var3, var4);
            this.r[var8] = var10;
            return var10;
         }
      }

      private Aquifer.b b(int var0, int var1, int var2) {
         Aquifer.b var3 = this.t.computeFluid(var0, var1, var2);
         int var4 = Integer.MAX_VALUE;
         int var5 = var1 + 12;
         int var6 = var1 - 12;
         boolean var7 = false;

         for(int[] var11 : C) {
            int var12 = var0 + SectionPosition.c(var11[0]);
            int var13 = var2 + SectionPosition.c(var11[1]);
            int var14 = this.l.a(var12, var13);
            int var15 = var14 + 8;
            boolean var16 = var11[0] == 0 && var11[1] == 0;
            if (var16 && var6 > var15) {
               return var3;
            }

            boolean var17 = var5 > var15;
            if (var17 || var16) {
               Aquifer.b var18 = this.t.computeFluid(var12, var15, var13);
               if (!var18.a(var15).h()) {
                  if (var16) {
                     var7 = true;
                  }

                  if (var17) {
                     return var18;
                  }
               }
            }

            var4 = Math.min(var4, var14);
         }

         int var8 = this.a(var0, var1, var2, var3, var4, var7);
         return new Aquifer.b(var8, this.a(var0, var1, var2, var3, var8));
      }

      private int a(int var0, int var1, int var2, Aquifer.b var3, int var4, boolean var5) {
         DensityFunction.e var6 = new DensityFunction.e(var0, var1, var2);
         double var7;
         double var9;
         if (OverworldBiomeBuilder.a(this.u, this.v, var6)) {
            var7 = -1.0;
            var9 = -1.0;
         } else {
            int var11 = var4 + 8 - var1;
            int var12 = 64;
            double var13 = var5 ? MathHelper.a((double)var11, 0.0, 64.0, 1.0, 0.0) : 0.0;
            double var15 = MathHelper.a(this.n.a(var6), -1.0, 1.0);
            double var17 = MathHelper.b(var13, 1.0, 0.0, -0.3, 0.8);
            double var19 = MathHelper.b(var13, 1.0, 0.0, -0.8, 0.4);
            var7 = var15 - var19;
            var9 = var15 - var17;
         }

         int var11;
         if (var9 > 0.0) {
            var11 = var3.a;
         } else if (var7 > 0.0) {
            var11 = this.a(var0, var1, var2, var4);
         } else {
            var11 = DimensionManager.g;
         }

         return var11;
      }

      private int a(int var0, int var1, int var2, int var3) {
         int var4 = 16;
         int var5 = 40;
         int var6 = Math.floorDiv(var0, 16);
         int var7 = Math.floorDiv(var1, 40);
         int var8 = Math.floorDiv(var2, 16);
         int var9 = var7 * 40 + 20;
         int var10 = 10;
         double var11 = this.o.a(new DensityFunction.e(var6, var7, var8)) * 10.0;
         int var13 = MathHelper.a(var11, 3);
         int var14 = var9 + var13;
         return Math.min(var3, var14);
      }

      private IBlockData a(int var0, int var1, int var2, Aquifer.b var3, int var4) {
         IBlockData var5 = var3.b;
         if (var4 <= -10 && var4 != DimensionManager.g && var3.b != Blocks.H.o()) {
            int var6 = 64;
            int var7 = 40;
            int var8 = Math.floorDiv(var0, 64);
            int var9 = Math.floorDiv(var1, 40);
            int var10 = Math.floorDiv(var2, 64);
            double var11 = this.p.a(new DensityFunction.e(var8, var9, var10));
            if (Math.abs(var11) > 0.3) {
               var5 = Blocks.H.o();
            }
         }

         return var5;
      }
   }
}
