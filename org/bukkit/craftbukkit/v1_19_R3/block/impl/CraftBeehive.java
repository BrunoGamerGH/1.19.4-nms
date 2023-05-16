package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.BlockBeehive;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Beehive;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftBeehive extends CraftBlockData implements Beehive, Directional {
   private static final BlockStateInteger HONEY_LEVEL = getInteger(BlockBeehive.class, "honey_level");
   private static final BlockStateEnum<?> FACING = getEnum(BlockBeehive.class, "facing");

   public CraftBeehive() {
   }

   public CraftBeehive(IBlockData state) {
      super(state);
   }

   public int getHoneyLevel() {
      return this.get(HONEY_LEVEL);
   }

   public void setHoneyLevel(int honeyLevel) {
      this.set(HONEY_LEVEL, Integer.valueOf(honeyLevel));
   }

   public int getMaximumHoneyLevel() {
      return getMax(HONEY_LEVEL);
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
