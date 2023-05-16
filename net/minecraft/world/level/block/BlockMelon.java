package net.minecraft.world.level.block;

import net.minecraft.world.level.block.state.BlockBase;

public class BlockMelon extends BlockStemmed {
   protected BlockMelon(BlockBase.Info var0) {
      super(var0);
   }

   @Override
   public BlockStem b() {
      return (BlockStem)Blocks.fd;
   }

   @Override
   public BlockStemAttached c() {
      return (BlockStemAttached)Blocks.fb;
   }
}
