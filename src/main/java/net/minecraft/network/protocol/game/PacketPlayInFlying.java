package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public abstract class PacketPlayInFlying implements Packet<PacketListenerPlayIn> {
   public final double a;
   public final double b;
   public final double c;
   public final float d;
   public final float e;
   protected final boolean f;
   public final boolean g;
   public final boolean h;

   protected PacketPlayInFlying(double var0, double var2, double var4, float var6, float var7, boolean var8, boolean var9, boolean var10) {
      this.a = var0;
      this.b = var2;
      this.c = var4;
      this.d = var6;
      this.e = var7;
      this.f = var8;
      this.g = var9;
      this.h = var10;
   }

   public void a(PacketListenerPlayIn var0) {
      var0.a(this);
   }

   public double a(double var0) {
      return this.g ? this.a : var0;
   }

   public double b(double var0) {
      return this.g ? this.b : var0;
   }

   public double c(double var0) {
      return this.g ? this.c : var0;
   }

   public float a(float var0) {
      return this.h ? this.d : var0;
   }

   public float b(float var0) {
      return this.h ? this.e : var0;
   }

   public boolean a() {
      return this.f;
   }

   public boolean c() {
      return this.g;
   }

   public boolean d() {
      return this.h;
   }

   public static class PacketPlayInLook extends PacketPlayInFlying {
      public PacketPlayInLook(float var0, float var1, boolean var2) {
         super(0.0, 0.0, 0.0, var0, var1, var2, false, true);
      }

      public static PacketPlayInFlying.PacketPlayInLook b(PacketDataSerializer var0) {
         float var1 = var0.readFloat();
         float var2 = var0.readFloat();
         boolean var3 = var0.readUnsignedByte() != 0;
         return new PacketPlayInFlying.PacketPlayInLook(var1, var2, var3);
      }

      @Override
      public void a(PacketDataSerializer var0) {
         var0.writeFloat(this.d);
         var0.writeFloat(this.e);
         var0.writeByte(this.f ? 1 : 0);
      }
   }

   public static class PacketPlayInPosition extends PacketPlayInFlying {
      public PacketPlayInPosition(double var0, double var2, double var4, boolean var6) {
         super(var0, var2, var4, 0.0F, 0.0F, var6, true, false);
      }

      public static PacketPlayInFlying.PacketPlayInPosition b(PacketDataSerializer var0) {
         double var1 = var0.readDouble();
         double var3 = var0.readDouble();
         double var5 = var0.readDouble();
         boolean var7 = var0.readUnsignedByte() != 0;
         return new PacketPlayInFlying.PacketPlayInPosition(var1, var3, var5, var7);
      }

      @Override
      public void a(PacketDataSerializer var0) {
         var0.writeDouble(this.a);
         var0.writeDouble(this.b);
         var0.writeDouble(this.c);
         var0.writeByte(this.f ? 1 : 0);
      }
   }

   public static class PacketPlayInPositionLook extends PacketPlayInFlying {
      public PacketPlayInPositionLook(double var0, double var2, double var4, float var6, float var7, boolean var8) {
         super(var0, var2, var4, var6, var7, var8, true, true);
      }

      public static PacketPlayInFlying.PacketPlayInPositionLook b(PacketDataSerializer var0) {
         double var1 = var0.readDouble();
         double var3 = var0.readDouble();
         double var5 = var0.readDouble();
         float var7 = var0.readFloat();
         float var8 = var0.readFloat();
         boolean var9 = var0.readUnsignedByte() != 0;
         return new PacketPlayInFlying.PacketPlayInPositionLook(var1, var3, var5, var7, var8, var9);
      }

      @Override
      public void a(PacketDataSerializer var0) {
         var0.writeDouble(this.a);
         var0.writeDouble(this.b);
         var0.writeDouble(this.c);
         var0.writeFloat(this.d);
         var0.writeFloat(this.e);
         var0.writeByte(this.f ? 1 : 0);
      }
   }

   public static class d extends PacketPlayInFlying {
      public d(boolean var0) {
         super(0.0, 0.0, 0.0, 0.0F, 0.0F, var0, false, false);
      }

      public static PacketPlayInFlying.d b(PacketDataSerializer var0) {
         boolean var1 = var0.readUnsignedByte() != 0;
         return new PacketPlayInFlying.d(var1);
      }

      @Override
      public void a(PacketDataSerializer var0) {
         var0.writeByte(this.f ? 1 : 0);
      }
   }
}
