package net.minecraft.world.item;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;

public class ItemBisected extends ItemBlock {
   public ItemBisected(Block var0, Item.Info var1) {
      super(var0, var1);
   }

   @Override
   protected boolean a(BlockActionContext var0, IBlockData var1) {
      World var2 = var0.q();
      BlockPosition var3 = var0.a().c();
      IBlockData var4 = var2.B(var3) ? Blocks.G.o() : Blocks.a.o();
      var2.a(var3, var4, 27);
      return super.a(var0, var1);
   }
}
