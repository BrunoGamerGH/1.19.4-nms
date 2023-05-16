package net.minecraft.gametest.framework;

import com.mojang.logging.LogUtils;
import net.minecraft.SystemUtils;
import org.slf4j.Logger;

public class GameTestHarnessLogger implements GameTestHarnessITestReporter {
   private static final Logger a = LogUtils.getLogger();

   @Override
   public void a(GameTestHarnessInfo var0) {
      if (var0.r()) {
         a.error("{} failed! {}", var0.c(), SystemUtils.c(var0.n()));
      } else {
         a.warn("(optional) {} failed. {}", var0.c(), SystemUtils.c(var0.n()));
      }
   }

   @Override
   public void b(GameTestHarnessInfo var0) {
   }
}
