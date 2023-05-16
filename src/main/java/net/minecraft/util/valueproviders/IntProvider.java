package net.minecraft.util.valueproviders;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.function.Function;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;

public abstract class IntProvider {
   private static final Codec<Either<Integer, IntProvider>> a = Codec.either(
      Codec.INT, BuiltInRegistries.M.q().dispatch(IntProvider::c, IntProviderType::codec)
   );
   public static final Codec<IntProvider> c = a.xmap(
      var0 -> (IntProvider)var0.map(ConstantInt::a, var0x -> var0x),
      var0 -> var0.c() == IntProviderType.a ? Either.left(((ConstantInt)var0).d()) : Either.right(var0)
   );
   public static final Codec<IntProvider> d = b(0, Integer.MAX_VALUE);
   public static final Codec<IntProvider> e = b(1, Integer.MAX_VALUE);

   public static Codec<IntProvider> b(int var0, int var1) {
      return a(var0, var1, c);
   }

   public static <T extends IntProvider> Codec<T> a(int var0, int var1, Codec<T> var2) {
      return ExtraCodecs.a(
         var2,
         (Function<T, DataResult<T>>)(var2x -> {
            if (var2x.a() < var0) {
               return DataResult.error(() -> "Value provider too low: " + var0 + " [" + var2x.a() + "-" + var2x.b() + "]");
            } else {
               return var2x.b() > var1
                  ? DataResult.error(() -> "Value provider too high: " + var1 + " [" + var2x.a() + "-" + var2x.b() + "]")
                  : DataResult.success(var2x);
            }
         })
      );
   }

   public abstract int a(RandomSource var1);

   public abstract int a();

   public abstract int b();

   public abstract IntProviderType<?> c();
}
