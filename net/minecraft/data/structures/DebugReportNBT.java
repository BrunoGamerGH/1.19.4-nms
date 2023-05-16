package net.minecraft.data.structures;

import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import com.mojang.logging.LogUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
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
import org.slf4j.Logger;

public class DebugReportNBT implements DebugReportProvider {
   private static final Logger d = LogUtils.getLogger();
   private final Iterable<Path> e;
   private final PackOutput f;

   public DebugReportNBT(PackOutput var0, Collection<Path> var1) {
      this.e = var1;
      this.f = var0;
   }

   @Override
   public CompletableFuture<?> a(CachedOutput var0) {
      Path var1 = this.f.a();
      List<CompletableFuture<?>> var2 = new ArrayList<>();

      for(Path var4 : this.e) {
         var2.add(
            CompletableFuture.<CompletableFuture>supplyAsync(
                  () -> {
                     try {
                        CompletableFuture var4x;
                        try (Stream<Path> var3x = Files.walk(var4)) {
                           var4x = CompletableFuture.allOf(
                              var3x.filter(var0xx -> var0xx.toString().endsWith(".nbt"))
                                 .map(var3xx -> CompletableFuture.runAsync(() -> a(var0, var3xx, a(var4, var3xx), var1), SystemUtils.g()))
                                 .toArray(var0xx -> new CompletableFuture[var0xx])
                           );
                        }
         
                        return var4x;
                     } catch (IOException var8) {
                        d.error("Failed to read structure input directory", var8);
                        return CompletableFuture.completedFuture(null);
                     }
                  },
                  SystemUtils.f()
               )
               .thenCompose(var0x -> var0x)
         );
      }

      return CompletableFuture.allOf(var2.toArray(var0x -> new CompletableFuture[var0x]));
   }

   @Override
   public final String a() {
      return "NBT -> SNBT";
   }

   private static String a(Path var0, Path var1) {
      String var2 = var0.relativize(var1).toString().replaceAll("\\\\", "/");
      return var2.substring(0, var2.length() - ".nbt".length());
   }

   @Nullable
   public static Path a(CachedOutput var0, Path var1, String var2, Path var3) {
      try {
         Path var6;
         try (InputStream var4 = Files.newInputStream(var1)) {
            Path var5 = var3.resolve(var2 + ".snbt");
            a(var0, var5, GameProfileSerializer.c(NBTCompressedStreamTools.a(var4)));
            d.info("Converted {} from NBT to SNBT", var2);
            var6 = var5;
         }

         return var6;
      } catch (IOException var9) {
         d.error("Couldn't convert {} from NBT to SNBT at {}", new Object[]{var2, var1, var9});
         return null;
      }
   }

   public static void a(CachedOutput var0, Path var1, String var2) throws IOException {
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();
      HashingOutputStream var4 = new HashingOutputStream(Hashing.sha1(), var3);
      var4.write(var2.getBytes(StandardCharsets.UTF_8));
      var4.write(10);
      var0.writeIfNeeded(var1, var3.toByteArray(), var4.hash());
   }
}
