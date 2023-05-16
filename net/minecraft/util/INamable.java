package net.minecraft.util;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Keyable;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public interface INamable {
   int W = 16;

   String c();

   static <E extends Enum<E> & INamable> INamable.a<E> a(Supplier<E[]> var0) {
      return a(var0, var0x -> var0x);
   }

   static <E extends Enum<E> & INamable> INamable.a<E> a(Supplier<E[]> var0, Function<String, String> var1) {
      E[] var2 = (E[])var0.get();
      if (var2.length > 16) {
         Map<String, E> var3 = Arrays.stream(var2)
            .collect(Collectors.toMap(var1x -> var1.apply(((INamable)var1x).c()), (Function<? super Enum, ? extends Enum>)(var0x -> var0x)));
         return new INamable.a<>(var2, var1x -> var1x == null ? null : var3.get(var1x));
      } else {
         return new INamable.a<>(var2, var2x -> {
            for(E var6 : var2) {
               if (var1.apply(var6.c()).equals(var2x)) {
                  return var6;
               }
            }

            return null;
         });
      }
   }

   static Keyable a(final INamable[] var0) {
      return new Keyable() {
         public <T> Stream<T> keys(DynamicOps<T> var0x) {
            return Arrays.stream(var0).map(INamable::c).map(var0::createString);
         }
      };
   }

   @Deprecated
   public static class a<E extends Enum<E> & INamable> implements Codec<E> {
      private final Codec<E> a;
      private final Function<String, E> b;

      public a(E[] var0, Function<String, E> var1) {
         this.a = ExtraCodecs.b(
            ExtraCodecs.a((Function<E, String>)(var0x -> var0x.c()), var1),
            ExtraCodecs.a(var0x -> var0x.ordinal(), var1x -> var1x >= 0 && var1x < var0.length ? var0[var1x] : null, -1)
         );
         this.b = var1;
      }

      public <T> DataResult<Pair<E, T>> decode(DynamicOps<T> var0, T var1) {
         return this.a.decode(var0, var1);
      }

      public <T> DataResult<T> a(E var0, DynamicOps<T> var1, T var2) {
         return this.a.encode(var0, var1, var2);
      }

      @Nullable
      public E a(@Nullable String var0) {
         return this.b.apply(var0);
      }

      public E a(@Nullable String var0, E var1) {
         return Objects.requireNonNullElse(this.a(var0), var1);
      }
   }
}
