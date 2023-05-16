package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.WeatheringCopperStairBlock;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.block.data.type.Stairs.Shape;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftWeatheringCopperStair extends CraftBlockData implements Stairs, Bisected, Directional, Waterlogged {
   private static final BlockStateEnum<?> SHAPE = getEnum(WeatheringCopperStairBlock.class, "shape");
   private static final BlockStateEnum<?> HALF = getEnum(WeatheringCopperStairBlock.class, "half");
   private static final BlockStateEnum<?> FACING = getEnum(WeatheringCopperStairBlock.class, "facing");
   private static final BlockStateBoolean WATERLOGGED = getBoolean(WeatheringCopperStairBlock.class, "waterlogged");

   public CraftWeatheringCopperStair() {
   }

   public CraftWeatheringCopperStair(IBlockData state) {
      super(state);
   }

   public Shape getShape() {
      return this.get(SHAPE, Shape.class);
   }

   public void setShape(Shape shape) {
      this.set(SHAPE, shape);
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

   public boolean isWaterlogged() {
      return this.get(WATERLOGGED);
   }

   public void setWaterlogged(boolean waterlogged) {
      this.set(WATERLOGGED, Boolean.valueOf(waterlogged));
   }
}
