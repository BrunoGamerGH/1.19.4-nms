package net.minecraft.world.level.storage.loot.predicates;

import java.util.function.Function;

public interface LootItemConditionUser<T extends LootItemConditionUser<T>> {
   T b(LootItemCondition.a var1);

   default <E> T a_(Iterable<E> var0, Function<E, LootItemCondition.a> var1) {
      T var2 = this.d();

      for(E var4 : var0) {
         var2 = var2.b(var1.apply(var4));
      }

      return var2;
   }

   T d();
}
