package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.data.type.RespawnAnchor;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftRespawnAnchor extends CraftBlockData implements RespawnAnchor {
   private static final BlockStateInteger CHARGES = getInteger("charges");

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
