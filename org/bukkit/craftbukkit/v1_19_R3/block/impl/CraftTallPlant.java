package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.BlockTallPlant;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftTallPlant extends CraftBlockData implements Bisected {
   private static final BlockStateEnum<?> HALF = getEnum(BlockTallPlant.class, "half");

   public CraftTallPlant() {
   }

   public CraftTallPlant(IBlockData state) {
      super(state);
   }

   public Half getHalf() {
      return this.get(HALF, Half.class);
   }

   public void setHalf(Half half) {
      this.set(HALF, half);
   }
}
