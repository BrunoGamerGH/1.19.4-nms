package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;

public class PacketPlayOutAnimation implements Packet<PacketListenerPlayOut> {
   public static final int a = 0;
   public static final int b = 2;
   public static final int c = 3;
   public static final int d = 4;
   public static final int e = 5;
   private final int f;
   private final int g;

   public PacketPlayOutAnimation(Entity var0, int var1) {
      this.f = var0.af();
      this.g = var1;
   }

   public PacketPlayOutAnimation(PacketDataSerializer var0) {
      this.f = var0.m();
      this.g = var0.readUnsignedByte();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.d(this.f);
      var0.writeByte(this.g);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public int a() {
      return this.f;
   }

   public int c() {
      return this.g;
   }
}
