package net.minecraft.util.random;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public interface WeightedEntry {
   Weight a();

   static <T> WeightedEntry.b<T> a(T var0, int var1) {
      return new WeightedEntry.b<>(var0, Weight.a(var1));
   }

   public static class a implements WeightedEntry {
      private final Weight a;

      public a(int var0) {
         this.a = Weight.a(var0);
      }

      public a(Weight var0) {
         this.a = var0;
      }

      @Override
      public Weight a() {
         return this.a;
      }
   }

   public static class b<T> implements WeightedEntry {
      private final T a;
      private final Weight b;

      b(T var0, Weight var1) {
         this.a = var0;
         this.b = var1;
      }

      public T b() {
         return this.a;
      }

      @Override
      public Weight a() {
         return this.b;
      }

      public static <E> Codec<WeightedEntry.b<E>> a(Codec<E> var0) {
         return RecordCodecBuilder.create(
            var1 -> var1.group(var0.fieldOf("data").forGetter(WeightedEntry.b::b), Weight.a.fieldOf("weight").forGetter(WeightedEntry.b::a))
                  .apply(var1, WeightedEntry.b::new)
         );
      }
   }
}
