package net.minecraft.world.level.levelgen;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Suppliers;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.text.DecimalFormat;
import java.util.List;
import java.util.OptionalInt;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistry;
import net.minecraft.core.QuartPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.RegionLimitedWorldAccess;
import net.minecraft.util.MathHelper;
import net.minecraft.world.level.BlockColumn;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.SpawnerCreature;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeResolver;
import net.minecraft.world.level.biome.BiomeSettingsGeneration;
import net.minecraft.world.level.biome.WorldChunkManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkSection;
import net.minecraft.world.level.chunk.IChunkAccess;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.dimension.DimensionManager;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.WorldGenCarverWrapper;
import org.apache.commons.lang3.mutable.MutableObject;

public final class ChunkGeneratorAbstract extends ChunkGenerator {
   public static final Codec<ChunkGeneratorAbstract> c = RecordCodecBuilder.create(
      instance -> instance.group(
               WorldChunkManager.a.fieldOf("biome_source").forGetter(chunkgeneratorabstract -> chunkgeneratorabstract.b),
               GeneratorSettingBase.b.fieldOf("settings").forGetter(chunkgeneratorabstract -> chunkgeneratorabstract.e)
            )
            .apply(instance, instance.stable(ChunkGeneratorAbstract::new))
   );
   private static final IBlockData d = Blocks.a.o();
   public final Holder<GeneratorSettingBase> e;
   private final Supplier<Aquifer.a> f;

   public ChunkGeneratorAbstract(WorldChunkManager worldchunkmanager, Holder<GeneratorSettingBase> holder) {
      super(worldchunkmanager);
      this.e = holder;
      this.f = Suppliers.memoize(() -> a(holder.a()));
   }

   private static Aquifer.a a(GeneratorSettingBase generatorsettingbase) {
      Aquifer.b aquifer_b = new Aquifer.b(-54, Blocks.H.o());
      int i = generatorsettingbase.l();
      Aquifer.b aquifer_b1 = new Aquifer.b(i, generatorsettingbase.h());
      new Aquifer.b(DimensionManager.e * 2, Blocks.a.o());
      return (j, k, l) -> k < Math.min(-54, i) ? aquifer_b : aquifer_b1;
   }

   @Override
   public CompletableFuture<IChunkAccess> a(
      Executor executor, RandomState randomstate, Blender blender, StructureManager structuremanager, IChunkAccess ichunkaccess
   ) {
      return CompletableFuture.supplyAsync(SystemUtils.a("init_biomes", () -> {
         this.a(blender, randomstate, structuremanager, ichunkaccess);
         return ichunkaccess;
      }), SystemUtils.f());
   }

   private void a(Blender blender, RandomState randomstate, StructureManager structuremanager, IChunkAccess ichunkaccess) {
      NoiseChunk noisechunk = ichunkaccess.a(ichunkaccess1 -> this.a(ichunkaccess1, structuremanager, blender, randomstate));
      BiomeResolver biomeresolver = BelowZeroRetrogen.a(blender.a(this.b), ichunkaccess);
      ichunkaccess.a(biomeresolver, noisechunk.a(randomstate.a(), this.e.a().k()));
   }

   private NoiseChunk a(IChunkAccess ichunkaccess, StructureManager structuremanager, Blender blender, RandomState randomstate) {
      return NoiseChunk.a(ichunkaccess, randomstate, Beardifier.a(structuremanager, ichunkaccess.f()), this.e.a(), this.f.get(), blender);
   }

   @Override
   protected Codec<? extends ChunkGenerator> a() {
      return c;
   }

   public Holder<GeneratorSettingBase> g() {
      return this.e;
   }

   public boolean a(ResourceKey<GeneratorSettingBase> resourcekey) {
      return this.e.a(resourcekey);
   }

   @Override
   public int a(int i, int j, HeightMap.Type heightmap_type, LevelHeightAccessor levelheightaccessor, RandomState randomstate) {
      return this.a(levelheightaccessor, randomstate, i, j, null, heightmap_type.e()).orElse(levelheightaccessor.v_());
   }

   @Override
   public BlockColumn a(int i, int j, LevelHeightAccessor levelheightaccessor, RandomState randomstate) {
      MutableObject<BlockColumn> mutableobject = new MutableObject();
      this.a(levelheightaccessor, randomstate, i, j, mutableobject, null);
      return (BlockColumn)mutableobject.getValue();
   }

