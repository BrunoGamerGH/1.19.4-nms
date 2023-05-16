package net.minecraft.world.phys;

import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.MathHelper;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;

public class AxisAlignedBB {
   private static final double g = 1.0E-7;
   public final double a;
   public final double b;
   public final double c;
   public final double d;
   public final double e;
   public final double f;

   public AxisAlignedBB(double var0, double var2, double var4, double var6, double var8, double var10) {
      this.a = Math.min(var0, var6);
      this.b = Math.min(var2, var8);
      this.c = Math.min(var4, var10);
      this.d = Math.max(var0, var6);
      this.e = Math.max(var2, var8);
      this.f = Math.max(var4, var10);
   }

   public AxisAlignedBB(BlockPosition var0) {
      this((double)var0.u(), (double)var0.v(), (double)var0.w(), (double)(var0.u() + 1), (double)(var0.v() + 1), (double)(var0.w() + 1));
   }

   public AxisAlignedBB(BlockPosition var0, BlockPosition var1) {
      this((double)var0.u(), (double)var0.v(), (double)var0.w(), (double)var1.u(), (double)var1.v(), (double)var1.w());
   }

   public AxisAlignedBB(Vec3D var0, Vec3D var1) {
      this(var0.c, var0.d, var0.e, var1.c, var1.d, var1.e);
   }

   public static AxisAlignedBB a(StructureBoundingBox var0) {
      return new AxisAlignedBB((double)var0.g(), (double)var0.h(), (double)var0.i(), (double)(var0.j() + 1), (double)(var0.k() + 1), (double)(var0.l() + 1));
   }

   public static AxisAlignedBB a(Vec3D var0) {
      return new AxisAlignedBB(var0.c, var0.d, var0.e, var0.c + 1.0, var0.d + 1.0, var0.e + 1.0);
   }

   public AxisAlignedBB a(double var0) {
      return new AxisAlignedBB(var0, this.b, this.c, this.d, this.e, this.f);
   }

   public AxisAlignedBB b(double var0) {
      return new AxisAlignedBB(this.a, var0, this.c, this.d, this.e, this.f);
   }

   public AxisAlignedBB c(double var0) {
      return new AxisAlignedBB(this.a, this.b, var0, this.d, this.e, this.f);
   }

   public AxisAlignedBB d(double var0) {
      return new AxisAlignedBB(this.a, this.b, this.c, var0, this.e, this.f);
   }

   public AxisAlignedBB e(double var0) {
      return new AxisAlignedBB(this.a, this.b, this.c, this.d, var0, this.f);
   }

   public AxisAlignedBB f(double var0) {
      return new AxisAlignedBB(this.a, this.b, this.c, this.d, this.e, var0);
   }

   public double a(EnumDirection.EnumAxis var0) {
      return var0.a(this.a, this.b, this.c);
   }

   public double b(EnumDirection.EnumAxis var0) {
      return var0.a(this.d, this.e, this.f);
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else if (!(var0 instanceof AxisAlignedBB)) {
         return false;
      } else {
         AxisAlignedBB var1 = (AxisAlignedBB)var0;
         if (Double.compare(var1.a, this.a) != 0) {
            return false;
         } else if (Double.compare(var1.b, this.b) != 0) {
            return false;
         } else if (Double.compare(var1.c, this.c) != 0) {
            return false;
         } else if (Double.compare(var1.d, this.d) != 0) {
            return false;
         } else if (Double.compare(var1.e, this.e) != 0) {
            return false;
         } else {
            return Double.compare(var1.f, this.f) == 0;
         }
      }
   }

   @Override
   public int hashCode() {
      long var0 = Double.doubleToLongBits(this.a);
      int var2 = (int)(var0 ^ var0 >>> 32);
      var0 = Double.doubleToLongBits(this.b);
      var2 = 31 * var2 + (int)(var0 ^ var0 >>> 32);
      var0 = Double.doubleToLongBits(this.c);
      var2 = 31 * var2 + (int)(var0 ^ var0 >>> 32);
      var0 = Double.doubleToLongBits(this.d);
      var2 = 31 * var2 + (int)(var0 ^ var0 >>> 32);
      var0 = Double.doubleToLongBits(this.e);
      var2 = 31 * var2 + (int)(var0 ^ var0 >>> 32);
      var0 = Double.doubleToLongBits(this.f);
      return 31 * var2 + (int)(var0 ^ var0 >>> 32);
   }

   public AxisAlignedBB a(double var0, double var2, double var4) {
      double var6 = this.a;
      double var8 = this.b;
      double var10 = this.c;
      double var12 = this.d;
      double var14 = this.e;
      double var16 = this.f;
      if (var0 < 0.0) {
         var6 -= var0;
      } else if (var0 > 0.0) {
         var12 -= var0;
      }

      if (var2 < 0.0) {
         var8 -= var2;
      } else if (var2 > 0.0) {
         var14 -= var2;
      }

      if (var4 < 0.0) {
         var10 -= var4;
      } else if (var4 > 0.0) {
         var16 -= var4;
      }

      return new AxisAlignedBB(var6, var8, var10, var12, var14, var16);
   }

