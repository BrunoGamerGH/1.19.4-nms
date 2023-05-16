package net.minecraft.network.protocol.game;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.Packet;

public record ClientboundSystemChatPacket(String content, boolean overlay) implements Packet<PacketListenerPlayOut> {
   private final String a;
   private final boolean b;

   public ClientboundSystemChatPacket(IChatBaseComponent content, boolean overlay) {
      this(IChatBaseComponent.ChatSerializer.a(content), overlay);
   }

   public ClientboundSystemChatPacket(BaseComponent[] content, boolean overlay) {
      this(ComponentSerializer.toString(content), overlay);
   }

   public ClientboundSystemChatPacket(PacketDataSerializer packetdataserializer) {
      this(packetdataserializer.l(), packetdataserializer.readBoolean());
   }

   @Override
   public void a(PacketDataSerializer packetdataserializer) {
      packetdataserializer.a(this.a, 262144);
      packetdataserializer.writeBoolean(this.b);
   }

   public void a(PacketListenerPlayOut packetlistenerplayout) {
      packetlistenerplayout.a(this);
   }

   @Override
   public boolean b() {
      return true;
   }

   public String content() {
      return this.a;
   }

   public boolean c() {
      return this.b;
   }

   public ClientboundSystemChatPacket(String var1, boolean var2) {
      this.a = var1;
      this.b = var2;
   }
}
