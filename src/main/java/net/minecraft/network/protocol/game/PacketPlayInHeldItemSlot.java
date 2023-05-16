package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketPlayInHeldItemSlot implements Packet<PacketListenerPlayIn> {
   private final int a;

   public PacketPlayInHeldItemSlot(int var0) {
      this.a = var0;
   }

   public PacketPlayInHeldItemSlot(PacketDataSerializer var0) {
      this.a = var0.readShort();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeShort(this.a);
   }

   public void a(PacketListenerPlayIn var0) {
      var0.a(this);
   }

   public int a() {
      return this.a;
   }
}
