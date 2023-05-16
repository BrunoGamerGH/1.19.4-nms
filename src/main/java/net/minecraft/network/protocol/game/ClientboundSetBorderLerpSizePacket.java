package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.border.WorldBorder;

public class ClientboundSetBorderLerpSizePacket implements Packet<PacketListenerPlayOut> {
   private final double a;
   private final double b;
   private final long c;

   public ClientboundSetBorderLerpSizePacket(WorldBorder var0) {
      this.a = var0.i();
      this.b = var0.k();
      this.c = var0.j();
   }

   public ClientboundSetBorderLerpSizePacket(PacketDataSerializer var0) {
      this.a = var0.readDouble();
      this.b = var0.readDouble();
      this.c = var0.n();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeDouble(this.a);
      var0.writeDouble(this.b);
      var0.b(this.c);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public double a() {
      return this.a;
   }

   public double c() {
      return this.b;
   }

   public long d() {
      return this.c;
   }
}
