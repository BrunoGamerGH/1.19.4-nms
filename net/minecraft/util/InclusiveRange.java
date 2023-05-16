package net.minecraft.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.function.Function;

public record InclusiveRange<T extends Comparable<T>>(T minInclusive, T maxInclusive) {
   private final T b;
   private final T c;
   public static final Codec<InclusiveRange<Integer>> a = a(Codec.INT);

   public InclusiveRange(T var0, T var1) {
      if (var0.compareTo(var1) > 0) {
         throw new IllegalArgumentException("min_inclusive must be less than or equal to max_inclusive");
      } else {
         this.b = var0;
         this.c = var1;
      }
   }

   public static <T extends Comparable<T>> Codec<InclusiveRange<T>> a(Codec<T> var0) {
      return ExtraCodecs.a(var0, "min_inclusive", "max_inclusive", InclusiveRange::a, InclusiveRange::a, InclusiveRange::b);
   }

   public static <T extends Comparable<T>> Codec<InclusiveRange<T>> a(Codec<T> var0, T var1, T var2) {
      return ExtraCodecs.a(
         a(var0),
         (Function<InclusiveRange<T>, DataResult<InclusiveRange<T>>>)(var2x -> {
            if (var2x.a().compareTo(var1) < 0) {
               return DataResult.error(() -> "Range limit too low, expected at least " + var1 + " [" + var2x.a() + "-" + var2x.b() + "]");
            } else {
               return var2x.b().compareTo(var2) > 0
                  ? DataResult.error(() -> "Range limit too high, expected at most " + var2 + " [" + var2x.a() + "-" + var2x.b() + "]")
                  : DataResult.success(var2x);
            }
         })
      );
   }

   public static <T extends Comparable<T>> DataResult<InclusiveRange<T>> a(T var0, T var1) {
      return var0.compareTo(var1) <= 0
         ? DataResult.success(new InclusiveRange(var0, var1))
         : DataResult.error(() -> "min_inclusive must be less than or equal to max_inclusive");
   }

   public boolean a(T var0) {
      return var0.compareTo(this.b) >= 0 && var0.compareTo(this.c) <= 0;
   }

   public boolean a(InclusiveRange<T> var0) {
      return var0.a().compareTo(this.b) >= 0 && var0.c.compareTo(this.c) <= 0;
   }

   @Override
   public String toString() {
      return "[" + this.b + ", " + this.c + "]";
   }

   public T a() {
      return this.b;
   }

   public T b() {
      return this.c;
   }
}
