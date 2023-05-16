package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EntityExperienceOrb;

public class PacketPlayOutSpawnEntityExperienceOrb implements Packet<PacketListenerPlayOut> {
   private final int a;
   private final double b;
   private final double c;
   private final double d;
   private final int e;

   public PacketPlayOutSpawnEntityExperienceOrb(EntityExperienceOrb var0) {
      this.a = var0.af();
      this.b = var0.dl();
      this.c = var0.dn();
      this.d = var0.dr();
      this.e = var0.i();
   }

   public PacketPlayOutSpawnEntityExperienceOrb(PacketDataSerializer var0) {
      this.a = var0.m();
      this.b = var0.readDouble();
      this.c = var0.readDouble();
      this.d = var0.readDouble();
      this.e = var0.readShort();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.d(this.a);
      var0.writeDouble(this.b);
      var0.writeDouble(this.c);
      var0.writeDouble(this.d);
      var0.writeShort(this.e);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public int a() {
      return this.a;
   }

   public double c() {
      return this.b;
   }

   public double d() {
      return this.c;
   }

   public double e() {
      return this.d;
   }

   public int f() {
      return this.e;
   }
}
