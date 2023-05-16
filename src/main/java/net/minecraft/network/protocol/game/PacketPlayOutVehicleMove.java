package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;

public class PacketPlayOutVehicleMove implements Packet<PacketListenerPlayOut> {
   private final double a;
   private final double b;
   private final double c;
   private final float d;
   private final float e;

   public PacketPlayOutVehicleMove(Entity var0) {
      this.a = var0.dl();
      this.b = var0.dn();
      this.c = var0.dr();
      this.d = var0.dw();
      this.e = var0.dy();
   }

   public PacketPlayOutVehicleMove(PacketDataSerializer var0) {
      this.a = var0.readDouble();
      this.b = var0.readDouble();
      this.c = var0.readDouble();
      this.d = var0.readFloat();
      this.e = var0.readFloat();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeDouble(this.a);
      var0.writeDouble(this.b);
      var0.writeDouble(this.c);
      var0.writeFloat(this.d);
      var0.writeFloat(this.e);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public double a() {
      return this.a;
   }

   public double c() {
      return this.b;
   }

   public double d() {
      return this.c;
   }

   public float e() {
      return this.d;
   }

   public float f() {
      return this.e;
   }
}
