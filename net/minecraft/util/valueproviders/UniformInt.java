package net.minecraft.util.valueproviders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Function;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;

public class UniformInt extends IntProvider {
   public static final Codec<UniformInt> a = RecordCodecBuilder.create(
         var0 -> var0.group(Codec.INT.fieldOf("min_inclusive").forGetter(var0x -> var0x.b), Codec.INT.fieldOf("max_inclusive").forGetter(var0x -> var0x.f))
               .apply(var0, UniformInt::new)
      )
      .comapFlatMap(
         var0 -> var0.f < var0.b
               ? DataResult.error(() -> "Max must be at least min, min_inclusive: " + var0.b + ", max_inclusive: " + var0.f)
               : DataResult.success(var0),
         Function.identity()
      );
   private final int b;
   private final int f;

   private UniformInt(int var0, int var1) {
      this.b = var0;
      this.f = var1;
   }

   public static UniformInt a(int var0, int var1) {
      return new UniformInt(var0, var1);
   }

   @Override
   public int a(RandomSource var0) {
      return MathHelper.b(var0, this.b, this.f);
   }

   @Override
   public int a() {
      return this.b;
   }

   @Override
   public int b() {
      return this.f;
   }

   @Override
   public IntProviderType<?> c() {
      return IntProviderType.b;
   }

   @Override
   public String toString() {
      return "[" + this.b + "-" + this.f + "]";
   }
}
