package net.minecraft.world.phys;

import net.minecraft.world.entity.Entity;

public abstract class MovingObjectPosition {
   protected final Vec3D a;

   protected MovingObjectPosition(Vec3D var0) {
      this.a = var0;
   }

   public double a(Entity var0) {
      double var1 = this.a.c - var0.dl();
      double var3 = this.a.d - var0.dn();
      double var5 = this.a.e - var0.dr();
      return var1 * var1 + var3 * var3 + var5 * var5;
   }

   public abstract MovingObjectPosition.EnumMovingObjectType c();

   public Vec3D e() {
      return this.a;
   }

   public static enum EnumMovingObjectType {
      a,
      b,
      c;
   }
}
