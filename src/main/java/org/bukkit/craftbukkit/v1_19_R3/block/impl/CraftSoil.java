package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.BlockSoil;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.data.type.Farmland;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftSoil extends CraftBlockData implements Farmland {
   private static final BlockStateInteger MOISTURE = getInteger(BlockSoil.class, "moisture");

   public CraftSoil() {
   }

   public CraftSoil(IBlockData state) {
      super(state);
   }

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
