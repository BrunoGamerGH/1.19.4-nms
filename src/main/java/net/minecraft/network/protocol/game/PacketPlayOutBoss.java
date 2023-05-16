package net.minecraft.network.protocol.game;

import java.util.UUID;
import java.util.function.Function;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.BossBattle;

public class PacketPlayOutBoss implements Packet<PacketListenerPlayOut> {
   private static final int a = 1;
   private static final int b = 2;
   private static final int c = 4;
   private final UUID d;
   private final PacketPlayOutBoss.Action e;
   static final PacketPlayOutBoss.Action f = new PacketPlayOutBoss.Action() {
      @Override
      public PacketPlayOutBoss.d a() {
         return PacketPlayOutBoss.d.b;
      }

      @Override
      public void a(UUID var0, PacketPlayOutBoss.b var1) {
         var1.a(var0);
      }

      @Override
      public void a(PacketDataSerializer var0) {
      }
   };

   private PacketPlayOutBoss(UUID var0, PacketPlayOutBoss.Action var1) {
      this.d = var0;
      this.e = var1;
   }

   public PacketPlayOutBoss(PacketDataSerializer var0) {
      this.d = var0.o();
      PacketPlayOutBoss.d var1 = var0.b(PacketPlayOutBoss.d.class);
      this.e = var1.g.apply(var0);
   }

   public static PacketPlayOutBoss a(BossBattle var0) {
      return new PacketPlayOutBoss(var0.i(), new PacketPlayOutBoss.a(var0));
   }

   public static PacketPlayOutBoss a(UUID var0) {
      return new PacketPlayOutBoss(var0, f);
   }

   public static PacketPlayOutBoss b(BossBattle var0) {
      return new PacketPlayOutBoss(var0.i(), new PacketPlayOutBoss.f(var0.k()));
   }

   public static PacketPlayOutBoss c(BossBattle var0) {
      return new PacketPlayOutBoss(var0.i(), new PacketPlayOutBoss.e(var0.j()));
   }

   public static PacketPlayOutBoss d(BossBattle var0) {
      return new PacketPlayOutBoss(var0.i(), new PacketPlayOutBoss.h(var0.l(), var0.m()));
   }

