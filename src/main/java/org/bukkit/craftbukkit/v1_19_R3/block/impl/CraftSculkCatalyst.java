package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.SculkCatalystBlock;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.block.data.type.SculkCatalyst;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftSculkCatalyst extends CraftBlockData implements SculkCatalyst {
   private static final BlockStateBoolean BLOOM = getBoolean(SculkCatalystBlock.class, "bloom");

   public CraftSculkCatalyst() {
   }

   public CraftSculkCatalyst(IBlockData state) {
      super(state);
   }

   public boolean isBloom() {
      return this.get(BLOOM);
   }

   public void setBloom(boolean bloom) {
      this.set(BLOOM, Boolean.valueOf(bloom));
   }
}
