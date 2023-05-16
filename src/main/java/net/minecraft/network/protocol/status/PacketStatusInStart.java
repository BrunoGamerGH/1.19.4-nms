package net.minecraft.network.protocol.status;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketStatusInStart implements Packet<PacketStatusInListener> {
   public PacketStatusInStart() {
   }

   public PacketStatusInStart(PacketDataSerializer var0) {
   }

   @Override
   public void a(PacketDataSerializer var0) {
   }

   public void a(PacketStatusInListener var0) {
      var0.a(this);
   }
}
