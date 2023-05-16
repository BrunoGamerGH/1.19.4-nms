package org.bukkit.craftbukkit.v1_19_R3.block.data;

import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.block.data.Hangable;

public abstract class CraftHangable extends CraftBlockData implements Hangable {
   private static final BlockStateBoolean HANGING = getBoolean("hanging");

   public boolean isHanging() {
      return this.get(HANGING);
   }

   public void setHanging(boolean hanging) {
      this.set(HANGING, Boolean.valueOf(hanging));
   }
}
