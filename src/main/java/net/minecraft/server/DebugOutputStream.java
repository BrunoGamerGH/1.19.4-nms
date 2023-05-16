package net.minecraft.server;

import com.mojang.logging.LogUtils;
import java.io.OutputStream;
import org.slf4j.Logger;

public class DebugOutputStream extends RedirectStream {
   private static final Logger b = LogUtils.getLogger();

   public DebugOutputStream(String var0, OutputStream var1) {
      super(var0, var1);
   }

   @Override
   protected void a(String var0) {
      StackTraceElement[] var1 = Thread.currentThread().getStackTrace();
      StackTraceElement var2 = var1[Math.min(3, var1.length)];
      b.info("[{}]@.({}:{}): {}", new Object[]{this.a, var2.getFileName(), var2.getLineNumber(), var0});
   }
}
