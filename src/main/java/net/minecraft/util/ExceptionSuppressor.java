package net.minecraft.util;

import javax.annotation.Nullable;

public class ExceptionSuppressor<T extends Throwable> {
   @Nullable
   private T a;

   public void a(T var0) {
      if (this.a == null) {
         this.a = var0;
      } else {
         this.a.addSuppressed(var0);
      }
   }

   public void a() throws T {
      if (this.a != null) {
         throw this.a;
      }
   }
}
