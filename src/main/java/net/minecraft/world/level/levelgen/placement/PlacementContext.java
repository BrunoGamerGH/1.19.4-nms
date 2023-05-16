package net.minecraft.world.level.levelgen.placement;

import java.util.Optional;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.WorldGenStage;
import net.minecraft.world.level.levelgen.WorldGenerationContext;

public class PlacementContext extends WorldGenerationContext {
   private final GeneratorAccessSeed a;
   private final ChunkGenerator b;
   private final Optional<PlacedFeature> c;

   public PlacementContext(GeneratorAccessSeed var0, ChunkGenerator var1, Optional<PlacedFeature> var2) {
      super(var1, var0);
      this.a = var0;
      this.b = var1;
      this.c = var2;
   }

   public int a(HeightMap.Type var0, int var1, int var2) {
      return this.a.a(var0, var1, var2);
   }

   public CarvingMask a(ChunkCoordIntPair var0, WorldGenStage.Features var1) {
      return ((ProtoChunk)this.a.a(var0.e, var0.f)).b(var1);
   }

   public IBlockData a(BlockPosition var0) {
      return this.a.a_(var0);
   }

   public int c() {
      return this.a.v_();
   }

   public GeneratorAccessSeed d() {
      return this.a;
   }

   public Optional<PlacedFeature> e() {
      return this.c;
   }

   public ChunkGenerator f() {
      return this.b;
   }
}
