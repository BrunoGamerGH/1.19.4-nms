package net.minecraft.world.level.chunk;

import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.Fluid;

class ChunkSection$a implements DataPaletteBlock.b<IBlockData> {
   public int a;
   public int b;
   public int c;

   ChunkSection$a(ChunkSection var0) {
      this.d = var0;
   }

   public void a(IBlockData var0, int var1) {
      Fluid var2 = var0.r();
      if (!var0.h()) {
         this.a += var1;
         if (var0.s()) {
            this.b += var1;
         }
      }

      if (!var2.c()) {
         this.a += var1;
         if (var2.f()) {
            this.c += var1;
         }
      }
   }
}
