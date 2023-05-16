package net.minecraft.world.level.levelgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Function;

public record NoiseRouter(
   DensityFunction barrierNoise,
   DensityFunction fluidLevelFloodednessNoise,
   DensityFunction fluidLevelSpreadNoise,
   DensityFunction lavaNoise,
   DensityFunction temperature,
   DensityFunction vegetation,
   DensityFunction continents,
   DensityFunction erosion,
   DensityFunction depth,
   DensityFunction ridges,
   DensityFunction initialDensityWithoutJaggedness,
   DensityFunction finalDensity,
   DensityFunction veinToggle,
   DensityFunction veinRidged,
   DensityFunction veinGap
) {
   private final DensityFunction b;
   private final DensityFunction c;
   private final DensityFunction d;
   private final DensityFunction e;
   private final DensityFunction f;
   private final DensityFunction g;
   private final DensityFunction h;
   private final DensityFunction i;
   private final DensityFunction j;
   private final DensityFunction k;
   private final DensityFunction l;
   private final DensityFunction m;
   private final DensityFunction n;
   private final DensityFunction o;
   private final DensityFunction p;
   public static final Codec<NoiseRouter> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               a("barrier", NoiseRouter::a),
               a("fluid_level_floodedness", NoiseRouter::b),
               a("fluid_level_spread", NoiseRouter::c),
               a("lava", NoiseRouter::d),
               a("temperature", NoiseRouter::e),
               a("vegetation", NoiseRouter::f),
               a("continents", NoiseRouter::g),
               a("erosion", NoiseRouter::h),
               a("depth", NoiseRouter::i),
               a("ridges", NoiseRouter::j),
               a("initial_density_without_jaggedness", NoiseRouter::k),
               a("final_density", NoiseRouter::l),
               a("vein_toggle", NoiseRouter::m),
               a("vein_ridged", NoiseRouter::n),
               a("vein_gap", NoiseRouter::o)
            )
            .apply(var0, NoiseRouter::new)
   );

   public NoiseRouter(
      DensityFunction var0,
      DensityFunction var1,
      DensityFunction var2,
      DensityFunction var3,
      DensityFunction var4,
      DensityFunction var5,
      DensityFunction var6,
      DensityFunction var7,
      DensityFunction var8,
      DensityFunction var9,
      DensityFunction var10,
      DensityFunction var11,
      DensityFunction var12,
      DensityFunction var13,
      DensityFunction var14
   ) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
      this.e = var3;
      this.f = var4;
      this.g = var5;
      this.h = var6;
      this.i = var7;
      this.j = var8;
      this.k = var9;
      this.l = var10;
      this.m = var11;
      this.n = var12;
      this.o = var13;
      this.p = var14;
   }

   private static RecordCodecBuilder<NoiseRouter, DensityFunction> a(String var0, Function<NoiseRouter, DensityFunction> var1) {
      return DensityFunction.d.fieldOf(var0).forGetter(var1);
   }

   public NoiseRouter a(DensityFunction.f var0) {
      return new NoiseRouter(
         this.b.a(var0),
         this.c.a(var0),
         this.d.a(var0),
         this.e.a(var0),
         this.f.a(var0),
         this.g.a(var0),
         this.h.a(var0),
         this.i.a(var0),
         this.j.a(var0),
         this.k.a(var0),
         this.l.a(var0),
         this.m.a(var0),
         this.n.a(var0),
         this.o.a(var0),
         this.p.a(var0)
      );
   }

   public DensityFunction a() {
      return this.b;
   }

   public DensityFunction b() {
      return this.c;
   }

   public DensityFunction c() {
      return this.d;
   }

   public DensityFunction d() {
      return this.e;
   }

   public DensityFunction e() {
      return this.f;
   }

   public DensityFunction f() {
      return this.g;
   }

   public DensityFunction g() {
      return this.h;
   }

   public DensityFunction h() {
      return this.i;
   }

   public DensityFunction i() {
      return this.j;
   }

   public DensityFunction j() {
      return this.k;
   }

   public DensityFunction k() {
      return this.l;
   }

   public DensityFunction l() {
      return this.m;
   }

   public DensityFunction m() {
      return this.n;
   }

   public DensityFunction n() {
      return this.o;
   }

   public DensityFunction o() {
      return this.p;
   }
}
