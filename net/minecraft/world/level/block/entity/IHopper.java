package net.minecraft.world.level.block.entity;

import net.minecraft.world.IInventory;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapes;

public interface IHopper extends IInventory {
   VoxelShape c = Block.a(2.0, 11.0, 2.0, 14.0, 16.0, 14.0);
   VoxelShape v_ = Block.a(0.0, 16.0, 0.0, 16.0, 32.0, 16.0);
   VoxelShape w_ = VoxelShapes.a(c, v_);

   default VoxelShape as_() {
      return w_;
   }

   double F();

   double G();

   double I();
}
