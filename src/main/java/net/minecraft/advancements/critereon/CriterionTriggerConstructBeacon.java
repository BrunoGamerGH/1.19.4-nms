package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;

public class CriterionTriggerConstructBeacon extends CriterionTriggerAbstract<CriterionTriggerConstructBeacon.a> {
   static final MinecraftKey a = new MinecraftKey("construct_beacon");

   @Override
   public MinecraftKey a() {
      return a;
   }

   public CriterionTriggerConstructBeacon.a a(JsonObject var0, CriterionConditionEntity.b var1, LootDeserializationContext var2) {
      CriterionConditionValue.IntegerRange var3 = CriterionConditionValue.IntegerRange.a(var0.get("level"));
      return new CriterionTriggerConstructBeacon.a(var1, var3);
   }

   public void a(EntityPlayer var0, int var1) {
      this.a(var0, var1x -> var1x.a(var1));
   }

   public static class a extends CriterionInstanceAbstract {
      private final CriterionConditionValue.IntegerRange a;

      public a(CriterionConditionEntity.b var0, CriterionConditionValue.IntegerRange var1) {
         super(CriterionTriggerConstructBeacon.a, var0);
         this.a = var1;
      }

      public static CriterionTriggerConstructBeacon.a c() {
         return new CriterionTriggerConstructBeacon.a(CriterionConditionEntity.b.a, CriterionConditionValue.IntegerRange.e);
      }

      public static CriterionTriggerConstructBeacon.a a(CriterionConditionValue.IntegerRange var0) {
         return new CriterionTriggerConstructBeacon.a(CriterionConditionEntity.b.a, var0);
      }

      public boolean a(int var0) {
         return this.a.d(var0);
      }

      @Override
      public JsonObject a(LootSerializationContext var0) {
         JsonObject var1 = super.a(var0);
         var1.add("level", this.a.d());
         return var1;
      }
   }
}
