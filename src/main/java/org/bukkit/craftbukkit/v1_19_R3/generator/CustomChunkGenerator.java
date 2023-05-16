package org.bukkit.craftbukkit.v1_19_R3.generator;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.server.level.RegionLimitedWorldAccess;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EnumCreatureType;
import net.minecraft.world.level.BlockColumn;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSettingsMobs;
import net.minecraft.world.level.biome.WorldChunkManager;
import net.minecraft.world.level.block.ITileEntity;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.chunk.ChunkSection;
import net.minecraft.world.level.chunk.IChunkAccess;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.SeededRandom;
import net.minecraft.world.level.levelgen.WorldGenStage;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.bukkit.block.Biome;
import org.bukkit.craftbukkit.v1_19_R3.CraftHeightMap;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_19_R3.util.RandomSourceWrapper;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;
import org.bukkit.generator.ChunkGenerator.ChunkData;

public class CustomChunkGenerator extends InternalChunkGenerator {
   private final ChunkGenerator delegate;
   private final org.bukkit.generator.ChunkGenerator generator;
   private final WorldServer world;
   private final Random random = new Random();
   private boolean newApi;
   private boolean implementBaseHeight = true;

   public CustomChunkGenerator(WorldServer world, ChunkGenerator delegate, org.bukkit.generator.ChunkGenerator generator) {
      super(delegate.c(), delegate.d);
      this.world = world;
      this.delegate = delegate;
      this.generator = generator;
   }

   public ChunkGenerator getDelegate() {
      return this.delegate;
   }

   private static SeededRandom getSeededRandom() {
      return new SeededRandom(new LegacyRandomSource(0L));
   }

   @Override
   public WorldChunkManager c() {
      return this.delegate.c();
   }

   @Override
   public int f() {
      return this.delegate.f();
   }

   @Override
   public int e() {
      return this.delegate.e();
   }

   @Override
   public void a(
      IRegistryCustom iregistrycustom,
      ChunkGeneratorStructureState chunkgeneratorstructurestate,
      StructureManager structuremanager,
      IChunkAccess ichunkaccess,
      StructureTemplateManager structuretemplatemanager
   ) {
      SeededRandom random = getSeededRandom();
      int x = ichunkaccess.f().e;
      int z = ichunkaccess.f().f;
      random.b(MathHelper.b(x, "should-structures".hashCode(), z) ^ this.world.A());
      if (this.generator.shouldGenerateStructures(this.world.getWorld(), new RandomSourceWrapper.RandomWrapper(random), x, z)) {
         super.a(iregistrycustom, chunkgeneratorstructurestate, structuremanager, ichunkaccess, structuretemplatemanager);
      }
   }

