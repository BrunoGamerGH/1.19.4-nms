package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.stream.Stream;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureConfiguration;

public record WorldGenFeatureConfigured<FC extends WorldGenFeatureConfiguration, F extends WorldGenerator<FC>>(F feature, FC config) {
   private final F d;
   private final FC e;
   public static final Codec<WorldGenFeatureConfigured<?, ?>> a = BuiltInRegistries.Q.q().dispatch(var0 -> var0.d, WorldGenerator::a);
   public static final Codec<Holder<WorldGenFeatureConfigured<?, ?>>> b = RegistryFileCodec.a(Registries.aq, a);
   public static final Codec<HolderSet<WorldGenFeatureConfigured<?, ?>>> c = RegistryCodecs.a(Registries.aq, a);

   public WorldGenFeatureConfigured(F var0, FC var1) {
      this.d = var0;
      this.e = var1;
   }

   public boolean a(GeneratorAccessSeed var0, ChunkGenerator var1, RandomSource var2, BlockPosition var3) {
      return this.d.a(this.e, var0, var1, var2, var3);
   }

   public Stream<WorldGenFeatureConfigured<?, ?>> a() {
      return Stream.concat(Stream.of(this), this.e.e());
   }

   @Override
   public String toString() {
      return "Configured: " + this.d + ": " + this.e;
   }

   public F b() {
      return this.d;
   }

   public FC c() {
      return this.e;
   }
}
