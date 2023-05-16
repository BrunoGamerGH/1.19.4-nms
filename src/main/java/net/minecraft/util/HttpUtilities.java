package net.minecraft.util;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mojang.logging.LogUtils;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.ServerSocket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import javax.annotation.Nullable;
import net.minecraft.DefaultUncaughtExceptionHandler;
import net.minecraft.network.chat.IChatBaseComponent;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;

public class HttpUtilities {
   private static final Logger b = LogUtils.getLogger();
   public static final ListeningExecutorService a = MoreExecutors.listeningDecorator(
      Executors.newCachedThreadPool(
         new ThreadFactoryBuilder().setDaemon(true).setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(b)).setNameFormat("Downloader %d").build()
      )
   );

   private HttpUtilities() {
   }

   public static CompletableFuture<?> a(File var0, URL var1, Map<String, String> var2, int var3, @Nullable IProgressUpdate var4, Proxy var5) {
      return CompletableFuture.supplyAsync(() -> {
         HttpURLConnection var6 = null;
         InputStream var7 = null;
         OutputStream var8 = null;
         if (var4 != null) {
            var4.b(IChatBaseComponent.c("resourcepack.downloading"));
            var4.c(IChatBaseComponent.c("resourcepack.requesting"));
         }

         try {
            byte[] var9 = new byte[4096];
            var6 = (HttpURLConnection)var1.openConnection(var5);
            var6.setInstanceFollowRedirects(true);
            float var10 = 0.0F;
            float var11 = (float)var2.entrySet().size();

            for(Entry<String, String> var13 : var2.entrySet()) {
               var6.setRequestProperty(var13.getKey(), var13.getValue());
               if (var4 != null) {
                  var4.a((int)(++var10 / var11 * 100.0F));
               }
            }

            var7 = var6.getInputStream();
            var11 = (float)var6.getContentLength();
            int var12 = var6.getContentLength();
            if (var4 != null) {
               var4.c(IChatBaseComponent.a("resourcepack.progress", String.format(Locale.ROOT, "%.2f", var11 / 1000.0F / 1000.0F)));
            }

            if (var0.exists()) {
               long var13 = var0.length();
               if (var13 == (long)var12) {
                  if (var4 != null) {
                     var4.a();
                  }

                  return null;
               }

               b.warn("Deleting {} as it does not match what we currently have ({} vs our {}).", new Object[]{var0, var12, var13});
               FileUtils.deleteQuietly(var0);
            } else if (var0.getParentFile() != null) {
               var0.getParentFile().mkdirs();
            }

            var8 = new DataOutputStream(new FileOutputStream(var0));
            if (var3 > 0 && var11 > (float)var3) {
               if (var4 != null) {
                  var4.a();
               }

               throw new IOException("Filesize is bigger than maximum allowed (file is " + var10 + ", limit is " + var3 + ")");
            } else {
               int var13;
               while((var13 = var7.read(var9)) >= 0) {
                  var10 += (float)var13;
                  if (var4 != null) {
                     var4.a((int)(var10 / var11 * 100.0F));
                  }

                  if (var3 > 0 && var10 > (float)var3) {
                     if (var4 != null) {
                        var4.a();
                     }

                     throw new IOException("Filesize was bigger than maximum allowed (got >= " + var10 + ", limit was " + var3 + ")");
                  }

                  if (Thread.interrupted()) {
                     b.error("INTERRUPTED");
                     if (var4 != null) {
                        var4.a();
                     }

                     return null;
                  }

                  var8.write(var9, 0, var13);
               }

               if (var4 != null) {
                  var4.a();
               }

               return null;
            }
         } catch (Throwable var21) {
            b.error("Failed to download file", var21);
            if (var6 != null) {
               InputStream var10 = var6.getErrorStream();

               try {
                  b.error("HTTP response error: {}", IOUtils.toString(var10, StandardCharsets.UTF_8));
               } catch (IOException var20) {
                  b.error("Failed to read response from server");
               }
            }

            if (var4 != null) {
               var4.a();
            }

            return null;
         } finally {
            IOUtils.closeQuietly(var7);
            IOUtils.closeQuietly(var8);
         }
      }, a);
   }

   public static int a() {
      try {
         int var1;
         try (ServerSocket var0 = new ServerSocket(0)) {
            var1 = var0.getLocalPort();
         }

         return var1;
      } catch (IOException var5) {
         return 25564;
      }
   }

   public static boolean a(int var0) {
      if (var0 >= 0 && var0 <= 65535) {
         try {
            boolean var2;
            try (ServerSocket var1 = new ServerSocket(var0)) {
               var2 = var1.getLocalPort() == var0;
            }

            return var2;
         } catch (IOException var6) {
            return false;
         }
      } else {
         return false;
      }
   }
}
