package net.minecraft.world.level.storage.loot.predicates;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.level.storage.loot.LootSerializer;
import net.minecraft.world.level.storage.loot.LootTableInfo;

public class LootItemConditionRandomChance implements LootItemCondition {
   final float a;

   LootItemConditionRandomChance(float var0) {
      this.a = var0;
   }

   @Override
   public LootItemConditionType a() {
      return LootItemConditions.c;
   }

   public boolean a(LootTableInfo var0) {
      return var0.a().i() < this.a;
   }

   public static LootItemCondition.a a(float var0) {
      return () -> new LootItemConditionRandomChance(var0);
   }

   public static class a implements LootSerializer<LootItemConditionRandomChance> {
      public void a(JsonObject var0, LootItemConditionRandomChance var1, JsonSerializationContext var2) {
         var0.addProperty("chance", var1.a);
      }

      public LootItemConditionRandomChance b(JsonObject var0, JsonDeserializationContext var1) {
         return new LootItemConditionRandomChance(ChatDeserializer.l(var0, "chance"));
      }
   }
}
