package net.minecraft.server.rcon.thread;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.server.IMinecraftServer;
import net.minecraft.server.dedicated.DedicatedServerProperties;
import org.slf4j.Logger;

public class RemoteControlListener extends RemoteConnectionThread {
   private static final Logger d = LogUtils.getLogger();
   private final ServerSocket e;
   private final String f;
   private final List<RemoteControlSession> g = Lists.newArrayList();
   private final IMinecraftServer h;

   private RemoteControlListener(IMinecraftServer var0, ServerSocket var1, String var2) {
      super("RCON Listener");
      this.h = var0;
      this.e = var1;
      this.f = var2;
   }

   private void d() {
      this.g.removeIf(var0 -> !var0.c());
   }

   @Override
   public void run() {
      try {
         while(this.a) {
            try {
               Socket var0 = this.e.accept();
               RemoteControlSession var1 = new RemoteControlSession(this.h, this.f, var0);
               var1.a();
               this.g.add(var1);
               this.d();
            } catch (SocketTimeoutException var7) {
               this.d();
            } catch (IOException var8) {
               if (this.a) {
                  d.info("IO exception: ", var8);
               }
            }
         }
      } finally {
         this.a(this.e);
      }
   }

   @Nullable
   public static RemoteControlListener a(IMinecraftServer var0) {
      DedicatedServerProperties var1 = var0.a();
      String var2 = var0.b();
      if (var2.isEmpty()) {
         var2 = "0.0.0.0";
      }

      int var3 = var1.s;
      if (0 < var3 && 65535 >= var3) {
         String var4 = var1.t;
         if (var4.isEmpty()) {
            d.warn("No rcon password set in server.properties, rcon disabled!");
            return null;
         } else {
            try {
               ServerSocket var5 = new ServerSocket(var3, 0, InetAddress.getByName(var2));
               var5.setSoTimeout(500);
               RemoteControlListener var6 = new RemoteControlListener(var0, var5, var4);
               if (!var6.a()) {
                  return null;
               } else {
                  d.info("RCON running on {}:{}", var2, var3);
                  return var6;
               }
            } catch (IOException var7) {
               d.warn("Unable to initialise RCON on {}:{}", new Object[]{var2, var3, var7});
               return null;
            }
         }
      } else {
         d.warn("Invalid rcon port {} found in server.properties, rcon disabled!", var3);
         return null;
      }
   }

   @Override
   public void b() {
      this.a = false;
      this.a(this.e);
      super.b();

      for(RemoteControlSession var1 : this.g) {
         if (var1.c()) {
            var1.b();
         }
      }

      this.g.clear();
   }

   private void a(ServerSocket var0) {
      d.debug("closeSocket: {}", var0);

      try {
         var0.close();
      } catch (IOException var3) {
         d.warn("Failed to close socket", var3);
      }
   }
}