   @Override
   public void a(List<String> list, RandomState randomstate, BlockPosition blockposition) {
      DecimalFormat decimalformat = new DecimalFormat("0.000");
      NoiseRouter noiserouter = randomstate.a();
      DensityFunction.e densityfunction_e = new DensityFunction.e(blockposition.u(), blockposition.v(), blockposition.w());
      double d0 = noiserouter.j().a(densityfunction_e);
      String s = decimalformat.format(noiserouter.e().a(densityfunction_e));
      list.add(
         "NoiseRouter T: "
            + s
            + " V: "
            + decimalformat.format(noiserouter.f().a(densityfunction_e))
            + " C: "
            + decimalformat.format(noiserouter.g().a(densityfunction_e))
            + " E: "
            + decimalformat.format(noiserouter.h().a(densityfunction_e))
            + " D: "
            + decimalformat.format(noiserouter.i().a(densityfunction_e))
            + " W: "
            + decimalformat.format(d0)
            + " PV: "
            + decimalformat.format((double)NoiseRouterData.a((float)d0))
            + " AS: "
            + decimalformat.format(noiserouter.k().a(densityfunction_e))
            + " N: "
            + decimalformat.format(noiserouter.l().a(densityfunction_e))
      );
   }

   private OptionalInt a(
      LevelHeightAccessor levelheightaccessor,
      RandomState randomstate,
      int i,
      int j,
      @Nullable MutableObject<BlockColumn> mutableobject,
      @Nullable Predicate<IBlockData> predicate
   ) {
      NoiseSettings noisesettings = this.e.a().f().a(levelheightaccessor);
      int k = noisesettings.a();
      int l = noisesettings.c();
      int i1 = MathHelper.a(l, k);
      int j1 = MathHelper.a(noisesettings.d(), k);
      if (j1 <= 0) {
         return OptionalInt.empty();
      } else {
         IBlockData[] aiblockdata;
         if (mutableobject == null) {
            aiblockdata = null;
         } else {
            aiblockdata = new IBlockData[noisesettings.d()];
            mutableobject.setValue(new BlockColumn(l, aiblockdata));
         }

         int k1 = noisesettings.b();
         int l1 = Math.floorDiv(i, k1);
         int i2 = Math.floorDiv(j, k1);
         int j2 = Math.floorMod(i, k1);
         int k2 = Math.floorMod(j, k1);
         int l2 = l1 * k1;
         int i3 = i2 * k1;
         double d0 = (double)j2 / (double)k1;
         double d1 = (double)k2 / (double)k1;
         NoiseChunk noisechunk = new NoiseChunk(1, randomstate, l2, i3, noisesettings, DensityFunctions.b.a, this.e.a(), this.f.get(), Blender.a());
         noisechunk.f();
         noisechunk.b(0);

         for(int j3 = j1 - 1; j3 >= 0; --j3) {
            noisechunk.b(j3, 0);

            for(int k3 = k - 1; k3 >= 0; --k3) {
               int l3 = (i1 + j3) * k + k3;
               double d2 = (double)k3 / (double)k;
               noisechunk.a(l3, d2);
               noisechunk.b(i, d0);
               noisechunk.c(j, d1);
               IBlockData iblockdata = noisechunk.e();
               IBlockData iblockdata1 = iblockdata == null ? this.e.a().g() : iblockdata;
               if (aiblockdata != null) {
                  int i4 = j3 * k + k3;
                  aiblockdata[i4] = iblockdata1;
               }

               if (predicate != null && predicate.test(iblockdata1)) {
                  noisechunk.g();
                  return OptionalInt.of(l3 + 1);
               }
            }
         }

         noisechunk.g();
         return OptionalInt.empty();
      }
   }

   @Override
   public void a(RegionLimitedWorldAccess regionlimitedworldaccess, StructureManager structuremanager, RandomState randomstate, IChunkAccess ichunkaccess) {
      if (!SharedConstants.a(ichunkaccess.f())) {
         WorldGenerationContext worldgenerationcontext = new WorldGenerationContext(this, regionlimitedworldaccess);
         this.a(
            ichunkaccess,
            worldgenerationcontext,
            randomstate,
            structuremanager,
            regionlimitedworldaccess.s_(),
            regionlimitedworldaccess.u_().d(Registries.an),
            Blender.a(regionlimitedworldaccess)
         );
      }
   }

