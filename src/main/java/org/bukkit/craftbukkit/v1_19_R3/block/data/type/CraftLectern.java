package org.bukkit.craftbukkit.v1_19_R3.block.data.type;

import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.block.data.type.Lectern;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public abstract class CraftLectern extends CraftBlockData implements Lectern {
   private static final BlockStateBoolean HAS_BOOK = getBoolean("has_book");

   public boolean hasBook() {
      return this.get(HAS_BOOK);
   }
}
