package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.PinkPetalsBlock;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.PinkPetals;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftPinkPetals extends CraftBlockData implements PinkPetals, Directional {
   private static final BlockStateInteger FLOWER_AMOUNT = getInteger(PinkPetalsBlock.class, "flower_amount");
   private static final BlockStateEnum<?> FACING = getEnum(PinkPetalsBlock.class, "facing");

   public CraftPinkPetals() {
   }

   public CraftPinkPetals(IBlockData state) {
      super(state);
   }

   public int getFlowerAmount() {
      return this.get(FLOWER_AMOUNT);
   }

   public void setFlowerAmount(int flower_amount) {
      this.set(FLOWER_AMOUNT, Integer.valueOf(flower_amount));
   }

   public int getMaximumFlowerAmount() {
      return getMax(FLOWER_AMOUNT);
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
