package net.minecraft.gametest.framework;

import javax.annotation.Nullable;

class GameTestHarnessEvent {
   @Nullable
   public final Long a;
   public final Runnable b;

   private GameTestHarnessEvent(@Nullable Long var0, Runnable var1) {
      this.a = var0;
      this.b = var1;
   }

   static GameTestHarnessEvent a(Runnable var0) {
      return new GameTestHarnessEvent(null, var0);
   }

   static GameTestHarnessEvent a(long var0, Runnable var2) {
      return new GameTestHarnessEvent(var0, var2);
   }
}
