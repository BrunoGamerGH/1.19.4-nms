package net.minecraft.server.players;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.util.UUID;

public class WhiteListEntry extends JsonListEntry<GameProfile> {
   public WhiteListEntry(GameProfile var0) {
      super(var0);
   }

   public WhiteListEntry(JsonObject var0) {
      super(b(var0));
   }

   @Override
   protected void a(JsonObject var0) {
      if (this.g() != null) {
         var0.addProperty("uuid", this.g().getId() == null ? "" : this.g().getId().toString());
         var0.addProperty("name", this.g().getName());
      }
   }

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
