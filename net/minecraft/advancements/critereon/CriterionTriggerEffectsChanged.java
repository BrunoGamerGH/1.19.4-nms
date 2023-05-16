package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.level.storage.loot.LootTableInfo;

public class CriterionTriggerEffectsChanged extends CriterionTriggerAbstract<CriterionTriggerEffectsChanged.a> {
   static final MinecraftKey a = new MinecraftKey("effects_changed");

   @Override
   public MinecraftKey a() {
      return a;
   }

   public CriterionTriggerEffectsChanged.a a(JsonObject var0, CriterionConditionEntity.b var1, LootDeserializationContext var2) {
      CriterionConditionMobEffect var3 = CriterionConditionMobEffect.a(var0.get("effects"));
      CriterionConditionEntity.b var4 = CriterionConditionEntity.b.a(var0, "source", var2);
      return new CriterionTriggerEffectsChanged.a(var1, var3, var4);
   }

   public void a(EntityPlayer var0, @Nullable Entity var1) {
      LootTableInfo var2 = var1 != null ? CriterionConditionEntity.b(var0, var1) : null;
      this.a(var0, var2x -> var2x.a(var0, var2));
   }

   public static class a extends CriterionInstanceAbstract {
      private final CriterionConditionMobEffect a;
      private final CriterionConditionEntity.b b;

      public a(CriterionConditionEntity.b var0, CriterionConditionMobEffect var1, CriterionConditionEntity.b var2) {
         super(CriterionTriggerEffectsChanged.a, var0);
         this.a = var1;
         this.b = var2;
      }

      public static CriterionTriggerEffectsChanged.a a(CriterionConditionMobEffect var0) {
         return new CriterionTriggerEffectsChanged.a(CriterionConditionEntity.b.a, var0, CriterionConditionEntity.b.a);
      }

      public static CriterionTriggerEffectsChanged.a a(CriterionConditionEntity var0) {
         return new CriterionTriggerEffectsChanged.a(CriterionConditionEntity.b.a, CriterionConditionMobEffect.a, CriterionConditionEntity.b.a(var0));
      }

      public boolean a(EntityPlayer var0, @Nullable LootTableInfo var1) {
         if (!this.a.a((EntityLiving)var0)) {
            return false;
         } else {
            return this.b == CriterionConditionEntity.b.a || var1 != null && this.b.a(var1);
         }
      }

      @Override
      public JsonObject a(LootSerializationContext var0) {
         JsonObject var1 = super.a(var0);
         var1.add("effects", this.a.b());
         var1.add("source", this.b.a(var0));
         return var1;
      }
   }
}