   @Override
   public void a(RegionLimitedWorldAccess regionlimitedworldaccess, StructureManager structuremanager, RandomState randomstate, IChunkAccess ichunkaccess) {
      SeededRandom random = getSeededRandom();
      int x = ichunkaccess.f().e;
      int z = ichunkaccess.f().f;
      random.b(MathHelper.b(x, "should-surface".hashCode(), z) ^ regionlimitedworldaccess.A());
      if (this.generator.shouldGenerateSurface(this.world.getWorld(), new RandomSourceWrapper.RandomWrapper(random), x, z)) {
         this.delegate.a(regionlimitedworldaccess, structuremanager, randomstate, ichunkaccess);
      }

      CraftChunkData chunkData = new CraftChunkData(this.world.getWorld(), ichunkaccess);
      random.b((long)x * 341873128712L + (long)z * 132897987541L);
      this.generator.generateSurface(this.world.getWorld(), new RandomSourceWrapper.RandomWrapper(random), x, z, chunkData);
      if (this.generator.shouldGenerateBedrock()) {
         random = getSeededRandom();
         random.b((long)x * 341873128712L + (long)z * 132897987541L);
      }

      random = getSeededRandom();
      random.b((long)x * 341873128712L + (long)z * 132897987541L);
      this.generator.generateBedrock(this.world.getWorld(), new RandomSourceWrapper.RandomWrapper(random), x, z, chunkData);
      chunkData.breakLink();
      if (!this.newApi) {
         this.random.setSeed((long)x * 341873128712L + (long)z * 132897987541L);
         CustomChunkGenerator.CustomBiomeGrid biomegrid = new CustomChunkGenerator.CustomBiomeGrid(ichunkaccess);

         ChunkData data;
         try {
            if (this.generator.isParallelCapable()) {
               data = this.generator.generateChunkData(this.world.getWorld(), this.random, x, z, biomegrid);
            } else {
               synchronized(this) {
                  data = this.generator.generateChunkData(this.world.getWorld(), this.random, x, z, biomegrid);
               }
            }
         } catch (UnsupportedOperationException var23) {
            this.newApi = true;
            return;
         }

         Preconditions.checkArgument(data instanceof OldCraftChunkData, "Plugins must use createChunkData(World) rather than implementing ChunkData: %s", data);
         OldCraftChunkData craftData = (OldCraftChunkData)data;
         ChunkSection[] sections = craftData.getRawChunkData();
         ChunkSection[] csect = ichunkaccess.d();
         int scnt = Math.min(csect.length, sections.length);

         for(int sec = 0; sec < scnt; ++sec) {
            if (sections[sec] != null) {
               ChunkSection section = sections[sec];
               ChunkSection oldSection = csect[sec];

               for(int biomeX = 0; biomeX < 4; ++biomeX) {
                  for(int biomeY = 0; biomeY < 4; ++biomeY) {
                     for(int biomeZ = 0; biomeZ < 4; ++biomeZ) {
                        section.setBiome(biomeX, biomeY, biomeZ, oldSection.c(biomeX, biomeY, biomeZ));
                     }
                  }
               }

               csect[sec] = section;
            }
         }

         if (craftData.getTiles() != null) {
            for(BlockPosition pos : craftData.getTiles()) {
               int tx = pos.u();
               int ty = pos.v();
               int tz = pos.w();
               IBlockData block = craftData.getTypeId(tx, ty, tz);
               if (block.q()) {
                  TileEntity tile = ((ITileEntity)block.b()).a(new BlockPosition((x << 4) + tx, ty, (z << 4) + tz), block);
                  ichunkaccess.a(tile);
               }
            }
         }

         for(BlockPosition lightPosition : craftData.getLights()) {
            ((ProtoChunk)ichunkaccess).j(new BlockPosition((x << 4) + lightPosition.u(), lightPosition.v(), (z << 4) + lightPosition.w()));
         }
      }
   }

   @Override
   public void a(
      RegionLimitedWorldAccess regionlimitedworldaccess,
      long seed,
      RandomState randomstate,
      BiomeManager biomemanager,
      StructureManager structuremanager,
      IChunkAccess ichunkaccess,
      WorldGenStage.Features worldgenstage_features
   ) {
      SeededRandom random = getSeededRandom();
      int x = ichunkaccess.f().e;
      int z = ichunkaccess.f().f;
      random.b(MathHelper.b(x, "should-caves".hashCode(), z) ^ regionlimitedworldaccess.A());
      if (this.generator.shouldGenerateCaves(this.world.getWorld(), new RandomSourceWrapper.RandomWrapper(random), x, z)) {
         this.delegate.a(regionlimitedworldaccess, seed, randomstate, biomemanager, structuremanager, ichunkaccess, worldgenstage_features);
      }

      if (worldgenstage_features == WorldGenStage.Features.b) {
         CraftChunkData chunkData = new CraftChunkData(this.world.getWorld(), ichunkaccess);
         random.a(seed, 0, 0);
         this.generator.generateCaves(this.world.getWorld(), new RandomSourceWrapper.RandomWrapper(random), x, z, chunkData);
         chunkData.breakLink();
      }
   }

   @Override
   public CompletableFuture<IChunkAccess> a(
      Executor executor, Blender blender, RandomState randomstate, StructureManager structuremanager, IChunkAccess ichunkaccess
   ) {
      CompletableFuture<IChunkAccess> future = null;
      SeededRandom random = getSeededRandom();
      int x = ichunkaccess.f().e;
      int z = ichunkaccess.f().f;
      random.b(MathHelper.b(x, "should-noise".hashCode(), z) ^ this.world.A());
      if (this.generator.shouldGenerateNoise(this.world.getWorld(), new RandomSourceWrapper.RandomWrapper(random), x, z)) {
         future = this.delegate.a(executor, blender, randomstate, structuremanager, ichunkaccess);
      }

      Function<IChunkAccess, IChunkAccess> function = ichunkaccess1 -> {
         CraftChunkData chunkData = new CraftChunkData(this.world.getWorld(), ichunkaccess1);
         random.b((long)x * 341873128712L + (long)z * 132897987541L);
         this.generator.generateNoise(this.world.getWorld(), new RandomSourceWrapper.RandomWrapper(random), x, z, chunkData);
         chunkData.breakLink();
         return ichunkaccess1;
      };
      return future == null ? CompletableFuture.supplyAsync(() -> function.apply(ichunkaccess), SystemUtils.f()) : future.thenApply(function);
   }

