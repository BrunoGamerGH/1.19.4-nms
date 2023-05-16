package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.animal.EntityAnimal;
import net.minecraft.world.level.storage.loot.LootTableInfo;

public class CriterionTriggerBredAnimals extends CriterionTriggerAbstract<CriterionTriggerBredAnimals.a> {
   static final MinecraftKey a = new MinecraftKey("bred_animals");

   @Override
   public MinecraftKey a() {
      return a;
   }

   public CriterionTriggerBredAnimals.a a(JsonObject var0, CriterionConditionEntity.b var1, LootDeserializationContext var2) {
      CriterionConditionEntity.b var3 = CriterionConditionEntity.b.a(var0, "parent", var2);
      CriterionConditionEntity.b var4 = CriterionConditionEntity.b.a(var0, "partner", var2);
      CriterionConditionEntity.b var5 = CriterionConditionEntity.b.a(var0, "child", var2);
      return new CriterionTriggerBredAnimals.a(var1, var3, var4, var5);
   }

   public void a(EntityPlayer var0, EntityAnimal var1, EntityAnimal var2, @Nullable EntityAgeable var3) {
      LootTableInfo var4 = CriterionConditionEntity.b(var0, var1);
      LootTableInfo var5 = CriterionConditionEntity.b(var0, var2);
      LootTableInfo var6 = var3 != null ? CriterionConditionEntity.b(var0, var3) : null;
      this.a(var0, var3x -> var3x.a(var4, var5, var6));
   }

   public static class a extends CriterionInstanceAbstract {
      private final CriterionConditionEntity.b a;
      private final CriterionConditionEntity.b b;
      private final CriterionConditionEntity.b c;

      public a(CriterionConditionEntity.b var0, CriterionConditionEntity.b var1, CriterionConditionEntity.b var2, CriterionConditionEntity.b var3) {
         super(CriterionTriggerBredAnimals.a, var0);
         this.a = var1;
         this.b = var2;
         this.c = var3;
      }

      public static CriterionTriggerBredAnimals.a c() {
         return new CriterionTriggerBredAnimals.a(
            CriterionConditionEntity.b.a, CriterionConditionEntity.b.a, CriterionConditionEntity.b.a, CriterionConditionEntity.b.a
         );
      }

      public static CriterionTriggerBredAnimals.a a(CriterionConditionEntity.a var0) {
         return new CriterionTriggerBredAnimals.a(
            CriterionConditionEntity.b.a, CriterionConditionEntity.b.a, CriterionConditionEntity.b.a, CriterionConditionEntity.b.a(var0.b())
         );
      }

      public static CriterionTriggerBredAnimals.a a(CriterionConditionEntity var0, CriterionConditionEntity var1, CriterionConditionEntity var2) {
         return new CriterionTriggerBredAnimals.a(
            CriterionConditionEntity.b.a, CriterionConditionEntity.b.a(var0), CriterionConditionEntity.b.a(var1), CriterionConditionEntity.b.a(var2)
         );
      }

      public boolean a(LootTableInfo var0, LootTableInfo var1, @Nullable LootTableInfo var2) {
         if (this.c == CriterionConditionEntity.b.a || var2 != null && this.c.a(var2)) {
            return this.a.a(var0) && this.b.a(var1) || this.a.a(var1) && this.b.a(var0);
         } else {
            return false;
         }
      }

      @Override
      public JsonObject a(LootSerializationContext var0) {
         JsonObject var1 = super.a(var0);
         var1.add("parent", this.a.a(var0));
         var1.add("partner", this.b.a(var0));
         var1.add("child", this.c.a(var0));
         return var1;
      }
   }
}
