package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;

public class BlockChain extends BlockRotatable implements IBlockWaterlogged {
   public static final BlockStateBoolean a = BlockProperties.C;
   protected static final float b = 6.5F;
   protected static final float c = 9.5F;
   protected static final VoxelShape d = Block.a(6.5, 0.0, 6.5, 9.5, 16.0, 9.5);
   protected static final VoxelShape e = Block.a(6.5, 6.5, 0.0, 9.5, 9.5, 16.0);
   protected static final VoxelShape f = Block.a(0.0, 6.5, 6.5, 16.0, 9.5, 9.5);

   public BlockChain(BlockBase.Info var0) {
      super(var0);
      this.k(this.D.b().a(a, Boolean.valueOf(false)).a(g, EnumDirection.EnumAxis.b));
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      switch((EnumDirection.EnumAxis)var0.c(g)) {
         case a:
         default:
            return f;
         case c:
            return e;
         case b:
            return d;
      }
   }

   @Nullable
   @Override
   public IBlockData a(BlockActionContext var0) {
      Fluid var1 = var0.q().b_(var0.a());
      boolean var2 = var1.a() == FluidTypes.c;
      return super.a(var0).a(a, Boolean.valueOf(var2));
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      if (var0.c(a)) {
         var3.a(var4, FluidTypes.c, FluidTypes.c.a(var3));
      }

      return super.a(var0, var1, var2, var3, var4, var5);
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(a).a(g);
   }

   @Override
   public Fluid c_(IBlockData var0) {
      return var0.c(a) ? FluidTypes.c.a(false) : super.c_(var0);
   }

   @Override
   public boolean a(IBlockData var0, IBlockAccess var1, BlockPosition var2, PathMode var3) {
      return false;
   }
}
