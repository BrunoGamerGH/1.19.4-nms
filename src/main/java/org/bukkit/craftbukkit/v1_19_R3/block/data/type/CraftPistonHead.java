package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.block.data.type.PistonHead;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftPistonHead extends CraftBlockData implements PistonHead {
   private static final BlockStateBoolean SHORT = getBoolean("short");

   public boolean isShort() {
      return this.get(SHORT);
   }

   public void setShort(boolean _short) {
      this.set(SHORT, Boolean.valueOf(_short));
   }
}
