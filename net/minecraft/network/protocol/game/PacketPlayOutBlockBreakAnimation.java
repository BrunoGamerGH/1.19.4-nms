package net.minecraft.network.protocol.game;

import net.minecraft.core.BlockPosition;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketPlayOutBlockBreakAnimation implements Packet<PacketListenerPlayOut> {
   private final int a;
   private final BlockPosition b;
   private final int c;

   public PacketPlayOutBlockBreakAnimation(int var0, BlockPosition var1, int var2) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
   }

   public PacketPlayOutBlockBreakAnimation(PacketDataSerializer var0) {
      this.a = var0.m();
      this.b = var0.f();
      this.c = var0.readUnsignedByte();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.d(this.a);
      var0.a(this.b);
      var0.writeByte(this.c);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public int a() {
      return this.a;
   }

   public BlockPosition c() {
      return this.b;
   }

   public int d() {
      return this.c;
   }
}
