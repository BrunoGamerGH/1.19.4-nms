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
import net.minecraft.world.level.block.state.properties.BlockStateDirection;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;

public class BlockLadder extends Block implements IBlockWaterlogged {
   public static final BlockStateDirection a = BlockFacingHorizontal.aD;
   public static final BlockStateBoolean b = BlockProperties.C;
   protected static final float c = 3.0F;
   protected static final VoxelShape d = Block.a(0.0, 0.0, 0.0, 3.0, 16.0, 16.0);
   protected static final VoxelShape e = Block.a(13.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape f = Block.a(0.0, 0.0, 0.0, 16.0, 16.0, 3.0);
   protected static final VoxelShape g = Block.a(0.0, 0.0, 13.0, 16.0, 16.0, 16.0);

   protected BlockLadder(BlockBase.Info var0) {
      super(var0);
      this.k(this.D.b().a(a, EnumDirection.c).a(b, Boolean.valueOf(false)));
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      switch((EnumDirection)var0.c(a)) {
         case c:
            return g;
         case d:
            return f;
         case e:
            return e;
         case f:
         default:
            return d;
      }
   }

   private boolean a(IBlockAccess var0, BlockPosition var1, EnumDirection var2) {
      IBlockData var3 = var0.a_(var1);
      return var3.d(var0, var1, var2);
   }

   @Override
   public boolean a(IBlockData var0, IWorldReader var1, BlockPosition var2) {
      EnumDirection var3 = var0.c(a);
      return this.a(var1, var2.a(var3.g()), var3);
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      if (var1.g() == var0.c(a) && !var0.a(var3, var4)) {
         return Blocks.a.o();
      } else {
         if (var0.c(b)) {
            var3.a(var4, FluidTypes.c, FluidTypes.c.a(var3));
         }

         return super.a(var0, var1, var2, var3, var4, var5);
      }
   }

   @Nullable
   @Override
   public IBlockData a(BlockActionContext var0) {
      if (!var0.c()) {
         IBlockData var1 = var0.q().a_(var0.a().a(var0.k().g()));
         if (var1.a(this) && var1.c(a) == var0.k()) {
            return null;
         }
      }

      IBlockData var1 = this.o();
      IWorldReader var2 = var0.q();
      BlockPosition var3 = var0.a();
      Fluid var4 = var0.q().b_(var0.a());

      for(EnumDirection var8 : var0.f()) {
         if (var8.o().d()) {
            var1 = var1.a(a, var8.g());
            if (var1.a(var2, var3)) {
               return var1.a(b, Boolean.valueOf(var4.a() == FluidTypes.c));
            }
         }
      }

      return null;
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockRotation var1) {
      return var0.a(a, var1.a(var0.c(a)));
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockMirror var1) {
      return var0.a(var1.a(var0.c(a)));
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(a, b);
   }

   @Override
   public Fluid c_(IBlockData var0) {
      return var0.c(b) ? FluidTypes.c.a(false) : super.c_(var0);
   }
}
