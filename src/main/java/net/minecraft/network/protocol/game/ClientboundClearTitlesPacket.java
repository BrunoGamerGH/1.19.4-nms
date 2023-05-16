package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class ClientboundClearTitlesPacket implements Packet<PacketListenerPlayOut> {
   private final boolean a;

   public ClientboundClearTitlesPacket(boolean var0) {
      this.a = var0;
   }

   public ClientboundClearTitlesPacket(PacketDataSerializer var0) {
      this.a = var0.readBoolean();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeBoolean(this.a);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public boolean a() {
      return this.a;
   }
}
