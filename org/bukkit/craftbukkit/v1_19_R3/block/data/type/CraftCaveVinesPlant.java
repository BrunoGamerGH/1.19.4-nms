package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.block.data.type.CaveVinesPlant;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftCaveVinesPlant extends CraftBlockData implements CaveVinesPlant {
   private static final BlockStateBoolean BERRIES = getBoolean("berries");

   public boolean isBerries() {
      return this.get(BERRIES);
   }

   public void setBerries(boolean berries) {
      this.set(BERRIES, Boolean.valueOf(berries));
   }
}
