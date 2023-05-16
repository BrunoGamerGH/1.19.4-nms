package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.CaveVinesBlock;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.type.CaveVines;
import org.bukkit.block.data.type.CaveVinesPlant;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftCaveVines extends CraftBlockData implements CaveVines, Ageable, CaveVinesPlant {
   private static final BlockStateInteger AGE = getInteger(CaveVinesBlock.class, "age");
   private static final BlockStateBoolean BERRIES = getBoolean(CaveVinesBlock.class, "berries");

   public CraftCaveVines() {
   }

   public CraftCaveVines(IBlockData state) {
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

   public boolean isBerries() {
      return this.get(BERRIES);
   }

   public void setBerries(boolean berries) {
      this.set(BERRIES, Boolean.valueOf(berries));
   }
}
