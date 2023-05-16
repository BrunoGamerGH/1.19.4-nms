package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.item.ItemStack;

public class CriterionTriggerEnchantedItem extends CriterionTriggerAbstract<CriterionTriggerEnchantedItem.a> {
   static final MinecraftKey a = new MinecraftKey("enchanted_item");

   @Override
   public MinecraftKey a() {
      return a;
   }

   public CriterionTriggerEnchantedItem.a a(JsonObject var0, CriterionConditionEntity.b var1, LootDeserializationContext var2) {
      CriterionConditionItem var3 = CriterionConditionItem.a(var0.get("item"));
      CriterionConditionValue.IntegerRange var4 = CriterionConditionValue.IntegerRange.a(var0.get("levels"));
      return new CriterionTriggerEnchantedItem.a(var1, var3, var4);
   }

   public void a(EntityPlayer var0, ItemStack var1, int var2) {
      this.a(var0, var2x -> var2x.a(var1, var2));
   }

   public static class a extends CriterionInstanceAbstract {
      private final CriterionConditionItem a;
      private final CriterionConditionValue.IntegerRange b;

      public a(CriterionConditionEntity.b var0, CriterionConditionItem var1, CriterionConditionValue.IntegerRange var2) {
         super(CriterionTriggerEnchantedItem.a, var0);
         this.a = var1;
         this.b = var2;
      }

      public static CriterionTriggerEnchantedItem.a c() {
         return new CriterionTriggerEnchantedItem.a(CriterionConditionEntity.b.a, CriterionConditionItem.a, CriterionConditionValue.IntegerRange.e);
      }

      public boolean a(ItemStack var0, int var1) {
         if (!this.a.a(var0)) {
            return false;
         } else {
            return this.b.d(var1);
         }
      }

      @Override
      public JsonObject a(LootSerializationContext var0) {
         JsonObject var1 = super.a(var0);
         var1.add("item", this.a.a());
         var1.add("levels", this.b.d());
         return var1;
      }
   }
}
