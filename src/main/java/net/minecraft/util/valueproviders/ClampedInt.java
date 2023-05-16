package net.minecraft.util.valueproviders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Function;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;

public class ClampedInt extends IntProvider {
   public static final Codec<ClampedInt> a = RecordCodecBuilder.create(
         var0 -> var0.group(
                  IntProvider.c.fieldOf("source").forGetter(var0x -> var0x.b),
                  Codec.INT.fieldOf("min_inclusive").forGetter(var0x -> var0x.f),
                  Codec.INT.fieldOf("max_inclusive").forGetter(var0x -> var0x.g)
               )
               .apply(var0, ClampedInt::new)
      )
      .comapFlatMap(
         var0 -> var0.g < var0.f
               ? DataResult.error(() -> "Max must be at least min, min_inclusive: " + var0.f + ", max_inclusive: " + var0.g)
               : DataResult.success(var0),
         Function.identity()
      );
   private final IntProvider b;
   private final int f;
   private final int g;

   public static ClampedInt a(IntProvider var0, int var1, int var2) {
      return new ClampedInt(var0, var1, var2);
   }

   public ClampedInt(IntProvider var0, int var1, int var2) {
      this.b = var0;
      this.f = var1;
      this.g = var2;
   }

   @Override
   public int a(RandomSource var0) {
      return MathHelper.a(this.b.a(var0), this.f, this.g);
   }

   @Override
   public int a() {
      return Math.max(this.f, this.b.a());
   }

   @Override
   public int b() {
      return Math.min(this.g, this.b.b());
   }

   @Override
   public IntProviderType<?> c() {
      return IntProviderType.d;
   }
}
