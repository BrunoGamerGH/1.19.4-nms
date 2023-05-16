package net.minecraft.network.protocol.game;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.function.IntFunction;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.inventory.InventoryClickType;
import net.minecraft.world.item.ItemStack;

public class PacketPlayInWindowClick implements Packet<PacketListenerPlayIn> {
   private static final int a = 128;
   private final int b;
   private final int c;
   private final int d;
   private final int e;
   private final InventoryClickType f;
   private final ItemStack g;
   private final Int2ObjectMap<ItemStack> h;

   public PacketPlayInWindowClick(int var0, int var1, int var2, int var3, InventoryClickType var4, ItemStack var5, Int2ObjectMap<ItemStack> var6) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
      this.e = var3;
      this.f = var4;
      this.g = var5;
      this.h = Int2ObjectMaps.unmodifiable(var6);
   }

   public PacketPlayInWindowClick(PacketDataSerializer var0) {
      this.b = var0.readByte();
      this.c = var0.m();
      this.d = var0.readShort();
      this.e = var0.readByte();
      this.f = var0.b(InventoryClickType.class);
      IntFunction<Int2ObjectOpenHashMap<ItemStack>> var1 = PacketDataSerializer.a(Int2ObjectOpenHashMap::new, 128);
      this.h = Int2ObjectMaps.unmodifiable(var0.a(var1, var0x -> Integer.valueOf(var0x.readShort()), PacketDataSerializer::r));
      this.g = var0.r();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeByte(this.b);
      var0.d(this.c);
      var0.writeShort(this.d);
      var0.writeByte(this.e);
      var0.a(this.f);
      var0.a(this.h, PacketDataSerializer::writeShort, PacketDataSerializer::a);
      var0.a(this.g);
   }

   public void a(PacketListenerPlayIn var0) {
      var0.a(this);
   }

   public int a() {
      return this.b;
   }

   public int c() {
      return this.d;
   }

   public int d() {
      return this.e;
   }

   public ItemStack e() {
      return this.g;
   }

   public Int2ObjectMap<ItemStack> f() {
      return this.h;
   }

   public InventoryClickType g() {
      return this.f;
   }

   public int h() {
      return this.c;
   }
}
