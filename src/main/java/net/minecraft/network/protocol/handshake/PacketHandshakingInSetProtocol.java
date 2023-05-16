package net.minecraft.network.protocol.handshake;

import net.minecraft.SharedConstants;
import net.minecraft.network.EnumProtocol;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketHandshakingInSetProtocol implements Packet<PacketHandshakingInListener> {
   private static final int a = 255;
   private final int b;
   public String c;
   public final int d;
   private final EnumProtocol e;

   public PacketHandshakingInSetProtocol(String s, int i, EnumProtocol enumprotocol) {
      this.b = SharedConstants.b().e();
      this.c = s;
      this.d = i;
      this.e = enumprotocol;
   }

   public PacketHandshakingInSetProtocol(PacketDataSerializer packetdataserializer) {
      this.b = packetdataserializer.m();
      this.c = packetdataserializer.e(32767);
      this.d = packetdataserializer.readUnsignedShort();
      this.e = EnumProtocol.a(packetdataserializer.m());
   }

   @Override
   public void a(PacketDataSerializer packetdataserializer) {
      packetdataserializer.d(this.b);
      packetdataserializer.a(this.c);
      packetdataserializer.writeShort(this.d);
      packetdataserializer.d(this.e.a());
   }

   public void a(PacketHandshakingInListener packethandshakinginlistener) {
      packethandshakinginlistener.a(this);
   }

   public EnumProtocol a() {
      return this.e;
   }

   public int c() {
      return this.b;
   }

   public String d() {
      return this.c;
   }

   public int e() {
      return this.d;
   }
}
