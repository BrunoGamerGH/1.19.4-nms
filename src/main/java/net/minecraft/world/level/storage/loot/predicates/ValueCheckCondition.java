package net.minecraft.world.level.storage.loot.predicates;

import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootSerializer;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameter;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class ValueCheckCondition implements LootItemCondition {
   final NumberProvider a;
   final IntRange b;

   ValueCheckCondition(NumberProvider var0, IntRange var1) {
      this.a = var0;
      this.b = var1;
   }

   @Override
   public LootItemConditionType a() {
      return LootItemConditions.q;
   }

   @Override
   public Set<LootContextParameter<?>> b() {
      return Sets.union(this.a.b(), this.b.a());
   }

   public boolean a(LootTableInfo var0) {
      return this.b.b(var0, this.a.a(var0));
   }

   public static LootItemCondition.a a(NumberProvider var0, IntRange var1) {
      return () -> new ValueCheckCondition(var0, var1);
   }

   public static class a implements LootSerializer<ValueCheckCondition> {
      public void a(JsonObject var0, ValueCheckCondition var1, JsonSerializationContext var2) {
         var0.add("value", var2.serialize(var1.a));
         var0.add("range", var2.serialize(var1.b));
      }

      public ValueCheckCondition b(JsonObject var0, JsonDeserializationContext var1) {
         NumberProvider var2 = ChatDeserializer.a(var0, "value", var1, NumberProvider.class);
         IntRange var3 = ChatDeserializer.a(var0, "range", var1, IntRange.class);
         return new ValueCheckCondition(var2, var3);
      }
   }
}
