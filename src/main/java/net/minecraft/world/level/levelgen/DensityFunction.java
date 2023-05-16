package net.minecraft.world.level.levelgen;

import com.mojang.serialization.Codec;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.synth.NoiseGeneratorNormal;

public interface DensityFunction {
   Codec<DensityFunction> b = DensityFunctions.b;
   Codec<Holder<DensityFunction>> c = RegistryFileCodec.a(Registries.ar, b);
   Codec<DensityFunction> d = c.xmap(DensityFunctions.j::new, var0 -> (Holder)(var0 instanceof DensityFunctions.j var1 ? var1.j() : new Holder.a<>(var0)));

   double a(DensityFunction.b var1);

   void a(double[] var1, DensityFunction.a var2);

   DensityFunction a(DensityFunction.f var1);

   double a();

   double b();

   KeyDispatchDataCodec<? extends DensityFunction> c();

   default DensityFunction a(double var0, double var2) {
      return new DensityFunctions.g(this, var0, var2);
   }

   default DensityFunction d() {
      return DensityFunctions.a(this, DensityFunctions.k.a.a);
   }

   default DensityFunction e() {
      return DensityFunctions.a(this, DensityFunctions.k.a.b);
   }

   default DensityFunction f() {
      return DensityFunctions.a(this, DensityFunctions.k.a.c);
   }

   default DensityFunction g() {
      return DensityFunctions.a(this, DensityFunctions.k.a.d);
   }

   default DensityFunction h() {
      return DensityFunctions.a(this, DensityFunctions.k.a.e);
   }

   default DensityFunction i() {
      return DensityFunctions.a(this, DensityFunctions.k.a.f);
   }

   public interface a {
      DensityFunction.b a(int var1);

      void a(double[] var1, DensityFunction var2);
   }

   public interface b {
      int a();

      int b();

      int c();

      default Blender d() {
         return Blender.a();
      }
   }

   public static record c(Holder<NoiseGeneratorNormal.a> noiseData, @Nullable NoiseGeneratorNormal noise) {
      private final Holder<NoiseGeneratorNormal.a> b;
      @Nullable
      private final NoiseGeneratorNormal c;
      public static final Codec<DensityFunction.c> a = NoiseGeneratorNormal.a.b.xmap(var0 -> new DensityFunction.c(var0, null), DensityFunction.c::b);

      public c(Holder<NoiseGeneratorNormal.a> var0) {
         this(var0, null);
      }

      public c(Holder<NoiseGeneratorNormal.a> var0, @Nullable NoiseGeneratorNormal var1) {
         this.b = var0;
         this.c = var1;
      }

      public double a(double var0, double var2, double var4) {
         return this.c == null ? 0.0 : this.c.a(var0, var2, var4);
      }

      public double a() {
         return this.c == null ? 2.0 : this.c.a();
      }
   }

   public interface d extends DensityFunction {
      @Override
      default void a(double[] var0, DensityFunction.a var1) {
         var1.a(var0, this);
      }

      @Override
      default DensityFunction a(DensityFunction.f var0) {
         return var0.apply(this);
      }
   }

   public static record e(int blockX, int blockY, int blockZ) implements DensityFunction.b {
      private final int a;
      private final int b;
      private final int c;

      public e(int var0, int var1, int var2) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
      }
   }

   public interface f {
      DensityFunction apply(DensityFunction var1);

      default DensityFunction.c a(DensityFunction.c var0) {
         return var0;
      }
   }
}
