package net.minecraft.world.phys.shapes;

import com.google.common.math.IntMath;
import it.unimi.dsi.fastutil.doubles.DoubleList;

public final class VoxelShapeCubeMerger implements VoxelShapeMerger {
   private final VoxelShapeCubePoint a;
   private final int b;
   private final int c;

   VoxelShapeCubeMerger(int var0, int var1) {
      this.a = new VoxelShapeCubePoint((int)VoxelShapes.a(var0, var1));
      int var2 = IntMath.gcd(var0, var1);
      this.b = var0 / var2;
      this.c = var1 / var2;
   }

   @Override
   public boolean a(VoxelShapeMerger.a var0) {
      int var1 = this.a.size() - 1;

      for(int var2 = 0; var2 < var1; ++var2) {
         if (!var0.merge(var2 / this.c, var2 / this.b, var2)) {
            return false;
         }
      }

      return true;
   }

   @Override
   public int size() {
      return this.a.size();
   }

   @Override
   public DoubleList a() {
      return this.a;
   }
}
