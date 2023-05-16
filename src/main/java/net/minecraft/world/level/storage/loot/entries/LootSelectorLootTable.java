package net.minecraft.world.level.storage.loot.entries;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.function.Consumer;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootCollector;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class LootSelectorLootTable extends LootSelectorEntry {
   final MinecraftKey i;

   LootSelectorLootTable(MinecraftKey var0, int var1, int var2, LootItemCondition[] var3, LootItemFunction[] var4) {
      super(var1, var2, var3, var4);
      this.i = var0;
   }

   @Override
   public LootEntryType a() {
      return LootEntries.c;
   }

   @Override
   public void a(Consumer<ItemStack> var0, LootTableInfo var1) {
      LootTable var2 = var1.a(this.i);
      var2.b(var1, var0);
   }

   @Override
   public void a(LootCollector var0) {
      if (var0.a(this.i)) {
         var0.a("Table " + this.i + " is recursively called");
      } else {
         super.a(var0);
         LootTable var1 = var0.c(this.i);
         if (var1 == null) {
            var0.a("Unknown loot table called " + this.i);
         } else {
            var1.a(var0.a("->{" + this.i + "}", this.i));
         }
      }
   }

   public static LootSelectorEntry.a<?> a(MinecraftKey var0) {
      return a((var1, var2, var3, var4) -> new LootSelectorLootTable(var0, var1, var2, var3, var4));
   }

   public static class a extends LootSelectorEntry.e<LootSelectorLootTable> {
      public void a(JsonObject var0, LootSelectorLootTable var1, JsonSerializationContext var2) {
         super.a(var0, var1, var2);
         var0.addProperty("name", var1.i.toString());
      }

      protected LootSelectorLootTable a(
         JsonObject var0, JsonDeserializationContext var1, int var2, int var3, LootItemCondition[] var4, LootItemFunction[] var5
      ) {
         MinecraftKey var6 = new MinecraftKey(ChatDeserializer.h(var0, "name"));
         return new LootSelectorLootTable(var6, var2, var3, var4, var5);
      }
   }
}
