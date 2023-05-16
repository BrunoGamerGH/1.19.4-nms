package net.minecraft.network.protocol.game;

import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.item.ItemStack;

public class PacketPlayOutWindowItems implements Packet<PacketListenerPlayOut> {
   private final int a;
   private final int b;
   private final List<ItemStack> c;
   private final ItemStack d;

   public PacketPlayOutWindowItems(int var0, int var1, NonNullList<ItemStack> var2, ItemStack var3) {
      this.a = var0;
      this.b = var1;
      this.c = NonNullList.a(var2.size(), ItemStack.b);

      for(int var4 = 0; var4 < var2.size(); ++var4) {
         this.c.set(var4, var2.get(var4).o());
      }

      this.d = var3.o();
   }

   public PacketPlayOutWindowItems(PacketDataSerializer var0) {
      this.a = var0.readUnsignedByte();
      this.b = var0.m();
      this.c = var0.a(NonNullList::a, PacketDataSerializer::r);
      this.d = var0.r();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeByte(this.a);
      var0.d(this.b);
      var0.a(this.c, PacketDataSerializer::a);
      var0.a(this.d);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public int a() {
      return this.a;
   }

   public List<ItemStack> c() {
      return this.c;
   }

   public ItemStack d() {
      return this.d;
   }

   public int e() {
      return this.b;
   }
}
