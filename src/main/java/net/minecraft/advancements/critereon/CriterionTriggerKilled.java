package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootTableInfo;

public class CriterionTriggerKilled extends CriterionTriggerAbstract<CriterionTriggerKilled.a> {
   final MinecraftKey a;

   public CriterionTriggerKilled(MinecraftKey var0) {
      this.a = var0;
   }

   @Override
   public MinecraftKey a() {
      return this.a;
   }

   public CriterionTriggerKilled.a a(JsonObject var0, CriterionConditionEntity.b var1, LootDeserializationContext var2) {
      return new CriterionTriggerKilled.a(
         this.a, var1, CriterionConditionEntity.b.a(var0, "entity", var2), CriterionConditionDamageSource.a(var0.get("killing_blow"))
      );
   }

   public void a(EntityPlayer var0, Entity var1, DamageSource var2) {
      LootTableInfo var3 = CriterionConditionEntity.b(var0, var1);
      this.a(var0, var3x -> var3x.a(var0, var3, var2));
   }

   public static class a extends CriterionInstanceAbstract {
      private final CriterionConditionEntity.b a;
      private final CriterionConditionDamageSource b;

      public a(MinecraftKey var0, CriterionConditionEntity.b var1, CriterionConditionEntity.b var2, CriterionConditionDamageSource var3) {
         super(var0, var1);
         this.a = var2;
         this.b = var3;
      }

      public static CriterionTriggerKilled.a a(CriterionConditionEntity var0) {
         return new CriterionTriggerKilled.a(
            CriterionTriggers.b.a, CriterionConditionEntity.b.a, CriterionConditionEntity.b.a(var0), CriterionConditionDamageSource.a
         );
      }

      public static CriterionTriggerKilled.a a(CriterionConditionEntity.a var0) {
         return new CriterionTriggerKilled.a(
            CriterionTriggers.b.a, CriterionConditionEntity.b.a, CriterionConditionEntity.b.a(var0.b()), CriterionConditionDamageSource.a
         );
      }

      public static CriterionTriggerKilled.a c() {
         return new CriterionTriggerKilled.a(
            CriterionTriggers.b.a, CriterionConditionEntity.b.a, CriterionConditionEntity.b.a, CriterionConditionDamageSource.a
         );
      }

      public static CriterionTriggerKilled.a a(CriterionConditionEntity var0, CriterionConditionDamageSource var1) {
         return new CriterionTriggerKilled.a(CriterionTriggers.b.a, CriterionConditionEntity.b.a, CriterionConditionEntity.b.a(var0), var1);
      }

      public static CriterionTriggerKilled.a a(CriterionConditionEntity.a var0, CriterionConditionDamageSource var1) {
         return new CriterionTriggerKilled.a(CriterionTriggers.b.a, CriterionConditionEntity.b.a, CriterionConditionEntity.b.a(var0.b()), var1);
      }

      public static CriterionTriggerKilled.a a(CriterionConditionEntity var0, CriterionConditionDamageSource.a var1) {
         return new CriterionTriggerKilled.a(CriterionTriggers.b.a, CriterionConditionEntity.b.a, CriterionConditionEntity.b.a(var0), var1.b());
      }

      public static CriterionTriggerKilled.a a(CriterionConditionEntity.a var0, CriterionConditionDamageSource.a var1) {
         return new CriterionTriggerKilled.a(CriterionTriggers.b.a, CriterionConditionEntity.b.a, CriterionConditionEntity.b.a(var0.b()), var1.b());
      }

      public static CriterionTriggerKilled.a d() {
         return new CriterionTriggerKilled.a(
            CriterionTriggers.W.a, CriterionConditionEntity.b.a, CriterionConditionEntity.b.a, CriterionConditionDamageSource.a
         );
      }

      public static CriterionTriggerKilled.a b(CriterionConditionEntity var0) {
         return new CriterionTriggerKilled.a(
            CriterionTriggers.c.a, CriterionConditionEntity.b.a, CriterionConditionEntity.b.a(var0), CriterionConditionDamageSource.a
         );
      }

      public static CriterionTriggerKilled.a b(CriterionConditionEntity.a var0) {
         return new CriterionTriggerKilled.a(
            CriterionTriggers.c.a, CriterionConditionEntity.b.a, CriterionConditionEntity.b.a(var0.b()), CriterionConditionDamageSource.a
         );
      }

      public static CriterionTriggerKilled.a e() {
         return new CriterionTriggerKilled.a(
            CriterionTriggers.c.a, CriterionConditionEntity.b.a, CriterionConditionEntity.b.a, CriterionConditionDamageSource.a
         );
      }

      public static CriterionTriggerKilled.a b(CriterionConditionEntity var0, CriterionConditionDamageSource var1) {
         return new CriterionTriggerKilled.a(CriterionTriggers.c.a, CriterionConditionEntity.b.a, CriterionConditionEntity.b.a(var0), var1);
      }

      public static CriterionTriggerKilled.a b(CriterionConditionEntity.a var0, CriterionConditionDamageSource var1) {
         return new CriterionTriggerKilled.a(CriterionTriggers.c.a, CriterionConditionEntity.b.a, CriterionConditionEntity.b.a(var0.b()), var1);
      }

      public static CriterionTriggerKilled.a b(CriterionConditionEntity var0, CriterionConditionDamageSource.a var1) {
         return new CriterionTriggerKilled.a(CriterionTriggers.c.a, CriterionConditionEntity.b.a, CriterionConditionEntity.b.a(var0), var1.b());
      }

      public static CriterionTriggerKilled.a b(CriterionConditionEntity.a var0, CriterionConditionDamageSource.a var1) {
         return new CriterionTriggerKilled.a(CriterionTriggers.c.a, CriterionConditionEntity.b.a, CriterionConditionEntity.b.a(var0.b()), var1.b());
      }

      public boolean a(EntityPlayer var0, LootTableInfo var1, DamageSource var2) {
         return !this.b.a(var0, var2) ? false : this.a.a(var1);
      }

      @Override
      public JsonObject a(LootSerializationContext var0) {
         JsonObject var1 = super.a(var0);
         var1.add("entity", this.a.a(var0));
         var1.add("killing_blow", this.b.a());
         return var1;
      }
   }
}
