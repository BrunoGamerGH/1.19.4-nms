package net.minecraft.data.worldgen;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.WorldGenFeatureDefinedStructurePoolTemplate;

public class WorldGenFeaturePieces {
   public static final ResourceKey<WorldGenFeatureDefinedStructurePoolTemplate> a = a("empty");

   public static ResourceKey<WorldGenFeatureDefinedStructurePoolTemplate> a(String var0) {
      return ResourceKey.a(Registries.aA, new MinecraftKey(var0));
   }

   public static void a(BootstapContext<WorldGenFeatureDefinedStructurePoolTemplate> var0, String var1, WorldGenFeatureDefinedStructurePoolTemplate var2) {
      var0.a(a(var1), var2);
   }

   public static void a(BootstapContext<WorldGenFeatureDefinedStructurePoolTemplate> var0) {
      HolderGetter<WorldGenFeatureDefinedStructurePoolTemplate> var1 = var0.a(Registries.aA);
      Holder<WorldGenFeatureDefinedStructurePoolTemplate> var2 = var1.b(a);
      var0.a(a, new WorldGenFeatureDefinedStructurePoolTemplate(var2, ImmutableList.of(), WorldGenFeatureDefinedStructurePoolTemplate.Matching.b));
      WorldGenFeatureBastionPieces.a(var0);
      WorldGenFeaturePillagerOutpostPieces.a(var0);
      WorldGenFeatureVillages.a(var0);
      AncientCityStructurePieces.a(var0);
   }
}
