package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootTableInfo;

public class CriterionTriggerSummonedEntity extends CriterionTriggerAbstract<CriterionTriggerSummonedEntity.a> {
   static final MinecraftKey a = new MinecraftKey("summoned_entity");

   @Override
   public MinecraftKey a() {
      return a;
   }

   public CriterionTriggerSummonedEntity.a a(JsonObject var0, CriterionConditionEntity.b var1, LootDeserializationContext var2) {
      CriterionConditionEntity.b var3 = CriterionConditionEntity.b.a(var0, "entity", var2);
      return new CriterionTriggerSummonedEntity.a(var1, var3);
   }

   public void a(EntityPlayer var0, Entity var1) {
      LootTableInfo var2 = CriterionConditionEntity.b(var0, var1);
      this.a(var0, var1x -> var1x.a(var2));
   }

   public static class a extends CriterionInstanceAbstract {
      private final CriterionConditionEntity.b a;

      public a(CriterionConditionEntity.b var0, CriterionConditionEntity.b var1) {
         super(CriterionTriggerSummonedEntity.a, var0);
         this.a = var1;
      }

      public static CriterionTriggerSummonedEntity.a a(CriterionConditionEntity.a var0) {
         return new CriterionTriggerSummonedEntity.a(CriterionConditionEntity.b.a, CriterionConditionEntity.b.a(var0.b()));
      }

      public boolean a(LootTableInfo var0) {
         return this.a.a(var0);
      }

      @Override
      public JsonObject a(LootSerializationContext var0) {
         JsonObject var1 = super.a(var0);
         var1.add("entity", this.a.a(var0));
         return var1;
      }
   }
}
