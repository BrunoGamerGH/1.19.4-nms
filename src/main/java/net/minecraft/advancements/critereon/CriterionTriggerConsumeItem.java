package net.minecraft.advancements.critereon;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonObject;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.IMaterial;

public class CriterionTriggerConsumeItem extends CriterionTriggerAbstract<CriterionTriggerConsumeItem.a> {
   static final MinecraftKey a = new MinecraftKey("consume_item");

   @Override
   public MinecraftKey a() {
      return a;
   }

   public CriterionTriggerConsumeItem.a a(JsonObject var0, CriterionConditionEntity.b var1, LootDeserializationContext var2) {
      return new CriterionTriggerConsumeItem.a(var1, CriterionConditionItem.a(var0.get("item")));
   }

   public void a(EntityPlayer var0, ItemStack var1) {
      this.a(var0, var1x -> var1x.a(var1));
   }

   public static class a extends CriterionInstanceAbstract {
      private final CriterionConditionItem a;

      public a(CriterionConditionEntity.b var0, CriterionConditionItem var1) {
         super(CriterionTriggerConsumeItem.a, var0);
         this.a = var1;
      }

      public static CriterionTriggerConsumeItem.a c() {
         return new CriterionTriggerConsumeItem.a(CriterionConditionEntity.b.a, CriterionConditionItem.a);
      }

      public static CriterionTriggerConsumeItem.a a(CriterionConditionItem var0) {
         return new CriterionTriggerConsumeItem.a(CriterionConditionEntity.b.a, var0);
      }

      public static CriterionTriggerConsumeItem.a a(IMaterial var0) {
         return new CriterionTriggerConsumeItem.a(
            CriterionConditionEntity.b.a,
            new CriterionConditionItem(
               null,
               ImmutableSet.of(var0.k()),
               CriterionConditionValue.IntegerRange.e,
               CriterionConditionValue.IntegerRange.e,
               CriterionConditionEnchantments.b,
               CriterionConditionEnchantments.b,
               null,
               CriterionConditionNBT.a
            )
         );
      }

      public boolean a(ItemStack var0) {
         return this.a.a(var0);
      }

      @Override
      public JsonObject a(LootSerializationContext var0) {
         JsonObject var1 = super.a(var0);
         var1.add("item", this.a.a());
         return var1;
      }
   }
}
