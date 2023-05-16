package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.item.ItemStack;

public class PacketPlayOutSetSlot implements Packet<PacketListenerPlayOut> {
   public static final int a = -1;
   public static final int b = -2;
   private final int c;
   private final int d;
   private final int e;
   private final ItemStack f;

   public PacketPlayOutSetSlot(int var0, int var1, int var2, ItemStack var3) {
      this.c = var0;
      this.d = var1;
      this.e = var2;
      this.f = var3.o();
   }

   public PacketPlayOutSetSlot(PacketDataSerializer var0) {
      this.c = var0.readByte();
      this.d = var0.m();
      this.e = var0.readShort();
      this.f = var0.r();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeByte(this.c);
      var0.d(this.d);
      var0.writeShort(this.e);
      var0.a(this.f);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public int a() {
      return this.c;
   }

   public int c() {
      return this.e;
   }

   public ItemStack d() {
      return this.f;
   }

   public int e() {
      return this.d;
   }
}
