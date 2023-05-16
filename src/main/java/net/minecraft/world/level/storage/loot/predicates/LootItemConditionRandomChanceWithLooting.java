package net.minecraft.world.level.storage.loot.predicates;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.level.storage.loot.LootSerializer;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameter;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;

public class LootItemConditionRandomChanceWithLooting implements LootItemCondition {
   final float a;
   final float b;

   LootItemConditionRandomChanceWithLooting(float f, float f1) {
      this.a = f;
      this.b = f1;
   }

   @Override
   public LootItemConditionType a() {
      return LootItemConditions.d;
   }

   @Override
   public Set<LootContextParameter<?>> b() {
      return ImmutableSet.of(LootContextParameters.d);
   }

   public boolean a(LootTableInfo loottableinfo) {
      Entity entity = loottableinfo.c(LootContextParameters.d);
      int i = 0;
      if (entity instanceof EntityLiving) {
         i = EnchantmentManager.h((EntityLiving)entity);
      }

      if (loottableinfo.a(LootContextParameters.LOOTING_MOD)) {
         i = loottableinfo.c(LootContextParameters.LOOTING_MOD);
      }

      return loottableinfo.a().i() < this.a + (float)i * this.b;
   }

   public static LootItemCondition.a a(float f, float f1) {
      return () -> new LootItemConditionRandomChanceWithLooting(f, f1);
   }

   public static class a implements LootSerializer<LootItemConditionRandomChanceWithLooting> {
      public void a(
         JsonObject jsonobject,
         LootItemConditionRandomChanceWithLooting lootitemconditionrandomchancewithlooting,
         JsonSerializationContext jsonserializationcontext
      ) {
         jsonobject.addProperty("chance", lootitemconditionrandomchancewithlooting.a);
         jsonobject.addProperty("looting_multiplier", lootitemconditionrandomchancewithlooting.b);
      }

      public LootItemConditionRandomChanceWithLooting b(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext) {
         return new LootItemConditionRandomChanceWithLooting(ChatDeserializer.l(jsonobject, "chance"), ChatDeserializer.l(jsonobject, "looting_multiplier"));
      }
   }
}
