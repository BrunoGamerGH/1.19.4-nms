package net.minecraft.world.entity;

import net.minecraft.util.MathHelper;

public class WalkAnimationState {
   private float a;
   private float b;
   private float c;

   public void a(float var0) {
      this.b = var0;
   }

   public void a(float var0, float var1) {
      this.a = this.b;
      this.b += (var0 - this.b) * var1;
      this.c += this.b;
   }

   public float a() {
      return this.b;
   }

   public float b(float var0) {
      return MathHelper.i(var0, this.a, this.b);
   }

   public float b() {
      return this.c;
   }

   public float c(float var0) {
      return this.c - this.b * (1.0F - var0);
   }

   public boolean c() {
      return this.b > 1.0E-5F;
   }
}
