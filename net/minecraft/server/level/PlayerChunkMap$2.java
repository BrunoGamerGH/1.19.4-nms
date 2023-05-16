package net.minecraft.server.level;

import com.mojang.datafixers.util.Either;
import net.minecraft.world.level.ChunkCoordIntPair;

class PlayerChunkMap$2 implements PlayerChunk.Failure {
   PlayerChunkMap$2(PlayerChunkMap var0, int var2, int var3, int var4, int var5, Either var6) {
      this.g = var0;
      this.a = var2;
      this.c = var3;
      this.d = var4;
      this.e = var5;
      this.f = var6;
   }

   @Override
   public String toString() {
      return "Unloaded " + new ChunkCoordIntPair(this.a + this.c % (this.d * 2 + 1), this.e + this.c / (this.d * 2 + 1)) + " " + this.f.right().get();
   }
}
