package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.BlockTurtleEgg;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.data.type.TurtleEgg;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftTurtleEgg extends CraftBlockData implements TurtleEgg {
   private static final BlockStateInteger EGGS = getInteger(BlockTurtleEgg.class, "eggs");
   private static final BlockStateInteger HATCH = getInteger(BlockTurtleEgg.class, "hatch");

   public CraftTurtleEgg() {
   }

   public CraftTurtleEgg(IBlockData state) {
      super(state);
   }

   public int getEggs() {
      return this.get(EGGS);
   }

   public void setEggs(int eggs) {
      this.set(EGGS, Integer.valueOf(eggs));
   }

   public int getMinimumEggs() {
      return getMin(EGGS);
   }

   public int getMaximumEggs() {
      return getMax(EGGS);
   }

   public int getHatch() {
      return this.get(HATCH);
   }

   public void setHatch(int hatch) {
      this.set(HATCH, Integer.valueOf(hatch));
   }

   public int getMaximumHatch() {
      return getMax(HATCH);
   }
}
