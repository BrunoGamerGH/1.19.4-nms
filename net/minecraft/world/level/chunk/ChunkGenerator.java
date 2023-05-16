package net.minecraft.world.level.chunk;

import com.google.common.base.Suppliers;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportSystemDetails;
import net.minecraft.ReportedException;
import net.minecraft.SharedConstants;
import net.minecraft.SystemUtils;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.IRegistry;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.SectionPosition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.protocol.game.PacketDebug;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.RegionLimitedWorldAccess;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EnumCreatureType;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSettingsGeneration;
import net.minecraft.world.level.biome.BiomeSettingsMobs;
import net.minecraft.world.level.biome.FeatureSorter;
import net.minecraft.world.level.biome.WorldChunkManager;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.RandomSupport;
import net.minecraft.world.level.levelgen.SeededRandom;
import net.minecraft.world.level.levelgen.WorldGenStage;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.levelgen.structure.StructureCheckResult;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.placement.ConcentricRingsStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_19_R3.generator.CraftLimitedRegion;
import org.bukkit.craftbukkit.v1_19_R3.generator.strucutre.CraftStructure;
import org.bukkit.craftbukkit.v1_19_R3.util.RandomSourceWrapper;
import org.bukkit.event.world.AsyncStructureSpawnEvent;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.util.BoundingBox;
import org.spigotmc.SpigotWorldConfig;

public abstract class ChunkGenerator {
   public static final Codec<ChunkGenerator> a = BuiltInRegistries.ac.q().dispatchStable(ChunkGenerator::a, Function.identity());
   protected final WorldChunkManager b;
   private final Supplier<List<FeatureSorter.b>> c;
   public final Function<Holder<BiomeBase>, BiomeSettingsGeneration> d;

   public ChunkGenerator(WorldChunkManager worldchunkmanager) {
      this(worldchunkmanager, holder -> holder.a().d());
   }

   public ChunkGenerator(WorldChunkManager worldchunkmanager, Function<Holder<BiomeBase>, BiomeSettingsGeneration> function) {
      this.b = worldchunkmanager;
      this.d = function;
      this.c = Suppliers.memoize(() -> FeatureSorter.a(List.copyOf(worldchunkmanager.c()), holder -> function.apply(holder).b(), true));
   }

   protected abstract Codec<? extends ChunkGenerator> a();

   public ChunkGeneratorStructureState createState(HolderLookup<StructureSet> holderlookup, RandomState randomstate, long i, SpigotWorldConfig conf) {
      return ChunkGeneratorStructureState.createForNormal(randomstate, i, this.b, holderlookup, conf);
   }

   public Optional<ResourceKey<Codec<? extends ChunkGenerator>>> b() {
      return BuiltInRegistries.ac.c(this.a());
   }

   public CompletableFuture<IChunkAccess> a(
      Executor executor, RandomState randomstate, Blender blender, StructureManager structuremanager, IChunkAccess ichunkaccess
   ) {
      return CompletableFuture.supplyAsync(SystemUtils.a("init_biomes", () -> {
         ichunkaccess.a(this.b, randomstate.b());
         return ichunkaccess;
      }), SystemUtils.f());
   }

   public abstract void a(
      RegionLimitedWorldAccess var1, long var2, RandomState var4, BiomeManager var5, StructureManager var6, IChunkAccess var7, WorldGenStage.Features var8
   );