   @Override
   public int a(int i, int j, HeightMap.Type heightmap_type, LevelHeightAccessor levelheightaccessor, RandomState randomstate) {
      if (this.implementBaseHeight) {
         try {
            SeededRandom random = getSeededRandom();
            int xChunk = i >> 4;
            int zChunk = j >> 4;
            random.b((long)xChunk * 341873128712L + (long)zChunk * 132897987541L);
            return this.generator
               .getBaseHeight(this.world.getWorld(), new RandomSourceWrapper.RandomWrapper(random), i, j, CraftHeightMap.fromNMS(heightmap_type));
         } catch (UnsupportedOperationException var9) {
            this.implementBaseHeight = false;
         }
      }

      return this.delegate.a(i, j, heightmap_type, levelheightaccessor, randomstate);
   }

   @Override
   public WeightedRandomList<BiomeSettingsMobs.c> a(
      Holder<BiomeBase> holder, StructureManager structuremanager, EnumCreatureType enumcreaturetype, BlockPosition blockposition
   ) {
      return this.delegate.a(holder, structuremanager, enumcreaturetype, blockposition);
   }

   @Override
   public void a(GeneratorAccessSeed generatoraccessseed, IChunkAccess ichunkaccess, StructureManager structuremanager) {
      SeededRandom random = getSeededRandom();
      int x = ichunkaccess.f().e;
      int z = ichunkaccess.f().f;
      random.b(MathHelper.b(x, "should-decoration".hashCode(), z) ^ generatoraccessseed.A());
      super.applyBiomeDecoration(
         generatoraccessseed,
         ichunkaccess,
         structuremanager,
         this.generator.shouldGenerateDecorations(this.world.getWorld(), new RandomSourceWrapper.RandomWrapper(random), x, z)
      );
   }

   @Override
   public void a(List<String> list, RandomState randomstate, BlockPosition blockposition) {
      this.delegate.a(list, randomstate, blockposition);
   }

   @Override
   public void a(RegionLimitedWorldAccess regionlimitedworldaccess) {
      SeededRandom random = getSeededRandom();
      int x = regionlimitedworldaccess.a().e;
      int z = regionlimitedworldaccess.a().f;
      random.b(MathHelper.b(x, "should-mobs".hashCode(), z) ^ regionlimitedworldaccess.A());
      if (this.generator.shouldGenerateMobs(this.world.getWorld(), new RandomSourceWrapper.RandomWrapper(random), x, z)) {
         this.delegate.a(regionlimitedworldaccess);
      }
   }

   @Override
   public int a(LevelHeightAccessor levelheightaccessor) {
      return this.delegate.a(levelheightaccessor);
   }

   @Override
   public int d() {
      return this.delegate.d();
   }

   @Override
   public BlockColumn a(int i, int j, LevelHeightAccessor levelheightaccessor, RandomState randomstate) {
      return this.delegate.a(i, j, levelheightaccessor, randomstate);
   }

   @Override
   protected Codec<? extends ChunkGenerator> a() {
      return Codec.unit(null);
   }

   @Deprecated
   private class CustomBiomeGrid implements BiomeGrid {
      private final IChunkAccess biome;

      public CustomBiomeGrid(IChunkAccess biome) {
         this.biome = biome;
      }

      public Biome getBiome(int x, int z) {
         return this.getBiome(x, 0, z);
      }

      public void setBiome(int x, int z, Biome bio) {
         for(int y = CustomChunkGenerator.this.world.getWorld().getMinHeight(); y < CustomChunkGenerator.this.world.getWorld().getMaxHeight(); y += 4) {
            this.setBiome(x, y, z, bio);
         }
      }

      public Biome getBiome(int x, int y, int z) {
         return CraftBlock.biomeBaseToBiome(this.biome.biomeRegistry, this.biome.getNoiseBiome(x >> 2, y >> 2, z >> 2));
      }

      public void setBiome(int x, int y, int z, Biome bio) {
         Preconditions.checkArgument(bio != Biome.CUSTOM, "Cannot set the biome to %s", bio);
         this.biome.setBiome(x >> 2, y >> 2, z >> 2, CraftBlock.biomeToBiomeBase(this.biome.biomeRegistry, bio));
      }
   }
}
