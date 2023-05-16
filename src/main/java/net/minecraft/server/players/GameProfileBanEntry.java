package net.minecraft.server.players;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.network.chat.IChatBaseComponent;

public class GameProfileBanEntry extends ExpirableListEntry<GameProfile> {
   public GameProfileBanEntry(GameProfile gameprofile) {
      this(gameprofile, null, null, null, null);
   }

   public GameProfileBanEntry(GameProfile gameprofile, @Nullable Date date, @Nullable String s, @Nullable Date date1, @Nullable String s1) {
      super(gameprofile, date, s, date1, s1);
   }

   public GameProfileBanEntry(JsonObject jsonobject) {
      super(b(jsonobject), jsonobject);
   }

   @Override
   protected void a(JsonObject jsonobject) {
      if (this.g() != null) {
         jsonobject.addProperty("uuid", this.g().getId() == null ? "" : this.g().getId().toString());
         jsonobject.addProperty("name", this.g().getName());
         super.a(jsonobject);
      }
   }

   @Override
   public IChatBaseComponent e() {
      GameProfile gameprofile = this.g();
      return IChatBaseComponent.b(gameprofile.getName() != null ? gameprofile.getName() : Objects.toString(gameprofile.getId(), "(Unknown)"));
   }

   private static GameProfile b(JsonObject jsonobject) {
      UUID uuid = null;
      String name = null;
      if (jsonobject.has("uuid")) {
         String s = jsonobject.get("uuid").getAsString();

         try {
            uuid = UUID.fromString(s);
         } catch (Throwable var5) {
         }
      }

      if (jsonobject.has("name")) {
         name = jsonobject.get("name").getAsString();
      }

      return uuid == null && name == null ? null : new GameProfile(uuid, name);
   }
}
