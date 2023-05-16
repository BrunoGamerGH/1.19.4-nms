package net.minecraft.world.level.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameter;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class LootItemFunctionLimitCount extends LootItemFunctionConditional {
   final IntRange a;

   LootItemFunctionLimitCount(LootItemCondition[] var0, IntRange var1) {
      super(var0);
      this.a = var1;
   }

   @Override
   public LootItemFunctionType a() {
      return LootItemFunctions.p;
   }

   @Override
   public Set<LootContextParameter<?>> b() {
      return this.a.a();
   }

   @Override
   public ItemStack a(ItemStack var0, LootTableInfo var1) {
      int var2 = this.a.a(var1, var0.K());
      var0.f(var2);
      return var0;
   }

   public static LootItemFunctionConditional.a<?> a(IntRange var0) {
      return a(var1 -> new LootItemFunctionLimitCount(var1, var0));
   }

   public static class a extends LootItemFunctionConditional.c<LootItemFunctionLimitCount> {
      public void a(JsonObject var0, LootItemFunctionLimitCount var1, JsonSerializationContext var2) {
         super.a(var0, var1, var2);
         var0.add("limit", var2.serialize(var1.a));
      }

      public LootItemFunctionLimitCount a(JsonObject var0, JsonDeserializationContext var1, LootItemCondition[] var2) {
         IntRange var3 = ChatDeserializer.a(var0, "limit", var1, IntRange.class);
         return new LootItemFunctionLimitCount(var2, var3);
      }
   }
}
