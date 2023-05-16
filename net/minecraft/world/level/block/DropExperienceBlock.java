package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;

public class DropExperienceBlock extends Block {
   private final IntProvider a;

   public DropExperienceBlock(BlockBase.Info blockbase_info) {
      this(blockbase_info, ConstantInt.a(0));
   }

   public DropExperienceBlock(BlockBase.Info blockbase_info, IntProvider intprovider) {
      super(blockbase_info);
      this.a = intprovider;
   }

   @Override
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, ItemStack itemstack, boolean flag) {
      super.a(iblockdata, worldserver, blockposition, itemstack, flag);
   }

   @Override
   public int getExpDrop(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, ItemStack itemstack, boolean flag) {
      return flag ? this.tryDropExperience(worldserver, blockposition, itemstack, this.a) : 0;
   }
}
