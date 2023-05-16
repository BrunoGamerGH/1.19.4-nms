package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.BlockPortal;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.Axis;
import org.bukkit.block.data.Orientable;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftPortal extends CraftBlockData implements Orientable {
   private static final BlockStateEnum<?> AXIS = getEnum(BlockPortal.class, "axis");

   public CraftPortal() {
   }

   public CraftPortal(IBlockData state) {
      super(state);
   }

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
