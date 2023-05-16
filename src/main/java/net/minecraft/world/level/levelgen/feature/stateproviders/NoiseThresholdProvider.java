package net.minecraft.world.level.levelgen.feature.stateproviders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.synth.NoiseGeneratorNormal;

public class NoiseThresholdProvider extends NoiseBasedStateProvider {
   public static final Codec<NoiseThresholdProvider> b = RecordCodecBuilder.create(
      var0 -> a(var0)
            .and(
               var0.group(
                  Codec.floatRange(-1.0F, 1.0F).fieldOf("threshold").forGetter(var0x -> var0x.g),
                  Codec.floatRange(0.0F, 1.0F).fieldOf("high_chance").forGetter(var0x -> var0x.h),
                  IBlockData.b.fieldOf("default_state").forGetter(var0x -> var0x.i),
                  Codec.list(IBlockData.b).fieldOf("low_states").forGetter(var0x -> var0x.j),
                  Codec.list(IBlockData.b).fieldOf("high_states").forGetter(var0x -> var0x.k)
               )
            )
            .apply(var0, NoiseThresholdProvider::new)
   );
   private final float g;
   private final float h;
   private final IBlockData i;
   private final List<IBlockData> j;
   private final List<IBlockData> k;

   public NoiseThresholdProvider(
      long var0, NoiseGeneratorNormal.a var2, float var3, float var4, float var5, IBlockData var6, List<IBlockData> var7, List<IBlockData> var8
   ) {
      super(var0, var2, var3);
      this.g = var4;
      this.h = var5;
      this.i = var6;
      this.j = var7;
      this.k = var8;
   }

   @Override
   protected WorldGenFeatureStateProviders<?> a() {
      return WorldGenFeatureStateProviders.c;
   }

   @Override
   public IBlockData a(RandomSource var0, BlockPosition var1) {
      double var2 = this.a(var1, (double)this.e);
      if (var2 < (double)this.g) {
         return SystemUtils.a(this.j, var0);
      } else {
         return var0.i() < this.h ? SystemUtils.a(this.k, var0) : this.i;
      }
   }
}
