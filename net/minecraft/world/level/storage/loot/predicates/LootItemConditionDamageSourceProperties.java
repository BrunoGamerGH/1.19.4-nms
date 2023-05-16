package net.minecraft.world.level.storage.loot.predicates;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.advancements.critereon.CriterionConditionDamageSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.storage.loot.LootSerializer;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameter;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;
import net.minecraft.world.phys.Vec3D;

public class LootItemConditionDamageSourceProperties implements LootItemCondition {
   final CriterionConditionDamageSource a;

   LootItemConditionDamageSourceProperties(CriterionConditionDamageSource var0) {
      this.a = var0;
   }

   @Override
   public LootItemConditionType a() {
      return LootItemConditions.l;
   }

   @Override
   public Set<LootContextParameter<?>> b() {
      return ImmutableSet.of(LootContextParameters.f, LootContextParameters.c);
   }

   public boolean a(LootTableInfo var0) {
      DamageSource var1 = var0.c(LootContextParameters.c);
      Vec3D var2 = var0.c(LootContextParameters.f);
      return var2 != null && var1 != null && this.a.a(var0.c(), var2, var1);
   }

   public static LootItemCondition.a a(CriterionConditionDamageSource.a var0) {
      return () -> new LootItemConditionDamageSourceProperties(var0.b());
   }

   public static class a implements LootSerializer<LootItemConditionDamageSourceProperties> {
      public void a(JsonObject var0, LootItemConditionDamageSourceProperties var1, JsonSerializationContext var2) {
         var0.add("predicate", var1.a.a());
      }

      public LootItemConditionDamageSourceProperties b(JsonObject var0, JsonDeserializationContext var1) {
         CriterionConditionDamageSource var2 = CriterionConditionDamageSource.a(var0.get("predicate"));
         return new LootItemConditionDamageSourceProperties(var2);
      }
   }
}
