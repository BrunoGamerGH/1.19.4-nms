package net.minecraft.world.level.storage.loot.predicates;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.level.storage.loot.LootCollector;
import net.minecraft.world.level.storage.loot.LootSerializer;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import org.slf4j.Logger;

public class LootItemConditionReference implements LootItemCondition {
   private static final Logger a = LogUtils.getLogger();
   final MinecraftKey b;

   LootItemConditionReference(MinecraftKey var0) {
      this.b = var0;
   }

   @Override
   public LootItemConditionType a() {
      return LootItemConditions.o;
   }

   @Override
   public void a(LootCollector var0) {
      if (var0.b(this.b)) {
         var0.a("Condition " + this.b + " is recursively called");
      } else {
         LootItemCondition.super.a(var0);
         LootItemCondition var1 = var0.d(this.b);
         if (var1 == null) {
            var0.a("Unknown condition table called " + this.b);
         } else {
            var1.a(var0.a(".{" + this.b + "}", this.b));
         }
      }
   }

   public boolean a(LootTableInfo var0) {
      LootItemCondition var1 = var0.b(this.b);
      if (var1 == null) {
         a.warn("Tried using unknown condition table called {}", this.b);
         return false;
      } else if (var0.a(var1)) {
         boolean var3;
         try {
            var3 = var1.test(var0);
         } finally {
            var0.b(var1);
         }

         return var3;
      } else {
         a.warn("Detected infinite loop in loot tables");
         return false;
      }
   }

   public static LootItemCondition.a a(MinecraftKey var0) {
      return () -> new LootItemConditionReference(var0);
   }

   public static class a implements LootSerializer<LootItemConditionReference> {
      public void a(JsonObject var0, LootItemConditionReference var1, JsonSerializationContext var2) {
         var0.addProperty("name", var1.b.toString());
      }

      public LootItemConditionReference b(JsonObject var0, JsonDeserializationContext var1) {
         MinecraftKey var2 = new MinecraftKey(ChatDeserializer.h(var0, "name"));
         return new LootItemConditionReference(var2);
      }
   }
}
