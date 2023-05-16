package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.chat.ChatMessageType;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.Packet;

public record ClientboundDisguisedChatPacket(IChatBaseComponent message, ChatMessageType.b chatType) implements Packet<PacketListenerPlayOut> {
   private final IChatBaseComponent a;
   private final ChatMessageType.b b;

   public ClientboundDisguisedChatPacket(PacketDataSerializer var0) {
      this(var0.l(), new ChatMessageType.b(var0));
   }

   public ClientboundDisguisedChatPacket(IChatBaseComponent var0, ChatMessageType.b var1) {
      this.a = var0;
      this.b = var1;
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.a);
      this.b.a(var0);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   @Override
   public boolean b() {
      return true;
   }

   public ChatMessageType.b c() {
      return this.b;
   }
}
