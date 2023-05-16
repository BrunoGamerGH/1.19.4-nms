package net.minecraft.world.level.storage.loot.functions;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootItemUser;
import net.minecraft.world.level.storage.loot.LootTableInfo;

public interface LootItemFunction extends LootItemUser, BiFunction<ItemStack, LootTableInfo, ItemStack> {
   LootItemFunctionType a();

   static Consumer<ItemStack> a(BiFunction<ItemStack, LootTableInfo, ItemStack> var0, Consumer<ItemStack> var1, LootTableInfo var2) {
      return var3 -> var1.accept(var0.apply(var3, var2));
   }

   public interface a {
      LootItemFunction b();
   }
}
