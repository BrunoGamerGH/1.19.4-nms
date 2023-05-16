package net.minecraft.world.level.levelgen.carver;

import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.IChunkAccess;
import net.minecraft.world.level.levelgen.ChunkGeneratorAbstract;
import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.WorldGenerationContext;

public class CarvingContext extends WorldGenerationContext {
   private final IRegistryCustom a;
   private final NoiseChunk b;
   private final RandomState c;
   private final SurfaceRules.o d;

   public CarvingContext(ChunkGeneratorAbstract var0, IRegistryCustom var1, LevelHeightAccessor var2, NoiseChunk var3, RandomState var4, SurfaceRules.o var5) {
      super(var0, var2);
      this.a = var1;
      this.b = var3;
      this.c = var4;
      this.d = var5;
   }

   @Deprecated
   public Optional<IBlockData> a(Function<BlockPosition, Holder<BiomeBase>> var0, IChunkAccess var1, BlockPosition var2, boolean var3) {
      return this.c.c().a(this.d, this, var0, var1, this.b, var2, var3);
   }

   @Deprecated
   public IRegistryCustom c() {
      return this.a;
   }

   public RandomState d() {
      return this.c;
   }
}
