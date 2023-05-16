package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.ParticleParamRedstone;
import net.minecraft.util.RandomSource;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.phys.MovingObjectPositionBlock;

public class BlockRepeater extends BlockDiodeAbstract {
   public static final BlockStateBoolean a = BlockProperties.s;
   public static final BlockStateInteger d = BlockProperties.aA;

   protected BlockRepeater(BlockBase.Info var0) {
      super(var0);
      this.k(this.D.b().a(aD, EnumDirection.c).a(d, Integer.valueOf(1)).a(a, Boolean.valueOf(false)).a(c, Boolean.valueOf(false)));
   }

   @Override
   public EnumInteractionResult a(IBlockData var0, World var1, BlockPosition var2, EntityHuman var3, EnumHand var4, MovingObjectPositionBlock var5) {
      if (!var3.fK().e) {
         return EnumInteractionResult.d;
      } else {
         var1.a(var2, var0.a(d), 3);
         return EnumInteractionResult.a(var1.B);
      }
   }

   @Override
   protected int g(IBlockData var0) {
      return var0.c(d) * 2;
   }

   @Override
   public IBlockData a(BlockActionContext var0) {
      IBlockData var1 = super.a(var0);
      return var1.a(a, Boolean.valueOf(this.a(var0.q(), var0.a(), var1)));
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      return !var3.k_() && var1.o() != var0.c(aD).o() ? var0.a(a, Boolean.valueOf(this.a(var3, var4, var0))) : super.a(var0, var1, var2, var3, var4, var5);
   }

   @Override
   public boolean a(IWorldReader var0, BlockPosition var1, IBlockData var2) {
      return this.b(var0, var1, var2) > 0;
   }

   @Override
   protected boolean h(IBlockData var0) {
      return n(var0);
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, RandomSource var3) {
      if (var0.c(c)) {
         EnumDirection var4 = var0.c(aD);
         double var5 = (double)var2.u() + 0.5 + (var3.j() - 0.5) * 0.2;
         double var7 = (double)var2.v() + 0.4 + (var3.j() - 0.5) * 0.2;
         double var9 = (double)var2.w() + 0.5 + (var3.j() - 0.5) * 0.2;
         float var11 = -5.0F;
         if (var3.h()) {
            var11 = (float)(var0.c(d) * 2 - 1);
         }

         var11 /= 16.0F;
         double var12 = (double)(var11 * (float)var4.j());
         double var14 = (double)(var11 * (float)var4.l());
         var1.a(ParticleParamRedstone.b, var5 + var12, var7, var9 + var14, 0.0, 0.0, 0.0);
      }
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(aD, d, a, c);
   }
}
