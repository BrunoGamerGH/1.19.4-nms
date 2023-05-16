package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.core.BlockPosition;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;

public class CriterionTriggerUsedEnderEye extends CriterionTriggerAbstract<CriterionTriggerUsedEnderEye.a> {
   static final MinecraftKey a = new MinecraftKey("used_ender_eye");

   @Override
   public MinecraftKey a() {
      return a;
   }

   public CriterionTriggerUsedEnderEye.a a(JsonObject var0, CriterionConditionEntity.b var1, LootDeserializationContext var2) {
      CriterionConditionValue.DoubleRange var3 = CriterionConditionValue.DoubleRange.a(var0.get("distance"));
      return new CriterionTriggerUsedEnderEye.a(var1, var3);
   }

   public void a(EntityPlayer var0, BlockPosition var1) {
      double var2 = var0.dl() - (double)var1.u();
      double var4 = var0.dr() - (double)var1.w();
      double var6 = var2 * var2 + var4 * var4;
      this.a(var0, var2x -> var2x.a(var6));
   }

   public static class a extends CriterionInstanceAbstract {
      private final CriterionConditionValue.DoubleRange a;

      public a(CriterionConditionEntity.b var0, CriterionConditionValue.DoubleRange var1) {
         super(CriterionTriggerUsedEnderEye.a, var0);
         this.a = var1;
      }

      public boolean a(double var0) {
         return this.a.e(var0);
      }
   }
}
