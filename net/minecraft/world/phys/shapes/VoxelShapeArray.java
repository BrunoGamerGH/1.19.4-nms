package net.minecraft.world.phys.shapes;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.util.Arrays;
import net.minecraft.SystemUtils;
import net.minecraft.core.EnumDirection;

public class VoxelShapeArray extends VoxelShape {
   private final DoubleList b;
   private final DoubleList c;
   private final DoubleList d;

   protected VoxelShapeArray(VoxelShapeDiscrete var0, double[] var1, double[] var2, double[] var3) {
      this(
         var0,
         DoubleArrayList.wrap(Arrays.copyOf(var1, var0.b() + 1)),
         DoubleArrayList.wrap(Arrays.copyOf(var2, var0.c() + 1)),
         DoubleArrayList.wrap(Arrays.copyOf(var3, var0.d() + 1))
      );
   }

   VoxelShapeArray(VoxelShapeDiscrete var0, DoubleList var1, DoubleList var2, DoubleList var3) {
      super(var0);
      int var4 = var0.b() + 1;
      int var5 = var0.c() + 1;
      int var6 = var0.d() + 1;
      if (var4 == var1.size() && var5 == var2.size() && var6 == var3.size()) {
         this.b = var1;
         this.c = var2;
         this.d = var3;
      } else {
         throw (IllegalArgumentException)SystemUtils.b(
            new IllegalArgumentException("Lengths of point arrays must be consistent with the size of the VoxelShape.")
         );
      }
   }

   @Override
   protected DoubleList a(EnumDirection.EnumAxis var0) {
      switch(var0) {
         case a:
            return this.b;
         case b:
            return this.c;
         case c:
            return this.d;
         default:
            throw new IllegalArgumentException();
      }
   }
}
