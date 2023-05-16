package net.minecraft.core.registries;

import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.SystemUtils;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.core.DefaultedMappedRegistry;
import net.minecraft.core.IRegistry;
import net.minecraft.core.IRegistryWritable;
import net.minecraft.core.RegistryBlocks;
import net.minecraft.core.RegistryMaterials;
import net.minecraft.core.particles.Particle;
import net.minecraft.core.particles.Particles;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.stats.StatisticList;
import net.minecraft.stats.StatisticWrapper;
import net.minecraft.util.valueproviders.FloatProviderType;
import net.minecraft.util.valueproviders.IntProviderType;
import net.minecraft.world.effect.MobEffectList;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.attributes.AttributeBase;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.ai.village.poi.VillagePlaceType;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.entity.decoration.PaintingVariants;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;
import net.minecraft.world.inventory.Containers;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.Instruments;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionRegistry;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.Recipes;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.biome.BiomeSources;
import net.minecraft.world.level.biome.WorldChunkManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BannerPatterns;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
import net.minecraft.world.level.block.entity.EnumBannerPatternType;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkGenerators;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.PositionSourceType;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;
import net.minecraft.world.level.levelgen.carver.WorldGenCarverAbstract;
import net.minecraft.world.level.levelgen.feature.WorldGenerator;
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSizeType;
import net.minecraft.world.level.levelgen.feature.foliageplacers.WorldGenFoilagePlacers;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacerType;
import net.minecraft.world.level.levelgen.feature.stateproviders.WorldGenFeatureStateProviders;
import net.minecraft.world.level.levelgen.feature.treedecorators.WorldGenFeatureTrees;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacers;
import net.minecraft.world.level.levelgen.heightproviders.HeightProviderType;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.WorldGenFeatureStructurePieceType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
import net.minecraft.world.level.levelgen.structure.pools.WorldGenFeatureDefinedStructurePools;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureRuleTestType;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureStructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.PosRuleTestType;
import net.minecraft.world.level.material.FluidType;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.level.storage.loot.entries.LootEntries;
import net.minecraft.world.level.storage.loot.entries.LootEntryType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import net.minecraft.world.level.storage.loot.providers.nbt.LootNbtProviderType;
import net.minecraft.world.level.storage.loot.providers.nbt.NbtProviders;
import net.minecraft.world.level.storage.loot.providers.number.LootNumberProviderType;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;
import net.minecraft.world.level.storage.loot.providers.score.LootScoreProviderType;
import net.minecraft.world.level.storage.loot.providers.score.ScoreboardNameProviders;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;

