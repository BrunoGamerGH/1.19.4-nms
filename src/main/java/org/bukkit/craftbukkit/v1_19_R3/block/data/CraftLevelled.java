package org.bukkit.craftbukkit.v1_19_R3.block.data;

import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.data.Levelled;

public abstract class CraftLevelled extends CraftBlockData implements Levelled {
   private static final BlockStateInteger LEVEL = getInteger("level");

   public int getLevel() {
      return this.get(LEVEL);
   }

   public void setLevel(int level) {
      this.set(LEVEL, Integer.valueOf(level));
   }

   public int getMaximumLevel() {
      return getMax(LEVEL);
   }
}
