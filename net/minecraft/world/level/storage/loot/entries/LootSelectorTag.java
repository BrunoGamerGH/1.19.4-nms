package net.minecraft.world.level.storage.loot.entries;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.function.Consumer;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class LootSelectorTag extends LootSelectorEntry {
   final TagKey<Item> i;
   final boolean j;

   LootSelectorTag(TagKey<Item> var0, boolean var1, int var2, int var3, LootItemCondition[] var4, LootItemFunction[] var5) {
      super(var2, var3, var4, var5);
      this.i = var0;
      this.j = var1;
   }

   @Override
   public LootEntryType a() {
      return LootEntries.e;
   }

   @Override
   public void a(Consumer<ItemStack> var0, LootTableInfo var1) {
      BuiltInRegistries.i.c(this.i).forEach(var1x -> var0.accept(new ItemStack(var1x)));
   }

   private boolean a(LootTableInfo var0, Consumer<LootEntry> var1) {
      if (!this.a(var0)) {
         return false;
      } else {
         for(final Holder<Item> var3 : BuiltInRegistries.i.c(this.i)) {
            var1.accept(new LootSelectorEntry.c() {
               @Override
               public void a(Consumer<ItemStack> var0, LootTableInfo var1) {
                  var0.accept(new ItemStack(var3));
               }
            });
         }

         return true;
      }
   }

   @Override
   public boolean expand(LootTableInfo var0, Consumer<LootEntry> var1) {
      return this.j ? this.a(var0, var1) : super.expand(var0, var1);
   }

   public static LootSelectorEntry.a<?> a(TagKey<Item> var0) {
      return a((var1, var2, var3, var4) -> new LootSelectorTag(var0, false, var1, var2, var3, var4));
   }

   public static LootSelectorEntry.a<?> b(TagKey<Item> var0) {
      return a((var1, var2, var3, var4) -> new LootSelectorTag(var0, true, var1, var2, var3, var4));
   }

   public static class a extends LootSelectorEntry.e<LootSelectorTag> {
      public void a(JsonObject var0, LootSelectorTag var1, JsonSerializationContext var2) {
         super.a(var0, var1, var2);
         var0.addProperty("name", var1.i.b().toString());
         var0.addProperty("expand", var1.j);
      }

      protected LootSelectorTag a(JsonObject var0, JsonDeserializationContext var1, int var2, int var3, LootItemCondition[] var4, LootItemFunction[] var5) {
         MinecraftKey var6 = new MinecraftKey(ChatDeserializer.h(var0, "name"));
         TagKey<Item> var7 = TagKey.a(Registries.C, var6);
         boolean var8 = ChatDeserializer.j(var0, "expand");
         return new LootSelectorTag(var7, var8, var2, var3, var4, var5);
      }
   }
}
