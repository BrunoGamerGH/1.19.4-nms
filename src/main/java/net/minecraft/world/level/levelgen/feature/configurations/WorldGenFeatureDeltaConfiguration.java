package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.state.IBlockData;

public class WorldGenFeatureDeltaConfiguration implements WorldGenFeatureConfiguration {
   public static final Codec<WorldGenFeatureDeltaConfiguration> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               IBlockData.b.fieldOf("contents").forGetter(var0x -> var0x.b),
               IBlockData.b.fieldOf("rim").forGetter(var0x -> var0x.c),
               IntProvider.b(0, 16).fieldOf("size").forGetter(var0x -> var0x.d),
               IntProvider.b(0, 16).fieldOf("rim_size").forGetter(var0x -> var0x.e)
            )
            .apply(var0, WorldGenFeatureDeltaConfiguration::new)
   );
   private final IBlockData b;
   private final IBlockData c;
   private final IntProvider d;
   private final IntProvider e;

   public WorldGenFeatureDeltaConfiguration(IBlockData var0, IBlockData var1, IntProvider var2, IntProvider var3) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
      this.e = var3;
   }

   public IBlockData a() {
      return this.b;
   }

   public IBlockData b() {
      return this.c;
   }

   public IntProvider c() {
      return this.d;
   }

   public IntProvider d() {
      return this.e;
   }
}
