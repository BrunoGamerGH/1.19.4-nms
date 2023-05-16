package net.minecraft.network.protocol.game;

import java.util.UUID;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.player.EntityHuman;

public class PacketPlayOutNamedEntitySpawn implements Packet<PacketListenerPlayOut> {
   private final int a;
   private final UUID b;
   private final double c;
   private final double d;
   private final double e;
   private final byte f;
   private final byte g;

   public PacketPlayOutNamedEntitySpawn(EntityHuman var0) {
      this.a = var0.af();
      this.b = var0.fI().getId();
      this.c = var0.dl();
      this.d = var0.dn();
      this.e = var0.dr();
      this.f = (byte)((int)(var0.dw() * 256.0F / 360.0F));
      this.g = (byte)((int)(var0.dy() * 256.0F / 360.0F));
   }

   public PacketPlayOutNamedEntitySpawn(PacketDataSerializer var0) {
      this.a = var0.m();
      this.b = var0.o();
      this.c = var0.readDouble();
      this.d = var0.readDouble();
      this.e = var0.readDouble();
      this.f = var0.readByte();
      this.g = var0.readByte();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.d(this.a);
      var0.a(this.b);
      var0.writeDouble(this.c);
      var0.writeDouble(this.d);
      var0.writeDouble(this.e);
      var0.writeByte(this.f);
      var0.writeByte(this.g);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public int a() {
      return this.a;
   }

   public UUID c() {
      return this.b;
   }

   public double d() {
      return this.c;
   }

   public double e() {
      return this.d;
   }

   public double f() {
      return this.e;
   }

   public byte g() {
      return this.f;
   }

   public byte h() {
      return this.g;
   }
}
