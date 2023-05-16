package net.minecraft.world.level.entity;

import net.minecraft.server.level.PlayerChunk;
import net.minecraft.world.level.ChunkCoordIntPair;

@FunctionalInterface
public interface ChunkStatusUpdateListener {
   void onChunkStatusChange(ChunkCoordIntPair var1, PlayerChunk.State var2);
}
