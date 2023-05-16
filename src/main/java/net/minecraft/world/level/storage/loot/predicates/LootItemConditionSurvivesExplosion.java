package net.minecraft.world.level.storage.loot.predicates;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.storage.loot.LootSerializer;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameter;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;

public class LootItemConditionSurvivesExplosion implements LootItemCondition {
   static final LootItemConditionSurvivesExplosion a = new LootItemConditionSurvivesExplosion();

   private LootItemConditionSurvivesExplosion() {
   }

   @Override
   public LootItemConditionType a() {
      return LootItemConditions.k;
   }

   @Override
   public Set<LootContextParameter<?>> b() {
      return ImmutableSet.of(LootContextParameters.j);
   }

   public boolean a(LootTableInfo loottableinfo) {
      Float ofloat = loottableinfo.c(LootContextParameters.j);
      if (ofloat != null) {
         RandomSource randomsource = loottableinfo.a();
         float f = 1.0F / ofloat;
         return randomsource.i() < f;
      } else {
         return true;
      }
   }

   public static LootItemCondition.a c() {
      return () -> a;
   }

   public static class a implements LootSerializer<LootItemConditionSurvivesExplosion> {
      public void a(
         JsonObject jsonobject, LootItemConditionSurvivesExplosion lootitemconditionsurvivesexplosion, JsonSerializationContext jsonserializationcontext
      ) {
      }

      public LootItemConditionSurvivesExplosion b(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext) {
         return LootItemConditionSurvivesExplosion.a;
      }
   }
}
