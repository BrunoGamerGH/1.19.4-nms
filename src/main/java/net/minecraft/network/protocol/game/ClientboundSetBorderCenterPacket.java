package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.border.WorldBorder;

public class ClientboundSetBorderCenterPacket implements Packet<PacketListenerPlayOut> {
   private final double a;
   private final double b;

   public ClientboundSetBorderCenterPacket(WorldBorder worldborder) {
      this.a = worldborder.a() * (worldborder.world != null ? worldborder.world.q_().k() : 1.0);
      this.b = worldborder.b() * (worldborder.world != null ? worldborder.world.q_().k() : 1.0);
   }

   public ClientboundSetBorderCenterPacket(PacketDataSerializer packetdataserializer) {
      this.a = packetdataserializer.readDouble();
      this.b = packetdataserializer.readDouble();
   }

   @Override
   public void a(PacketDataSerializer packetdataserializer) {
      packetdataserializer.writeDouble(this.a);
      packetdataserializer.writeDouble(this.b);
   }

   public void a(PacketListenerPlayOut packetlistenerplayout) {
      packetlistenerplayout.a(this);
   }

   public double a() {
      return this.b;
   }

   public double c() {
      return this.a;
   }
}
