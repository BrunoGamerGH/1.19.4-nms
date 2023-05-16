package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.BlockKelp;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.data.Ageable;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftKelp extends CraftBlockData implements Ageable {
   private static final BlockStateInteger AGE = getInteger(BlockKelp.class, "age");

   public CraftKelp() {
   }

   public CraftKelp(IBlockData state) {
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
}
