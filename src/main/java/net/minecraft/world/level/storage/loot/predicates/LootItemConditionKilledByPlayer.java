package net.minecraft.world.level.storage.loot.predicates;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.world.level.storage.loot.LootSerializer;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameter;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;

public class LootItemConditionKilledByPlayer implements LootItemCondition {
   static final LootItemConditionKilledByPlayer a = new LootItemConditionKilledByPlayer();

   private LootItemConditionKilledByPlayer() {
   }

   @Override
   public LootItemConditionType a() {
      return LootItemConditions.f;
   }

   @Override
   public Set<LootContextParameter<?>> b() {
      return ImmutableSet.of(LootContextParameters.b);
   }

   public boolean a(LootTableInfo var0) {
      return var0.a(LootContextParameters.b);
   }

   public static LootItemCondition.a c() {
      return () -> a;
   }

   public static class a implements LootSerializer<LootItemConditionKilledByPlayer> {
      public void a(JsonObject var0, LootItemConditionKilledByPlayer var1, JsonSerializationContext var2) {
      }

      public LootItemConditionKilledByPlayer b(JsonObject var0, JsonDeserializationContext var1) {
         return LootItemConditionKilledByPlayer.a;
      }
   }
}
