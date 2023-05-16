package net.minecraft.world.level.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameter;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class LootItemFunctionSetCount extends LootItemFunctionConditional {
   final NumberProvider a;
   final boolean b;

   LootItemFunctionSetCount(LootItemCondition[] var0, NumberProvider var1, boolean var2) {
      super(var0);
      this.a = var1;
      this.b = var2;
   }

   @Override
   public LootItemFunctionType a() {
      return LootItemFunctions.b;
   }

   @Override
   public Set<LootContextParameter<?>> b() {
      return this.a.b();
   }

   @Override
   public ItemStack a(ItemStack var0, LootTableInfo var1) {
      int var2 = this.b ? var0.K() : 0;
      var0.f(MathHelper.a(var2 + this.a.a(var1), 0, var0.f()));
      return var0;
   }

   public static LootItemFunctionConditional.a<?> a(NumberProvider var0) {
      return a(var1 -> new LootItemFunctionSetCount(var1, var0, false));
   }

   public static LootItemFunctionConditional.a<?> a(NumberProvider var0, boolean var1) {
      return a(var2 -> new LootItemFunctionSetCount(var2, var0, var1));
   }

   public static class a extends LootItemFunctionConditional.c<LootItemFunctionSetCount> {
      public void a(JsonObject var0, LootItemFunctionSetCount var1, JsonSerializationContext var2) {
         super.a(var0, var1, var2);
         var0.add("count", var2.serialize(var1.a));
         var0.addProperty("add", var1.b);
      }

      public LootItemFunctionSetCount a(JsonObject var0, JsonDeserializationContext var1, LootItemCondition[] var2) {
         NumberProvider var3 = ChatDeserializer.a(var0, "count", var1, NumberProvider.class);
         boolean var4 = ChatDeserializer.a(var0, "add", false);
         return new LootItemFunctionSetCount(var2, var3, var4);
      }
   }
}
