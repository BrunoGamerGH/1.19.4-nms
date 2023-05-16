package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.block.data.type.SculkCatalyst;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftSculkCatalyst extends CraftBlockData implements SculkCatalyst {
   private static final BlockStateBoolean BLOOM = getBoolean("bloom");

   public boolean isBloom() {
      return this.get(BLOOM);
   }

   public void setBloom(boolean bloom) {
      this.set(BLOOM, Boolean.valueOf(bloom));
   }
}
