package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.BlockBarrel;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Openable;
import org.bukkit.block.data.type.Barrel;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftBarrel extends CraftBlockData implements Barrel, Directional, Openable {
   private static final BlockStateEnum<?> FACING = getEnum(BlockBarrel.class, "facing");
   private static final BlockStateBoolean OPEN = getBoolean(BlockBarrel.class, "open");

   public CraftBarrel() {
   }

   public CraftBarrel(IBlockData state) {
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

   public boolean isOpen() {
      return this.get(OPEN);
   }

   public void setOpen(boolean open) {
      this.set(OPEN, Boolean.valueOf(open));
   }
}
