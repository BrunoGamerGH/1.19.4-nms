package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class ServerboundPongPacket implements Packet<PacketListenerPlayIn> {
   private final int a;

   public ServerboundPongPacket(int var0) {
      this.a = var0;
   }

   public ServerboundPongPacket(PacketDataSerializer var0) {
      this.a = var0.readInt();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeInt(this.a);
   }

   public void a(PacketListenerPlayIn var0) {
      var0.a(this);
   }

   public int a() {
      return this.a;
   }
}