   @Nullable
   public Pair<BlockPosition, Holder<Structure>> a(WorldServer worldserver, HolderSet<Structure> holderset, BlockPosition blockposition, int i, boolean flag) {
      ChunkGeneratorStructureState chunkgeneratorstructurestate = worldserver.k().h();
      Map<StructurePlacement, Set<Holder<Structure>>> map = new Object2ObjectArrayMap();

      for(Holder<Structure> holder : holderset) {
         for(StructurePlacement structureplacement : chunkgeneratorstructurestate.a(holder)) {
            map.computeIfAbsent(structureplacement, structureplacement1 -> new ObjectArraySet()).add(holder);
         }
      }

      if (map.isEmpty()) {
         return null;
      } else {
         Pair<BlockPosition, Holder<Structure>> pair = null;
         double d0 = Double.MAX_VALUE;
         StructureManager structuremanager = worldserver.a();
         List<Entry<StructurePlacement, Set<Holder<Structure>>>> list = new ArrayList<>(map.size());

         for(Entry<StructurePlacement, Set<Holder<Structure>>> entry : map.entrySet()) {
            StructurePlacement structureplacement1 = entry.getKey();
            if (structureplacement1 instanceof ConcentricRingsStructurePlacement concentricringsstructureplacement) {
               Pair<BlockPosition, Holder<Structure>> pair1 = this.a(
                  entry.getValue(), worldserver, structuremanager, blockposition, flag, concentricringsstructureplacement
               );
               if (pair1 != null) {
                  BlockPosition blockposition1 = (BlockPosition)pair1.getFirst();
                  double d1 = blockposition.j(blockposition1);
                  if (d1 < d0) {
                     d0 = d1;
                     pair = pair1;
                  }
               }
            } else if (structureplacement1 instanceof RandomSpreadStructurePlacement) {
               list.add(entry);
            }
         }

         if (!list.isEmpty()) {
            int j = SectionPosition.a(blockposition.u());
            int k = SectionPosition.a(blockposition.w());

            for(int l = 0; l <= i; ++l) {
               boolean flag1 = false;

               for(Entry<StructurePlacement, Set<Holder<Structure>>> entry1 : list) {
                  RandomSpreadStructurePlacement randomspreadstructureplacement = (RandomSpreadStructurePlacement)entry1.getKey();
                  Pair<BlockPosition, Holder<Structure>> pair2 = a(
                     entry1.getValue(), worldserver, structuremanager, j, k, l, flag, chunkgeneratorstructurestate.d(), randomspreadstructureplacement
                  );
                  if (pair2 != null) {
                     flag1 = true;
                     double d2 = blockposition.j((BaseBlockPosition)pair2.getFirst());
                     if (d2 < d0) {
                        d0 = d2;
                        pair = pair2;
                     }
                  }
               }

               if (flag1) {
                  return pair;
               }
            }
         }

         return pair;
      }
   }

   @Nullable
   private Pair<BlockPosition, Holder<Structure>> a(
      Set<Holder<Structure>> set,
      WorldServer worldserver,
      StructureManager structuremanager,
      BlockPosition blockposition,
      boolean flag,
      ConcentricRingsStructurePlacement concentricringsstructureplacement
   ) {
      List<ChunkCoordIntPair> list = worldserver.k().h().a(concentricringsstructureplacement);
      if (list == null) {
         throw new IllegalStateException("Somehow tried to find structures for a placement that doesn't exist");
      } else {
         Pair<BlockPosition, Holder<Structure>> pair = null;
         double d0 = Double.MAX_VALUE;
         BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();

         for(ChunkCoordIntPair chunkcoordintpair : list) {
            blockposition_mutableblockposition.d(SectionPosition.a(chunkcoordintpair.e, 8), 32, SectionPosition.a(chunkcoordintpair.f, 8));
            double d1 = blockposition_mutableblockposition.j(blockposition);
            boolean flag1 = pair == null || d1 < d0;
            if (flag1) {
               Pair<BlockPosition, Holder<Structure>> pair1 = a(set, worldserver, structuremanager, flag, concentricringsstructureplacement, chunkcoordintpair);
               if (pair1 != null) {
                  pair = pair1;
                  d0 = d1;
               }
            }
         }

         return pair;
      }
   }

   @Nullable
   private static Pair<BlockPosition, Holder<Structure>> a(
      Set<Holder<Structure>> set,
      IWorldReader iworldreader,
      StructureManager structuremanager,
      int i,
      int j,
      int k,
      boolean flag,
      long l,
      RandomSpreadStructurePlacement randomspreadstructureplacement
   ) {
      int i1 = randomspreadstructureplacement.a();

      for(int j1 = -k; j1 <= k; ++j1) {
         boolean flag1 = j1 == -k || j1 == k;

         for(int k1 = -k; k1 <= k; ++k1) {
            boolean flag2 = k1 == -k || k1 == k;
            if (flag1 || flag2) {
               int l1 = i + i1 * j1;
               int i2 = j + i1 * k1;
               ChunkCoordIntPair chunkcoordintpair = randomspreadstructureplacement.a(l, l1, i2);
               Pair<BlockPosition, Holder<Structure>> pair = a(set, iworldreader, structuremanager, flag, randomspreadstructureplacement, chunkcoordintpair);
               if (pair != null) {
                  return pair;
               }
            }
         }
      }

      return null;
   }

