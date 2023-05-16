package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.chat.RemoteChatSession;
import net.minecraft.network.protocol.Packet;

public record ServerboundChatSessionUpdatePacket(RemoteChatSession.a chatSession) implements Packet<PacketListenerPlayIn> {
   private final RemoteChatSession.a a;

   public ServerboundChatSessionUpdatePacket(PacketDataSerializer var0) {
      this(RemoteChatSession.a.a(var0));
   }

   public ServerboundChatSessionUpdatePacket(RemoteChatSession.a var0) {
      this.a = var0;
   }

   @Override
   public void a(PacketDataSerializer var0) {
      RemoteChatSession.a.a(var0, this.a);
   }

   public void a(PacketListenerPlayIn var0) {
      var0.a(this);
   }
}
