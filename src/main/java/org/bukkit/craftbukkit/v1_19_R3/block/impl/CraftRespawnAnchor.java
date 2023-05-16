package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.BlockRespawnAnchor;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.data.type.RespawnAnchor;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftRespawnAnchor extends CraftBlockData implements RespawnAnchor {
   private static final BlockStateInteger CHARGES = getInteger(BlockRespawnAnchor.class, "charges");

   public CraftRespawnAnchor() {
   }

   public CraftRespawnAnchor(IBlockData state) {
      super(state);
   }

   public int getCharges() {
      return this.get(CHARGES);
   }

   public void setCharges(int charges) {
      this.set(CHARGES, Integer.valueOf(charges));
   }

   public int getMaximumCharges() {
      return getMax(CHARGES);
   }
}
