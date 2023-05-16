package net.minecraft.gametest.framework;

class ExhaustedAttemptsException extends Throwable {
   public ExhaustedAttemptsException(int var0, int var1, GameTestHarnessInfo var2) {
      super("Not enough successes: " + var1 + " out of " + var0 + " attempts. Required successes: " + var2.z() + ". max attempts: " + var2.y() + ".", var2.n());
   }
}
