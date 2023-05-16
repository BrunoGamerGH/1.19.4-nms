package net.minecraft.server.rcon.thread;

import com.mojang.logging.LogUtils;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import net.minecraft.server.IMinecraftServer;
import net.minecraft.server.rcon.StatusChallengeUtils;
import org.slf4j.Logger;

public class RemoteControlSession extends RemoteConnectionThread {
   private static final Logger d = LogUtils.getLogger();
   private static final int e = 3;
   private static final int f = 2;
   private static final int g = 0;
   private static final int h = 2;
   private static final int i = -1;
   private boolean j;
   private final Socket k;
   private final byte[] l = new byte[1460];
   private final String m;
   private final IMinecraftServer n;

   RemoteControlSession(IMinecraftServer var0, String var1, Socket var2) {
      super("RCON Client " + var2.getInetAddress());
      this.n = var0;
      this.k = var2;

      try {
         this.k.setSoTimeout(0);
      } catch (Exception var5) {
         this.a = false;
      }

      this.m = var1;
   }

   @Override
   public void run() {
      try {
         try {
            while(this.a) {
               BufferedInputStream var0 = new BufferedInputStream(this.k.getInputStream());
               int var1 = var0.read(this.l, 0, 1460);
               if (10 > var1) {
                  return;
               }

               int var2 = 0;
               int var3 = StatusChallengeUtils.b(this.l, 0, var1);
               if (var3 != var1 - 4) {
                  return;
               }

               var2 += 4;
               int var4 = StatusChallengeUtils.b(this.l, var2, var1);
               var2 += 4;
               int var5 = StatusChallengeUtils.a(this.l, var2);
               var2 += 4;
               switch(var5) {
                  case 2:
                     if (this.j) {
                        String var7 = StatusChallengeUtils.a(this.l, var2, var1);

                        try {
                           this.a(var4, this.n.a(var7));
                        } catch (Exception var15) {
                           this.a(var4, "Error executing: " + var7 + " (" + var15.getMessage() + ")");
                        }
                        break;
                     }

                     this.d();
                     break;
                  case 3:
                     String var6 = StatusChallengeUtils.a(this.l, var2, var1);
                     var2 += var6.length();
                     if (!var6.isEmpty() && var6.equals(this.m)) {
                        this.j = true;
                        this.a(var4, 2, "");
                        break;
                     }

                     this.j = false;
                     this.d();
                     break;
                  default:
                     this.a(var4, String.format(Locale.ROOT, "Unknown request %s", Integer.toHexString(var5)));
               }
            }

            return;
         } catch (IOException var16) {
         } catch (Exception var17) {
            d.error("Exception whilst parsing RCON input", var17);
         }
      } finally {
         this.e();
         d.info("Thread {} shutting down", this.b);
         this.a = false;
      }
   }

   private void a(int var0, int var1, String var2) throws IOException {
      ByteArrayOutputStream var3 = new ByteArrayOutputStream(1248);
      DataOutputStream var4 = new DataOutputStream(var3);
      byte[] var5 = var2.getBytes(StandardCharsets.UTF_8);
      var4.writeInt(Integer.reverseBytes(var5.length + 10));
      var4.writeInt(Integer.reverseBytes(var0));
      var4.writeInt(Integer.reverseBytes(var1));
      var4.write(var5);
      var4.write(0);
      var4.write(0);
      this.k.getOutputStream().write(var3.toByteArray());
   }

   private void d() throws IOException {
      this.a(-1, 2, "");
   }

   private void a(int var0, String var1) throws IOException {
      int var2 = var1.length();

      do {
         int var3 = 4096 <= var2 ? 4096 : var2;
         this.a(var0, 0, var1.substring(0, var3));
         var1 = var1.substring(var3);
         var2 = var1.length();
      } while(0 != var2);
   }

   @Override
   public void b() {
      this.a = false;
      this.e();
      super.b();
   }

   private void e() {
      try {
         this.k.close();
      } catch (IOException var2) {
         d.warn("Failed to close socket", var2);
      }
   }
}
