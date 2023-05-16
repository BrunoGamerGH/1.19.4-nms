package net.minecraft.world.phys.shapes;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.math.DoubleMath;
import com.google.common.math.IntMath;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.util.Arrays;
import java.util.Objects;
import net.minecraft.SystemUtils;
import net.minecraft.core.EnumAxisCycle;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.phys.AxisAlignedBB;

public final class VoxelShapes {
   public static final double a = 1.0E-7;
   public static final double b = 1.0E-6;
   private static final VoxelShape d = SystemUtils.a(() -> {
      VoxelShapeDiscrete var0 = new VoxelShapeBitSet(1, 1, 1);
      var0.c(0, 0, 0);
      return new VoxelShapeCube(var0);
   });
   public static final VoxelShape c = a(
      Double.NEGATIVE_INFINITY,
      Double.NEGATIVE_INFINITY,
      Double.NEGATIVE_INFINITY,
      Double.POSITIVE_INFINITY,
      Double.POSITIVE_INFINITY,
      Double.POSITIVE_INFINITY
   );
   private static final VoxelShape e = new VoxelShapeArray(
      new VoxelShapeBitSet(0, 0, 0), new DoubleArrayList(new double[]{0.0}), new DoubleArrayList(new double[]{0.0}), new DoubleArrayList(new double[]{0.0})
   );

   public static VoxelShape a() {
      return e;
   }

   public static VoxelShape b() {
      return d;
   }

   public static VoxelShape a(double var0, double var2, double var4, double var6, double var8, double var10) {
      if (!(var0 > var6) && !(var2 > var8) && !(var4 > var10)) {
         return b(var0, var2, var4, var6, var8, var10);
      } else {
         throw new IllegalArgumentException("The min values need to be smaller or equals to the max values");
      }
   }

   public static VoxelShape b(double var0, double var2, double var4, double var6, double var8, double var10) {
      if (!(var6 - var0 < 1.0E-7) && !(var8 - var2 < 1.0E-7) && !(var10 - var4 < 1.0E-7)) {
         int var12 = a(var0, var6);
         int var13 = a(var2, var8);
         int var14 = a(var4, var10);
         if (var12 < 0 || var13 < 0 || var14 < 0) {
            return new VoxelShapeArray(
               d.a,
               DoubleArrayList.wrap(new double[]{var0, var6}),
               DoubleArrayList.wrap(new double[]{var2, var8}),
               DoubleArrayList.wrap(new double[]{var4, var10})
            );
         } else if (var12 == 0 && var13 == 0 && var14 == 0) {
            return b();
         } else {
            int var15 = 1 << var12;
            int var16 = 1 << var13;
            int var17 = 1 << var14;
            VoxelShapeBitSet var18 = VoxelShapeBitSet.a(
               var15,
               var16,
               var17,
               (int)Math.round(var0 * (double)var15),
               (int)Math.round(var2 * (double)var16),
               (int)Math.round(var4 * (double)var17),
               (int)Math.round(var6 * (double)var15),
               (int)Math.round(var8 * (double)var16),
               (int)Math.round(var10 * (double)var17)
            );
            return new VoxelShapeCube(var18);
         }
      } else {
         return a();
      }
   }

   public static VoxelShape a(AxisAlignedBB var0) {
      return b(var0.a, var0.b, var0.c, var0.d, var0.e, var0.f);
   }

   @VisibleForTesting
   protected static int a(double var0, double var2) {
      if (!(var0 < -1.0E-7) && !(var2 > 1.0000001)) {
         for(int var4 = 0; var4 <= 3; ++var4) {
            int var5 = 1 << var4;
            double var6 = var0 * (double)var5;
            double var8 = var2 * (double)var5;
            boolean var10 = Math.abs(var6 - (double)Math.round(var6)) < 1.0E-7 * (double)var5;
            boolean var11 = Math.abs(var8 - (double)Math.round(var8)) < 1.0E-7 * (double)var5;
            if (var10 && var11) {
               return var4;
            }
         }

         return -1;
      } else {
         return -1;
      }
   }

   protected static long a(int var0, int var1) {
      return (long)var0 * (long)(var1 / IntMath.gcd(var0, var1));
   }

   public static VoxelShape a(VoxelShape var0, VoxelShape var1) {
      return a(var0, var1, OperatorBoolean.o);
   }

