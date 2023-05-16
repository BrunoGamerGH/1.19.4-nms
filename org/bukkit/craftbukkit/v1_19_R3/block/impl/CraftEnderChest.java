package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.BlockEnderChest;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.EnderChest;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftEnderChest extends CraftBlockData implements EnderChest, Directional, Waterlogged {
   private static final BlockStateEnum<?> FACING = getEnum(BlockEnderChest.class, "facing");
   private static final BlockStateBoolean WATERLOGGED = getBoolean(BlockEnderChest.class, "waterlogged");

   public CraftEnderChest() {
   }

   public CraftEnderChest(IBlockData state) {
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

   public boolean isWaterlogged() {
      return this.get(WATERLOGGED);
   }

   public void setWaterlogged(boolean waterlogged) {
      this.set(WATERLOGGED, Boolean.valueOf(waterlogged));
   }
}
