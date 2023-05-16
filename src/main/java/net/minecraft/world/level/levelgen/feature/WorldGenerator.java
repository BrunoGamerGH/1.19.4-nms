package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.IWorldWriter;
import net.minecraft.world.level.VirtualLevelReadable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.DripstoneClusterConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.LargeDripstoneConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.MultifaceGrowthConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NetherForestVegetationConfig;
import net.minecraft.world.level.levelgen.feature.configurations.PointedDripstoneConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RootSystemConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SculkPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TwistingVinesConfig;
import net.minecraft.world.level.levelgen.feature.configurations.UnderwaterMagmaConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenDecoratorFrequencyConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenEndGatewayConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureBasaltColumnsConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureBlockPileConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureChoiceConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureCircleConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureConfigurationChance;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureDeltaConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureEmptyConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureEndSpikeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureFillConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureHellFlowingLavaConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureLakeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureMushroomConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureOreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureRadiusConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureRandom2;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureRandomChoiceConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureRandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureReplaceBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureTreeConfiguration;

public abstract class WorldGenerator<FC extends WorldGenFeatureConfiguration> {
   public static final WorldGenerator<WorldGenFeatureEmptyConfiguration> f = a("no_op", new WorldGenFeatureEmpty(WorldGenFeatureEmptyConfiguration.a));
   public static final WorldGenerator<WorldGenFeatureTreeConfiguration> g = a("tree", new WorldGenTrees(WorldGenFeatureTreeConfiguration.a));
   public static final WorldGenerator<WorldGenFeatureRandomPatchConfiguration> h = a(
      "flower", new WorldGenFeatureRandomPatch(WorldGenFeatureRandomPatchConfiguration.a)
   );
   public static final WorldGenerator<WorldGenFeatureRandomPatchConfiguration> i = a(
      "no_bonemeal_flower", new WorldGenFeatureRandomPatch(WorldGenFeatureRandomPatchConfiguration.a)
   );
   public static final WorldGenerator<WorldGenFeatureRandomPatchConfiguration> j = a(
      "random_patch", new WorldGenFeatureRandomPatch(WorldGenFeatureRandomPatchConfiguration.a)
   );
   public static final WorldGenerator<WorldGenFeatureBlockPileConfiguration> k = a(
      "block_pile", new WorldGenFeatureBlockPile(WorldGenFeatureBlockPileConfiguration.a)
   );
   public static final WorldGenerator<WorldGenFeatureHellFlowingLavaConfiguration> l = a(
      "spring_feature", new WorldGenLiquids(WorldGenFeatureHellFlowingLavaConfiguration.a)
   );
   public static final WorldGenerator<WorldGenFeatureEmptyConfiguration> m = a(
      "chorus_plant", new WorldGenFeatureChorusPlant(WorldGenFeatureEmptyConfiguration.a)
   );
   public static final WorldGenerator<WorldGenFeatureReplaceBlockConfiguration> n = a(
      "replace_single_block", new WorldGenFeatureReplaceBlock(WorldGenFeatureReplaceBlockConfiguration.a)
   );
   public static final WorldGenerator<WorldGenFeatureEmptyConfiguration> o = a(
      "void_start_platform", new WorldGenFeatureEndPlatform(WorldGenFeatureEmptyConfiguration.a)
   );
   public static final WorldGenerator<WorldGenFeatureEmptyConfiguration> p = a("desert_well", new WorldGenDesertWell(WorldGenFeatureEmptyConfiguration.a));
   public static final WorldGenerator<FossilFeatureConfiguration> q = a("fossil", new WorldGenFossils(FossilFeatureConfiguration.a));
   public static final WorldGenerator<WorldGenFeatureMushroomConfiguration> r = a(
      "huge_red_mushroom", new WorldGenHugeMushroomRed(WorldGenFeatureMushroomConfiguration.a)
   );
   public static final WorldGenerator<WorldGenFeatureMushroomConfiguration> s = a(
      "huge_brown_mushroom", new WorldGenHugeMushroomBrown(WorldGenFeatureMushroomConfiguration.a)
   );
   public static final WorldGenerator<WorldGenFeatureEmptyConfiguration> t = a("ice_spike", new WorldGenPackedIce2(WorldGenFeatureEmptyConfiguration.a));
   public static final WorldGenerator<WorldGenFeatureEmptyConfiguration> u = a("glowstone_blob", new WorldGenLightStone1(WorldGenFeatureEmptyConfiguration.a));
   public static final WorldGenerator<WorldGenFeatureEmptyConfiguration> v = a(
      "freeze_top_layer", new WorldGenFeatureIceSnow(WorldGenFeatureEmptyConfiguration.a)
   );
   public static final WorldGenerator<WorldGenFeatureEmptyConfiguration> w = a("vines", new WorldGenVines(WorldGenFeatureEmptyConfiguration.a));
   public static final WorldGenerator<BlockColumnConfiguration> x = a("block_column", new BlockColumnFeature(BlockColumnConfiguration.a));
   public static final WorldGenerator<VegetationPatchConfiguration> y = a("vegetation_patch", new VegetationPatchFeature(VegetationPatchConfiguration.a));
   public static final WorldGenerator<VegetationPatchConfiguration> z = a(
      "waterlogged_vegetation_patch", new WaterloggedVegetationPatchFeature(VegetationPatchConfiguration.a)
   );
   public static final WorldGenerator<RootSystemConfiguration> A = a("root_system", new RootSystemFeature(RootSystemConfiguration.a));
   public static final WorldGenerator<MultifaceGrowthConfiguration> B = a("multiface_growth", new MultifaceGrowthFeature(MultifaceGrowthConfiguration.a));
   public static final WorldGenerator<UnderwaterMagmaConfiguration> C = a("underwater_magma", new UnderwaterMagmaFeature(UnderwaterMagmaConfiguration.a));
   public static final WorldGenerator<WorldGenFeatureEmptyConfiguration> D = a("monster_room", new WorldGenDungeons(WorldGenFeatureEmptyConfiguration.a));
   public static final WorldGenerator<WorldGenFeatureEmptyConfiguration> E = a("blue_ice", new WorldGenFeatureBlueIce(WorldGenFeatureEmptyConfiguration.a));
   public static final WorldGenerator<WorldGenFeatureLakeConfiguration> F = a("iceberg", new WorldGenFeatureIceburg(WorldGenFeatureLakeConfiguration.a));
   public static final WorldGenerator<WorldGenFeatureLakeConfiguration> G = a("forest_rock", new WorldGenTaigaStructure(WorldGenFeatureLakeConfiguration.a));
   public static final WorldGenerator<WorldGenFeatureCircleConfiguration> H = a("disk", new DiskFeature(WorldGenFeatureCircleConfiguration.a));
   public static final WorldGenerator<WorldGenLakes.a> I = a("lake", new WorldGenLakes(WorldGenLakes.a.a));
   public static final WorldGenerator<WorldGenFeatureOreConfiguration> J = a("ore", new WorldGenMinable(WorldGenFeatureOreConfiguration.a));
   public static final WorldGenerator<WorldGenFeatureEndSpikeConfiguration> K = a("end_spike", new WorldGenEnder(WorldGenFeatureEndSpikeConfiguration.a));
   public static final WorldGenerator<WorldGenFeatureEmptyConfiguration> L = a("end_island", new WorldGenEndIsland(WorldGenFeatureEmptyConfiguration.a));
   public static final WorldGenerator<WorldGenEndGatewayConfiguration> M = a("end_gateway", new WorldGenEndGateway(WorldGenEndGatewayConfiguration.a));
   public static final WorldGenFeatureSeaGrass N = a("seagrass", new WorldGenFeatureSeaGrass(WorldGenFeatureConfigurationChance.k));
   public static final WorldGenerator<WorldGenFeatureEmptyConfiguration> O = a("kelp", new WorldGenFeatureKelp(WorldGenFeatureEmptyConfiguration.a));
   public static final WorldGenerator<WorldGenFeatureEmptyConfiguration> P = a("coral_tree", new WorldGenFeatureCoralTree(WorldGenFeatureEmptyConfiguration.a));
   public static final WorldGenerator<WorldGenFeatureEmptyConfiguration> Q = a(
      "coral_mushroom", new WorldGenFeatureCoralMushroom(WorldGenFeatureEmptyConfiguration.a)
   );
   public static final WorldGenerator<WorldGenFeatureEmptyConfiguration> R = a("coral_claw", new WorldGenFeatureCoralClaw(WorldGenFeatureEmptyConfiguration.a));
   public static final WorldGenerator<WorldGenDecoratorFrequencyConfiguration> S = a(
      "sea_pickle", new WorldGenFeatureSeaPickel(WorldGenDecoratorFrequencyConfiguration.a)
   );
   public static final WorldGenerator<WorldGenFeatureBlockConfiguration> T = a("simple_block", new WorldGenFeatureBlock(WorldGenFeatureBlockConfiguration.a));
   public static final WorldGenerator<WorldGenFeatureConfigurationChance> U = a("bamboo", new WorldGenFeatureBamboo(WorldGenFeatureConfigurationChance.k));
   public static final WorldGenerator<WorldGenFeatureHugeFungiConfiguration> V = a(
      "huge_fungus", new WorldGenFeatureHugeFungi(WorldGenFeatureHugeFungiConfiguration.a)
   );
   public static final WorldGenerator<NetherForestVegetationConfig> W = a(
      "nether_forest_vegetation", new WorldGenFeatureNetherForestVegetation(NetherForestVegetationConfig.c)
   );
   public static final WorldGenerator<WorldGenFeatureEmptyConfiguration> X = a(
      "weeping_vines", new WorldGenFeatureWeepingVines(WorldGenFeatureEmptyConfiguration.a)
   );
   public static final WorldGenerator<TwistingVinesConfig> Y = a("twisting_vines", new WorldGenFeatureTwistingVines(TwistingVinesConfig.a));
   public static final WorldGenerator<WorldGenFeatureBasaltColumnsConfiguration> Z = a(
      "basalt_columns", new WorldGenFeatureBasaltColumns(WorldGenFeatureBasaltColumnsConfiguration.a)
   );
   public static final WorldGenerator<WorldGenFeatureDeltaConfiguration> aa = a("delta_feature", new WorldGenFeatureDelta(WorldGenFeatureDeltaConfiguration.a));
   public static final WorldGenerator<WorldGenFeatureRadiusConfiguration> ab = a(
      "netherrack_replace_blobs", new WorldGenFeatureNetherrackReplaceBlobs(WorldGenFeatureRadiusConfiguration.a)
   );
   public static final WorldGenerator<WorldGenFeatureFillConfiguration> ac = a("fill_layer", new WorldGenFeatureFill(WorldGenFeatureFillConfiguration.a));
   public static final WorldGenBonusChest ad = a("bonus_chest", new WorldGenBonusChest(WorldGenFeatureEmptyConfiguration.a));
   public static final WorldGenerator<WorldGenFeatureEmptyConfiguration> ae = a(
      "basalt_pillar", new WorldGenFeatureBasaltPillar(WorldGenFeatureEmptyConfiguration.a)
   );
   public static final WorldGenerator<WorldGenFeatureOreConfiguration> af = a("scattered_ore", new ScatteredOreFeature(WorldGenFeatureOreConfiguration.a));
   public static final WorldGenerator<WorldGenFeatureRandomChoiceConfiguration> ag = a(
      "random_selector", new WorldGenFeatureRandomChoice(WorldGenFeatureRandomChoiceConfiguration.a)
   );
   public static final WorldGenerator<WorldGenFeatureRandom2> ah = a(
      "simple_random_selector", new WorldGenFeatureRandom2Configuration(WorldGenFeatureRandom2.a)
   );
   public static final WorldGenerator<WorldGenFeatureChoiceConfiguration> ai = a(
      "random_boolean_selector", new WorldGenFeatureChoice(WorldGenFeatureChoiceConfiguration.a)
   );
   public static final WorldGenerator<GeodeConfiguration> aj = a("geode", new GeodeFeature(GeodeConfiguration.b));
   public static final WorldGenerator<DripstoneClusterConfiguration> ak = a("dripstone_cluster", new DripstoneClusterFeature(DripstoneClusterConfiguration.a));
   public static final WorldGenerator<LargeDripstoneConfiguration> al = a("large_dripstone", new LargeDripstoneFeature(LargeDripstoneConfiguration.a));
   public static final WorldGenerator<PointedDripstoneConfiguration> am = a("pointed_dripstone", new PointedDripstoneFeature(PointedDripstoneConfiguration.a));
   public static final WorldGenerator<SculkPatchConfiguration> an = a("sculk_patch", new SculkPatchFeature(SculkPatchConfiguration.a));
   private final Codec<WorldGenFeatureConfigured<FC, WorldGenerator<FC>>> a;

