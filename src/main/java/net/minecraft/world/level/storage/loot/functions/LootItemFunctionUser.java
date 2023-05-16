package net.minecraft.world.level.storage.loot.functions;

import java.util.Arrays;
import java.util.function.Function;

public interface LootItemFunctionUser<T extends LootItemFunctionUser<T>> {
   T b(LootItemFunction.a var1);

   default <E> T a(Iterable<E> var0, Function<E, LootItemFunction.a> var1) {
      T var2 = this.c();

      for(E var4 : var0) {
         var2 = var2.b(var1.apply(var4));
      }

      return var2;
   }

   default <E> T a(E[] var0, Function<E, LootItemFunction.a> var1) {
      return this.a(Arrays.asList(var0), var1);
   }

   T c();
}
