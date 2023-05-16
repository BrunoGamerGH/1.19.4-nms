package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketPlayInSteerVehicle implements Packet<PacketListenerPlayIn> {
   private static final int a = 1;
   private static final int b = 2;
   private final float c;
   private final float d;
   private final boolean e;
   private final boolean f;

   public PacketPlayInSteerVehicle(float var0, float var1, boolean var2, boolean var3) {
      this.c = var0;
      this.d = var1;
      this.e = var2;
      this.f = var3;
   }

   public PacketPlayInSteerVehicle(PacketDataSerializer var0) {
      this.c = var0.readFloat();
      this.d = var0.readFloat();
      byte var1 = var0.readByte();
      this.e = (var1 & 1) > 0;
      this.f = (var1 & 2) > 0;
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeFloat(this.c);
      var0.writeFloat(this.d);
      byte var1 = 0;
      if (this.e) {
         var1 = (byte)(var1 | 1);
      }

      if (this.f) {
         var1 = (byte)(var1 | 2);
      }

      var0.writeByte(var1);
   }

   public void a(PacketListenerPlayIn var0) {
      var0.a(this);
   }

   public float a() {
      return this.c;
   }

   public float c() {
      return this.d;
   }

   public boolean d() {
      return this.e;
   }

   public boolean e() {
      return this.f;
   }
}
