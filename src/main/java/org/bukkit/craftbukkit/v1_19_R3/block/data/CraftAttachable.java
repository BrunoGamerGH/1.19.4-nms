package org.bukkit.craftbukkit.v1_19_R3.block.data;

import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.block.data.Attachable;

public abstract class CraftAttachable extends CraftBlockData implements Attachable {
   private static final BlockStateBoolean ATTACHED = getBoolean("attached");

   public boolean isAttached() {
      return this.get(ATTACHED);
   }

   public void setAttached(boolean attached) {
      this.set(ATTACHED, Boolean.valueOf(attached));
   }
}