   private static <C extends WorldGenFeatureConfiguration, F extends WorldGenerator<C>> F a(String var0, F var1) {
      return IRegistry.a(BuiltInRegistries.Q, var0, var1);
   }

   public WorldGenerator(Codec<FC> var0) {
      this.a = var0.fieldOf("config").xmap(var0x -> new WorldGenFeatureConfigured<>(this, var0x), WorldGenFeatureConfigured::c).codec();
   }

   public Codec<WorldGenFeatureConfigured<FC, WorldGenerator<FC>>> a() {
      return this.a;
   }

   protected void a(IWorldWriter var0, BlockPosition var1, IBlockData var2) {
      var0.a(var1, var2, 3);
   }

   public static Predicate<IBlockData> a(TagKey<Block> var0) {
      return var1 -> !var1.a(var0);
   }

   protected void a(GeneratorAccessSeed var0, BlockPosition var1, IBlockData var2, Predicate<IBlockData> var3) {
      if (var3.test(var0.a_(var1))) {
         var0.a(var1, var2, 2);
      }
   }

   public abstract boolean a(FeaturePlaceContext<FC> var1);

   public boolean a(FC var0, GeneratorAccessSeed var1, ChunkGenerator var2, RandomSource var3, BlockPosition var4) {
      return var1.e_(var4) ? this.a(new FeaturePlaceContext<>(Optional.empty(), var1, var2, var3, var4, var0)) : false;
   }

