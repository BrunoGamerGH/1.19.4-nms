package net.minecraft.world.phys;

import net.minecraft.util.MathHelper;

public class Vec2F {
   public static final Vec2F a = new Vec2F(0.0F, 0.0F);
   public static final Vec2F b = new Vec2F(1.0F, 1.0F);
   public static final Vec2F c = new Vec2F(1.0F, 0.0F);
   public static final Vec2F d = new Vec2F(-1.0F, 0.0F);
   public static final Vec2F e = new Vec2F(0.0F, 1.0F);
   public static final Vec2F f = new Vec2F(0.0F, -1.0F);
   public static final Vec2F g = new Vec2F(Float.MAX_VALUE, Float.MAX_VALUE);
   public static final Vec2F h = new Vec2F(Float.MIN_VALUE, Float.MIN_VALUE);
   public final float i;
   public final float j;

   public Vec2F(float var0, float var1) {
      this.i = var0;
      this.j = var1;
   }

   public Vec2F a(float var0) {
      return new Vec2F(this.i * var0, this.j * var0);
   }

   public float a(Vec2F var0) {
      return this.i * var0.i + this.j * var0.j;
   }

   public Vec2F b(Vec2F var0) {
      return new Vec2F(this.i + var0.i, this.j + var0.j);
   }

   public Vec2F b(float var0) {
      return new Vec2F(this.i + var0, this.j + var0);
   }

   public boolean c(Vec2F var0) {
      return this.i == var0.i && this.j == var0.j;
   }

   public Vec2F a() {
      float var0 = MathHelper.c(this.i * this.i + this.j * this.j);
      return var0 < 1.0E-4F ? a : new Vec2F(this.i / var0, this.j / var0);
   }

   public float b() {
      return MathHelper.c(this.i * this.i + this.j * this.j);
   }

   public float c() {
      return this.i * this.i + this.j * this.j;
   }

   public float d(Vec2F var0) {
      float var1 = var0.i - this.i;
      float var2 = var0.j - this.j;
      return var1 * var1 + var2 * var2;
   }

   public Vec2F d() {
      return new Vec2F(-this.i, -this.j);
   }
}
