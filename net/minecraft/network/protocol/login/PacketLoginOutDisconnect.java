package net.minecraft.network.protocol.login;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.Packet;

public class PacketLoginOutDisconnect implements Packet<PacketLoginOutListener> {
   private final IChatBaseComponent a;

   public PacketLoginOutDisconnect(IChatBaseComponent var0) {
      this.a = var0;
   }

   public PacketLoginOutDisconnect(PacketDataSerializer var0) {
      this.a = IChatBaseComponent.ChatSerializer.b(var0.e(262144));
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.a);
   }

   public void a(PacketLoginOutListener var0) {
      var0.a(this);
   }

   public IChatBaseComponent a() {
      return this.a;
   }
}