   @VisibleForTesting
   public void a(
      IChunkAccess ichunkaccess,
      WorldGenerationContext worldgenerationcontext,
      RandomState randomstate,
      StructureManager structuremanager,
      BiomeManager biomemanager,
      IRegistry<BiomeBase> iregistry,
      Blender blender
   ) {
      NoiseChunk noisechunk = ichunkaccess.a(ichunkaccess1 -> this.a(ichunkaccess1, structuremanager, blender, randomstate));
      GeneratorSettingBase generatorsettingbase = this.e.a();
      randomstate.c()
         .a(randomstate, biomemanager, iregistry, generatorsettingbase.n(), worldgenerationcontext, ichunkaccess, noisechunk, generatorsettingbase.j());
   }

   @Override
   public void a(
      RegionLimitedWorldAccess regionlimitedworldaccess,
      long i,
      RandomState randomstate,
      BiomeManager biomemanager,
      StructureManager structuremanager,
      IChunkAccess ichunkaccess,
      WorldGenStage.Features worldgenstage_features
   ) {
      BiomeManager biomemanager1 = biomemanager.a((jx, kx, lx) -> this.b.getNoiseBiome(jx, kx, lx, randomstate.b()));
      SeededRandom seededrandom = new SeededRandom(new LegacyRandomSource(RandomSupport.a()));
      boolean flag = true;
      ChunkCoordIntPair chunkcoordintpair = ichunkaccess.f();
      NoiseChunk noisechunk = ichunkaccess.a(ichunkaccess1x -> this.a(ichunkaccess1x, structuremanager, Blender.a(regionlimitedworldaccess), randomstate));
      Aquifer aquifer = noisechunk.i();
      CarvingContext carvingcontext = new CarvingContext(this, regionlimitedworldaccess.u_(), ichunkaccess.z(), noisechunk, randomstate, this.e.a().j());
      CarvingMask carvingmask = ((ProtoChunk)ichunkaccess).b(worldgenstage_features);

      for(int j = -8; j <= 8; ++j) {
         for(int k = -8; k <= 8; ++k) {
            ChunkCoordIntPair chunkcoordintpair1 = new ChunkCoordIntPair(chunkcoordintpair.e + j, chunkcoordintpair.f + k);
            IChunkAccess ichunkaccess1 = regionlimitedworldaccess.a(chunkcoordintpair1.e, chunkcoordintpair1.f);
            BiomeSettingsGeneration biomesettingsgeneration = ichunkaccess1.a(
               () -> this.a(this.b.getNoiseBiome(QuartPos.a(chunkcoordintpair1.d()), 0, QuartPos.a(chunkcoordintpair1.e()), randomstate.b()))
            );
            Iterable<Holder<WorldGenCarverWrapper<?>>> iterable = biomesettingsgeneration.a(worldgenstage_features);
            int l = 0;

            for(Holder<WorldGenCarverWrapper<?>> holder : iterable) {
               WorldGenCarverWrapper<?> worldgencarverwrapper = holder.a();
               seededrandom.c(i + (long)l, chunkcoordintpair1.e, chunkcoordintpair1.f);
               if (worldgencarverwrapper.a(seededrandom)) {
                  worldgencarverwrapper.a(carvingcontext, ichunkaccess, biomemanager1::a, seededrandom, aquifer, chunkcoordintpair1, carvingmask);
               }

               ++l;
            }
         }
      }
   }

   @Override
   public CompletableFuture<IChunkAccess> a(
      Executor executor, Blender blender, RandomState randomstate, StructureManager structuremanager, IChunkAccess ichunkaccess
   ) {
      NoiseSettings noisesettings = this.e.a().f().a(ichunkaccess.z());
      int i = noisesettings.c();
      int j = MathHelper.a(i, noisesettings.a());
      int k = MathHelper.a(noisesettings.d(), noisesettings.a());
      if (k <= 0) {
         return CompletableFuture.completedFuture(ichunkaccess);
      } else {
         int l = ichunkaccess.e(k * noisesettings.a() - 1 + i);
         int i1 = ichunkaccess.e(i);
         Set<ChunkSection> set = Sets.newHashSet();

         for(int j1 = l; j1 >= i1; --j1) {
            ChunkSection chunksection = ichunkaccess.b(j1);
            chunksection.a();
            set.add(chunksection);
         }

         return CompletableFuture.supplyAsync(
               SystemUtils.a("wgen_fill_noise", () -> this.a(blender, structuremanager, randomstate, ichunkaccess, j, k)), SystemUtils.f()
            )
            .whenCompleteAsync((ichunkaccess1, throwable) -> {
               for(ChunkSection chunksection1 : set) {
                  chunksection1.b();
               }
            }, executor);
      }
   }

