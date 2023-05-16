package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.BlockMinecartTrack;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.data.Rail;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.Rail.Shape;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftMinecartTrack extends CraftBlockData implements Rail, Waterlogged {
   private static final BlockStateEnum<?> SHAPE = getEnum(BlockMinecartTrack.class, "shape");
   private static final BlockStateBoolean WATERLOGGED = getBoolean(BlockMinecartTrack.class, "waterlogged");

   public CraftMinecartTrack() {
   }

   public CraftMinecartTrack(IBlockData state) {
      super(state);
   }

   public Shape getShape() {
      return this.get(SHAPE, Shape.class);
   }

   public void setShape(Shape shape) {
      this.set(SHAPE, shape);
   }

   public Set<Shape> getShapes() {
      return this.getValues(SHAPE, Shape.class);
   }

   public boolean isWaterlogged() {
      return this.get(WATERLOGGED);
   }

   public void setWaterlogged(boolean waterlogged) {
      this.set(WATERLOGGED, Boolean.valueOf(waterlogged));
   }
}
