package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.CaveVinesPlantBlock;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.block.data.type.CaveVinesPlant;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftCaveVinesPlant extends CraftBlockData implements CaveVinesPlant {
   private static final BlockStateBoolean BERRIES = getBoolean(CaveVinesPlantBlock.class, "berries");

   public CraftCaveVinesPlant() {
   }

   public CraftCaveVinesPlant(IBlockData state) {
      super(state);
   }

   public boolean isBerries() {
      return this.get(BERRIES);
   }

   public void setBerries(boolean berries) {
      this.set(BERRIES, Boolean.valueOf(berries));
   }
}
