package net.minecraft.world.level;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.block.Block;

public record BlockActionData(BlockPosition pos, Block block, int paramA, int paramB) {
   private final BlockPosition a;
   private final Block b;
   private final int c;
   private final int d;

   public BlockActionData(BlockPosition var0, Block var1, int var2, int var3) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
      this.d = var3;
   }
}
