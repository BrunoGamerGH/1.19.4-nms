package net.minecraft.network.protocol.game;

import net.minecraft.network.PacketDataSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3D;

public class PacketPlayOutEntityVelocity implements Packet<PacketListenerPlayOut> {
   private final int a;
   private final int b;
   private final int c;
   private final int d;

   public PacketPlayOutEntityVelocity(Entity var0) {
      this(var0.af(), var0.dj());
   }

   public PacketPlayOutEntityVelocity(int var0, Vec3D var1) {
      this.a = var0;
      double var2 = 3.9;
      double var4 = MathHelper.a(var1.c, -3.9, 3.9);
      double var6 = MathHelper.a(var1.d, -3.9, 3.9);
      double var8 = MathHelper.a(var1.e, -3.9, 3.9);
      this.b = (int)(var4 * 8000.0);
      this.c = (int)(var6 * 8000.0);
      this.d = (int)(var8 * 8000.0);
   }

   public PacketPlayOutEntityVelocity(PacketDataSerializer var0) {
      this.a = var0.m();
      this.b = var0.readShort();
      this.c = var0.readShort();
      this.d = var0.readShort();
   }

   @Override
   public void a(PacketDataSerializer var0) {
      var0.d(this.a);
      var0.writeShort(this.b);
      var0.writeShort(this.c);
      var0.writeShort(this.d);
   }

   public void a(PacketListenerPlayOut var0) {
      var0.a(this);
   }

   public int a() {
      return this.a;
   }

   public int c() {
      return this.b;
   }

   public int d() {
      return this.c;
   }

   public int e() {
      return this.d;
   }
}
