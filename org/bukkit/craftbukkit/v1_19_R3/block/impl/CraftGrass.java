package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.BlockGrass;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.block.data.Snowable;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftGrass extends CraftBlockData implements Snowable {
   private static final BlockStateBoolean SNOWY = getBoolean(BlockGrass.class, "snowy");

   public CraftGrass() {
   }

   public CraftGrass(IBlockData state) {
      super(state);
   }

   public boolean isSnowy() {
      return this.get(SNOWY);
   }

   public void setSnowy(boolean snowy) {
      this.set(SNOWY, Boolean.valueOf(snowy));
   }
}
