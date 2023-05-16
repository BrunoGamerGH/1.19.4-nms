package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.BlockTrapdoor;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Openable;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftTrapdoor extends CraftBlockData implements TrapDoor, Bisected, Directional, Openable, Powerable, Waterlogged {
   private static final BlockStateEnum<?> HALF = getEnum(BlockTrapdoor.class, "half");
   private static final BlockStateEnum<?> FACING = getEnum(BlockTrapdoor.class, "facing");
   private static final BlockStateBoolean OPEN = getBoolean(BlockTrapdoor.class, "open");
   private static final BlockStateBoolean POWERED = getBoolean(BlockTrapdoor.class, "powered");
   private static final BlockStateBoolean WATERLOGGED = getBoolean(BlockTrapdoor.class, "waterlogged");

   public CraftTrapdoor() {
   }

   public CraftTrapdoor(IBlockData state) {
      super(state);
   }

   public Half getHalf() {
      return this.get(HALF, Half.class);
   }

   public void setHalf(Half half) {
      this.set(HALF, half);
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

   public boolean isOpen() {
      return this.get(OPEN);
   }

   public void setOpen(boolean open) {
      this.set(OPEN, Boolean.valueOf(open));
   }

   public boolean isPowered() {
      return this.get(POWERED);
   }

   public void setPowered(boolean powered) {
      this.set(POWERED, Boolean.valueOf(powered));
   }

   public boolean isWaterlogged() {
      return this.get(WATERLOGGED);
   }

   public void setWaterlogged(boolean waterlogged) {
      this.set(WATERLOGGED, Boolean.valueOf(waterlogged));
   }
}
