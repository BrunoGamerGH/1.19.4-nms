package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.BlockComposter;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.data.Levelled;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftComposter extends CraftBlockData implements Levelled {
   private static final BlockStateInteger LEVEL = getInteger(BlockComposter.class, "level");

   public CraftComposter() {
   }

   public CraftComposter(IBlockData state) {
      super(state);
   }

   public int getLevel() {
      return this.get(LEVEL);
   }

   public void setLevel(int level) {
      this.set(LEVEL, Integer.valueOf(level));
   }

   public int getMaximumLevel() {
      return getMax(LEVEL);
   }
}
