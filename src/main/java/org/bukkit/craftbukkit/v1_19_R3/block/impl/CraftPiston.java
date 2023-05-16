package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.piston.BlockPiston;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Piston;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftPiston extends CraftBlockData implements Piston, Directional {
   private static final BlockStateBoolean EXTENDED = getBoolean(BlockPiston.class, "extended");
   private static final BlockStateEnum<?> FACING = getEnum(BlockPiston.class, "facing");

   public CraftPiston() {
   }

   public CraftPiston(IBlockData state) {
      super(state);
   }

   public boolean isExtended() {
      return this.get(EXTENDED);
   }

   public void setExtended(boolean extended) {
      this.set(EXTENDED, Boolean.valueOf(extended));
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
