package net.minecraft.world.level.levelgen;

import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.chunk.ChunkGenerator;

public class WorldGenerationContext {
   private final int a;
   private final int b;

   public WorldGenerationContext(ChunkGenerator var0, LevelHeightAccessor var1) {
      this.a = Math.max(var1.v_(), var0.f());
      this.b = Math.min(var1.w_(), var0.d());
   }

   public int a() {
      return this.a;
   }

   public int b() {
      return this.b;
   }
}
