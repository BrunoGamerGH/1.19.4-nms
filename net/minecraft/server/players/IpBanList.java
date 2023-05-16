package net.minecraft.server.players;

import com.google.gson.JsonObject;
import java.io.File;
import java.net.SocketAddress;
import javax.annotation.Nullable;

public class IpBanList extends JsonList<String, IpBanEntry> {
   public IpBanList(File var0) {
      super(var0);
   }

   @Override
   protected JsonListEntry<String> a(JsonObject var0) {
      return new IpBanEntry(var0);
   }

   public boolean a(SocketAddress var0) {
      String var1 = this.c(var0);
      return this.d(var1);
   }

   public boolean a(String var0) {
      return this.d(var0);
   }

   @Nullable
   public IpBanEntry b(SocketAddress var0) {
      String var1 = this.c(var0);
      return this.b(var1);
   }

   private String c(SocketAddress var0) {
      String var1 = var0.toString();
      if (var1.contains("/")) {
         var1 = var1.substring(var1.indexOf(47) + 1);
      }

      if (var1.contains(":")) {
         var1 = var1.substring(0, var1.indexOf(58));
      }

      return var1;
   }
}
