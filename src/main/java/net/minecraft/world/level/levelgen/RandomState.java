package net.minecraft.world.level.levelgen;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.levelgen.synth.BlendedNoise;
import net.minecraft.world.level.levelgen.synth.NoiseGeneratorNormal;

public final class RandomState {
   final PositionalRandomFactory a;
   private final HolderGetter<NoiseGeneratorNormal.a> b;
   private final NoiseRouter c;
   private final Climate.Sampler d;
   private final SurfaceSystem e;
   private final PositionalRandomFactory f;
   private final PositionalRandomFactory g;
   private final Map<ResourceKey<NoiseGeneratorNormal.a>, NoiseGeneratorNormal> h;
   private final Map<MinecraftKey, PositionalRandomFactory> i;

   public static RandomState a(HolderGetter.a var0, ResourceKey<GeneratorSettingBase> var1, long var2) {
      return a(var0.<GeneratorSettingBase>b(Registries.au).b(var1).a(), var0.b(Registries.av), var2);
   }

   public static RandomState a(GeneratorSettingBase var0, HolderGetter<NoiseGeneratorNormal.a> var1, long var2) {
      return new RandomState(var0, var1, var2);
   }

   private RandomState(GeneratorSettingBase var0, HolderGetter<NoiseGeneratorNormal.a> var1, final long var2) {
      this.a = var0.d().a(var2).e();
      this.b = var1;
      this.f = this.a.a(new MinecraftKey("aquifer")).e();
      this.g = this.a.a(new MinecraftKey("ore")).e();
      this.h = new ConcurrentHashMap<>();
      this.i = new ConcurrentHashMap<>();
      this.e = new SurfaceSystem(this, var0.g(), var0.l(), this.a);
      final boolean var4 = var0.n();

      class a implements DensityFunction.f {
         private final Map<DensityFunction, DensityFunction> d = new HashMap<>();

         private RandomSource a(long var0) {
            return new LegacyRandomSource(var2 + var0);
         }

         @Override
         public DensityFunction.c a(DensityFunction.c var0) {
            Holder<NoiseGeneratorNormal.a> var1 = var0.b();
            if (var4) {
               if (var1.a(Noises.a)) {
                  NoiseGeneratorNormal var2 = NoiseGeneratorNormal.a(this.a(0L), new NoiseGeneratorNormal.a(-7, 1.0, 1.0));
                  return new DensityFunction.c(var1, var2);
               }

               if (var1.a(Noises.b)) {
                  NoiseGeneratorNormal var2 = NoiseGeneratorNormal.a(this.a(1L), new NoiseGeneratorNormal.a(-7, 1.0, 1.0));
                  return new DensityFunction.c(var1, var2);
               }

               if (var1.a(Noises.j)) {
                  NoiseGeneratorNormal var2 = NoiseGeneratorNormal.b(RandomState.this.a.a(Noises.j.a()), new NoiseGeneratorNormal.a(0, 0.0));
                  return new DensityFunction.c(var1, var2);
               }
            }

            NoiseGeneratorNormal var2 = RandomState.this.a(var1.e().orElseThrow());
            return new DensityFunction.c(var1, var2);
         }

         private DensityFunction a(DensityFunction var0) {
            if (var0 instanceof BlendedNoise var1) {
               RandomSource var2 = var4 ? this.a(0L) : RandomState.this.a.a(new MinecraftKey("terrain"));
               return var1.a(var2);
            } else {
               return (DensityFunction)(var0 instanceof DensityFunctions.i ? new DensityFunctions.i(var2) : var0);
            }
         }

         @Override
         public DensityFunction apply(DensityFunction var0) {
            return this.d.computeIfAbsent(var0, this::a);
         }
      }

      this.c = var0.i().a(new a());
      DensityFunction.f var5 = new DensityFunction.f() {
         private final Map<DensityFunction, DensityFunction> b = new HashMap<>();

         private DensityFunction a(DensityFunction var0) {
            if (var0 instanceof DensityFunctions.j var1) {
               return var1.j().a();
            } else {
               return var0 instanceof DensityFunctions.l var1 ? var1.k() : var0;
            }
         }

         @Override
         public DensityFunction apply(DensityFunction var0) {
            return this.b.computeIfAbsent(var0, this::a);
         }
      };
      this.d = new Climate.Sampler(
         this.c.e().a(var5), this.c.f().a(var5), this.c.g().a(var5), this.c.h().a(var5), this.c.i().a(var5), this.c.j().a(var5), var0.k()
      );
   }

   public NoiseGeneratorNormal a(ResourceKey<NoiseGeneratorNormal.a> var0) {
      return this.h.computeIfAbsent(var0, var1x -> Noises.a(this.b, this.a, var0));
   }

   public PositionalRandomFactory a(MinecraftKey var0) {
      return this.i.computeIfAbsent(var0, var1x -> this.a.a(var0).e());
   }

   public NoiseRouter a() {
      return this.c;
   }

   public Climate.Sampler b() {
      return this.d;
   }

   public SurfaceSystem c() {
      return this.e;
   }

   public PositionalRandomFactory d() {
      return this.f;
   }

   public PositionalRandomFactory e() {
      return this.g;
   }
}
