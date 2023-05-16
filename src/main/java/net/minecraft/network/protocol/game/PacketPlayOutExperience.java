package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketPlayOutExperience implements Packet<PacketListenerPlayOut> {
   private final float a;
   private final int b;
   private final int c;

   public PacketPlayOutExperience(float var0, int var1, int var2) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
   }

   public PacketPlayOutExperience(PacketDataSerializer var0) {
      this.a = var0.readFloat();
      this.c = var0.m();
      this.b = var0.m();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeFloat(this.a);
      var0.d(this.c);
      var0.d(this.b);
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

   public int d() {
      return this.c;
   }
}
