package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftDispenser extends CraftBlockData implements Dispenser {
   private static final BlockStateBoolean TRIGGERED = getBoolean("triggered");

   public boolean isTriggered() {
      return this.get(TRIGGERED);
   }

   public void setTriggered(boolean triggered) {
      this.set(TRIGGERED, Boolean.valueOf(triggered));
   }
}