   public static PacketPlayOutBoss e(BossBattle var0) {
      return new PacketPlayOutBoss(var0.i(), new PacketPlayOutBoss.g(var0.n(), var0.o(), var0.p()));
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.d);
      var0.a(this.e.a());
      this.e.a(var0);
   }

   static int a(boolean var0, boolean var1, boolean var2) {
      int var3 = 0;
      if (var0) {
         var3 |= 1;
      }

      if (var1) {
         var3 |= 2;
      }

      if (var2) {
         var3 |= 4;
      }

      return var3;
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public void a(PacketPlayOutBoss.b var0) {
      this.e.a(this.d, var0);
   }

   interface Action {
      PacketPlayOutBoss.d a();

      void a(UUID var1, PacketPlayOutBoss.b var2);

      void a(PacketDataSerializer var1);
   }

   static class a implements PacketPlayOutBoss.Action {
      private final IChatBaseComponent a;
      private final float b;
      private final BossBattle.BarColor c;
      private final BossBattle.BarStyle d;
      private final boolean e;
      private final boolean f;
      private final boolean g;

      a(BossBattle var0) {
         this.a = var0.j();
         this.b = var0.k();
         this.c = var0.l();
         this.d = var0.m();
         this.e = var0.n();
         this.f = var0.o();
         this.g = var0.p();
      }

      private a(PacketDataSerializer var0) {
         this.a = var0.l();
         this.b = var0.readFloat();
         this.c = var0.b(BossBattle.BarColor.class);
         this.d = var0.b(BossBattle.BarStyle.class);
         int var1 = var0.readUnsignedByte();
         this.e = (var1 & 1) > 0;
         this.f = (var1 & 2) > 0;
         this.g = (var1 & 4) > 0;
      }

      @Override
      public PacketPlayOutBoss.d a() {
         return PacketPlayOutBoss.d.a;
      }

      @Override
      public void a(UUID var0, PacketPlayOutBoss.b var1) {
         var1.a(var0, this.a, this.b, this.c, this.d, this.e, this.f, this.g);
      }

      @Override
      public void a(PacketDataSerializer var0) {
         var0.a(this.a);
         var0.writeFloat(this.b);
         var0.a(this.c);
         var0.a(this.d);
         var0.writeByte(PacketPlayOutBoss.a(this.e, this.f, this.g));
      }
   }

   public interface b {
      default void a(
         UUID var0, IChatBaseComponent var1, float var2, BossBattle.BarColor var3, BossBattle.BarStyle var4, boolean var5, boolean var6, boolean var7
      ) {
      }

      default void a(UUID var0) {
      }

      default void a(UUID var0, float var1) {
      }

      default void a(UUID var0, IChatBaseComponent var1) {
      }

      default void a(UUID var0, BossBattle.BarColor var1, BossBattle.BarStyle var2) {
      }

      default void a(UUID var0, boolean var1, boolean var2, boolean var3) {
      }
   }

   static enum d {
      a(PacketPlayOutBoss.a::new),
      b(var0 -> PacketPlayOutBoss.f),
      c(PacketPlayOutBoss.f::new),
      d(PacketPlayOutBoss.e::new),
      e(PacketPlayOutBoss.h::new),
      f(PacketPlayOutBoss.g::new);

      final Function<PacketDataSerializer, PacketPlayOutBoss.Action> g;

      private d(Function var2) {
         this.g = var2;
      }
   }

   static class e implements PacketPlayOutBoss.Action {
      private final IChatBaseComponent a;

      e(IChatBaseComponent var0) {
         this.a = var0;
      }

      private e(PacketDataSerializer var0) {
         this.a = var0.l();
      }

      @Override
      public PacketPlayOutBoss.d a() {
         return PacketPlayOutBoss.d.d;
      }

      @Override
      public void a(UUID var0, PacketPlayOutBoss.b var1) {
         var1.a(var0, this.a);
      }

      @Override
      public void a(PacketDataSerializer var0) {
         var0.a(this.a);
      }
   }

   static class f implements PacketPlayOutBoss.Action {
      private final float a;

      f(float var0) {
         this.a = var0;
      }

      private f(PacketDataSerializer var0) {
         this.a = var0.readFloat();
      }

      @Override
      public PacketPlayOutBoss.d a() {
         return PacketPlayOutBoss.d.c;
      }

      @Override
      public void a(UUID var0, PacketPlayOutBoss.b var1) {
         var1.a(var0, this.a);
      }

      @Override
      public void a(PacketDataSerializer var0) {
         var0.writeFloat(this.a);
      }
   }

   static class g implements PacketPlayOutBoss.Action {
      private final boolean a;
      private final boolean b;
      private final boolean c;

      g(boolean var0, boolean var1, boolean var2) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
      }

      private g(PacketDataSerializer var0) {
         int var1 = var0.readUnsignedByte();
         this.a = (var1 & 1) > 0;
         this.b = (var1 & 2) > 0;
         this.c = (var1 & 4) > 0;
      }

      @Override
      public PacketPlayOutBoss.d a() {
         return PacketPlayOutBoss.d.f;
      }

      @Override
      public void a(UUID var0, PacketPlayOutBoss.b var1) {
         var1.a(var0, this.a, this.b, this.c);
      }

      @Override
      public void a(PacketDataSerializer var0) {
         var0.writeByte(PacketPlayOutBoss.a(this.a, this.b, this.c));
      }
   }

   static class h implements PacketPlayOutBoss.Action {
      private final BossBattle.BarColor a;
      private final BossBattle.BarStyle b;

      h(BossBattle.BarColor var0, BossBattle.BarStyle var1) {
         this.a = var0;
         this.b = var1;
      }

      private h(PacketDataSerializer var0) {
         this.a = var0.b(BossBattle.BarColor.class);
         this.b = var0.b(BossBattle.BarStyle.class);
      }

      @Override
      public PacketPlayOutBoss.d a() {
         return PacketPlayOutBoss.d.e;
      }

      @Override
      public void a(UUID var0, PacketPlayOutBoss.b var1) {
         var1.a(var0, this.a, this.b);
      }

      @Override
      public void a(PacketDataSerializer var0) {
         var0.a(this.a);
         var0.a(this.b);
      }
   }
}
