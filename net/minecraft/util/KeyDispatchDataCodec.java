package net.minecraft.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;

public record KeyDispatchDataCodec<A>(Codec<A> codec) {
   private final Codec<A> a;

   public KeyDispatchDataCodec(Codec<A> var0) {
      this.a = var0;
   }

   public static <A> KeyDispatchDataCodec<A> a(Codec<A> var0) {
      return new KeyDispatchDataCodec<>(var0);
   }

   public static <A> KeyDispatchDataCodec<A> a(MapCodec<A> var0) {
      return new KeyDispatchDataCodec<>(var0.codec());
   }
}
