package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.BlockSnow;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.data.type.Snow;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftSnow extends CraftBlockData implements Snow {
   private static final BlockStateInteger LAYERS = getInteger(BlockSnow.class, "layers");

   public CraftSnow() {
   }

   public CraftSnow(IBlockData state) {
      super(state);
   }

   public int getLayers() {
      return this.get(LAYERS);
   }

   public void setLayers(int layers) {
      this.set(LAYERS, Integer.valueOf(layers));
   }

   public int getMinimumLayers() {
      return getMin(LAYERS);
   }

   public int getMaximumLayers() {
      return getMax(LAYERS);
   }
}
