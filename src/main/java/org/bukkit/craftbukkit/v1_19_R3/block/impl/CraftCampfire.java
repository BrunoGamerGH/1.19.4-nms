package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.BlockCampfire;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Lightable;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Campfire;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftCampfire extends CraftBlockData implements Campfire, Directional, Lightable, Waterlogged {
   private static final BlockStateBoolean SIGNAL_FIRE = getBoolean(BlockCampfire.class, "signal_fire");
   private static final BlockStateEnum<?> FACING = getEnum(BlockCampfire.class, "facing");
   private static final BlockStateBoolean LIT = getBoolean(BlockCampfire.class, "lit");
   private static final BlockStateBoolean WATERLOGGED = getBoolean(BlockCampfire.class, "waterlogged");

   public CraftCampfire() {
   }

   public CraftCampfire(IBlockData state) {
      super(state);
   }

   public boolean isSignalFire() {
      return this.get(SIGNAL_FIRE);
   }

   public void setSignalFire(boolean signalFire) {
      this.set(SIGNAL_FIRE, Boolean.valueOf(signalFire));
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

   public boolean isLit() {
      return this.get(LIT);
   }

   public void setLit(boolean lit) {
      this.set(LIT, Boolean.valueOf(lit));
   }

   public boolean isWaterlogged() {
      return this.get(WATERLOGGED);
   }

   public void setWaterlogged(boolean waterlogged) {
      this.set(WATERLOGGED, Boolean.valueOf(waterlogged));
   }
}
