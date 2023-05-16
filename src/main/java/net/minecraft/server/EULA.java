package net.minecraft.server;

import com.mojang.logging.LogUtils;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import net.minecraft.SharedConstants;
import org.slf4j.Logger;

public class EULA {
   private static final Logger a = LogUtils.getLogger();
   private final Path b;
   private final boolean c;

   public EULA(Path var0) {
      this.b = var0;
      this.c = SharedConstants.aO || this.b();
   }

   private boolean b() {
      try {
         boolean var3;
         try (InputStream var0 = Files.newInputStream(this.b)) {
            Properties var1 = new Properties();
            var1.load(var0);
            var3 = Boolean.parseBoolean(var1.getProperty("eula", "false"));
         }

         return var3;
      } catch (Exception var6) {
         a.warn("Failed to load {}", this.b);
         this.c();
         return false;
      }
   }

   public boolean a() {
      return this.c;
   }

   private void c() {
      if (!SharedConstants.aO) {
         try (OutputStream var0 = Files.newOutputStream(this.b)) {
            Properties var1 = new Properties();
            var1.setProperty("eula", "false");
            var1.store(var0, "By changing the setting below to TRUE you are indicating your agreement to our EULA (https://aka.ms/MinecraftEULA).");
         } catch (Exception var6) {
            a.warn("Failed to save {}", this.b, var6);
         }
      }
   }
}
