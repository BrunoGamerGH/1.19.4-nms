package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.Particles;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.EnumPistonReaction;

public class BlockEndRod extends RodBlock {
   protected BlockEndRod(BlockBase.Info var0) {
      super(var0);
      this.k(this.D.b().a(a, EnumDirection.b));
   }

   @Override
   public IBlockData a(BlockActionContext var0) {
      EnumDirection var1 = var0.k();
      IBlockData var2 = var0.q().a_(var0.a().a(var1.g()));
      return var2.a(this) && var2.c(a) == var1 ? this.o().a(a, var1.g()) : this.o().a(a, var1);
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, RandomSource var3) {
      EnumDirection var4 = var0.c(a);
      double var5 = (double)var2.u() + 0.55 - (double)(var3.i() * 0.1F);
      double var7 = (double)var2.v() + 0.55 - (double)(var3.i() * 0.1F);
      double var9 = (double)var2.w() + 0.55 - (double)(var3.i() * 0.1F);
      double var11 = (double)(0.4F - (var3.i() + var3.i()) * 0.4F);
      if (var3.a(5) == 0) {
         var1.a(
            Particles.u,
            var5 + (double)var4.j() * var11,
            var7 + (double)var4.k() * var11,
            var9 + (double)var4.l() * var11,
            var3.k() * 0.005,
            var3.k() * 0.005,
            var3.k() * 0.005
         );
      }
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(a);
   }

   @Override
   public EnumPistonReaction d(IBlockData var0) {
      return EnumPistonReaction.a;
   }
}
