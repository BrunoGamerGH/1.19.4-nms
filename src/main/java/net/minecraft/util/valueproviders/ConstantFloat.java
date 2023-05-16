package net.minecraft.util.valueproviders;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;

public class ConstantFloat extends FloatProvider {
   public static final ConstantFloat a = new ConstantFloat(0.0F);
   public static final Codec<ConstantFloat> b = Codec.either(
         Codec.FLOAT, RecordCodecBuilder.create(var0 -> var0.group(Codec.FLOAT.fieldOf("value").forGetter(var0x -> var0x.d)).apply(var0, ConstantFloat::new))
      )
      .xmap(var0 -> (ConstantFloat)var0.map(ConstantFloat::a, var0x -> var0x), var0 -> Either.left(var0.d));
   private final float d;

   public static ConstantFloat a(float var0) {
      return var0 == 0.0F ? a : new ConstantFloat(var0);
   }

   private ConstantFloat(float var0) {
      this.d = var0;
   }

   public float d() {
      return this.d;
   }

   @Override
   public float a(RandomSource var0) {
      return this.d;
   }

   @Override
   public float a() {
      return this.d;
   }

   @Override
   public float b() {
      return this.d + 1.0F;
   }

   @Override
   public FloatProviderType<?> c() {
      return FloatProviderType.a;
   }

   @Override
   public String toString() {
      return Float.toString(this.d);
   }
}
