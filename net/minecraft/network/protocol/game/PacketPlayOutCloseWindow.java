package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketPlayOutCloseWindow implements Packet<PacketListenerPlayOut> {
   private final int a;

   public PacketPlayOutCloseWindow(int var0) {
      this.a = var0;
   }

   public PacketPlayOutCloseWindow(PacketDataSerializer var0) {
      this.a = var0.readUnsignedByte();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeByte(this.a);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public int a() {
      return this.a;
   }
}
