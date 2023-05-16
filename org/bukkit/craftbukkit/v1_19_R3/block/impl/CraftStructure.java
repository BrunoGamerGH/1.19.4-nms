package org.bukkit.craftbukkit.v1_19_R3.block.impl;

import net.minecraft.world.level.block.BlockStructure;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import org.bukkit.block.data.type.StructureBlock;
import org.bukkit.block.data.type.StructureBlock.Mode;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;

public final class CraftStructure extends CraftBlockData implements StructureBlock {
   private static final BlockStateEnum<?> MODE = getEnum(BlockStructure.class, "mode");

   public CraftStructure() {
   }

   public CraftStructure(IBlockData state) {
      super(state);
   }

   public Mode getMode() {
      return this.get(MODE, Mode.class);
   }

   public void setMode(Mode mode) {
      this.set(MODE, mode);
   }
}
