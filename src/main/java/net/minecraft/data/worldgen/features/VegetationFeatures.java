package net.minecraft.data.worldgen.features;

import java.util.List;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.InclusiveRange;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.world.level.block.BlockSweetBerryBush;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PinkPetalsBlock;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.feature.WorldGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureChoiceConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureConfigurationChance;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureRandom2;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureRandomChoiceConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureRandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.DualNoiseProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseThresholdProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WorldGenFeatureStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WorldGenFeatureStateProviderWeighted;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.synth.NoiseGeneratorNormal;
import net.minecraft.world.level.material.FluidTypes;

public class VegetationFeatures {
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> a = FeatureUtils.a("bamboo_no_podzol");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> b = FeatureUtils.a("bamboo_some_podzol");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> c = FeatureUtils.a("vines");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> d = FeatureUtils.a("patch_brown_mushroom");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> e = FeatureUtils.a("patch_red_mushroom");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> f = FeatureUtils.a("patch_sunflower");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> g = FeatureUtils.a("patch_pumpkin");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> h = FeatureUtils.a("patch_berry_bush");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> i = FeatureUtils.a("patch_taiga_grass");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> j = FeatureUtils.a("patch_grass");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> k = FeatureUtils.a("patch_grass_jungle");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> l = FeatureUtils.a("single_piece_of_grass");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> m = FeatureUtils.a("patch_dead_bush");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> n = FeatureUtils.a("patch_melon");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> o = FeatureUtils.a("patch_waterlily");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> p = FeatureUtils.a("patch_tall_grass");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> q = FeatureUtils.a("patch_large_fern");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> r = FeatureUtils.a("patch_cactus");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> s = FeatureUtils.a("patch_sugar_cane");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> t = FeatureUtils.a("flower_default");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> u = FeatureUtils.a("flower_flower_forest");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> v = FeatureUtils.a("flower_swamp");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> w = FeatureUtils.a("flower_plain");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> x = FeatureUtils.a("flower_meadow");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> y = FeatureUtils.a("flower_cherry");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> z = FeatureUtils.a("forest_flowers");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> A = FeatureUtils.a("dark_forest_vegetation");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> B = FeatureUtils.a("trees_flower_forest");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> C = FeatureUtils.a("meadow_trees");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> D = FeatureUtils.a("trees_taiga");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> E = FeatureUtils.a("trees_grove");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> F = FeatureUtils.a("trees_savanna");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> G = FeatureUtils.a("birch_tall");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> H = FeatureUtils.a("trees_windswept_hills");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> I = FeatureUtils.a("trees_water");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> J = FeatureUtils.a("trees_birch_and_oak");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> K = FeatureUtils.a("trees_plains");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> L = FeatureUtils.a("trees_sparse_jungle");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> M = FeatureUtils.a("trees_old_growth_spruce_taiga");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> N = FeatureUtils.a("trees_old_growth_pine_taiga");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> O = FeatureUtils.a("trees_jungle");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> P = FeatureUtils.a("bamboo_vegetation");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> Q = FeatureUtils.a("mushroom_island_vegetation");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> R = FeatureUtils.a("mangrove_vegetation");

   private static WorldGenFeatureRandomPatchConfiguration a(WorldGenFeatureStateProvider var0, int var1) {
      return FeatureUtils.a(var1, PlacementUtils.a(WorldGenerator.T, new WorldGenFeatureBlockConfiguration(var0)));
   }

