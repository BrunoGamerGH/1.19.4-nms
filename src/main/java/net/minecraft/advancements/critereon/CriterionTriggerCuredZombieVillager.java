package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.monster.EntityZombie;
import net.minecraft.world.entity.npc.EntityVillager;
import net.minecraft.world.level.storage.loot.LootTableInfo;

public class CriterionTriggerCuredZombieVillager extends CriterionTriggerAbstract<CriterionTriggerCuredZombieVillager.a> {
   static final MinecraftKey a = new MinecraftKey("cured_zombie_villager");

   @Override
   public MinecraftKey a() {
      return a;
   }

   public CriterionTriggerCuredZombieVillager.a a(JsonObject var0, CriterionConditionEntity.b var1, LootDeserializationContext var2) {
      CriterionConditionEntity.b var3 = CriterionConditionEntity.b.a(var0, "zombie", var2);
      CriterionConditionEntity.b var4 = CriterionConditionEntity.b.a(var0, "villager", var2);
      return new CriterionTriggerCuredZombieVillager.a(var1, var3, var4);
   }

   public void a(EntityPlayer var0, EntityZombie var1, EntityVillager var2) {
      LootTableInfo var3 = CriterionConditionEntity.b(var0, var1);
      LootTableInfo var4 = CriterionConditionEntity.b(var0, var2);
      this.a(var0, var2x -> var2x.a(var3, var4));
   }

   public static class a extends CriterionInstanceAbstract {
      private final CriterionConditionEntity.b a;
      private final CriterionConditionEntity.b b;

      public a(CriterionConditionEntity.b var0, CriterionConditionEntity.b var1, CriterionConditionEntity.b var2) {
         super(CriterionTriggerCuredZombieVillager.a, var0);
         this.a = var1;
         this.b = var2;
      }

      public static CriterionTriggerCuredZombieVillager.a c() {
         return new CriterionTriggerCuredZombieVillager.a(CriterionConditionEntity.b.a, CriterionConditionEntity.b.a, CriterionConditionEntity.b.a);
      }

      public boolean a(LootTableInfo var0, LootTableInfo var1) {
         if (!this.a.a(var0)) {
            return false;
         } else {
            return this.b.a(var1);
         }
      }

      @Override
      public JsonObject a(LootSerializationContext var0) {
         JsonObject var1 = super.a(var0);
         var1.add("zombie", this.a.a(var0));
         var1.add("villager", this.b.a(var0));
         return var1;
      }
   }
}