public class BuiltInRegistries {
   private static final Logger ao = LogUtils.getLogger();
   private static final Map<MinecraftKey, Supplier<?>> ap = Maps.newLinkedHashMap();
   public static final MinecraftKey a = new MinecraftKey("root");
   private static final IRegistryWritable<IRegistryWritable<?>> aq = new RegistryMaterials<>(ResourceKey.a(a), Lifecycle.stable());
   public static final RegistryBlocks<GameEvent> b = b(Registries.y, "step", var0 -> GameEvent.T);
   public static final IRegistry<SoundEffect> c = a(Registries.ab, var0 -> SoundEffects.lR);
   public static final RegistryBlocks<FluidType> d = b(Registries.v, "empty", var0 -> FluidTypes.a);
   public static final IRegistry<MobEffectList> e = a(Registries.N, var0 -> MobEffects.z);
   public static final RegistryBlocks<Block> f = b(Registries.e, "air", var0 -> Blocks.a);
   public static final IRegistry<Enchantment> g = a(Registries.q, var0 -> Enchantments.x);
   public static final RegistryBlocks<EntityTypes<?>> h = b(Registries.r, "pig", var0 -> EntityTypes.av);
   public static final RegistryBlocks<Item> i = b(Registries.C, "air", var0 -> Items.a);
   public static final RegistryBlocks<PotionRegistry> j = a(Registries.U, "empty", var0 -> Potions.b);
   public static final IRegistry<Particle<?>> k = a(Registries.P, var0 -> Particles.c);
   public static final IRegistry<TileEntityTypes<?>> l = a(Registries.f, var0 -> TileEntityTypes.a);
   public static final RegistryBlocks<PaintingVariant> m = a(Registries.O, "kebab", PaintingVariants::a);
   public static final IRegistry<MinecraftKey> n = a(Registries.n, var0 -> StatisticList.E);
   public static final RegistryBlocks<ChunkStatus> o = a(Registries.l, "empty", var0 -> ChunkStatus.c);
   public static final IRegistry<DefinedStructureRuleTestType<?>> p = a(Registries.Y, var0 -> DefinedStructureRuleTestType.a);
   public static final IRegistry<PosRuleTestType<?>> q = a(Registries.T, var0 -> PosRuleTestType.a);
   public static final IRegistry<Containers<?>> r = a(Registries.M, var0 -> Containers.h);
   public static final IRegistry<Recipes<?>> s = a(Registries.W, var0 -> Recipes.a);
   public static final IRegistry<RecipeSerializer<?>> t = a(Registries.V, var0 -> RecipeSerializer.b);
   public static final IRegistry<AttributeBase> u = a(Registries.b, var0 -> GenericAttributes.k);
   public static final IRegistry<PositionSourceType<?>> v = a(Registries.S, var0 -> PositionSourceType.a);
   public static final IRegistry<ArgumentTypeInfo<?, ?>> w = a(Registries.m, ArgumentTypeInfos::a);
   public static final IRegistry<StatisticWrapper<?>> x = a(Registries.ac, var0 -> StatisticList.c);
   public static final RegistryBlocks<VillagerType> y = a(Registries.al, "plains", var0 -> VillagerType.c);
   public static final RegistryBlocks<VillagerProfession> z = a(Registries.ak, "none", var0 -> VillagerProfession.b);
   public static final IRegistry<VillagePlaceType> A = a(Registries.R, PoiTypes::a);
   public static final RegistryBlocks<MemoryModuleType<?>> B = a(Registries.L, "dummy", var0 -> MemoryModuleType.a);
   public static final RegistryBlocks<SensorType<?>> C = a(Registries.aa, "dummy", var0 -> SensorType.a);
   public static final IRegistry<Schedule> D = a(Registries.Z, var0 -> Schedule.c);
   public static final IRegistry<Activity> E = a(Registries.a, var0 -> Activity.b);
   public static final IRegistry<LootEntryType> F = a(Registries.H, var0 -> LootEntries.a);
   public static final IRegistry<LootItemFunctionType> G = a(Registries.E, var0 -> LootItemFunctions.b);
   public static final IRegistry<LootItemConditionType> H = a(Registries.D, var0 -> LootItemConditions.a);
   public static final IRegistry<LootNumberProviderType> I = a(Registries.G, var0 -> NumberProviders.a);
   public static final IRegistry<LootNbtProviderType> J = a(Registries.F, var0 -> NbtProviders.b);
   public static final IRegistry<LootScoreProviderType> K = a(Registries.I, var0 -> ScoreboardNameProviders.b);
   public static final IRegistry<FloatProviderType<?>> L = a(Registries.u, var0 -> FloatProviderType.a);
   public static final IRegistry<IntProviderType<?>> M = a(Registries.B, var0 -> IntProviderType.a);
   public static final IRegistry<HeightProviderType<?>> N = a(Registries.z, var0 -> HeightProviderType.a);
   public static final IRegistry<BlockPredicateType<?>> O = a(Registries.g, var0 -> BlockPredicateType.k);
   public static final IRegistry<WorldGenCarverAbstract<?>> P = a(Registries.i, var0 -> WorldGenCarverAbstract.a);
   public static final IRegistry<WorldGenerator<?>> Q = a(Registries.s, var0 -> WorldGenerator.J);
   public static final IRegistry<StructurePlacementType<?>> R = a(Registries.ae, var0 -> StructurePlacementType.a);
   public static final IRegistry<WorldGenFeatureStructurePieceType> S = a(Registries.ad, var0 -> WorldGenFeatureStructurePieceType.c);
   public static final IRegistry<StructureType<?>> T = a(Registries.ah, var0 -> StructureType.f);
   public static final IRegistry<PlacementModifierType<?>> U = a(Registries.Q, var0 -> PlacementModifierType.f);
   public static final IRegistry<WorldGenFeatureStateProviders<?>> V = a(Registries.h, var0 -> WorldGenFeatureStateProviders.a);
   public static final IRegistry<WorldGenFoilagePlacers<?>> W = a(Registries.w, var0 -> WorldGenFoilagePlacers.a);
   public static final IRegistry<TrunkPlacers<?>> X = a(Registries.aj, var0 -> TrunkPlacers.a);
   public static final IRegistry<RootPlacerType<?>> Y = a(Registries.X, var0 -> RootPlacerType.a);
   public static final IRegistry<WorldGenFeatureTrees<?>> Z = a(Registries.ai, var0 -> WorldGenFeatureTrees.b);
   public static final IRegistry<FeatureSizeType<?>> aa = a(Registries.t, var0 -> FeatureSizeType.a);
   public static final IRegistry<Codec<? extends WorldChunkManager>> ab = a(Registries.d, Lifecycle.stable(), BiomeSources::a);
   public static final IRegistry<Codec<? extends ChunkGenerator>> ac = a(Registries.k, Lifecycle.stable(), ChunkGenerators::a);
   public static final IRegistry<Codec<? extends SurfaceRules.f>> ad = a(Registries.J, SurfaceRules.f::a);
   public static final IRegistry<Codec<? extends SurfaceRules.o>> ae = a(Registries.K, SurfaceRules.o::a);
   public static final IRegistry<Codec<? extends DensityFunction>> af = a(Registries.p, DensityFunctions::a);
   public static final IRegistry<DefinedStructureStructureProcessorType<?>> ag = a(Registries.ag, var0 -> DefinedStructureStructureProcessorType.a);
   public static final IRegistry<WorldGenFeatureDefinedStructurePools<?>> ah = a(Registries.af, var0 -> WorldGenFeatureDefinedStructurePools.d);
   public static final IRegistry<CatVariant> ai = a(Registries.j, CatVariant::a);
   public static final IRegistry<FrogVariant> aj = a(Registries.x, var0 -> FrogVariant.a);
   public static final IRegistry<EnumBannerPatternType> ak = a(Registries.c, BannerPatterns::a);
   public static final IRegistry<Instrument> al = a(Registries.A, Instruments::a);
   public static final IRegistry<String> am = a(Registries.am, DecoratedPotPatterns::a);
   public static final IRegistry<? extends IRegistry<?>> an = aq;

