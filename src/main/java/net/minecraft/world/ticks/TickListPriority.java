package net.minecraft.world.ticks;

public enum TickListPriority {
   a(-3),
   b(-2),
   c(-1),
   d(0),
   e(1),
   f(2),
   g(3);

   private final int h;

   private TickListPriority(int var2) {
      this.h = var2;
   }

   public static TickListPriority a(int var0) {
      for(TickListPriority var4 : values()) {
         if (var4.h == var0) {
            return var4;
         }
      }

      return var0 < a.h ? a : g;
   }

   public int a() {
      return this.h;
   }
}
