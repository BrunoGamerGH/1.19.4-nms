package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.BlockFluids;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.data.Levelled;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftFluids extends CraftBlockData implements Levelled {
   private static final BlockStateInteger LEVEL = getInteger(BlockFluids.class, "level");

   public CraftFluids() {
   }

   public CraftFluids(IBlockData state) {
      super(state);
   }

   public int getLevel() {
      return this.get(LEVEL);
   }

   public void setLevel(int level) {
      this.set(LEVEL, Integer.valueOf(level));
   }

   public int getMaximumLevel() {
      return getMax(LEVEL);
   }
}
