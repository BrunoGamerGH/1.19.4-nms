package net.minecraft.world.level.storage.loot.predicates;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.advancements.critereon.CriterionConditionItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootSerializer;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameter;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;

public class LootItemConditionMatchTool implements LootItemCondition {
   final CriterionConditionItem a;

   public LootItemConditionMatchTool(CriterionConditionItem var0) {
      this.a = var0;
   }

   @Override
   public LootItemConditionType a() {
      return LootItemConditions.i;
   }

   @Override
   public Set<LootContextParameter<?>> b() {
      return ImmutableSet.of(LootContextParameters.i);
   }

   public boolean a(LootTableInfo var0) {
      ItemStack var1 = var0.c(LootContextParameters.i);
      return var1 != null && this.a.a(var1);
   }

   public static LootItemCondition.a a(CriterionConditionItem.a var0) {
      return () -> new LootItemConditionMatchTool(var0.b());
   }

   public static class a implements LootSerializer<LootItemConditionMatchTool> {
      public void a(JsonObject var0, LootItemConditionMatchTool var1, JsonSerializationContext var2) {
         var0.add("predicate", var1.a.a());
      }

      public LootItemConditionMatchTool b(JsonObject var0, JsonDeserializationContext var1) {
         CriterionConditionItem var2 = CriterionConditionItem.a(var0.get("predicate"));
         return new LootItemConditionMatchTool(var2);
      }
   }
}
