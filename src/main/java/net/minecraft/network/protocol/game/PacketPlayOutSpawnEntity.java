package net.minecraft.network.protocol.game;

import java.util.UUID;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.phys.Vec3D;

public class PacketPlayOutSpawnEntity implements Packet<PacketListenerPlayOut> {
   private static final double a = 8000.0;
   private static final double b = 3.9;
   private final int c;
   private final UUID d;
   private final EntityTypes<?> e;
   private final double f;
   private final double g;
   private final double h;
   private final int i;
   private final int j;
   private final int k;
   private final byte l;
   private final byte m;
   private final byte n;
   private final int o;

   public PacketPlayOutSpawnEntity(Entity var0) {
      this(var0, 0);
   }

   public PacketPlayOutSpawnEntity(Entity var0, int var1) {
      this(var0.af(), var0.cs(), var0.dl(), var0.dn(), var0.dr(), var0.dy(), var0.dw(), var0.ae(), var1, var0.dj(), (double)var0.ck());
   }

   public PacketPlayOutSpawnEntity(Entity var0, int var1, BlockPosition var2) {
      this(var0.af(), var0.cs(), (double)var2.u(), (double)var2.v(), (double)var2.w(), var0.dy(), var0.dw(), var0.ae(), var1, var0.dj(), (double)var0.ck());
   }

   public PacketPlayOutSpawnEntity(
      int var0, UUID var1, double var2, double var4, double var6, float var8, float var9, EntityTypes<?> var10, int var11, Vec3D var12, double var13
   ) {
      this.c = var0;
      this.d = var1;
      this.f = var2;
      this.g = var4;
      this.h = var6;
      this.l = (byte)MathHelper.d(var8 * 256.0F / 360.0F);
      this.m = (byte)MathHelper.d(var9 * 256.0F / 360.0F);
      this.n = (byte)MathHelper.a(var13 * 256.0 / 360.0);
      this.e = var10;
      this.o = var11;
      this.i = (int)(MathHelper.a(var12.c, -3.9, 3.9) * 8000.0);
      this.j = (int)(MathHelper.a(var12.d, -3.9, 3.9) * 8000.0);
      this.k = (int)(MathHelper.a(var12.e, -3.9, 3.9) * 8000.0);
   }

   public PacketPlayOutSpawnEntity(PacketDataSerializer var0) {
      this.c = var0.m();
      this.d = var0.o();
      this.e = var0.a(BuiltInRegistries.h);
      this.f = var0.readDouble();
      this.g = var0.readDouble();
      this.h = var0.readDouble();
      this.l = var0.readByte();
      this.m = var0.readByte();
      this.n = var0.readByte();
      this.o = var0.m();
      this.i = var0.readShort();
      this.j = var0.readShort();
      this.k = var0.readShort();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.d(this.c);
      var0.a(this.d);
      var0.a(BuiltInRegistries.h, this.e);
      var0.writeDouble(this.f);
      var0.writeDouble(this.g);
      var0.writeDouble(this.h);
      var0.writeByte(this.l);
      var0.writeByte(this.m);
      var0.writeByte(this.n);
      var0.d(this.o);
      var0.writeShort(this.i);
      var0.writeShort(this.j);
      var0.writeShort(this.k);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public int a() {
      return this.c;
   }

   public UUID c() {
      return this.d;
   }

   public EntityTypes<?> d() {
      return this.e;
   }

   public double e() {
      return this.f;
   }

   public double f() {
      return this.g;
   }

   public double g() {
      return this.h;
   }

   public double h() {
      return (double)this.i / 8000.0;
   }

   public double i() {
      return (double)this.j / 8000.0;
   }

   public double j() {
      return (double)this.k / 8000.0;
   }

   public float k() {
      return (float)(this.l * 360) / 256.0F;
   }

   public float l() {
      return (float)(this.m * 360) / 256.0F;
   }

   public float m() {
      return (float)(this.n * 360) / 256.0F;
   }

   public int n() {
      return this.o;
   }
}
