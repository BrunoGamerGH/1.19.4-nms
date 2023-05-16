package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.data.Levelled;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftLayeredCauldron extends CraftBlockData implements Levelled {
   private static final BlockStateInteger LEVEL = getInteger(LayeredCauldronBlock.class, "level");

   public CraftLayeredCauldron() {
   }

   public CraftLayeredCauldron(IBlockData state) {
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
