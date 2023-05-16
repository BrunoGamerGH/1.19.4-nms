package net.minecraft.world.phys.shapes;

import it.unimi.dsi.fastutil.doubles.DoubleList;

interface VoxelShapeMerger {
   DoubleList a();

   boolean a(VoxelShapeMerger.a var1);

   int size();

   public interface a {
      boolean merge(int var1, int var2, int var3);
   }
}
