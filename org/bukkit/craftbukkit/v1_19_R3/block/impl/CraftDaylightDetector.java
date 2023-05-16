package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.BlockDaylightDetector;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.data.AnaloguePowerable;
import org.bukkit.block.data.type.DaylightDetector;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftDaylightDetector extends CraftBlockData implements DaylightDetector, AnaloguePowerable {
   private static final BlockStateBoolean INVERTED = getBoolean(BlockDaylightDetector.class, "inverted");
   private static final BlockStateInteger POWER = getInteger(BlockDaylightDetector.class, "power");

   public CraftDaylightDetector() {
   }

   public CraftDaylightDetector(IBlockData state) {
      super(state);
   }

   public boolean isInverted() {
      return this.get(INVERTED);
   }

   public void setInverted(boolean inverted) {
      this.set(INVERTED, Boolean.valueOf(inverted));
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