   public AxisAlignedBB b(Vec3D var0) {
      return this.b(var0.c, var0.d, var0.e);
   }

   public AxisAlignedBB b(double var0, double var2, double var4) {
      double var6 = this.a;
      double var8 = this.b;
      double var10 = this.c;
      double var12 = this.d;
      double var14 = this.e;
      double var16 = this.f;
      if (var0 < 0.0) {
         var6 += var0;
      } else if (var0 > 0.0) {
         var12 += var0;
      }

      if (var2 < 0.0) {
         var8 += var2;
      } else if (var2 > 0.0) {
         var14 += var2;
      }

      if (var4 < 0.0) {
         var10 += var4;
      } else if (var4 > 0.0) {
         var16 += var4;
      }

      return new AxisAlignedBB(var6, var8, var10, var12, var14, var16);
   }

   public AxisAlignedBB c(double var0, double var2, double var4) {
      double var6 = this.a - var0;
      double var8 = this.b - var2;
      double var10 = this.c - var4;
      double var12 = this.d + var0;
      double var14 = this.e + var2;
      double var16 = this.f + var4;
      return new AxisAlignedBB(var6, var8, var10, var12, var14, var16);
   }

   public AxisAlignedBB g(double var0) {
      return this.c(var0, var0, var0);
   }

   public AxisAlignedBB a(AxisAlignedBB var0) {
      double var1 = Math.max(this.a, var0.a);
      double var3 = Math.max(this.b, var0.b);
      double var5 = Math.max(this.c, var0.c);
      double var7 = Math.min(this.d, var0.d);
      double var9 = Math.min(this.e, var0.e);
      double var11 = Math.min(this.f, var0.f);
      return new AxisAlignedBB(var1, var3, var5, var7, var9, var11);
   }

   public AxisAlignedBB b(AxisAlignedBB var0) {
      double var1 = Math.min(this.a, var0.a);
      double var3 = Math.min(this.b, var0.b);
      double var5 = Math.min(this.c, var0.c);
      double var7 = Math.max(this.d, var0.d);
      double var9 = Math.max(this.e, var0.e);
      double var11 = Math.max(this.f, var0.f);
      return new AxisAlignedBB(var1, var3, var5, var7, var9, var11);
   }

   public AxisAlignedBB d(double var0, double var2, double var4) {
      return new AxisAlignedBB(this.a + var0, this.b + var2, this.c + var4, this.d + var0, this.e + var2, this.f + var4);
   }

   public AxisAlignedBB a(BlockPosition var0) {
      return new AxisAlignedBB(
         this.a + (double)var0.u(),
         this.b + (double)var0.v(),
         this.c + (double)var0.w(),
         this.d + (double)var0.u(),
         this.e + (double)var0.v(),
         this.f + (double)var0.w()
      );
   }

   public AxisAlignedBB c(Vec3D var0) {
      return this.d(var0.c, var0.d, var0.e);
   }

   public boolean c(AxisAlignedBB var0) {
      return this.a(var0.a, var0.b, var0.c, var0.d, var0.e, var0.f);
   }

   public boolean a(double var0, double var2, double var4, double var6, double var8, double var10) {
      return this.a < var6 && this.d > var0 && this.b < var8 && this.e > var2 && this.c < var10 && this.f > var4;
   }

   public boolean a(Vec3D var0, Vec3D var1) {
      return this.a(
         Math.min(var0.c, var1.c),
         Math.min(var0.d, var1.d),
         Math.min(var0.e, var1.e),
         Math.max(var0.c, var1.c),
         Math.max(var0.d, var1.d),
         Math.max(var0.e, var1.e)
      );
   }

   public boolean d(Vec3D var0) {
      return this.e(var0.c, var0.d, var0.e);
   }

   public boolean e(double var0, double var2, double var4) {
      return var0 >= this.a && var0 < this.d && var2 >= this.b && var2 < this.e && var4 >= this.c && var4 < this.f;
   }

   public double a() {
      double var0 = this.b();
      double var2 = this.c();
      double var4 = this.d();
      return (var0 + var2 + var4) / 3.0;
   }

   public double b() {
      return this.d - this.a;
   }

   public double c() {
      return this.e - this.b;
   }

   public double d() {
      return this.f - this.c;
   }

   public AxisAlignedBB f(double var0, double var2, double var4) {
      return this.c(-var0, -var2, -var4);
   }

   public AxisAlignedBB h(double var0) {
      return this.g(-var0);
   }

