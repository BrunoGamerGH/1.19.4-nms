package net.minecraft.util;

import java.util.Objects;
import java.util.function.Function;
import javax.annotation.Nullable;

public class SingleKeyCache<K, V> {
   private final Function<K, V> a;
   @Nullable
   private K b = (K)null;
   @Nullable
   private V c;

   public SingleKeyCache(Function<K, V> var0) {
      this.a = var0;
   }

   public V a(K var0) {
      if (this.c == null || !Objects.equals(this.b, var0)) {
         this.c = this.a.apply(var0);
         this.b = var0;
      }

      return this.c;
   }
}
