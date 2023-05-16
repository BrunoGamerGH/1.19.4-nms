package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.BlockHopper;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Hopper;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftHopper extends CraftBlockData implements Hopper, Directional {
   private static final BlockStateBoolean ENABLED = getBoolean(BlockHopper.class, "enabled");
   private static final BlockStateEnum<?> FACING = getEnum(BlockHopper.class, "facing");

   public CraftHopper() {
   }

   public CraftHopper(IBlockData state) {
      super(state);
   }

   public boolean isEnabled() {
      return this.get(ENABLED);
   }

   public void setEnabled(boolean enabled) {
      this.set(ENABLED, Boolean.valueOf(enabled));
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
