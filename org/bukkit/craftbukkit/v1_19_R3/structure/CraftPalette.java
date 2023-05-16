package org.bukkit.craftbukkit.v1_19_R3.structure;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructure;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlockStates;
import org.bukkit.structure.Palette;

public class CraftPalette implements Palette {
   private final DefinedStructure.a palette;

   public CraftPalette(DefinedStructure.a palette) {
      this.palette = palette;
   }

   public List<BlockState> getBlocks() {
      List<BlockState> blocks = new ArrayList();

      for(DefinedStructure.BlockInfo blockInfo : this.palette.a()) {
         blocks.add(CraftBlockStates.getBlockState(blockInfo.a, blockInfo.b, blockInfo.c));
      }

      return blocks;
   }

   public int getBlockCount() {
      return this.palette.a().size();
   }
}
