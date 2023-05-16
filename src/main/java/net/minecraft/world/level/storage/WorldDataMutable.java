package net.minecraft.world.level.storage;

import net.minecraft.core.BlockPosition;

public interface WorldDataMutable extends WorldData {
   void b(int var1);

   void c(int var1);

   void d(int var1);

   void a(float var1);

   default void a(BlockPosition var0, float var1) {
      this.b(var0.u());
      this.c(var0.v());
      this.d(var0.w());
      this.a(var1);
   }
}
