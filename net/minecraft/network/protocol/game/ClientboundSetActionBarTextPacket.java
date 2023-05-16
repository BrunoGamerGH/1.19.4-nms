package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.Packet;

public class ClientboundSetActionBarTextPacket implements Packet<PacketListenerPlayOut> {
   private final IChatBaseComponent a;

   public ClientboundSetActionBarTextPacket(IChatBaseComponent var0) {
      this.a = var0;
   }

   public ClientboundSetActionBarTextPacket(PacketDataSerializer var0) {
      this.a = var0.l();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.a);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public IChatBaseComponent a() {
      return this.a;
   }
}
