package net.minecraft.world.level.levelgen.feature.stateproviders;

import com.mojang.datafixers.Products.P4;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;
import java.util.List;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.synth.NoiseGeneratorNormal;

public class NoiseProvider extends NoiseBasedStateProvider {
   public static final Codec<NoiseProvider> g = RecordCodecBuilder.create(var0 -> b(var0).apply(var0, NoiseProvider::new));
   protected final List<IBlockData> h;

   protected static <P extends NoiseProvider> P4<Mu<P>, Long, NoiseGeneratorNormal.a, Float, List<IBlockData>> b(Instance<P> var0) {
      return a(var0).and(Codec.list(IBlockData.b).fieldOf("states").forGetter(var0x -> var0x.h));
   }

   public NoiseProvider(long var0, NoiseGeneratorNormal.a var2, float var3, List<IBlockData> var4) {
      super(var0, var2, var3);
      this.h = var4;
   }

   @Override
   protected WorldGenFeatureStateProviders<?> a() {
      return WorldGenFeatureStateProviders.d;
   }

   @Override
   public IBlockData a(RandomSource var0, BlockPosition var1) {
      return this.a(this.h, var1, (double)this.e);
   }

   protected IBlockData a(List<IBlockData> var0, BlockPosition var1, double var2) {
      double var4 = this.a(var1, var2);
      return this.a(var0, var4);
   }

   protected IBlockData a(List<IBlockData> var0, double var1) {
      double var3 = MathHelper.a((1.0 + var1) / 2.0, 0.0, 0.9999);
      return var0.get((int)(var3 * (double)var0.size()));
   }
}
