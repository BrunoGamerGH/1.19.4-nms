package com.mojang.math;

import org.joml.Math;
import org.joml.Matrix3f;
import org.joml.Quaternionf;

public record GivensParameters(float sinHalf, float cosHalf) {
   private final float a;
   private final float b;

   public GivensParameters(float var0, float var1) {
      this.a = var0;
      this.b = var1;
   }

   public static GivensParameters a(float var0, float var1) {
      float var2 = Math.invsqrt(var0 * var0 + var1 * var1);
      return new GivensParameters(var2 * var0, var2 * var1);
   }

   public static GivensParameters a(float var0) {
      float var1 = Math.sin(var0 / 2.0F);
      float var2 = Math.cosFromSin(var1, var0 / 2.0F);
      return new GivensParameters(var1, var2);
   }

   public GivensParameters a() {
      return new GivensParameters(-this.a, this.b);
   }

   public Quaternionf a(Quaternionf var0) {
      return var0.set(this.a, 0.0F, 0.0F, this.b);
   }

   public Quaternionf b(Quaternionf var0) {
      return var0.set(0.0F, this.a, 0.0F, this.b);
   }

   public Quaternionf c(Quaternionf var0) {
      return var0.set(0.0F, 0.0F, this.a, this.b);
   }

   public float b() {
      return this.b * this.b - this.a * this.a;
   }

   public float c() {
      return 2.0F * this.a * this.b;
   }

   public Matrix3f a(Matrix3f var0) {
      var0.m01 = 0.0F;
      var0.m02 = 0.0F;
      var0.m10 = 0.0F;
      var0.m20 = 0.0F;
      float var1 = this.b();
      float var2 = this.c();
      var0.m11 = var1;
      var0.m22 = var1;
      var0.m12 = var2;
      var0.m21 = -var2;
      var0.m00 = 1.0F;
      return var0;
   }

   public Matrix3f b(Matrix3f var0) {
      var0.m01 = 0.0F;
      var0.m10 = 0.0F;
      var0.m12 = 0.0F;
      var0.m21 = 0.0F;
      float var1 = this.b();
      float var2 = this.c();
      var0.m00 = var1;
      var0.m22 = var1;
      var0.m02 = -var2;
      var0.m20 = var2;
      var0.m11 = 1.0F;
      return var0;
   }

   public Matrix3f c(Matrix3f var0) {
      var0.m02 = 0.0F;
      var0.m12 = 0.0F;
      var0.m20 = 0.0F;
      var0.m21 = 0.0F;
      float var1 = this.b();
      float var2 = this.c();
      var0.m00 = var1;
      var0.m11 = var1;
      var0.m01 = var2;
      var0.m10 = -var2;
      var0.m22 = 1.0F;
      return var0;
   }

   public float d() {
      return this.a;
   }

   public float e() {
      return this.b;
   }
}