   private IChunkAccess a(Blender blender, StructureManager structuremanager, RandomState randomstate, IChunkAccess ichunkaccess, int i, int j) {
      NoiseChunk noisechunk = ichunkaccess.a(ichunkaccess1 -> this.a(ichunkaccess1, structuremanager, blender, randomstate));
      HeightMap heightmap = ichunkaccess.a(HeightMap.Type.c);
      HeightMap heightmap1 = ichunkaccess.a(HeightMap.Type.a);
      ChunkCoordIntPair chunkcoordintpair = ichunkaccess.f();
      int k = chunkcoordintpair.d();
      int l = chunkcoordintpair.e();
      Aquifer aquifer = noisechunk.i();
      noisechunk.f();
      BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();
      int i1 = noisechunk.j();
      int j1 = noisechunk.k();
      int k1 = 16 / i1;
      int l1 = 16 / i1;

      for(int i2 = 0; i2 < k1; ++i2) {
         noisechunk.b(i2);

         for(int j2 = 0; j2 < l1; ++j2) {
            ChunkSection chunksection = ichunkaccess.b(ichunkaccess.aj() - 1);

            for(int k2 = j - 1; k2 >= 0; --k2) {
               noisechunk.b(k2, j2);

               for(int l2 = j1 - 1; l2 >= 0; --l2) {
                  int i3 = (i + k2) * j1 + l2;
                  int j3 = i3 & 15;
                  int k3 = ichunkaccess.e(i3);
                  if (ichunkaccess.e(chunksection.g()) != k3) {
                     chunksection = ichunkaccess.b(k3);
                  }

                  double d0 = (double)l2 / (double)j1;
                  noisechunk.a(i3, d0);

                  for(int l3 = 0; l3 < i1; ++l3) {
                     int i4 = k + i2 * i1 + l3;
                     int j4 = i4 & 15;
                     double d1 = (double)l3 / (double)i1;
                     noisechunk.b(i4, d1);

                     for(int k4 = 0; k4 < i1; ++k4) {
                        int l4 = l + j2 * i1 + k4;
                        int i5 = l4 & 15;
                        double d2 = (double)k4 / (double)i1;
                        noisechunk.c(l4, d2);
                        IBlockData iblockdata = noisechunk.e();
                        if (iblockdata == null) {
                           iblockdata = this.e.a().g();
                        }

                        iblockdata = this.a(noisechunk, i4, i3, l4, iblockdata);
                        if (iblockdata != d && !SharedConstants.a(ichunkaccess.f())) {
                           if (iblockdata.g() != 0 && ichunkaccess instanceof ProtoChunk) {
                              blockposition_mutableblockposition.d(i4, i3, l4);
                              ((ProtoChunk)ichunkaccess).j(blockposition_mutableblockposition);
                           }

                           chunksection.a(j4, j3, i5, iblockdata, false);
                           heightmap.a(j4, i3, i5, iblockdata);
                           heightmap1.a(j4, i3, i5, iblockdata);
                           if (aquifer.a() && !iblockdata.r().c()) {
                              blockposition_mutableblockposition.d(i4, i3, l4);
                              ichunkaccess.e(blockposition_mutableblockposition);
                           }
                        }
                     }
                  }
               }
            }
         }

         noisechunk.h();
      }

      noisechunk.g();
      return ichunkaccess;
   }

   private IBlockData a(NoiseChunk noisechunk, int i, int j, int k, IBlockData iblockdata) {
      return iblockdata;
   }

   @Override
   public int d() {
      return this.e.a().f().d();
   }

   @Override
   public int e() {
      return this.e.a().l();
   }

   @Override
   public int f() {
      return this.e.a().f().c();
   }

   @Override
   public void a(RegionLimitedWorldAccess regionlimitedworldaccess) {
      if (!this.e.a().a()) {
         ChunkCoordIntPair chunkcoordintpair = regionlimitedworldaccess.a();
         Holder<BiomeBase> holder = regionlimitedworldaccess.v(chunkcoordintpair.l().h(regionlimitedworldaccess.ai() - 1));
         SeededRandom seededrandom = new SeededRandom(new LegacyRandomSource(RandomSupport.a()));
         seededrandom.a(regionlimitedworldaccess.A(), chunkcoordintpair.d(), chunkcoordintpair.e());
         SpawnerCreature.a(regionlimitedworldaccess, holder, chunkcoordintpair, seededrandom);
      }
   }
}
