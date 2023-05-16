package net.minecraft.util.valueproviders;

import com.mojang.serialization.Codec;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;

public interface IntProviderType<P extends IntProvider> {
   IntProviderType<ConstantInt> a = a("constant", ConstantInt.b);
   IntProviderType<UniformInt> b = a("uniform", UniformInt.a);
   IntProviderType<BiasedToBottomInt> c = a("biased_to_bottom", BiasedToBottomInt.a);
   IntProviderType<ClampedInt> d = a("clamped", ClampedInt.a);
   IntProviderType<WeightedListInt> e = a("weighted_list", WeightedListInt.a);
   IntProviderType<ClampedNormalInt> f = a("clamped_normal", ClampedNormalInt.a);

   Codec<P> codec();

   static <P extends IntProvider> IntProviderType<P> a(String var0, Codec<P> var1) {
      return IRegistry.a(BuiltInRegistries.M, var0, () -> var1);
   }
}
