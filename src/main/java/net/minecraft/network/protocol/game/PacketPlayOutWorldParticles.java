package net.minecraft.network.protocol.game;

import net.minecraft.core.particles.Particle;
import net.minecraft.core.particles.ParticleParam;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;

public class PacketPlayOutWorldParticles implements Packet<PacketListenerPlayOut> {
   private final double a;
   private final double b;
   private final double c;
   private final float d;
   private final float e;
   private final float f;
   private final float g;
   private final int h;
   private final boolean i;
   private final ParticleParam j;

   public <T extends ParticleParam> PacketPlayOutWorldParticles(
      T var0, boolean var1, double var2, double var4, double var6, float var8, float var9, float var10, float var11, int var12
   ) {
      this.j = var0;
      this.i = var1;
      this.a = var2;
      this.b = var4;
      this.c = var6;
      this.d = var8;
      this.e = var9;
      this.f = var10;
      this.g = var11;
      this.h = var12;
   }

   public PacketPlayOutWorldParticles(PacketDataSerializer var0) {
      Particle<?> var1 = var0.a(BuiltInRegistries.k);
      this.i = var0.readBoolean();
      this.a = var0.readDouble();
      this.b = var0.readDouble();
      this.c = var0.readDouble();
      this.d = var0.readFloat();
      this.e = var0.readFloat();
      this.f = var0.readFloat();
      this.g = var0.readFloat();
      this.h = var0.readInt();
      this.j = this.a(var0, var1);
   }

   private <T extends ParticleParam> T a(PacketDataSerializer var0, Particle<T> var1) {
      return var1.d().b(var1, var0);
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.a(BuiltInRegistries.k, this.j.b());
      var0.writeBoolean(this.i);
      var0.writeDouble(this.a);
      var0.writeDouble(this.b);
      var0.writeDouble(this.c);
      var0.writeFloat(this.d);
      var0.writeFloat(this.e);
      var0.writeFloat(this.f);
      var0.writeFloat(this.g);
      var0.writeInt(this.h);
      this.j.a(var0);
   }

   public boolean a() {
      return this.i;
   }

   public double c() {
      return this.a;
   }

   public double d() {
      return this.b;
   }

   public double e() {
      return this.c;
   }

   public float f() {
      return this.d;
   }

   public float g() {
      return this.e;
   }

   public float h() {
      return this.f;
   }

   public float i() {
      return this.g;
   }

   public int j() {
      return this.h;
   }

   public ParticleParam k() {
      return this.j;
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }
}
