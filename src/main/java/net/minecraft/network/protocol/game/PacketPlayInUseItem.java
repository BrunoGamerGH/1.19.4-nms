package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.EnumHand;
import net.minecraft.world.phys.MovingObjectPositionBlock;

public class PacketPlayInUseItem implements Packet<PacketListenerPlayIn> {
   private final MovingObjectPositionBlock a;
   private final EnumHand b;
   private final int c;
   public long timestamp;

   public PacketPlayInUseItem(EnumHand enumhand, MovingObjectPositionBlock movingobjectpositionblock, int i) {
      this.b = enumhand;
      this.a = movingobjectpositionblock;
      this.c = i;
   }

   public PacketPlayInUseItem(PacketDataSerializer packetdataserializer) {
      this.timestamp = System.currentTimeMillis();
      this.b = packetdataserializer.b(EnumHand.class);
      this.a = packetdataserializer.x();
      this.c = packetdataserializer.m();
   }

   @Override
   public void a(PacketDataSerializer packetdataserializer) {
      packetdataserializer.a(this.b);
      packetdataserializer.a(this.a);
      packetdataserializer.d(this.c);
   }

   public void a(PacketListenerPlayIn packetlistenerplayin) {
      packetlistenerplayin.a(this);
   }

   public EnumHand a() {
      return this.b;
   }

   public MovingObjectPositionBlock c() {
      return this.a;
   }

   public int d() {
      return this.c;
   }
}
