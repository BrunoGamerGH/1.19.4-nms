package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.block.data.type.Stairs.Shape;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftStairs extends CraftBlockData implements Stairs {
   private static final BlockStateEnum<?> SHAPE = getEnum("shape");

   public Shape getShape() {
      return this.get(SHAPE, Shape.class);
   }

   public void setShape(Shape shape) {
      this.set(SHAPE, shape);
   }
}
