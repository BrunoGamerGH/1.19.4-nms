package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.BlockLantern;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.block.data.Hangable;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Lantern;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftLantern extends CraftBlockData implements Lantern, Hangable, Waterlogged {
   private static final BlockStateBoolean HANGING = getBoolean(BlockLantern.class, "hanging");
   private static final BlockStateBoolean WATERLOGGED = getBoolean(BlockLantern.class, "waterlogged");

   public CraftLantern() {
   }

   public CraftLantern(IBlockData state) {
      super(state);
   }

   public boolean isHanging() {
      return this.get(HANGING);
   }

   public void setHanging(boolean hanging) {
      this.set(HANGING, Boolean.valueOf(hanging));
   }

   public boolean isWaterlogged() {
      return this.get(WATERLOGGED);
   }

   public void setWaterlogged(boolean waterlogged) {
      this.set(WATERLOGGED, Boolean.valueOf(waterlogged));
   }
}
