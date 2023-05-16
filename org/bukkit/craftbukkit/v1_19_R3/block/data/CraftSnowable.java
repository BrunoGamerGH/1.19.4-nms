package org.bukkit.craftbukkit.v1_19_R3.block.data;

import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.block.data.Snowable;

public abstract class CraftSnowable extends CraftBlockData implements Snowable {
   private static final BlockStateBoolean SNOWY = getBoolean("snowy");

   public boolean isSnowy() {
      return this.get(SNOWY);
   }

   public void setSnowy(boolean snowy) {
      this.set(SNOWY, Boolean.valueOf(snowy));
   }
}
