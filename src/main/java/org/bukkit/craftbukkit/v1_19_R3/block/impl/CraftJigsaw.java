package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.BlockJigsaw;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.data.type.Jigsaw;
import org.bukkit.block.data.type.Jigsaw.Orientation;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftJigsaw extends CraftBlockData implements Jigsaw {
   private static final BlockStateEnum<?> ORIENTATION = getEnum(BlockJigsaw.class, "orientation");

   public CraftJigsaw() {
   }

   public CraftJigsaw(IBlockData state) {
      super(state);
   }

   public Orientation getOrientation() {
      return this.get(ORIENTATION, Orientation.class);
   }

   public void setOrientation(Orientation orientation) {
      this.set(ORIENTATION, orientation);
   }
}
