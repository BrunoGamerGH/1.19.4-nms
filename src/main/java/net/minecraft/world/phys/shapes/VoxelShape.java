package net.minecraft.world.phys.shapes;

import com.google.common.collect.Lists;
import com.google.common.math.DoubleMath;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumAxisCycle;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.MathHelper;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.Vec3D;

public abstract class VoxelShape {
   protected final VoxelShapeDiscrete a;
   @Nullable
   private VoxelShape[] b;

   VoxelShape(VoxelShapeDiscrete var0) {
      this.a = var0;
   }

   public double b(EnumDirection.EnumAxis var0) {
      int var1 = this.a.a(var0);
      return var1 >= this.a.c(var0) ? Double.POSITIVE_INFINITY : this.a(var0, var1);
   }

   public double c(EnumDirection.EnumAxis var0) {
      int var1 = this.a.b(var0);
      return var1 <= 0 ? Double.NEGATIVE_INFINITY : this.a(var0, var1);
   }

   public AxisAlignedBB a() {
      if (this.b()) {
         throw (UnsupportedOperationException)SystemUtils.b(new UnsupportedOperationException("No bounds for empty shape."));
      } else {
         return new AxisAlignedBB(
            this.b(EnumDirection.EnumAxis.a),
            this.b(EnumDirection.EnumAxis.b),
            this.b(EnumDirection.EnumAxis.c),
            this.c(EnumDirection.EnumAxis.a),
            this.c(EnumDirection.EnumAxis.b),
            this.c(EnumDirection.EnumAxis.c)
         );
      }
   }

   protected double a(EnumDirection.EnumAxis var0, int var1) {
      return this.a(var0).getDouble(var1);
   }

   protected abstract DoubleList a(EnumDirection.EnumAxis var1);

   public boolean b() {
      return this.a.a();
   }

   public VoxelShape a(double var0, double var2, double var4) {
      return (VoxelShape)(this.b()
         ? VoxelShapes.a()
         : new VoxelShapeArray(
            this.a,
            new DoubleListOffset(this.a(EnumDirection.EnumAxis.a), var0),
            new DoubleListOffset(this.a(EnumDirection.EnumAxis.b), var2),
            new DoubleListOffset(this.a(EnumDirection.EnumAxis.c), var4)
         ));
   }

   public VoxelShape c() {
      VoxelShape[] var0 = new VoxelShape[]{VoxelShapes.a()};
      this.b(
         (var1x, var3, var5, var7, var9, var11) -> var0[0] = VoxelShapes.b(var0[0], VoxelShapes.a(var1x, var3, var5, var7, var9, var11), OperatorBoolean.o)
      );
      return var0[0];
   }

   public void a(VoxelShapes.a var0) {
      this.a
         .a(
            (var1x, var2, var3, var4, var5, var6) -> var0.consume(
                  this.a(EnumDirection.EnumAxis.a, var1x),
                  this.a(EnumDirection.EnumAxis.b, var2),
                  this.a(EnumDirection.EnumAxis.c, var3),
                  this.a(EnumDirection.EnumAxis.a, var4),
                  this.a(EnumDirection.EnumAxis.b, var5),
                  this.a(EnumDirection.EnumAxis.c, var6)
               ),
            true
         );
   }

   public void b(VoxelShapes.a var0) {
      DoubleList var1 = this.a(EnumDirection.EnumAxis.a);
      DoubleList var2 = this.a(EnumDirection.EnumAxis.b);
      DoubleList var3 = this.a(EnumDirection.EnumAxis.c);
      this.a
         .b(
            (var4x, var5, var6, var7, var8, var9) -> var0.consume(
                  var1.getDouble(var4x), var2.getDouble(var5), var3.getDouble(var6), var1.getDouble(var7), var2.getDouble(var8), var3.getDouble(var9)
               ),
            true
         );
   }

   public List<AxisAlignedBB> d() {
      List<AxisAlignedBB> var0 = Lists.newArrayList();
      this.b((var1x, var3, var5, var7, var9, var11) -> var0.add(new AxisAlignedBB(var1x, var3, var5, var7, var9, var11)));
      return var0;
   }

   public double a(EnumDirection.EnumAxis var0, double var1, double var3) {
      EnumDirection.EnumAxis var5 = EnumAxisCycle.b.a(var0);
      EnumDirection.EnumAxis var6 = EnumAxisCycle.c.a(var0);
      int var7 = this.a(var5, var1);
      int var8 = this.a(var6, var3);
      int var9 = this.a.a(var0, var7, var8);
      return var9 >= this.a.c(var0) ? Double.POSITIVE_INFINITY : this.a(var0, var9);
   }

   public double b(EnumDirection.EnumAxis var0, double var1, double var3) {
      EnumDirection.EnumAxis var5 = EnumAxisCycle.b.a(var0);
      EnumDirection.EnumAxis var6 = EnumAxisCycle.c.a(var0);
      int var7 = this.a(var5, var1);
      int var8 = this.a(var6, var3);
      int var9 = this.a.b(var0, var7, var8);
      return var9 <= 0 ? Double.NEGATIVE_INFINITY : this.a(var0, var9);
   }

   protected int a(EnumDirection.EnumAxis var0, double var1) {
      return MathHelper.a(0, this.a.c(var0) + 1, var3 -> var1 < this.a(var0, var3)) - 1;
   }

