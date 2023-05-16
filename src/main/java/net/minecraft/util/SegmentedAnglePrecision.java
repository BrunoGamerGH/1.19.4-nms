package net.minecraft.util;

import net.minecraft.core.EnumDirection;

public class SegmentedAnglePrecision {
   private final int a;
   private final int b;
   private final float c;
   private final float d;

   public SegmentedAnglePrecision(int var0) {
      if (var0 < 2) {
         throw new IllegalArgumentException("Precision cannot be less than 2 bits");
      } else if (var0 > 30) {
         throw new IllegalArgumentException("Precision cannot be greater than 30 bits");
      } else {
         int var1 = 1 << var0;
         this.a = var1 - 1;
         this.b = var0;
         this.c = (float)var1 / 360.0F;
         this.d = 360.0F / (float)var1;
      }
   }

   public boolean a(int var0, int var1) {
      int var2 = this.a() >> 1;
      return (var0 & var2) == (var1 & var2);
   }

   public int a(EnumDirection var0) {
      if (var0.o().b()) {
         return 0;
      } else {
         int var1 = var0.e();
         return var1 << this.b - 2;
      }
   }

   public int a(float var0) {
      return Math.round(var0 * this.c);
   }

   public int b(float var0) {
      return this.c(this.a(var0));
   }

   public float a(int var0) {
      return (float)var0 * this.d;
   }

   public float b(int var0) {
      float var1 = this.a(this.c(var0));
      return var1 >= 180.0F ? var1 - 360.0F : var1;
   }

   public int c(int var0) {
      return var0 & this.a;
   }

   public int a() {
      return this.a;
   }
}
