package net.minecraft.world.level.storage.loot.predicates;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.level.storage.loot.LootCollector;
import net.minecraft.world.level.storage.loot.LootSerializer;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameter;

public class LootItemConditionInverted implements LootItemCondition {
   final LootItemCondition a;

   LootItemConditionInverted(LootItemCondition var0) {
      this.a = var0;
   }

   @Override
   public LootItemConditionType a() {
      return LootItemConditions.a;
   }

   public final boolean a(LootTableInfo var0) {
      return !this.a.test(var0);
   }

   @Override
   public Set<LootContextParameter<?>> b() {
      return this.a.b();
   }

   @Override
   public void a(LootCollector var0) {
      LootItemCondition.super.a(var0);
      this.a.a(var0);
   }

   public static LootItemCondition.a a(LootItemCondition.a var0) {
      LootItemConditionInverted var1 = new LootItemConditionInverted(var0.build());
      return () -> var1;
   }

   public static class a implements LootSerializer<LootItemConditionInverted> {
      public void a(JsonObject var0, LootItemConditionInverted var1, JsonSerializationContext var2) {
         var0.add("term", var2.serialize(var1.a));
      }

      public LootItemConditionInverted b(JsonObject var0, JsonDeserializationContext var1) {
         LootItemCondition var2 = ChatDeserializer.a(var0, "term", var1, LootItemCondition.class);
         return new LootItemConditionInverted(var2);
      }
   }
}
