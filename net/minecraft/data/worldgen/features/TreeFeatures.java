package net.minecraft.data.worldgen.features;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockHugeMushroom;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MangrovePropaguleBlock;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureHugeFungiConfiguration;
import net.minecraft.world.level.levelgen.feature.WorldGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureMushroomConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureTreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSizeThreeLayers;
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSizeTwoLayers;
import net.minecraft.world.level.levelgen.feature.foliageplacers.CherryFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.WorldGenFoilagePlacerAcacia;
import net.minecraft.world.level.levelgen.feature.foliageplacers.WorldGenFoilagePlacerBlob;
import net.minecraft.world.level.levelgen.feature.foliageplacers.WorldGenFoilagePlacerBush;
import net.minecraft.world.level.levelgen.feature.foliageplacers.WorldGenFoilagePlacerDarkOak;
import net.minecraft.world.level.levelgen.feature.foliageplacers.WorldGenFoilagePlacerFancy;
import net.minecraft.world.level.levelgen.feature.foliageplacers.WorldGenFoilagePlacerJungle;
import net.minecraft.world.level.levelgen.feature.foliageplacers.WorldGenFoilagePlacerMegaPine;
import net.minecraft.world.level.levelgen.feature.foliageplacers.WorldGenFoilagePlacerPine;
import net.minecraft.world.level.levelgen.feature.foliageplacers.WorldGenFoilagePlacerSpruce;
import net.minecraft.world.level.levelgen.feature.rootplacers.AboveRootPlacement;
import net.minecraft.world.level.levelgen.feature.rootplacers.MangroveRootPlacement;
import net.minecraft.world.level.levelgen.feature.rootplacers.MangroveRootPlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.RandomizedIntStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WorldGenFeatureStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WorldGenFeatureStateProviderWeighted;
import net.minecraft.world.level.levelgen.feature.treedecorators.AttachedToLeavesDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.WorldGenFeatureTreeAlterGround;
import net.minecraft.world.level.levelgen.feature.treedecorators.WorldGenFeatureTreeBeehive;
import net.minecraft.world.level.levelgen.feature.treedecorators.WorldGenFeatureTreeCocoa;
import net.minecraft.world.level.levelgen.feature.treedecorators.WorldGenFeatureTreeVineLeaves;
import net.minecraft.world.level.levelgen.feature.treedecorators.WorldGenFeatureTreeVineTrunk;
import net.minecraft.world.level.levelgen.feature.trunkplacers.BendingTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.CherryTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerDarkOak;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerFancy;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerForking;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerGiant;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerMegaJungle;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerStraight;
import net.minecraft.world.level.levelgen.feature.trunkplacers.UpwardsBranchingTrunkPlacer;

public class TreeFeatures {
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> a = FeatureUtils.a("crimson_fungus");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> b = FeatureUtils.a("crimson_fungus_planted");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> c = FeatureUtils.a("warped_fungus");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> d = FeatureUtils.a("warped_fungus_planted");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> e = FeatureUtils.a("huge_brown_mushroom");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> f = FeatureUtils.a("huge_red_mushroom");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> g = FeatureUtils.a("oak");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> h = FeatureUtils.a("dark_oak");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> i = FeatureUtils.a("birch");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> j = FeatureUtils.a("acacia");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> k = FeatureUtils.a("spruce");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> l = FeatureUtils.a("pine");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> m = FeatureUtils.a("jungle_tree");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> n = FeatureUtils.a("fancy_oak");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> o = FeatureUtils.a("jungle_tree_no_vine");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> p = FeatureUtils.a("mega_jungle_tree");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> q = FeatureUtils.a("mega_spruce");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> r = FeatureUtils.a("mega_pine");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> s = FeatureUtils.a("super_birch_bees_0002");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> t = FeatureUtils.a("super_birch_bees");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> u = FeatureUtils.a("swamp_oak");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> v = FeatureUtils.a("jungle_bush");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> w = FeatureUtils.a("azalea_tree");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> x = FeatureUtils.a("mangrove");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> y = FeatureUtils.a("tall_mangrove");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> z = FeatureUtils.a("cherry");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> A = FeatureUtils.a("oak_bees_0002");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> B = FeatureUtils.a("oak_bees_002");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> C = FeatureUtils.a("oak_bees_005");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> D = FeatureUtils.a("birch_bees_0002");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> E = FeatureUtils.a("birch_bees_002");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> F = FeatureUtils.a("birch_bees_005");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> G = FeatureUtils.a("fancy_oak_bees_0002");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> H = FeatureUtils.a("fancy_oak_bees_002");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> I = FeatureUtils.a("fancy_oak_bees_005");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> J = FeatureUtils.a("fancy_oak_bees");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> K = FeatureUtils.a("cherry_bees_005");

