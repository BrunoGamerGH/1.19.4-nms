package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public record ServerboundChatAckPacket(int offset) implements Packet<PacketListenerPlayIn> {
   private final int a;

   public ServerboundChatAckPacket(PacketDataSerializer var0) {
      this(var0.m());
   }

   public ServerboundChatAckPacket(int var0) {
      this.a = var0;
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.d(this.a);
   }

   public void a(PacketListenerPlayIn var0) {
      var0.a(this);
   }
}
