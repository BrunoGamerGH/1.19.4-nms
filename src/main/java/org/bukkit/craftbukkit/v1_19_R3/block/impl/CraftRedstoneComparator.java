package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.BlockRedstoneComparator;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.type.Comparator;
import org.bukkit.block.data.type.Comparator.Mode;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftRedstoneComparator extends CraftBlockData implements Comparator, Directional, Powerable {
   private static final BlockStateEnum<?> MODE = getEnum(BlockRedstoneComparator.class, "mode");
   private static final BlockStateEnum<?> FACING = getEnum(BlockRedstoneComparator.class, "facing");
   private static final BlockStateBoolean POWERED = getBoolean(BlockRedstoneComparator.class, "powered");

   public CraftRedstoneComparator() {
   }

   public CraftRedstoneComparator(IBlockData state) {
      super(state);
   }

   public Mode getMode() {
      return this.get(MODE, Mode.class);
   }

   public void setMode(Mode mode) {
      this.set(MODE, mode);
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

   public boolean isPowered() {
      return this.get(POWERED);
   }

   public void setPowered(boolean powered) {
      this.set(POWERED, Boolean.valueOf(powered));
   }
}
