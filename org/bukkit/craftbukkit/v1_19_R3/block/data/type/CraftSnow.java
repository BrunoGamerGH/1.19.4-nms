package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.data.type.Snow;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public class CraftSnow extends CraftBlockData implements Snow {
   private static final BlockStateInteger LAYERS = getInteger("layers");

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
