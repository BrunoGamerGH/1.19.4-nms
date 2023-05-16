package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.BlockBubbleColumn;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.block.data.type.BubbleColumn;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftBubbleColumn extends CraftBlockData implements BubbleColumn {
   private static final BlockStateBoolean DRAG = getBoolean(BlockBubbleColumn.class, "drag");

   public CraftBubbleColumn() {
   }

   public CraftBubbleColumn(IBlockData state) {
      super(state);
   }

   public boolean isDrag() {
      return this.get(DRAG);
   }

   public void setDrag(boolean drag) {
      this.set(DRAG, Boolean.valueOf(drag));
   }
}
