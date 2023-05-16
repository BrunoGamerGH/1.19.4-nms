package org.bukkit.craftbukkit.v1_19_R3.block.data;

import java.util.Set;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.data.Rail;
import org.bukkit.block.data.Rail.Shape;

public abstract class CraftRail extends CraftBlockData implements Rail {
   private static final BlockStateEnum<?> SHAPE = getEnum("shape");

   public Shape getShape() {
      return this.get(SHAPE, Shape.class);
   }

   public void setShape(Shape shape) {
      this.set(SHAPE, shape);
   }

   public Set<Shape> getShapes() {
      return this.getValues(SHAPE, Shape.class);
   }
}
