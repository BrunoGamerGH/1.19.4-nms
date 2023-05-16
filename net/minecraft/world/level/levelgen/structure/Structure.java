package net.minecraft.world.level.levelgen.structure;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.QuartPos;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.INamable;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EnumCreatureType;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.WorldChunkManager;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.SeededRandom;
import net.minecraft.world.level.levelgen.WorldGenStage;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public abstract class Structure {
   public static final Codec<Structure> a = BuiltInRegistries.T.q().dispatch(Structure::e, StructureType::codec);
   public static final Codec<Holder<Structure>> b = RegistryFileCodec.a(Registries.ax, a);
   protected final Structure.c c;

   public static <S extends Structure> RecordCodecBuilder<S, Structure.c> a(Instance<S> var0) {
      return Structure.c.a.forGetter(var0x -> var0x.c);
   }

   public static <S extends Structure> Codec<S> a(Function<Structure.c, S> var0) {
      return RecordCodecBuilder.create(var1 -> var1.group(a(var1)).apply(var1, var0));
   }

   protected Structure(Structure.c var0) {
      this.c = var0;
   }

   public HolderSet<BiomeBase> a() {
      return this.c.b;
   }

   public Map<EnumCreatureType, StructureSpawnOverride> b() {
      return this.c.c;
   }

   public WorldGenStage.Decoration c() {
      return this.c.d;
   }

   public TerrainAdjustment d() {
      return this.c.e;
   }

   public StructureBoundingBox a(StructureBoundingBox var0) {
      return this.d() != TerrainAdjustment.a ? var0.a(12) : var0;
   }

   public StructureStart a(
      IRegistryCustom var0,
      ChunkGenerator var1,
      WorldChunkManager var2,
      RandomState var3,
      StructureTemplateManager var4,
      long var5,
      ChunkCoordIntPair var7,
      int var8,
      LevelHeightAccessor var9,
      Predicate<Holder<BiomeBase>> var10
   ) {
      Structure.a var11 = new Structure.a(var0, var1, var2, var3, var4, var5, var7, var9, var10);
      Optional<Structure.b> var12 = this.b(var11);
      if (var12.isPresent()) {
         StructurePiecesBuilder var13 = var12.get().a();
         StructureStart var14 = new StructureStart(this, var7, var8, var13.a());
         if (var14.b()) {
            return var14;
         }
      }

      return StructureStart.b;
   }

   protected static Optional<Structure.b> a(Structure.a var0, HeightMap.Type var1, Consumer<StructurePiecesBuilder> var2) {
      ChunkCoordIntPair var3 = var0.h();
      int var4 = var3.b();
      int var5 = var3.c();
      int var6 = var0.b().c(var4, var5, var1, var0.i(), var0.d());
      return Optional.of(new Structure.b(new BlockPosition(var4, var6, var5), var2));
   }

   private static boolean a(Structure.b var0, Structure.a var1) {
      BlockPosition var2 = var0.b();
      return var1.j.test(var1.b.c().getNoiseBiome(QuartPos.a(var2.u()), QuartPos.a(var2.v()), QuartPos.a(var2.w()), var1.d.b()));
   }

   public void a(
      GeneratorAccessSeed var0,
      StructureManager var1,
      ChunkGenerator var2,
      RandomSource var3,
      StructureBoundingBox var4,
      ChunkCoordIntPair var5,
      PiecesContainer var6
   ) {
   }

   private static int[] b(Structure.a var0, int var1, int var2, int var3, int var4) {
      ChunkGenerator var5 = var0.b();
      LevelHeightAccessor var6 = var0.i();
      RandomState var7 = var0.d();
      return new int[]{
         var5.c(var1, var3, HeightMap.Type.a, var6, var7),
         var5.c(var1, var3 + var4, HeightMap.Type.a, var6, var7),
         var5.c(var1 + var2, var3, HeightMap.Type.a, var6, var7),
         var5.c(var1 + var2, var3 + var4, HeightMap.Type.a, var6, var7)
      };
   }

   protected static int a(Structure.a var0, int var1, int var2) {
      ChunkCoordIntPair var3 = var0.h();
      int var4 = var3.d();
      int var5 = var3.e();
      return a(var0, var4, var5, var1, var2);
   }

   protected static int a(Structure.a var0, int var1, int var2, int var3, int var4) {
      int[] var5 = b(var0, var1, var3, var2, var4);
      return Math.min(Math.min(var5[0], var5[1]), Math.min(var5[2], var5[3]));
   }

   @Deprecated
   protected BlockPosition a(Structure.a var0, EnumBlockRotation var1) {
      int var2 = 5;
      int var3 = 5;
      if (var1 == EnumBlockRotation.b) {
         var2 = -5;
      } else if (var1 == EnumBlockRotation.c) {
         var2 = -5;
         var3 = -5;
      } else if (var1 == EnumBlockRotation.d) {
         var3 = -5;
      }

      ChunkCoordIntPair var4 = var0.h();
      int var5 = var4.a(7);
      int var6 = var4.b(7);
      return new BlockPosition(var5, a(var0, var5, var6, var2, var3), var6);
   }

   protected abstract Optional<Structure.b> a(Structure.a var1);

   public Optional<Structure.b> b(Structure.a var0) {
      return this.a(var0).filter(var1x -> a(var1x, var0));
   }

   public abstract StructureType<?> e();

   public static record a(
      IRegistryCustom registryAccess,
      ChunkGenerator chunkGenerator,
      WorldChunkManager biomeSource,
      RandomState randomState,
      StructureTemplateManager structureTemplateManager,
      SeededRandom random,
      long seed,
      ChunkCoordIntPair chunkPos,
      LevelHeightAccessor heightAccessor,
      Predicate<Holder<BiomeBase>> validBiome
   ) {
      private final IRegistryCustom a;
      final ChunkGenerator b;
      private final WorldChunkManager c;
      final RandomState d;
      private final StructureTemplateManager e;
      private final SeededRandom f;
      private final long g;
      private final ChunkCoordIntPair h;
      private final LevelHeightAccessor i;
      final Predicate<Holder<BiomeBase>> j;

      public a(
         IRegistryCustom var0,
         ChunkGenerator var1,
         WorldChunkManager var2,
         RandomState var3,
         StructureTemplateManager var4,
         long var5,
         ChunkCoordIntPair var7,
         LevelHeightAccessor var8,
         Predicate<Holder<BiomeBase>> var9
      ) {
         this(var0, var1, var2, var3, var4, a(var5, var7), var5, var7, var8, var9);
      }

      public a(
         IRegistryCustom var0,
         ChunkGenerator var1,
         WorldChunkManager var2,
         RandomState var3,
         StructureTemplateManager var4,
         SeededRandom var5,
         long var6,
         ChunkCoordIntPair var8,
         LevelHeightAccessor var9,
         Predicate<Holder<BiomeBase>> var10
      ) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
         this.d = var3;
         this.e = var4;
         this.f = var5;
         this.g = var6;
         this.h = var8;
         this.i = var9;
         this.j = var10;
      }

      private static SeededRandom a(long var0, ChunkCoordIntPair var2) {
         SeededRandom var3 = new SeededRandom(new LegacyRandomSource(0L));
         var3.c(var0, var2.e, var2.f);
         return var3;
      }
   }

   public static record b(BlockPosition position, Either<Consumer<StructurePiecesBuilder>, StructurePiecesBuilder> generator) {
      private final BlockPosition a;
      private final Either<Consumer<StructurePiecesBuilder>, StructurePiecesBuilder> b;

      public b(BlockPosition var0, Consumer<StructurePiecesBuilder> var1) {
         this(var0, Either.left(var1));
      }

      public b(BlockPosition var0, Either<Consumer<StructurePiecesBuilder>, StructurePiecesBuilder> var1) {
         this.a = var0;
         this.b = var1;
      }

      public StructurePiecesBuilder a() {
         return (StructurePiecesBuilder)this.b.map(var0 -> {
            StructurePiecesBuilder var1 = new StructurePiecesBuilder();
            var0.accept(var1);
            return var1;
         }, var0 -> var0);
      }

      public BlockPosition b() {
         return this.a;
      }

      public Either<Consumer<StructurePiecesBuilder>, StructurePiecesBuilder> c() {
         return this.b;
      }
   }

   public static record c(
      HolderSet<BiomeBase> biomes,
      Map<EnumCreatureType, StructureSpawnOverride> spawnOverrides,
      WorldGenStage.Decoration step,
      TerrainAdjustment terrainAdaptation
   ) {
      final HolderSet<BiomeBase> b;
      final Map<EnumCreatureType, StructureSpawnOverride> c;
      final WorldGenStage.Decoration d;
      final TerrainAdjustment e;
      public static final MapCodec<Structure.c> a = RecordCodecBuilder.mapCodec(
         var0 -> var0.group(
                  RegistryCodecs.a(Registries.an).fieldOf("biomes").forGetter(Structure.c::a),
                  Codec.simpleMap(EnumCreatureType.i, StructureSpawnOverride.a, INamable.a(EnumCreatureType.values()))
                     .fieldOf("spawn_overrides")
                     .forGetter(Structure.c::b),
                  WorldGenStage.Decoration.l.fieldOf("step").forGetter(Structure.c::c),
                  TerrainAdjustment.e.optionalFieldOf("terrain_adaptation", TerrainAdjustment.a).forGetter(Structure.c::d)
               )
               .apply(var0, Structure.c::new)
      );

      public c(HolderSet<BiomeBase> var0, Map<EnumCreatureType, StructureSpawnOverride> var1, WorldGenStage.Decoration var2, TerrainAdjustment var3) {
         this.b = var0;
         this.c = var1;
         this.d = var2;
         this.e = var3;
      }

      public HolderSet<BiomeBase> a() {
         return this.b;
      }

      public Map<EnumCreatureType, StructureSpawnOverride> b() {
         return this.c;
      }

      public WorldGenStage.Decoration c() {
         return this.d;
      }

      public TerrainAdjustment d() {
         return this.e;
      }
   }
}
