package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.Items;

public class CriterionConditionEntityEquipment {
   public static final CriterionConditionEntityEquipment a = new CriterionConditionEntityEquipment(
      CriterionConditionItem.a,
      CriterionConditionItem.a,
      CriterionConditionItem.a,
      CriterionConditionItem.a,
      CriterionConditionItem.a,
      CriterionConditionItem.a
   );
   public static final CriterionConditionEntityEquipment b = new CriterionConditionEntityEquipment(
      CriterionConditionItem.a.a().a(Items.tR).a(Raid.s().u()).b(),
      CriterionConditionItem.a,
      CriterionConditionItem.a,
      CriterionConditionItem.a,
      CriterionConditionItem.a,
      CriterionConditionItem.a
   );
   private final CriterionConditionItem c;
   private final CriterionConditionItem d;
   private final CriterionConditionItem e;
   private final CriterionConditionItem f;
   private final CriterionConditionItem g;
   private final CriterionConditionItem h;

   public CriterionConditionEntityEquipment(
      CriterionConditionItem var0,
      CriterionConditionItem var1,
      CriterionConditionItem var2,
      CriterionConditionItem var3,
      CriterionConditionItem var4,
      CriterionConditionItem var5
   ) {
      this.c = var0;
      this.d = var1;
      this.e = var2;
      this.f = var3;
      this.g = var4;
      this.h = var5;
   }

   public boolean a(@Nullable Entity var0) {
      if (this == a) {
         return true;
      } else if (!(var0 instanceof EntityLiving)) {
         return false;
      } else {
         EntityLiving var1 = (EntityLiving)var0;
         if (!this.c.a(var1.c(EnumItemSlot.f))) {
            return false;
         } else if (!this.d.a(var1.c(EnumItemSlot.e))) {
            return false;
         } else if (!this.e.a(var1.c(EnumItemSlot.d))) {
            return false;
         } else if (!this.f.a(var1.c(EnumItemSlot.c))) {
            return false;
         } else if (!this.g.a(var1.c(EnumItemSlot.a))) {
            return false;
         } else {
            return this.h.a(var1.c(EnumItemSlot.b));
         }
      }
   }

   public static CriterionConditionEntityEquipment a(@Nullable JsonElement var0) {
      if (var0 != null && !var0.isJsonNull()) {
         JsonObject var1 = ChatDeserializer.m(var0, "equipment");
         CriterionConditionItem var2 = CriterionConditionItem.a(var1.get("head"));
         CriterionConditionItem var3 = CriterionConditionItem.a(var1.get("chest"));
         CriterionConditionItem var4 = CriterionConditionItem.a(var1.get("legs"));
         CriterionConditionItem var5 = CriterionConditionItem.a(var1.get("feet"));
         CriterionConditionItem var6 = CriterionConditionItem.a(var1.get("mainhand"));
         CriterionConditionItem var7 = CriterionConditionItem.a(var1.get("offhand"));
         return new CriterionConditionEntityEquipment(var2, var3, var4, var5, var6, var7);
      } else {
         return a;
      }
   }

   public JsonElement a() {
      if (this == a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject var0 = new JsonObject();
         var0.add("head", this.c.a());
         var0.add("chest", this.d.a());
         var0.add("legs", this.e.a());
         var0.add("feet", this.f.a());
         var0.add("mainhand", this.g.a());
         var0.add("offhand", this.h.a());
         return var0;
      }
   }

   public static class a {
      private CriterionConditionItem a;
      private CriterionConditionItem b;
      private CriterionConditionItem c;
      private CriterionConditionItem d;
      private CriterionConditionItem e;
      private CriterionConditionItem f;

      public a() {
         this.a = CriterionConditionItem.a;
         this.b = CriterionConditionItem.a;
         this.c = CriterionConditionItem.a;
         this.d = CriterionConditionItem.a;
         this.e = CriterionConditionItem.a;
         this.f = CriterionConditionItem.a;
      }

      public static CriterionConditionEntityEquipment.a a() {
         return new CriterionConditionEntityEquipment.a();
      }

      public CriterionConditionEntityEquipment.a a(CriterionConditionItem var0) {
         this.a = var0;
         return this;
      }

      public CriterionConditionEntityEquipment.a b(CriterionConditionItem var0) {
         this.b = var0;
         return this;
      }

      public CriterionConditionEntityEquipment.a c(CriterionConditionItem var0) {
         this.c = var0;
         return this;
      }

      public CriterionConditionEntityEquipment.a d(CriterionConditionItem var0) {
         this.d = var0;
         return this;
      }

      public CriterionConditionEntityEquipment.a e(CriterionConditionItem var0) {
         this.e = var0;
         return this;
      }

      public CriterionConditionEntityEquipment.a f(CriterionConditionItem var0) {
         this.f = var0;
         return this;
      }

      public CriterionConditionEntityEquipment b() {
         return new CriterionConditionEntityEquipment(this.a, this.b, this.c, this.d, this.e, this.f);
      }
   }
}
