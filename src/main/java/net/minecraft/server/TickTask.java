package net.minecraft.server;

public class TickTask implements Runnable {
   private final int a;
   private final Runnable b;

   public TickTask(int var0, Runnable var1) {
      this.a = var0;
      this.b = var1;
   }

   public int a() {
      return this.a;
   }

   @Override
   public void run() {
      this.b.run();
   }
}
