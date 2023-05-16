package net.minecraft.network.protocol.status;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketStatusInPing implements Packet<PacketStatusInListener> {
   private final long a;

   public PacketStatusInPing(long var0) {
      this.a = var0;
   }

   public PacketStatusInPing(PacketDataSerializer var0) {
      this.a = var0.readLong();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeLong(this.a);
   }

   public void a(PacketStatusInListener var0) {
      var0.a(this);
   }

   public long a() {
      return this.a;
   }
}
