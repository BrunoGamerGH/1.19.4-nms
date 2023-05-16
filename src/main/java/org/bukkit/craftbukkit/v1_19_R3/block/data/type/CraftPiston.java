package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.block.data.type.Piston;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftPiston extends CraftBlockData implements Piston {
   private static final BlockStateBoolean EXTENDED = getBoolean("extended");

   public boolean isExtended() {
      return this.get(EXTENDED);
   }

   public void setExtended(boolean extended) {
      this.set(EXTENDED, Boolean.valueOf(extended));
   }
}
