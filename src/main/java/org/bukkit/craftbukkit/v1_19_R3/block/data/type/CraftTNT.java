package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.block.data.type.TNT;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftTNT extends CraftBlockData implements TNT {
   private static final BlockStateBoolean UNSTABLE = getBoolean("unstable");

   public boolean isUnstable() {
      return this.get(UNSTABLE);
   }

   public void setUnstable(boolean unstable) {
      this.set(UNSTABLE, Boolean.valueOf(unstable));
   }
}
