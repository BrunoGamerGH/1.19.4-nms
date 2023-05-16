package net.minecraft.world.phys.shapes;

import it.unimi.dsi.fastutil.doubles.DoubleList;

public class VoxelShapeMergerIdentical implements VoxelShapeMerger {
   private final DoubleList a;

   public VoxelShapeMergerIdentical(DoubleList var0) {
      this.a = var0;
   }

   @Override
   public boolean a(VoxelShapeMerger.a var0) {
      int var1 = this.a.size() - 1;

      for(int var2 = 0; var2 < var1; ++var2) {
         if (!var0.merge(var2, var2, var2)) {
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
