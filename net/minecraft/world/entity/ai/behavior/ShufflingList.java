package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.util.RandomSource;

public class ShufflingList<U> implements Iterable<U> {
   protected final List<ShufflingList.a<U>> a;
   private final RandomSource b = RandomSource.a();

   public ShufflingList() {
      this.a = Lists.newArrayList();
   }

   private ShufflingList(List<ShufflingList.a<U>> var0) {
      this.a = Lists.newArrayList(var0);
   }

   public static <U> Codec<ShufflingList<U>> a(Codec<U> var0) {
      return ShufflingList.a.a(var0).listOf().xmap(ShufflingList::new, var0x -> var0x.a);
   }

   public ShufflingList<U> a(U var0, int var1) {
      this.a.add(new ShufflingList.a<>(var0, var1));
      return this;
   }

   public ShufflingList<U> a() {
      this.a.forEach(var0 -> var0.a(this.b.i()));
      this.a.sort(Comparator.comparingDouble(ShufflingList.a::c));
      return this;
   }

   public Stream<U> b() {
      return this.a.stream().map(ShufflingList.a::a);
   }

   @Override
   public Iterator<U> iterator() {
      return Iterators.transform(this.a.iterator(), ShufflingList.a::a);
   }

   @Override
   public String toString() {
      return "ShufflingList[" + this.a + "]";
   }

   public static class a<T> {
      final T a;
      final int b;
      private double c;

      a(T var0, int var1) {
         this.b = var1;
         this.a = var0;
      }

      private double c() {
         return this.c;
      }

      void a(float var0) {
         this.c = -Math.pow((double)var0, (double)(1.0F / (float)this.b));
      }

      public T a() {
         return this.a;
      }

      public int b() {
         return this.b;
      }

      @Override
      public String toString() {
         return this.b + ":" + this.a;
      }

      public static <E> Codec<ShufflingList.a<E>> a(final Codec<E> var0) {
         return new Codec<ShufflingList.a<E>>() {
            public <T> DataResult<Pair<ShufflingList.a<E>, T>> decode(DynamicOps<T> var0x, T var1) {
               Dynamic<T> var2 = new Dynamic(var0, var1);
               return var2.get("data")
                  .flatMap(var0::parse)
                  .map(var1x -> new ShufflingList.a<>(var1x, var2.get("weight").asInt(1)))
                  .map(var1x -> Pair.of(var1x, var0.empty()));
            }

            public <T> DataResult<T> a(ShufflingList.a<E> var0x, DynamicOps<T> var1, T var2) {
               return var1.mapBuilder().add("weight", var1.createInt(var0.b)).add("data", var0.encodeStart(var1, var0.a)).build(var2);
            }
         };
      }
   }
}
