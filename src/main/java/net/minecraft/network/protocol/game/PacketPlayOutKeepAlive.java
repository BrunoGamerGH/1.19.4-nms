package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketPlayOutKeepAlive implements Packet<PacketListenerPlayOut> {
   private final long a;

   public PacketPlayOutKeepAlive(long var0) {
      this.a = var0;
   }

   public PacketPlayOutKeepAlive(PacketDataSerializer var0) {
      this.a = var0.readLong();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeLong(this.a);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public long a() {
      return this.a;
   }
}
