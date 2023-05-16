package net.minecraft.network.protocol.game;

import net.minecraft.core.BlockPosition;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketPlayInJigsawGenerate implements Packet<PacketListenerPlayIn> {
   private final BlockPosition a;
   private final int b;
   private final boolean c;

   public PacketPlayInJigsawGenerate(BlockPosition var0, int var1, boolean var2) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
   }

   public PacketPlayInJigsawGenerate(PacketDataSerializer var0) {
      this.a = var0.f();
      this.b = var0.m();
      this.c = var0.readBoolean();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.a);
      var0.d(this.b);
      var0.writeBoolean(this.c);
   }

   public void a(PacketListenerPlayIn var0) {
      var0.a(this);
   }

   public BlockPosition a() {
      return this.a;
   }

   public int c() {
      return this.b;
   }

   public boolean d() {
      return this.c;
   }
}
