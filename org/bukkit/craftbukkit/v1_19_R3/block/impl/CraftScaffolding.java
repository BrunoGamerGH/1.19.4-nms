package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.BlockScaffolding;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Scaffolding;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftScaffolding extends CraftBlockData implements Scaffolding, Waterlogged {
   private static final BlockStateBoolean BOTTOM = getBoolean(BlockScaffolding.class, "bottom");
   private static final BlockStateInteger DISTANCE = getInteger(BlockScaffolding.class, "distance");
   private static final BlockStateBoolean WATERLOGGED = getBoolean(BlockScaffolding.class, "waterlogged");

   public CraftScaffolding() {
   }

   public CraftScaffolding(IBlockData state) {
      super(state);
   }

   public boolean isBottom() {
      return this.get(BOTTOM);
   }

   public void setBottom(boolean bottom) {
      this.set(BOTTOM, Boolean.valueOf(bottom));
   }

   public int getDistance() {
      return this.get(DISTANCE);
   }

   public void setDistance(int distance) {
      this.set(DISTANCE, Integer.valueOf(distance));
   }

   public int getMaximumDistance() {
      return getMax(DISTANCE);
   }

   public boolean isWaterlogged() {
      return this.get(WATERLOGGED);
   }

   public void setWaterlogged(boolean waterlogged) {
      this.set(WATERLOGGED, Boolean.valueOf(waterlogged));
   }
}
