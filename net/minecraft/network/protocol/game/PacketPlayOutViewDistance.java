package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketPlayOutViewDistance implements Packet<PacketListenerPlayOut> {
   private final int a;

   public PacketPlayOutViewDistance(int var0) {
      this.a = var0;
   }

   public PacketPlayOutViewDistance(PacketDataSerializer var0) {
      this.a = var0.m();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.d(this.a);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public int a() {
      return this.a;
   }
}
