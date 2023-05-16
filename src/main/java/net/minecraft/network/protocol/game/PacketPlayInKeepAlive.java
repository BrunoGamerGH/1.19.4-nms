package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketPlayInKeepAlive implements Packet<PacketListenerPlayIn> {
   private final long a;

   public PacketPlayInKeepAlive(long var0) {
      this.a = var0;
   }

   public void a(PacketListenerPlayIn var0) {
      var0.a(this);
   }

   public PacketPlayInKeepAlive(PacketDataSerializer var0) {
      this.a = var0.readLong();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeLong(this.a);
   }

   public long a() {
      return this.a;
   }
}
