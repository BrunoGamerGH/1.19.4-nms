package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.data.type.Jigsaw;
import org.bukkit.block.data.type.Jigsaw.Orientation;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftJigsaw extends CraftBlockData implements Jigsaw {
   private static final BlockStateEnum<?> ORIENTATION = getEnum("orientation");

   public Orientation getOrientation() {
      return this.get(ORIENTATION, Orientation.class);
   }

   public void setOrientation(Orientation orientation) {
      this.set(ORIENTATION, orientation);
   }
}
