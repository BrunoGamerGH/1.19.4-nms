package net.minecraft.util.valueproviders;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.function.Function;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.ExtraCodecs;

public abstract class FloatProvider implements SampledFloat {
   private static final Codec<Either<Float, FloatProvider>> a = Codec.either(
      Codec.FLOAT, BuiltInRegistries.L.q().dispatch(FloatProvider::c, FloatProviderType::codec)
   );
   public static final Codec<FloatProvider> c = a.xmap(
      var0 -> (FloatProvider)var0.map(ConstantFloat::a, var0x -> var0x),
      var0 -> var0.c() == FloatProviderType.a ? Either.left(((ConstantFloat)var0).d()) : Either.right(var0)
   );

   public static Codec<FloatProvider> a(float var0, float var1) {
      return ExtraCodecs.a(
         c,
         (Function<FloatProvider, DataResult<FloatProvider>>)(var2 -> {
            if (var2.a() < var0) {
               return DataResult.error(() -> "Value provider too low: " + var0 + " [" + var2.a() + "-" + var2.b() + "]");
            } else {
               return var2.b() > var1
                  ? DataResult.error(() -> "Value provider too high: " + var1 + " [" + var2.a() + "-" + var2.b() + "]")
                  : DataResult.success(var2);
            }
         })
      );
   }

   public abstract float a();

   public abstract float b();

   public abstract FloatProviderType<?> c();
}
