package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.BlockSeaPickle;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.SeaPickle;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftSeaPickle extends CraftBlockData implements SeaPickle, Waterlogged {
   private static final BlockStateInteger PICKLES = getInteger(BlockSeaPickle.class, "pickles");
   private static final BlockStateBoolean WATERLOGGED = getBoolean(BlockSeaPickle.class, "waterlogged");

   public CraftSeaPickle() {
   }

   public CraftSeaPickle(IBlockData state) {
      super(state);
   }

   public int getPickles() {
      return this.get(PICKLES);
   }

   public void setPickles(int pickles) {
      this.set(PICKLES, Integer.valueOf(pickles));
   }

   public int getMinimumPickles() {
      return getMin(PICKLES);
   }

   public int getMaximumPickles() {
      return getMax(PICKLES);
   }

   public boolean isWaterlogged() {
      return this.get(WATERLOGGED);
   }

   public void setWaterlogged(boolean waterlogged) {
      this.set(WATERLOGGED, Boolean.valueOf(waterlogged));
   }
}
