package net.minecraft.world.level.chunk.storage;

import java.util.concurrent.CompletableFuture;
import net.minecraft.nbt.StreamTagVisitor;
import net.minecraft.world.level.ChunkCoordIntPair;

public interface ChunkScanAccess {
   CompletableFuture<Void> a(ChunkCoordIntPair var1, StreamTagVisitor var2);
}
