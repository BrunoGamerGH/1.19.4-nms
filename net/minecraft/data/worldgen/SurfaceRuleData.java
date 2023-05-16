package net.minecraft.data.worldgen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class SurfaceRuleData {
   private static final SurfaceRules.o a = a(Blocks.a);
   private static final SurfaceRules.o b = a(Blocks.F);
   private static final SurfaceRules.o c = a(Blocks.hi);
   private static final SurfaceRules.o d = a(Blocks.hj);
   private static final SurfaceRules.o e = a(Blocks.iz);
   private static final SurfaceRules.o f = a(Blocks.K);
   private static final SurfaceRules.o g = a(Blocks.jo);
   private static final SurfaceRules.o h = a(Blocks.b);
   private static final SurfaceRules.o i = a(Blocks.rD);
   private static final SurfaceRules.o j = a(Blocks.j);
   private static final SurfaceRules.o k = a(Blocks.l);
   private static final SurfaceRules.o l = a(Blocks.k);
   private static final SurfaceRules.o m = a(Blocks.fk);
   private static final SurfaceRules.o n = a(Blocks.i);
   private static final SurfaceRules.o o = a(Blocks.qw);
   private static final SurfaceRules.o p = a(Blocks.L);
   private static final SurfaceRules.o q = a(Blocks.I);
   private static final SurfaceRules.o r = a(Blocks.aU);
   private static final SurfaceRules.o s = a(Blocks.iB);
   private static final SurfaceRules.o t = a(Blocks.dO);
   private static final SurfaceRules.o u = a(Blocks.rC);
   private static final SurfaceRules.o v = a(Blocks.qy);
   private static final SurfaceRules.o w = a(Blocks.dN);
   private static final SurfaceRules.o x = a(Blocks.G);
   private static final SurfaceRules.o y = a(Blocks.H);
   private static final SurfaceRules.o z = a(Blocks.dV);
   private static final SurfaceRules.o A = a(Blocks.dW);
   private static final SurfaceRules.o B = a(Blocks.dX);
   private static final SurfaceRules.o C = a(Blocks.dY);
   private static final SurfaceRules.o D = a(Blocks.pn);
   private static final SurfaceRules.o E = a(Blocks.ol);
   private static final SurfaceRules.o F = a(Blocks.oj);
   private static final SurfaceRules.o G = a(Blocks.kH);
   private static final SurfaceRules.o H = a(Blocks.os);
   private static final SurfaceRules.o I = a(Blocks.fy);

   private static SurfaceRules.o a(Block var0) {
      return SurfaceRules.a(var0.o());
   }

   public static SurfaceRules.o a() {
      return a(true, false, true);
   }

   public static SurfaceRules.o a(boolean var0, boolean var1, boolean var2) {
      SurfaceRules.f var3 = SurfaceRules.a(VerticalAnchor.a(97), 2);
      SurfaceRules.f var4 = SurfaceRules.a(VerticalAnchor.a(256), 0);
      SurfaceRules.f var5 = SurfaceRules.b(VerticalAnchor.a(63), -1);
      SurfaceRules.f var6 = SurfaceRules.b(VerticalAnchor.a(74), 1);
      SurfaceRules.f var7 = SurfaceRules.a(VerticalAnchor.a(60), 0);
      SurfaceRules.f var8 = SurfaceRules.a(VerticalAnchor.a(62), 0);
      SurfaceRules.f var9 = SurfaceRules.a(VerticalAnchor.a(63), 0);
      SurfaceRules.f var10 = SurfaceRules.a(-1, 0);
      SurfaceRules.f var11 = SurfaceRules.a(0, 0);
      SurfaceRules.f var12 = SurfaceRules.b(-6, -1);
      SurfaceRules.f var13 = SurfaceRules.b();
      SurfaceRules.f var14 = SurfaceRules.a(Biomes.W, Biomes.X);
      SurfaceRules.f var15 = SurfaceRules.a();
      SurfaceRules.o var16 = SurfaceRules.a(SurfaceRules.a(var11, n), j);
      SurfaceRules.o var17 = SurfaceRules.a(SurfaceRules.a(SurfaceRules.e, r), q);
      SurfaceRules.o var18 = SurfaceRules.a(SurfaceRules.a(SurfaceRules.e, h), p);
      SurfaceRules.f var19 = SurfaceRules.a(Biomes.P, Biomes.M, Biomes.N);
      SurfaceRules.f var20 = SurfaceRules.a(Biomes.f);
      SurfaceRules.o var21 = SurfaceRules.a(
         SurfaceRules.a(SurfaceRules.a(Biomes.J), SurfaceRules.a(SurfaceRules.a(SurfaceRules.a(Noises.X, -0.0125, 0.0125), o), h)),
         SurfaceRules.a(SurfaceRules.a(Biomes.O), SurfaceRules.a(SurfaceRules.a(SurfaceRules.a(Noises.Y, -0.05, 0.05), var18), h)),
         SurfaceRules.a(SurfaceRules.a(Biomes.t), SurfaceRules.a(a(1.0), h)),
         SurfaceRules.a(var19, var17),
         SurfaceRules.a(var20, var17),
         SurfaceRules.a(SurfaceRules.a(Biomes.Z), h)
      );
      SurfaceRules.o var22 = SurfaceRules.a(SurfaceRules.a(Noises.Z, 0.45, 0.58), SurfaceRules.a(var11, v));
      SurfaceRules.o var23 = SurfaceRules.a(SurfaceRules.a(Noises.Z, 0.35, 0.6), SurfaceRules.a(var11, v));
      SurfaceRules.o var24 = SurfaceRules.a(
         SurfaceRules.a(
            SurfaceRules.a(Biomes.H),
            SurfaceRules.a(
               SurfaceRules.a(var15, s),
               SurfaceRules.a(SurfaceRules.a(Noises.aa, -0.5, 0.2), s),
               SurfaceRules.a(SurfaceRules.a(Noises.ab, -0.0625, 0.025), w),
               SurfaceRules.a(var11, t)
            )
         ),
         SurfaceRules.a(SurfaceRules.a(Biomes.G), SurfaceRules.a(SurfaceRules.a(var15, h), var22, SurfaceRules.a(var11, t))),
         SurfaceRules.a(SurfaceRules.a(Biomes.I), h),
         SurfaceRules.a(SurfaceRules.a(Biomes.F), SurfaceRules.a(var22, j)),
         var21,
         SurfaceRules.a(SurfaceRules.a(Biomes.w), SurfaceRules.a(a(1.75), h)),
         SurfaceRules.a(SurfaceRules.a(Biomes.u), SurfaceRules.a(SurfaceRules.a(a(2.0), var18), SurfaceRules.a(a(1.0), h), SurfaceRules.a(a(-1.0), j), var18)),
         SurfaceRules.a(SurfaceRules.a(Biomes.h), u),
         j
      );
      SurfaceRules.o var25 = SurfaceRules.a(
         SurfaceRules.a(
            SurfaceRules.a(Biomes.H),
            SurfaceRules.a(
               SurfaceRules.a(var15, s),
               SurfaceRules.a(SurfaceRules.a(Noises.aa, 0.0, 0.2), s),
               SurfaceRules.a(SurfaceRules.a(Noises.ab, 0.0, 0.025), w),
               SurfaceRules.a(var11, t)
            )
         ),
         SurfaceRules.a(SurfaceRules.a(Biomes.G), SurfaceRules.a(SurfaceRules.a(var15, h), var23, SurfaceRules.a(var11, t))),
         SurfaceRules.a(SurfaceRules.a(Biomes.I), SurfaceRules.a(SurfaceRules.a(var15, h), SurfaceRules.a(var11, t))),
         SurfaceRules.a(SurfaceRules.a(Biomes.F), SurfaceRules.a(var23, SurfaceRules.a(var11, t))),
         var21,
         SurfaceRules.a(SurfaceRules.a(Biomes.w), SurfaceRules.a(SurfaceRules.a(a(1.75), h), SurfaceRules.a(a(-0.5), l))),
         SurfaceRules.a(
            SurfaceRules.a(Biomes.u), SurfaceRules.a(SurfaceRules.a(a(2.0), var18), SurfaceRules.a(a(1.0), h), SurfaceRules.a(a(-1.0), var16), var18)
         ),
         SurfaceRules.a(SurfaceRules.a(Biomes.n, Biomes.o), SurfaceRules.a(SurfaceRules.a(a(1.75), l), SurfaceRules.a(a(-0.95), k))),
         SurfaceRules.a(SurfaceRules.a(Biomes.e), SurfaceRules.a(var11, t)),
         SurfaceRules.a(SurfaceRules.a(Biomes.h), u),
         SurfaceRules.a(SurfaceRules.a(Biomes.Y), m),
         var16
      );
      SurfaceRules.f var26 = SurfaceRules.a(Noises.N, -0.909, -0.5454);
      SurfaceRules.f var27 = SurfaceRules.a(Noises.N, -0.1818, 0.1818);
      SurfaceRules.f var28 = SurfaceRules.a(Noises.N, 0.5454, 0.909);
      SurfaceRules.o var29 = SurfaceRules.a(
         SurfaceRules.a(
            SurfaceRules.a,
            SurfaceRules.a(
               SurfaceRules.a(
                  SurfaceRules.a(Biomes.C),
                  SurfaceRules.a(var3, SurfaceRules.a(SurfaceRules.a(var26, l), SurfaceRules.a(var27, l), SurfaceRules.a(var28, l), var16))
               ),
               SurfaceRules.a(
                  SurfaceRules.a(Biomes.g), SurfaceRules.a(var8, SurfaceRules.a(SurfaceRules.a(var9), SurfaceRules.a(SurfaceRules.a(Noises.W, 0.0), x)))
               ),
               SurfaceRules.a(
                  SurfaceRules.a(Biomes.h), SurfaceRules.a(var7, SurfaceRules.a(SurfaceRules.a(var9), SurfaceRules.a(SurfaceRules.a(Noises.W, 0.0), x)))
               )
            )
         ),
         SurfaceRules.a(
            SurfaceRules.a(Biomes.A, Biomes.B, Biomes.C),
            SurfaceRules.a(
               SurfaceRules.a(
                  SurfaceRules.a,
                  SurfaceRules.a(
                     SurfaceRules.a(var4, d),
                     SurfaceRules.a(var6, SurfaceRules.a(SurfaceRules.a(var26, e), SurfaceRules.a(var27, e), SurfaceRules.a(var28, e), SurfaceRules.e())),
                     SurfaceRules.a(var10, SurfaceRules.a(SurfaceRules.a(SurfaceRules.e, g), f)),
                     SurfaceRules.a(SurfaceRules.a(var13), d),
                     SurfaceRules.a(var12, c),
                     var18
                  )
               ),
               SurfaceRules.a(var5, SurfaceRules.a(SurfaceRules.a(var9, SurfaceRules.a(SurfaceRules.a(var6), d)), SurfaceRules.e())),
               SurfaceRules.a(SurfaceRules.b, SurfaceRules.a(var12, c))
            )
         ),
         SurfaceRules.a(
            SurfaceRules.a,
            SurfaceRules.a(
               var10,
               SurfaceRules.a(
                  SurfaceRules.a(var14, SurfaceRules.a(var13, SurfaceRules.a(SurfaceRules.a(var11, a), SurfaceRules.a(SurfaceRules.d(), w), x))), var25
               )
            )
         ),
         SurfaceRules.a(
            var12,
            SurfaceRules.a(
               SurfaceRules.a(SurfaceRules.a, SurfaceRules.a(var14, SurfaceRules.a(var13, x))),
               SurfaceRules.a(SurfaceRules.b, var24),
               SurfaceRules.a(var19, SurfaceRules.a(SurfaceRules.c, r)),
               SurfaceRules.a(var20, SurfaceRules.a(SurfaceRules.d, r))
            )
         ),
         SurfaceRules.a(
            SurfaceRules.a,
            SurfaceRules.a(SurfaceRules.a(SurfaceRules.a(Biomes.H, Biomes.I), h), SurfaceRules.a(SurfaceRules.a(Biomes.P, Biomes.Q, Biomes.R), var17), var18)
         )
      );
      Builder<SurfaceRules.o> var30 = ImmutableList.builder();
      if (var1) {
         var30.add(SurfaceRules.a(SurfaceRules.a(SurfaceRules.a("bedrock_roof", VerticalAnchor.c(5), VerticalAnchor.b())), b));
      }

      if (var2) {
         var30.add(SurfaceRules.a(SurfaceRules.a("bedrock_floor", VerticalAnchor.a(), VerticalAnchor.b(5)), b));
      }

      SurfaceRules.o var31 = SurfaceRules.a(SurfaceRules.c(), var29);
      var30.add(var0 ? var31 : var29);
      var30.add(SurfaceRules.a(SurfaceRules.a("deepslate", VerticalAnchor.a(0), VerticalAnchor.a(8)), i));
      return SurfaceRules.a((SurfaceRules.o[])var30.build().toArray(var0x -> new SurfaceRules.o[var0x]));
   }

   public static SurfaceRules.o b() {
      SurfaceRules.f var0 = SurfaceRules.a(VerticalAnchor.a(31), 0);
      SurfaceRules.f var1 = SurfaceRules.a(VerticalAnchor.a(32), 0);
      SurfaceRules.f var2 = SurfaceRules.b(VerticalAnchor.a(30), 0);
      SurfaceRules.f var3 = SurfaceRules.a(SurfaceRules.b(VerticalAnchor.a(35), 0));
      SurfaceRules.f var4 = SurfaceRules.a(VerticalAnchor.c(5), 0);
      SurfaceRules.f var5 = SurfaceRules.b();
      SurfaceRules.f var6 = SurfaceRules.a(Noises.ac, -0.012);
      SurfaceRules.f var7 = SurfaceRules.a(Noises.ad, -0.012);
      SurfaceRules.f var8 = SurfaceRules.a(Noises.ae, -0.012);
      SurfaceRules.f var9 = SurfaceRules.a(Noises.af, 0.54);
      SurfaceRules.f var10 = SurfaceRules.a(Noises.ag, 1.17);
      SurfaceRules.f var11 = SurfaceRules.a(Noises.ah, 0.0);
      SurfaceRules.o var12 = SurfaceRules.a(var8, SurfaceRules.a(var2, SurfaceRules.a(var3, p)));
      return SurfaceRules.a(
         SurfaceRules.a(SurfaceRules.a("bedrock_floor", VerticalAnchor.a(), VerticalAnchor.b(5)), b),
         SurfaceRules.a(SurfaceRules.a(SurfaceRules.a("bedrock_roof", VerticalAnchor.c(5), VerticalAnchor.b())), b),
         SurfaceRules.a(var4, z),
         SurfaceRules.a(
            SurfaceRules.a(Biomes.ag),
            SurfaceRules.a(SurfaceRules.a(SurfaceRules.f, C), SurfaceRules.a(SurfaceRules.b, SurfaceRules.a(var12, SurfaceRules.a(var11, C), D)))
         ),
         SurfaceRules.a(
            SurfaceRules.a(Biomes.af),
            SurfaceRules.a(
               SurfaceRules.a(SurfaceRules.f, SurfaceRules.a(SurfaceRules.a(var11, A), B)),
               SurfaceRules.a(SurfaceRules.b, SurfaceRules.a(var12, SurfaceRules.a(var11, A), B))
            )
         ),
         SurfaceRules.a(
            SurfaceRules.a,
            SurfaceRules.a(
               SurfaceRules.a(SurfaceRules.a(var1), SurfaceRules.a(var5, y)),
               SurfaceRules.a(
                  SurfaceRules.a(Biomes.ad), SurfaceRules.a(SurfaceRules.a(var9), SurfaceRules.a(var0, SurfaceRules.a(SurfaceRules.a(var10, E), F)))
               ),
               SurfaceRules.a(
                  SurfaceRules.a(Biomes.ae), SurfaceRules.a(SurfaceRules.a(var9), SurfaceRules.a(var0, SurfaceRules.a(SurfaceRules.a(var10, G), H)))
               )
            )
         ),
         SurfaceRules.a(
            SurfaceRules.a(Biomes.ac),
            SurfaceRules.a(
               SurfaceRules.a(
                  SurfaceRules.b, SurfaceRules.a(var6, SurfaceRules.a(SurfaceRules.a(SurfaceRules.a(var5), SurfaceRules.a(var2, SurfaceRules.a(var3, A))), z))
               ),
               SurfaceRules.a(
                  SurfaceRules.a,
                  SurfaceRules.a(
                     var0, SurfaceRules.a(var3, SurfaceRules.a(var7, SurfaceRules.a(SurfaceRules.a(var1, p), SurfaceRules.a(SurfaceRules.a(var5), p))))
                  )
               )
            )
         ),
         z
      );
   }

   public static SurfaceRules.o c() {
      return I;
   }

   public static SurfaceRules.o d() {
      return a;
   }

   private static SurfaceRules.f a(double var0) {
      return SurfaceRules.a(Noises.N, var0 / 8.25, Double.MAX_VALUE);
   }
}
