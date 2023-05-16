package net.minecraft.server.level;

import net.minecraft.world.level.ChunkCoordIntPair;

class PlayerChunkMap$1 implements PlayerChunk.Failure {
   PlayerChunkMap$1(PlayerChunkMap var0, ChunkCoordIntPair var2) {
      this.c = var0;
      this.a = var2;
   }

   @Override
   public String toString() {
      return "Unloaded " + this.a;
   }
}
