package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.network.protocol.Packet;

public record ClientboundDeleteChatPacket(MessageSignature.a messageSignature) implements Packet<PacketListenerPlayOut> {
   private final MessageSignature.a a;

   public ClientboundDeleteChatPacket(PacketDataSerializer var0) {
      this(MessageSignature.a.a(var0));
   }

   public ClientboundDeleteChatPacket(MessageSignature.a var0) {
      this.a = var0;
   }

   @Override
   public void a(PacketDataSerializer var0) {
      MessageSignature.a.a(var0, this.a);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }
}
