package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.npc.EntityVillagerAbstract;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootTableInfo;

public class CriterionTriggerVillagerTrade extends CriterionTriggerAbstract<CriterionTriggerVillagerTrade.a> {
   static final MinecraftKey a = new MinecraftKey("villager_trade");

   @Override
   public MinecraftKey a() {
      return a;
   }

   public CriterionTriggerVillagerTrade.a a(JsonObject var0, CriterionConditionEntity.b var1, LootDeserializationContext var2) {
      CriterionConditionEntity.b var3 = CriterionConditionEntity.b.a(var0, "villager", var2);
      CriterionConditionItem var4 = CriterionConditionItem.a(var0.get("item"));
      return new CriterionTriggerVillagerTrade.a(var1, var3, var4);
   }

   public void a(EntityPlayer var0, EntityVillagerAbstract var1, ItemStack var2) {
      LootTableInfo var3 = CriterionConditionEntity.b(var0, var1);
      this.a(var0, var2x -> var2x.a(var3, var2));
   }

   public static class a extends CriterionInstanceAbstract {
      private final CriterionConditionEntity.b a;
      private final CriterionConditionItem b;

      public a(CriterionConditionEntity.b var0, CriterionConditionEntity.b var1, CriterionConditionItem var2) {
         super(CriterionTriggerVillagerTrade.a, var0);
         this.a = var1;
         this.b = var2;
      }

      public static CriterionTriggerVillagerTrade.a c() {
         return new CriterionTriggerVillagerTrade.a(CriterionConditionEntity.b.a, CriterionConditionEntity.b.a, CriterionConditionItem.a);
      }

      public static CriterionTriggerVillagerTrade.a a(CriterionConditionEntity.a var0) {
         return new CriterionTriggerVillagerTrade.a(CriterionConditionEntity.b.a(var0.b()), CriterionConditionEntity.b.a, CriterionConditionItem.a);
      }

      public boolean a(LootTableInfo var0, ItemStack var1) {
         if (!this.a.a(var0)) {
            return false;
         } else {
            return this.b.a(var1);
         }
      }

      @Override
      public JsonObject a(LootSerializationContext var0) {
         JsonObject var1 = super.a(var0);
         var1.add("item", this.b.a());
         var1.add("villager", this.a.a(var0));
         return var1;
      }
   }
}
