package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.phys.Vec3D;

public class CriterionTriggerTargetHit extends CriterionTriggerAbstract<CriterionTriggerTargetHit.a> {
   static final MinecraftKey a = new MinecraftKey("target_hit");

   @Override
   public MinecraftKey a() {
      return a;
   }

   public CriterionTriggerTargetHit.a a(JsonObject var0, CriterionConditionEntity.b var1, LootDeserializationContext var2) {
      CriterionConditionValue.IntegerRange var3 = CriterionConditionValue.IntegerRange.a(var0.get("signal_strength"));
      CriterionConditionEntity.b var4 = CriterionConditionEntity.b.a(var0, "projectile", var2);
      return new CriterionTriggerTargetHit.a(var1, var3, var4);
   }

   public void a(EntityPlayer var0, Entity var1, Vec3D var2, int var3) {
      LootTableInfo var4 = CriterionConditionEntity.b(var0, var1);
      this.a(var0, var3x -> var3x.a(var4, var2, var3));
   }

   public static class a extends CriterionInstanceAbstract {
      private final CriterionConditionValue.IntegerRange a;
      private final CriterionConditionEntity.b b;

      public a(CriterionConditionEntity.b var0, CriterionConditionValue.IntegerRange var1, CriterionConditionEntity.b var2) {
         super(CriterionTriggerTargetHit.a, var0);
         this.a = var1;
         this.b = var2;
      }

      public static CriterionTriggerTargetHit.a a(CriterionConditionValue.IntegerRange var0, CriterionConditionEntity.b var1) {
         return new CriterionTriggerTargetHit.a(CriterionConditionEntity.b.a, var0, var1);
      }

      @Override
      public JsonObject a(LootSerializationContext var0) {
         JsonObject var1 = super.a(var0);
         var1.add("signal_strength", this.a.d());
         var1.add("projectile", this.b.a(var0));
         return var1;
      }

      public boolean a(LootTableInfo var0, Vec3D var1, int var2) {
         if (!this.a.d(var2)) {
            return false;
         } else {
            return this.b.a(var0);
         }
      }
   }
}
