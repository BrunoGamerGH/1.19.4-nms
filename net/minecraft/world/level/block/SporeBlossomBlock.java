package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.Particles;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;

public class SporeBlossomBlock extends Block {
   private static final VoxelShape a = Block.a(2.0, 13.0, 2.0, 14.0, 16.0, 14.0);
   private static final int b = 14;
   private static final int c = 10;
   private static final int d = 10;

   public SporeBlossomBlock(BlockBase.Info var0) {
      super(var0);
   }

   @Override
   public boolean a(IBlockData var0, IWorldReader var1, BlockPosition var2) {
      return Block.a(var1, var2.c(), EnumDirection.a) && !var1.B(var2);
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      return var1 == EnumDirection.b && !this.a(var0, var3, var4) ? Blocks.a.o() : super.a(var0, var1, var2, var3, var4, var5);
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, RandomSource var3) {
      int var4 = var2.u();
      int var5 = var2.v();
      int var6 = var2.w();
      double var7 = (double)var4 + var3.j();
      double var9 = (double)var5 + 0.7;
      double var11 = (double)var6 + var3.j();
      var1.a(Particles.av, var7, var9, var11, 0.0, 0.0, 0.0);
      BlockPosition.MutableBlockPosition var13 = new BlockPosition.MutableBlockPosition();

      for(int var14 = 0; var14 < 14; ++var14) {
         var13.d(var4 + MathHelper.a(var3, -10, 10), var5 - var3.a(10), var6 + MathHelper.a(var3, -10, 10));
         IBlockData var15 = var1.a_(var13);
         if (!var15.r(var1, var13)) {
            var1.a(Particles.az, (double)var13.u() + var3.j(), (double)var13.v() + var3.j(), (double)var13.w() + var3.j(), 0.0, 0.0, 0.0);
         }
      }
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return a;
   }
}
