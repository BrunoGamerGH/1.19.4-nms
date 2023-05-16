package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.BlockMinecartDetector;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.Rail;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.Rail.Shape;
import org.bukkit.block.data.type.RedstoneRail;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftMinecartDetector extends CraftBlockData implements RedstoneRail, Powerable, Rail, Waterlogged {
   private static final BlockStateBoolean POWERED = getBoolean(BlockMinecartDetector.class, "powered");
   private static final BlockStateEnum<?> SHAPE = getEnum(BlockMinecartDetector.class, "shape");
   private static final BlockStateBoolean WATERLOGGED = getBoolean(BlockMinecartDetector.class, "waterlogged");

   public CraftMinecartDetector() {
   }

   public CraftMinecartDetector(IBlockData state) {
      super(state);
   }

   public boolean isPowered() {
      return this.get(POWERED);
   }

   public void setPowered(boolean powered) {
      this.set(POWERED, Boolean.valueOf(powered));
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
