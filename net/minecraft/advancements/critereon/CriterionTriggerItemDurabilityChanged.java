package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.item.ItemStack;

public class CriterionTriggerItemDurabilityChanged extends CriterionTriggerAbstract<CriterionTriggerItemDurabilityChanged.a> {
   static final MinecraftKey a = new MinecraftKey("item_durability_changed");

   @Override
   public MinecraftKey a() {
      return a;
   }

   public CriterionTriggerItemDurabilityChanged.a a(JsonObject var0, CriterionConditionEntity.b var1, LootDeserializationContext var2) {
      CriterionConditionItem var3 = CriterionConditionItem.a(var0.get("item"));
      CriterionConditionValue.IntegerRange var4 = CriterionConditionValue.IntegerRange.a(var0.get("durability"));
      CriterionConditionValue.IntegerRange var5 = CriterionConditionValue.IntegerRange.a(var0.get("delta"));
      return new CriterionTriggerItemDurabilityChanged.a(var1, var3, var4, var5);
   }

   public void a(EntityPlayer var0, ItemStack var1, int var2) {
      this.a(var0, var2x -> var2x.a(var1, var2));
   }

   public static class a extends CriterionInstanceAbstract {
      private final CriterionConditionItem a;
      private final CriterionConditionValue.IntegerRange b;
      private final CriterionConditionValue.IntegerRange c;

      public a(
         CriterionConditionEntity.b var0, CriterionConditionItem var1, CriterionConditionValue.IntegerRange var2, CriterionConditionValue.IntegerRange var3
      ) {
         super(CriterionTriggerItemDurabilityChanged.a, var0);
         this.a = var1;
         this.b = var2;
         this.c = var3;
      }

      public static CriterionTriggerItemDurabilityChanged.a a(CriterionConditionItem var0, CriterionConditionValue.IntegerRange var1) {
         return a(CriterionConditionEntity.b.a, var0, var1);
      }

      public static CriterionTriggerItemDurabilityChanged.a a(
         CriterionConditionEntity.b var0, CriterionConditionItem var1, CriterionConditionValue.IntegerRange var2
      ) {
         return new CriterionTriggerItemDurabilityChanged.a(var0, var1, var2, CriterionConditionValue.IntegerRange.e);
      }

      public boolean a(ItemStack var0, int var1) {
         if (!this.a.a(var0)) {
            return false;
         } else if (!this.b.d(var0.k() - var1)) {
            return false;
         } else {
            return this.c.d(var0.j() - var1);
         }
      }

      @Override
      public JsonObject a(LootSerializationContext var0) {
         JsonObject var1 = super.a(var0);
         var1.add("item", this.a.a());
         var1.add("durability", this.b.d());
         var1.add("delta", this.c.d());
         return var1;
      }
   }
}
