package net.minecraft.util;

import com.google.common.base.Suppliers;
import java.util.function.Supplier;

@Deprecated
public class LazyInitVar<T> {
   private final Supplier<T> a;

   public LazyInitVar(Supplier<T> var0) {
      this.a = Suppliers.memoize(var0::get);
   }

   public T a() {
      return this.a.get();
   }
}
