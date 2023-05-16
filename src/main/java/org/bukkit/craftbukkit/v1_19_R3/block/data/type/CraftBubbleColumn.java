package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.block.data.type.BubbleColumn;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftBubbleColumn extends CraftBlockData implements BubbleColumn {
   private static final BlockStateBoolean DRAG = getBoolean("drag");

   public boolean isDrag() {
      return this.get(DRAG);
   }

   public void setDrag(boolean drag) {
      this.set(DRAG, Boolean.valueOf(drag));
   }
}
