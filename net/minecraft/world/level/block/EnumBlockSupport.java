package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.shapes.OperatorBoolean;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapes;

public enum EnumBlockSupport {
   a {
      @Override
      public boolean a(IBlockData var0, IBlockAccess var1, BlockPosition var2, EnumDirection var3) {
         return Block.a(var0.l(var1, var2), var3);
      }
   },
   b {
      private final int d = 1;
      private final VoxelShape e = Block.a(7.0, 0.0, 7.0, 9.0, 10.0, 9.0);

      @Override
      public boolean a(IBlockData var0, IBlockAccess var1, BlockPosition var2, EnumDirection var3) {
         return !VoxelShapes.c(var0.l(var1, var2).a(var3), this.e, OperatorBoolean.c);
      }
   },
   c {
      private final int d = 2;
      private final VoxelShape e = VoxelShapes.a(VoxelShapes.b(), Block.a(2.0, 0.0, 2.0, 14.0, 16.0, 14.0), OperatorBoolean.e);

      @Override
      public boolean a(IBlockData var0, IBlockAccess var1, BlockPosition var2, EnumDirection var3) {
         return !VoxelShapes.c(var0.l(var1, var2).a(var3), this.e, OperatorBoolean.c);
      }
   };

   public abstract boolean a(IBlockData var1, IBlockAccess var2, BlockPosition var3, EnumDirection var4);
}
