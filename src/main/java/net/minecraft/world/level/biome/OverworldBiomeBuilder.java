package net.minecraft.world.level.biome;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.SharedConstants;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.data.worldgen.TerrainProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.CubicSpline;
import net.minecraft.util.ToFloatFunction;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.NoiseRouterData;

public final class OverworldBiomeBuilder {
   private static final float h = 0.05F;
   private static final float i = 0.26666668F;
   public static final float a = 0.4F;
   private static final float j = 0.93333334F;
   private static final float k = 0.1F;
   public static final float b = 0.56666666F;
   private static final float l = 0.7666667F;
   public static final float c = -0.11F;
   public static final float d = 0.03F;
   public static final float e = 0.3F;
   public static final float f = -0.78F;
   public static final float g = -0.375F;
   private static final float m = -0.225F;
   private static final float n = 0.9F;
   private final OverworldBiomeBuilder.a o;
   private final Climate.b p = Climate.b.a(-1.0F, 1.0F);
   private final Climate.b[] q = new Climate.b[]{
      Climate.b.a(-1.0F, -0.45F), Climate.b.a(-0.45F, -0.15F), Climate.b.a(-0.15F, 0.2F), Climate.b.a(0.2F, 0.55F), Climate.b.a(0.55F, 1.0F)
   };
   private final Climate.b[] r = new Climate.b[]{
      Climate.b.a(-1.0F, -0.35F), Climate.b.a(-0.35F, -0.1F), Climate.b.a(-0.1F, 0.1F), Climate.b.a(0.1F, 0.3F), Climate.b.a(0.3F, 1.0F)
   };
   private final Climate.b[] s = new Climate.b[]{
      Climate.b.a(-1.0F, -0.78F),
      Climate.b.a(-0.78F, -0.375F),
      Climate.b.a(-0.375F, -0.2225F),
      Climate.b.a(-0.2225F, 0.05F),
      Climate.b.a(0.05F, 0.45F),
      Climate.b.a(0.45F, 0.55F),
      Climate.b.a(0.55F, 1.0F)
   };
   private final Climate.b t = this.q[0];
   private final Climate.b u = Climate.b.a(this.q[1], this.q[4]);
   private final Climate.b v = Climate.b.a(-1.2F, -1.05F);
   private final Climate.b w = Climate.b.a(-1.05F, -0.455F);
   private final Climate.b x = Climate.b.a(-0.455F, -0.19F);
   private final Climate.b y = Climate.b.a(-0.19F, -0.11F);
   private final Climate.b z = Climate.b.a(-0.11F, 0.55F);
   private final Climate.b A = Climate.b.a(-0.11F, 0.03F);
   private final Climate.b B = Climate.b.a(0.03F, 0.3F);
   private final Climate.b C = Climate.b.a(0.3F, 1.0F);
   private final ResourceKey<BiomeBase>[][] D = new ResourceKey[][]{
      {Biomes.X, Biomes.V, Biomes.T, Biomes.R, Biomes.P}, {Biomes.W, Biomes.U, Biomes.S, Biomes.Q, Biomes.P}
   };
   private final ResourceKey<BiomeBase>[][] E = new ResourceKey[][]{
      {Biomes.d, Biomes.d, Biomes.d, Biomes.q, Biomes.p},
      {Biomes.b, Biomes.b, Biomes.i, Biomes.p, Biomes.o},
      {Biomes.j, Biomes.b, Biomes.i, Biomes.k, Biomes.l},
      {Biomes.r, Biomes.r, Biomes.i, Biomes.x, Biomes.x},
      {Biomes.f, Biomes.f, Biomes.f, Biomes.f, Biomes.f}
   };
   private final ResourceKey<BiomeBase>[][] F = new ResourceKey[][]{
      {Biomes.e, null, Biomes.q, null, null},
      {null, null, null, null, Biomes.n},
      {Biomes.c, null, null, Biomes.m, null},
      {null, null, Biomes.b, Biomes.y, Biomes.z},
      {null, null, null, null, null}
   };
   private final ResourceKey<BiomeBase>[][] G = new ResourceKey[][]{
      {Biomes.d, Biomes.d, Biomes.d, Biomes.q, Biomes.q},
      {Biomes.D, Biomes.D, Biomes.i, Biomes.p, Biomes.o},
      {Biomes.D, Biomes.D, Biomes.D, Biomes.D, Biomes.l},
      {Biomes.s, Biomes.s, Biomes.i, Biomes.i, Biomes.x},
      {Biomes.A, Biomes.A, Biomes.A, Biomes.C, Biomes.C}
   };
   private final ResourceKey<BiomeBase>[][] H = new ResourceKey[][]{
      {Biomes.e, null, null, null, null},
      {Biomes.E, null, Biomes.D, Biomes.D, Biomes.n},
      {Biomes.E, Biomes.E, Biomes.i, Biomes.k, null},
      {null, null, null, null, null},
      {Biomes.B, Biomes.B, null, null, null}
   };
   private final ResourceKey<BiomeBase>[][] I = new ResourceKey[][]{
      {Biomes.u, Biomes.u, Biomes.t, Biomes.v, Biomes.v},
      {Biomes.u, Biomes.u, Biomes.t, Biomes.v, Biomes.v},
      {Biomes.t, Biomes.t, Biomes.t, Biomes.v, Biomes.v},
      {null, null, null, null, null},
      {null, null, null, null, null}
   };

