package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.animal.EntityAnimal;
import net.minecraft.world.level.storage.loot.LootTableInfo;

public class CriterionTriggerTamedAnimal extends CriterionTriggerAbstract<CriterionTriggerTamedAnimal.a> {
   static final MinecraftKey a = new MinecraftKey("tame_animal");

   @Override
   public MinecraftKey a() {
      return a;
   }

   public CriterionTriggerTamedAnimal.a a(JsonObject var0, CriterionConditionEntity.b var1, LootDeserializationContext var2) {
      CriterionConditionEntity.b var3 = CriterionConditionEntity.b.a(var0, "entity", var2);
      return new CriterionTriggerTamedAnimal.a(var1, var3);
   }

   public void a(EntityPlayer var0, EntityAnimal var1) {
      LootTableInfo var2 = CriterionConditionEntity.b(var0, var1);
      this.a(var0, var1x -> var1x.a(var2));
   }

   public static class a extends CriterionInstanceAbstract {
      private final CriterionConditionEntity.b a;

      public a(CriterionConditionEntity.b var0, CriterionConditionEntity.b var1) {
         super(CriterionTriggerTamedAnimal.a, var0);
         this.a = var1;
      }

      public static CriterionTriggerTamedAnimal.a c() {
         return new CriterionTriggerTamedAnimal.a(CriterionConditionEntity.b.a, CriterionConditionEntity.b.a);
      }

      public static CriterionTriggerTamedAnimal.a a(CriterionConditionEntity var0) {
         return new CriterionTriggerTamedAnimal.a(CriterionConditionEntity.b.a, CriterionConditionEntity.b.a(var0));
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
