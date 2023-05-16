package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.BlockDoor;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Openable;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.block.data.type.Door;
import org.bukkit.block.data.type.Door.Hinge;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftDoor extends CraftBlockData implements Door, Bisected, Directional, Openable, Powerable {
   private static final BlockStateEnum<?> HINGE = getEnum(BlockDoor.class, "hinge");
   private static final BlockStateEnum<?> HALF = getEnum(BlockDoor.class, "half");
   private static final BlockStateEnum<?> FACING = getEnum(BlockDoor.class, "facing");
   private static final BlockStateBoolean OPEN = getBoolean(BlockDoor.class, "open");
   private static final BlockStateBoolean POWERED = getBoolean(BlockDoor.class, "powered");

   public CraftDoor() {
   }

   public CraftDoor(IBlockData state) {
      super(state);
   }

   public Hinge getHinge() {
      return this.get(HINGE, Hinge.class);
   }

   public void setHinge(Hinge hinge) {
      this.set(HINGE, hinge);
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
}
