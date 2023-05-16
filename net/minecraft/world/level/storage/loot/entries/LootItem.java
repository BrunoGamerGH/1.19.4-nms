package net.minecraft.world.level.storage.loot.entries;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.function.Consumer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.IMaterial;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class LootItem extends LootSelectorEntry {
   final Item i;

   LootItem(Item var0, int var1, int var2, LootItemCondition[] var3, LootItemFunction[] var4) {
      super(var1, var2, var3, var4);
      this.i = var0;
   }

   @Override
   public LootEntryType a() {
      return LootEntries.b;
   }

   @Override
   public void a(Consumer<ItemStack> var0, LootTableInfo var1) {
      var0.accept(new ItemStack(this.i));
   }

   public static LootSelectorEntry.a<?> a(IMaterial var0) {
      return a((var1, var2, var3, var4) -> new LootItem(var0.k(), var1, var2, var3, var4));
   }

   public static class a extends LootSelectorEntry.e<LootItem> {
      public void a(JsonObject var0, LootItem var1, JsonSerializationContext var2) {
         super.a(var0, var1, var2);
         MinecraftKey var3 = BuiltInRegistries.i.b(var1.i);
         if (var3 == null) {
            throw new IllegalArgumentException("Can't serialize unknown item " + var1.i);
         } else {
            var0.addProperty("name", var3.toString());
         }
      }

      protected LootItem a(JsonObject var0, JsonDeserializationContext var1, int var2, int var3, LootItemCondition[] var4, LootItemFunction[] var5) {
         Item var6 = ChatDeserializer.i(var0, "name");
         return new LootItem(var6, var2, var3, var4, var5);
      }
   }
}
