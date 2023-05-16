package net.minecraft.world.item;

import net.minecraft.world.level.block.Block;

public class ItemNamedBlock extends ItemBlock {
   public ItemNamedBlock(Block var0, Item.Info var1) {
      super(var0, var1);
   }

   @Override
   public String a() {
      return this.q();
   }
}
