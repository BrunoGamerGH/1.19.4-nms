package net.minecraft;

import java.lang.Thread.UncaughtExceptionHandler;
import org.slf4j.Logger;

public class DefaultUncaughtExceptionHandler implements UncaughtExceptionHandler {
   private final Logger a;

   public DefaultUncaughtExceptionHandler(Logger var0) {
      this.a = var0;
   }

   @Override
   public void uncaughtException(Thread var0, Throwable var1) {
      this.a.error("Caught previously unhandled exception :", var1);
   }
}
