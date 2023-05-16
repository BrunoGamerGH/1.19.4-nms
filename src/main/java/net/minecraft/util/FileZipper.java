package net.minecraft.util;

import com.google.common.collect.ImmutableMap;
import com.mojang.logging.LogUtils;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.SystemUtils;
import org.slf4j.Logger;

public class FileZipper implements Closeable {
   private static final Logger a = LogUtils.getLogger();
   private final Path b;
   private final Path c;
   private final FileSystem d;

   public FileZipper(Path var0) {
      this.b = var0;
      this.c = var0.resolveSibling(var0.getFileName().toString() + "_tmp");

      try {
         this.d = SystemUtils.d.newFileSystem(this.c, ImmutableMap.of("create", "true"));
      } catch (IOException var3) {
         throw new UncheckedIOException(var3);
      }
   }

   public void a(Path var0, String var1) {
      try {
         Path var2 = this.d.getPath(File.separator);
         Path var3 = var2.resolve(var0.toString());
         Files.createDirectories(var3.getParent());
         Files.write(var3, var1.getBytes(StandardCharsets.UTF_8));
      } catch (IOException var5) {
         throw new UncheckedIOException(var5);
      }
   }

   public void a(Path var0, File var1) {
      try {
         Path var2 = this.d.getPath(File.separator);
         Path var3 = var2.resolve(var0.toString());
         Files.createDirectories(var3.getParent());
         Files.copy(var1.toPath(), var3);
      } catch (IOException var5) {
         throw new UncheckedIOException(var5);
      }
   }

   public void a(Path var0) {
      try {
         Path var1 = this.d.getPath(File.separator);
         if (Files.isRegularFile(var0)) {
            Path var2 = var1.resolve(var0.getParent().relativize(var0).toString());
            Files.copy(var2, var0);
         } else {
            try (Stream<Path> var2 = Files.find(var0, Integer.MAX_VALUE, (var0x, var1x) -> var1x.isRegularFile())) {
               for(Path var4 : var2.collect(Collectors.toList())) {
                  Path var5 = var1.resolve(var0.relativize(var4).toString());
                  Files.createDirectories(var5.getParent());
                  Files.copy(var4, var5);
               }
            }
         }
      } catch (IOException var9) {
         throw new UncheckedIOException(var9);
      }
   }

   @Override
   public void close() {
      try {
         this.d.close();
         Files.move(this.c, this.b);
         a.info("Compressed to {}", this.b);
      } catch (IOException var2) {
         throw new UncheckedIOException(var2);
      }
   }
}
