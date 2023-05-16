package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3D;

public class PacketPlayOutEntityTeleport implements Packet<PacketListenerPlayOut> {
   private final int a;
   private final double b;
   private final double c;
   private final double d;
   private final byte e;
   private final byte f;
   private final boolean g;

   public PacketPlayOutEntityTeleport(Entity var0) {
      this.a = var0.af();
      Vec3D var1 = var0.df();
      this.b = var1.c;
      this.c = var1.d;
      this.d = var1.e;
      this.e = (byte)((int)(var0.dw() * 256.0F / 360.0F));
      this.f = (byte)((int)(var0.dy() * 256.0F / 360.0F));
      this.g = var0.ax();
   }

   public PacketPlayOutEntityTeleport(PacketDataSerializer var0) {
      this.a = var0.m();
      this.b = var0.readDouble();
      this.c = var0.readDouble();
      this.d = var0.readDouble();
      this.e = var0.readByte();
      this.f = var0.readByte();
      this.g = var0.readBoolean();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.d(this.a);
      var0.writeDouble(this.b);
      var0.writeDouble(this.c);
      var0.writeDouble(this.d);
      var0.writeByte(this.e);
      var0.writeByte(this.f);
      var0.writeBoolean(this.g);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public int a() {
      return this.a;
   }

   public double c() {
      return this.b;
   }

   public double d() {
      return this.c;
   }

   public double e() {
      return this.d;
   }

   public byte f() {
      return this.e;
   }

   public byte g() {
      return this.f;
   }

   public boolean h() {
      return this.g;
   }
}