   @Nullable
   public MovingObjectPositionBlock a(Vec3D var0, Vec3D var1, BlockPosition var2) {
      if (this.b()) {
         return null;
      } else {
         Vec3D var3 = var1.d(var0);
         if (var3.g() < 1.0E-7) {
            return null;
         } else {
            Vec3D var4 = var0.e(var3.a(0.001));
            return this.a
                  .d(
                     this.a(EnumDirection.EnumAxis.a, var4.c - (double)var2.u()),
                     this.a(EnumDirection.EnumAxis.b, var4.d - (double)var2.v()),
                     this.a(EnumDirection.EnumAxis.c, var4.e - (double)var2.w())
                  )
               ? new MovingObjectPositionBlock(var4, EnumDirection.a(var3.c, var3.d, var3.e).g(), var2, true)
               : AxisAlignedBB.a(this.d(), var0, var1, var2);
         }
      }
   }

   public Optional<Vec3D> a(Vec3D var0) {
      if (this.b()) {
         return Optional.empty();
      } else {
         Vec3D[] var1 = new Vec3D[1];
         this.b((var2x, var4, var6, var8, var10, var12) -> {
            double var14 = MathHelper.a(var0.a(), var2x, var8);
            double var16 = MathHelper.a(var0.b(), var4, var10);
            double var18 = MathHelper.a(var0.c(), var6, var12);
            if (var1[0] == null || var0.c(var14, var16, var18) < var0.g(var1[0])) {
               var1[0] = new Vec3D(var14, var16, var18);
            }
         });
         return Optional.of(var1[0]);
      }
   }

   public VoxelShape a(EnumDirection var0) {
      if (!this.b() && this != VoxelShapes.b()) {
         if (this.b != null) {
            VoxelShape var1 = this.b[var0.ordinal()];
            if (var1 != null) {
               return var1;
            }
         } else {
            this.b = new VoxelShape[6];
         }

         VoxelShape var1 = this.b(var0);
         this.b[var0.ordinal()] = var1;
         return var1;
      } else {
         return this;
      }
   }

   private VoxelShape b(EnumDirection var0) {
      EnumDirection.EnumAxis var1 = var0.o();
      DoubleList var2 = this.a(var1);
      if (var2.size() == 2 && DoubleMath.fuzzyEquals(var2.getDouble(0), 0.0, 1.0E-7) && DoubleMath.fuzzyEquals(var2.getDouble(1), 1.0, 1.0E-7)) {
         return this;
      } else {
         EnumDirection.EnumAxisDirection var3 = var0.f();
         int var4 = this.a(var1, var3 == EnumDirection.EnumAxisDirection.a ? 0.9999999 : 1.0E-7);
         return new VoxelShapeSlice(this, var1, var4);
      }
   }

   public double a(EnumDirection.EnumAxis var0, AxisAlignedBB var1, double var2) {
      return this.a(EnumAxisCycle.a(var0, EnumDirection.EnumAxis.a), var1, var2);
   }

   protected double a(EnumAxisCycle var0, AxisAlignedBB var1, double var2) {
      if (this.b()) {
         return var2;
      } else if (Math.abs(var2) < 1.0E-7) {
         return 0.0;
      } else {
         EnumAxisCycle var4 = var0.a();
         EnumDirection.EnumAxis var5 = var4.a(EnumDirection.EnumAxis.a);
         EnumDirection.EnumAxis var6 = var4.a(EnumDirection.EnumAxis.b);
         EnumDirection.EnumAxis var7 = var4.a(EnumDirection.EnumAxis.c);
         double var8 = var1.b(var5);
         double var10 = var1.a(var5);
         int var12 = this.a(var5, var10 + 1.0E-7);
         int var13 = this.a(var5, var8 - 1.0E-7);
         int var14 = Math.max(0, this.a(var6, var1.a(var6) + 1.0E-7));
         int var15 = Math.min(this.a.c(var6), this.a(var6, var1.b(var6) - 1.0E-7) + 1);
         int var16 = Math.max(0, this.a(var7, var1.a(var7) + 1.0E-7));
         int var17 = Math.min(this.a.c(var7), this.a(var7, var1.b(var7) - 1.0E-7) + 1);
         int var18 = this.a.c(var5);
         if (var2 > 0.0) {
            for(int var19 = var13 + 1; var19 < var18; ++var19) {
               for(int var20 = var14; var20 < var15; ++var20) {
                  for(int var21 = var16; var21 < var17; ++var21) {
                     if (this.a.a(var4, var19, var20, var21)) {
                        double var22 = this.a(var5, var19) - var8;
                        if (var22 >= -1.0E-7) {
                           var2 = Math.min(var2, var22);
                        }

                        return var2;
                     }
                  }
               }
            }
         } else if (var2 < 0.0) {
            for(int var19 = var12 - 1; var19 >= 0; --var19) {
               for(int var20 = var14; var20 < var15; ++var20) {
                  for(int var21 = var16; var21 < var17; ++var21) {
                     if (this.a.a(var4, var19, var20, var21)) {
                        double var22 = this.a(var5, var19 + 1) - var10;
                        if (var22 <= 1.0E-7) {
                           var2 = Math.max(var2, var22);
                        }

                        return var2;
                     }
                  }
               }
            }
         }

         return var2;
      }
   }

   @Override
   public String toString() {
      return this.b() ? "EMPTY" : "VoxelShape[" + this.a() + "]";
   }
}