   @Nullable
   private static Pair<BlockPosition, Holder<Structure>> a(
      Set<Holder<Structure>> set,
      IWorldReader iworldreader,
      StructureManager structuremanager,
      boolean flag,
      StructurePlacement structureplacement,
      ChunkCoordIntPair chunkcoordintpair
   ) {
      for(Holder holder : set) {
         StructureCheckResult structurecheckresult = structuremanager.a(chunkcoordintpair, (Structure)holder.a(), flag);
         if (structurecheckresult != StructureCheckResult.b) {
            if (!flag && structurecheckresult == StructureCheckResult.a) {
               return Pair.of(structureplacement.a(chunkcoordintpair), holder);
            }

            IChunkAccess ichunkaccess = iworldreader.a(chunkcoordintpair.e, chunkcoordintpair.f, ChunkStatus.d);
            StructureStart structurestart = structuremanager.a(SectionPosition.a(ichunkaccess), (Structure)holder.a(), ichunkaccess);
            if (structurestart != null && structurestart.b() && (!flag || a(structuremanager, structurestart))) {
               return Pair.of(structureplacement.a(structurestart.c()), holder);
            }
         }
      }

      return null;
   }

   private static boolean a(StructureManager structuremanager, StructureStart structurestart) {
      if (structurestart.d()) {
         structuremanager.a(structurestart);
         return true;
      } else {
         return false;
      }
   }

   public void addVanillaDecorations(GeneratorAccessSeed generatoraccessseed, IChunkAccess ichunkaccess, StructureManager structuremanager) {
      ChunkCoordIntPair chunkcoordintpair = ichunkaccess.f();
      if (!SharedConstants.a(chunkcoordintpair)) {
         SectionPosition sectionposition = SectionPosition.a(chunkcoordintpair, generatoraccessseed.ak());
         BlockPosition blockposition = sectionposition.j();
         IRegistry<Structure> iregistry = generatoraccessseed.u_().d(Registries.ax);
         Map<Integer, List<Structure>> map = iregistry.s().collect(Collectors.groupingBy(structure -> structure.c().ordinal()));
         List<FeatureSorter.b> list = this.c.get();
         SeededRandom seededrandom = new SeededRandom(new XoroshiroRandomSource(RandomSupport.a()));
         long i = seededrandom.a(generatoraccessseed.A(), blockposition.u(), blockposition.w());
         Set<Holder<BiomeBase>> set = new ObjectArraySet();
         ChunkCoordIntPair.a(sectionposition.r(), 1).forEach(chunkcoordintpair1 -> {
            IChunkAccess ichunkaccess1 = generatoraccessseed.a(chunkcoordintpair1.e, chunkcoordintpair1.f);

            for(ChunkSection chunksection : ichunkaccess1.d()) {
               PalettedContainerRO<Holder<BiomeBase>> palettedcontainerro = chunksection.j();
               palettedcontainerro.a(set::add);
            }
         });
         set.retainAll(this.b.c());
         int j = list.size();

         try {
            IRegistry<PlacedFeature> iregistry1 = generatoraccessseed.u_().d(Registries.aw);
            int k = Math.max(WorldGenStage.Decoration.values().length, j);

            for(int l = 0; l < k; ++l) {
               int i1 = 0;
               if (structuremanager.a()) {
                  for(Structure structure : map.getOrDefault(l, Collections.emptyList())) {
                     seededrandom.b(i, i1, l);
                     Supplier<String> supplier = () -> {
                        Optional optional = iregistry.c(structure).map(Object::toString);
                        return optional.orElseGet(structure::toString);
                     };

                     try {
                        generatoraccessseed.a(supplier);
                        structuremanager.a(sectionposition, structure)
                           .forEach(
                              structurestart -> structurestart.a(generatoraccessseed, structuremanager, this, seededrandom, a(ichunkaccess), chunkcoordintpair)
                           );
                     } catch (Exception var32) {
                        CrashReport crashreport = CrashReport.a(var32, "Feature placement");
                        CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Feature");
                        crashreportsystemdetails.a("Description", supplier::get);
                        throw new ReportedException(crashreport);
                     }

                     ++i1;
                  }
               }

               if (l < j) {
                  IntArraySet intarrayset = new IntArraySet();

                  for(Holder<BiomeBase> holder : set) {
                     List<HolderSet<PlacedFeature>> list2 = this.d.apply(holder).b();
                     if (l < list2.size()) {
                        HolderSet<PlacedFeature> holderset = list2.get(l);
                        FeatureSorter.b featuresorter_b = list.get(l);
                        holderset.a().map(Holder::a).forEach(placedfeaturex -> intarrayset.add(featuresorter_b.b().applyAsInt(placedfeaturex)));
                     }
                  }

                  int j1 = intarrayset.size();
                  int[] aint = intarrayset.toIntArray();
                  Arrays.sort(aint);
                  FeatureSorter.b featuresorter_b1 = list.get(l);

                  for(int k1 = 0; k1 < j1; ++k1) {
                     int l1 = aint[k1];
                     PlacedFeature placedfeature = featuresorter_b1.a().get(l1);
                     Supplier<String> supplier1 = () -> {
                        Optional optional = iregistry1.c(placedfeature).map(Object::toString);
                        return optional.orElseGet(placedfeature::toString);
                     };
                     seededrandom.b(i, l1, l);

                     try {
                        generatoraccessseed.a(supplier1);
                        placedfeature.b(generatoraccessseed, this, seededrandom, blockposition);
                     } catch (Exception var31) {
                        CrashReport crashreport1 = CrashReport.a(var31, "Feature placement");
                        CrashReportSystemDetails crashreportsystemdetails = crashreport1.a("Feature");
                        crashreportsystemdetails.a("Description", supplier1::get);
                        throw new ReportedException(crashreport1);
                     }
                  }
               }
            }

            generatoraccessseed.a(null);
         } catch (Exception var33) {
            CrashReport crashreport2 = CrashReport.a(var33, "Biome decoration");
            crashreport2.a("Generation").a("CenterX", chunkcoordintpair.e).a("CenterZ", chunkcoordintpair.f).a("Seed", i);
            throw new ReportedException(crashreport2);
         }
      }
   }

