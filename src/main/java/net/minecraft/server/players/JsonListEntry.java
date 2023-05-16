package net.minecraft.server.players;

import com.google.gson.JsonObject;
import javax.annotation.Nullable;

public abstract class JsonListEntry<T> {
   @Nullable
   private final T a;

   public JsonListEntry(@Nullable T var0) {
      this.a = var0;
   }

   @Nullable
   public T g() {
      return this.a;
   }

   boolean f() {
      return false;
   }

   protected abstract void a(JsonObject var1);
}
