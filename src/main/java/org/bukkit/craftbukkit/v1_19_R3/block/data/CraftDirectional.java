package org.bukkit.craftbukkit.v1_19_R3.block.data;

import java.util.Set;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;

public abstract class CraftDirectional extends CraftBlockData implements Directional {
   private static final BlockStateEnum<?> FACING = getEnum("facing");

   public BlockFace getFacing() {
      return this.get(FACING, BlockFace.class);
   }

   public void setFacing(BlockFace facing) {
      this.set(FACING, facing);
   }

   public Set<BlockFace> getFaces() {
      return this.getValues(FACING, BlockFace.class);
   }
}