   public static VoxelShape a(VoxelShape var0, VoxelShape... var1) {
      return Arrays.stream(var1).reduce(var0, VoxelShapes::a);
   }

   public static VoxelShape a(VoxelShape var0, VoxelShape var1, OperatorBoolean var2) {
      return b(var0, var1, var2).c();
   }

   public static VoxelShape b(VoxelShape var0, VoxelShape var1, OperatorBoolean var2) {
      if (var2.apply(false, false)) {
         throw (IllegalArgumentException)SystemUtils.b(new IllegalArgumentException());
      } else if (var0 == var1) {
         return var2.apply(true, true) ? var0 : a();
      } else {
         boolean var3 = var2.apply(true, false);
         boolean var4 = var2.apply(false, true);
         if (var0.b()) {
            return var4 ? var1 : a();
         } else if (var1.b()) {
            return var3 ? var0 : a();
         } else {
            VoxelShapeMerger var5 = a(1, var0.a(EnumDirection.EnumAxis.a), var1.a(EnumDirection.EnumAxis.a), var3, var4);
            VoxelShapeMerger var6 = a(var5.size() - 1, var0.a(EnumDirection.EnumAxis.b), var1.a(EnumDirection.EnumAxis.b), var3, var4);
            VoxelShapeMerger var7 = a((var5.size() - 1) * (var6.size() - 1), var0.a(EnumDirection.EnumAxis.c), var1.a(EnumDirection.EnumAxis.c), var3, var4);
            VoxelShapeBitSet var8 = VoxelShapeBitSet.a(var0.a, var1.a, var5, var6, var7, var2);
            return (VoxelShape)(var5 instanceof VoxelShapeCubeMerger && var6 instanceof VoxelShapeCubeMerger && var7 instanceof VoxelShapeCubeMerger
               ? new VoxelShapeCube(var8)
               : new VoxelShapeArray(var8, var5.a(), var6.a(), var7.a()));
         }
      }
   }

   public static boolean c(VoxelShape var0, VoxelShape var1, OperatorBoolean var2) {
      if (var2.apply(false, false)) {
         throw (IllegalArgumentException)SystemUtils.b(new IllegalArgumentException());
      } else {
         boolean var3 = var0.b();
         boolean var4 = var1.b();
         if (!var3 && !var4) {
            if (var0 == var1) {
               return var2.apply(true, true);
            } else {
               boolean var5 = var2.apply(true, false);
               boolean var6 = var2.apply(false, true);

               for(EnumDirection.EnumAxis var10 : EnumAxisCycle.d) {
                  if (var0.c(var10) < var1.b(var10) - 1.0E-7) {
                     return var5 || var6;
                  }

                  if (var1.c(var10) < var0.b(var10) - 1.0E-7) {
                     return var5 || var6;
                  }
               }

               VoxelShapeMerger var7 = a(1, var0.a(EnumDirection.EnumAxis.a), var1.a(EnumDirection.EnumAxis.a), var5, var6);
               VoxelShapeMerger var8 = a(var7.size() - 1, var0.a(EnumDirection.EnumAxis.b), var1.a(EnumDirection.EnumAxis.b), var5, var6);
               VoxelShapeMerger var9 = a((var7.size() - 1) * (var8.size() - 1), var0.a(EnumDirection.EnumAxis.c), var1.a(EnumDirection.EnumAxis.c), var5, var6);
               return a(var7, var8, var9, var0.a, var1.a, var2);
            }
         } else {
            return var2.apply(!var3, !var4);
         }
      }
   }

   private static boolean a(
      VoxelShapeMerger var0, VoxelShapeMerger var1, VoxelShapeMerger var2, VoxelShapeDiscrete var3, VoxelShapeDiscrete var4, OperatorBoolean var5
   ) {
      return !var0.a(
         (var5x, var6, var7) -> var1.a(
               (var6x, var7x, var8) -> var2.a((var7xx, var8x, var9) -> !var5.apply(var3.d(var5x, var6x, var7xx), var4.d(var6, var7x, var8x)))
            )
      );
   }

   public static double a(EnumDirection.EnumAxis var0, AxisAlignedBB var1, Iterable<VoxelShape> var2, double var3) {
      for(VoxelShape var6 : var2) {
         if (Math.abs(var3) < 1.0E-7) {
            return 0.0;
         }

         var3 = var6.a(var0, var1, var3);
      }

      return var3;
   }

