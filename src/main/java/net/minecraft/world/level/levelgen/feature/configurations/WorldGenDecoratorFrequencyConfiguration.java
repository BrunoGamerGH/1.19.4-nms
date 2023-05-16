package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;

public class WorldGenDecoratorFrequencyConfiguration implements WorldGenFeatureConfiguration {
   public static final Codec<WorldGenDecoratorFrequencyConfiguration> a = IntProvider.b(0, 256)
      .fieldOf("count")
      .xmap(WorldGenDecoratorFrequencyConfiguration::new, WorldGenDecoratorFrequencyConfiguration::a)
      .codec();
   private final IntProvider b;

   public WorldGenDecoratorFrequencyConfiguration(int var0) {
      this.b = ConstantInt.a(var0);
   }

   public WorldGenDecoratorFrequencyConfiguration(IntProvider var0) {
      this.b = var0;
   }

   public IntProvider a() {
      return this.b;
   }
}
