package net.minecraft.util.random;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Optional;
import net.minecraft.util.RandomSource;

public class WeightedRandomList<E extends WeightedEntry> {
   private final int a;
   private final ImmutableList<E> b;

   WeightedRandomList(List<? extends E> var0) {
      this.b = ImmutableList.copyOf(var0);
      this.a = WeightedRandom2.a(var0);
   }

   public static <E extends WeightedEntry> WeightedRandomList<E> c() {
      return new WeightedRandomList<>(ImmutableList.of());
   }

   @SafeVarargs
   public static <E extends WeightedEntry> WeightedRandomList<E> a(E... var0) {
      return new WeightedRandomList<>(ImmutableList.copyOf(var0));
   }

   public static <E extends WeightedEntry> WeightedRandomList<E> a(List<E> var0) {
      return new WeightedRandomList<>(var0);
   }

   public boolean d() {
      return this.b.isEmpty();
   }

   public Optional<E> b(RandomSource var0) {
      if (this.a == 0) {
         return Optional.empty();
      } else {
         int var1 = var0.a(this.a);
         return WeightedRandom2.a(this.b, var1);
      }
   }

   public List<E> e() {
      return this.b;
   }

   public static <E extends WeightedEntry> Codec<WeightedRandomList<E>> c(Codec<E> var0) {
      return var0.listOf().xmap(WeightedRandomList::a, WeightedRandomList::e);
   }
}
