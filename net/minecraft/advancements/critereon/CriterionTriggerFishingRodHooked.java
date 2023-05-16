package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import java.util.Collection;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.projectile.EntityFishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;

public class CriterionTriggerFishingRodHooked extends CriterionTriggerAbstract<CriterionTriggerFishingRodHooked.a> {
   static final MinecraftKey a = new MinecraftKey("fishing_rod_hooked");

   @Override
   public MinecraftKey a() {
      return a;
   }

   public CriterionTriggerFishingRodHooked.a a(JsonObject var0, CriterionConditionEntity.b var1, LootDeserializationContext var2) {
      CriterionConditionItem var3 = CriterionConditionItem.a(var0.get("rod"));
      CriterionConditionEntity.b var4 = CriterionConditionEntity.b.a(var0, "entity", var2);
      CriterionConditionItem var5 = CriterionConditionItem.a(var0.get("item"));
      return new CriterionTriggerFishingRodHooked.a(var1, var3, var4, var5);
   }

   public void a(EntityPlayer var0, ItemStack var1, EntityFishingHook var2, Collection<ItemStack> var3) {
      LootTableInfo var4 = CriterionConditionEntity.b(var0, (Entity)(var2.k() != null ? var2.k() : var2));
      this.a(var0, var3x -> var3x.a(var1, var4, var3));
   }

   public static class a extends CriterionInstanceAbstract {
      private final CriterionConditionItem a;
      private final CriterionConditionEntity.b b;
      private final CriterionConditionItem c;

      public a(CriterionConditionEntity.b var0, CriterionConditionItem var1, CriterionConditionEntity.b var2, CriterionConditionItem var3) {
         super(CriterionTriggerFishingRodHooked.a, var0);
         this.a = var1;
         this.b = var2;
         this.c = var3;
      }

      public static CriterionTriggerFishingRodHooked.a a(CriterionConditionItem var0, CriterionConditionEntity var1, CriterionConditionItem var2) {
         return new CriterionTriggerFishingRodHooked.a(CriterionConditionEntity.b.a, var0, CriterionConditionEntity.b.a(var1), var2);
      }

      public boolean a(ItemStack var0, LootTableInfo var1, Collection<ItemStack> var2) {
         if (!this.a.a(var0)) {
            return false;
         } else if (!this.b.a(var1)) {
            return false;
         } else {
            if (this.c != CriterionConditionItem.a) {
               boolean var3 = false;
               Entity var4 = var1.c(LootContextParameters.a);
               if (var4 instanceof EntityItem var5 && this.c.a(var5.i())) {
                  var3 = true;
               }

               for(ItemStack var6 : var2) {
                  if (this.c.a(var6)) {
                     var3 = true;
                     break;
                  }
               }

               if (!var3) {
                  return false;
               }
            }

            return true;
         }
      }

      @Override
      public JsonObject a(LootSerializationContext var0) {
         JsonObject var1 = super.a(var0);
         var1.add("rod", this.a.a());
         var1.add("entity", this.b.a(var0));
         var1.add("item", this.c.a());
         return var1;
      }
   }
}
