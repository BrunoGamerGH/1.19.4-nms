package net.minecraft.world.level.storage.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.mojang.datafixers.util.Pair;
import java.lang.reflect.Type;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.core.IRegistry;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.ChatDeserializer;

public class JsonRegistry {
   public static <E, T extends LootSerializerType<E>> JsonRegistry.a<E, T> a(IRegistry<T> var0, String var1, String var2, Function<E, T> var3) {
      return new JsonRegistry.a<>(var0, var1, var2, var3);
   }

   public static class a<E, T extends LootSerializerType<E>> {
      private final IRegistry<T> a;
      private final String b;
      private final String c;
      private final Function<E, T> d;
      @Nullable
      private Pair<T, JsonRegistry.b<? extends E>> e;
      @Nullable
      private T f;

      a(IRegistry<T> var0, String var1, String var2, Function<E, T> var3) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
         this.d = var3;
      }

      public JsonRegistry.a<E, T> a(T var0, JsonRegistry.b<? extends E> var1) {
         this.e = Pair.of(var0, var1);
         return this;
      }

      public JsonRegistry.a<E, T> a(T var0) {
         this.f = var0;
         return this;
      }

      public Object a() {
         return new JsonRegistry.c<>(this.a, this.b, this.c, this.d, this.f, this.e);
      }
   }

   public interface b<T> {
      JsonElement a(T var1, JsonSerializationContext var2);

      T a(JsonElement var1, JsonDeserializationContext var2);
   }

   static class c<E, T extends LootSerializerType<E>> implements JsonDeserializer<E>, JsonSerializer<E> {
      private final IRegistry<T> a;
      private final String b;
      private final String c;
      private final Function<E, T> d;
      @Nullable
      private final T e;
      @Nullable
      private final Pair<T, JsonRegistry.b<? extends E>> f;

      c(IRegistry<T> var0, String var1, String var2, Function<E, T> var3, @Nullable T var4, @Nullable Pair<T, JsonRegistry.b<? extends E>> var5) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
         this.d = var3;
         this.e = var4;
         this.f = var5;
      }

      public E deserialize(JsonElement var0, Type var1, JsonDeserializationContext var2) throws JsonParseException {
         if (var0.isJsonObject()) {
            JsonObject var3 = ChatDeserializer.m(var0, this.b);
            String var5 = ChatDeserializer.a(var3, this.c, "");
            T var4;
            if (var5.isEmpty()) {
               var4 = this.e;
            } else {
               MinecraftKey var6 = new MinecraftKey(var5);
               var4 = this.a.a(var6);
            }

            if (var4 == null) {
               throw new JsonSyntaxException("Unknown type '" + var5 + "'");
            } else {
               return var4.a().a(var3, var2);
            }
         } else if (this.f == null) {
            throw new UnsupportedOperationException("Object " + var0 + " can't be deserialized");
         } else {
            return (E)((JsonRegistry.b)this.f.getSecond()).a(var0, var2);
         }
      }

      public JsonElement serialize(E var0, Type var1, JsonSerializationContext var2) {
         T var3 = this.d.apply(var0);
         if (this.f != null && this.f.getFirst() == var3) {
            return ((JsonRegistry.b)this.f.getSecond()).a((T)var0, var2);
         } else if (var3 == null) {
            throw new JsonSyntaxException("Unknown type: " + var0);
         } else {
            JsonObject var4 = new JsonObject();
            var4.addProperty(this.c, this.a.b(var3).toString());
            var3.a().a(var4, var0, var2);
            return var4;
         }
      }
   }
}
