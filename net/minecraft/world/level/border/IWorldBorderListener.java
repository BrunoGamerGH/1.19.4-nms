package net.minecraft.world.level.border;

public interface IWorldBorderListener {
   void a(WorldBorder var1, double var2);

   void a(WorldBorder var1, double var2, double var4, long var6);

   void a(WorldBorder var1, double var2, double var4);

   void a(WorldBorder var1, int var2);

   void b(WorldBorder var1, int var2);

   void b(WorldBorder var1, double var2);

   void c(WorldBorder var1, double var2);

   public static class a implements IWorldBorderListener {
      private final WorldBorder a;

      public a(WorldBorder var0) {
         this.a = var0;
      }

      @Override
      public void a(WorldBorder var0, double var1) {
         this.a.a(var1);
      }

      @Override
      public void a(WorldBorder var0, double var1, double var3, long var5) {
         this.a.a(var1, var3, var5);
      }

      @Override
      public void a(WorldBorder var0, double var1, double var3) {
         this.a.c(var1, var3);
      }

      @Override
      public void a(WorldBorder var0, int var1) {
         this.a.b(var1);
      }

      @Override
      public void b(WorldBorder var0, int var1) {
         this.a.c(var1);
      }

      @Override
      public void b(WorldBorder var0, double var1) {
         this.a.c(var1);
      }

      @Override
      public void c(WorldBorder var0, double var1) {
         this.a.b(var1);
      }
   }
}
