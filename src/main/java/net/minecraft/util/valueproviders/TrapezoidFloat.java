package net.minecraft.util.valueproviders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Function;
import net.minecraft.util.RandomSource;

public class TrapezoidFloat extends FloatProvider {
   public static final Codec<TrapezoidFloat> a = RecordCodecBuilder.create(
         var0 -> var0.group(
                  Codec.FLOAT.fieldOf("min").forGetter(var0x -> var0x.b),
                  Codec.FLOAT.fieldOf("max").forGetter(var0x -> var0x.d),
                  Codec.FLOAT.fieldOf("plateau").forGetter(var0x -> var0x.e)
               )
               .apply(var0, TrapezoidFloat::new)
      )
      .comapFlatMap(
         var0 -> {
            if (var0.d < var0.b) {
               return DataResult.error(() -> "Max must be larger than min: [" + var0.b + ", " + var0.d + "]");
            } else {
               return var0.e > var0.d - var0.b
                  ? DataResult.error(() -> "Plateau can at most be the full span: [" + var0.b + ", " + var0.d + "]")
                  : DataResult.success(var0);
            }
         },
         Function.identity()
      );
   private final float b;
   private final float d;
   private final float e;

   public static TrapezoidFloat a(float var0, float var1, float var2) {
      return new TrapezoidFloat(var0, var1, var2);
   }

   private TrapezoidFloat(float var0, float var1, float var2) {
      this.b = var0;
      this.d = var1;
      this.e = var2;
   }

   @Override
   public float a(RandomSource var0) {
      float var1 = this.d - this.b;
      float var2 = (var1 - this.e) / 2.0F;
      float var3 = var1 - var2;
      return this.b + var0.i() * var3 + var0.i() * var2;
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
      return FloatProviderType.d;
   }

   @Override
   public String toString() {
      return "trapezoid(" + this.e + ") in [" + this.b + "-" + this.d + "]";
   }
}
