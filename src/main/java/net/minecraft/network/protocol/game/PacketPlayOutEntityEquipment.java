package net.minecraft.network.protocol.game;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.item.ItemStack;

public class PacketPlayOutEntityEquipment implements Packet<PacketListenerPlayOut> {
   private static final byte a = -128;
   private final int b;
   private final List<Pair<EnumItemSlot, ItemStack>> c;

   public PacketPlayOutEntityEquipment(int var0, List<Pair<EnumItemSlot, ItemStack>> var1) {
      this.b = var0;
      this.c = var1;
   }

   public PacketPlayOutEntityEquipment(PacketDataSerializer var0) {
      this.b = var0.m();
      EnumItemSlot[] var1 = EnumItemSlot.values();
      this.c = Lists.newArrayList();

      int var2;
      do {
         var2 = var0.readByte();
         EnumItemSlot var3 = var1[var2 & 127];
         ItemStack var4 = var0.r();
         this.c.add(Pair.of(var3, var4));
      } while((var2 & -128) != 0);
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.d(this.b);
      int var1 = this.c.size();

      for(int var2 = 0; var2 < var1; ++var2) {
         Pair<EnumItemSlot, ItemStack> var3 = (Pair)this.c.get(var2);
         EnumItemSlot var4 = (EnumItemSlot)var3.getFirst();
         boolean var5 = var2 != var1 - 1;
         int var6 = var4.ordinal();
         var0.writeByte(var5 ? var6 | -128 : var6);
         var0.a((ItemStack)var3.getSecond());
      }
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public int a() {
      return this.b;
   }

   public List<Pair<EnumItemSlot, ItemStack>> c() {
      return this.c;
   }
}
