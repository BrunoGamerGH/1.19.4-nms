package net.minecraft.world.level.levelgen.flat;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.BiomeSettingsGeneration;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.dimension.DimensionManager;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.WorldGenStage;
import net.minecraft.world.level.levelgen.feature.WorldGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureFillConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.BuiltinStructureSets;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import org.slf4j.Logger;

public class GeneratorSettingsFlat {
   private static final Logger b = LogUtils.getLogger();
   public static final Codec<GeneratorSettingsFlat> a = RecordCodecBuilder.create(
         var0 -> var0.group(
                  RegistryCodecs.a(Registries.az).optionalFieldOf("structure_overrides").forGetter(var0x -> var0x.c),
                  WorldGenFlatLayerInfo.a.listOf().fieldOf("layers").forGetter(GeneratorSettingsFlat::e),
                  Codec.BOOL.fieldOf("lakes").orElse(false).forGetter(var0x -> var0x.i),
                  Codec.BOOL.fieldOf("features").orElse(false).forGetter(var0x -> var0x.h),
                  BiomeBase.c.optionalFieldOf("biome").orElseGet(Optional::empty).forGetter(var0x -> Optional.of(var0x.e)),
                  RegistryOps.d(Biomes.b),
                  RegistryOps.d(MiscOverworldPlacements.g),
                  RegistryOps.d(MiscOverworldPlacements.h)
               )
               .apply(var0, GeneratorSettingsFlat::new)
      )
      .comapFlatMap(GeneratorSettingsFlat::a, Function.identity())
      .stable();
   private final Optional<HolderSet<StructureSet>> c;
   private final List<WorldGenFlatLayerInfo> d = Lists.newArrayList();
   private final Holder<BiomeBase> e;
   private final List<IBlockData> f;
   private boolean g;
   private boolean h;
   private boolean i;
   private final List<Holder<PlacedFeature>> j;

   private static DataResult<GeneratorSettingsFlat> a(GeneratorSettingsFlat var0) {
      int var1 = var0.d.stream().mapToInt(WorldGenFlatLayerInfo::a).sum();
      return var1 > DimensionManager.c ? DataResult.error(() -> "Sum of layer heights is > " + DimensionManager.c, var0) : DataResult.success(var0);
   }

   private GeneratorSettingsFlat(
      Optional<HolderSet<StructureSet>> var0,
      List<WorldGenFlatLayerInfo> var1,
      boolean var2,
      boolean var3,
      Optional<Holder<BiomeBase>> var4,
      Holder.c<BiomeBase> var5,
      Holder<PlacedFeature> var6,
      Holder<PlacedFeature> var7
   ) {
      this(var0, a(var4, var5), List.of(var6, var7));
      if (var2) {
         this.b();
      }

      if (var3) {
         this.a();
      }

      this.d.addAll(var1);
      this.g();
   }

   private static Holder<BiomeBase> a(Optional<? extends Holder<BiomeBase>> var0, Holder<BiomeBase> var1) {
      if (var0.isEmpty()) {
         b.error("Unknown biome, defaulting to plains");
         return var1;
      } else {
         return var0.get();
      }
   }

   public GeneratorSettingsFlat(Optional<HolderSet<StructureSet>> var0, Holder<BiomeBase> var1, List<Holder<PlacedFeature>> var2) {
      this.c = var0;
      this.e = var1;
      this.f = Lists.newArrayList();
      this.j = var2;
   }

   public GeneratorSettingsFlat a(List<WorldGenFlatLayerInfo> var0, Optional<HolderSet<StructureSet>> var1, Holder<BiomeBase> var2) {
      GeneratorSettingsFlat var3 = new GeneratorSettingsFlat(var1, var2, this.j);

      for(WorldGenFlatLayerInfo var5 : var0) {
         var3.d.add(new WorldGenFlatLayerInfo(var5.a(), var5.b().b()));
         var3.g();
      }

      if (this.h) {
         var3.a();
      }

      if (this.i) {
         var3.b();
      }

      return var3;
   }

   public void a() {
      this.h = true;
   }

   public void b() {
      this.i = true;
   }

   public BiomeSettingsGeneration a(Holder<BiomeBase> var0) {
      if (!var0.equals(this.e)) {
         return var0.a().d();
      } else {
         BiomeSettingsGeneration var1 = this.d().a().d();
         BiomeSettingsGeneration.b var2 = new BiomeSettingsGeneration.b();
         if (this.i) {
            for(Holder<PlacedFeature> var4 : this.j) {
               var2.a(WorldGenStage.Decoration.b, var4);
            }
         }

         boolean var3 = (!this.g || var0.a(Biomes.a)) && this.h;
         if (var3) {
            List<HolderSet<PlacedFeature>> var4 = var1.b();

            for(int var5 = 0; var5 < var4.size(); ++var5) {
               if (var5 != WorldGenStage.Decoration.d.ordinal()
                  && var5 != WorldGenStage.Decoration.e.ordinal()
                  && (!this.i || var5 != WorldGenStage.Decoration.b.ordinal())) {
                  for(Holder<PlacedFeature> var8 : var4.get(var5)) {
                     var2.a(var5, var8);
                  }
               }
            }
         }

         List<IBlockData> var4 = this.f();

         for(int var5 = 0; var5 < var4.size(); ++var5) {
            IBlockData var6 = var4.get(var5);
            if (!HeightMap.Type.e.e().test(var6)) {
               var4.set(var5, null);
               var2.a(WorldGenStage.Decoration.k, PlacementUtils.a(WorldGenerator.ac, new WorldGenFeatureFillConfiguration(var5, var6)));
            }
         }

         return var2.a();
      }
   }

   public Optional<HolderSet<StructureSet>> c() {
      return this.c;
   }

   public Holder<BiomeBase> d() {
      return this.e;
   }

   public List<WorldGenFlatLayerInfo> e() {
      return this.d;
   }

   public List<IBlockData> f() {
      return this.f;
   }

   public void g() {
      this.f.clear();

      for(WorldGenFlatLayerInfo var1 : this.d) {
         for(int var2 = 0; var2 < var1.a(); ++var2) {
            this.f.add(var1.b());
         }
      }

      this.g = this.f.stream().allMatch(var0 -> var0.a(Blocks.a));
   }

   public static GeneratorSettingsFlat a(HolderGetter<BiomeBase> var0, HolderGetter<StructureSet> var1, HolderGetter<PlacedFeature> var2) {
      HolderSet<StructureSet> var3 = HolderSet.a(var1.b(BuiltinStructureSets.r), var1.b(BuiltinStructureSets.a));
      GeneratorSettingsFlat var4 = new GeneratorSettingsFlat(Optional.of(var3), a(var0), b(var2));
      var4.e().add(new WorldGenFlatLayerInfo(1, Blocks.F));
      var4.e().add(new WorldGenFlatLayerInfo(2, Blocks.j));
      var4.e().add(new WorldGenFlatLayerInfo(1, Blocks.i));
      var4.g();
      return var4;
   }

   public static Holder<BiomeBase> a(HolderGetter<BiomeBase> var0) {
      return var0.b(Biomes.b);
   }

   public static List<Holder<PlacedFeature>> b(HolderGetter<PlacedFeature> var0) {
      return List.of(var0.b(MiscOverworldPlacements.g), var0.b(MiscOverworldPlacements.h));
   }
}
