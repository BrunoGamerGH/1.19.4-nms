package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.data.type.PinkPetals;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftPinkPetals extends CraftBlockData implements PinkPetals {
   private static final BlockStateInteger FLOWER_AMOUNT = getInteger("flower_amount");

   public int getFlowerAmount() {
      return this.get(FLOWER_AMOUNT);
   }

   public void setFlowerAmount(int flower_amount) {
      this.set(FLOWER_AMOUNT, Integer.valueOf(flower_amount));
   }

   public int getMaximumFlowerAmount() {
      return getMax(FLOWER_AMOUNT);
   }
}
