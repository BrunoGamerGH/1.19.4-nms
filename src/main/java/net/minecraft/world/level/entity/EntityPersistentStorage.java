package net.minecraft.world.level.entity;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import net.minecraft.world.level.ChunkCoordIntPair;

public interface EntityPersistentStorage<T> extends AutoCloseable {
   CompletableFuture<ChunkEntities<T>> a(ChunkCoordIntPair var1);

   void a(ChunkEntities<T> var1);

   void a(boolean var1);

   @Override
   default void close() throws IOException {
   }
}
