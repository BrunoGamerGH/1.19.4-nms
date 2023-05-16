package net.minecraft.world.level.chunk;

import com.google.common.base.Stopwatch;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.SectionPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.WorldChunkManager;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.ConcentricRingsStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import org.slf4j.Logger;
import org.spigotmc.SpigotWorldConfig;

public class ChunkGeneratorStructureState {
   private static final Logger a = LogUtils.getLogger();
   private final RandomState b;
   private final WorldChunkManager c;
   private final long d;
   private final long e;
   private final Map<Structure, List<StructurePlacement>> f = new Object2ObjectOpenHashMap();
   private final Map<ConcentricRingsStructurePlacement, CompletableFuture<List<ChunkCoordIntPair>>> g = new Object2ObjectArrayMap();
   private boolean h;
   private final List<Holder<StructureSet>> i;

   public static ChunkGeneratorStructureState createForFlat(
      RandomState randomstate, long i, WorldChunkManager worldchunkmanager, Stream<Holder<StructureSet>> stream, SpigotWorldConfig conf
   ) {
      List<Holder<StructureSet>> list = stream.filter(holder -> a(holder.a(), worldchunkmanager)).toList();
      return new ChunkGeneratorStructureState(randomstate, worldchunkmanager, i, 0L, injectSpigot(list, conf));
   }

   public static ChunkGeneratorStructureState createForNormal(
      RandomState randomstate, long i, WorldChunkManager worldchunkmanager, HolderLookup<StructureSet> holderlookup, SpigotWorldConfig conf
   ) {
      List<Holder<StructureSet>> list = holderlookup.b().filter(holder_c -> a(holder_c.a(), worldchunkmanager)).collect(Collectors.toUnmodifiableList());
      return new ChunkGeneratorStructureState(randomstate, worldchunkmanager, i, i, injectSpigot(list, conf));
   }

   private static List<Holder<StructureSet>> injectSpigot(List<Holder<StructureSet>> list, SpigotWorldConfig conf) {
      return list.stream()
         .map(
            holder -> {
               StructureSet structureset = holder.a();
               StructurePlacement  instanceOfPatternExpressionValue = structureset.b();
               RandomSpreadStructurePlacement randomConfig;
               if ( instanceOfPatternExpressionValue instanceof RandomSpreadStructurePlacement
                  && (randomConfig = (RandomSpreadStructurePlacement) instanceOfPatternExpressionValue)
                     == (RandomSpreadStructurePlacement) instanceOfPatternExpressionValue) {
                  String name = holder.e().orElseThrow().a().a();
                  int seed = randomConfig.f;
                  switch(name.hashCode()) {
                     case -2088994748:
                        if (name.equals("jungle_temples")) {
                           seed = conf.jungleSeed;
                        }
                        break;
                     case -1972973283:
                        if (name.equals("shipwrecks")) {
                           seed = conf.shipwreckSeed;
                        }
                        break;
                     case -1542166003:
                        if (name.equals("desert_pyramids")) {
                           seed = conf.desertSeed;
                        }
                        break;
                     case -1460087925:
                        if (name.equals("ocean_monuments")) {
                           seed = conf.monumentSeed;
                        }
                        break;
                     case -1190456123:
                        if (name.equals("igloos")) {
                           seed = conf.iglooSeed;
                        }
                        break;
                     case -333943488:
                        if (name.equals("nether_fossils")) {
                           seed = conf.fossilSeed;
                        }
                        break;
                     case -88829139:
                        if (name.equals("end_cities")) {
                           seed = conf.endCitySeed;
                        }
                        break;
                     case 166086680:
                        if (name.equals("pillager_outposts")) {
                           seed = conf.outpostSeed;
                        }
                        break;
                     case 1181295690:
                        if (name.equals("ocean_ruins")) {
                           seed = conf.oceanSeed;
                        }
                        break;
                     case 1216879159:
                        if (name.equals("nether_complexes")) {
                           seed = conf.netherSeed;
                        }
                        break;
                     case 1337012299:
                        if (name.equals("swamp_huts")) {
                           seed = conf.swampSeed;
                        }
                        break;
                     case 1386475847:
                        if (name.equals("villages")) {
                           seed = conf.villageSeed;
                        }
                        break;
                     case 2041351823:
                        if (name.equals("ruined_portals")) {
                           seed = conf.portalSeed;
                        }
                        break;
                     case 2084793739:
                        if (name.equals("woodland_mansions")) {
                           seed = conf.mansionSeed;
                        }
                  }
      
                  structureset = new StructureSet(
                     structureset.a(),
                     new RandomSpreadStructurePlacement(
                        randomConfig.c, randomConfig.d, randomConfig.e, seed, randomConfig.g, randomConfig.a(), randomConfig.b(), randomConfig.c()
                     )
                  );
               }
      
               return Holder.a(structureset);
            }
         )
         .collect(Collectors.toUnmodifiableList());
   }

   private static boolean a(StructureSet structureset, WorldChunkManager worldchunkmanager) {
      Stream<Holder<BiomeBase>> stream = structureset.a().stream().flatMap(structureset_a -> {
         Structure structure = structureset_a.a().a();
         return structure.a().a();
      });
      Set set = worldchunkmanager.c();
      return stream.anyMatch(set::contains);
   }