   private static WorldGenFeatureTreeConfiguration.a a(Block var0, Block var1, int var2, int var3, int var4, int var5) {
      return new WorldGenFeatureTreeConfiguration.a(
         WorldGenFeatureStateProvider.a(var0),
         new TrunkPlacerStraight(var2, var3, var4),
         WorldGenFeatureStateProvider.a(var1),
         new WorldGenFoilagePlacerBlob(ConstantInt.a(var5), ConstantInt.a(0), 3),
         new FeatureSizeTwoLayers(1, 0, 1)
      );
   }

   private static WorldGenFeatureTreeConfiguration.a a() {
      return a(Blocks.T, Blocks.aD, 4, 2, 0, 2).a();
   }

   private static WorldGenFeatureTreeConfiguration.a b() {
      return a(Blocks.V, Blocks.aF, 5, 2, 0, 2).a();
   }

   private static WorldGenFeatureTreeConfiguration.a c() {
      return a(Blocks.V, Blocks.aF, 5, 2, 6, 2).a();
   }

   private static WorldGenFeatureTreeConfiguration.a d() {
      return a(Blocks.W, Blocks.aG, 4, 8, 0, 2);
   }

   private static WorldGenFeatureTreeConfiguration.a e() {
      return new WorldGenFeatureTreeConfiguration.a(
            WorldGenFeatureStateProvider.a(Blocks.T),
            new TrunkPlacerFancy(3, 11, 0),
            WorldGenFeatureStateProvider.a(Blocks.aD),
            new WorldGenFoilagePlacerFancy(ConstantInt.a(2), ConstantInt.a(4), 4),
            new FeatureSizeTwoLayers(0, 0, 0, OptionalInt.of(4))
         )
         .a();
   }

   private static WorldGenFeatureTreeConfiguration.a f() {
      return new WorldGenFeatureTreeConfiguration.a(
            WorldGenFeatureStateProvider.a(Blocks.Y),
            new CherryTrunkPlacer(
               7,
               1,
               0,
               new WeightedListInt(SimpleWeightedRandomList.<IntProvider>a().a(ConstantInt.a(1), 1).a(ConstantInt.a(2), 1).a(ConstantInt.a(3), 1).a()),
               UniformInt.a(2, 4),
               UniformInt.a(-4, -3),
               UniformInt.a(-1, 0)
            ),
            WorldGenFeatureStateProvider.a(Blocks.aI),
            new CherryFoliagePlacer(ConstantInt.a(4), ConstantInt.a(0), ConstantInt.a(5), 0.25F, 0.5F, 0.16666667F, 0.33333334F),
            new FeatureSizeTwoLayers(1, 0, 2)
         )
         .a();
   }

