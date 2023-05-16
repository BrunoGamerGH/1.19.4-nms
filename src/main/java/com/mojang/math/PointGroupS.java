package com.mojang.math;

import java.util.Arrays;
import net.minecraft.SystemUtils;
import org.joml.Matrix3f;

public enum PointGroupS {
   a(0, 1, 2),
   b(1, 0, 2),
   c(0, 2, 1),
   d(1, 2, 0),
   e(2, 0, 1),
   f(2, 1, 0);

   private final int[] g;
   private final Matrix3f h;
   private static final int i = 3;
   private static final PointGroupS[][] j = SystemUtils.a(new PointGroupS[values().length][values().length], var0 -> {
      for(PointGroupS var4 : values()) {
         for(PointGroupS var8 : values()) {
            int[] var9 = new int[3];

            for(int var10 = 0; var10 < 3; ++var10) {
               var9[var10] = var4.g[var8.g[var10]];
            }

            PointGroupS var10 = Arrays.stream(values()).filter(var1 -> Arrays.equals(var1.g, var9)).findFirst().get();
            var0[var4.ordinal()][var8.ordinal()] = var10;
         }
      }
   });

   private PointGroupS(int var2, int var3, int var4) {
      this.g = new int[]{var2, var3, var4};
      this.h = new Matrix3f();
      this.h.set(this.a(0), 0, 1.0F);
      this.h.set(this.a(1), 1, 1.0F);
      this.h.set(this.a(2), 2, 1.0F);
   }

   public PointGroupS a(PointGroupS var0) {
      return j[this.ordinal()][var0.ordinal()];
   }

   public int a(int var0) {
      return this.g[var0];
   }

   public Matrix3f a() {
      return this.h;
   }
}
