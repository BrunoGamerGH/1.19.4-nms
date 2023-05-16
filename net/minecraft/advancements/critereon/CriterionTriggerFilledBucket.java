package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.item.ItemStack;

public class CriterionTriggerFilledBucket extends CriterionTriggerAbstract<CriterionTriggerFilledBucket.a> {
   static final MinecraftKey a = new MinecraftKey("filled_bucket");

   @Override
   public MinecraftKey a() {
      return a;
   }

   public CriterionTriggerFilledBucket.a a(JsonObject var0, CriterionConditionEntity.b var1, LootDeserializationContext var2) {
      CriterionConditionItem var3 = CriterionConditionItem.a(var0.get("item"));
      return new CriterionTriggerFilledBucket.a(var1, var3);
   }

   public void a(EntityPlayer var0, ItemStack var1) {
      this.a(var0, var1x -> var1x.a(var1));
   }

   public static class a extends CriterionInstanceAbstract {
      private final CriterionConditionItem a;

      public a(CriterionConditionEntity.b var0, CriterionConditionItem var1) {
         super(CriterionTriggerFilledBucket.a, var0);
         this.a = var1;
      }

      public static CriterionTriggerFilledBucket.a a(CriterionConditionItem var0) {
         return new CriterionTriggerFilledBucket.a(CriterionConditionEntity.b.a, var0);
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