   public OverworldBiomeBuilder() {
      this(OverworldBiomeBuilder.a.a);
   }

   public OverworldBiomeBuilder(OverworldBiomeBuilder.a var0) {
      this.o = var0;
   }

   public List<Climate.d> a() {
      Climate.b var0 = Climate.b.a(0.0F);
      float var1 = 0.16F;
      return List.of(
         new Climate.d(this.p, this.p, Climate.b.a(this.z, this.p), this.p, var0, Climate.b.a(-1.0F, -0.16F), 0L),
         new Climate.d(this.p, this.p, Climate.b.a(this.z, this.p), this.p, var0, Climate.b.a(0.16F, 1.0F), 0L)
      );
   }

   protected void a(Consumer<Pair<Climate.d, ResourceKey<BiomeBase>>> var0) {
      if (SharedConstants.an) {
         this.b(var0);
      } else {
         this.c(var0);
         this.d(var0);
         this.e(var0);
      }
   }

   private void b(Consumer<Pair<Climate.d, ResourceKey<BiomeBase>>> var0) {
      HolderLookup.b var1 = VanillaRegistries.a();
      HolderGetter<DensityFunction> var2 = var1.b(Registries.ar);
      DensityFunctions.w.a var3 = new DensityFunctions.w.a(var2.b(NoiseRouterData.d));
      DensityFunctions.w.a var4 = new DensityFunctions.w.a(var2.b(NoiseRouterData.e));
      DensityFunctions.w.a var5 = new DensityFunctions.w.a(var2.b(NoiseRouterData.g));
      var0.accept(Pair.of(Climate.a(this.p, this.p, this.p, this.p, Climate.b.a(0.0F), this.p, 0.01F), Biomes.b));
      CubicSpline<?, ?> var6 = TerrainProvider.a(var4, var5, -0.15F, 0.0F, 0.0F, 0.1F, 0.0F, -0.03F, false, false, ToFloatFunction.a);
      if (var6 instanceof CubicSpline.e var7) {
         ResourceKey<BiomeBase> var8 = Biomes.f;

         for(float var12 : var7.e()) {
            var0.accept(Pair.of(Climate.a(this.p, this.p, this.p, Climate.b.a(var12), Climate.b.a(0.0F), this.p, 0.0F), var8));
            var8 = var8 == Biomes.f ? Biomes.A : Biomes.f;
         }
      }

      CubicSpline<?, ?> var7 = TerrainProvider.a(var3, var4, var5, false);
      if (var7 instanceof CubicSpline.e var8) {
         for(float var12 : var8.e()) {
            var0.accept(Pair.of(Climate.a(this.p, this.p, Climate.b.a(var12), this.p, Climate.b.a(0.0F), this.p, 0.0F), Biomes.q));
         }
      }
   }

