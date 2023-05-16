package net.minecraft.network.protocol.game;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.EnumChatFormat;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.scores.ScoreboardTeam;

public class PacketPlayOutScoreboardTeam implements Packet<PacketListenerPlayOut> {
   private static final int a = 0;
   private static final int b = 1;
   private static final int c = 2;
   private static final int d = 3;
   private static final int e = 4;
   private static final int f = 40;
   private static final int g = 40;
   private final int h;
   private final String i;
   private final Collection<String> j;
   private final Optional<PacketPlayOutScoreboardTeam.b> k;

   private PacketPlayOutScoreboardTeam(String var0, int var1, Optional<PacketPlayOutScoreboardTeam.b> var2, Collection<String> var3) {
      this.i = var0;
      this.h = var1;
      this.k = var2;
      this.j = ImmutableList.copyOf(var3);
   }

   public static PacketPlayOutScoreboardTeam a(ScoreboardTeam var0, boolean var1) {
      return new PacketPlayOutScoreboardTeam(
         var0.b(), var1 ? 0 : 2, Optional.of(new PacketPlayOutScoreboardTeam.b(var0)), (Collection<String>)(var1 ? var0.g() : ImmutableList.of())
      );
   }

   public static PacketPlayOutScoreboardTeam a(ScoreboardTeam var0) {
      return new PacketPlayOutScoreboardTeam(var0.b(), 1, Optional.empty(), ImmutableList.of());
   }

   public static PacketPlayOutScoreboardTeam a(ScoreboardTeam var0, String var1, PacketPlayOutScoreboardTeam.a var2) {
      return new PacketPlayOutScoreboardTeam(var0.b(), var2 == PacketPlayOutScoreboardTeam.a.a ? 3 : 4, Optional.empty(), ImmutableList.of(var1));
   }

   public PacketPlayOutScoreboardTeam(PacketDataSerializer var0) {
      this.i = var0.s();
      this.h = var0.readByte();
      if (b(this.h)) {
         this.k = Optional.of(new PacketPlayOutScoreboardTeam.b(var0));
      } else {
         this.k = Optional.empty();
      }

      if (a(this.h)) {
         this.j = var0.a(PacketDataSerializer::s);
      } else {
         this.j = ImmutableList.of();
      }
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.i);
      var0.writeByte(this.h);
      if (b(this.h)) {
         this.k.orElseThrow(() -> new IllegalStateException("Parameters not present, but method is" + this.h)).a(var0);
      }

      if (a(this.h)) {
         var0.a(this.j, PacketDataSerializer::a);
      }
   }

   private static boolean a(int var0) {
      return var0 == 0 || var0 == 3 || var0 == 4;
   }

   private static boolean b(int var0) {
      return var0 == 0 || var0 == 2;
   }

   @Nullable
   public PacketPlayOutScoreboardTeam.a a() {
      switch(this.h) {
         case 0:
         case 3:
            return PacketPlayOutScoreboardTeam.a.a;
         case 1:
         case 2:
         default:
            return null;
         case 4:
            return PacketPlayOutScoreboardTeam.a.b;
      }
   }

   @Nullable
   public PacketPlayOutScoreboardTeam.a c() {
      switch(this.h) {
         case 0:
            return PacketPlayOutScoreboardTeam.a.a;
         case 1:
            return PacketPlayOutScoreboardTeam.a.b;
         default:
            return null;
      }
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public String d() {
      return this.i;
   }

   public Collection<String> e() {
      return this.j;
   }

   public Optional<PacketPlayOutScoreboardTeam.b> f() {
      return this.k;
   }

   public static enum a {
      a,
      b;
   }

   public static class b {
      private final IChatBaseComponent a;
      private final IChatBaseComponent b;
      private final IChatBaseComponent c;
      private final String d;
      private final String e;
      private final EnumChatFormat f;
      private final int g;

      public b(ScoreboardTeam var0) {
         this.a = var0.c();
         this.g = var0.m();
         this.d = var0.j().e;
         this.e = var0.l().e;
         this.f = var0.n();
         this.b = var0.e();
         this.c = var0.f();
      }

      public b(PacketDataSerializer var0) {
         this.a = var0.l();
         this.g = var0.readByte();
         this.d = var0.e(40);
         this.e = var0.e(40);
         this.f = var0.b(EnumChatFormat.class);
         this.b = var0.l();
         this.c = var0.l();
      }

      public IChatBaseComponent a() {
         return this.a;
      }

      public int b() {
         return this.g;
      }

      public EnumChatFormat c() {
         return this.f;
      }

      public String d() {
         return this.d;
      }

      public String e() {
         return this.e;
      }

      public IChatBaseComponent f() {
         return this.b;
      }

      public IChatBaseComponent g() {
         return this.c;
      }

      public void a(PacketDataSerializer var0) {
         var0.a(this.a);
         var0.writeByte(this.g);
         var0.a(this.d);
         var0.a(this.e);
         var0.a(this.f);
         var0.a(this.b);
         var0.a(this.c);
      }
   }
}
