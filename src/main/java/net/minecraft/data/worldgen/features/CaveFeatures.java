package net.minecraft.data.worldgen.features;

import java.util.List;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.ProcessorLists;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ClampedNormalFloat;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CaveVines;
import net.minecraft.world.level.block.CaveVinesBlock;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.SmallDripleafBlock;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.FossilFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.feature.WorldGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.DripstoneClusterConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.LargeDripstoneConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.MultifaceGrowthConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.PointedDripstoneConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RootSystemConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SculkPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.UnderwaterMagmaConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureChoiceConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureRandom2;
import net.minecraft.world.level.levelgen.feature.stateproviders.RandomizedIntStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WorldGenFeatureStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WorldGenFeatureStateProviderWeighted;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorList;

public class CaveFeatures {
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> a = FeatureUtils.a("monster_room");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> b = FeatureUtils.a("fossil_coal");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> c = FeatureUtils.a("fossil_diamonds");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> d = FeatureUtils.a("dripstone_cluster");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> e = FeatureUtils.a("large_dripstone");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> f = FeatureUtils.a("pointed_dripstone");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> g = FeatureUtils.a("underwater_magma");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> h = FeatureUtils.a("glow_lichen");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> i = FeatureUtils.a("rooted_azalea_tree");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> j = FeatureUtils.a("cave_vine");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> k = FeatureUtils.a("cave_vine_in_moss");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> l = FeatureUtils.a("moss_vegetation");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> m = FeatureUtils.a("moss_patch");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> n = FeatureUtils.a("moss_patch_bonemeal");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> o = FeatureUtils.a("dripleaf");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> p = FeatureUtils.a("clay_with_dripleaves");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> q = FeatureUtils.a("clay_pool_with_dripleaves");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> r = FeatureUtils.a("lush_caves_clay");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> s = FeatureUtils.a("moss_patch_ceiling");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> t = FeatureUtils.a("spore_blossom");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> u = FeatureUtils.a("amethyst_geode");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> v = FeatureUtils.a("sculk_patch_deep_dark");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> w = FeatureUtils.a("sculk_patch_ancient_city");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> x = FeatureUtils.a("sculk_vein");

   private static Holder<PlacedFeature> a(EnumDirection var0) {
      return PlacementUtils.a(
         WorldGenerator.x,
         new BlockColumnConfiguration(
            List.of(
               BlockColumnConfiguration.a(
                  new WeightedListInt(SimpleWeightedRandomList.<IntProvider>a().a(UniformInt.a(0, 4), 2).a(ConstantInt.a(0), 1).a()),
                  WorldGenFeatureStateProvider.a(Blocks.ry.o().a(BlockProperties.R, var0))
               ),
               BlockColumnConfiguration.a(ConstantInt.a(1), WorldGenFeatureStateProvider.a(Blocks.rx.o().a(BlockProperties.R, var0)))
            ),
            EnumDirection.b,
            BlockPredicate.d,
            true
         )
      );
   }

   private static Holder<PlacedFeature> a() {
      return PlacementUtils.a(
         WorldGenerator.T,
         new WorldGenFeatureBlockConfiguration(
            new WorldGenFeatureStateProviderWeighted(
               SimpleWeightedRandomList.<IBlockData>a()
                  .a(Blocks.rz.o().a(SmallDripleafBlock.b, EnumDirection.f), 1)
                  .a(Blocks.rz.o().a(SmallDripleafBlock.b, EnumDirection.e), 1)
                  .a(Blocks.rz.o().a(SmallDripleafBlock.b, EnumDirection.c), 1)
                  .a(Blocks.rz.o().a(SmallDripleafBlock.b, EnumDirection.d), 1)
            )
         )
      );
   }

