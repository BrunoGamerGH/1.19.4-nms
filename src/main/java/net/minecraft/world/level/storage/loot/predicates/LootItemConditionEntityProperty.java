package net.minecraft.world.level.storage.loot.predicates;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.advancements.critereon.CriterionConditionEntity;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootSerializer;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameter;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;
import net.minecraft.world.phys.Vec3D;

public class LootItemConditionEntityProperty implements LootItemCondition {
   final CriterionConditionEntity a;
   final LootTableInfo.EntityTarget b;

   LootItemConditionEntityProperty(CriterionConditionEntity var0, LootTableInfo.EntityTarget var1) {
      this.a = var0;
      this.b = var1;
   }

   @Override
   public LootItemConditionType a() {
      return LootItemConditions.e;
   }

   @Override
   public Set<LootContextParameter<?>> b() {
      return ImmutableSet.of(LootContextParameters.f, this.b.a());
   }

   public boolean a(LootTableInfo var0) {
      Entity var1 = var0.c(this.b.a());
      Vec3D var2 = var0.c(LootContextParameters.f);
      return this.a.a(var0.c(), var2, var1);
   }

   public static LootItemCondition.a a(LootTableInfo.EntityTarget var0) {
      return a(var0, CriterionConditionEntity.a.a());
   }

   public static LootItemCondition.a a(LootTableInfo.EntityTarget var0, CriterionConditionEntity.a var1) {
      return () -> new LootItemConditionEntityProperty(var1.b(), var0);
   }

   public static LootItemCondition.a a(LootTableInfo.EntityTarget var0, CriterionConditionEntity var1) {
      return () -> new LootItemConditionEntityProperty(var1, var0);
   }

   public static class a implements LootSerializer<LootItemConditionEntityProperty> {
      public void a(JsonObject var0, LootItemConditionEntityProperty var1, JsonSerializationContext var2) {
         var0.add("predicate", var1.a.a());
         var0.add("entity", var2.serialize(var1.b));
      }

      public LootItemConditionEntityProperty b(JsonObject var0, JsonDeserializationContext var1) {
         CriterionConditionEntity var2 = CriterionConditionEntity.a(var0.get("predicate"));
         return new LootItemConditionEntityProperty(var2, ChatDeserializer.a(var0, "entity", var1, LootTableInfo.EntityTarget.class));
      }
   }
}
