package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootTableInfo;

public class PickedUpItemTrigger extends CriterionTriggerAbstract<PickedUpItemTrigger.a> {
   private final MinecraftKey a;

   public PickedUpItemTrigger(MinecraftKey var0) {
      this.a = var0;
   }

   @Override
   public MinecraftKey a() {
      return this.a;
   }

   protected PickedUpItemTrigger.a a(JsonObject var0, CriterionConditionEntity.b var1, LootDeserializationContext var2) {
      CriterionConditionItem var3 = CriterionConditionItem.a(var0.get("item"));
      CriterionConditionEntity.b var4 = CriterionConditionEntity.b.a(var0, "entity", var2);
      return new PickedUpItemTrigger.a(this.a, var1, var3, var4);
   }

   public void a(EntityPlayer var0, ItemStack var1, @Nullable Entity var2) {
      LootTableInfo var3 = CriterionConditionEntity.b(var0, var2);
      this.a(var0, var3x -> var3x.a(var0, var1, var3));
   }

   public static class a extends CriterionInstanceAbstract {
      private final CriterionConditionItem a;
      private final CriterionConditionEntity.b b;

      public a(MinecraftKey var0, CriterionConditionEntity.b var1, CriterionConditionItem var2, CriterionConditionEntity.b var3) {
         super(var0, var1);
         this.a = var2;
         this.b = var3;
      }

      public static PickedUpItemTrigger.a a(CriterionConditionEntity.b var0, CriterionConditionItem var1, CriterionConditionEntity.b var2) {
         return new PickedUpItemTrigger.a(CriterionTriggers.O.a(), var0, var1, var2);
      }

      public static PickedUpItemTrigger.a b(CriterionConditionEntity.b var0, CriterionConditionItem var1, CriterionConditionEntity.b var2) {
         return new PickedUpItemTrigger.a(CriterionTriggers.P.a(), var0, var1, var2);
      }

      public boolean a(EntityPlayer var0, ItemStack var1, LootTableInfo var2) {
         if (!this.a.a(var1)) {
            return false;
         } else {
            return this.b.a(var2);
         }
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
