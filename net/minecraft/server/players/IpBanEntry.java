package net.minecraft.server.players;

import com.google.gson.JsonObject;
import java.util.Date;
import javax.annotation.Nullable;
import net.minecraft.network.chat.IChatBaseComponent;

public class IpBanEntry extends ExpirableListEntry<String> {
   public IpBanEntry(String var0) {
      this(var0, null, null, null, null);
   }

   public IpBanEntry(String var0, @Nullable Date var1, @Nullable String var2, @Nullable Date var3, @Nullable String var4) {
      super(var0, var1, var2, var3, var4);
   }

   @Override
   public IChatBaseComponent e() {
      return IChatBaseComponent.b(String.valueOf(this.g()));
   }

   public IpBanEntry(JsonObject var0) {
      super(b(var0), var0);
   }

   private static String b(JsonObject var0) {
      return var0.has("ip") ? var0.get("ip").getAsString() : null;
   }

   @Override
   protected void a(JsonObject var0) {
      if (this.g() != null) {
         var0.addProperty("ip", this.g());
         super.a(var0);
      }
   }
}
