package net.minecraft.world.level.storage.loot.entries;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import java.util.function.Consumer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class LootSelectorEmpty extends LootSelectorEntry {
   LootSelectorEmpty(int var0, int var1, LootItemCondition[] var2, LootItemFunction[] var3) {
      super(var0, var1, var2, var3);
   }

   @Override
   public LootEntryType a() {
      return LootEntries.a;
   }

   @Override
   public void a(Consumer<ItemStack> var0, LootTableInfo var1) {
   }

   public static LootSelectorEntry.a<?> b() {
      return a(LootSelectorEmpty::new);
   }

   public static class a extends LootSelectorEntry.e<LootSelectorEmpty> {
      public LootSelectorEmpty a(JsonObject var0, JsonDeserializationContext var1, int var2, int var3, LootItemCondition[] var4, LootItemFunction[] var5) {
         return new LootSelectorEmpty(var2, var3, var4, var5);
      }
   }
}
