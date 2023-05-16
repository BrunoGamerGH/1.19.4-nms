package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketPlayOutOpenWindowHorse implements Packet<PacketListenerPlayOut> {
   private final int a;
   private final int b;
   private final int c;

   public PacketPlayOutOpenWindowHorse(int var0, int var1, int var2) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
   }

   public PacketPlayOutOpenWindowHorse(PacketDataSerializer var0) {
      this.a = var0.readUnsignedByte();
      this.b = var0.m();
      this.c = var0.readInt();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeByte(this.a);
      var0.d(this.b);
      var0.writeInt(this.c);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public int a() {
      return this.a;
   }

   public int c() {
      return this.b;
   }

   public int d() {
      return this.c;
   }
}
