package net.minecraft.util;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;

public class ChatTypeAdapterFactory implements TypeAdapterFactory {
   @Nullable
   public <T> TypeAdapter<T> create(Gson var0, TypeToken<T> var1) {
      Class<T> var2 = var1.getRawType();
      if (!var2.isEnum()) {
         return null;
      } else {
         final Map<String, T> var3 = Maps.newHashMap();

         for(T var7 : var2.getEnumConstants()) {
            var3.put(this.a(var7), var7);
         }

         return new TypeAdapter<T>() {
            public void write(JsonWriter var0, T var1) throws IOException {
               if (var1 == null) {
                  var0.nullValue();
               } else {
                  var0.value(ChatTypeAdapterFactory.this.a(var1));
               }
            }

            @Nullable
            public T read(JsonReader var0) throws IOException {
               if (var0.peek() == JsonToken.NULL) {
                  var0.nextNull();
                  return null;
               } else {
                  return var3.get(var0.nextString());
               }
            }
         };
      }
   }

   String a(Object var0) {
      return var0 instanceof Enum ? ((Enum)var0).name().toLowerCase(Locale.ROOT) : var0.toString().toLowerCase(Locale.ROOT);
   }
}
