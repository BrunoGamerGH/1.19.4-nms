package net.minecraft.util.valueproviders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Function;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;

public class ClampedNormalInt extends IntProvider {
   public static final Codec<ClampedNormalInt> a = RecordCodecBuilder.create(
         var0 -> var0.group(
                  Codec.FLOAT.fieldOf("mean").forGetter(var0x -> var0x.b),
                  Codec.FLOAT.fieldOf("deviation").forGetter(var0x -> var0x.f),
                  Codec.INT.fieldOf("min_inclusive").forGetter(var0x -> var0x.g),
                  Codec.INT.fieldOf("max_inclusive").forGetter(var0x -> var0x.h)
               )
               .apply(var0, ClampedNormalInt::new)
      )
      .comapFlatMap(
         var0 -> var0.h < var0.g ? DataResult.error(() -> "Max must be larger than min: [" + var0.g + ", " + var0.h + "]") : DataResult.success(var0),
         Function.identity()
      );
   private final float b;
   private final float f;
   private final int g;
   private final int h;

   public static ClampedNormalInt a(float var0, float var1, int var2, int var3) {
      return new ClampedNormalInt(var0, var1, var2, var3);
   }

   private ClampedNormalInt(float var0, float var1, int var2, int var3) {
      this.b = var0;
      this.f = var1;
      this.g = var2;
      this.h = var3;
   }

   @Override
   public int a(RandomSource var0) {
      return a(var0, this.b, this.f, (float)this.g, (float)this.h);
   }

   public static int a(RandomSource var0, float var1, float var2, float var3, float var4) {
      return (int)MathHelper.a(MathHelper.c(var0, var1, var2), var3, var4);
   }

   @Override
   public int a() {
      return this.g;
   }

   @Override
   public int b() {
      return this.h;
   }

   @Override
   public IntProviderType<?> c() {
      return IntProviderType.f;
   }

   @Override
   public String toString() {
      return "normal(" + this.b + ", " + this.f + ") in [" + this.g + "-" + this.h + "]";
   }
}
