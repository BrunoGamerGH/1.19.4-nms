package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.SuspiciousSandBlock;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import org.bukkit.block.data.type.SuspiciousSand;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftSuspiciousSand extends CraftBlockData implements SuspiciousSand {
   private static final BlockStateInteger DUSTED = getInteger(SuspiciousSandBlock.class, "dusted");

   public CraftSuspiciousSand() {
   }

   public CraftSuspiciousSand(IBlockData state) {
      super(state);
   }

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
