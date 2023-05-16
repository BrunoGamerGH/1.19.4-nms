package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.data.Levelled;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Light;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftLight extends CraftBlockData implements Light, Levelled, Waterlogged {
   private static final BlockStateInteger LEVEL = getInteger(LightBlock.class, "level");
   private static final BlockStateBoolean WATERLOGGED = getBoolean(LightBlock.class, "waterlogged");

   public CraftLight() {
   }

   public CraftLight(IBlockData state) {
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

   public boolean isWaterlogged() {
      return this.get(WATERLOGGED);
   }

   public void setWaterlogged(boolean waterlogged) {
      this.set(WATERLOGGED, Boolean.valueOf(waterlogged));
   }
}