   public static boolean a(VoxelShape var0, VoxelShape var1, EnumDirection var2) {
      if (var0 == b() && var1 == b()) {
         return true;
      } else if (var1.b()) {
         return false;
      } else {
         EnumDirection.EnumAxis var3 = var2.o();
         EnumDirection.EnumAxisDirection var4 = var2.f();
         VoxelShape var5 = var4 == EnumDirection.EnumAxisDirection.a ? var0 : var1;
         VoxelShape var6 = var4 == EnumDirection.EnumAxisDirection.a ? var1 : var0;
         OperatorBoolean var7 = var4 == EnumDirection.EnumAxisDirection.a ? OperatorBoolean.e : OperatorBoolean.c;
         return DoubleMath.fuzzyEquals(var5.c(var3), 1.0, 1.0E-7)
            && DoubleMath.fuzzyEquals(var6.b(var3), 0.0, 1.0E-7)
            && !c(new VoxelShapeSlice(var5, var3, var5.a.c(var3) - 1), new VoxelShapeSlice(var6, var3, 0), var7);
      }
   }

   public static VoxelShape a(VoxelShape var0, EnumDirection var1) {
      if (var0 == b()) {
         return b();
      } else {
         EnumDirection.EnumAxis var4 = var1.o();
         boolean var2;
         int var3;
         if (var1.f() == EnumDirection.EnumAxisDirection.a) {
            var2 = DoubleMath.fuzzyEquals(var0.c(var4), 1.0, 1.0E-7);
            var3 = var0.a.c(var4) - 1;
         } else {
            var2 = DoubleMath.fuzzyEquals(var0.b(var4), 0.0, 1.0E-7);
            var3 = 0;
         }

         return (VoxelShape)(!var2 ? a() : new VoxelShapeSlice(var0, var4, var3));
      }
   }

   public static boolean b(VoxelShape var0, VoxelShape var1, EnumDirection var2) {
      if (var0 != b() && var1 != b()) {
         EnumDirection.EnumAxis var3 = var2.o();
         EnumDirection.EnumAxisDirection var4 = var2.f();
         VoxelShape var5 = var4 == EnumDirection.EnumAxisDirection.a ? var0 : var1;
         VoxelShape var6 = var4 == EnumDirection.EnumAxisDirection.a ? var1 : var0;
         if (!DoubleMath.fuzzyEquals(var5.c(var3), 1.0, 1.0E-7)) {
            var5 = a();
         }

         if (!DoubleMath.fuzzyEquals(var6.b(var3), 0.0, 1.0E-7)) {
            var6 = a();
         }

         return !c(b(), b(new VoxelShapeSlice(var5, var3, var5.a.c(var3) - 1), new VoxelShapeSlice(var6, var3, 0), OperatorBoolean.o), OperatorBoolean.e);
      } else {
         return true;
      }
   }

   public static boolean b(VoxelShape var0, VoxelShape var1) {
      if (var0 == b() || var1 == b()) {
         return true;
      } else if (var0.b() && var1.b()) {
         return false;
      } else {
         return !c(b(), b(var0, var1, OperatorBoolean.o), OperatorBoolean.e);
      }
   }

   @VisibleForTesting
   protected static VoxelShapeMerger a(int var0, DoubleList var1, DoubleList var2, boolean var3, boolean var4) {
      int var5 = var1.size() - 1;
      int var6 = var2.size() - 1;
      if (var1 instanceof VoxelShapeCubePoint && var2 instanceof VoxelShapeCubePoint) {
         long var7 = a(var5, var6);
         if ((long)var0 * var7 <= 256L) {
            return new VoxelShapeCubeMerger(var5, var6);
         }
      }

      if (var1.getDouble(var5) < var2.getDouble(0) - 1.0E-7) {
         return new VoxelShapeMergerDisjoint(var1, var2, false);
      } else if (var2.getDouble(var6) < var1.getDouble(0) - 1.0E-7) {
         return new VoxelShapeMergerDisjoint(var2, var1, true);
      } else {
         return (VoxelShapeMerger)(var5 == var6 && Objects.equals(var1, var2)
            ? new VoxelShapeMergerIdentical(var1)
            : new VoxelShapeMergerList(var1, var2, var3, var4));
      }
   }

   public interface a {
      void consume(double var1, double var3, double var5, double var7, double var9, double var11);
   }
}
