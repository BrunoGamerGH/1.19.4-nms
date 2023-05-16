package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.material.EnumPistonReaction;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;

public class BlockLantern extends Block implements IBlockWaterlogged {
   public static final BlockStateBoolean a = BlockProperties.j;
   public static final BlockStateBoolean b = BlockProperties.C;
   protected static final VoxelShape c = VoxelShapes.a(Block.a(5.0, 0.0, 5.0, 11.0, 7.0, 11.0), Block.a(6.0, 7.0, 6.0, 10.0, 9.0, 10.0));
   protected static final VoxelShape d = VoxelShapes.a(Block.a(5.0, 1.0, 5.0, 11.0, 8.0, 11.0), Block.a(6.0, 8.0, 6.0, 10.0, 10.0, 10.0));

   public BlockLantern(BlockBase.Info var0) {
      super(var0);
      this.k(this.D.b().a(a, Boolean.valueOf(false)).a(b, Boolean.valueOf(false)));
   }

   @Nullable
   @Override
   public IBlockData a(BlockActionContext var0) {
      Fluid var1 = var0.q().b_(var0.a());

      for(EnumDirection var5 : var0.f()) {
         if (var5.o() == EnumDirection.EnumAxis.b) {
            IBlockData var6 = this.o().a(a, Boolean.valueOf(var5 == EnumDirection.b));
            if (var6.a((IWorldReader)var0.q(), var0.a())) {
               return var6.a(b, Boolean.valueOf(var1.a() == FluidTypes.c));
            }
         }
      }

      return null;
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return var0.c(a) ? d : c;
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(a, b);
   }

   @Override
   public boolean a(IBlockData var0, IWorldReader var1, BlockPosition var2) {
      EnumDirection var3 = h(var0).g();
      return Block.a(var1, var2.a(var3), var3.g());
   }

   protected static EnumDirection h(IBlockData var0) {
      return var0.c(a) ? EnumDirection.a : EnumDirection.b;
   }

   @Override
   public EnumPistonReaction d(IBlockData var0) {
      return EnumPistonReaction.b;
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      if (var0.c(b)) {
         var3.a(var4, FluidTypes.c, FluidTypes.c.a(var3));
      }

      return h(var0).g() == var1 && !var0.a(var3, var4) ? Blocks.a.o() : super.a(var0, var1, var2, var3, var4, var5);
   }

   @Override
   public Fluid c_(IBlockData var0) {
      return var0.c(b) ? FluidTypes.c.a(false) : super.c_(var0);
   }

   @Override
   public boolean a(IBlockData var0, IBlockAccess var1, BlockPosition var2, PathMode var3) {
      return false;
   }
}
