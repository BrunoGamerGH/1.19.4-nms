package net.minecraft.network.protocol.game;

import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.ScoreboardServer;

public class PacketPlayOutScoreboardScore implements Packet<PacketListenerPlayOut> {
   private final String a;
   @Nullable
   private final String b;
   private final int c;
   private final ScoreboardServer.Action d;

   public PacketPlayOutScoreboardScore(ScoreboardServer.Action var0, @Nullable String var1, String var2, int var3) {
      if (var0 != ScoreboardServer.Action.b && var1 == null) {
         throw new IllegalArgumentException("Need an objective name");
      } else {
         this.a = var2;
         this.b = var1;
         this.c = var3;
         this.d = var0;
      }
   }

   public PacketPlayOutScoreboardScore(PacketDataSerializer var0) {
      this.a = var0.s();
      this.d = var0.b(ScoreboardServer.Action.class);
      String var1 = var0.s();
      this.b = Objects.equals(var1, "") ? null : var1;
      if (this.d != ScoreboardServer.Action.b) {
         this.c = var0.m();
      } else {
         this.c = 0;
      }
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.a);
      var0.a(this.d);
      var0.a(this.b == null ? "" : this.b);
      if (this.d != ScoreboardServer.Action.b) {
         var0.d(this.c);
      }
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public String a() {
      return this.a;
   }

   @Nullable
   public String c() {
      return this.b;
   }

   public int d() {
      return this.c;
   }

   public ScoreboardServer.Action e() {
      return this.d;
   }
}
