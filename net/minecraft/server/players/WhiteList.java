package net.minecraft.server.players;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.io.File;
import java.util.Objects;

public class WhiteList extends JsonList<GameProfile, WhiteListEntry> {
   public WhiteList(File var0) {
      super(var0);
   }

   @Override
   protected JsonListEntry<GameProfile> a(JsonObject var0) {
      return new WhiteListEntry(var0);
   }

   public boolean a(GameProfile var0) {
      return this.d(var0);
   }

   @Override
   public String[] a() {
      return this.d().stream().map(JsonListEntry::g).filter(Objects::nonNull).map(GameProfile::getName).toArray(var0 -> new String[var0]);
   }

   protected String b(GameProfile var0) {
      return var0.getId().toString();
   }
}