   public void a(GeneratorAccessSeed generatoraccessseed, IChunkAccess ichunkaccess, StructureManager structuremanager) {
      this.applyBiomeDecoration(generatoraccessseed, ichunkaccess, structuremanager, true);
   }

   public void applyBiomeDecoration(GeneratorAccessSeed generatoraccessseed, IChunkAccess ichunkaccess, StructureManager structuremanager, boolean vanilla) {
      if (vanilla) {
         this.addVanillaDecorations(generatoraccessseed, ichunkaccess, structuremanager);
      }

      World world = generatoraccessseed.getMinecraftWorld().getWorld();
      if (!world.getPopulators().isEmpty()) {
         CraftLimitedRegion limitedRegion = new CraftLimitedRegion(generatoraccessseed, ichunkaccess.f());
         int x = ichunkaccess.f().e;
         int z = ichunkaccess.f().f;

         for(BlockPopulator populator : world.getPopulators()) {
            SeededRandom seededrandom = new SeededRandom(new LegacyRandomSource(generatoraccessseed.A()));
            seededrandom.a(generatoraccessseed.A(), x, z);
            populator.populate(world, new RandomSourceWrapper.RandomWrapper(seededrandom), x, z, limitedRegion);
         }

         limitedRegion.saveEntities();
         limitedRegion.breakLink();
      }
   }

   private static StructureBoundingBox a(IChunkAccess ichunkaccess) {
      ChunkCoordIntPair chunkcoordintpair = ichunkaccess.f();
      int i = chunkcoordintpair.d();
      int j = chunkcoordintpair.e();
      LevelHeightAccessor levelheightaccessor = ichunkaccess.z();
      int k = levelheightaccessor.v_() + 1;
      int l = levelheightaccessor.ai() - 1;
      return new StructureBoundingBox(i, k, j, i + 15, l, j + 15);
   }

   public abstract void a(RegionLimitedWorldAccess var1, StructureManager var2, RandomState var3, IChunkAccess var4);

   public abstract void a(RegionLimitedWorldAccess var1);

   public int a(LevelHeightAccessor levelheightaccessor) {
      return 64;
   }

   public WorldChunkManager c() {
      return this.b;
   }

   public abstract int d();

