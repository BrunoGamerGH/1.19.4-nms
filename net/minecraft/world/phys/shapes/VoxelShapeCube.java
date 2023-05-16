package net.minecraft.world.phys.shapes;

import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.MathHelper;

public final class VoxelShapeCube extends VoxelShape {
   protected VoxelShapeCube(VoxelShapeDiscrete var0) {
      super(var0);
   }

   @Override
   protected DoubleList a(EnumDirection.EnumAxis var0) {
      return new VoxelShapeCubePoint(this.a.c(var0));
   }

   @Override
   protected int a(EnumDirection.EnumAxis var0, double var1) {
      int var3 = this.a.c(var0);
      return MathHelper.a(MathHelper.a(var1 * (double)var3, -1.0, (double)var3));
   }
}
