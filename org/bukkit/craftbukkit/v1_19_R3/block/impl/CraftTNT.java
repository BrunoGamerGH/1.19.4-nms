package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.BlockTNT;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.block.data.type.TNT;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftTNT extends CraftBlockData implements TNT {
   private static final BlockStateBoolean UNSTABLE = getBoolean(BlockTNT.class, "unstable");

   public CraftTNT() {
   }

   public CraftTNT(IBlockData state) {
      super(state);
   }

   public boolean isUnstable() {
      return this.get(UNSTABLE);
   }

   public void setUnstable(boolean unstable) {
      this.set(UNSTABLE, Boolean.valueOf(unstable));
   }
}