   private void c(Consumer<Pair<Climate.d, ResourceKey<BiomeBase>>> var0) {
      this.a(var0, this.p, this.p, this.v, this.p, this.p, 0.0F, Biomes.Y);

      for(int var1 = 0; var1 < this.q.length; ++var1) {
         Climate.b var2 = this.q[var1];
         this.a(var0, var2, this.p, this.w, this.p, this.p, 0.0F, this.D[0][var1]);
         this.a(var0, var2, this.p, this.x, this.p, this.p, 0.0F, this.D[1][var1]);
      }
   }

   private void d(Consumer<Pair<Climate.d, ResourceKey<BiomeBase>>> var0) {
      this.c(var0, Climate.b.a(-1.0F, -0.93333334F));
      this.b(var0, Climate.b.a(-0.93333334F, -0.7666667F));
      this.a(var0, Climate.b.a(-0.7666667F, -0.56666666F));
      this.b(var0, Climate.b.a(-0.56666666F, -0.4F));
      this.c(var0, Climate.b.a(-0.4F, -0.26666668F));
      this.d(var0, Climate.b.a(-0.26666668F, -0.05F));
      this.e(var0, Climate.b.a(-0.05F, 0.05F));
      this.d(var0, Climate.b.a(0.05F, 0.26666668F));
      this.c(var0, Climate.b.a(0.26666668F, 0.4F));
      this.b(var0, Climate.b.a(0.4F, 0.56666666F));
      this.a(var0, Climate.b.a(0.56666666F, 0.7666667F));
      this.b(var0, Climate.b.a(0.7666667F, 0.93333334F));
      this.c(var0, Climate.b.a(0.93333334F, 1.0F));
   }

   private void a(Consumer<Pair<Climate.d, ResourceKey<BiomeBase>>> var0, Climate.b var1) {
      for(int var2 = 0; var2 < this.q.length; ++var2) {
         Climate.b var3 = this.q[var2];

         for(int var4 = 0; var4 < this.r.length; ++var4) {
            Climate.b var5 = this.r[var4];
            ResourceKey<BiomeBase> var6 = this.a(var2, var4, var1);
            ResourceKey<BiomeBase> var7 = this.b(var2, var4, var1);
            ResourceKey<BiomeBase> var8 = this.c(var2, var4, var1);
            ResourceKey<BiomeBase> var9 = this.e(var2, var4, var1);
            ResourceKey<BiomeBase> var10 = this.h(var2, var4, var1);
            ResourceKey<BiomeBase> var11 = this.a(var2, var4, var1, var10);
            ResourceKey<BiomeBase> var12 = this.f(var2, var4, var1);
            this.a(var0, var3, var5, Climate.b.a(this.y, this.C), this.s[0], var1, 0.0F, var12);
            this.a(var0, var3, var5, Climate.b.a(this.y, this.A), this.s[1], var1, 0.0F, var8);
            this.a(var0, var3, var5, Climate.b.a(this.B, this.C), this.s[1], var1, 0.0F, var12);
            this.a(var0, var3, var5, Climate.b.a(this.y, this.A), Climate.b.a(this.s[2], this.s[3]), var1, 0.0F, var6);
            this.a(var0, var3, var5, Climate.b.a(this.B, this.C), this.s[2], var1, 0.0F, var9);
            this.a(var0, var3, var5, this.B, this.s[3], var1, 0.0F, var7);
            this.a(var0, var3, var5, this.C, this.s[3], var1, 0.0F, var9);
            this.a(var0, var3, var5, Climate.b.a(this.y, this.C), this.s[4], var1, 0.0F, var6);
            this.a(var0, var3, var5, Climate.b.a(this.y, this.A), this.s[5], var1, 0.0F, var11);
            this.a(var0, var3, var5, Climate.b.a(this.B, this.C), this.s[5], var1, 0.0F, var10);
            this.a(var0, var3, var5, Climate.b.a(this.y, this.C), this.s[6], var1, 0.0F, var6);
         }
      }
   }

