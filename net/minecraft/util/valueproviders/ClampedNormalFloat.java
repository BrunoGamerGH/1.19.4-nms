package net.minecraft.util.valueproviders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Function;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;

public class ClampedNormalFloat extends FloatProvider {
   public static final Codec<ClampedNormalFloat> a = RecordCodecBuilder.create(
         var0 -> var0.group(
                  Codec.FLOAT.fieldOf("mean").forGetter(var0x -> var0x.b),
                  Codec.FLOAT.fieldOf("deviation").forGetter(var0x -> var0x.d),
                  Codec.FLOAT.fieldOf("min").forGetter(var0x -> var0x.e),
                  Codec.FLOAT.fieldOf("max").forGetter(var0x -> var0x.f)
               )
               .apply(var0, ClampedNormalFloat::new)
      )
      .comapFlatMap(
         var0 -> var0.f < var0.e ? DataResult.error(() -> "Max must be larger than min: [" + var0.e + ", " + var0.f + "]") : DataResult.success(var0),
         Function.identity()
      );
   private final float b;
   private final float d;
   private final float e;
   private final float f;

   public static ClampedNormalFloat a(float var0, float var1, float var2, float var3) {
      return new ClampedNormalFloat(var0, var1, var2, var3);
   }

   private ClampedNormalFloat(float var0, float var1, float var2, float var3) {
      this.b = var0;
      this.d = var1;
      this.e = var2;
      this.f = var3;
   }

   @Override
   public float a(RandomSource var0) {
      return a(var0, this.b, this.d, this.e, this.f);
   }

   public static float a(RandomSource var0, float var1, float var2, float var3, float var4) {
      return MathHelper.a(MathHelper.c(var0, var1, var2), var3, var4);
   }

   @Override
   public float a() {
      return this.e;
   }

   @Override
   public float b() {
      return this.f;
   }

   @Override
   public FloatProviderType<?> c() {
      return FloatProviderType.c;
   }

   @Override
   public String toString() {
      return "normal(" + this.b + ", " + this.d + ") in [" + this.e + "-" + this.f + "]";
   }
}
