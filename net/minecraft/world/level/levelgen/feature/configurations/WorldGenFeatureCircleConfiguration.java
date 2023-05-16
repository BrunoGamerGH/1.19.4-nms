package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;

public record WorldGenFeatureCircleConfiguration(RuleBasedBlockStateProvider stateProvider, BlockPredicate target, IntProvider radius, int halfHeight)
   implements WorldGenFeatureConfiguration {
   private final RuleBasedBlockStateProvider b;
   private final BlockPredicate c;
   private final IntProvider d;
   private final int e;
   public static final Codec<WorldGenFeatureCircleConfiguration> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               RuleBasedBlockStateProvider.a.fieldOf("state_provider").forGetter(WorldGenFeatureCircleConfiguration::a),
               BlockPredicate.b.fieldOf("target").forGetter(WorldGenFeatureCircleConfiguration::b),
               IntProvider.b(0, 8).fieldOf("radius").forGetter(WorldGenFeatureCircleConfiguration::c),
               Codec.intRange(0, 4).fieldOf("half_height").forGetter(WorldGenFeatureCircleConfiguration::d)
            )
            .apply(var0, WorldGenFeatureCircleConfiguration::new)
   );

   public WorldGenFeatureCircleConfiguration(RuleBasedBlockStateProvider var0, BlockPredicate var1, IntProvider var2, int var3) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
      this.e = var3;
   }

   public RuleBasedBlockStateProvider a() {
      return this.b;
   }

   public BlockPredicate b() {
      return this.c;
   }

   public IntProvider c() {
      return this.d;
   }

   public int d() {
      return this.e;
   }
}
