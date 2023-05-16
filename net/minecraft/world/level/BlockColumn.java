package net.minecraft.world.level;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;

public final class BlockColumn implements net.minecraft.world.level.chunk.BlockColumn {
   private final int a;
   private final IBlockData[] b;

   public BlockColumn(int var0, IBlockData[] var1) {
      this.a = var0;
      this.b = var1;
   }

   @Override
   public IBlockData a(int var0) {
      int var1 = var0 - this.a;
      return var1 >= 0 && var1 < this.b.length ? this.b[var1] : Blocks.a.o();
   }

   @Override
   public void a(int var0, IBlockData var1) {
      int var2 = var0 - this.a;
      if (var2 >= 0 && var2 < this.b.length) {
         this.b[var2] = var1;
      } else {
         throw new IllegalArgumentException("Outside of column height: " + var0);
      }
   }
}
