package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public record ClientboundSetSimulationDistancePacket(int simulationDistance) implements Packet<PacketListenerPlayOut> {
   private final int a;

   public ClientboundSetSimulationDistancePacket(PacketDataSerializer var0) {
      this(var0.m());
   }

   public ClientboundSetSimulationDistancePacket(int var0) {
      this.a = var0;
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.d(this.a);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }
}
