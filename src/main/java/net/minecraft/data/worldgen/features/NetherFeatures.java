package net.minecraft.data.worldgen.features;

import java.util.List;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.feature.WorldGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.NetherForestVegetationConfig;
import net.minecraft.world.level.levelgen.feature.configurations.TwistingVinesConfig;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureBasaltColumnsConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureDeltaConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureHellFlowingLavaConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureRadiusConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.WorldGenFeatureStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WorldGenFeatureStateProviderWeighted;
import net.minecraft.world.level.material.FluidTypes;

public class NetherFeatures {
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> a = FeatureUtils.a("delta");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> b = FeatureUtils.a("small_basalt_columns");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> c = FeatureUtils.a("large_basalt_columns");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> d = FeatureUtils.a("basalt_blobs");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> e = FeatureUtils.a("blackstone_blobs");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> f = FeatureUtils.a("glowstone_extra");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> g = FeatureUtils.a("crimson_forest_vegetation");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> h = FeatureUtils.a("crimson_forest_vegetation_bonemeal");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> i = FeatureUtils.a("warped_forest_vegetation");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> j = FeatureUtils.a("warped_forest_vegetation_bonemeal");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> k = FeatureUtils.a("nether_sprouts");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> l = FeatureUtils.a("nether_sprouts_bonemeal");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> m = FeatureUtils.a("twisting_vines");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> n = FeatureUtils.a("twisting_vines_bonemeal");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> o = FeatureUtils.a("weeping_vines");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> p = FeatureUtils.a("patch_crimson_roots");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> q = FeatureUtils.a("basalt_pillar");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> r = FeatureUtils.a("spring_lava_nether");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> s = FeatureUtils.a("spring_nether_closed");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> t = FeatureUtils.a("spring_nether_open");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> u = FeatureUtils.a("patch_fire");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> v = FeatureUtils.a("patch_soul_fire");

   public static void a(BootstapContext<WorldGenFeatureConfigured<?, ?>> var0) {
      FeatureUtils.a(var0, a, WorldGenerator.aa, new WorldGenFeatureDeltaConfiguration(Blocks.H.o(), Blocks.kG.o(), UniformInt.a(3, 7), UniformInt.a(0, 2)));
      FeatureUtils.a(var0, b, WorldGenerator.Z, new WorldGenFeatureBasaltColumnsConfiguration(ConstantInt.a(1), UniformInt.a(1, 4)));
      FeatureUtils.a(var0, c, WorldGenerator.Z, new WorldGenFeatureBasaltColumnsConfiguration(UniformInt.a(2, 3), UniformInt.a(5, 10)));
      FeatureUtils.a(var0, d, WorldGenerator.ab, new WorldGenFeatureRadiusConfiguration(Blocks.dV.o(), Blocks.dY.o(), UniformInt.a(3, 7)));
      FeatureUtils.a(var0, e, WorldGenerator.ab, new WorldGenFeatureRadiusConfiguration(Blocks.dV.o(), Blocks.pn.o(), UniformInt.a(3, 7)));
      FeatureUtils.a(var0, f, WorldGenerator.u);
      WorldGenFeatureStateProviderWeighted var1 = new WorldGenFeatureStateProviderWeighted(
         SimpleWeightedRandomList.<IBlockData>a().a(Blocks.oz.o(), 87).a(Blocks.ot.o(), 11).a(Blocks.ok.o(), 1)
      );
      FeatureUtils.a(var0, g, WorldGenerator.W, new NetherForestVegetationConfig(var1, 8, 4));
      FeatureUtils.a(var0, h, WorldGenerator.W, new NetherForestVegetationConfig(var1, 3, 1));
      WorldGenFeatureStateProviderWeighted var2 = new WorldGenFeatureStateProviderWeighted(
         SimpleWeightedRandomList.<IBlockData>a().a(Blocks.om.o(), 85).a(Blocks.oz.o(), 1).a(Blocks.ok.o(), 13).a(Blocks.ot.o(), 1)
      );
      FeatureUtils.a(var0, i, WorldGenerator.W, new NetherForestVegetationConfig(var2, 8, 4));
      FeatureUtils.a(var0, j, WorldGenerator.W, new NetherForestVegetationConfig(var2, 3, 1));
      FeatureUtils.a(var0, k, WorldGenerator.W, new NetherForestVegetationConfig(WorldGenFeatureStateProvider.a(Blocks.on), 8, 4));
      FeatureUtils.a(var0, l, WorldGenerator.W, new NetherForestVegetationConfig(WorldGenFeatureStateProvider.a(Blocks.on), 3, 1));
      FeatureUtils.a(var0, m, WorldGenerator.Y, new TwistingVinesConfig(8, 4, 8));
      FeatureUtils.a(var0, n, WorldGenerator.Y, new TwistingVinesConfig(3, 1, 2));
      FeatureUtils.a(var0, o, WorldGenerator.X);
      FeatureUtils.a(
         var0, p, WorldGenerator.j, FeatureUtils.a(WorldGenerator.T, new WorldGenFeatureBlockConfiguration(WorldGenFeatureStateProvider.a(Blocks.oz)))
      );
      FeatureUtils.a(var0, q, WorldGenerator.ae);
      FeatureUtils.a(
         var0,
         r,
         WorldGenerator.l,
         new WorldGenFeatureHellFlowingLavaConfiguration(
            FluidTypes.e.g(), true, 4, 1, HolderSet.a(Block::r, Blocks.dV, Blocks.dW, Blocks.L, Blocks.kG, Blocks.pn)
         )
      );
      FeatureUtils.a(
         var0, s, WorldGenerator.l, new WorldGenFeatureHellFlowingLavaConfiguration(FluidTypes.e.g(), false, 5, 0, HolderSet.a(Block::r, Blocks.dV))
      );
      FeatureUtils.a(
         var0, t, WorldGenerator.l, new WorldGenFeatureHellFlowingLavaConfiguration(FluidTypes.e.g(), false, 4, 1, HolderSet.a(Block::r, Blocks.dV))
      );
      FeatureUtils.a(
         var0,
         u,
         WorldGenerator.j,
         FeatureUtils.a(WorldGenerator.T, new WorldGenFeatureBlockConfiguration(WorldGenFeatureStateProvider.a(Blocks.cq)), List.of(Blocks.dV))
      );
      FeatureUtils.a(
         var0,
         v,
         WorldGenerator.j,
         FeatureUtils.a(WorldGenerator.T, new WorldGenFeatureBlockConfiguration(WorldGenFeatureStateProvider.a(Blocks.cr)), List.of(Blocks.dX))
      );
   }
}
