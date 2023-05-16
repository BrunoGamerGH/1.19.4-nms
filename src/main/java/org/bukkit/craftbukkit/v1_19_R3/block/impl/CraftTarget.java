package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.BlockTarget;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftTarget extends CraftBlockData implements AnaloguePowerable {
   private static final BlockStateInteger POWER = getInteger(BlockTarget.class, "power");

   public CraftTarget() {
   }

   public CraftTarget(IBlockData state) {
      super(state);
   }

   public int getPower() {
      return this.get(POWER);
   }

   public void setPower(int power) {
      this.set(POWER, Integer.valueOf(power));
   }

   public int getMaximumPower() {
      return getMax(POWER);
   }
}