   private void b(Consumer<Pair<Climate.d, ResourceKey<BiomeBase>>> var0, Climate.b var1) {
      for(int var2 = 0; var2 < this.q.length; ++var2) {
         Climate.b var3 = this.q[var2];

         for(int var4 = 0; var4 < this.r.length; ++var4) {
            Climate.b var5 = this.r[var4];
            ResourceKey<BiomeBase> var6 = this.a(var2, var4, var1);
            ResourceKey<BiomeBase> var7 = this.b(var2, var4, var1);
            ResourceKey<BiomeBase> var8 = this.c(var2, var4, var1);
            ResourceKey<BiomeBase> var9 = this.e(var2, var4, var1);
            ResourceKey<BiomeBase> var10 = this.h(var2, var4, var1);
            ResourceKey<BiomeBase> var11 = this.a(var2, var4, var1, var6);
            ResourceKey<BiomeBase> var12 = this.g(var2, var4, var1);
            ResourceKey<BiomeBase> var13 = this.f(var2, var4, var1);
            this.a(var0, var3, var5, this.y, Climate.b.a(this.s[0], this.s[1]), var1, 0.0F, var6);
            this.a(var0, var3, var5, this.A, this.s[0], var1, 0.0F, var12);
            this.a(var0, var3, var5, Climate.b.a(this.B, this.C), this.s[0], var1, 0.0F, var13);
            this.a(var0, var3, var5, this.A, this.s[1], var1, 0.0F, var8);
            this.a(var0, var3, var5, Climate.b.a(this.B, this.C), this.s[1], var1, 0.0F, var12);
            this.a(var0, var3, var5, Climate.b.a(this.y, this.A), Climate.b.a(this.s[2], this.s[3]), var1, 0.0F, var6);
            this.a(var0, var3, var5, Climate.b.a(this.B, this.C), this.s[2], var1, 0.0F, var9);
            this.a(var0, var3, var5, this.B, this.s[3], var1, 0.0F, var7);
            this.a(var0, var3, var5, this.C, this.s[3], var1, 0.0F, var9);
            this.a(var0, var3, var5, Climate.b.a(this.y, this.C), this.s[4], var1, 0.0F, var6);
            this.a(var0, var3, var5, Climate.b.a(this.y, this.A), this.s[5], var1, 0.0F, var11);
            this.a(var0, var3, var5, Climate.b.a(this.B, this.C), this.s[5], var1, 0.0F, var10);
            this.a(var0, var3, var5, Climate.b.a(this.y, this.C), this.s[6], var1, 0.0F, var6);
         }
      }
   }

