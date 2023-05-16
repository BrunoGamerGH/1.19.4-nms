package net.minecraft.world.item;

public interface TooltipFlag {
   TooltipFlag.a a = new TooltipFlag.a(false, false);
   TooltipFlag.a b = new TooltipFlag.a(true, false);

   boolean a();

   boolean b();

   public static record a(boolean advanced, boolean creative) implements TooltipFlag {
      private final boolean c;
      private final boolean d;

      public a(boolean var0, boolean var1) {
         this.c = var0;
         this.d = var1;
      }

      @Override
      public boolean a() {
         return this.c;
      }

      @Override
      public boolean b() {
         return this.d;
      }

      public TooltipFlag.a c() {
         return new TooltipFlag.a(this.c, true);
      }

      public boolean d() {
         return this.c;
      }

      public boolean e() {
         return this.d;
      }
   }
}
