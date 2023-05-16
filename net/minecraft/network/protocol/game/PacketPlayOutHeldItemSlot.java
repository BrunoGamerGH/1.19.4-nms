package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketPlayOutHeldItemSlot implements Packet<PacketListenerPlayOut> {
   private final int a;

   public PacketPlayOutHeldItemSlot(int var0) {
      this.a = var0;
   }

   public PacketPlayOutHeldItemSlot(PacketDataSerializer var0) {
      this.a = var0.readByte();
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
