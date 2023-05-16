package net.minecraft.util.valueproviders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Function;
import net.minecraft.util.RandomSource;

public class BiasedToBottomInt extends IntProvider {
   public static final Codec<BiasedToBottomInt> a = RecordCodecBuilder.create(
         var0 -> var0.group(Codec.INT.fieldOf("min_inclusive").forGetter(var0x -> var0x.b), Codec.INT.fieldOf("max_inclusive").forGetter(var0x -> var0x.f))
               .apply(var0, BiasedToBottomInt::new)
      )
      .comapFlatMap(
         var0 -> var0.f < var0.b
               ? DataResult.error(() -> "Max must be at least min, min_inclusive: " + var0.b + ", max_inclusive: " + var0.f)
               : DataResult.success(var0),
         Function.identity()
      );
   private final int b;
   private final int f;

   private BiasedToBottomInt(int var0, int var1) {
      this.b = var0;
      this.f = var1;
   }

   public static BiasedToBottomInt a(int var0, int var1) {
      return new BiasedToBottomInt(var0, var1);
   }

   @Override
   public int a(RandomSource var0) {
      return this.b + var0.a(var0.a(this.f - this.b + 1) + 1);
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
      return IntProviderType.c;
   }

   @Override
   public String toString() {
      return "[" + this.b + "-" + this.f + "]";
   }
}
