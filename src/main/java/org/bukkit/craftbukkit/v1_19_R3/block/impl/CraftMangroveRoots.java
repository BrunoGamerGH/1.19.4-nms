package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.MangroveRootsBlock;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftMangroveRoots extends CraftBlockData implements Waterlogged {
   private static final BlockStateBoolean WATERLOGGED = getBoolean(MangroveRootsBlock.class, "waterlogged");

   public CraftMangroveRoots() {
   }

   public CraftMangroveRoots(IBlockData state) {
      super(state);
   }

   public boolean isWaterlogged() {
      return this.get(WATERLOGGED);
   }

   public void setWaterlogged(boolean waterlogged) {
      this.set(WATERLOGGED, Boolean.valueOf(waterlogged));
   }
}
