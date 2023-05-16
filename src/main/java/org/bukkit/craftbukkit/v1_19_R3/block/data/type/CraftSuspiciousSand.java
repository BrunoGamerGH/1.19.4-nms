package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.data.type.SuspiciousSand;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftSuspiciousSand extends CraftBlockData implements SuspiciousSand {
   private static final BlockStateInteger DUSTED = getInteger("dusted");

   public int getDusted() {
      return this.get(DUSTED);
   }

   public void setDusted(int dusted) {
      this.set(DUSTED, Integer.valueOf(dusted));
   }

   public int getMaximumDusted() {
      return getMax(DUSTED);
   }
}
