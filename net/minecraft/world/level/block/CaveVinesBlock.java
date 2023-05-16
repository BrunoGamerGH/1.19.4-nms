package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.MovingObjectPositionBlock;

public class CaveVinesBlock extends BlockGrowingTop implements IBlockFragilePlantElement, CaveVines {
   private static final float f = 0.11F;

   public CaveVinesBlock(BlockBase.Info var0) {
      super(var0, EnumDirection.a, p_, false, 0.1);
      this.k(this.D.b().a(d, Integer.valueOf(0)).a(q_, Boolean.valueOf(false)));
   }

   @Override
   protected int a(RandomSource var0) {
      return 1;
   }

   @Override
   protected boolean g(IBlockData var0) {
      return var0.h();
   }

   @Override
   protected Block b() {
      return Blocks.rq;
   }

   @Override
   protected IBlockData a(IBlockData var0, IBlockData var1) {
      return var1.a(q_, var0.c(q_));
   }

   @Override
   protected IBlockData a(IBlockData var0, RandomSource var1) {
      return super.a(var0, var1).a(q_, Boolean.valueOf(var1.i() < 0.11F));
   }

   @Override
   public ItemStack a(IBlockAccess var0, BlockPosition var1, IBlockData var2) {
      return new ItemStack(Items.vq);
   }

   @Override
   public EnumInteractionResult a(IBlockData var0, World var1, BlockPosition var2, EntityHuman var3, EnumHand var4, MovingObjectPositionBlock var5) {
      return CaveVines.a(var3, var0, var1, var2);
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      super.a(var0);
      var0.a(q_);
   }

   @Override
   public boolean a(IWorldReader var0, BlockPosition var1, IBlockData var2, boolean var3) {
      return !var2.c(q_);
   }

   @Override
   public boolean a(World var0, RandomSource var1, BlockPosition var2, IBlockData var3) {
      return true;
   }

   @Override
   public void a(WorldServer var0, RandomSource var1, BlockPosition var2, IBlockData var3) {
      var0.a(var2, var3.a(q_, Boolean.valueOf(true)), 2);
   }
}
