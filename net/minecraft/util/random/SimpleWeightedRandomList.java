package net.minecraft.util.random;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Optional;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;

public class SimpleWeightedRandomList<E> extends WeightedRandomList<WeightedEntry.b<E>> {
   public static <E> Codec<SimpleWeightedRandomList<E>> a(Codec<E> var0) {
      return WeightedEntry.b.a(var0).listOf().xmap(SimpleWeightedRandomList::new, WeightedRandomList::e);
   }

   public static <E> Codec<SimpleWeightedRandomList<E>> b(Codec<E> var0) {
      return ExtraCodecs.a(WeightedEntry.b.a(var0).listOf()).xmap(SimpleWeightedRandomList::new, WeightedRandomList::e);
   }

   SimpleWeightedRandomList(List<? extends WeightedEntry.b<E>> var0) {
      super(var0);
   }

   public static <E> SimpleWeightedRandomList.a<E> a() {
      return new SimpleWeightedRandomList.a<>();
   }

   public static <E> SimpleWeightedRandomList<E> b() {
      return new SimpleWeightedRandomList<>(List.of());
   }

   public static <E> SimpleWeightedRandomList<E> a(E var0) {
      return new SimpleWeightedRandomList<>(List.of(WeightedEntry.a(var0, 1)));
   }

   public Optional<E> a(RandomSource var0) {
      return this.b(var0).map(WeightedEntry.b::b);
   }

   public static class a<E> {
      private final Builder<WeightedEntry.b<E>> a = ImmutableList.builder();

      public SimpleWeightedRandomList.a<E> a(E var0, int var1) {
         this.a.add(WeightedEntry.a(var0, var1));
         return this;
      }

      public SimpleWeightedRandomList<E> a() {
         return new SimpleWeightedRandomList<>(this.a.build());
      }
   }
}
