package net.minecraft.world.level.storage.loot.predicates;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootSerializer;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameter;

public class LootItemConditionTimeCheck implements LootItemCondition {
   @Nullable
   final Long a;
   final IntRange b;

   LootItemConditionTimeCheck(@Nullable Long var0, IntRange var1) {
      this.a = var0;
      this.b = var1;
   }

   @Override
   public LootItemConditionType a() {
      return LootItemConditions.p;
   }

   @Override
   public Set<LootContextParameter<?>> b() {
      return this.b.a();
   }

   public boolean a(LootTableInfo var0) {
      WorldServer var1 = var0.c();
      long var2 = var1.V();
      if (this.a != null) {
         var2 %= this.a;
      }

      return this.b.b(var0, (int)var2);
   }

   public static LootItemConditionTimeCheck.a a(IntRange var0) {
      return new LootItemConditionTimeCheck.a(var0);
   }

   public static class a implements LootItemCondition.a {
      @Nullable
      private Long a;
      private final IntRange b;

      public a(IntRange var0) {
         this.b = var0;
      }

      public LootItemConditionTimeCheck.a a(long var0) {
         this.a = var0;
         return this;
      }

      public LootItemConditionTimeCheck a() {
         return new LootItemConditionTimeCheck(this.a, this.b);
      }
   }

   public static class b implements LootSerializer<LootItemConditionTimeCheck> {
      public void a(JsonObject var0, LootItemConditionTimeCheck var1, JsonSerializationContext var2) {
         var0.addProperty("period", var1.a);
         var0.add("value", var2.serialize(var1.b));
      }

      public LootItemConditionTimeCheck b(JsonObject var0, JsonDeserializationContext var1) {
         Long var2 = var0.has("period") ? ChatDeserializer.m(var0, "period") : null;
         IntRange var3 = ChatDeserializer.a(var0, "value", var1, IntRange.class);
         return new LootItemConditionTimeCheck(var2, var3);
      }
   }
}
