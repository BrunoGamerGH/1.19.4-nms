package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import java.util.Set;
import net.minecraft.world.level.block.BlockCocoa;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Cocoa;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftCocoa extends CraftBlockData implements Cocoa, Ageable, Directional {
   private static final BlockStateInteger AGE = getInteger(BlockCocoa.class, "age");
   private static final BlockStateEnum<?> FACING = getEnum(BlockCocoa.class, "facing");

   public CraftCocoa() {
   }

   public CraftCocoa(IBlockData state) {
      super(state);
   }

   public int getAge() {
      return this.get(AGE);
   }

   public void setAge(int age) {
      this.set(AGE, Integer.valueOf(age));
   }

   public int getMaximumAge() {
      return getMax(AGE);
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
