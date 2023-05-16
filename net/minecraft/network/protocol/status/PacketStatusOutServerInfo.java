package net.minecraft.network.protocol.status;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public record PacketStatusOutServerInfo(ServerPing status) implements Packet<PacketStatusOutListener> {
   private final ServerPing a;

   public PacketStatusOutServerInfo(PacketDataSerializer var0) {
      this(var0.a(ServerPing.a));
   }

   public PacketStatusOutServerInfo(ServerPing var0) {
      this.a = var0;
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(ServerPing.a, this.a);
   }

   public void a(PacketStatusOutListener var0) {
      var0.a(this);
   }
}