   public static void a(BootstapContext<WorldGenFeatureConfigured<?, ?>> var0) {
      HolderGetter<WorldGenFeatureConfigured<?, ?>> var1 = var0.a(Registries.aq);
      HolderGetter<ProcessorList> var2 = var0.a(Registries.ay);
      FeatureUtils.a(var0, a, WorldGenerator.D);
      List<MinecraftKey> var3 = List.of(
         new MinecraftKey("fossil/spine_1"),
         new MinecraftKey("fossil/spine_2"),
         new MinecraftKey("fossil/spine_3"),
         new MinecraftKey("fossil/spine_4"),
         new MinecraftKey("fossil/skull_1"),
         new MinecraftKey("fossil/skull_2"),
         new MinecraftKey("fossil/skull_3"),
         new MinecraftKey("fossil/skull_4")
      );
      List<MinecraftKey> var4 = List.of(
         new MinecraftKey("fossil/spine_1_coal"),
         new MinecraftKey("fossil/spine_2_coal"),
         new MinecraftKey("fossil/spine_3_coal"),
         new MinecraftKey("fossil/spine_4_coal"),
         new MinecraftKey("fossil/skull_1_coal"),
         new MinecraftKey("fossil/skull_2_coal"),
         new MinecraftKey("fossil/skull_3_coal"),
         new MinecraftKey("fossil/skull_4_coal")
      );
      Holder<ProcessorList> var5 = var2.b(ProcessorLists.D);
      FeatureUtils.a(var0, b, WorldGenerator.q, new FossilFeatureConfiguration(var3, var4, var5, var2.b(ProcessorLists.E), 4));
      FeatureUtils.a(var0, c, WorldGenerator.q, new FossilFeatureConfiguration(var3, var4, var5, var2.b(ProcessorLists.F), 4));
      FeatureUtils.a(
         var0,
         d,
         WorldGenerator.ak,
         new DripstoneClusterConfiguration(
            12,
            UniformInt.a(3, 6),
            UniformInt.a(2, 8),
            1,
            3,
            UniformInt.a(2, 4),
            UniformFloat.b(0.3F, 0.7F),
            ClampedNormalFloat.a(0.1F, 0.3F, 0.1F, 0.9F),
            0.1F,
            3,
            8
         )
      );
      FeatureUtils.a(
         var0,
         e,
         WorldGenerator.al,
         new LargeDripstoneConfiguration(
            30,
            UniformInt.a(3, 19),
            UniformFloat.b(0.4F, 2.0F),
            0.33F,
            UniformFloat.b(0.3F, 0.9F),
            UniformFloat.b(0.4F, 1.0F),
            UniformFloat.b(0.0F, 0.3F),
            4,
            0.6F
         )
      );
      FeatureUtils.a(
         var0,
         f,
         WorldGenerator.ah,
         new WorldGenFeatureRandom2(
            HolderSet.a(
               PlacementUtils.a(
                  WorldGenerator.am,
                  new PointedDripstoneConfiguration(0.2F, 0.7F, 0.5F, 0.5F),
                  EnvironmentScanPlacement.a(EnumDirection.a, BlockPredicate.c(), BlockPredicate.d, 12),
                  RandomOffsetPlacement.a(ConstantInt.a(1))
               ),
               PlacementUtils.a(
                  WorldGenerator.am,
                  new PointedDripstoneConfiguration(0.2F, 0.7F, 0.5F, 0.5F),
                  EnvironmentScanPlacement.a(EnumDirection.b, BlockPredicate.c(), BlockPredicate.d, 12),
                  RandomOffsetPlacement.a(ConstantInt.a(-1))
               )
            )
         )
      );
      FeatureUtils.a(var0, g, WorldGenerator.C, new UnderwaterMagmaConfiguration(5, 1, 0.5F));
      MultifaceBlock var6 = (MultifaceBlock)Blocks.ff;
      FeatureUtils.a(
         var0,
         h,
         WorldGenerator.B,
         new MultifaceGrowthConfiguration(
            var6, 20, false, true, true, 0.5F, HolderSet.a(Block::r, Blocks.b, Blocks.g, Blocks.e, Blocks.c, Blocks.ro, Blocks.qw, Blocks.qv, Blocks.rD)
         )
      );
      FeatureUtils.a(
         var0,
         i,
         WorldGenerator.A,
         new RootSystemConfiguration(
            PlacementUtils.a(var1.b(TreeFeatures.w)),
            3,
            3,
            TagsBlock.br,
            WorldGenFeatureStateProvider.a(Blocks.rB),
            20,
            100,
            3,
            2,
            WorldGenFeatureStateProvider.a(Blocks.rA),
            20,
            2,
            BlockPredicate.a(
               BlockPredicate.b(
                  BlockPredicate.c(List.of(Blocks.a, Blocks.mY, Blocks.mX, Blocks.G)), BlockPredicate.a(TagsBlock.N), BlockPredicate.a(TagsBlock.bU)
               ),
               BlockPredicate.a(EnumDirection.a.q(), TagsBlock.bT)
            )
         )
      );
      WorldGenFeatureStateProviderWeighted var7 = new WorldGenFeatureStateProviderWeighted(
         SimpleWeightedRandomList.<IBlockData>a().a(Blocks.rq.o(), 4).a(Blocks.rq.o().a(CaveVines.q_, Boolean.valueOf(true)), 1)
      );
      RandomizedIntStateProvider var8 = new RandomizedIntStateProvider(
         new WorldGenFeatureStateProviderWeighted(
            SimpleWeightedRandomList.<IBlockData>a().a(Blocks.rp.o(), 4).a(Blocks.rp.o().a(CaveVines.q_, Boolean.valueOf(true)), 1)
         ),
         CaveVinesBlock.d,
         UniformInt.a(23, 25)
      );
      FeatureUtils.a(
         var0,
         j,
         WorldGenerator.x,
         new BlockColumnConfiguration(
            List.of(
               BlockColumnConfiguration.a(
                  new WeightedListInt(
                     SimpleWeightedRandomList.<IntProvider>a().a(UniformInt.a(0, 19), 2).a(UniformInt.a(0, 2), 3).a(UniformInt.a(0, 6), 10).a()
                  ),
                  var7
               ),
               BlockColumnConfiguration.a(ConstantInt.a(1), var8)
            ),
            EnumDirection.a,
            BlockPredicate.c,
            true
         )
      );
      FeatureUtils.a(
         var0,
         k,
         WorldGenerator.x,
         new BlockColumnConfiguration(
            List.of(
               BlockColumnConfiguration.a(
                  new WeightedListInt(SimpleWeightedRandomList.<IntProvider>a().a(UniformInt.a(0, 3), 5).a(UniformInt.a(1, 7), 1).a()), var7
               ),
               BlockColumnConfiguration.a(ConstantInt.a(1), var8)
            ),
            EnumDirection.a,
            BlockPredicate.c,
            true
         )
      );
      FeatureUtils.a(
         var0,
         l,
         WorldGenerator.T,
         new WorldGenFeatureBlockConfiguration(
            new WorldGenFeatureStateProviderWeighted(
               SimpleWeightedRandomList.<IBlockData>a().a(Blocks.rt.o(), 4).a(Blocks.rs.o(), 7).a(Blocks.ru.o(), 25).a(Blocks.bs.o(), 50).a(Blocks.iG.o(), 10)
            )
         )
      );
      FeatureUtils.a(
         var0,
         m,
         WorldGenerator.y,
         new VegetationPatchConfiguration(
            TagsBlock.bp,
            WorldGenFeatureStateProvider.a(Blocks.rw),
            PlacementUtils.a(var1.b(l)),
            CaveSurface.b,
            ConstantInt.a(1),
            0.0F,
            5,
            0.8F,
            UniformInt.a(4, 7),
            0.3F
         )
      );
      FeatureUtils.a(
         var0,
         n,
         WorldGenerator.y,
         new VegetationPatchConfiguration(
            TagsBlock.bp,
            WorldGenFeatureStateProvider.a(Blocks.rw),
            PlacementUtils.a(var1.b(l)),
            CaveSurface.b,
            ConstantInt.a(1),
            0.0F,
            5,
            0.6F,
            UniformInt.a(1, 2),
            0.75F
         )
      );
      FeatureUtils.a(
         var0,
         o,
         WorldGenerator.ah,
         new WorldGenFeatureRandom2(HolderSet.a(a(), a(EnumDirection.f), a(EnumDirection.e), a(EnumDirection.d), a(EnumDirection.c)))
      );
      FeatureUtils.a(
         var0,
         p,
         WorldGenerator.y,
         new VegetationPatchConfiguration(
            TagsBlock.bq,
            WorldGenFeatureStateProvider.a(Blocks.dQ),
            PlacementUtils.a(var1.b(o)),
            CaveSurface.b,
            ConstantInt.a(3),
            0.8F,
            2,
            0.05F,
            UniformInt.a(4, 7),
            0.7F
         )
      );
      FeatureUtils.a(
         var0,
         q,
         WorldGenerator.z,
         new VegetationPatchConfiguration(
            TagsBlock.bq,
            WorldGenFeatureStateProvider.a(Blocks.dQ),
            PlacementUtils.a(var1.b(o)),
            CaveSurface.b,
            ConstantInt.a(3),
            0.8F,
            5,
            0.1F,
            UniformInt.a(4, 7),
            0.7F
         )
      );
      FeatureUtils.a(var0, r, WorldGenerator.ai, new WorldGenFeatureChoiceConfiguration(PlacementUtils.a(var1.b(p)), PlacementUtils.a(var1.b(q))));
      FeatureUtils.a(
         var0,
         s,
         WorldGenerator.y,
         new VegetationPatchConfiguration(
            TagsBlock.bp,
            WorldGenFeatureStateProvider.a(Blocks.rw),
            PlacementUtils.a(var1.b(k)),
            CaveSurface.a,
            UniformInt.a(1, 2),
            0.0F,
            5,
            0.08F,
            UniformInt.a(4, 7),
            0.3F
         )
      );
      FeatureUtils.a(var0, t, WorldGenerator.T, new WorldGenFeatureBlockConfiguration(WorldGenFeatureStateProvider.a(Blocks.rr)));
      FeatureUtils.a(
         var0,
         u,
         WorldGenerator.aj,
         new GeodeConfiguration(
            new GeodeBlockSettings(
               WorldGenFeatureStateProvider.a(Blocks.a),
               WorldGenFeatureStateProvider.a(Blocks.qp),
               WorldGenFeatureStateProvider.a(Blocks.qq),
               WorldGenFeatureStateProvider.a(Blocks.qw),
               WorldGenFeatureStateProvider.a(Blocks.rY),
               List.of(Blocks.qu.o(), Blocks.qt.o(), Blocks.qs.o(), Blocks.qr.o()),
               TagsBlock.bC,
               TagsBlock.bE
            ),
            new GeodeLayerSettings(1.7, 2.2, 3.2, 4.2),
            new GeodeCrackSettings(0.95, 2.0, 2),
            0.35,
            0.083,
            true,
            UniformInt.a(4, 6),
            UniformInt.a(3, 4),
            UniformInt.a(1, 2),
            -16,
            16,
            0.05,
            1
         )
      );
      FeatureUtils.a(var0, v, WorldGenerator.an, new SculkPatchConfiguration(10, 32, 64, 0, 1, ConstantInt.a(0), 0.5F));
      FeatureUtils.a(var0, w, WorldGenerator.an, new SculkPatchConfiguration(10, 32, 64, 0, 1, UniformInt.a(1, 3), 0.5F));
      MultifaceBlock var9 = (MultifaceBlock)Blocks.qB;
      FeatureUtils.a(
         var0,
         x,
         WorldGenerator.B,
         new MultifaceGrowthConfiguration(
            var9, 20, true, true, true, 1.0F, HolderSet.a(Block::r, Blocks.b, Blocks.g, Blocks.e, Blocks.c, Blocks.ro, Blocks.qw, Blocks.qv, Blocks.rD)
         )
      );
   }
}
