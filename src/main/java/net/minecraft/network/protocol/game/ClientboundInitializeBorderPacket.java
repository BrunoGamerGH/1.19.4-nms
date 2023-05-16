package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.border.WorldBorder;

public class ClientboundInitializeBorderPacket implements Packet<PacketListenerPlayOut> {
   private final double a;
   private final double b;
   private final double c;
   private final double d;
   private final long e;
   private final int f;
   private final int g;
   private final int h;

   public ClientboundInitializeBorderPacket(PacketDataSerializer packetdataserializer) {
      this.a = packetdataserializer.readDouble();
      this.b = packetdataserializer.readDouble();
      this.c = packetdataserializer.readDouble();
      this.d = packetdataserializer.readDouble();
      this.e = packetdataserializer.n();
      this.f = packetdataserializer.m();
      this.g = packetdataserializer.m();
      this.h = packetdataserializer.m();
   }

   public ClientboundInitializeBorderPacket(WorldBorder worldborder) {
      this.a = worldborder.a() * worldborder.world.q_().k();
      this.b = worldborder.b() * worldborder.world.q_().k();
      this.c = worldborder.i();
      this.d = worldborder.k();
      this.e = worldborder.j();
      this.f = worldborder.m();
      this.g = worldborder.r();
      this.h = worldborder.q();
   }

   @Override
   public void a(PacketDataSerializer packetdataserializer) {
      packetdataserializer.writeDouble(this.a);
      packetdataserializer.writeDouble(this.b);
      packetdataserializer.writeDouble(this.c);
      packetdataserializer.writeDouble(this.d);
      packetdataserializer.b(this.e);
      packetdataserializer.d(this.f);
      packetdataserializer.d(this.g);
      packetdataserializer.d(this.h);
   }

   public void a(PacketListenerPlayOut packetlistenerplayout) {
      packetlistenerplayout.a(this);
   }

   public double a() {
      return this.a;
   }

   public double c() {
      return this.b;
   }

   public double d() {
      return this.d;
   }

   public double e() {
      return this.c;
   }

   public long f() {
      return this.e;
   }

   public int g() {
      return this.f;
   }

   public int h() {
      return this.h;
   }

   public int i() {
      return this.g;
   }
}
