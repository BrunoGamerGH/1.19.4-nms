package net.minecraft.world.level.levelgen.heightproviders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.levelgen.WorldGenerationContext;

public class WeightedListHeight extends HeightProvider {
   public static final Codec<WeightedListHeight> a = RecordCodecBuilder.create(
      var0 -> var0.group(SimpleWeightedRandomList.b(HeightProvider.c).fieldOf("distribution").forGetter(var0x -> var0x.b))
            .apply(var0, WeightedListHeight::new)
   );
   private final SimpleWeightedRandomList<HeightProvider> b;

   public WeightedListHeight(SimpleWeightedRandomList<HeightProvider> var0) {
      this.b = var0;
   }

   @Override
   public int a(RandomSource var0, WorldGenerationContext var1) {
      return this.b.a(var0).orElseThrow(IllegalStateException::new).a(var0, var1);
   }

   @Override
   public HeightProviderType<?> a() {
      return HeightProviderType.f;
   }
}
