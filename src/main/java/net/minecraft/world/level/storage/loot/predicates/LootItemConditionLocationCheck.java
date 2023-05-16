package net.minecraft.world.level.storage.loot.predicates;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.advancements.critereon.CriterionConditionLocation;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.level.storage.loot.LootSerializer;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;
import net.minecraft.world.phys.Vec3D;

public class LootItemConditionLocationCheck implements LootItemCondition {
   final CriterionConditionLocation a;
   final BlockPosition b;

   LootItemConditionLocationCheck(CriterionConditionLocation var0, BlockPosition var1) {
      this.a = var0;
      this.b = var1;
   }

   @Override
   public LootItemConditionType a() {
      return LootItemConditions.m;
   }

   public boolean a(LootTableInfo var0) {
      Vec3D var1 = var0.c(LootContextParameters.f);
      return var1 != null && this.a.a(var0.c(), var1.a() + (double)this.b.u(), var1.b() + (double)this.b.v(), var1.c() + (double)this.b.w());
   }

   public static LootItemCondition.a a(CriterionConditionLocation.a var0) {
      return () -> new LootItemConditionLocationCheck(var0.b(), BlockPosition.b);
   }

   public static LootItemCondition.a a(CriterionConditionLocation.a var0, BlockPosition var1) {
      return () -> new LootItemConditionLocationCheck(var0.b(), var1);
   }

   public static class a implements LootSerializer<LootItemConditionLocationCheck> {
      public void a(JsonObject var0, LootItemConditionLocationCheck var1, JsonSerializationContext var2) {
         var0.add("predicate", var1.a.a());
         if (var1.b.u() != 0) {
            var0.addProperty("offsetX", var1.b.u());
         }

         if (var1.b.v() != 0) {
            var0.addProperty("offsetY", var1.b.v());
         }

         if (var1.b.w() != 0) {
            var0.addProperty("offsetZ", var1.b.w());
         }
      }

      public LootItemConditionLocationCheck b(JsonObject var0, JsonDeserializationContext var1) {
         CriterionConditionLocation var2 = CriterionConditionLocation.a(var0.get("predicate"));
         int var3 = ChatDeserializer.a(var0, "offsetX", 0);
         int var4 = ChatDeserializer.a(var0, "offsetY", 0);
         int var5 = ChatDeserializer.a(var0, "offsetZ", 0);
         return new LootItemConditionLocationCheck(var2, new BlockPosition(var3, var4, var5));
      }
   }
}
