package net.minecraft.network.protocol.status;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketStatusOutPong implements Packet<PacketStatusOutListener> {
   private final long a;

   public PacketStatusOutPong(long var0) {
      this.a = var0;
   }

   public PacketStatusOutPong(PacketDataSerializer var0) {
      this.a = var0.readLong();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeLong(this.a);
   }

   public void a(PacketStatusOutListener var0) {
      var0.a(this);
   }

   public long a() {
      return this.a;
   }
}
