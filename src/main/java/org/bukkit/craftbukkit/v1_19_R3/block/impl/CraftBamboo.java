package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.BlockBamboo;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.type.Bamboo;
import org.bukkit.block.data.type.Sapling;
import org.bukkit.block.data.type.Bamboo.Leaves;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftBamboo extends CraftBlockData implements Bamboo, Ageable, Sapling {
   private static final BlockStateEnum<?> LEAVES = getEnum(BlockBamboo.class, "leaves");
   private static final BlockStateInteger AGE = getInteger(BlockBamboo.class, "age");
   private static final BlockStateInteger STAGE = getInteger(BlockBamboo.class, "stage");

   public CraftBamboo() {
   }

   public CraftBamboo(IBlockData state) {
      super(state);
   }

   public Leaves getLeaves() {
      return this.get(LEAVES, Leaves.class);
   }

   public void setLeaves(Leaves leaves) {
      this.set(LEAVES, leaves);
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

   public int getStage() {
      return this.get(STAGE);
   }

   public void setStage(int stage) {
      this.set(STAGE, Integer.valueOf(stage));
   }

   public int getMaximumStage() {
      return getMax(STAGE);
   }
}
