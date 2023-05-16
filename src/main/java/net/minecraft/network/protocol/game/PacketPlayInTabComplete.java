package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketPlayInTabComplete implements Packet<PacketListenerPlayIn> {
   private final int a;
   private final String b;

   public PacketPlayInTabComplete(int var0, String var1) {
      this.a = var0;
      this.b = var1;
   }

   public PacketPlayInTabComplete(PacketDataSerializer var0) {
      this.a = var0.m();
      this.b = var0.e(32500);
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.d(this.a);
      var0.a(this.b, 32500);
   }

   public void a(PacketListenerPlayIn var0) {
      var0.a(this);
   }

   public int a() {
      return this.a;
   }

   public String c() {
      return this.b;
   }
}
