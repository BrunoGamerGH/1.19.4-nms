package net.minecraft.util.valueproviders;

import com.mojang.serialization.Codec;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;

public interface FloatProviderType<P extends FloatProvider> {
   FloatProviderType<ConstantFloat> a = a("constant", ConstantFloat.b);
   FloatProviderType<UniformFloat> b = a("uniform", UniformFloat.a);
   FloatProviderType<ClampedNormalFloat> c = a("clamped_normal", ClampedNormalFloat.a);
   FloatProviderType<TrapezoidFloat> d = a("trapezoid", TrapezoidFloat.a);

   Codec<P> codec();

   static <P extends FloatProvider> FloatProviderType<P> a(String var0, Codec<P> var1) {
      return IRegistry.a(BuiltInRegistries.L, var0, () -> var1);
   }
}
