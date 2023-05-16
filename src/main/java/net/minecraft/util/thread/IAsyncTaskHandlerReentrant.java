package net.minecraft.util.thread;

public abstract class IAsyncTaskHandlerReentrant<R extends Runnable> extends IAsyncTaskHandler<R> {
   private int b;

   public IAsyncTaskHandlerReentrant(String var0) {
      super(var0);
   }

   @Override
   public boolean at() {
      return this.bt() || super.at();
   }

   protected boolean bt() {
      return this.b != 0;
   }

   @Override
   public void d(R var0) {
      ++this.b;

      try {
         super.d(var0);
      } finally {
         --this.b;
      }
   }
}
