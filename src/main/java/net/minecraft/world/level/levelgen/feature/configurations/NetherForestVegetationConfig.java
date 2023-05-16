package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.levelgen.feature.stateproviders.WorldGenFeatureStateProvider;

public class NetherForestVegetationConfig extends WorldGenFeatureBlockPileConfiguration {
   public static final Codec<NetherForestVegetationConfig> c = RecordCodecBuilder.create(
      var0 -> var0.group(
               WorldGenFeatureStateProvider.a.fieldOf("state_provider").forGetter(var0x -> var0x.b),
               ExtraCodecs.i.fieldOf("spread_width").forGetter(var0x -> var0x.d),
               ExtraCodecs.i.fieldOf("spread_height").forGetter(var0x -> var0x.e)
            )
            .apply(var0, NetherForestVegetationConfig::new)
   );
   public final int d;
   public final int e;

   public NetherForestVegetationConfig(WorldGenFeatureStateProvider var0, int var1, int var2) {
      super(var0);
      this.d = var1;
      this.e = var2;
   }
}
