package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.ParticleParam;
import net.minecraft.core.particles.Particles;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;

public class BlockTorch extends Block {
   protected static final int g = 2;
   protected static final VoxelShape h = Block.a(6.0, 0.0, 6.0, 10.0, 10.0, 10.0);
   protected final ParticleParam i;

   protected BlockTorch(BlockBase.Info var0, ParticleParam var1) {
      super(var0);
      this.i = var1;
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return h;
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      return var1 == EnumDirection.a && !this.a(var0, var3, var4) ? Blocks.a.o() : super.a(var0, var1, var2, var3, var4, var5);
   }

   @Override
   public boolean a(IBlockData var0, IWorldReader var1, BlockPosition var2) {
      return a(var1, var2.d(), EnumDirection.b);
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, RandomSource var3) {
      double var4 = (double)var2.u() + 0.5;
      double var6 = (double)var2.v() + 0.7;
      double var8 = (double)var2.w() + 0.5;
      var1.a(Particles.ab, var4, var6, var8, 0.0, 0.0, 0.0);
      var1.a(this.i, var4, var6, var8, 0.0, 0.0, 0.0);
   }
}
