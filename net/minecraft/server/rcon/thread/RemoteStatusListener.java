package net.minecraft.server.rcon.thread;

import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.PortUnreachableException;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.server.IMinecraftServer;
import net.minecraft.server.rcon.RemoteStatusReply;
import net.minecraft.server.rcon.StatusChallengeUtils;
import net.minecraft.util.RandomSource;
import org.slf4j.Logger;

public class RemoteStatusListener extends RemoteConnectionThread {
   private static final Logger d = LogUtils.getLogger();
   private static final String e = "SMP";
   private static final String f = "MINECRAFT";
   private static final long g = 30000L;
   private static final long h = 5000L;
   private long i;
   private final int j;
   private final int k;
   private final int l;
   private final String m;
   private final String n;
   private DatagramSocket o;
   private final byte[] p = new byte[1460];
   private String q;
   private String r;
   private final Map<SocketAddress, RemoteStatusListener.RemoteStatusChallenge> s;
   private final RemoteStatusReply t;
   private long u;
   private final IMinecraftServer v;

   private RemoteStatusListener(IMinecraftServer var0, int var1) {
      super("Query Listener");
      this.v = var0;
      this.j = var1;
      this.r = var0.b();
      this.k = var0.d();
      this.m = var0.f();
      this.l = var0.I();
      this.n = var0.q();
      this.u = 0L;
      this.q = "0.0.0.0";
      if (!this.r.isEmpty() && !this.q.equals(this.r)) {
         this.q = this.r;
      } else {
         this.r = "0.0.0.0";

         try {
            InetAddress var2 = InetAddress.getLocalHost();
            this.q = var2.getHostAddress();
         } catch (UnknownHostException var4) {
            d.warn("Unable to determine local host IP, please set server-ip in server.properties", var4);
         }
      }

      this.t = new RemoteStatusReply(1460);
      this.s = Maps.newHashMap();
   }

   @Nullable
   public static RemoteStatusListener a(IMinecraftServer var0) {
      int var1 = var0.a().q;
      if (0 < var1 && 65535 >= var1) {
         RemoteStatusListener var2 = new RemoteStatusListener(var0, var1);
         return !var2.a() ? null : var2;
      } else {
         d.warn("Invalid query port {} found in server.properties (queries disabled)", var1);
         return null;
      }
   }

   private void a(byte[] var0, DatagramPacket var1) throws IOException {
      this.o.send(new DatagramPacket(var0, var0.length, var1.getSocketAddress()));
   }

   private boolean a(DatagramPacket var0) throws IOException {
      byte[] var1 = var0.getData();
      int var2 = var0.getLength();
      SocketAddress var3 = var0.getSocketAddress();
      d.debug("Packet len {} [{}]", var2, var3);
      if (3 <= var2 && -2 == var1[0] && -3 == var1[1]) {
         d.debug("Packet '{}' [{}]", StatusChallengeUtils.a(var1[2]), var3);
         switch(var1[2]) {
            case 0:
               if (!this.c(var0)) {
                  d.debug("Invalid challenge [{}]", var3);
                  return false;
               } else if (15 == var2) {
                  this.a(this.b(var0), var0);
                  d.debug("Rules [{}]", var3);
               } else {
                  RemoteStatusReply var4 = new RemoteStatusReply(1460);
                  var4.a(0);
                  var4.a(this.a(var0.getSocketAddress()));
                  var4.a(this.m);
                  var4.a("SMP");
                  var4.a(this.n);
                  var4.a(Integer.toString(this.v.H()));
                  var4.a(Integer.toString(this.l));
                  var4.a((short)this.k);
                  var4.a(this.q);
                  this.a(var4.a(), var0);
                  d.debug("Status [{}]", var3);
               }
            default:
               return true;
            case 9:
               this.d(var0);
               d.debug("Challenge [{}]", var3);
               return true;
         }
      } else {
         d.debug("Invalid packet [{}]", var3);
         return false;
      }
   }

