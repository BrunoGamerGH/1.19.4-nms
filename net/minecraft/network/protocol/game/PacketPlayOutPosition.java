package net.minecraft.network.protocol.game;

import java.util.Set;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.RelativeMovement;

public class PacketPlayOutPosition implements Packet<PacketListenerPlayOut> {
   private final double a;
   private final double b;
   private final double c;
   private final float d;
   private final float e;
   private final Set<RelativeMovement> f;
   private final int g;

   public PacketPlayOutPosition(double var0, double var2, double var4, float var6, float var7, Set<RelativeMovement> var8, int var9) {
      this.a = var0;
      this.b = var2;
      this.c = var4;
      this.d = var6;
      this.e = var7;
      this.f = var8;
      this.g = var9;
   }

   public PacketPlayOutPosition(PacketDataSerializer var0) {
      this.a = var0.readDouble();
      this.b = var0.readDouble();
      this.c = var0.readDouble();
      this.d = var0.readFloat();
      this.e = var0.readFloat();
      this.f = RelativeMovement.a(var0.readUnsignedByte());
      this.g = var0.m();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.writeDouble(this.a);
      var0.writeDouble(this.b);
      var0.writeDouble(this.c);
      var0.writeFloat(this.d);
      var0.writeFloat(this.e);
      var0.writeByte(RelativeMovement.a(this.f));
      var0.d(this.g);
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

   public double d() {
      return this.c;
   }

   public float e() {
      return this.d;
   }

   public float f() {
      return this.e;
   }

   public int g() {
      return this.g;
   }

   public Set<RelativeMovement> h() {
      return this.f;
   }
}
