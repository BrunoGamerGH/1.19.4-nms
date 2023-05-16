package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.border.WorldBorder;

public class ClientboundSetBorderSizePacket implements Packet<PacketListenerPlayOut> {
   private final double a;

   public ClientboundSetBorderSizePacket(WorldBorder var0) {
      this.a = var0.k();
   }

   public ClientboundSetBorderSizePacket(PacketDataSerializer var0) {
      this.a = var0.readDouble();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeDouble(this.a);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public double a() {
      return this.a;
   }
}
