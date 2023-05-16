package net.minecraft.server;

import com.mojang.logging.LogUtils;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.annotation.Nullable;
import org.slf4j.Logger;

public class RedirectStream extends PrintStream {
   private static final Logger b = LogUtils.getLogger();
   protected final String a;

   public RedirectStream(String var0, OutputStream var1) {
      super(var1);
      this.a = var0;
   }

   @Override
   public void println(@Nullable String var0) {
      this.a(var0);
   }

   @Override
   public void println(Object var0) {
      this.a(String.valueOf(var0));
   }

   protected void a(@Nullable String var0) {
      b.info("[{}]: {}", this.a, var0);
   }
}
