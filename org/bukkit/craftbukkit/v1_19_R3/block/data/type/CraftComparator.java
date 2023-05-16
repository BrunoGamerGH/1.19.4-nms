package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.data.type.Comparator;
import org.bukkit.block.data.type.Comparator.Mode;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftComparator extends CraftBlockData implements Comparator {
   private static final BlockStateEnum<?> MODE = getEnum("mode");

   public Mode getMode() {
      return this.get(MODE, Mode.class);
   }

   public void setMode(Mode mode) {
      this.set(MODE, mode);
   }
}
