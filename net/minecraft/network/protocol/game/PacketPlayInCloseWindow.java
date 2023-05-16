package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketPlayInCloseWindow implements Packet<PacketListenerPlayIn> {
   private final int a;

   public PacketPlayInCloseWindow(int var0) {
      this.a = var0;
   }

   public void a(PacketListenerPlayIn var0) {
      var0.a(this);
   }

   public PacketPlayInCloseWindow(PacketDataSerializer var0) {
      this.a = var0.readByte();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeByte(this.a);
   }

   public int a() {
      return this.a;
   }
}
