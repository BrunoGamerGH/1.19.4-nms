package net.minecraft.world.level.storage.loot.providers.number;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.level.storage.loot.JsonRegistry;
import net.minecraft.world.level.storage.loot.LootSerializer;
import net.minecraft.world.level.storage.loot.LootTableInfo;

public final class ConstantValue implements NumberProvider {
   final float a;

   ConstantValue(float var0) {
      this.a = var0;
   }

   @Override
   public LootNumberProviderType a() {
      return NumberProviders.a;
   }

   @Override
   public float b(LootTableInfo var0) {
      return this.a;
   }

   public static ConstantValue a(float var0) {
      return new ConstantValue(var0);
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else if (var0 != null && this.getClass() == var0.getClass()) {
         return Float.compare(((ConstantValue)var0).a, this.a) == 0;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.a != 0.0F ? Float.floatToIntBits(this.a) : 0;
   }

   public static class a implements JsonRegistry.b<ConstantValue> {
      public JsonElement a(ConstantValue var0, JsonSerializationContext var1) {
         return new JsonPrimitive(var0.a);
      }

      public ConstantValue b(JsonElement var0, JsonDeserializationContext var1) {
         return new ConstantValue(ChatDeserializer.e(var0, "value"));
      }
   }

   public static class b implements LootSerializer<ConstantValue> {
      public void a(JsonObject var0, ConstantValue var1, JsonSerializationContext var2) {
         var0.addProperty("value", var1.a);
      }

      public ConstantValue b(JsonObject var0, JsonDeserializationContext var1) {
         float var2 = ChatDeserializer.l(var0, "value");
         return new ConstantValue(var2);
      }
   }
}
