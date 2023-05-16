package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.LightningRod;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftLightningRod extends CraftBlockData implements LightningRod, Directional, Powerable, Waterlogged {
   private static final BlockStateEnum<?> FACING = getEnum(LightningRodBlock.class, "facing");
   private static final BlockStateBoolean POWERED = getBoolean(LightningRodBlock.class, "powered");
   private static final BlockStateBoolean WATERLOGGED = getBoolean(LightningRodBlock.class, "waterlogged");

   public CraftLightningRod() {
   }

   public CraftLightningRod(IBlockData state) {
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

   public boolean isPowered() {
      return this.get(POWERED);
   }

   public void setPowered(boolean powered) {
      this.set(POWERED, Boolean.valueOf(powered));
   }

   public boolean isWaterlogged() {
      return this.get(WATERLOGGED);
   }

   public void setWaterlogged(boolean waterlogged) {
      this.set(WATERLOGGED, Boolean.valueOf(waterlogged));
   }
}
