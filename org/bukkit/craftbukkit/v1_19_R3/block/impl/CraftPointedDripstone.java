package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.PointedDripstone;
import org.bukkit.block.data.type.PointedDripstone.Thickness;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftPointedDripstone extends CraftBlockData implements PointedDripstone, Waterlogged {
   private static final BlockStateEnum<?> VERTICAL_DIRECTION = getEnum(PointedDripstoneBlock.class, "vertical_direction");
   private static final BlockStateEnum<?> THICKNESS = getEnum(PointedDripstoneBlock.class, "thickness");
   private static final BlockStateBoolean WATERLOGGED = getBoolean(PointedDripstoneBlock.class, "waterlogged");

   public CraftPointedDripstone() {
   }

   public CraftPointedDripstone(IBlockData state) {
      super(state);
   }

   public BlockFace getVerticalDirection() {
      return this.get(VERTICAL_DIRECTION, BlockFace.class);
   }

   public void setVerticalDirection(BlockFace direction) {
      this.set(VERTICAL_DIRECTION, direction);
   }

   public Set<BlockFace> getVerticalDirections() {
      return this.getValues(VERTICAL_DIRECTION, BlockFace.class);
   }

   public Thickness getThickness() {
      return this.get(THICKNESS, Thickness.class);
   }

   public void setThickness(Thickness thickness) {
      this.set(THICKNESS, thickness);
   }

   public boolean isWaterlogged() {
      return this.get(WATERLOGGED);
   }

   public void setWaterlogged(boolean waterlogged) {
      this.set(WATERLOGGED, Boolean.valueOf(waterlogged));
   }
}
