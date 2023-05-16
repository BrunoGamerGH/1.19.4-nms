package org.bukkit.craftbukkit.v1_19_R3.block.data;

import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.block.data.Openable;

public abstract class CraftOpenable extends CraftBlockData implements Openable {
   private static final BlockStateBoolean OPEN = getBoolean("open");

   public boolean isOpen() {
      return this.get(OPEN);
   }

   public void setOpen(boolean open) {
      this.set(OPEN, Boolean.valueOf(open));
   }
}
