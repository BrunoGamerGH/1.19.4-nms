package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.EnumHand;

public class PacketPlayInBlockPlace implements Packet<PacketListenerPlayIn> {
   private final EnumHand a;
   private final int b;
   public long timestamp;

   public PacketPlayInBlockPlace(EnumHand enumhand, int i) {
      this.a = enumhand;
      this.b = i;
   }

   public PacketPlayInBlockPlace(PacketDataSerializer packetdataserializer) {
      this.timestamp = System.currentTimeMillis();
      this.a = packetdataserializer.b(EnumHand.class);
      this.b = packetdataserializer.m();
   }

   @Override
   public void a(PacketDataSerializer packetdataserializer) {
      packetdataserializer.a(this.a);
      packetdataserializer.d(this.b);
   }

   public void a(PacketListenerPlayIn packetlistenerplayin) {
      packetlistenerplayin.a(this);
   }

   public EnumHand a() {
      return this.a;
   }

   public int c() {
      return this.b;
   }
}
