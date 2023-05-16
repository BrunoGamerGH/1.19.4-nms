package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public record ClientboundBlockChangedAckPacket(int sequence) implements Packet<PacketListenerPlayOut> {
   private final int a;

   public ClientboundBlockChangedAckPacket(PacketDataSerializer var0) {
      this(var0.m());
   }

   public ClientboundBlockChangedAckPacket(int var0) {
      this.a = var0;
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.d(this.a);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }
}
