package org.bukkit.craftbukkit.v1_19_R3.block.data;

import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.block.data.Lightable;

public abstract class CraftLightable extends CraftBlockData implements Lightable {
   private static final BlockStateBoolean LIT = getBoolean("lit");

   public boolean isLit() {
      return this.get(LIT);
   }

   public void setLit(boolean lit) {
      this.set(LIT, Boolean.valueOf(lit));
   }
}
