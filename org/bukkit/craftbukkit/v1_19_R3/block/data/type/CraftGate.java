package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.block.data.type.Gate;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftGate extends CraftBlockData implements Gate {
   private static final BlockStateBoolean IN_WALL = getBoolean("in_wall");

   public boolean isInWall() {
      return this.get(IN_WALL);
   }

   public void setInWall(boolean inWall) {
      this.set(IN_WALL, Boolean.valueOf(inWall));
   }
}
