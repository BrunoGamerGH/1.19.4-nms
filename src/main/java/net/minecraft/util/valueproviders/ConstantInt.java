package net.minecraft.util.valueproviders;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;

public class ConstantInt extends IntProvider {
   public static final ConstantInt a = new ConstantInt(0);
   public static final Codec<ConstantInt> b = Codec.either(
         Codec.INT, RecordCodecBuilder.create(var0 -> var0.group(Codec.INT.fieldOf("value").forGetter(var0x -> var0x.f)).apply(var0, ConstantInt::new))
      )
      .xmap(var0 -> (ConstantInt)var0.map(ConstantInt::a, var0x -> var0x), var0 -> Either.left(var0.f));
   private final int f;

   public static ConstantInt a(int var0) {
      return var0 == 0 ? a : new ConstantInt(var0);
   }

   private ConstantInt(int var0) {
      this.f = var0;
   }

   public int d() {
      return this.f;
   }

   @Override
   public int a(RandomSource var0) {
      return this.f;
   }

   @Override
   public int a() {
      return this.f;
   }

   @Override
   public int b() {
      return this.f;
   }

   @Override
   public IntProviderType<?> c() {
      return IntProviderType.a;
   }

   @Override
   public String toString() {
      return Integer.toString(this.f);
   }
}
