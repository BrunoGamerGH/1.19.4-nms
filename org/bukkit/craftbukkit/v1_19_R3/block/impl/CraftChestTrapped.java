package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.BlockChestTrapped;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Chest;
import org.bukkit.block.data.type.Chest.Type;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftChestTrapped extends CraftBlockData implements Chest, Directional, Waterlogged {
   private static final BlockStateEnum<?> TYPE = getEnum(BlockChestTrapped.class, "type");
   private static final BlockStateEnum<?> FACING = getEnum(BlockChestTrapped.class, "facing");
   private static final BlockStateBoolean WATERLOGGED = getBoolean(BlockChestTrapped.class, "waterlogged");

   public CraftChestTrapped() {
   }

   public CraftChestTrapped(IBlockData state) {
      super(state);
   }

   public Type getType() {
      return this.get(TYPE, Type.class);
   }

   public void setType(Type type) {
      this.set(TYPE, type);
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
