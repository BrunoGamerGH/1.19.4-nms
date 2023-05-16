package net.minecraft.world.item;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.IBlockData;

public class ItemSign extends ItemBlockWallable {
   public static BlockPosition openSign;

   public ItemSign(Item.Info item_info, Block block, Block block1) {
      super(block, block1, item_info, EnumDirection.a);
   }

   @Override
   protected boolean a(BlockPosition blockposition, World world, @Nullable EntityHuman entityhuman, ItemStack itemstack, IBlockData iblockdata) {
      boolean flag = super.a(blockposition, world, entityhuman, itemstack, iblockdata);
      if (!world.B && !flag && entityhuman != null) {
         openSign = blockposition;
      }

      return flag;
   }
}
