package org.bukkit.craftbukkit.v1_19_R3.block.data;

import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.Bisected.Half;

public class CraftBisected extends CraftBlockData implements Bisected {
   private static final BlockStateEnum<?> HALF = getEnum("half");

   public Half getHalf() {
      return this.get(HALF, Half.class);
   }

   public void setHalf(Half half) {
      this.set(HALF, half);
   }
}