   public Optional<Vec3D> b(Vec3D var0, Vec3D var1) {
      double[] var2 = new double[]{1.0};
      double var3 = var1.c - var0.c;
      double var5 = var1.d - var0.d;
      double var7 = var1.e - var0.e;
      EnumDirection var9 = a(this, var0, var2, null, var3, var5, var7);
      if (var9 == null) {
         return Optional.empty();
      } else {
         double var10 = var2[0];
         return Optional.of(var0.b(var10 * var3, var10 * var5, var10 * var7));
      }
   }

   @Nullable
   public static MovingObjectPositionBlock a(Iterable<AxisAlignedBB> var0, Vec3D var1, Vec3D var2, BlockPosition var3) {
      double[] var4 = new double[]{1.0};
      EnumDirection var5 = null;
      double var6 = var2.c - var1.c;
      double var8 = var2.d - var1.d;
      double var10 = var2.e - var1.e;

      for(AxisAlignedBB var13 : var0) {
         var5 = a(var13.a(var3), var1, var4, var5, var6, var8, var10);
      }

      if (var5 == null) {
         return null;
      } else {
         double var12 = var4[0];
         return new MovingObjectPositionBlock(var1.b(var12 * var6, var12 * var8, var12 * var10), var5, var3, false);
      }
   }

   @Nullable
   private static EnumDirection a(AxisAlignedBB var0, Vec3D var1, double[] var2, @Nullable EnumDirection var3, double var4, double var6, double var8) {
      if (var4 > 1.0E-7) {
         var3 = a(var2, var3, var4, var6, var8, var0.a, var0.b, var0.e, var0.c, var0.f, EnumDirection.e, var1.c, var1.d, var1.e);
      } else if (var4 < -1.0E-7) {
         var3 = a(var2, var3, var4, var6, var8, var0.d, var0.b, var0.e, var0.c, var0.f, EnumDirection.f, var1.c, var1.d, var1.e);
      }

      if (var6 > 1.0E-7) {
         var3 = a(var2, var3, var6, var8, var4, var0.b, var0.c, var0.f, var0.a, var0.d, EnumDirection.a, var1.d, var1.e, var1.c);
      } else if (var6 < -1.0E-7) {
         var3 = a(var2, var3, var6, var8, var4, var0.e, var0.c, var0.f, var0.a, var0.d, EnumDirection.b, var1.d, var1.e, var1.c);
      }

      if (var8 > 1.0E-7) {
         var3 = a(var2, var3, var8, var4, var6, var0.c, var0.a, var0.d, var0.b, var0.e, EnumDirection.c, var1.e, var1.c, var1.d);
      } else if (var8 < -1.0E-7) {
         var3 = a(var2, var3, var8, var4, var6, var0.f, var0.a, var0.d, var0.b, var0.e, EnumDirection.d, var1.e, var1.c, var1.d);
      }

      return var3;
   }

   @Nullable
   private static EnumDirection a(
      double[] var0,
      @Nullable EnumDirection var1,
      double var2,
      double var4,
      double var6,
      double var8,
      double var10,
      double var12,
      double var14,
      double var16,
      EnumDirection var18,
      double var19,
      double var21,
      double var23
   ) {
      double var25 = (var8 - var19) / var2;
      double var27 = var21 + var25 * var4;
      double var29 = var23 + var25 * var6;
      if (0.0 < var25 && var25 < var0[0] && var10 - 1.0E-7 < var27 && var27 < var12 + 1.0E-7 && var14 - 1.0E-7 < var29 && var29 < var16 + 1.0E-7) {
         var0[0] = var25;
         return var18;
      } else {
         return var1;
      }
   }

   public double e(Vec3D var0) {
      double var1 = Math.max(Math.max(this.a - var0.c, var0.c - this.d), 0.0);
      double var3 = Math.max(Math.max(this.b - var0.d, var0.d - this.e), 0.0);
      double var5 = Math.max(Math.max(this.c - var0.e, var0.e - this.f), 0.0);
      return MathHelper.e(var1, var3, var5);
   }

   @Override
   public String toString() {
      return "AABB[" + this.a + ", " + this.b + ", " + this.c + "] -> [" + this.d + ", " + this.e + ", " + this.f + "]";
   }

   public boolean e() {
      return Double.isNaN(this.a) || Double.isNaN(this.b) || Double.isNaN(this.c) || Double.isNaN(this.d) || Double.isNaN(this.e) || Double.isNaN(this.f);
   }

   public Vec3D f() {
      return new Vec3D(MathHelper.d(0.5, this.a, this.d), MathHelper.d(0.5, this.b, this.e), MathHelper.d(0.5, this.c, this.f));
   }

   public static AxisAlignedBB a(Vec3D var0, double var1, double var3, double var5) {
      return new AxisAlignedBB(var0.c - var1 / 2.0, var0.d - var3 / 2.0, var0.e - var5 / 2.0, var0.c + var1 / 2.0, var0.d + var3 / 2.0, var0.e + var5 / 2.0);
   }
}
