package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.item.ItemStack;

public class PacketPlayInSetCreativeSlot implements Packet<PacketListenerPlayIn> {
   private final int a;
   private final ItemStack b;

   public PacketPlayInSetCreativeSlot(int var0, ItemStack var1) {
      this.a = var0;
      this.b = var1.o();
   }

   public void a(PacketListenerPlayIn var0) {
      var0.a(this);
   }

   public PacketPlayInSetCreativeSlot(PacketDataSerializer var0) {
      this.a = var0.readShort();
      this.b = var0.r();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeShort(this.a);
      var0.a(this.b);
   }

   public int a() {
      return this.a;
   }

   public ItemStack c() {
      return this.b;
   }
}
