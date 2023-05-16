package net.minecraft.network.protocol.login;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketLoginOutSetCompression implements Packet<PacketLoginOutListener> {
   private final int a;

   public PacketLoginOutSetCompression(int var0) {
      this.a = var0;
   }

   public PacketLoginOutSetCompression(PacketDataSerializer var0) {
      this.a = var0.m();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.d(this.a);
   }

   public void a(PacketLoginOutListener var0) {
      var0.a(this);
   }

   public int a() {
      return this.a;
   }
}
