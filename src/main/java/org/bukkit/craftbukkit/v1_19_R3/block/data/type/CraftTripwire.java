package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.block.data.type.Tripwire;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftTripwire extends CraftBlockData implements Tripwire {
   private static final BlockStateBoolean DISARMED = getBoolean("disarmed");

   public boolean isDisarmed() {
      return this.get(DISARMED);
   }

   public void setDisarmed(boolean disarmed) {
      this.set(DISARMED, Boolean.valueOf(disarmed));
   }
}
