package net.minecraft.world.level.storage.loot.entries;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.function.Consumer;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class LootSelectorDynamic extends LootSelectorEntry {
   final MinecraftKey i;

   LootSelectorDynamic(MinecraftKey var0, int var1, int var2, LootItemCondition[] var3, LootItemFunction[] var4) {
      super(var1, var2, var3, var4);
      this.i = var0;
   }

   @Override
   public LootEntryType a() {
      return LootEntries.d;
   }

   @Override
   public void a(Consumer<ItemStack> var0, LootTableInfo var1) {
      var1.a(this.i, var0);
   }

   public static LootSelectorEntry.a<?> a(MinecraftKey var0) {
      return a((var1, var2, var3, var4) -> new LootSelectorDynamic(var0, var1, var2, var3, var4));
   }

   public static class a extends LootSelectorEntry.e<LootSelectorDynamic> {
      public void a(JsonObject var0, LootSelectorDynamic var1, JsonSerializationContext var2) {
         super.a(var0, var1, var2);
         var0.addProperty("name", var1.i.toString());
      }

      protected LootSelectorDynamic a(JsonObject var0, JsonDeserializationContext var1, int var2, int var3, LootItemCondition[] var4, LootItemFunction[] var5) {
         MinecraftKey var6 = new MinecraftKey(ChatDeserializer.h(var0, "name"));
         return new LootSelectorDynamic(var6, var2, var3, var4, var5);
      }
   }
}
