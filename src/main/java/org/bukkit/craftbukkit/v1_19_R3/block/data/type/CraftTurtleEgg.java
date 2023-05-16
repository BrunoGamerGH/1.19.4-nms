package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.data.type.TurtleEgg;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftTurtleEgg extends CraftBlockData implements TurtleEgg {
   private static final BlockStateInteger EGGS = getInteger("eggs");
   private static final BlockStateInteger HATCH = getInteger("hatch");

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