   private static <T> IRegistry<T> a(ResourceKey<? extends IRegistry<T>> var0, BuiltInRegistries.a<T> var1) {
      return a(var0, Lifecycle.stable(), var1);
   }

   private static <T> RegistryBlocks<T> a(ResourceKey<? extends IRegistry<T>> var0, String var1, BuiltInRegistries.a<T> var2) {
      return a(var0, var1, Lifecycle.stable(), var2);
   }

   private static <T> RegistryBlocks<T> b(ResourceKey<? extends IRegistry<T>> var0, String var1, BuiltInRegistries.a<T> var2) {
      return b(var0, var1, Lifecycle.stable(), var2);
   }

   private static <T> IRegistry<T> a(ResourceKey<? extends IRegistry<T>> var0, Lifecycle var1, BuiltInRegistries.a<T> var2) {
      return a(var0, new RegistryMaterials<>(var0, var1, false), var2, var1);
   }

   private static <T> RegistryBlocks<T> a(ResourceKey<? extends IRegistry<T>> var0, String var1, Lifecycle var2, BuiltInRegistries.a<T> var3) {
      return a(var0, new DefaultedMappedRegistry<>(var1, var0, var2, false), var3, var2);
   }

   private static <T> RegistryBlocks<T> b(ResourceKey<? extends IRegistry<T>> var0, String var1, Lifecycle var2, BuiltInRegistries.a<T> var3) {
      return a(var0, new DefaultedMappedRegistry<>(var1, var0, var2, true), var3, var2);
   }

   private static <T, R extends IRegistryWritable<T>> R a(ResourceKey<? extends IRegistry<T>> var0, R var1, BuiltInRegistries.a<T> var2, Lifecycle var3) {
      MinecraftKey var4 = var0.a();
      ap.put(var4, () -> var2.run(var1));
      aq.a(var0, var1, var3);
      return var1;
   }

   public static void a() {
      b();
      c();
      a(an);
   }

   private static void b() {
      ap.forEach((var0, var1) -> {
         if (var1.get() == null) {
            ao.error("Unable to bootstrap registry '{}'", var0);
         }
      });
   }

   private static void c() {
      an.l();

      for(IRegistry<?> var1 : an) {
         var1.l();
      }
   }

   private static <T extends IRegistry<?>> void a(IRegistry<T> var0) {
      var0.forEach(var1 -> {
         if (var1.e().isEmpty()) {
            SystemUtils.a("Registry '" + var0.b(var1) + "' was empty after loading");
         }

         if (var1 instanceof RegistryBlocks) {
            MinecraftKey var2 = ((RegistryBlocks)var1).a();
            Validate.notNull(var1.a(var2), "Missing default of DefaultedMappedRegistry: " + var2, new Object[0]);
         }
      });
   }

   @FunctionalInterface
   interface a<T> {
      T run(IRegistry<T> var1);
   }
}
