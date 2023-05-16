package net.minecraft.world;

import javax.annotation.Nullable;

public interface Clearable {
   void a();

   static void a_(@Nullable Object var0) {
      if (var0 instanceof Clearable) {
         ((Clearable)var0).a();
      }
   }
}
