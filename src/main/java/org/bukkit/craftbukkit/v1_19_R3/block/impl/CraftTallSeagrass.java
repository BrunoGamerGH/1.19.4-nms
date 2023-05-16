package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.TallSeagrassBlock;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftTallSeagrass extends CraftBlockData implements Bisected {
   private static final BlockStateEnum<?> HALF = getEnum(TallSeagrassBlock.class, "half");

   public CraftTallSeagrass() {
   }

   public CraftTallSeagrass(IBlockData state) {
      super(state);
   }

   public Half getHalf() {
      return this.get(HALF, Half.class);
   }

   public void setHalf(Half half) {
      this.set(HALF, half);
   }
}
