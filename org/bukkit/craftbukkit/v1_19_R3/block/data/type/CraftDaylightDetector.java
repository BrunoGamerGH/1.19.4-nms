package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.block.data.type.DaylightDetector;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftDaylightDetector extends CraftBlockData implements DaylightDetector {
   private static final BlockStateBoolean INVERTED = getBoolean("inverted");

   public boolean isInverted() {
      return this.get(INVERTED);
   }

   public void setInverted(boolean inverted) {
      this.set(INVERTED, Boolean.valueOf(inverted));
   }
}