   public WeightedRandomList<BiomeSettingsMobs.c> a(
      Holder<BiomeBase> holder, StructureManager structuremanager, EnumCreatureType enumcreaturetype, BlockPosition blockposition
   ) {
      Map<Structure, LongSet> map = structuremanager.b(blockposition);

      for(Entry<Structure, LongSet> entry : map.entrySet()) {
         Structure structure = entry.getKey();
         StructureSpawnOverride structurespawnoverride = structure.b().get(enumcreaturetype);
         if (structurespawnoverride != null) {
            MutableBoolean mutableboolean = new MutableBoolean(false);
            Predicate<StructureStart> predicate = structurespawnoverride.a() == StructureSpawnOverride.a.a
               ? structurestart -> structuremanager.a(blockposition, structurestart)
               : structurestart -> structurestart.a().b(blockposition);
            structuremanager.a(structure, (LongSet)entry.getValue(), structurestart -> {
               if (mutableboolean.isFalse() && predicate.test(structurestart)) {
                  mutableboolean.setTrue();
               }
            });
            if (mutableboolean.isTrue()) {
               return structurespawnoverride.b();
            }
         }
      }

      return holder.a().b().a(enumcreaturetype);
   }

   public void a(
      IRegistryCustom iregistrycustom,
      ChunkGeneratorStructureState chunkgeneratorstructurestate,
      StructureManager structuremanager,
      IChunkAccess ichunkaccess,
      StructureTemplateManager structuretemplatemanager
   ) {
      ChunkCoordIntPair chunkcoordintpair = ichunkaccess.f();
      SectionPosition sectionposition = SectionPosition.a(ichunkaccess);
      RandomState randomstate = chunkgeneratorstructurestate.c();
      chunkgeneratorstructurestate.a()
         .forEach(
            holder -> {
               StructurePlacement structureplacement = holder.a().b();
               List<StructureSet.a> list = holder.a().a();
      
               for(StructureSet.a structureset_a : list) {
                  StructureStart structurestart = structuremanager.a(sectionposition, structureset_a.a().a(), ichunkaccess);
                  if (structurestart != null && structurestart.b()) {
                     return;
                  }
               }
      
               if (structureplacement.b(chunkgeneratorstructurestate, chunkcoordintpair.e, chunkcoordintpair.f)) {
                  if (list.size() == 1) {
                     this.a(
                        list.get(0),
                        structuremanager,
                        iregistrycustom,
                        randomstate,
                        structuretemplatemanager,
                        chunkgeneratorstructurestate.d(),
                        ichunkaccess,
                        chunkcoordintpair,
                        sectionposition
                     );
                  } else {
                     ArrayList<StructureSet.a> arraylist = new ArrayList<>(list.size());
                     arraylist.addAll(list);
                     SeededRandom seededrandom = new SeededRandom(new LegacyRandomSource(0L));
                     seededrandom.c(chunkgeneratorstructurestate.d(), chunkcoordintpair.e, chunkcoordintpair.f);
                     int i = 0;
      
                     for(StructureSet.a structureset_a1 : arraylist) {
                        i += structureset_a1.b();
                     }
      
                     while(!arraylist.isEmpty()) {
                        int j = seededrandom.a(i);
                        int k = 0;
      
                        for(StructureSet.a structureset_a2 : arraylist) {
                           j -= structureset_a2.b();
                           if (j < 0) {
                              break;
                           }
      
                           ++k;
                        }
      
                        StructureSet.a structureset_a3 = arraylist.get(k);
                        if (this.a(
                           structureset_a3,
                           structuremanager,
                           iregistrycustom,
                           randomstate,
                           structuretemplatemanager,
                           chunkgeneratorstructurestate.d(),
                           ichunkaccess,
                           chunkcoordintpair,
                           sectionposition
                        )) {
                           return;
                        }
      
                        arraylist.remove(k);
                        i -= structureset_a3.b();
                     }
                  }
               }
            }
         );
   }

