package net.minecraft.server.level.progress;

import javax.annotation.Nullable;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.chunk.ChunkStatus;

public interface WorldLoadListener {
   void a(ChunkCoordIntPair var1);

   void a(ChunkCoordIntPair var1, @Nullable ChunkStatus var2);

   void a();

   void b();
}
