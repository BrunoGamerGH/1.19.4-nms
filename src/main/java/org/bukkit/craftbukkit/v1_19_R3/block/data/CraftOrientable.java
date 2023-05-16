package org.bukkit.craftbukkit.v1_19_R3.block.data;

import java.util.Set;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.Axis;
import org.bukkit.block.data.Orientable;

public class CraftOrientable extends CraftBlockData implements Orientable {
   private static final BlockStateEnum<?> AXIS = getEnum("axis");

   public Axis getAxis() {
      return this.get(AXIS, Axis.class);
   }

   public void setAxis(Axis axis) {
      this.set(AXIS, axis);
   }

   public Set<Axis> getAxes() {
      return this.getValues(AXIS, Axis.class);
   }
}
