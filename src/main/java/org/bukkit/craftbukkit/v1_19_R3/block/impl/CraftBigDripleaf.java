package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.BigDripleafBlock;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.BigDripleaf;
import org.bukkit.block.data.type.Dripleaf;
import org.bukkit.block.data.type.BigDripleaf.Tilt;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftBigDripleaf extends CraftBlockData implements BigDripleaf, Dripleaf, Directional, Waterlogged {
   private static final BlockStateEnum<?> TILT = getEnum(BigDripleafBlock.class, "tilt");
   private static final BlockStateEnum<?> FACING = getEnum(BigDripleafBlock.class, "facing");
   private static final BlockStateBoolean WATERLOGGED = getBoolean(BigDripleafBlock.class, "waterlogged");

   public CraftBigDripleaf() {
   }

   public CraftBigDripleaf(IBlockData state) {
      super(state);
   }

   public Tilt getTilt() {
      return this.get(TILT, Tilt.class);
   }

   public void setTilt(Tilt tilt) {
      this.set(TILT, tilt);
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
