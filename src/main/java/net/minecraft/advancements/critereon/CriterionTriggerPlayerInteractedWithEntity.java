package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootTableInfo;

public class CriterionTriggerPlayerInteractedWithEntity extends CriterionTriggerAbstract<CriterionTriggerPlayerInteractedWithEntity.a> {
   static final MinecraftKey a = new MinecraftKey("player_interacted_with_entity");

   @Override
   public MinecraftKey a() {
      return a;
   }

   protected CriterionTriggerPlayerInteractedWithEntity.a a(JsonObject var0, CriterionConditionEntity.b var1, LootDeserializationContext var2) {
      CriterionConditionItem var3 = CriterionConditionItem.a(var0.get("item"));
      CriterionConditionEntity.b var4 = CriterionConditionEntity.b.a(var0, "entity", var2);
      return new CriterionTriggerPlayerInteractedWithEntity.a(var1, var3, var4);
   }

   public void a(EntityPlayer var0, ItemStack var1, Entity var2) {
      LootTableInfo var3 = CriterionConditionEntity.b(var0, var2);
      this.a(var0, var2x -> var2x.a(var1, var3));
   }

   public static class a extends CriterionInstanceAbstract {
      private final CriterionConditionItem a;
      private final CriterionConditionEntity.b b;

      public a(CriterionConditionEntity.b var0, CriterionConditionItem var1, CriterionConditionEntity.b var2) {
         super(CriterionTriggerPlayerInteractedWithEntity.a, var0);
         this.a = var1;
         this.b = var2;
      }

      public static CriterionTriggerPlayerInteractedWithEntity.a a(
         CriterionConditionEntity.b var0, CriterionConditionItem.a var1, CriterionConditionEntity.b var2
      ) {
         return new CriterionTriggerPlayerInteractedWithEntity.a(var0, var1.b(), var2);
      }

      public static CriterionTriggerPlayerInteractedWithEntity.a a(CriterionConditionItem.a var0, CriterionConditionEntity.b var1) {
         return a(CriterionConditionEntity.b.a, var0, var1);
      }

      public boolean a(ItemStack var0, LootTableInfo var1) {
         return !this.a.a(var0) ? false : this.b.a(var1);
      }

      @Override
      public JsonObject a(LootSerializationContext var0) {
         JsonObject var1 = super.a(var0);
         var1.add("item", this.a.a());
         var1.add("entity", this.b.a(var0));
         return var1;
      }
   }
}