   private void c(Consumer<Pair<Climate.d, ResourceKey<BiomeBase>>> var0, Climate.b var1) {
      this.a(var0, this.p, this.p, this.y, Climate.b.a(this.s[0], this.s[2]), var1, 0.0F, Biomes.O);
      this.a(var0, Climate.b.a(this.q[1], this.q[2]), this.p, Climate.b.a(this.A, this.C), this.s[6], var1, 0.0F, Biomes.g);
      this.a(var0, Climate.b.a(this.q[3], this.q[4]), this.p, Climate.b.a(this.A, this.C), this.s[6], var1, 0.0F, Biomes.h);

      for(int var2 = 0; var2 < this.q.length; ++var2) {
         Climate.b var3 = this.q[var2];

         for(int var4 = 0; var4 < this.r.length; ++var4) {
            Climate.b var5 = this.r[var4];
            ResourceKey<BiomeBase> var6 = this.a(var2, var4, var1);
            ResourceKey<BiomeBase> var7 = this.b(var2, var4, var1);
            ResourceKey<BiomeBase> var8 = this.c(var2, var4, var1);
            ResourceKey<BiomeBase> var9 = this.h(var2, var4, var1);
            ResourceKey<BiomeBase> var10 = this.e(var2, var4, var1);
            ResourceKey<BiomeBase> var11 = this.a(var2, var4);
            ResourceKey<BiomeBase> var12 = this.a(var2, var4, var1, var6);
            ResourceKey<BiomeBase> var13 = this.d(var2, var4, var1);
            ResourceKey<BiomeBase> var14 = this.g(var2, var4, var1);
            this.a(var0, var3, var5, Climate.b.a(this.A, this.C), this.s[0], var1, 0.0F, var14);
            this.a(var0, var3, var5, Climate.b.a(this.A, this.B), this.s[1], var1, 0.0F, var8);
            this.a(var0, var3, var5, this.C, this.s[1], var1, 0.0F, var2 == 0 ? var14 : var10);
            this.a(var0, var3, var5, this.A, this.s[2], var1, 0.0F, var6);
            this.a(var0, var3, var5, this.B, this.s[2], var1, 0.0F, var7);
            this.a(var0, var3, var5, this.C, this.s[2], var1, 0.0F, var10);
            this.a(var0, var3, var5, Climate.b.a(this.y, this.A), this.s[3], var1, 0.0F, var6);
            this.a(var0, var3, var5, Climate.b.a(this.B, this.C), this.s[3], var1, 0.0F, var7);
            if (var1.b() < 0L) {
               this.a(var0, var3, var5, this.y, this.s[4], var1, 0.0F, var11);
               this.a(var0, var3, var5, Climate.b.a(this.A, this.C), this.s[4], var1, 0.0F, var6);
            } else {
               this.a(var0, var3, var5, Climate.b.a(this.y, this.C), this.s[4], var1, 0.0F, var6);
            }

            this.a(var0, var3, var5, this.y, this.s[5], var1, 0.0F, var13);
            this.a(var0, var3, var5, this.A, this.s[5], var1, 0.0F, var12);
            this.a(var0, var3, var5, Climate.b.a(this.B, this.C), this.s[5], var1, 0.0F, var9);
            if (var1.b() < 0L) {
               this.a(var0, var3, var5, this.y, this.s[6], var1, 0.0F, var11);
            } else {
               this.a(var0, var3, var5, this.y, this.s[6], var1, 0.0F, var6);
            }

            if (var2 == 0) {
               this.a(var0, var3, var5, Climate.b.a(this.A, this.C), this.s[6], var1, 0.0F, var6);
            }
         }
      }
   }

