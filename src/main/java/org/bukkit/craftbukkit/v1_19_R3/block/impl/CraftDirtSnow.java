package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.BlockDirtSnow;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.block.data.Snowable;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftDirtSnow extends CraftBlockData implements Snowable {
   private static final BlockStateBoolean SNOWY = getBoolean(BlockDirtSnow.class, "snowy");

   public CraftDirtSnow() {
   }

   public CraftDirtSnow(IBlockData state) {
      super(state);
   }

   public boolean isSnowy() {
      return this.get(SNOWY);
   }

   public void setSnowy(boolean snowy) {
      this.set(SNOWY, Boolean.valueOf(snowy));
   }
}
