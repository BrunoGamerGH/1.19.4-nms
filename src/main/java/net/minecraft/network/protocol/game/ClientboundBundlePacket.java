package net.minecraft.network.protocol.game;

import net.minecraft.network.protocol.BundlePacket;
import net.minecraft.network.protocol.Packet;

public class ClientboundBundlePacket extends BundlePacket<PacketListenerPlayOut> {
   public ClientboundBundlePacket(Iterable<Packet<PacketListenerPlayOut>> var0) {
      super(var0);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }
}
