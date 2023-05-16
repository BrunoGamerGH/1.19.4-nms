package net.minecraft.world.level.block.entity;

import net.minecraft.core.BlockPosition;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.level.block.state.IBlockData;

public class TileEntityDropper extends TileEntityDispenser {
   public TileEntityDropper(BlockPosition var0, IBlockData var1) {
      super(TileEntityTypes.g, var0, var1);
   }

   @Override
   protected IChatBaseComponent g() {
      return IChatBaseComponent.c("container.dropper");
   }
}
