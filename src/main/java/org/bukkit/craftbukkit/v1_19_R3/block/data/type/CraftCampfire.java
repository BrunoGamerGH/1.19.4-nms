package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.block.data.type.Campfire;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftCampfire extends CraftBlockData implements Campfire {
   private static final BlockStateBoolean SIGNAL_FIRE = getBoolean("signal_fire");

   public boolean isSignalFire() {
      return this.get(SIGNAL_FIRE);
   }

   public void setSignalFire(boolean signalFire) {
      this.set(SIGNAL_FIRE, Boolean.valueOf(signalFire));
   }
}
