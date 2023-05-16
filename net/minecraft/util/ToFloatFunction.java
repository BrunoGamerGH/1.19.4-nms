package net.minecraft.util;

import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import java.util.function.Function;

public interface ToFloatFunction<C> {
   ToFloatFunction<Float> a = a(var0 -> var0);

   float a(C var1);

   float b();

   float c();

   static ToFloatFunction<Float> a(final Float2FloatFunction var0) {
      return new ToFloatFunction<Float>() {
         public float a(Float var0x) {
            return var0.apply(var0);
         }

         @Override
         public float b() {
            return Float.NEGATIVE_INFINITY;
         }

         @Override
         public float c() {
            return Float.POSITIVE_INFINITY;
         }
      };
   }

   default <C2> ToFloatFunction<C2> a(final Function<C2, C> var0) {
      final ToFloatFunction<C> var1 = this;
      return new ToFloatFunction<C2>() {
         @Override
         public float a(C2 var0x) {
            return var1.a(var0.apply(var0));
         }

         @Override
         public float b() {
            return var1.b();
         }

         @Override
         public float c() {
            return var1.c();
         }
      };
   }
}
