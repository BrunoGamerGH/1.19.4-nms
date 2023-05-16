package net.minecraft.world.level.storage.loot.predicates;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import javax.annotation.Nullable;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.level.storage.loot.LootSerializer;
import net.minecraft.world.level.storage.loot.LootTableInfo;

public class LootItemConditionWeatherCheck implements LootItemCondition {
   @Nullable
   final Boolean a;
   @Nullable
   final Boolean b;

   LootItemConditionWeatherCheck(@Nullable Boolean var0, @Nullable Boolean var1) {
      this.a = var0;
      this.b = var1;
   }

   @Override
   public LootItemConditionType a() {
      return LootItemConditions.n;
   }

   public boolean a(LootTableInfo var0) {
      WorldServer var1 = var0.c();
      if (this.a != null && this.a != var1.Y()) {
         return false;
      } else {
         return this.b == null || this.b == var1.X();
      }
   }

   public static LootItemConditionWeatherCheck.a c() {
      return new LootItemConditionWeatherCheck.a();
   }

   public static class a implements LootItemCondition.a {
      @Nullable
      private Boolean a;
      @Nullable
      private Boolean b;

      public LootItemConditionWeatherCheck.a a(@Nullable Boolean var0) {
         this.a = var0;
         return this;
      }

      public LootItemConditionWeatherCheck.a b(@Nullable Boolean var0) {
         this.b = var0;
         return this;
      }

      public LootItemConditionWeatherCheck a() {
         return new LootItemConditionWeatherCheck(this.a, this.b);
      }
   }

   public static class b implements LootSerializer<LootItemConditionWeatherCheck> {
      public void a(JsonObject var0, LootItemConditionWeatherCheck var1, JsonSerializationContext var2) {
         var0.addProperty("raining", var1.a);
         var0.addProperty("thundering", var1.b);
      }

      public LootItemConditionWeatherCheck b(JsonObject var0, JsonDeserializationContext var1) {
         Boolean var2 = var0.has("raining") ? ChatDeserializer.j(var0, "raining") : null;
         Boolean var3 = var0.has("thundering") ? ChatDeserializer.j(var0, "thundering") : null;
         return new LootItemConditionWeatherCheck(var2, var3);
      }
   }
}
