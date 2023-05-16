package net.minecraft.network.protocol.game;

import java.util.List;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public record ClientboundCustomChatCompletionsPacket(ClientboundCustomChatCompletionsPacket.Action action, List<String> entries)
   implements Packet<PacketListenerPlayOut> {
   private final ClientboundCustomChatCompletionsPacket.Action a;
   private final List<String> b;

   public ClientboundCustomChatCompletionsPacket(PacketDataSerializer var0) {
      this(var0.b(ClientboundCustomChatCompletionsPacket.Action.class), var0.a(PacketDataSerializer::s));
   }

   public ClientboundCustomChatCompletionsPacket(ClientboundCustomChatCompletionsPacket.Action var0, List<String> var1) {
      this.a = var0;
      this.b = var1;
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.a);
      var0.a(this.b, PacketDataSerializer::a);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public List<String> c() {
      return this.b;
   }

   public static enum Action {
      a,
      b,
      c;
   }
}
