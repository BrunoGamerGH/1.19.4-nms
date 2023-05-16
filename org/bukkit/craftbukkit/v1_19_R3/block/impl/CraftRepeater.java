package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.BlockRepeater;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.type.Repeater;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftRepeater extends CraftBlockData implements Repeater, Directional, Powerable {
   private static final BlockStateInteger DELAY = getInteger(BlockRepeater.class, "delay");
   private static final BlockStateBoolean LOCKED = getBoolean(BlockRepeater.class, "locked");
   private static final BlockStateEnum<?> FACING = getEnum(BlockRepeater.class, "facing");
   private static final BlockStateBoolean POWERED = getBoolean(BlockRepeater.class, "powered");

   public CraftRepeater() {
   }

   public CraftRepeater(IBlockData state) {
      super(state);
   }

   public int getDelay() {
      return this.get(DELAY);
   }

   public void setDelay(int delay) {
      this.set(DELAY, Integer.valueOf(delay));
   }

   public int getMinimumDelay() {
      return getMin(DELAY);
   }

   public int getMaximumDelay() {
      return getMax(DELAY);
   }

   public boolean isLocked() {
      return this.get(LOCKED);
   }

   public void setLocked(boolean locked) {
      this.set(LOCKED, Boolean.valueOf(locked));
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
