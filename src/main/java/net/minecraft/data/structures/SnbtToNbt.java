package net.minecraft.data.structures;

import com.google.common.collect.Lists;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import com.mojang.logging.LogUtils;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DebugReportProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTCompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;

public class SnbtToNbt implements DebugReportProvider {
   @Nullable
   private static final Path d = null;
   private static final Logger e = LogUtils.getLogger();
   private final PackOutput f;
   private final Iterable<Path> g;
   private final List<SnbtToNbt.a> h = Lists.newArrayList();

   public SnbtToNbt(PackOutput var0, Iterable<Path> var1) {
      this.f = var0;
      this.g = var1;
   }

   public SnbtToNbt a(SnbtToNbt.a var0) {
      this.h.add(var0);
      return this;
   }

   private NBTTagCompound a(String var0, NBTTagCompound var1) {
      NBTTagCompound var2 = var1;

      for(SnbtToNbt.a var4 : this.h) {
         var2 = var4.apply(var0, var2);
      }

      return var2;
   }

   @Override
   public CompletableFuture<?> a(CachedOutput var0) {
      Path var1 = this.f.a();
      List<CompletableFuture<?>> var2 = Lists.newArrayList();

      for(Path var4 : this.g) {
         var2.add(CompletableFuture.<CompletableFuture>supplyAsync(() -> {
            try {
               CompletableFuture var5x;
               try (Stream<Path> var3x = Files.walk(var4)) {
                  var5x = CompletableFuture.allOf(var3x.filter(var0xx -> var0xx.toString().endsWith(".snbt")).map(var3xx -> CompletableFuture.runAsync(() -> {
                        SnbtToNbt.c var4x = this.a(var3xx, this.a(var4, var3xx));
                        this.a(var0, var4x, var1);
                     }, SystemUtils.f())).toArray(var0xx -> new CompletableFuture[var0xx]));
               }

               return var5x;
            } catch (Exception var9) {
               throw new RuntimeException("Failed to read structure input directory, aborting", var9);
            }
         }, SystemUtils.f()).thenCompose(var0x -> var0x));
      }

      return SystemUtils.c(var2);
   }

   @Override
   public final String a() {
      return "SNBT -> NBT";
   }

   private String a(Path var0, Path var1) {
      String var2 = var0.relativize(var1).toString().replaceAll("\\\\", "/");
      return var2.substring(0, var2.length() - ".snbt".length());
   }

   private SnbtToNbt.c a(Path var0, String var1) {
      try {
         SnbtToNbt.c var11;
         try (BufferedReader var2 = Files.newBufferedReader(var0)) {
            String var3 = IOUtils.toString(var2);
            NBTTagCompound var4 = this.a(var1, GameProfileSerializer.a(var3));
            ByteArrayOutputStream var5 = new ByteArrayOutputStream();
            HashingOutputStream var6 = new HashingOutputStream(Hashing.sha1(), var5);
            NBTCompressedStreamTools.a(var4, var6);
            byte[] var7 = var5.toByteArray();
            HashCode var8 = var6.hash();
            String var9;
            if (d != null) {
               var9 = GameProfileSerializer.c(var4);
            } else {
               var9 = null;
            }

            var11 = new SnbtToNbt.c(var1, var7, var9, var8);
         }

         return var11;
      } catch (Throwable var14) {
         throw new SnbtToNbt.b(var0, var14);
      }
   }

   private void a(CachedOutput var0, SnbtToNbt.c var1, Path var2) {
      if (var1.c != null) {
         Path var3 = d.resolve(var1.a + ".snbt");

         try {
            DebugReportNBT.a(CachedOutput.a, var3, var1.c);
         } catch (IOException var7) {
            e.error("Couldn't write structure SNBT {} at {}", new Object[]{var1.a, var3, var7});
         }
      }

      Path var3 = var2.resolve(var1.a + ".nbt");

      try {
         var0.writeIfNeeded(var3, var1.b, var1.d);
      } catch (IOException var6) {
         e.error("Couldn't write structure {} at {}", new Object[]{var1.a, var3, var6});
      }
   }

   @FunctionalInterface
   public interface a {
      NBTTagCompound apply(String var1, NBTTagCompound var2);
   }

   static class b extends RuntimeException {
      public b(Path var0, Throwable var1) {
         super(var0.toAbsolutePath().toString(), var1);
      }
   }

   static record c(String name, byte[] payload, @Nullable String snbtPayload, HashCode hash) {
      final String a;
      final byte[] b;
      @Nullable
      final String c;
      final HashCode d;

      c(String var0, byte[] var1, @Nullable String var2, HashCode var3) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
         this.d = var3;
      }
   }
}
