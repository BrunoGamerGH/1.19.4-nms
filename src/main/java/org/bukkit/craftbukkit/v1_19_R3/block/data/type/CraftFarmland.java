package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.data.type.Farmland;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftFarmland extends CraftBlockData implements Farmland {
   private static final BlockStateInteger MOISTURE = getInteger("moisture");

   public int getMoisture() {
      return this.get(MOISTURE);
   }

   public void setMoisture(int moisture) {
      this.set(MOISTURE, Integer.valueOf(moisture));
   }

   public int getMaximumMoisture() {
      return getMax(MOISTURE);
   }
}
