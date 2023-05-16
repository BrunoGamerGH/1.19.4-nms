package net.minecraft.network.protocol.game;

import net.minecraft.core.BlockPosition;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketPlayInTileNBTQuery implements Packet<PacketListenerPlayIn> {
   private final int a;
   private final BlockPosition b;

   public PacketPlayInTileNBTQuery(int var0, BlockPosition var1) {
      this.a = var0;
      this.b = var1;
   }

   public PacketPlayInTileNBTQuery(PacketDataSerializer var0) {
      this.a = var0.m();
      this.b = var0.f();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.d(this.a);
      var0.a(this.b);
   }

   public void a(PacketListenerPlayIn var0) {
      var0.a(this);
   }

   public int a() {
      return this.a;
   }

   public BlockPosition c() {
      return this.b;
   }
}
