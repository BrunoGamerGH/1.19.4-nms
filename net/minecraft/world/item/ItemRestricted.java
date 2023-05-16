package net.minecraft.world.item;

import javax.annotation.Nullable;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.IBlockData;

public class ItemRestricted extends ItemBlock {
   public ItemRestricted(Block var0, Item.Info var1) {
      super(var0, var1);
   }

   @Nullable
   @Override
   protected IBlockData c(BlockActionContext var0) {
      EntityHuman var1 = var0.o();
      return var1 != null && !var1.gg() ? null : super.c(var0);
   }
}
