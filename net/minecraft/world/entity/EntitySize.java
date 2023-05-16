package net.minecraft.world.entity;

import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec3D;

public class EntitySize {
   public final float a;
   public final float b;
   public final boolean c;

   public EntitySize(float var0, float var1, boolean var2) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
   }

   public AxisAlignedBB a(Vec3D var0) {
      return this.a(var0.c, var0.d, var0.e);
   }

   public AxisAlignedBB a(double var0, double var2, double var4) {
      float var6 = this.a / 2.0F;
      float var7 = this.b;
      return new AxisAlignedBB(var0 - (double)var6, var2, var4 - (double)var6, var0 + (double)var6, var2 + (double)var7, var4 + (double)var6);
   }

   public EntitySize a(float var0) {
      return this.a(var0, var0);
   }

   public EntitySize a(float var0, float var1) {
      return !this.c && (var0 != 1.0F || var1 != 1.0F) ? b(this.a * var0, this.b * var1) : this;
   }

   public static EntitySize b(float var0, float var1) {
      return new EntitySize(var0, var1, false);
   }

   public static EntitySize c(float var0, float var1) {
      return new EntitySize(var0, var1, true);
   }

   @Override
   public String toString() {
      return "EntityDimensions w=" + this.a + ", h=" + this.b + ", fixed=" + this.c;
   }
}
