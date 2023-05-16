package net.minecraft.network.protocol.game;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.item.Item;

public class PacketPlayOutSetCooldown implements Packet<PacketListenerPlayOut> {
   private final Item a;
   private final int b;

   public PacketPlayOutSetCooldown(Item var0, int var1) {
      this.a = var0;
      this.b = var1;
   }

   public PacketPlayOutSetCooldown(PacketDataSerializer var0) {
      this.a = var0.a(BuiltInRegistries.i);
      this.b = var0.m();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(BuiltInRegistries.i, this.a);
      var0.d(this.b);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public Item a() {
      return this.a;
   }

   public int c() {
      return this.b;
   }
}
