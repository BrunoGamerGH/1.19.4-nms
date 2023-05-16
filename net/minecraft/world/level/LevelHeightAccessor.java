package net.minecraft.world.level;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.SectionPosition;

public interface LevelHeightAccessor {
   int w_();

   int v_();

   default int ai() {
      return this.v_() + this.w_();
   }

   default int aj() {
      return this.al() - this.ak();
   }

   default int ak() {
      return SectionPosition.a(this.v_());
   }

   default int al() {
      return SectionPosition.a(this.ai() - 1) + 1;
   }

   default boolean u(BlockPosition var0) {
      return this.d(var0.v());
   }

   default boolean d(int var0) {
      return var0 < this.v_() || var0 >= this.ai();
   }

   default int e(int var0) {
      return this.f(SectionPosition.a(var0));
   }

   default int f(int var0) {
      return var0 - this.ak();
   }

   default int g(int var0) {
      return var0 + this.ak();
   }

   static LevelHeightAccessor e(final int var0, final int var1) {
      return new LevelHeightAccessor() {
         @Override
         public int w_() {
            return var1;
         }

         @Override
         public int v_() {
            return var0;
         }
      };
   }
}
