package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketPlayInPickItem implements Packet<PacketListenerPlayIn> {
   private final int a;

   public PacketPlayInPickItem(int var0) {
      this.a = var0;
   }

   public PacketPlayInPickItem(PacketDataSerializer var0) {
      this.a = var0.m();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.d(this.a);
   }

   public void a(PacketListenerPlayIn var0) {
      var0.a(this);
   }

   public int a() {
      return this.a;
   }
}
