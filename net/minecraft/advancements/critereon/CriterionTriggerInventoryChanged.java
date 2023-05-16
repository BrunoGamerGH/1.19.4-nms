package net.minecraft.advancements.critereon;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.IMaterial;

public class CriterionTriggerInventoryChanged extends CriterionTriggerAbstract<CriterionTriggerInventoryChanged.a> {
   static final MinecraftKey a = new MinecraftKey("inventory_changed");

   @Override
   public MinecraftKey a() {
      return a;
   }

   public CriterionTriggerInventoryChanged.a a(JsonObject var0, CriterionConditionEntity.b var1, LootDeserializationContext var2) {
      JsonObject var3 = ChatDeserializer.a(var0, "slots", new JsonObject());
      CriterionConditionValue.IntegerRange var4 = CriterionConditionValue.IntegerRange.a(var3.get("occupied"));
      CriterionConditionValue.IntegerRange var5 = CriterionConditionValue.IntegerRange.a(var3.get("full"));
      CriterionConditionValue.IntegerRange var6 = CriterionConditionValue.IntegerRange.a(var3.get("empty"));
      CriterionConditionItem[] var7 = CriterionConditionItem.b(var0.get("items"));
      return new CriterionTriggerInventoryChanged.a(var1, var4, var5, var6, var7);
   }

   public void a(EntityPlayer var0, PlayerInventory var1, ItemStack var2) {
      int var3 = 0;
      int var4 = 0;
      int var5 = 0;

      for(int var6 = 0; var6 < var1.b(); ++var6) {
         ItemStack var7 = var1.a(var6);
         if (var7.b()) {
            ++var4;
         } else {
            ++var5;
            if (var7.K() >= var7.f()) {
               ++var3;
            }
         }
      }

      this.a(var0, var1, var2, var3, var4, var5);
   }

   private void a(EntityPlayer var0, PlayerInventory var1, ItemStack var2, int var3, int var4, int var5) {
      this.a(var0, var5x -> var5x.a(var1, var2, var3, var4, var5));
   }

   public static class a extends CriterionInstanceAbstract {
      private final CriterionConditionValue.IntegerRange a;
      private final CriterionConditionValue.IntegerRange b;
      private final CriterionConditionValue.IntegerRange c;
      private final CriterionConditionItem[] d;

      public a(
         CriterionConditionEntity.b var0,
         CriterionConditionValue.IntegerRange var1,
         CriterionConditionValue.IntegerRange var2,
         CriterionConditionValue.IntegerRange var3,
         CriterionConditionItem[] var4
      ) {
         super(CriterionTriggerInventoryChanged.a, var0);
         this.a = var1;
         this.b = var2;
         this.c = var3;
         this.d = var4;
      }

      public static CriterionTriggerInventoryChanged.a a(CriterionConditionItem... var0) {
         return new CriterionTriggerInventoryChanged.a(
            CriterionConditionEntity.b.a,
            CriterionConditionValue.IntegerRange.e,
            CriterionConditionValue.IntegerRange.e,
            CriterionConditionValue.IntegerRange.e,
            var0
         );
      }

      public static CriterionTriggerInventoryChanged.a a(IMaterial... var0) {
         CriterionConditionItem[] var1 = new CriterionConditionItem[var0.length];

         for(int var2 = 0; var2 < var0.length; ++var2) {
            var1[var2] = new CriterionConditionItem(
               null,
               ImmutableSet.of(var0[var2].k()),
               CriterionConditionValue.IntegerRange.e,
               CriterionConditionValue.IntegerRange.e,
               CriterionConditionEnchantments.b,
               CriterionConditionEnchantments.b,
               null,
               CriterionConditionNBT.a
            );
         }

         return a(var1);
      }

      @Override
      public JsonObject a(LootSerializationContext var0) {
         JsonObject var1 = super.a(var0);
         if (!this.a.c() || !this.b.c() || !this.c.c()) {
            JsonObject var2 = new JsonObject();
            var2.add("occupied", this.a.d());
            var2.add("full", this.b.d());
            var2.add("empty", this.c.d());
            var1.add("slots", var2);
         }

         if (this.d.length > 0) {
            JsonArray var2 = new JsonArray();

            for(CriterionConditionItem var6 : this.d) {
               var2.add(var6.a());
            }

            var1.add("items", var2);
         }

         return var1;
      }

      public boolean a(PlayerInventory var0, ItemStack var1, int var2, int var3, int var4) {
         if (!this.b.d(var2)) {
            return false;
         } else if (!this.c.d(var3)) {
            return false;
         } else if (!this.a.d(var4)) {
            return false;
         } else {
            int var5 = this.d.length;
            if (var5 == 0) {
               return true;
            } else if (var5 != 1) {
               List<CriterionConditionItem> var6 = new ObjectArrayList(this.d);
               int var7 = var0.b();

               for(int var8 = 0; var8 < var7; ++var8) {
                  if (var6.isEmpty()) {
                     return true;
                  }

                  ItemStack var9 = var0.a(var8);
                  if (!var9.b()) {
                     var6.removeIf(var1x -> var1x.a(var9));
                  }
               }

               return var6.isEmpty();
            } else {
               return !var1.b() && this.d[0].a(var1);
            }
         }
      }
   }
}
