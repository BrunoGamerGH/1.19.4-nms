package net.minecraft.server.packs.metadata;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;

public interface MetadataSectionType<T> extends ResourcePackMetaParser<T> {
   JsonObject a(T var1);

   static <T> MetadataSectionType<T> a(final String var0, final Codec<T> var1) {
      return new MetadataSectionType<T>() {
         @Override
         public String a() {
            return var0;
         }

         @Override
         public T a(JsonObject var0x) {
            return (T)var1.parse(JsonOps.INSTANCE, var0).getOrThrow(false, var0xx -> {
            });
         }

         @Override
         public JsonObject a(T var0x) {
            return ((JsonElement)var1.encodeStart(JsonOps.INSTANCE, var0).getOrThrow(false, var0xx -> {
            })).getAsJsonObject();
         }
      };
   }
}
