package com.mojang.math;

import org.apache.commons.lang3.tuple.Triple;
import org.joml.Math;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class MatrixUtil {
   private static final float a = 3.0F + 2.0F * Math.sqrt(2.0F);
   private static final GivensParameters b = GivensParameters.a((float) (java.lang.Math.PI / 4));

   private MatrixUtil() {
   }

   public static Matrix4f a(Matrix4f var0, float var1) {
      return var0.set(
         var0.m00() * var1,
         var0.m01() * var1,
         var0.m02() * var1,
         var0.m03() * var1,
         var0.m10() * var1,
         var0.m11() * var1,
         var0.m12() * var1,
         var0.m13() * var1,
         var0.m20() * var1,
         var0.m21() * var1,
         var0.m22() * var1,
         var0.m23() * var1,
         var0.m30() * var1,
         var0.m31() * var1,
         var0.m32() * var1,
         var0.m33() * var1
      );
   }

   private static GivensParameters a(float var0, float var1, float var2) {
      float var3 = 2.0F * (var0 - var2);
      return a * var1 * var1 < var3 * var3 ? GivensParameters.a(var1, var3) : b;
   }

   private static GivensParameters a(float var0, float var1) {
      float var2 = (float)java.lang.Math.hypot((double)var0, (double)var1);
      float var3 = var2 > 1.0E-6F ? var1 : 0.0F;
      float var4 = Math.abs(var0) + Math.max(var2, 1.0E-6F);
      if (var0 < 0.0F) {
         float var5 = var3;
         var3 = var4;
         var4 = var5;
      }

      return GivensParameters.a(var3, var4);
   }

   private static void a(Matrix3f var0, Matrix3f var1) {
      var0.mul(var1);
      var1.transpose();
      var1.mul(var0);
      var0.set(var1);
   }

   private static void a(Matrix3f var0, Matrix3f var1, Quaternionf var2, Quaternionf var3) {
      if (var0.m01 * var0.m01 + var0.m10 * var0.m10 > 1.0E-6F) {
         GivensParameters var4 = a(var0.m00, 0.5F * (var0.m01 + var0.m10), var0.m11);
         Quaternionf var5 = var4.c(var2);
         var3.mul(var5);
         var4.c(var1);
         a(var0, var1);
      }

      if (var0.m02 * var0.m02 + var0.m20 * var0.m20 > 1.0E-6F) {
         GivensParameters var4 = a(var0.m00, 0.5F * (var0.m02 + var0.m20), var0.m22).a();
         Quaternionf var5 = var4.b(var2);
         var3.mul(var5);
         var4.b(var1);
         a(var0, var1);
      }

      if (var0.m12 * var0.m12 + var0.m21 * var0.m21 > 1.0E-6F) {
         GivensParameters var4 = a(var0.m11, 0.5F * (var0.m12 + var0.m21), var0.m22);
         Quaternionf var5 = var4.a(var2);
         var3.mul(var5);
         var4.a(var1);
         a(var0, var1);
      }
   }

   public static Quaternionf a(Matrix3f var0, int var1) {
      Quaternionf var2 = new Quaternionf();
      Matrix3f var3 = new Matrix3f();
      Quaternionf var4 = new Quaternionf();

      for(int var5 = 0; var5 < var1; ++var5) {
         a(var0, var3, var4, var2);
      }

      var2.normalize();
      return var2;
   }

   public static Triple<Quaternionf, Vector3f, Quaternionf> a(Matrix3f var0) {
      Matrix3f var1 = new Matrix3f(var0);
      var1.transpose();
      var1.mul(var0);
      Quaternionf var2 = a(var1, 5);
      boolean var3 = (double)var1.m00 < 1.0E-6;
      boolean var4 = (double)var1.m11 < 1.0E-6;
      Matrix3f var6 = var0.rotate(var2);
      float var7 = 1.0F;
      Quaternionf var8 = new Quaternionf();
      Quaternionf var9 = new Quaternionf();
      GivensParameters var10;
      if (var3) {
         var10 = a(var6.m11, -var6.m10);
      } else {
         var10 = a(var6.m00, var6.m01);
      }

      Quaternionf var11 = var10.c(var9);
      Matrix3f var12 = var10.c(var1);
      var7 *= var12.m22;
      var8.mul(var11);
      var12.transpose().mul(var6);
      if (var3) {
         var10 = a(var12.m22, -var12.m20);
      } else {
         var10 = a(var12.m00, var12.m02);
      }

      var10 = var10.a();
      Quaternionf var13 = var10.b(var9);
      Matrix3f var14 = var10.b(var6);
      var7 *= var14.m11;
      var8.mul(var13);
      var14.transpose().mul(var12);
      if (var4) {
         var10 = a(var14.m22, -var14.m21);
      } else {
         var10 = a(var14.m11, var14.m12);
      }

      Quaternionf var15 = var10.a(var9);
      Matrix3f var16 = var10.a(var12);
      var7 *= var16.m00;
      var8.mul(var15);
      var16.transpose().mul(var14);
      var7 = 1.0F / var7;
      var8.mul(Math.sqrt(var7));
      Vector3f var17 = new Vector3f(var16.m00 * var7, var16.m11 * var7, var16.m22 * var7);
      return Triple.of(var8, var17, var2.conjugate());
   }
}
