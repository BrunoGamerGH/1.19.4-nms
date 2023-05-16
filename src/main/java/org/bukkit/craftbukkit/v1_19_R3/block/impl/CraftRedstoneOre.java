package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.BlockRedstoneOre;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.block.data.Lightable;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftRedstoneOre extends CraftBlockData implements Lightable {
   private static final BlockStateBoolean LIT = getBoolean(BlockRedstoneOre.class, "lit");

   public CraftRedstoneOre() {
   }

   public CraftRedstoneOre(IBlockData state) {
      super(state);
   }

   public boolean isLit() {
      return this.get(LIT);
   }

   public void setLit(boolean lit) {
      this.set(LIT, Boolean.valueOf(lit));
   }
}