   private void d(Consumer<Pair<Climate.d, ResourceKey<BiomeBase>>> var0, Climate.b var1) {
      this.a(var0, this.p, this.p, this.y, Climate.b.a(this.s[0], this.s[2]), var1, 0.0F, Biomes.O);
      this.a(var0, Climate.b.a(this.q[1], this.q[2]), this.p, Climate.b.a(this.A, this.C), this.s[6], var1, 0.0F, Biomes.g);
      this.a(var0, Climate.b.a(this.q[3], this.q[4]), this.p, Climate.b.a(this.A, this.C), this.s[6], var1, 0.0F, Biomes.h);

      for(int var2 = 0; var2 < this.q.length; ++var2) {
         Climate.b var3 = this.q[var2];

         for(int var4 = 0; var4 < this.r.length; ++var4) {
            Climate.b var5 = this.r[var4];
            ResourceKey<BiomeBase> var6 = this.a(var2, var4, var1);
            ResourceKey<BiomeBase> var7 = this.b(var2, var4, var1);
            ResourceKey<BiomeBase> var8 = this.c(var2, var4, var1);
            ResourceKey<BiomeBase> var9 = this.a(var2, var4);
            ResourceKey<BiomeBase> var10 = this.a(var2, var4, var1, var6);
            ResourceKey<BiomeBase> var11 = this.d(var2, var4, var1);
            this.a(var0, var3, var5, this.A, Climate.b.a(this.s[0], this.s[1]), var1, 0.0F, var7);
            this.a(var0, var3, var5, Climate.b.a(this.B, this.C), Climate.b.a(this.s[0], this.s[1]), var1, 0.0F, var8);
            this.a(var0, var3, var5, this.A, Climate.b.a(this.s[2], this.s[3]), var1, 0.0F, var6);
            this.a(var0, var3, var5, Climate.b.a(this.B, this.C), Climate.b.a(this.s[2], this.s[3]), var1, 0.0F, var7);
            this.a(var0, var3, var5, this.y, Climate.b.a(this.s[3], this.s[4]), var1, 0.0F, var9);
            this.a(var0, var3, var5, Climate.b.a(this.A, this.C), this.s[4], var1, 0.0F, var6);
            this.a(var0, var3, var5, this.y, this.s[5], var1, 0.0F, var11);
            this.a(var0, var3, var5, this.A, this.s[5], var1, 0.0F, var10);
            this.a(var0, var3, var5, Climate.b.a(this.B, this.C), this.s[5], var1, 0.0F, var6);
            this.a(var0, var3, var5, this.y, this.s[6], var1, 0.0F, var9);
            if (var2 == 0) {
               this.a(var0, var3, var5, Climate.b.a(this.A, this.C), this.s[6], var1, 0.0F, var6);
            }
         }
      }
   }

   private void e(Consumer<Pair<Climate.d, ResourceKey<BiomeBase>>> var0, Climate.b var1) {
      this.a(var0, this.t, this.p, this.y, Climate.b.a(this.s[0], this.s[1]), var1, 0.0F, var1.b() < 0L ? Biomes.O : Biomes.L);
      this.a(var0, this.u, this.p, this.y, Climate.b.a(this.s[0], this.s[1]), var1, 0.0F, var1.b() < 0L ? Biomes.O : Biomes.K);
      this.a(var0, this.t, this.p, this.A, Climate.b.a(this.s[0], this.s[1]), var1, 0.0F, Biomes.L);
      this.a(var0, this.u, this.p, this.A, Climate.b.a(this.s[0], this.s[1]), var1, 0.0F, Biomes.K);
      this.a(var0, this.t, this.p, Climate.b.a(this.y, this.C), Climate.b.a(this.s[2], this.s[5]), var1, 0.0F, Biomes.L);
      this.a(var0, this.u, this.p, Climate.b.a(this.y, this.C), Climate.b.a(this.s[2], this.s[5]), var1, 0.0F, Biomes.K);
      this.a(var0, this.t, this.p, this.y, this.s[6], var1, 0.0F, Biomes.L);
      this.a(var0, this.u, this.p, this.y, this.s[6], var1, 0.0F, Biomes.K);
      this.a(var0, Climate.b.a(this.q[1], this.q[2]), this.p, Climate.b.a(this.z, this.C), this.s[6], var1, 0.0F, Biomes.g);
      this.a(var0, Climate.b.a(this.q[3], this.q[4]), this.p, Climate.b.a(this.z, this.C), this.s[6], var1, 0.0F, Biomes.h);
      this.a(var0, this.t, this.p, Climate.b.a(this.z, this.C), this.s[6], var1, 0.0F, Biomes.L);

      for(int var2 = 0; var2 < this.q.length; ++var2) {
         Climate.b var3 = this.q[var2];

         for(int var4 = 0; var4 < this.r.length; ++var4) {
            Climate.b var5 = this.r[var4];
            ResourceKey<BiomeBase> var6 = this.b(var2, var4, var1);
            this.a(var0, var3, var5, Climate.b.a(this.B, this.C), Climate.b.a(this.s[0], this.s[1]), var1, 0.0F, var6);
         }
      }
   }

