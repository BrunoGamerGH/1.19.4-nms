package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.BlockDropper;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftDropper extends CraftBlockData implements Dispenser, Directional {
   private static final BlockStateBoolean TRIGGERED = getBoolean(BlockDropper.class, "triggered");
   private static final BlockStateEnum<?> FACING = getEnum(BlockDropper.class, "facing");

   public CraftDropper() {
   }

   public CraftDropper(IBlockData state) {
      super(state);
   }

   public boolean isTriggered() {
      return this.get(TRIGGERED);
   }

   public void setTriggered(boolean triggered) {
      this.set(TRIGGERED, Boolean.valueOf(triggered));
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
}
