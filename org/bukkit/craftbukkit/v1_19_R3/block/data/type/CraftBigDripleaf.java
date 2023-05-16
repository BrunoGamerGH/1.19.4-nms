package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.data.type.BigDripleaf;
import org.bukkit.block.data.type.BigDripleaf.Tilt;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftBigDripleaf extends CraftBlockData implements BigDripleaf {
   private static final BlockStateEnum<?> TILT = getEnum("tilt");

   public Tilt getTilt() {
      return this.get(TILT, Tilt.class);
   }

   public void setTilt(Tilt tilt) {
      this.set(TILT, tilt);
   }
}
