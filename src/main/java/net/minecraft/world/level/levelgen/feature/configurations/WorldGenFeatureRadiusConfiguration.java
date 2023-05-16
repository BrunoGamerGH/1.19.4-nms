package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.state.IBlockData;

public class WorldGenFeatureRadiusConfiguration implements WorldGenFeatureConfiguration {
   public static final Codec<WorldGenFeatureRadiusConfiguration> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               IBlockData.b.fieldOf("target").forGetter(var0x -> var0x.b),
               IBlockData.b.fieldOf("state").forGetter(var0x -> var0x.c),
               IntProvider.b(0, 12).fieldOf("radius").forGetter(var0x -> var0x.d)
            )
            .apply(var0, WorldGenFeatureRadiusConfiguration::new)
   );
   public final IBlockData b;
   public final IBlockData c;
   private final IntProvider d;

   public WorldGenFeatureRadiusConfiguration(IBlockData var0, IBlockData var1, IntProvider var2) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
   }

   public IntProvider a() {
      return this.d;
   }
}
