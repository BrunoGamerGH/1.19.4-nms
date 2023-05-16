package org.bukkit.craftbukkit.v1_19_R3.map;

import com.google.common.base.Preconditions;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;
import net.minecraft.SystemUtils;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapPalette.MapColorCache;

public class CraftMapColorCache implements MapColorCache {
   private static final String MD5_CACHE_HASH = "E88EDD068D12D39934B40E8B6B124C83";
   private static final File CACHE_FILE = new File("map-color-cache.dat");
   private byte[] cache;
   private final Logger logger;
   private boolean cached = false;
   private final AtomicBoolean running = new AtomicBoolean(false);

   public CraftMapColorCache(Logger logger) {
      this.logger = logger;
   }

   public static void main(String[] args) {
      CraftMapColorCache craftMapColorCache = new CraftMapColorCache(Logger.getGlobal());
      craftMapColorCache.buildCache();

      try {
         byte[] hash = MessageDigest.getInstance("MD5").digest(craftMapColorCache.cache);
         System.out.println("MD5_CACHE_HASH: " + bytesToString(hash));
      } catch (NoSuchAlgorithmException var3) {
         var3.printStackTrace();
      }
   }

   public static String bytesToString(byte[] bytes) {
      char[] chars = "0123456789ABCDEF".toCharArray();
      StringBuilder builder = new StringBuilder();

      for(byte value : bytes) {
         int first = (value & 240) >> 4;
         int second = value & 15;
         builder.append(chars[first]);
         builder.append(chars[second]);
      }

      return builder.toString();
   }

   // $QF: Could not inline inconsistent finally blocks
   // Please report this to the Quiltflower issue tracker, at https://github.com/QuiltMC/quiltflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public CompletableFuture<Void> initCache() {
      Preconditions.checkState(!this.cached && !this.running.getAndSet(true), "Cache is already build or is currently being build");
      this.cache = new byte[16777216];
      if (CACHE_FILE.exists()) {
         byte[] fileContent;
         try {
            Throwable hash = null;
            Object e = null;

            try {
               InputStream inputStream = new InflaterInputStream(new FileInputStream(CACHE_FILE));

               try {
                  fileContent = inputStream.readAllBytes();
               } finally {
                  if (inputStream != null) {
                     inputStream.close();
                  }
               }
            } catch (Throwable var15) {
               if (hash == null) {
                  hash = var15;
               } else if (hash != var15) {
                  hash.addSuppressed(var15);
               }

               throw hash;
            }
         } catch (IOException var16) {
            this.logger.warning("Error while reading map color cache");
            var16.printStackTrace();
            return CompletableFuture.completedFuture(null);
         }

         byte[] hash;
         try {
            hash = MessageDigest.getInstance("MD5").digest(fileContent);
         } catch (NoSuchAlgorithmException var13) {
            this.logger.warning("Error while hashing map color cache");
            var13.printStackTrace();
            return CompletableFuture.completedFuture(null);
         }

         if (!"E88EDD068D12D39934B40E8B6B124C83".equals(bytesToString(hash))) {
            this.logger.info("Map color cache hash invalid, rebuilding cache in the background");
            return this.buildAndSaveCache();
         } else {
            System.arraycopy(fileContent, 0, this.cache, 0, fileContent.length);
            this.cached = true;
            return CompletableFuture.completedFuture(null);
         }
      } else {
         this.logger.info("Map color cache not found, building it in the background");
         return this.buildAndSaveCache();
      }
   }

   private void buildCache() {
      for(int r = 0; r < 256; ++r) {
         for(int g = 0; g < 256; ++g) {
            for(int b = 0; b < 256; ++b) {
               Color color = new Color(r, g, b);
               this.cache[this.toInt(color)] = MapPalette.matchColor(color);
            }
         }
      }
   }

   private CompletableFuture<Void> buildAndSaveCache() {
      return CompletableFuture.runAsync(() -> {
         this.buildCache();
         if (!CACHE_FILE.exists()) {
            try {
               if (!CACHE_FILE.createNewFile()) {
                  this.cached = true;
                  return;
               }
            } catch (IOException var12) {
               this.logger.warning("Error while building map color cache");
               var12.printStackTrace();
               this.cached = true;
               return;
            }
         }

         try {
            Throwable e = null;
            Object var2 = null;

            try {
               OutputStream outputStream = new DeflaterOutputStream(new FileOutputStream(CACHE_FILE));

               try {
                  outputStream.write(this.cache);
               } finally {
                  if (outputStream != null) {
                     outputStream.close();
                  }
               }
            } catch (Throwable var14) {
               if (e == null) {
                  e = var14;
               } else if (e != var14) {
                  e.addSuppressed(var14);
               }

               throw e;
            }
         } catch (IOException var15) {
            this.logger.warning("Error while building map color cache");
            var15.printStackTrace();
            this.cached = true;
            return;
         }

         this.cached = true;
         this.logger.info("Map color cache build successfully");
      }, SystemUtils.f());
   }

   private int toInt(Color color) {
      return color.getRGB() & 16777215;
   }

   public boolean isCached() {
      return this.cached || !this.running.get() && this.initCache().isDone();
   }

   public byte matchColor(Color color) {
      Preconditions.checkState(this.isCached(), "Cache not build jet");
      return this.cache[this.toInt(color)];
   }
}
