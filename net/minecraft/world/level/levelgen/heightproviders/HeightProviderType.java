package net.minecraft.world.level.levelgen.heightproviders;

import com.mojang.serialization.Codec;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;

public interface HeightProviderType<P extends HeightProvider> {
   HeightProviderType<ConstantHeight> a = a("constant", ConstantHeight.b);
   HeightProviderType<UniformHeight> b = a("uniform", UniformHeight.a);
   HeightProviderType<BiasedToBottomHeight> c = a("biased_to_bottom", BiasedToBottomHeight.a);
   HeightProviderType<VeryBiasedToBottomHeight> d = a("very_biased_to_bottom", VeryBiasedToBottomHeight.a);
   HeightProviderType<TrapezoidHeight> e = a("trapezoid", TrapezoidHeight.a);
   HeightProviderType<WeightedListHeight> f = a("weighted_list", WeightedListHeight.a);

   Codec<P> codec();

   private static <P extends HeightProvider> HeightProviderType<P> a(String var0, Codec<P> var1) {
      return IRegistry.a(BuiltInRegistries.N, var0, () -> var1);
   }
}
