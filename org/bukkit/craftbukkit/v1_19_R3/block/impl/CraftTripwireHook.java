package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.BlockTripwireHook;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Attachable;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.type.TripwireHook;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftTripwireHook extends CraftBlockData implements TripwireHook, Attachable, Directional, Powerable {
   private static final BlockStateBoolean ATTACHED = getBoolean(BlockTripwireHook.class, "attached");
   private static final BlockStateEnum<?> FACING = getEnum(BlockTripwireHook.class, "facing");
   private static final BlockStateBoolean POWERED = getBoolean(BlockTripwireHook.class, "powered");

   public CraftTripwireHook() {
   }

   public CraftTripwireHook(IBlockData state) {
      super(state);
   }

   public boolean isAttached() {
      return this.get(ATTACHED);
   }

   public void setAttached(boolean attached) {
      this.set(ATTACHED, Boolean.valueOf(attached));
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
}