   private byte[] b(DatagramPacket var0) throws IOException {
      long var1 = SystemUtils.b();
      if (var1 < this.u + 5000L) {
         byte[] var3 = this.t.a();
         byte[] var4 = this.a(var0.getSocketAddress());
         var3[1] = var4[0];
         var3[2] = var4[1];
         var3[3] = var4[2];
         var3[4] = var4[3];
         return var3;
      } else {
         this.u = var1;
         this.t.b();
         this.t.a(0);
         this.t.a(this.a(var0.getSocketAddress()));
         this.t.a("splitnum");
         this.t.a(128);
         this.t.a(0);
         this.t.a("hostname");
         this.t.a(this.m);
         this.t.a("gametype");
         this.t.a("SMP");
         this.t.a("game_id");
         this.t.a("MINECRAFT");
         this.t.a("version");
         this.t.a(this.v.G());
         this.t.a("plugins");
         this.t.a(this.v.s());
         this.t.a("map");
         this.t.a(this.n);
         this.t.a("numplayers");
         this.t.a(this.v.H() + "");
         this.t.a("maxplayers");
         this.t.a(this.l + "");
         this.t.a("hostport");
         this.t.a(this.k + "");
         this.t.a("hostip");
         this.t.a(this.q);
         this.t.a(0);
         this.t.a(1);
         this.t.a("player_");
         this.t.a(0);
         String[] var3 = this.v.J();

         for(String var7 : var3) {
            this.t.a(var7);
         }

         this.t.a(0);
         return this.t.a();
      }
   }

   private byte[] a(SocketAddress var0) {
      return this.s.get(var0).c();
   }

   private Boolean c(DatagramPacket var0) {
      SocketAddress var1 = var0.getSocketAddress();
      if (!this.s.containsKey(var1)) {
         return false;
      } else {
         byte[] var2 = var0.getData();
         return this.s.get(var1).a() == StatusChallengeUtils.c(var2, 7, var0.getLength());
      }
   }

   private void d(DatagramPacket var0) throws IOException {
      RemoteStatusListener.RemoteStatusChallenge var1 = new RemoteStatusListener.RemoteStatusChallenge(var0);
      this.s.put(var0.getSocketAddress(), var1);
      this.a(var1.b(), var0);
   }

   private void d() {
      if (this.a) {
         long var0 = SystemUtils.b();
         if (var0 >= this.i + 30000L) {
            this.i = var0;
            this.s.values().removeIf(var2 -> var2.a(var0));
         }
      }
   }

   @Override
   public void run() {
      d.info("Query running on {}:{}", this.r, this.j);
      this.i = SystemUtils.b();
      DatagramPacket var0 = new DatagramPacket(this.p, this.p.length);

      try {
         while(this.a) {
            try {
               this.o.receive(var0);
               this.d();
               this.a(var0);
            } catch (SocketTimeoutException var8) {
               this.d();
            } catch (PortUnreachableException var9) {
            } catch (IOException var10) {
               this.a(var10);
            }
         }
      } finally {
         d.debug("closeSocket: {}:{}", this.r, this.j);
         this.o.close();
      }
   }

   @Override
   public boolean a() {
      if (this.a) {
         return true;
      } else {
         return !this.e() ? false : super.a();
      }
   }

   private void a(Exception var0) {
      if (this.a) {
         d.warn("Unexpected exception", var0);
         if (!this.e()) {
            d.error("Failed to recover from exception, shutting down!");
            this.a = false;
         }
      }
   }

   private boolean e() {
      try {
         this.o = new DatagramSocket(this.j, InetAddress.getByName(this.r));
         this.o.setSoTimeout(500);
         return true;
      } catch (Exception var2) {
         d.warn("Unable to initialise query system on {}:{}", new Object[]{this.r, this.j, var2});
         return false;
      }
   }

   static class RemoteStatusChallenge {
      private final long a = new Date().getTime();
      private final int b;
      private final byte[] c;
      private final byte[] d;
      private final String e;

      public RemoteStatusChallenge(DatagramPacket var0) {
         byte[] var1 = var0.getData();
         this.c = new byte[4];
         this.c[0] = var1[3];
         this.c[1] = var1[4];
         this.c[2] = var1[5];
         this.c[3] = var1[6];
         this.e = new String(this.c, StandardCharsets.UTF_8);
         this.b = RandomSource.a().a(16777216);
         this.d = String.format(Locale.ROOT, "\t%s%d\u0000", this.e, this.b).getBytes(StandardCharsets.UTF_8);
      }

      public Boolean a(long var0) {
         return this.a < var0;
      }

      public int a() {
         return this.b;
      }

      public byte[] b() {
         return this.d;
      }

      public byte[] c() {
         return this.c;
      }

      public String d() {
         return this.e;
      }
   }
}
