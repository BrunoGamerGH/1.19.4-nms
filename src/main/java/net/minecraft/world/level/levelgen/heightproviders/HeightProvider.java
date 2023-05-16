package net.minecraft.world.level.levelgen.heightproviders;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.WorldGenerationContext;

public abstract class HeightProvider {
   private static final Codec<Either<VerticalAnchor, HeightProvider>> a = Codec.either(
      VerticalAnchor.a, BuiltInRegistries.N.q().dispatch(HeightProvider::a, HeightProviderType::codec)
   );
   public static final Codec<HeightProvider> c = a.xmap(
      var0 -> (HeightProvider)var0.map(ConstantHeight::a, var0x -> var0x),
      var0 -> var0.a() == HeightProviderType.a ? Either.left(((ConstantHeight)var0).b()) : Either.right(var0)
   );

   public abstract int a(RandomSource var1, WorldGenerationContext var2);

   public abstract HeightProviderType<?> a();
}
