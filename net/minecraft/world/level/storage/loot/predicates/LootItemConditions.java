package net.minecraft.world.level.storage.loot.predicates;

import java.util.function.Predicate;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.level.storage.loot.JsonRegistry;
import net.minecraft.world.level.storage.loot.LootSerializer;

public class LootItemConditions {
   public static final LootItemConditionType a = a("inverted", new LootItemConditionInverted.a());
   public static final LootItemConditionType b = a("alternative", new LootItemConditionAlternative.b());
   public static final LootItemConditionType c = a("random_chance", new LootItemConditionRandomChance.a());
   public static final LootItemConditionType d = a("random_chance_with_looting", new LootItemConditionRandomChanceWithLooting.a());
   public static final LootItemConditionType e = a("entity_properties", new LootItemConditionEntityProperty.a());
   public static final LootItemConditionType f = a("killed_by_player", new LootItemConditionKilledByPlayer.a());
   public static final LootItemConditionType g = a("entity_scores", new LootItemConditionEntityScore.b());
   public static final LootItemConditionType h = a("block_state_property", new LootItemConditionBlockStateProperty.b());
   public static final LootItemConditionType i = a("match_tool", new LootItemConditionMatchTool.a());
   public static final LootItemConditionType j = a("table_bonus", new LootItemConditionTableBonus.a());
   public static final LootItemConditionType k = a("survives_explosion", new LootItemConditionSurvivesExplosion.a());
   public static final LootItemConditionType l = a("damage_source_properties", new LootItemConditionDamageSourceProperties.a());
   public static final LootItemConditionType m = a("location_check", new LootItemConditionLocationCheck.a());
   public static final LootItemConditionType n = a("weather_check", new LootItemConditionWeatherCheck.b());
   public static final LootItemConditionType o = a("reference", new LootItemConditionReference.a());
   public static final LootItemConditionType p = a("time_check", new LootItemConditionTimeCheck.b());
   public static final LootItemConditionType q = a("value_check", new ValueCheckCondition.a());

   private static LootItemConditionType a(String var0, LootSerializer<? extends LootItemCondition> var1) {
      return IRegistry.a(BuiltInRegistries.H, new MinecraftKey(var0), new LootItemConditionType(var1));
   }

   public static Object a() {
      return JsonRegistry.a(BuiltInRegistries.H, "condition", "condition", LootItemCondition::a).a();
   }

   public static <T> Predicate<T> a(Predicate<T>[] var0) {
      switch(var0.length) {
         case 0:
            return var0x -> true;
         case 1:
            return var0[0];
         case 2:
            return var0[0].and(var0[1]);
         default:
            return var1 -> {
               for(Predicate<T> var5 : var0) {
                  if (!var5.test(var1)) {
                     return false;
                  }
               }

               return true;
            };
      }
   }

   public static <T> Predicate<T> b(Predicate<T>[] var0) {
      switch(var0.length) {
         case 0:
            return var0x -> false;
         case 1:
            return var0[0];
         case 2:
            return var0[0].or(var0[1]);
         default:
            return var1 -> {
               for(Predicate<T> var5 : var0) {
                  if (var5.test(var1)) {
                     return true;
                  }
               }

               return false;
            };
      }
   }
}