   public static void a(BootstapContext<WorldGenFeatureConfigured<?, ?>> var0) {
      HolderGetter<Block> var1 = var0.a(Registries.e);
      FeatureUtils.a(var0, a, WorldGenerator.V, new WorldGenFeatureHugeFungiConfiguration(Blocks.os.o(), Blocks.oo.o(), Blocks.kH.o(), Blocks.ou.o(), false));
      FeatureUtils.a(var0, b, WorldGenerator.V, new WorldGenFeatureHugeFungiConfiguration(Blocks.os.o(), Blocks.oo.o(), Blocks.kH.o(), Blocks.ou.o(), true));
      FeatureUtils.a(var0, c, WorldGenerator.V, new WorldGenFeatureHugeFungiConfiguration(Blocks.oj.o(), Blocks.of.o(), Blocks.ol.o(), Blocks.ou.o(), false));
      FeatureUtils.a(var0, d, WorldGenerator.V, new WorldGenFeatureHugeFungiConfiguration(Blocks.oj.o(), Blocks.of.o(), Blocks.ol.o(), Blocks.ou.o(), true));
      FeatureUtils.a(
         var0,
         e,
         WorldGenerator.s,
         new WorldGenFeatureMushroomConfiguration(
            WorldGenFeatureStateProvider.a(Blocks.eT.o().a(BlockHugeMushroom.e, Boolean.valueOf(true)).a(BlockHugeMushroom.f, Boolean.valueOf(false))),
            WorldGenFeatureStateProvider.a(Blocks.eV.o().a(BlockHugeMushroom.e, Boolean.valueOf(false)).a(BlockHugeMushroom.f, Boolean.valueOf(false))),
            3
         )
      );
      FeatureUtils.a(
         var0,
         f,
         WorldGenerator.r,
         new WorldGenFeatureMushroomConfiguration(
            WorldGenFeatureStateProvider.a(Blocks.eU.o().a(BlockHugeMushroom.f, Boolean.valueOf(false))),
            WorldGenFeatureStateProvider.a(Blocks.eV.o().a(BlockHugeMushroom.e, Boolean.valueOf(false)).a(BlockHugeMushroom.f, Boolean.valueOf(false))),
            2
         )
      );
      WorldGenFeatureTreeBeehive var2 = new WorldGenFeatureTreeBeehive(0.002F);
      WorldGenFeatureTreeBeehive var3 = new WorldGenFeatureTreeBeehive(0.01F);
      WorldGenFeatureTreeBeehive var4 = new WorldGenFeatureTreeBeehive(0.02F);
      WorldGenFeatureTreeBeehive var5 = new WorldGenFeatureTreeBeehive(0.05F);
      WorldGenFeatureTreeBeehive var6 = new WorldGenFeatureTreeBeehive(1.0F);
      FeatureUtils.a(var0, g, WorldGenerator.g, a().c());
      FeatureUtils.a(
         var0,
         h,
         WorldGenerator.g,
         new WorldGenFeatureTreeConfiguration.a(
               WorldGenFeatureStateProvider.a(Blocks.Z),
               new TrunkPlacerDarkOak(6, 2, 1),
               WorldGenFeatureStateProvider.a(Blocks.aJ),
               new WorldGenFoilagePlacerDarkOak(ConstantInt.a(0), ConstantInt.a(0)),
               new FeatureSizeThreeLayers(1, 1, 0, 1, 2, OptionalInt.empty())
            )
            .a()
            .c()
      );
      FeatureUtils.a(var0, i, WorldGenerator.g, b().c());
      FeatureUtils.a(
         var0,
         j,
         WorldGenerator.g,
         new WorldGenFeatureTreeConfiguration.a(
               WorldGenFeatureStateProvider.a(Blocks.X),
               new TrunkPlacerForking(5, 2, 2),
               WorldGenFeatureStateProvider.a(Blocks.aH),
               new WorldGenFoilagePlacerAcacia(ConstantInt.a(2), ConstantInt.a(0)),
               new FeatureSizeTwoLayers(1, 0, 2)
            )
            .a()
            .c()
      );
      FeatureUtils.a(var0, z, WorldGenerator.g, f().c());
      FeatureUtils.a(var0, K, WorldGenerator.g, f().a(List.of(var5)).c());
      FeatureUtils.a(
         var0,
         k,
         WorldGenerator.g,
         new WorldGenFeatureTreeConfiguration.a(
               WorldGenFeatureStateProvider.a(Blocks.U),
               new TrunkPlacerStraight(5, 2, 1),
               WorldGenFeatureStateProvider.a(Blocks.aE),
               new WorldGenFoilagePlacerSpruce(UniformInt.a(2, 3), UniformInt.a(0, 2), UniformInt.a(1, 2)),
               new FeatureSizeTwoLayers(2, 0, 2)
            )
            .a()
            .c()
      );
      FeatureUtils.a(
         var0,
         l,
         WorldGenerator.g,
         new WorldGenFeatureTreeConfiguration.a(
               WorldGenFeatureStateProvider.a(Blocks.U),
               new TrunkPlacerStraight(6, 4, 0),
               WorldGenFeatureStateProvider.a(Blocks.aE),
               new WorldGenFoilagePlacerPine(ConstantInt.a(1), ConstantInt.a(1), UniformInt.a(3, 4)),
               new FeatureSizeTwoLayers(2, 0, 2)
            )
            .a()
            .c()
      );
      FeatureUtils.a(
         var0,
         m,
         WorldGenerator.g,
         d().a(ImmutableList.of(new WorldGenFeatureTreeCocoa(0.2F), WorldGenFeatureTreeVineTrunk.b, new WorldGenFeatureTreeVineLeaves(0.25F))).a().c()
      );
      FeatureUtils.a(var0, n, WorldGenerator.g, e().c());
      FeatureUtils.a(var0, o, WorldGenerator.g, d().a().c());
      FeatureUtils.a(
         var0,
         p,
         WorldGenerator.g,
         new WorldGenFeatureTreeConfiguration.a(
               WorldGenFeatureStateProvider.a(Blocks.W),
               new TrunkPlacerMegaJungle(10, 2, 19),
               WorldGenFeatureStateProvider.a(Blocks.aG),
               new WorldGenFoilagePlacerJungle(ConstantInt.a(2), ConstantInt.a(0), 2),
               new FeatureSizeTwoLayers(1, 1, 2)
            )
            .a(ImmutableList.of(WorldGenFeatureTreeVineTrunk.b, new WorldGenFeatureTreeVineLeaves(0.25F)))
            .c()
      );
      FeatureUtils.a(
         var0,
         q,
         WorldGenerator.g,
         new WorldGenFeatureTreeConfiguration.a(
               WorldGenFeatureStateProvider.a(Blocks.U),
               new TrunkPlacerGiant(13, 2, 14),
               WorldGenFeatureStateProvider.a(Blocks.aE),
               new WorldGenFoilagePlacerMegaPine(ConstantInt.a(0), ConstantInt.a(0), UniformInt.a(13, 17)),
               new FeatureSizeTwoLayers(1, 1, 2)
            )
            .a(ImmutableList.of(new WorldGenFeatureTreeAlterGround(WorldGenFeatureStateProvider.a(Blocks.l))))
            .c()
      );
      FeatureUtils.a(
         var0,
         r,
         WorldGenerator.g,
         new WorldGenFeatureTreeConfiguration.a(
               WorldGenFeatureStateProvider.a(Blocks.U),
               new TrunkPlacerGiant(13, 2, 14),
               WorldGenFeatureStateProvider.a(Blocks.aE),
               new WorldGenFoilagePlacerMegaPine(ConstantInt.a(0), ConstantInt.a(0), UniformInt.a(3, 7)),
               new FeatureSizeTwoLayers(1, 1, 2)
            )
            .a(ImmutableList.of(new WorldGenFeatureTreeAlterGround(WorldGenFeatureStateProvider.a(Blocks.l))))
            .c()
      );
      FeatureUtils.a(var0, s, WorldGenerator.g, c().a(ImmutableList.of(var2)).c());
      FeatureUtils.a(var0, t, WorldGenerator.g, c().a(ImmutableList.of(var6)).c());
      FeatureUtils.a(var0, u, WorldGenerator.g, a(Blocks.T, Blocks.aD, 5, 3, 0, 3).a(ImmutableList.of(new WorldGenFeatureTreeVineLeaves(0.25F))).c());
      FeatureUtils.a(
         var0,
         v,
         WorldGenerator.g,
         new WorldGenFeatureTreeConfiguration.a(
               WorldGenFeatureStateProvider.a(Blocks.W),
               new TrunkPlacerStraight(1, 0, 0),
               WorldGenFeatureStateProvider.a(Blocks.aD),
               new WorldGenFoilagePlacerBush(ConstantInt.a(2), ConstantInt.a(1), 2),
               new FeatureSizeTwoLayers(0, 0, 0)
            )
            .c()
      );
      FeatureUtils.a(
         var0,
         w,
         WorldGenerator.g,
         new WorldGenFeatureTreeConfiguration.a(
               WorldGenFeatureStateProvider.a(Blocks.T),
               new BendingTrunkPlacer(4, 2, 0, 3, UniformInt.a(1, 2)),
               new WorldGenFeatureStateProviderWeighted(SimpleWeightedRandomList.<IBlockData>a().a(Blocks.aL.o(), 3).a(Blocks.aM.o(), 1)),
               new RandomSpreadFoliagePlacer(ConstantInt.a(3), ConstantInt.a(0), ConstantInt.a(2), 50),
               new FeatureSizeTwoLayers(1, 0, 1)
            )
            .a(WorldGenFeatureStateProvider.a(Blocks.rB))
            .b()
            .c()
      );
      FeatureUtils.a(
         var0,
         x,
         WorldGenerator.g,
         new WorldGenFeatureTreeConfiguration.a(
               WorldGenFeatureStateProvider.a(Blocks.aa),
               new UpwardsBranchingTrunkPlacer(2, 1, 4, UniformInt.a(1, 4), 0.5F, UniformInt.a(0, 1), var1.b(TagsBlock.bW)),
               WorldGenFeatureStateProvider.a(Blocks.aK),
               new RandomSpreadFoliagePlacer(ConstantInt.a(3), ConstantInt.a(0), ConstantInt.a(2), 70),
               Optional.of(
                  new MangroveRootPlacer(
                     UniformInt.a(1, 3),
                     WorldGenFeatureStateProvider.a(Blocks.ab),
                     Optional.of(new AboveRootPlacement(WorldGenFeatureStateProvider.a(Blocks.ru), 0.5F)),
                     new MangroveRootPlacement(
                        var1.b(TagsBlock.bX), HolderSet.a(Block::r, Blocks.rC, Blocks.ac), WorldGenFeatureStateProvider.a(Blocks.ac), 8, 15, 0.2F
                     )
                  )
               ),
               new FeatureSizeTwoLayers(2, 0, 2)
            )
            .a(
               List.of(
                  new WorldGenFeatureTreeVineLeaves(0.125F),
                  new AttachedToLeavesDecorator(
                     0.14F,
                     1,
                     0,
                     new RandomizedIntStateProvider(
                        WorldGenFeatureStateProvider.a(Blocks.E.o().a(MangrovePropaguleBlock.c, Boolean.valueOf(true))),
                        MangrovePropaguleBlock.a,
                        UniformInt.a(0, 4)
                     ),
                     2,
                     List.of(EnumDirection.a)
                  ),
                  var3
               )
            )
            .a()
            .c()
      );
      FeatureUtils.a(
         var0,
         y,
         WorldGenerator.g,
         new WorldGenFeatureTreeConfiguration.a(
               WorldGenFeatureStateProvider.a(Blocks.aa),
               new UpwardsBranchingTrunkPlacer(4, 1, 9, UniformInt.a(1, 6), 0.5F, UniformInt.a(0, 1), var1.b(TagsBlock.bW)),
               WorldGenFeatureStateProvider.a(Blocks.aK),
               new RandomSpreadFoliagePlacer(ConstantInt.a(3), ConstantInt.a(0), ConstantInt.a(2), 70),
               Optional.of(
                  new MangroveRootPlacer(
                     UniformInt.a(3, 7),
                     WorldGenFeatureStateProvider.a(Blocks.ab),
                     Optional.of(new AboveRootPlacement(WorldGenFeatureStateProvider.a(Blocks.ru), 0.5F)),
                     new MangroveRootPlacement(
                        var1.b(TagsBlock.bX), HolderSet.a(Block::r, Blocks.rC, Blocks.ac), WorldGenFeatureStateProvider.a(Blocks.ac), 8, 15, 0.2F
                     )
                  )
               ),
               new FeatureSizeTwoLayers(3, 0, 2)
            )
            .a(
               List.of(
                  new WorldGenFeatureTreeVineLeaves(0.125F),
                  new AttachedToLeavesDecorator(
                     0.14F,
                     1,
                     0,
                     new RandomizedIntStateProvider(
                        WorldGenFeatureStateProvider.a(Blocks.E.o().a(MangrovePropaguleBlock.c, Boolean.valueOf(true))),
                        MangrovePropaguleBlock.a,
                        UniformInt.a(0, 4)
                     ),
                     2,
                     List.of(EnumDirection.a)
                  ),
                  var3
               )
            )
            .a()
            .c()
      );
      FeatureUtils.a(var0, A, WorldGenerator.g, a().a(List.of(var2)).c());
      FeatureUtils.a(var0, B, WorldGenerator.g, a().a(List.of(var4)).c());
      FeatureUtils.a(var0, C, WorldGenerator.g, a().a(List.of(var5)).c());
      FeatureUtils.a(var0, D, WorldGenerator.g, b().a(List.of(var2)).c());
      FeatureUtils.a(var0, E, WorldGenerator.g, b().a(List.of(var4)).c());
      FeatureUtils.a(var0, F, WorldGenerator.g, b().a(List.of(var5)).c());
      FeatureUtils.a(var0, G, WorldGenerator.g, e().a(List.of(var2)).c());
      FeatureUtils.a(var0, H, WorldGenerator.g, e().a(List.of(var4)).c());
      FeatureUtils.a(var0, I, WorldGenerator.g, e().a(List.of(var5)).c());
      FeatureUtils.a(var0, J, WorldGenerator.g, e().a(List.of(var6)).c());
   }
}
