package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.data.type.SeaPickle;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftSeaPickle extends CraftBlockData implements SeaPickle {
   private static final BlockStateInteger PICKLES = getInteger("pickles");

   public int getPickles() {
      return this.get(PICKLES);
   }

   public void setPickles(int pickles) {
      this.set(PICKLES, Integer.valueOf(pickles));
   }

   public int getMinimumPickles() {
      return getMin(PICKLES);
   }

   public int getMaximumPickles() {
      return getMax(PICKLES);
   }
}
