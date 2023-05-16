package net.minecraft.world.level.levelgen.feature.stateproviders;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.InclusiveRange;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.SeededRandom;
import net.minecraft.world.level.levelgen.synth.NoiseGeneratorNormal;

public class DualNoiseProvider extends NoiseProvider {
   public static final Codec<DualNoiseProvider> b = RecordCodecBuilder.create(
      var0 -> var0.group(
               InclusiveRange.a(Codec.INT, 1, 64).fieldOf("variety").forGetter(var0x -> var0x.i),
               NoiseGeneratorNormal.a.a.fieldOf("slow_noise").forGetter(var0x -> var0x.j),
               ExtraCodecs.j.fieldOf("slow_scale").forGetter(var0x -> var0x.k)
            )
            .and(b(var0))
            .apply(var0, DualNoiseProvider::new)
   );
   private final InclusiveRange<Integer> i;
   private final NoiseGeneratorNormal.a j;
   private final float k;
   private final NoiseGeneratorNormal l;

   public DualNoiseProvider(
      InclusiveRange<Integer> var0, NoiseGeneratorNormal.a var1, float var2, long var3, NoiseGeneratorNormal.a var5, float var6, List<IBlockData> var7
   ) {
      super(var3, var5, var6, var7);
      this.i = var0;
      this.j = var1;
      this.k = var2;
      this.l = NoiseGeneratorNormal.b(new SeededRandom(new LegacyRandomSource(var3)), var1);
   }

   @Override
   protected WorldGenFeatureStateProviders<?> a() {
      return WorldGenFeatureStateProviders.e;
   }

   @Override
   public IBlockData a(RandomSource var0, BlockPosition var1) {
      double var2 = this.a(var1);
      int var4 = (int)MathHelper.a(var2, -1.0, 1.0, (double)this.i.a().intValue(), (double)(this.i.b() + 1));
      List<IBlockData> var5 = Lists.newArrayListWithCapacity(var4);

      for(int var6 = 0; var6 < var4; ++var6) {
         var5.add(this.a(this.h, this.a(var1.b(var6 * 54545, 0, var6 * 34234))));
      }

      return this.a(var5, var1, (double)this.e);
   }

   protected double a(BlockPosition var0) {
      return this.l.a((double)((float)var0.u() * this.k), (double)((float)var0.v() * this.k), (double)((float)var0.w() * this.k));
   }
}
