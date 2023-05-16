package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.MangroveLeavesBlock;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftMangroveLeaves extends CraftBlockData implements Leaves, Waterlogged {
   private static final BlockStateInteger DISTANCE = getInteger(MangroveLeavesBlock.class, "distance");
   private static final BlockStateBoolean PERSISTENT = getBoolean(MangroveLeavesBlock.class, "persistent");
   private static final BlockStateBoolean WATERLOGGED = getBoolean(MangroveLeavesBlock.class, "waterlogged");

   public CraftMangroveLeaves() {
   }

   public CraftMangroveLeaves(IBlockData state) {
      super(state);
   }

   public boolean isPersistent() {
      return this.get(PERSISTENT);
   }

   public void setPersistent(boolean persistent) {
      this.set(PERSISTENT, Boolean.valueOf(persistent));
   }

   public int getDistance() {
      return this.get(DISTANCE);
   }

   public void setDistance(int distance) {
      this.set(DISTANCE, Integer.valueOf(distance));
   }

   public boolean isWaterlogged() {
      return this.get(WATERLOGGED);
   }

   public void setWaterlogged(boolean waterlogged) {
      this.set(WATERLOGGED, Boolean.valueOf(waterlogged));
   }
}
