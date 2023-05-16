package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftLeaves extends CraftBlockData implements Leaves {
   private static final BlockStateInteger DISTANCE = getInteger("distance");
   private static final BlockStateBoolean PERSISTENT = getBoolean("persistent");

   public boolean isPersistent() {
      return this.get(PERSISTENT);
   }

   public void setPersistent(boolean persistent) {
      this.set(PERSISTENT, Boolean.valueOf(persistent));
   }

   public int getDistance() {
      return this.get(DISTANCE);
   }

   public void setDistance(int distance) {
      this.set(DISTANCE, Integer.valueOf(distance));
   }
}
