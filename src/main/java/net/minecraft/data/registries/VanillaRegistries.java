package net.minecraft.data.registries;

import java.util.List;
import net.minecraft.SystemUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.DimensionTypes;
import net.minecraft.data.worldgen.NoiseData;
import net.minecraft.data.worldgen.ProcessorLists;
import net.minecraft.data.worldgen.StructureSets;
import net.minecraft.data.worldgen.Structures;
import net.minecraft.data.worldgen.WorldGenCarvers;
import net.minecraft.data.worldgen.WorldGenFeaturePieces;
import net.minecraft.data.worldgen.biome.BiomeData;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.network.chat.ChatMessageType;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.item.armortrim.TrimPatterns;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterLists;
import net.minecraft.world.level.levelgen.GeneratorSettingBase;
import net.minecraft.world.level.levelgen.NoiseRouterData;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorPresets;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.presets.WorldPresets;

public class VanillaRegistries {
   private static final RegistrySetBuilder a = new RegistrySetBuilder()
      .a(Registries.as, DimensionTypes::a)
      .a(Registries.ap, WorldGenCarvers::a)
      .a(Registries.aq, FeatureUtils::a)
      .a(Registries.aw, PlacementUtils::a)
      .a(Registries.ax, Structures::a)
      .a(Registries.az, StructureSets::a)
      .a(Registries.ay, ProcessorLists::a)
      .a(Registries.aA, WorldGenFeaturePieces::a)
      .a(Registries.an, BiomeData::a)
      .a(Registries.aE, MultiNoiseBiomeSourceParameterLists::a)
      .a(Registries.av, NoiseData::a)
      .a(Registries.ar, NoiseRouterData::a)
      .a(Registries.au, GeneratorSettingBase::a)
      .a(Registries.aD, WorldPresets::a)
      .a(Registries.at, FlatLevelGeneratorPresets::a)
      .a(Registries.ao, ChatMessageType::a)
      .a(Registries.aC, TrimPatterns::a)
      .a(Registries.aB, TrimMaterials::a)
      .a(Registries.o, DamageTypes::a);

   private static void a(HolderLookup.b var0) {
      a(var0.b(Registries.aw), var0.b(Registries.an));
   }

   public static void a(HolderGetter<PlacedFeature> var0, HolderLookup<BiomeBase> var1) {
      var1.b().forEach(var1x -> {
         MinecraftKey var2 = var1x.g().a();
         List<HolderSet<PlacedFeature>> var3 = var1x.a().d().b();
         var3.stream().flatMap(HolderSet::a).forEach(var3x -> var3x.d().ifLeft(var2xx -> {
               Holder.c<PlacedFeature> var3xx = var0.b(var2xx);
               if (!a((PlacedFeature)var3xx.a())) {
                  SystemUtils.a("Placed feature " + var2xx.a() + " in biome " + var2 + " is missing BiomeFilter.biome()");
               }
            }).ifRight(var1xxx -> {
               if (!a(var1xxx)) {
                  SystemUtils.a("Placed inline feature in biome " + var1x + " is missing BiomeFilter.biome()");
               }
            }));
      });
   }

   private static boolean a(PlacedFeature var0) {
      return var0.c().contains(BiomeFilter.a());
   }

   public static HolderLookup.b a() {
      IRegistryCustom.Dimension var0 = IRegistryCustom.a(BuiltInRegistries.an);
      HolderLookup.b var1 = a.a(var0);
      a(var1);
      return var1;
   }
}
