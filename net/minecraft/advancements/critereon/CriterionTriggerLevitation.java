package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.phys.Vec3D;

public class CriterionTriggerLevitation extends CriterionTriggerAbstract<CriterionTriggerLevitation.a> {
   static final MinecraftKey a = new MinecraftKey("levitation");

   @Override
   public MinecraftKey a() {
      return a;
   }

   public CriterionTriggerLevitation.a a(JsonObject var0, CriterionConditionEntity.b var1, LootDeserializationContext var2) {
      CriterionConditionDistance var3 = CriterionConditionDistance.a(var0.get("distance"));
      CriterionConditionValue.IntegerRange var4 = CriterionConditionValue.IntegerRange.a(var0.get("duration"));
      return new CriterionTriggerLevitation.a(var1, var3, var4);
   }

   public void a(EntityPlayer var0, Vec3D var1, int var2) {
      this.a(var0, var3x -> var3x.a(var0, var1, var2));
   }

   public static class a extends CriterionInstanceAbstract {
      private final CriterionConditionDistance a;
      private final CriterionConditionValue.IntegerRange b;

      public a(CriterionConditionEntity.b var0, CriterionConditionDistance var1, CriterionConditionValue.IntegerRange var2) {
         super(CriterionTriggerLevitation.a, var0);
         this.a = var1;
         this.b = var2;
      }

      public static CriterionTriggerLevitation.a a(CriterionConditionDistance var0) {
         return new CriterionTriggerLevitation.a(CriterionConditionEntity.b.a, var0, CriterionConditionValue.IntegerRange.e);
      }

      public boolean a(EntityPlayer var0, Vec3D var1, int var2) {
         if (!this.a.a(var1.c, var1.d, var1.e, var0.dl(), var0.dn(), var0.dr())) {
            return false;
         } else {
            return this.b.d(var2);
         }
      }

      @Override
      public JsonObject a(LootSerializationContext var0) {
         JsonObject var1 = super.a(var0);
         var1.add("distance", this.a.a());
         var1.add("duration", this.b.d());
         return var1;
      }
   }
}
