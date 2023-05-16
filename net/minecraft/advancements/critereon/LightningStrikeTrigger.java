package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLightning;
import net.minecraft.world.level.storage.loot.LootTableInfo;

public class LightningStrikeTrigger extends CriterionTriggerAbstract<LightningStrikeTrigger.a> {
   static final MinecraftKey a = new MinecraftKey("lightning_strike");

   @Override
   public MinecraftKey a() {
      return a;
   }

   public LightningStrikeTrigger.a a(JsonObject var0, CriterionConditionEntity.b var1, LootDeserializationContext var2) {
      CriterionConditionEntity.b var3 = CriterionConditionEntity.b.a(var0, "lightning", var2);
      CriterionConditionEntity.b var4 = CriterionConditionEntity.b.a(var0, "bystander", var2);
      return new LightningStrikeTrigger.a(var1, var3, var4);
   }

   public void a(EntityPlayer var0, EntityLightning var1, List<Entity> var2) {
      List<LootTableInfo> var3 = var2.stream().map(var1x -> CriterionConditionEntity.b(var0, var1x)).collect(Collectors.toList());
      LootTableInfo var4 = CriterionConditionEntity.b(var0, var1);
      this.a(var0, var2x -> var2x.a(var4, var3));
   }

   public static class a extends CriterionInstanceAbstract {
      private final CriterionConditionEntity.b a;
      private final CriterionConditionEntity.b b;

      public a(CriterionConditionEntity.b var0, CriterionConditionEntity.b var1, CriterionConditionEntity.b var2) {
         super(LightningStrikeTrigger.a, var0);
         this.a = var1;
         this.b = var2;
      }

      public static LightningStrikeTrigger.a a(CriterionConditionEntity var0, CriterionConditionEntity var1) {
         return new LightningStrikeTrigger.a(CriterionConditionEntity.b.a, CriterionConditionEntity.b.a(var0), CriterionConditionEntity.b.a(var1));
      }

      public boolean a(LootTableInfo var0, List<LootTableInfo> var1) {
         if (!this.a.a(var0)) {
            return false;
         } else {
            return this.b == CriterionConditionEntity.b.a || !var1.stream().noneMatch(this.b::a);
         }
      }

      @Override
      public JsonObject a(LootSerializationContext var0) {
         JsonObject var1 = super.a(var0);
         var1.add("lightning", this.a.a(var0));
         var1.add("bystander", this.b.a(var0));
         return var1;
      }
   }
}