   private boolean a(
      StructureSet.a structureset_a,
      StructureManager structuremanager,
      IRegistryCustom iregistrycustom,
      RandomState randomstate,
      StructureTemplateManager structuretemplatemanager,
      long i,
      IChunkAccess ichunkaccess,
      ChunkCoordIntPair chunkcoordintpair,
      SectionPosition sectionposition
   ) {
      Structure structure = structureset_a.a().a();
      int j = a(structuremanager, ichunkaccess, sectionposition, structure);
      HolderSet<BiomeBase> holderset = structure.a();
      Predicate<Holder<BiomeBase>> predicate = holderset::a;
      StructureStart structurestart = structure.a(
         iregistrycustom, this, this.b, randomstate, structuretemplatemanager, i, chunkcoordintpair, j, ichunkaccess, predicate
      );
      if (structurestart.b()) {
         StructureBoundingBox box = structurestart.a();
         AsyncStructureSpawnEvent event = new AsyncStructureSpawnEvent(
            structuremanager.a.getMinecraftWorld().getWorld(),
            CraftStructure.minecraftToBukkit(structure, iregistrycustom),
            new BoundingBox((double)box.g(), (double)box.h(), (double)box.i(), (double)box.j(), (double)box.k(), (double)box.l()),
            chunkcoordintpair.e,
            chunkcoordintpair.f
         );
         Bukkit.getPluginManager().callEvent(event);
         if (event.isCancelled()) {
            return true;
         } else {
            structuremanager.a(sectionposition, structure, structurestart, ichunkaccess);
            return true;
         }
      } else {
         return false;
      }
   }

   private static int a(StructureManager structuremanager, IChunkAccess ichunkaccess, SectionPosition sectionposition, Structure structure) {
      StructureStart structurestart = structuremanager.a(sectionposition, structure, ichunkaccess);
      return structurestart != null ? structurestart.f() : 0;
   }

   public void a(GeneratorAccessSeed generatoraccessseed, StructureManager structuremanager, IChunkAccess ichunkaccess) {
      boolean flag = true;
      ChunkCoordIntPair chunkcoordintpair = ichunkaccess.f();
      int i = chunkcoordintpair.e;
      int j = chunkcoordintpair.f;
      int k = chunkcoordintpair.d();
      int l = chunkcoordintpair.e();
      SectionPosition sectionposition = SectionPosition.a(ichunkaccess);

      for(int i1 = i - 8; i1 <= i + 8; ++i1) {
         for(int j1 = j - 8; j1 <= j + 8; ++j1) {
            long k1 = ChunkCoordIntPair.c(i1, j1);

            for(StructureStart structurestart : generatoraccessseed.a(i1, j1).g().values()) {
               try {
                  if (structurestart.b() && structurestart.a().a(k, l, k + 15, l + 15)) {
                     structuremanager.a(sectionposition, structurestart.h(), k1, ichunkaccess);
                     PacketDebug.a(generatoraccessseed, structurestart);
                  }
               } catch (Exception var21) {
                  CrashReport crashreport = CrashReport.a(var21, "Generating structure reference");
                  CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Structure");
                  Optional<? extends IRegistry<Structure>> optional = generatoraccessseed.u_().c(Registries.ax);
                  crashreportsystemdetails.a("Id", () -> optional.<String>map(iregistry -> iregistry.b(structurestart.h()).toString()).orElse("UNKNOWN"));
                  crashreportsystemdetails.a("Name", () -> BuiltInRegistries.T.b(structurestart.h().e()).toString());
                  crashreportsystemdetails.a("Class", () -> structurestart.h().getClass().getCanonicalName());
                  throw new ReportedException(crashreport);
               }
            }
         }
      }
   }

   public abstract CompletableFuture<IChunkAccess> a(Executor var1, Blender var2, RandomState var3, StructureManager var4, IChunkAccess var5);

   public abstract int e();

   public abstract int f();

   public abstract int a(int var1, int var2, HeightMap.Type var3, LevelHeightAccessor var4, RandomState var5);

   public abstract net.minecraft.world.level.BlockColumn a(int var1, int var2, LevelHeightAccessor var3, RandomState var4);

   public int b(int i, int j, HeightMap.Type heightmap_type, LevelHeightAccessor levelheightaccessor, RandomState randomstate) {
      return this.a(i, j, heightmap_type, levelheightaccessor, randomstate);
   }

   public int c(int i, int j, HeightMap.Type heightmap_type, LevelHeightAccessor levelheightaccessor, RandomState randomstate) {
      return this.a(i, j, heightmap_type, levelheightaccessor, randomstate) - 1;
   }

   public abstract void a(List<String> var1, RandomState var2, BlockPosition var3);

   @Deprecated
   public BiomeSettingsGeneration a(Holder<BiomeBase> holder) {
      return this.d.apply(holder);
   }
}
