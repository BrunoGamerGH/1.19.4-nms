package net.minecraft.util.valueproviders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Function;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;

public class UniformFloat extends FloatProvider {
   public static final Codec<UniformFloat> a = RecordCodecBuilder.create(
         var0 -> var0.group(Codec.FLOAT.fieldOf("min_inclusive").forGetter(var0x -> var0x.b), Codec.FLOAT.fieldOf("max_exclusive").forGetter(var0x -> var0x.d))
               .apply(var0, UniformFloat::new)
      )
      .comapFlatMap(
         var0 -> var0.d <= var0.b
               ? DataResult.error(() -> "Max must be larger than min, min_inclusive: " + var0.b + ", max_exclusive: " + var0.d)
               : DataResult.success(var0),
         Function.identity()
      );
   private final float b;
   private final float d;

   private UniformFloat(float var0, float var1) {
      this.b = var0;
      this.d = var1;
   }

   public static UniformFloat b(float var0, float var1) {
      if (var1 <= var0) {
         throw new IllegalArgumentException("Max must exceed min");
      } else {
         return new UniformFloat(var0, var1);
      }
   }

   @Override
   public float a(RandomSource var0) {
      return MathHelper.b(var0, this.b, this.d);
   }

   @Override
   public float a() {
      return this.b;
   }

   @Override
   public float b() {
      return this.d;
   }

   @Override
   public FloatProviderType<?> c() {
      return FloatProviderType.b;
   }

   @Override
   public String toString() {
      return "[" + this.b + "-" + this.d + "]";
   }
}