   private void e(Consumer<Pair<Climate.d, ResourceKey<BiomeBase>>> var0) {
      this.b(var0, this.p, this.p, Climate.b.a(0.8F, 1.0F), this.p, this.p, 0.0F, Biomes.Z);
      this.b(var0, this.p, Climate.b.a(0.7F, 1.0F), this.p, this.p, this.p, 0.0F, Biomes.aa);
      this.c(var0, this.p, this.p, this.p, Climate.b.a(this.s[0], this.s[1]), this.p, 0.0F, Biomes.ab);
   }

   private ResourceKey<BiomeBase> a(int var0, int var1, Climate.b var2) {
      if (var2.b() < 0L) {
         return this.E[var0][var1];
      } else {
         ResourceKey<BiomeBase> var3 = this.F[var0][var1];
         return var3 == null ? this.E[var0][var1] : var3;
      }
   }

   private ResourceKey<BiomeBase> b(int var0, int var1, Climate.b var2) {
      return var0 == 4 ? this.a(var1, var2) : this.a(var0, var1, var2);
   }

   private ResourceKey<BiomeBase> c(int var0, int var1, Climate.b var2) {
      return var0 == 0 ? this.g(var0, var1, var2) : this.b(var0, var1, var2);
   }

   private ResourceKey<BiomeBase> a(int var0, int var1, Climate.b var2, ResourceKey<BiomeBase> var3) {
      return var0 > 1 && var1 < 4 && var2.b() >= 0L ? Biomes.w : var3;
   }

   private ResourceKey<BiomeBase> d(int var0, int var1, Climate.b var2) {
      ResourceKey<BiomeBase> var3 = var2.b() >= 0L ? this.a(var0, var1, var2) : this.a(var0, var1);
      return this.a(var0, var1, var2, var3);
   }

   private ResourceKey<BiomeBase> a(int var0, int var1) {
      if (var0 == 0) {
         return Biomes.N;
      } else {
         return var0 == 4 ? Biomes.f : Biomes.M;
      }
   }

   private ResourceKey<BiomeBase> a(int var0, Climate.b var1) {
      if (var0 < 2) {
         return var1.b() < 0L ? Biomes.A : Biomes.B;
      } else {
         return var0 < 3 ? Biomes.A : Biomes.C;
      }
   }

   private ResourceKey<BiomeBase> e(int var0, int var1, Climate.b var2) {
      ResourceKey<BiomeBase> var3 = this.H[var0][var1];
      return var2.b() >= 0L && var3 != null && (var3 != Biomes.E || this.o == OverworldBiomeBuilder.a.b) ? var3 : this.G[var0][var1];
   }

   private ResourceKey<BiomeBase> f(int var0, int var1, Climate.b var2) {
      if (var0 <= 2) {
         return var2.b() < 0L ? Biomes.I : Biomes.H;
      } else {
         return var0 == 3 ? Biomes.J : this.a(var1, var2);
      }
   }

   private ResourceKey<BiomeBase> g(int var0, int var1, Climate.b var2) {
      if (var0 >= 3) {
         return this.e(var0, var1, var2);
      } else {
         return var1 <= 1 ? Biomes.G : Biomes.F;
      }
   }

   private ResourceKey<BiomeBase> h(int var0, int var1, Climate.b var2) {
      ResourceKey<BiomeBase> var3 = this.I[var0][var1];
      return var3 == null ? this.a(var0, var1, var2) : var3;
   }

   private void a(
      Consumer<Pair<Climate.d, ResourceKey<BiomeBase>>> var0,
      Climate.b var1,
      Climate.b var2,
      Climate.b var3,
      Climate.b var4,
      Climate.b var5,
      float var6,
      ResourceKey<BiomeBase> var7
   ) {
      var0.accept(Pair.of(Climate.a(var1, var2, var3, var4, Climate.b.a(0.0F), var5, var6), var7));
      var0.accept(Pair.of(Climate.a(var1, var2, var3, var4, Climate.b.a(1.0F), var5, var6), var7));
   }