   public static void a(BootstapContext<WorldGenFeatureConfigured<?, ?>> var0) {
      HolderGetter<WorldGenFeatureConfigured<?, ?>> var1 = var0.a(Registries.aq);
      Holder<WorldGenFeatureConfigured<?, ?>> var2 = var1.b(TreeFeatures.e);
      Holder<WorldGenFeatureConfigured<?, ?>> var3 = var1.b(TreeFeatures.f);
      Holder<WorldGenFeatureConfigured<?, ?>> var4 = var1.b(TreeFeatures.I);
      Holder<WorldGenFeatureConfigured<?, ?>> var5 = var1.b(TreeFeatures.C);
      Holder<WorldGenFeatureConfigured<?, ?>> var6 = var1.b(k);
      HolderGetter<PlacedFeature> var7 = var0.a(Registries.aw);
      Holder<PlacedFeature> var8 = var7.b(TreePlacements.d);
      Holder<PlacedFeature> var9 = var7.b(TreePlacements.e);
      Holder<PlacedFeature> var10 = var7.b(TreePlacements.n);
      Holder<PlacedFeature> var11 = var7.b(TreePlacements.y);
      Holder<PlacedFeature> var12 = var7.b(TreePlacements.A);
      Holder<PlacedFeature> var13 = var7.b(TreePlacements.B);
      Holder<PlacedFeature> var14 = var7.b(TreePlacements.l);
      Holder<PlacedFeature> var15 = var7.b(TreePlacements.g);
      Holder<PlacedFeature> var16 = var7.b(TreePlacements.j);
      Holder<PlacedFeature> var17 = var7.b(TreePlacements.f);
      Holder<PlacedFeature> var18 = var7.b(TreePlacements.t);
      Holder<PlacedFeature> var19 = var7.b(TreePlacements.x);
      Holder<PlacedFeature> var20 = var7.b(TreePlacements.z);
      Holder<PlacedFeature> var21 = var7.b(TreePlacements.s);
      Holder<PlacedFeature> var22 = var7.b(TreePlacements.p);
      Holder<PlacedFeature> var23 = var7.b(TreePlacements.q);
      Holder<PlacedFeature> var24 = var7.b(TreePlacements.o);
      Holder<PlacedFeature> var25 = var7.b(TreePlacements.r);
      Holder<PlacedFeature> var26 = var7.b(TreePlacements.c);
      Holder<PlacedFeature> var27 = var7.b(TreePlacements.w);
      Holder<PlacedFeature> var28 = var7.b(TreePlacements.u);
      Holder<PlacedFeature> var29 = var7.b(TreePlacements.k);
      Holder<PlacedFeature> var30 = var7.b(TreePlacements.v);
      Holder<PlacedFeature> var31 = var7.b(TreePlacements.m);
      Holder<PlacedFeature> var32 = var7.b(TreePlacements.h);
      FeatureUtils.a(var0, a, WorldGenerator.U, new WorldGenFeatureConfigurationChance(0.0F));
      FeatureUtils.a(var0, b, WorldGenerator.U, new WorldGenFeatureConfigurationChance(0.2F));
      FeatureUtils.a(var0, c, WorldGenerator.w);
      FeatureUtils.a(
         var0, d, WorldGenerator.j, FeatureUtils.a(WorldGenerator.T, new WorldGenFeatureBlockConfiguration(WorldGenFeatureStateProvider.a(Blocks.ce)))
      );
      FeatureUtils.a(
         var0, e, WorldGenerator.j, FeatureUtils.a(WorldGenerator.T, new WorldGenFeatureBlockConfiguration(WorldGenFeatureStateProvider.a(Blocks.cf)))
      );
      FeatureUtils.a(
         var0, f, WorldGenerator.j, FeatureUtils.a(WorldGenerator.T, new WorldGenFeatureBlockConfiguration(WorldGenFeatureStateProvider.a(Blocks.iC)))
      );
      FeatureUtils.a(
         var0,
         g,
         WorldGenerator.j,
         FeatureUtils.a(WorldGenerator.T, new WorldGenFeatureBlockConfiguration(WorldGenFeatureStateProvider.a(Blocks.dU)), List.of(Blocks.i))
      );
      FeatureUtils.a(
         var0,
         h,
         WorldGenerator.j,
         FeatureUtils.a(
            WorldGenerator.T,
            new WorldGenFeatureBlockConfiguration(WorldGenFeatureStateProvider.a(Blocks.oe.o().a(BlockSweetBerryBush.b, Integer.valueOf(3)))),
            List.of(Blocks.i)
         )
      );
      FeatureUtils.a(
         var0,
         i,
         WorldGenerator.j,
         a(new WorldGenFeatureStateProviderWeighted(SimpleWeightedRandomList.<IBlockData>a().a(Blocks.bs.o(), 1).a(Blocks.bt.o(), 4)), 32)
      );
      FeatureUtils.a(var0, j, WorldGenerator.j, a(WorldGenFeatureStateProvider.a(Blocks.bs), 32));
      FeatureUtils.a(
         var0,
         k,
         WorldGenerator.j,
         new WorldGenFeatureRandomPatchConfiguration(
            32,
            7,
            3,
            PlacementUtils.a(
               WorldGenerator.T,
               new WorldGenFeatureBlockConfiguration(
                  new WorldGenFeatureStateProviderWeighted(SimpleWeightedRandomList.<IBlockData>a().a(Blocks.bs.o(), 3).a(Blocks.bt.o(), 1))
               ),
               BlockPredicate.a(BlockPredicate.c, BlockPredicate.a(BlockPredicate.a(EnumDirection.a.q(), Blocks.l)))
            )
         )
      );
      FeatureUtils.a(var0, l, WorldGenerator.T, new WorldGenFeatureBlockConfiguration(WorldGenFeatureStateProvider.a(Blocks.bs.o())));
      FeatureUtils.a(var0, m, WorldGenerator.j, a(WorldGenFeatureStateProvider.a(Blocks.bu), 4));
      FeatureUtils.a(
         var0,
         n,
         WorldGenerator.j,
         new WorldGenFeatureRandomPatchConfiguration(
            64,
            7,
            3,
            PlacementUtils.a(
               WorldGenerator.T,
               new WorldGenFeatureBlockConfiguration(WorldGenFeatureStateProvider.a(Blocks.eZ)),
               BlockPredicate.a(BlockPredicate.b(), BlockPredicate.d(), BlockPredicate.a(EnumDirection.a.q(), Blocks.i))
            )
         )
      );
      FeatureUtils.a(
         var0,
         o,
         WorldGenerator.j,
         new WorldGenFeatureRandomPatchConfiguration(
            10, 7, 3, PlacementUtils.a(WorldGenerator.T, new WorldGenFeatureBlockConfiguration(WorldGenFeatureStateProvider.a(Blocks.fl)))
         )
      );
      FeatureUtils.a(
         var0, p, WorldGenerator.j, FeatureUtils.a(WorldGenerator.T, new WorldGenFeatureBlockConfiguration(WorldGenFeatureStateProvider.a(Blocks.iG)))
      );
      FeatureUtils.a(
         var0, q, WorldGenerator.j, FeatureUtils.a(WorldGenerator.T, new WorldGenFeatureBlockConfiguration(WorldGenFeatureStateProvider.a(Blocks.iH)))
      );
      FeatureUtils.a(
         var0,
         r,
         WorldGenerator.j,
         FeatureUtils.a(
            10,
            PlacementUtils.a(
               WorldGenerator.x,
               BlockColumnConfiguration.b(BiasedToBottomInt.a(1, 3), WorldGenFeatureStateProvider.a(Blocks.dP)),
               BlockPredicateFilter.a(BlockPredicate.a(BlockPredicate.c, BlockPredicate.a(Blocks.dP.o(), BlockPosition.b)))
            )
         )
      );
      FeatureUtils.a(
         var0,
         s,
         WorldGenerator.j,
         new WorldGenFeatureRandomPatchConfiguration(
            20,
            4,
            0,
            PlacementUtils.a(
               WorldGenerator.x,
               BlockColumnConfiguration.b(BiasedToBottomInt.a(2, 4), WorldGenFeatureStateProvider.a(Blocks.dR)),
               BlockPredicateFilter.a(
                  BlockPredicate.a(
                     BlockPredicate.c,
                     BlockPredicate.a(Blocks.dR.o(), BlockPosition.b),
                     BlockPredicate.b(
                        BlockPredicate.a(new BlockPosition(1, -1, 0), FluidTypes.c, FluidTypes.b),
                        BlockPredicate.a(new BlockPosition(-1, -1, 0), FluidTypes.c, FluidTypes.b),
                        BlockPredicate.a(new BlockPosition(0, -1, 1), FluidTypes.c, FluidTypes.b),
                        BlockPredicate.a(new BlockPosition(0, -1, -1), FluidTypes.c, FluidTypes.b)
                     )
                  )
               )
            )
         )
      );
      FeatureUtils.a(
         var0,
         t,
         WorldGenerator.h,
         a(new WorldGenFeatureStateProviderWeighted(SimpleWeightedRandomList.<IBlockData>a().a(Blocks.bS.o(), 2).a(Blocks.bQ.o(), 1)), 64)
      );
      FeatureUtils.a(
         var0,
         u,
         WorldGenerator.h,
         new WorldGenFeatureRandomPatchConfiguration(
            96,
            6,
            2,
            PlacementUtils.a(
               WorldGenerator.T,
               new WorldGenFeatureBlockConfiguration(
                  new NoiseProvider(
                     2345L,
                     new NoiseGeneratorNormal.a(0, 1.0),
                     0.020833334F,
                     List.of(
                        Blocks.bQ.o(),
                        Blocks.bS.o(),
                        Blocks.bU.o(),
                        Blocks.bV.o(),
                        Blocks.bW.o(),
                        Blocks.bX.o(),
                        Blocks.bY.o(),
                        Blocks.bZ.o(),
                        Blocks.ca.o(),
                        Blocks.cb.o(),
                        Blocks.cd.o()
                     )
                  )
               )
            )
         )
      );
      FeatureUtils.a(
         var0,
         v,
         WorldGenerator.h,
         new WorldGenFeatureRandomPatchConfiguration(
            64, 6, 2, PlacementUtils.a(WorldGenerator.T, new WorldGenFeatureBlockConfiguration(WorldGenFeatureStateProvider.a(Blocks.bT)))
         )
      );
      FeatureUtils.a(
         var0,
         w,
         WorldGenerator.h,
         new WorldGenFeatureRandomPatchConfiguration(
            64,
            6,
            2,
            PlacementUtils.a(
               WorldGenerator.T,
               new WorldGenFeatureBlockConfiguration(
                  new NoiseThresholdProvider(
                     2345L,
                     new NoiseGeneratorNormal.a(0, 1.0),
                     0.005F,
                     -0.8F,
                     0.33333334F,
                     Blocks.bQ.o(),
                     List.of(Blocks.bX.o(), Blocks.bW.o(), Blocks.bZ.o(), Blocks.bY.o()),
                     List.of(Blocks.bS.o(), Blocks.bV.o(), Blocks.ca.o(), Blocks.cb.o())
                  )
               )
            )
         )
      );
      FeatureUtils.a(
         var0,
         x,
         WorldGenerator.h,
         new WorldGenFeatureRandomPatchConfiguration(
            96,
            6,
            2,
            PlacementUtils.a(
               WorldGenerator.T,
               new WorldGenFeatureBlockConfiguration(
                  new DualNoiseProvider(
                     new InclusiveRange<>(1, 3),
                     new NoiseGeneratorNormal.a(-10, 1.0),
                     1.0F,
                     2345L,
                     new NoiseGeneratorNormal.a(-3, 1.0),
                     1.0F,
                     List.of(Blocks.iG.o(), Blocks.bU.o(), Blocks.bS.o(), Blocks.bV.o(), Blocks.bQ.o(), Blocks.cb.o(), Blocks.ca.o(), Blocks.bs.o())
                  )
               )
            )
         )
      );
      SimpleWeightedRandomList.a<IBlockData> var33 = SimpleWeightedRandomList.a();

      for(int var34 = 1; var34 <= 4; ++var34) {
         for(EnumDirection var36 : EnumDirection.EnumDirectionLimit.a) {
            var33.a(Blocks.rv.o().a(PinkPetalsBlock.d, Integer.valueOf(var34)).a(PinkPetalsBlock.c, var36), 1);
         }
      }

      FeatureUtils.a(
         var0,
         y,
         WorldGenerator.h,
         new WorldGenFeatureRandomPatchConfiguration(
            96, 6, 2, PlacementUtils.a(WorldGenerator.T, new WorldGenFeatureBlockConfiguration(new WorldGenFeatureStateProviderWeighted(var33)))
         )
      );
      FeatureUtils.a(
         var0,
         z,
         WorldGenerator.ah,
         new WorldGenFeatureRandom2(
            HolderSet.a(
               PlacementUtils.a(
                  WorldGenerator.j, FeatureUtils.a(WorldGenerator.T, new WorldGenFeatureBlockConfiguration(WorldGenFeatureStateProvider.a(Blocks.iD)))
               ),
               PlacementUtils.a(
                  WorldGenerator.j, FeatureUtils.a(WorldGenerator.T, new WorldGenFeatureBlockConfiguration(WorldGenFeatureStateProvider.a(Blocks.iE)))
               ),
               PlacementUtils.a(
                  WorldGenerator.j, FeatureUtils.a(WorldGenerator.T, new WorldGenFeatureBlockConfiguration(WorldGenFeatureStateProvider.a(Blocks.iF)))
               ),
               PlacementUtils.a(
                  WorldGenerator.i, FeatureUtils.a(WorldGenerator.T, new WorldGenFeatureBlockConfiguration(WorldGenFeatureStateProvider.a(Blocks.cd)))
               )
            )
         )
      );
      FeatureUtils.a(
         var0,
         A,
         WorldGenerator.ag,
         new WorldGenFeatureRandomChoiceConfiguration(
            List.of(
               new WeightedPlacedFeature(PlacementUtils.a(var2), 0.025F),
               new WeightedPlacedFeature(PlacementUtils.a(var3), 0.05F),
               new WeightedPlacedFeature(var8, 0.6666667F),
               new WeightedPlacedFeature(var9, 0.2F),
               new WeightedPlacedFeature(var10, 0.1F)
            ),
            var26
         )
      );
      FeatureUtils.a(
         var0,
         B,
         WorldGenerator.ag,
         new WorldGenFeatureRandomChoiceConfiguration(List.of(new WeightedPlacedFeature(var11, 0.2F), new WeightedPlacedFeature(var12, 0.1F)), var27)
      );
      FeatureUtils.a(var0, C, WorldGenerator.ag, new WorldGenFeatureRandomChoiceConfiguration(List.of(new WeightedPlacedFeature(var13, 0.5F)), var28));
      FeatureUtils.a(var0, D, WorldGenerator.ag, new WorldGenFeatureRandomChoiceConfiguration(List.of(new WeightedPlacedFeature(var14, 0.33333334F)), var15));
      FeatureUtils.a(var0, E, WorldGenerator.ag, new WorldGenFeatureRandomChoiceConfiguration(List.of(new WeightedPlacedFeature(var16, 0.33333334F)), var29));
      FeatureUtils.a(var0, F, WorldGenerator.ag, new WorldGenFeatureRandomChoiceConfiguration(List.of(new WeightedPlacedFeature(var17, 0.8F)), var26));
      FeatureUtils.a(var0, G, WorldGenerator.ag, new WorldGenFeatureRandomChoiceConfiguration(List.of(new WeightedPlacedFeature(var18, 0.5F)), var19));
      FeatureUtils.a(
         var0,
         H,
         WorldGenerator.ag,
         new WorldGenFeatureRandomChoiceConfiguration(List.of(new WeightedPlacedFeature(var15, 0.666F), new WeightedPlacedFeature(var10, 0.1F)), var26)
      );
      FeatureUtils.a(var0, I, WorldGenerator.ag, new WorldGenFeatureRandomChoiceConfiguration(List.of(new WeightedPlacedFeature(var10, 0.1F)), var26));
      FeatureUtils.a(
         var0,
         J,
         WorldGenerator.ag,
         new WorldGenFeatureRandomChoiceConfiguration(List.of(new WeightedPlacedFeature(var19, 0.2F), new WeightedPlacedFeature(var20, 0.1F)), var30)
      );
      FeatureUtils.a(
         var0,
         K,
         WorldGenerator.ag,
         new WorldGenFeatureRandomChoiceConfiguration(List.of(new WeightedPlacedFeature(PlacementUtils.a(var4), 0.33333334F)), PlacementUtils.a(var5))
      );
      FeatureUtils.a(
         var0,
         L,
         WorldGenerator.ag,
         new WorldGenFeatureRandomChoiceConfiguration(List.of(new WeightedPlacedFeature(var10, 0.1F), new WeightedPlacedFeature(var21, 0.5F)), var31)
      );
      FeatureUtils.a(
         var0,
         M,
         WorldGenerator.ag,
         new WorldGenFeatureRandomChoiceConfiguration(
            List.of(new WeightedPlacedFeature(var22, 0.33333334F), new WeightedPlacedFeature(var14, 0.33333334F)), var15
         )
      );
      FeatureUtils.a(
         var0,
         N,
         WorldGenerator.ag,
         new WorldGenFeatureRandomChoiceConfiguration(
            List.of(
               new WeightedPlacedFeature(var22, 0.025641026F), new WeightedPlacedFeature(var23, 0.30769232F), new WeightedPlacedFeature(var14, 0.33333334F)
            ),
            var15
         )
      );
      FeatureUtils.a(
         var0,
         O,
         WorldGenerator.ag,
         new WorldGenFeatureRandomChoiceConfiguration(
            List.of(new WeightedPlacedFeature(var10, 0.1F), new WeightedPlacedFeature(var21, 0.5F), new WeightedPlacedFeature(var24, 0.33333334F)), var31
         )
      );
      FeatureUtils.a(
         var0,
         P,
         WorldGenerator.ag,
         new WorldGenFeatureRandomChoiceConfiguration(
            List.of(new WeightedPlacedFeature(var10, 0.05F), new WeightedPlacedFeature(var21, 0.15F), new WeightedPlacedFeature(var24, 0.7F)),
            PlacementUtils.a(var6)
         )
      );
      FeatureUtils.a(var0, Q, WorldGenerator.ai, new WorldGenFeatureChoiceConfiguration(PlacementUtils.a(var3), PlacementUtils.a(var2)));
      FeatureUtils.a(var0, R, WorldGenerator.ag, new WorldGenFeatureRandomChoiceConfiguration(List.of(new WeightedPlacedFeature(var25, 0.85F)), var32));
   }
}
