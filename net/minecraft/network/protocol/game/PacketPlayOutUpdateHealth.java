package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketPlayOutUpdateHealth implements Packet<PacketListenerPlayOut> {
   private final float a;
   private final int b;
   private final float c;

   public PacketPlayOutUpdateHealth(float var0, int var1, float var2) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
   }

   public PacketPlayOutUpdateHealth(PacketDataSerializer var0) {
      this.a = var0.readFloat();
      this.b = var0.m();
      this.c = var0.readFloat();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeFloat(this.a);
      var0.d(this.b);
      var0.writeFloat(this.c);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public float a() {
      return this.a;
   }

   public int c() {
      return this.b;
   }

   public float d() {
      return this.c;
   }
}
