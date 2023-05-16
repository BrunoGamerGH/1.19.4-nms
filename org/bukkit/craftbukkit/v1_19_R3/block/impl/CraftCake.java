package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.BlockCake;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.data.type.Cake;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftCake extends CraftBlockData implements Cake {
   private static final BlockStateInteger BITES = getInteger(BlockCake.class, "bites");

   public CraftCake() {
   }

   public CraftCake(IBlockData state) {
      super(state);
   }

   public int getBites() {
      return this.get(BITES);
   }

   public void setBites(int bites) {
      this.set(BITES, Integer.valueOf(bites));
   }

   public int getMaximumBites() {
      return getMax(BITES);
   }
}