   private void b(
      Consumer<Pair<Climate.d, ResourceKey<BiomeBase>>> var0,
      Climate.b var1,
      Climate.b var2,
      Climate.b var3,
      Climate.b var4,
      Climate.b var5,
      float var6,
      ResourceKey<BiomeBase> var7
   ) {
      var0.accept(Pair.of(Climate.a(var1, var2, var3, var4, Climate.b.a(0.2F, 0.9F), var5, var6), var7));
   }

   private void c(
      Consumer<Pair<Climate.d, ResourceKey<BiomeBase>>> var0,
      Climate.b var1,
      Climate.b var2,
      Climate.b var3,
      Climate.b var4,
      Climate.b var5,
      float var6,
      ResourceKey<BiomeBase> var7
   ) {
      var0.accept(Pair.of(Climate.a(var1, var2, var3, var4, Climate.b.a(1.1F), var5, var6), var7));
   }

   public static boolean a(DensityFunction var0, DensityFunction var1, DensityFunction.b var2) {
      return var0.a(var2) < -0.225F && var1.a(var2) > 0.9F;
   }

   public static String a(double var0) {
      if (var0 < (double)NoiseRouterData.a(0.05F)) {
         return "Valley";
      } else if (var0 < (double)NoiseRouterData.a(0.26666668F)) {
         return "Low";
      } else if (var0 < (double)NoiseRouterData.a(0.4F)) {
         return "Mid";
      } else {
         return var0 < (double)NoiseRouterData.a(0.56666666F) ? "High" : "Peak";
      }
   }

   public String b(double var0) {
      double var2 = (double)Climate.a((float)var0);
      if (var2 < (double)this.v.b()) {
         return "Mushroom fields";
      } else if (var2 < (double)this.w.b()) {
         return "Deep ocean";
      } else if (var2 < (double)this.x.b()) {
         return "Ocean";
      } else if (var2 < (double)this.y.b()) {
         return "Coast";
      } else if (var2 < (double)this.A.b()) {
         return "Near inland";
      } else {
         return var2 < (double)this.B.b() ? "Mid inland" : "Far inland";
      }
   }

   public String c(double var0) {
      return a(var0, this.s);
   }

   public String d(double var0) {
      return a(var0, this.q);
   }

   public String e(double var0) {
      return a(var0, this.r);
   }

   private static String a(double var0, Climate.b[] var2) {
      double var3 = (double)Climate.a((float)var0);

      for(int var5 = 0; var5 < var2.length; ++var5) {
         if (var3 < (double)var2[var5].b()) {
            return var5 + "";
         }
      }

      return "?";
   }

   @VisibleForDebug
   public Climate.b[] b() {
      return this.q;
   }

   @VisibleForDebug
   public Climate.b[] c() {
      return this.r;
   }

   @VisibleForDebug
   public Climate.b[] d() {
      return this.s;
   }

   @VisibleForDebug
   public Climate.b[] e() {
      return new Climate.b[]{this.v, this.w, this.x, this.y, this.A, this.B, this.C};
   }

   @VisibleForDebug
   public Climate.b[] f() {
      return new Climate.b[]{
         Climate.b.a(-2.0F, NoiseRouterData.a(0.05F)),
         Climate.b.a(NoiseRouterData.a(0.05F), NoiseRouterData.a(0.26666668F)),
         Climate.b.a(NoiseRouterData.a(0.26666668F), NoiseRouterData.a(0.4F)),
         Climate.b.a(NoiseRouterData.a(0.4F), NoiseRouterData.a(0.56666666F)),
         Climate.b.a(NoiseRouterData.a(0.56666666F), 2.0F)
      };
   }

   @VisibleForDebug
   public Climate.b[] g() {
      return new Climate.b[]{Climate.b.a(-2.0F, 0.0F), Climate.b.a(0.0F, 2.0F)};
   }

   public static enum a {
      a,
      b;
   }
}
