package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.data.type.Scaffolding;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftScaffolding extends CraftBlockData implements Scaffolding {
   private static final BlockStateBoolean BOTTOM = getBoolean("bottom");
   private static final BlockStateInteger DISTANCE = getInteger("distance");

   public boolean isBottom() {
      return this.get(BOTTOM);
   }

   public void setBottom(boolean bottom) {
      this.set(BOTTOM, Boolean.valueOf(bottom));
   }

   public int getDistance() {
      return this.get(DISTANCE);
   }

   public void setDistance(int distance) {
      this.set(DISTANCE, Integer.valueOf(distance));
   }

   public int getMaximumDistance() {
      return getMax(DISTANCE);
   }
}
