package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.data.type.Beehive;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftBeehive extends CraftBlockData implements Beehive {
   private static final BlockStateInteger HONEY_LEVEL = getInteger("honey_level");

   public int getHoneyLevel() {
      return this.get(HONEY_LEVEL);
   }

   public void setHoneyLevel(int honeyLevel) {
      this.set(HONEY_LEVEL, Integer.valueOf(honeyLevel));
   }

   public int getMaximumHoneyLevel() {
      return getMax(HONEY_LEVEL);
   }
}
