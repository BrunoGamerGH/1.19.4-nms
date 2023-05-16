package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.block.data.type.EndPortalFrame;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftEndPortalFrame extends CraftBlockData implements EndPortalFrame {
   private static final BlockStateBoolean EYE = getBoolean("eye");

   public boolean hasEye() {
      return this.get(EYE);
   }

   public void setEye(boolean eye) {
      this.set(EYE, Boolean.valueOf(eye));
   }
}
