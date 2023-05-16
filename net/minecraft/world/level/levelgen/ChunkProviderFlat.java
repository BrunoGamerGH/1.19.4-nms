package net.minecraft.world.level.levelgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.server.level.RegionLimitedWorldAccess;
import net.minecraft.world.level.BlockColumn;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.WorldChunkManager;
import net.minecraft.world.level.biome.WorldChunkManagerHell;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.chunk.IChunkAccess;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.flat.GeneratorSettingsFlat;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import org.spigotmc.SpigotWorldConfig;

public class ChunkProviderFlat extends ChunkGenerator {
   public static final Codec<ChunkProviderFlat> c = RecordCodecBuilder.create(
      instance -> instance.group(GeneratorSettingsFlat.a.fieldOf("settings").forGetter(ChunkProviderFlat::g))
            .apply(instance, instance.stable(ChunkProviderFlat::new))
   );
   private final GeneratorSettingsFlat d;

   public ChunkProviderFlat(GeneratorSettingsFlat generatorsettingsflat) {
      this(generatorsettingsflat, new WorldChunkManagerHell(generatorsettingsflat.d()));
   }

   public ChunkProviderFlat(GeneratorSettingsFlat generatorsettingsflat, WorldChunkManager worldchunkmanager) {
      super(worldchunkmanager, SystemUtils.b(generatorsettingsflat::a));
      this.d = generatorsettingsflat;
   }

   @Override
   public ChunkGeneratorStructureState createState(HolderLookup<StructureSet> holderlookup, RandomState randomstate, long i, SpigotWorldConfig conf) {
      Stream<Holder<StructureSet>> stream = this.d
         .c()
         .map(HolderSet::a)
         .orElseGet(() -> holderlookup.b().map((Function<? super Holder.c<StructureSet>, ? extends Holder.c<StructureSet>>)(holder_c -> holder_c)));
      return ChunkGeneratorStructureState.createForFlat(randomstate, i, this.b, stream, conf);
   }

   @Override
   protected Codec<? extends ChunkGenerator> a() {
      return c;
   }

   public GeneratorSettingsFlat g() {
      return this.d;
   }

   @Override
   public void a(RegionLimitedWorldAccess regionlimitedworldaccess, StructureManager structuremanager, RandomState randomstate, IChunkAccess ichunkaccess) {
   }

   @Override
   public int a(LevelHeightAccessor levelheightaccessor) {
      return levelheightaccessor.v_() + Math.min(levelheightaccessor.w_(), this.d.f().size());
   }

   @Override
   public CompletableFuture<IChunkAccess> a(
      Executor executor, Blender blender, RandomState randomstate, StructureManager structuremanager, IChunkAccess ichunkaccess
   ) {
      List<IBlockData> list = this.d.f();
      BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();
      HeightMap heightmap = ichunkaccess.a(HeightMap.Type.c);
      HeightMap heightmap1 = ichunkaccess.a(HeightMap.Type.a);

      for(int i = 0; i < Math.min(ichunkaccess.w_(), list.size()); ++i) {
         IBlockData iblockdata = list.get(i);
         if (iblockdata != null) {
            int j = ichunkaccess.v_() + i;

            for(int k = 0; k < 16; ++k) {
               for(int l = 0; l < 16; ++l) {
                  ichunkaccess.a(blockposition_mutableblockposition.d(k, j, l), iblockdata, false);
                  heightmap.a(k, j, l, iblockdata);
                  heightmap1.a(k, j, l, iblockdata);
               }
            }
         }
      }

      return CompletableFuture.completedFuture(ichunkaccess);
   }

   @Override
   public int a(int i, int j, HeightMap.Type heightmap_type, LevelHeightAccessor levelheightaccessor, RandomState randomstate) {
      List<IBlockData> list = this.d.f();

      for(int k = Math.min(list.size(), levelheightaccessor.ai()) - 1; k >= 0; --k) {
         IBlockData iblockdata = list.get(k);
         if (iblockdata != null && heightmap_type.e().test(iblockdata)) {
            return levelheightaccessor.v_() + k + 1;
         }
      }

      return levelheightaccessor.v_();
   }

   @Override
   public BlockColumn a(int i, int j, LevelHeightAccessor levelheightaccessor, RandomState randomstate) {
      return new BlockColumn(
         levelheightaccessor.v_(),
         this.d
            .f()
            .stream()
            .limit((long)levelheightaccessor.w_())
            .map(iblockdata -> iblockdata == null ? Blocks.a.o() : iblockdata)
            .toArray(k -> new IBlockData[k])
      );
   }

   @Override
   public void a(List<String> list, RandomState randomstate, BlockPosition blockposition) {
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
   }

   @Override
   public void a(RegionLimitedWorldAccess regionlimitedworldaccess) {
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
      return -63;
   }
}
