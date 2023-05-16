package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.BlockSapling;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.data.type.Sapling;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftSapling extends CraftBlockData implements Sapling {
   private static final BlockStateInteger STAGE = getInteger(BlockSapling.class, "stage");

   public CraftSapling() {
   }

   public CraftSapling(IBlockData state) {
      super(state);
   }

   public int getStage() {
      return this.get(STAGE);
   }

   public void setStage(int stage) {
      this.set(STAGE, Integer.valueOf(stage));
   }

   public int getMaximumStage() {
      return getMax(STAGE);
   }
}