   protected static boolean a(IBlockData var0) {
      return var0.a(TagsBlock.bb);
   }

   public static boolean b(IBlockData var0) {
      return var0.a(TagsBlock.ae);
   }

   public static boolean a(VirtualLevelReadable var0, BlockPosition var1) {
      return var0.a(var1, WorldGenerator::b);
   }

   public static boolean a(Function<BlockPosition, IBlockData> var0, BlockPosition var1, Predicate<IBlockData> var2) {
      BlockPosition.MutableBlockPosition var3 = new BlockPosition.MutableBlockPosition();

      for(EnumDirection var7 : EnumDirection.values()) {
         var3.a(var1, var7);
         if (var2.test(var0.apply(var3))) {
            return true;
         }
      }

      return false;
   }

   public static boolean a(Function<BlockPosition, IBlockData> var0, BlockPosition var1) {
      return a(var0, var1, BlockBase.BlockData::h);
   }

   protected void a(GeneratorAccessSeed var0, BlockPosition var1) {
      BlockPosition.MutableBlockPosition var2 = var1.j();

      for(int var3 = 0; var3 < 2; ++var3) {
         var2.c(EnumDirection.b);
         if (var0.a_(var2).h()) {
            return;
         }

         var0.A(var2).e(var2);
      }
   }
}
