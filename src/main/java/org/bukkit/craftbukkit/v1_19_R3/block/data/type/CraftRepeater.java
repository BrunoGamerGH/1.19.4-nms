package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.data.type.Repeater;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftRepeater extends CraftBlockData implements Repeater {
   private static final BlockStateInteger DELAY = getInteger("delay");
   private static final BlockStateBoolean LOCKED = getBoolean("locked");

   public int getDelay() {
      return this.get(DELAY);
   }

   public void setDelay(int delay) {
      this.set(DELAY, Integer.valueOf(delay));
   }

   public int getMinimumDelay() {
      return getMin(DELAY);
   }

   public int getMaximumDelay() {
      return getMax(DELAY);
   }

   public boolean isLocked() {
      return this.get(LOCKED);
   }

   public void setLocked(boolean locked) {
      this.set(LOCKED, Boolean.valueOf(locked));
   }
}
