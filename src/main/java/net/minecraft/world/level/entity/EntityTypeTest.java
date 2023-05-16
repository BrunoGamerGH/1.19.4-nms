package net.minecraft.world.level.entity;

import javax.annotation.Nullable;

public interface EntityTypeTest<B, T extends B> {
   static <B, T extends B> EntityTypeTest<B, T> a(final Class<T> var0) {
      return new EntityTypeTest<B, T>() {
         @Nullable
         @Override
         public T a(B var0x) {
            return (T)(var0.isInstance(var0) ? var0 : null);
         }

         @Override
         public Class<? extends B> a() {
            return var0;
         }
      };
   }

   @Nullable
   T a(B var1);

   Class<? extends B> a();
}
