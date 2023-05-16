package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.block.data.type.Jukebox;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftJukebox extends CraftBlockData implements Jukebox {
   private static final BlockStateBoolean HAS_RECORD = getBoolean("has_record");

   public boolean hasRecord() {
      return this.get(HAS_RECORD);
   }
}
