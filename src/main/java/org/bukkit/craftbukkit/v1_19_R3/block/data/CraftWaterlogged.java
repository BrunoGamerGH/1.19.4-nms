package org.bukkit.craftbukkit.v1_19_R3.block.data;

import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.block.data.Waterlogged;

public abstract class CraftWaterlogged extends CraftBlockData implements Waterlogged {
   private static final BlockStateBoolean WATERLOGGED = getBoolean("waterlogged");

   public boolean isWaterlogged() {
      return this.get(WATERLOGGED);
   }

   public void setWaterlogged(boolean waterlogged) {
      this.set(WATERLOGGED, Boolean.valueOf(waterlogged));
   }
}
