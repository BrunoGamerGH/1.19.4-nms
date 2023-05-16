package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.BlockCoralDead;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftCoralDead extends CraftBlockData implements Waterlogged {
   private static final BlockStateBoolean WATERLOGGED = getBoolean(BlockCoralDead.class, "waterlogged");

   public CraftCoralDead() {
   }

   public CraftCoralDead(IBlockData state) {
      super(state);
   }

   public boolean isWaterlogged() {
      return this.get(WATERLOGGED);
   }

   public void setWaterlogged(boolean waterlogged) {
      this.set(WATERLOGGED, Boolean.valueOf(waterlogged));
   }
}
