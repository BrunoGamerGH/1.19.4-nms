package net.minecraft.world.level.levelgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.SectionPosition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.level.RegionLimitedWorldAccess;
import net.minecraft.util.MathHelper;
import net.minecraft.world.level.BlockColumn;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.WorldChunkManagerHell;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.IChunkAccess;
import net.minecraft.world.level.levelgen.blending.Blender;

public class ChunkProviderDebug extends ChunkGenerator {
   public static final Codec<ChunkProviderDebug> c = RecordCodecBuilder.create(
      var0 -> var0.group(RegistryOps.d(Biomes.b)).apply(var0, var0.stable(ChunkProviderDebug::new))
   );
   private static final int h = 2;
   private static final List<IBlockData> i = StreamSupport.stream(BuiltInRegistries.f.spliterator(), false)
      .flatMap(var0 -> var0.n().a().stream())
      .collect(Collectors.toList());
   private static final int j = MathHelper.f(MathHelper.c((float)i.size()));
   private static final int k = MathHelper.f((float)i.size() / (float)j);
   protected static final IBlockData d = Blocks.a.o();
   protected static final IBlockData e = Blocks.hV.o();
   public static final int f = 70;
   public static final int g = 60;

   public ChunkProviderDebug(Holder.c<BiomeBase> var0) {
      super(new WorldChunkManagerHell(var0));
   }

   @Override
   protected Codec<? extends ChunkGenerator> a() {
      return c;
   }

   @Override
   public void a(RegionLimitedWorldAccess var0, StructureManager var1, RandomState var2, IChunkAccess var3) {
   }

   @Override
   public void a(GeneratorAccessSeed var0, IChunkAccess var1, StructureManager var2) {
      BlockPosition.MutableBlockPosition var3 = new BlockPosition.MutableBlockPosition();
      ChunkCoordIntPair var4 = var1.f();
      int var5 = var4.e;
      int var6 = var4.f;

      for(int var7 = 0; var7 < 16; ++var7) {
         for(int var8 = 0; var8 < 16; ++var8) {
            int var9 = SectionPosition.a(var5, var7);
            int var10 = SectionPosition.a(var6, var8);
            var0.a(var3.d(var9, 60, var10), e, 2);
            IBlockData var11 = a(var9, var10);
            var0.a(var3.d(var9, 70, var10), var11, 2);
         }
      }
   }

   @Override
   public CompletableFuture<IChunkAccess> a(Executor var0, Blender var1, RandomState var2, StructureManager var3, IChunkAccess var4) {
      return CompletableFuture.completedFuture(var4);
   }

   @Override
   public int a(int var0, int var1, HeightMap.Type var2, LevelHeightAccessor var3, RandomState var4) {
      return 0;
   }

   @Override
   public BlockColumn a(int var0, int var1, LevelHeightAccessor var2, RandomState var3) {
      return new BlockColumn(0, new IBlockData[0]);
   }

   @Override
   public void a(List<String> var0, RandomState var1, BlockPosition var2) {
   }

   public static IBlockData a(int var0, int var1) {
      IBlockData var2 = d;
      if (var0 > 0 && var1 > 0 && var0 % 2 != 0 && var1 % 2 != 0) {
         var0 /= 2;
         var1 /= 2;
         if (var0 <= j && var1 <= k) {
            int var3 = MathHelper.a(var0 * j + var1);
            if (var3 < i.size()) {
               var2 = i.get(var3);
            }
         }
      }

      return var2;
   }

   @Override
   public void a(
      RegionLimitedWorldAccess var0, long var1, RandomState var3, BiomeManager var4, StructureManager var5, IChunkAccess var6, WorldGenStage.Features var7
   ) {
   }

   @Override
   public void a(RegionLimitedWorldAccess var0) {
   }

   @Override
   public int f() {
      return 0;
   }

   @Override
   public int d() {
      return 384;
   }

   @Override
   public int e() {
      return 63;
   }
}
