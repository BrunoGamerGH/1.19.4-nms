package net.minecraft.world.level.storage.loot.predicates;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.level.storage.loot.LootCollector;
import net.minecraft.world.level.storage.loot.LootSerializer;
import net.minecraft.world.level.storage.loot.LootTableInfo;

public class LootItemConditionAlternative implements LootItemCondition {
   final LootItemCondition[] a;
   private final Predicate<LootTableInfo> b;

   LootItemConditionAlternative(LootItemCondition[] var0) {
      this.a = var0;
      this.b = LootItemConditions.b(var0);
   }

   @Override
   public LootItemConditionType a() {
      return LootItemConditions.b;
   }

   public final boolean a(LootTableInfo var0) {
      return this.b.test(var0);
   }

   @Override
   public void a(LootCollector var0) {
      LootItemCondition.super.a(var0);

      for(int var1 = 0; var1 < this.a.length; ++var1) {
         this.a[var1].a(var0.b(".term[" + var1 + "]"));
      }
   }

   public static LootItemConditionAlternative.a a(LootItemCondition.a... var0) {
      return new LootItemConditionAlternative.a(var0);
   }

   public static class a implements LootItemCondition.a {
      private final List<LootItemCondition> a = Lists.newArrayList();

      public a(LootItemCondition.a... var0) {
         for(LootItemCondition.a var4 : var0) {
            this.a.add(var4.build());
         }
      }

      @Override
      public LootItemConditionAlternative.a or(LootItemCondition.a var0) {
         this.a.add(var0.build());
         return this;
      }

      @Override
      public LootItemCondition build() {
         return new LootItemConditionAlternative(this.a.toArray(new LootItemCondition[0]));
      }
   }

   public static class b implements LootSerializer<LootItemConditionAlternative> {
      public void a(JsonObject var0, LootItemConditionAlternative var1, JsonSerializationContext var2) {
         var0.add("terms", var2.serialize(var1.a));
      }

      public LootItemConditionAlternative b(JsonObject var0, JsonDeserializationContext var1) {
         LootItemCondition[] var2 = (LootItemCondition[])ChatDeserializer.a(var0, "terms", var1, LootItemCondition[].class);
         return new LootItemConditionAlternative(var2);
      }
   }
}
