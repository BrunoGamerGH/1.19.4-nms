package net.minecraft.world.item;

import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.IBlockData;

public class ItemBed extends ItemBlock {
   public ItemBed(Block var0, Item.Info var1) {
      super(var0, var1);
   }

   @Override
   protected boolean a(BlockActionContext var0, IBlockData var1) {
      return var0.q().a(var0.a(), var1, 26);
   }
}
