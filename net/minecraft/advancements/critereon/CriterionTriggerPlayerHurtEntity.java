package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootTableInfo;

public class CriterionTriggerPlayerHurtEntity extends CriterionTriggerAbstract<CriterionTriggerPlayerHurtEntity.a> {
   static final MinecraftKey a = new MinecraftKey("player_hurt_entity");

   @Override
   public MinecraftKey a() {
      return a;
   }

   public CriterionTriggerPlayerHurtEntity.a a(JsonObject var0, CriterionConditionEntity.b var1, LootDeserializationContext var2) {
      CriterionConditionDamage var3 = CriterionConditionDamage.a(var0.get("damage"));
      CriterionConditionEntity.b var4 = CriterionConditionEntity.b.a(var0, "entity", var2);
      return new CriterionTriggerPlayerHurtEntity.a(var1, var3, var4);
   }

   public void a(EntityPlayer var0, Entity var1, DamageSource var2, float var3, float var4, boolean var5) {
      LootTableInfo var6 = CriterionConditionEntity.b(var0, var1);
      this.a(var0, var6x -> var6x.a(var0, var6, var2, var3, var4, var5));
   }

   public static class a extends CriterionInstanceAbstract {
      private final CriterionConditionDamage a;
      private final CriterionConditionEntity.b b;

      public a(CriterionConditionEntity.b var0, CriterionConditionDamage var1, CriterionConditionEntity.b var2) {
         super(CriterionTriggerPlayerHurtEntity.a, var0);
         this.a = var1;
         this.b = var2;
      }

      public static CriterionTriggerPlayerHurtEntity.a c() {
         return new CriterionTriggerPlayerHurtEntity.a(CriterionConditionEntity.b.a, CriterionConditionDamage.a, CriterionConditionEntity.b.a);
      }

      public static CriterionTriggerPlayerHurtEntity.a a(CriterionConditionDamage var0) {
         return new CriterionTriggerPlayerHurtEntity.a(CriterionConditionEntity.b.a, var0, CriterionConditionEntity.b.a);
      }

      public static CriterionTriggerPlayerHurtEntity.a a(CriterionConditionDamage.a var0) {
         return new CriterionTriggerPlayerHurtEntity.a(CriterionConditionEntity.b.a, var0.b(), CriterionConditionEntity.b.a);
      }

      public static CriterionTriggerPlayerHurtEntity.a a(CriterionConditionEntity var0) {
         return new CriterionTriggerPlayerHurtEntity.a(CriterionConditionEntity.b.a, CriterionConditionDamage.a, CriterionConditionEntity.b.a(var0));
      }

      public static CriterionTriggerPlayerHurtEntity.a a(CriterionConditionDamage var0, CriterionConditionEntity var1) {
         return new CriterionTriggerPlayerHurtEntity.a(CriterionConditionEntity.b.a, var0, CriterionConditionEntity.b.a(var1));
      }

      public static CriterionTriggerPlayerHurtEntity.a a(CriterionConditionDamage.a var0, CriterionConditionEntity var1) {
         return new CriterionTriggerPlayerHurtEntity.a(CriterionConditionEntity.b.a, var0.b(), CriterionConditionEntity.b.a(var1));
      }

      public boolean a(EntityPlayer var0, LootTableInfo var1, DamageSource var2, float var3, float var4, boolean var5) {
         if (!this.a.a(var0, var2, var3, var4, var5)) {
            return false;
         } else {
            return this.b.a(var1);
         }
      }

      @Override
      public JsonObject a(LootSerializationContext var0) {
         JsonObject var1 = super.a(var0);
         var1.add("damage", this.a.a());
         var1.add("entity", this.b.a(var0));
         return var1;
      }
   }
}
