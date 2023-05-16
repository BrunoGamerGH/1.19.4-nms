package net.minecraft.world.level.entity;

import java.util.List;
import java.util.stream.Stream;
import net.minecraft.world.level.ChunkCoordIntPair;

public class ChunkEntities<T> {
   private final ChunkCoordIntPair a;
   private final List<T> b;

   public ChunkEntities(ChunkCoordIntPair var0, List<T> var1) {
      this.a = var0;
      this.b = var1;
   }

   public ChunkCoordIntPair a() {
      return this.a;
   }

   public Stream<T> b() {
      return this.b.stream();
   }

   public boolean c() {
      return this.b.isEmpty();
   }
}
