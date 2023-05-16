package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketPlayOutViewCentre implements Packet<PacketListenerPlayOut> {
   private final int a;
   private final int b;

   public PacketPlayOutViewCentre(int var0, int var1) {
      this.a = var0;
      this.b = var1;
   }

   public PacketPlayOutViewCentre(PacketDataSerializer var0) {
      this.a = var0.m();
      this.b = var0.m();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.d(this.a);
      var0.d(this.b);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public int a() {
      return this.a;
   }

   public int c() {
      return this.b;
   }
}
