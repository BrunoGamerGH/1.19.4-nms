package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureConfiguration;

public class WorldGenFeatureHugeFungiConfiguration implements WorldGenFeatureConfiguration {
   public static final Codec<WorldGenFeatureHugeFungiConfiguration> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               IBlockData.b.fieldOf("valid_base_block").forGetter(var0x -> var0x.b),
               IBlockData.b.fieldOf("stem_state").forGetter(var0x -> var0x.c),
               IBlockData.b.fieldOf("hat_state").forGetter(var0x -> var0x.d),
               IBlockData.b.fieldOf("decor_state").forGetter(var0x -> var0x.e),
               Codec.BOOL.fieldOf("planted").orElse(false).forGetter(var0x -> var0x.f)
            )
            .apply(var0, WorldGenFeatureHugeFungiConfiguration::new)
   );
   public final IBlockData b;
   public final IBlockData c;
   public final IBlockData d;
   public final IBlockData e;
   public final boolean f;

   public WorldGenFeatureHugeFungiConfiguration(IBlockData var0, IBlockData var1, IBlockData var2, IBlockData var3, boolean var4) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
      this.e = var3;
      this.f = var4;
   }
}
