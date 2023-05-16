package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.block.data.type.Hopper;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftHopper extends CraftBlockData implements Hopper {
   private static final BlockStateBoolean ENABLED = getBoolean("enabled");

   public boolean isEnabled() {
      return this.get(ENABLED);
   }

   public void setEnabled(boolean enabled) {
      this.set(ENABLED, Boolean.valueOf(enabled));
   }
}
