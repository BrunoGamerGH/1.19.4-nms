package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.BlockJukeBox;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.block.data.type.Jukebox;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftJukeBox extends CraftBlockData implements Jukebox {
   private static final BlockStateBoolean HAS_RECORD = getBoolean(BlockJukeBox.class, "has_record");

   public CraftJukeBox() {
   }

   public CraftJukeBox(IBlockData state) {
      super(state);
   }

   public boolean hasRecord() {
      return this.get(HAS_RECORD);
   }
}
