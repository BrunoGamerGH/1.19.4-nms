package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.block.data.type.CommandBlock;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftCommandBlock extends CraftBlockData implements CommandBlock {
   private static final BlockStateBoolean CONDITIONAL = getBoolean("conditional");

   public boolean isConditional() {
      return this.get(CONDITIONAL);
   }

   public void setConditional(boolean conditional) {
      this.set(CONDITIONAL, Boolean.valueOf(conditional));
   }
}
