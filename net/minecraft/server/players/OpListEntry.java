package net.minecraft.server.players;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import javax.annotation.Nullable;

public class OpListEntry extends JsonListEntry<GameProfile> {
   private final int a;
   private final boolean b;

   public OpListEntry(GameProfile var0, int var1, boolean var2) {
      super(var0);
      this.a = var1;
      this.b = var2;
   }

   public OpListEntry(JsonObject var0) {
      super(b(var0));
      this.a = var0.has("level") ? var0.get("level").getAsInt() : 0;
      this.b = var0.has("bypassesPlayerLimit") && var0.get("bypassesPlayerLimit").getAsBoolean();
   }

   public int a() {
      return this.a;
   }

   public boolean b() {
      return this.b;
   }

   @Override
   protected void a(JsonObject var0) {
      if (this.g() != null) {
         var0.addProperty("uuid", this.g().getId() == null ? "" : this.g().getId().toString());
         var0.addProperty("name", this.g().getName());
         var0.addProperty("level", this.a);
         var0.addProperty("bypassesPlayerLimit", this.b);
      }
   }

   @Nullable
   private static GameProfile b(JsonObject var0) {
      if (var0.has("uuid") && var0.has("name")) {
         String var1 = var0.get("uuid").getAsString();

         UUID var2;
         try {
            var2 = UUID.fromString(var1);
         } catch (Throwable var4) {
            return null;
         }

         return new GameProfile(var2, var0.get("name").getAsString());
      } else {
         return null;
      }
   }
}
