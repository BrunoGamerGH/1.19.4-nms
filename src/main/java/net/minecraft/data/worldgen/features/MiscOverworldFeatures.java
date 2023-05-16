package net.minecraft.data.worldgen.features;

import java.util.List;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.feature.WorldGenLakes;
import net.minecraft.world.level.levelgen.feature.WorldGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureCircleConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureHellFlowingLavaConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureLakeConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WorldGenFeatureStateProvider;
import net.minecraft.world.level.material.FluidTypes;

public class MiscOverworldFeatures {
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> a = FeatureUtils.a("ice_spike");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> b = FeatureUtils.a("ice_patch");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> c = FeatureUtils.a("forest_rock");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> d = FeatureUtils.a("iceberg_packed");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> e = FeatureUtils.a("iceberg_blue");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> f = FeatureUtils.a("blue_ice");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> g = FeatureUtils.a("lake_lava");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> h = FeatureUtils.a("disk_clay");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> i = FeatureUtils.a("disk_gravel");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> j = FeatureUtils.a("disk_sand");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> k = FeatureUtils.a("freeze_top_layer");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> l = FeatureUtils.a("disk_grass");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> m = FeatureUtils.a("bonus_chest");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> n = FeatureUtils.a("void_start_platform");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> o = FeatureUtils.a("desert_well");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> p = FeatureUtils.a("spring_lava_overworld");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> q = FeatureUtils.a("spring_lava_frozen");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> r = FeatureUtils.a("spring_water");

   public static void a(BootstapContext<WorldGenFeatureConfigured<?, ?>> var0) {
      FeatureUtils.a(var0, a, WorldGenerator.t);
      FeatureUtils.a(
         var0,
         b,
         WorldGenerator.H,
         new WorldGenFeatureCircleConfiguration(
            RuleBasedBlockStateProvider.a(Blocks.iB),
            BlockPredicate.c(List.of(Blocks.j, Blocks.i, Blocks.l, Blocks.k, Blocks.fk, Blocks.dO, Blocks.dN)),
            UniformInt.a(2, 3),
            1
         )
      );
      FeatureUtils.a(var0, c, WorldGenerator.G, new WorldGenFeatureLakeConfiguration(Blocks.cm.o()));
      FeatureUtils.a(var0, d, WorldGenerator.F, new WorldGenFeatureLakeConfiguration(Blocks.iB.o()));
      FeatureUtils.a(var0, e, WorldGenerator.F, new WorldGenFeatureLakeConfiguration(Blocks.mS.o()));
      FeatureUtils.a(var0, f, WorldGenerator.E);
      FeatureUtils.a(
         var0, g, WorldGenerator.I, new WorldGenLakes.a(WorldGenFeatureStateProvider.a(Blocks.H.o()), WorldGenFeatureStateProvider.a(Blocks.b.o()))
      );
      FeatureUtils.a(
         var0,
         h,
         WorldGenerator.H,
         new WorldGenFeatureCircleConfiguration(
            RuleBasedBlockStateProvider.a(Blocks.dQ), BlockPredicate.c(List.of(Blocks.j, Blocks.dQ)), UniformInt.a(2, 3), 1
         )
      );
      FeatureUtils.a(
         var0,
         i,
         WorldGenerator.H,
         new WorldGenFeatureCircleConfiguration(RuleBasedBlockStateProvider.a(Blocks.L), BlockPredicate.c(List.of(Blocks.j, Blocks.i)), UniformInt.a(2, 5), 2)
      );
      FeatureUtils.a(
         var0,
         j,
         WorldGenerator.H,
         new WorldGenFeatureCircleConfiguration(
            new RuleBasedBlockStateProvider(
               WorldGenFeatureStateProvider.a(Blocks.I),
               List.of(new RuleBasedBlockStateProvider.a(BlockPredicate.a(EnumDirection.a.q(), Blocks.a), WorldGenFeatureStateProvider.a(Blocks.aU)))
            ),
            BlockPredicate.c(List.of(Blocks.j, Blocks.i)),
            UniformInt.a(2, 6),
            2
         )
      );
      FeatureUtils.a(var0, k, WorldGenerator.v);
      FeatureUtils.a(
         var0,
         l,
         WorldGenerator.H,
         new WorldGenFeatureCircleConfiguration(
            new RuleBasedBlockStateProvider(
               WorldGenFeatureStateProvider.a(Blocks.j),
               List.of(
                  new RuleBasedBlockStateProvider.a(
                     BlockPredicate.a(BlockPredicate.b(BlockPredicate.b(EnumDirection.b.q()), BlockPredicate.a(EnumDirection.b.q(), FluidTypes.c))),
                     WorldGenFeatureStateProvider.a(Blocks.i)
                  )
               )
            ),
            BlockPredicate.c(List.of(Blocks.j, Blocks.rC)),
            UniformInt.a(2, 6),
            2
         )
      );
      FeatureUtils.a(var0, m, WorldGenerator.ad);
      FeatureUtils.a(var0, n, WorldGenerator.o);
      FeatureUtils.a(var0, o, WorldGenerator.p);
      FeatureUtils.a(
         var0,
         p,
         WorldGenerator.l,
         new WorldGenFeatureHellFlowingLavaConfiguration(
            FluidTypes.e.g(), true, 4, 1, HolderSet.a(Block::r, Blocks.b, Blocks.c, Blocks.e, Blocks.g, Blocks.rD, Blocks.qv, Blocks.qw, Blocks.j)
         )
      );
      FeatureUtils.a(
         var0,
         q,
         WorldGenerator.l,
         new WorldGenFeatureHellFlowingLavaConfiguration(FluidTypes.e.g(), true, 4, 1, HolderSet.a(Block::r, Blocks.dO, Blocks.qy, Blocks.iB))
      );
      FeatureUtils.a(
         var0,
         r,
         WorldGenerator.l,
         new WorldGenFeatureHellFlowingLavaConfiguration(
            FluidTypes.c.g(),
            true,
            4,
            1,
            HolderSet.a(Block::r, Blocks.b, Blocks.c, Blocks.e, Blocks.g, Blocks.rD, Blocks.qv, Blocks.qw, Blocks.j, Blocks.dO, Blocks.qy, Blocks.iB)
         )
      );
   }
}
