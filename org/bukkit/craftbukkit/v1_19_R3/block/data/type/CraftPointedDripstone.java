package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import java.util.Set;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.PointedDripstone;
import org.bukkit.block.data.type.PointedDripstone.Thickness;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftPointedDripstone extends CraftBlockData implements PointedDripstone {
   private static final BlockStateEnum<?> VERTICAL_DIRECTION = getEnum("vertical_direction");
   private static final BlockStateEnum<?> THICKNESS = getEnum("thickness");

   public BlockFace getVerticalDirection() {
      return this.get(VERTICAL_DIRECTION, BlockFace.class);
   }

   public void setVerticalDirection(BlockFace direction) {
      this.set(VERTICAL_DIRECTION, direction);
   }

   public Set<BlockFace> getVerticalDirections() {
      return this.getValues(VERTICAL_DIRECTION, BlockFace.class);
   }

   public Thickness getThickness() {
      return this.get(THICKNESS, Thickness.class);
   }

   public void setThickness(Thickness thickness) {
      this.set(THICKNESS, thickness);
   }
}
