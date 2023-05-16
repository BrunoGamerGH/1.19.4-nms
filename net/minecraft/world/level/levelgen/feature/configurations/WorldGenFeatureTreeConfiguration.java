package net.minecraft.world.level.levelgen.feature.configurations;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.WorldGenFoilagePlacer;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.WorldGenFeatureStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.WorldGenFeatureTree;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;

public class WorldGenFeatureTreeConfiguration implements WorldGenFeatureConfiguration {
   public static final Codec<WorldGenFeatureTreeConfiguration> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               WorldGenFeatureStateProvider.a.fieldOf("trunk_provider").forGetter(var0x -> var0x.b),
               TrunkPlacer.c.fieldOf("trunk_placer").forGetter(var0x -> var0x.d),
               WorldGenFeatureStateProvider.a.fieldOf("foliage_provider").forGetter(var0x -> var0x.e),
               WorldGenFoilagePlacer.d.fieldOf("foliage_placer").forGetter(var0x -> var0x.f),
               RootPlacer.d.optionalFieldOf("root_placer").forGetter(var0x -> var0x.g),
               WorldGenFeatureStateProvider.a.fieldOf("dirt_provider").forGetter(var0x -> var0x.c),
               FeatureSize.a.fieldOf("minimum_size").forGetter(var0x -> var0x.h),
               WorldGenFeatureTree.h.listOf().fieldOf("decorators").forGetter(var0x -> var0x.i),
               Codec.BOOL.fieldOf("ignore_vines").orElse(false).forGetter(var0x -> var0x.j),
               Codec.BOOL.fieldOf("force_dirt").orElse(false).forGetter(var0x -> var0x.k)
            )
            .apply(var0, WorldGenFeatureTreeConfiguration::new)
   );
   public final WorldGenFeatureStateProvider b;
   public final WorldGenFeatureStateProvider c;
   public final TrunkPlacer d;
   public final WorldGenFeatureStateProvider e;
   public final WorldGenFoilagePlacer f;
   public final Optional<RootPlacer> g;
   public final FeatureSize h;
   public final List<WorldGenFeatureTree> i;
   public final boolean j;
   public final boolean k;

   protected WorldGenFeatureTreeConfiguration(
      WorldGenFeatureStateProvider var0,
      TrunkPlacer var1,
      WorldGenFeatureStateProvider var2,
      WorldGenFoilagePlacer var3,
      Optional<RootPlacer> var4,
      WorldGenFeatureStateProvider var5,
      FeatureSize var6,
      List<WorldGenFeatureTree> var7,
      boolean var8,
      boolean var9
   ) {
      this.b = var0;
      this.d = var1;
      this.e = var2;
      this.f = var3;
      this.g = var4;
      this.c = var5;
      this.h = var6;
      this.i = var7;
      this.j = var8;
      this.k = var9;
   }

   public static class a {
      public final WorldGenFeatureStateProvider a;
      private final TrunkPlacer c;
      public final WorldGenFeatureStateProvider b;
      private final WorldGenFoilagePlacer d;
      private final Optional<RootPlacer> e;
      private WorldGenFeatureStateProvider f;
      private final FeatureSize g;
      private List<WorldGenFeatureTree> h = ImmutableList.of();
      private boolean i;
      private boolean j;

      public a(
         WorldGenFeatureStateProvider var0,
         TrunkPlacer var1,
         WorldGenFeatureStateProvider var2,
         WorldGenFoilagePlacer var3,
         Optional<RootPlacer> var4,
         FeatureSize var5
      ) {
         this.a = var0;
         this.c = var1;
         this.b = var2;
         this.f = WorldGenFeatureStateProvider.a(Blocks.j);
         this.d = var3;
         this.e = var4;
         this.g = var5;
      }

      public a(WorldGenFeatureStateProvider var0, TrunkPlacer var1, WorldGenFeatureStateProvider var2, WorldGenFoilagePlacer var3, FeatureSize var4) {
         this(var0, var1, var2, var3, Optional.empty(), var4);
      }

      public WorldGenFeatureTreeConfiguration.a a(WorldGenFeatureStateProvider var0) {
         this.f = var0;
         return this;
      }

      public WorldGenFeatureTreeConfiguration.a a(List<WorldGenFeatureTree> var0) {
         this.h = var0;
         return this;
      }

      public WorldGenFeatureTreeConfiguration.a a() {
         this.i = true;
         return this;
      }

      public WorldGenFeatureTreeConfiguration.a b() {
         this.j = true;
         return this;
      }

      public WorldGenFeatureTreeConfiguration c() {
         return new WorldGenFeatureTreeConfiguration(this.a, this.c, this.b, this.d, this.e, this.f, this.g, this.h, this.i, this.j);
      }
   }
}
