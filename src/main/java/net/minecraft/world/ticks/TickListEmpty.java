package net.minecraft.world.ticks;

import net.minecraft.core.BlockPosition;

public class TickListEmpty {
   private static final TickContainerAccess<Object> a = new TickContainerAccess<Object>() {
      @Override
      public void a(NextTickListEntry<Object> var0) {
      }

      @Override
      public boolean a(BlockPosition var0, Object var1) {
         return false;
      }

      @Override
      public int a() {
         return 0;
      }
   };
   private static final LevelTickAccess<Object> b = new LevelTickAccess<Object>() {
      @Override
      public void a(NextTickListEntry<Object> var0) {
      }

      @Override
      public boolean a(BlockPosition var0, Object var1) {
         return false;
      }

      @Override
      public boolean b(BlockPosition var0, Object var1) {
         return false;
      }

      @Override
      public int a() {
         return 0;
      }
   };

   public static <T> TickContainerAccess<T> a() {
      return a;
   }

   public static <T> LevelTickAccess<T> b() {
      return b;
   }
}
