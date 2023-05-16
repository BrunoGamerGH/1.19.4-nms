package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.BlockSkullPlayerWall;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftSkullPlayerWall extends CraftBlockData implements Directional {
   private static final BlockStateEnum<?> FACING = getEnum(BlockSkullPlayerWall.class, "facing");

   public CraftSkullPlayerWall() {
   }

   public CraftSkullPlayerWall(IBlockData state) {
      super(state);
   }

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
