package net.minecraft.gametest.framework;

public class GlobalTestReporter {
   private static GameTestHarnessITestReporter a = new GameTestHarnessLogger();

   public static void a(GameTestHarnessITestReporter var0) {
      a = var0;
   }

   public static void a(GameTestHarnessInfo var0) {
      a.a(var0);
   }

   public static void b(GameTestHarnessInfo var0) {
      a.b(var0);
   }

   public static void a() {
      a.a();
   }
}
