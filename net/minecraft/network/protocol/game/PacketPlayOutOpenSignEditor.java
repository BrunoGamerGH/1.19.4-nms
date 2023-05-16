package net.minecraft.network.protocol.game;

import net.minecraft.core.BlockPosition;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketPlayOutOpenSignEditor implements Packet<PacketListenerPlayOut> {
   private final BlockPosition a;

   public PacketPlayOutOpenSignEditor(BlockPosition var0) {
      this.a = var0;
   }

   public PacketPlayOutOpenSignEditor(PacketDataSerializer var0) {
      this.a = var0.f();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(this.a);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public BlockPosition a() {
      return this.a;
   }
}
