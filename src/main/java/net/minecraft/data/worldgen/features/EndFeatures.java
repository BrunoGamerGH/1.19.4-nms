package net.minecraft.data.worldgen.features;

import com.google.common.collect.ImmutableList;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.feature.WorldGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenEndGatewayConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureEndSpikeConfiguration;

public class EndFeatures {
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> a = FeatureUtils.a("end_spike");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> b = FeatureUtils.a("end_gateway_return");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> c = FeatureUtils.a("end_gateway_delayed");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> d = FeatureUtils.a("chorus_plant");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> e = FeatureUtils.a("end_island");

   public static void a(BootstapContext<WorldGenFeatureConfigured<?, ?>> var0) {
      FeatureUtils.a(var0, a, WorldGenerator.K, new WorldGenFeatureEndSpikeConfiguration(false, ImmutableList.of(), null));
      FeatureUtils.a(var0, b, WorldGenerator.M, WorldGenEndGatewayConfiguration.a(WorldServer.a, true));
      FeatureUtils.a(var0, c, WorldGenerator.M, WorldGenEndGatewayConfiguration.a());
      FeatureUtils.a(var0, d, WorldGenerator.m);
      FeatureUtils.a(var0, e, WorldGenerator.L);
   }
}
