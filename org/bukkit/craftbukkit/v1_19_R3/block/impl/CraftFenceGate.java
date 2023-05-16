package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.BlockFenceGate;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Openable;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.type.Gate;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftFenceGate extends CraftBlockData implements Gate, Directional, Openable, Powerable {
   private static final BlockStateBoolean IN_WALL = getBoolean(BlockFenceGate.class, "in_wall");
   private static final BlockStateEnum<?> FACING = getEnum(BlockFenceGate.class, "facing");
   private static final BlockStateBoolean OPEN = getBoolean(BlockFenceGate.class, "open");
   private static final BlockStateBoolean POWERED = getBoolean(BlockFenceGate.class, "powered");

   public CraftFenceGate() {
   }

   public CraftFenceGate(IBlockData state) {
      super(state);
   }

   public boolean isInWall() {
      return this.get(IN_WALL);
   }

   public void setInWall(boolean inWall) {
      this.set(IN_WALL, Boolean.valueOf(inWall));
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
