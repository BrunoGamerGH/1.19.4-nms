package net.minecraft.server.packs.repository;

import com.mojang.logging.LogUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.FileUtils;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.packs.EnumResourcePackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.ResourcePackFile;
import net.minecraft.server.packs.linkfs.LinkFileSystem;
import org.slf4j.Logger;

public class ResourcePackSourceFolder implements ResourcePackSource {
   private static final Logger a = LogUtils.getLogger();
   private final Path b;
   private final EnumResourcePackType c;
   private final PackSource d;

   public ResourcePackSourceFolder(Path var0, EnumResourcePackType var1, PackSource var2) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
   }

   private static String a(Path var0) {
      return var0.getFileName().toString();
   }

   @Override
   public void a(Consumer<ResourcePackLoader> var0) {
      try {
         FileUtils.c(this.b);
         a(
            this.b,
            false,
            (var1x, var2) -> {
               String var3x = a(var1x);
               ResourcePackLoader var4 = ResourcePackLoader.a(
                  "file/" + var3x, IChatBaseComponent.b(var3x), false, var2, this.c, ResourcePackLoader.Position.a, this.d
               );
               if (var4 != null) {
                  var0.accept(var4);
               }
            }
         );
      } catch (IOException var3) {
         a.warn("Failed to list packs in {}", this.b, var3);
      }
   }

   public static void a(Path var0, boolean var1, BiConsumer<Path, ResourcePackLoader.c> var2) throws IOException {
      try (DirectoryStream<Path> var3 = Files.newDirectoryStream(var0)) {
         for(Path var5 : var3) {
            ResourcePackLoader.c var6 = a(var5, var1);
            if (var6 != null) {
               var2.accept(var5, var6);
            }
         }
      }
   }

   @Nullable
   public static ResourcePackLoader.c a(Path var0, boolean var1) {
      BasicFileAttributes var2;
      try {
         var2 = Files.readAttributes(var0, BasicFileAttributes.class);
      } catch (NoSuchFileException var5) {
         return null;
      } catch (IOException var6) {
         a.warn("Failed to read properties of '{}', ignoring", var0, var6);
         return null;
      }

      if (var2.isDirectory() && Files.isRegularFile(var0.resolve("pack.mcmeta"))) {
         return var2x -> new PathPackResources(var2x, var0, var1);
      } else {
         if (var2.isRegularFile() && var0.getFileName().toString().endsWith(".zip")) {
            FileSystem var3 = var0.getFileSystem();
            if (var3 == FileSystems.getDefault() || var3 instanceof LinkFileSystem) {
               File var4 = var0.toFile();
               return var2x -> new ResourcePackFile(var2x, var4, var1);
            }
         }

         a.info("Found non-pack entry '{}', ignoring", var0);
         return null;
      }
   }
}
