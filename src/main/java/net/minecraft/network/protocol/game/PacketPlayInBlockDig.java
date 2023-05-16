package net.minecraft.network.protocol.game;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketPlayInBlockDig implements Packet<PacketListenerPlayIn> {
   private final BlockPosition a;
   private final EnumDirection b;
   private final PacketPlayInBlockDig.EnumPlayerDigType c;
   private final int d;

   public PacketPlayInBlockDig(PacketPlayInBlockDig.EnumPlayerDigType var0, BlockPosition var1, EnumDirection var2, int var3) {
      this.c = var0;
      this.a = var1.i();
      this.b = var2;
      this.d = var3;
   }

   public PacketPlayInBlockDig(PacketPlayInBlockDig.EnumPlayerDigType var0, BlockPosition var1, EnumDirection var2) {
      this(var0, var1, var2, 0);
   }

   public PacketPlayInBlockDig(PacketDataSerializer var0) {
      this.c = var0.b(PacketPlayInBlockDig.EnumPlayerDigType.class);
      this.a = var0.f();
      this.b = EnumDirection.a(var0.readUnsignedByte());
      this.d = var0.m();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.c);
      var0.a(this.a);
      var0.writeByte(this.b.d());
      var0.d(this.d);
   }

   public void a(PacketListenerPlayIn var0) {
      var0.a(this);
   }

   public BlockPosition a() {
      return this.a;
   }

   public EnumDirection c() {
      return this.b;
   }

   public PacketPlayInBlockDig.EnumPlayerDigType d() {
      return this.c;
   }

   public int e() {
      return this.d;
   }

   public static enum EnumPlayerDigType {
      a,
      b,
      c,
      d,
      e,
      f,
      g;
   }
}
