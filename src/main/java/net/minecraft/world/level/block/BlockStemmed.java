package net.minecraft.world.level.block;

import net.minecraft.world.level.block.state.BlockBase;

public abstract class BlockStemmed extends Block {
   public BlockStemmed(BlockBase.Info var0) {
      super(var0);
   }

   public abstract BlockStem b();

   public abstract BlockStemAttached c();
}
