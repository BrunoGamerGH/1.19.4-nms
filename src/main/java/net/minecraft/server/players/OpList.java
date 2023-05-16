package net.minecraft.server.players;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.io.File;
import java.util.Objects;

public class OpList extends JsonList<GameProfile, OpListEntry> {
   public OpList(File var0) {
      super(var0);
   }

   @Override
   protected JsonListEntry<GameProfile> a(JsonObject var0) {
      return new OpListEntry(var0);
   }

   @Override
   public String[] a() {
      return this.d().stream().map(JsonListEntry::g).filter(Objects::nonNull).map(GameProfile::getName).toArray(var0 -> new String[var0]);
   }

   public boolean a(GameProfile var0) {
      OpListEntry var1 = this.b(var0);
      return var1 != null ? var1.b() : false;
   }

   protected String b(GameProfile var0) {
      return var0.getId().toString();
   }
}
