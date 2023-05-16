package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketPlayInEnchantItem implements Packet<PacketListenerPlayIn> {
   private final int a;
   private final int b;

   public PacketPlayInEnchantItem(int var0, int var1) {
      this.a = var0;
      this.b = var1;
   }

   public void a(PacketListenerPlayIn var0) {
      var0.a(this);
   }

   public PacketPlayInEnchantItem(PacketDataSerializer var0) {
      this.a = var0.readByte();
      this.b = var0.readByte();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeByte(this.a);
      var0.writeByte(this.b);
   }

   public int a() {
      return this.a;
   }

   public int c() {
      return this.b;
   }
}
