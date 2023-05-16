package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.BlockCobbleWall;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Wall;
import org.bukkit.block.data.type.Wall.Height;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftCobbleWall extends CraftBlockData implements Wall, Waterlogged {
   private static final BlockStateBoolean UP = getBoolean(BlockCobbleWall.class, "up");
   private static final BlockStateEnum<?>[] HEIGHTS = new BlockStateEnum[]{
      getEnum(BlockCobbleWall.class, "north"),
      getEnum(BlockCobbleWall.class, "east"),
      getEnum(BlockCobbleWall.class, "south"),
      getEnum(BlockCobbleWall.class, "west")
   };
   private static final BlockStateBoolean WATERLOGGED = getBoolean(BlockCobbleWall.class, "waterlogged");

   public CraftCobbleWall() {
   }

   public CraftCobbleWall(IBlockData state) {
      super(state);
   }

   public boolean isUp() {
      return this.get(UP);
   }

   public void setUp(boolean up) {
      this.set(UP, Boolean.valueOf(up));
   }

   public Height getHeight(BlockFace face) {
      return this.get(HEIGHTS[face.ordinal()], Height.class);
   }

   public void setHeight(BlockFace face, Height height) {
      this.set(HEIGHTS[face.ordinal()], height);
   }

   public boolean isWaterlogged() {
      return this.get(WATERLOGGED);
   }

   public void setWaterlogged(boolean waterlogged) {
      this.set(WATERLOGGED, Boolean.valueOf(waterlogged));
   }
}
