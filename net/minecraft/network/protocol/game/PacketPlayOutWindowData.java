package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketPlayOutWindowData implements Packet<PacketListenerPlayOut> {
   private final int a;
   private final int b;
   private final int c;

   public PacketPlayOutWindowData(int var0, int var1, int var2) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
   }

   public PacketPlayOutWindowData(PacketDataSerializer var0) {
      this.a = var0.readUnsignedByte();
      this.b = var0.readShort();
      this.c = var0.readShort();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeByte(this.a);
      var0.writeShort(this.b);
      var0.writeShort(this.c);
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
