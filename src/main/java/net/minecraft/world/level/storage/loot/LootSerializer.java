package net.minecraft.world.level.storage.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

public interface LootSerializer<T> {
   void a(JsonObject var1, T var2, JsonSerializationContext var3);

   T a(JsonObject var1, JsonDeserializationContext var2);
}
