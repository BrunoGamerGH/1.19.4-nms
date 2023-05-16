package org.bukkit.craftbukkit.v1_19_R3.block.data;

import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.data.AnaloguePowerable;

public abstract class CraftAnaloguePowerable extends CraftBlockData implements AnaloguePowerable {
   private static final BlockStateInteger POWER = getInteger("power");

   public int getPower() {
      return this.get(POWER);
   }

   public void setPower(int power) {
      this.set(POWER, Integer.valueOf(power));
   }

   public int getMaximumPower() {
      return getMax(POWER);
   }
}
