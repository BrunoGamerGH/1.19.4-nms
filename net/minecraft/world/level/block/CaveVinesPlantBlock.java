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

public class CaveVinesPlantBlock extends BlockGrowingStem implements IBlockFragilePlantElement, CaveVines {
   public CaveVinesPlantBlock(BlockBase.Info var0) {
      super(var0, EnumDirection.a, p_, false);
      this.k(this.D.b().a(q_, Boolean.valueOf(false)));
   }

   @Override
   protected BlockGrowingTop c() {
      return (BlockGrowingTop)Blocks.rp;
   }

   @Override
   protected IBlockData a(IBlockData var0, IBlockData var1) {
      return var1.a(q_, var0.c(q_));
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