   private ChunkGeneratorStructureState(RandomState randomstate, WorldChunkManager worldchunkmanager, long i, long j, List<Holder<StructureSet>> list) {
      this.b = randomstate;
      this.d = i;
      this.c = worldchunkmanager;
      this.e = j;
      this.i = list;
   }

   public List<Holder<StructureSet>> a() {
      return this.i;
   }

   private void e() {
      Set<Holder<BiomeBase>> set = this.c.c();
      this.a().forEach(holder -> {
         StructureSet structureset = holder.a();
         boolean flag = false;

         for(StructureSet.a structureset_a : structureset.a()) {
            Structure structure = structureset_a.a().a();
            Stream stream = structure.a().a();
            if (stream.anyMatch(set::contains)) {
               this.f.computeIfAbsent(structure, structure1 -> new ArrayList()).add(structureset.b());
               flag = true;
            }
         }

         if (flag) {
            StructurePlacement structureplacement = structureset.b();
            if (structureplacement instanceof ConcentricRingsStructurePlacement concentricringsstructureplacement) {
               this.g.put(concentricringsstructureplacement, this.a(holder, concentricringsstructureplacement));
            }
         }
      });
   }

   private CompletableFuture<List<ChunkCoordIntPair>> a(Holder<StructureSet> holder, ConcentricRingsStructurePlacement concentricringsstructureplacement) {
      if (concentricringsstructureplacement.c() == 0) {
         return CompletableFuture.completedFuture(List.of());
      } else {
         Stopwatch stopwatch = Stopwatch.createStarted(SystemUtils.b);
         int i = concentricringsstructureplacement.a();
         int j = concentricringsstructureplacement.c();
         List<CompletableFuture<ChunkCoordIntPair>> list = new ArrayList<>(j);
         int k = concentricringsstructureplacement.b();
         HolderSet<BiomeBase> holderset = concentricringsstructureplacement.d();
         RandomSource randomsource = RandomSource.a();
         randomsource.b(this.e);
         double d0 = randomsource.j() * Math.PI * 2.0;
         int l = 0;
         int i1 = 0;

         for(int j1 = 0; j1 < j; ++j1) {
            double d1 = (double)(4 * i + i * i1 * 6) + (randomsource.j() - 0.5) * (double)i * 2.5;
            int k1 = (int)Math.round(Math.cos(d0) * d1);
            int l1 = (int)Math.round(Math.sin(d0) * d1);
            RandomSource randomsource1 = randomsource.d();
            list.add(CompletableFuture.supplyAsync(() -> {
               WorldChunkManager worldchunkmanager = this.c;
               int i2 = SectionPosition.a(k1, 8);
               int j2 = SectionPosition.a(l1, 8);
               Pair<BlockPosition, Holder<BiomeBase>> pair = worldchunkmanager.a(i2, 0, j2, 112, holderset::a, randomsource1, this.b.b());
               if (pair != null) {
                  BlockPosition blockposition = (BlockPosition)pair.getFirst();
                  return new ChunkCoordIntPair(SectionPosition.a(blockposition.u()), SectionPosition.a(blockposition.w()));
               } else {
                  return new ChunkCoordIntPair(k1, l1);
               }
            }, SystemUtils.f()));
            d0 += (Math.PI * 2) / (double)k;
            if (++l == k) {
               ++i1;
               l = 0;
               k += 2 * k / (i1 + 1);
               k = Math.min(k, j - j1);
               d0 += randomsource.j() * Math.PI * 2.0;
            }
         }

         return SystemUtils.b(list).thenApply((Function<? super List<ChunkCoordIntPair>, ? extends List<ChunkCoordIntPair>>)(list1 -> {
            double d2 = (double)stopwatch.stop().elapsed(TimeUnit.MILLISECONDS) / 1000.0;
            a.debug("Calculation for {} took {}s", holder, d2);
            return list1;
         }));
      }
   }

   public void b() {
      if (!this.h) {
         this.e();
         this.h = true;
      }
   }

   @Nullable
   public List<ChunkCoordIntPair> a(ConcentricRingsStructurePlacement concentricringsstructureplacement) {
      this.b();
      CompletableFuture<List<ChunkCoordIntPair>> completablefuture = this.g.get(concentricringsstructureplacement);
      return completablefuture != null ? completablefuture.join() : null;
   }

   public List<StructurePlacement> a(Holder<Structure> holder) {
      this.b();
      return this.f.getOrDefault(holder.a(), List.of());
   }

   public RandomState c() {
      return this.b;
   }

   public boolean a(Holder<StructureSet> holder, int i, int j, int k) {
      StructurePlacement structureplacement = holder.a().b();

      for(int l = i - k; l <= i + k; ++l) {
         for(int i1 = j - k; i1 <= j + k; ++i1) {
            if (structureplacement.b(this, l, i1)) {
               return true;
            }
         }
      }

      return false;
   }

   public long d() {
      return this.d;
   }
}
