package net.minecraft.server.packs;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.FileUtils;
import net.minecraft.SystemUtils;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.packs.resources.IoSupplier;
import org.slf4j.Logger;

public class PathPackResources extends ResourcePackAbstract {
   private static final Logger a = LogUtils.getLogger();
   private static final Joiner d = Joiner.on("/");
   private final Path e;

   public PathPackResources(String var0, Path var1, boolean var2) {
      super(var0, var2);
      this.e = var1;
   }

   @Nullable
   @Override
   public IoSupplier<InputStream> a(String... var0) {
      FileUtils.a(var0);
      Path var1 = FileUtils.a(this.e, List.of(var0));
      return Files.exists(var1) ? IoSupplier.create(var1) : null;
   }

   public static boolean a(Path var0) {
      return true;
   }

   @Nullable
   @Override
   public IoSupplier<InputStream> a(EnumResourcePackType var0, MinecraftKey var1) {
      Path var2 = this.e.resolve(var0.a()).resolve(var1.b());
      return a(var1, var2);
   }

   public static IoSupplier<InputStream> a(MinecraftKey var0, Path var1) {
      return (IoSupplier<InputStream>)FileUtils.c(var0.a()).get().map(var1x -> {
         Path var2 = FileUtils.a(var1, var1x);
         return b(var2);
      }, var1x -> {
         a.error("Invalid path {}: {}", var0, var1x.message());
         return null;
      });
   }

   @Nullable
   private static IoSupplier<InputStream> b(Path var0) {
      return Files.exists(var0) && a(var0) ? IoSupplier.create(var0) : null;
   }

   @Override
   public void a(EnumResourcePackType var0, String var1, String var2, IResourcePack.a var3) {
      FileUtils.c(var2).get().ifLeft(var3x -> {
         Path var4x = this.e.resolve(var0.a()).resolve(var1);
         a(var1, var4x, var3x, var3);
      }).ifRight(var1x -> a.error("Invalid path {}: {}", var2, var1x.message()));
   }

   public static void a(String var0, Path var1, List<String> var2, IResourcePack.a var3) {
      Path var4 = FileUtils.a(var1, var2);

      try (Stream<Path> var5 = Files.find(var4, Integer.MAX_VALUE, (var0x, var1x) -> var1x.isRegularFile())) {
         var5.forEach(var3x -> {
            String var4x = d.join(var1.relativize(var3x));
            MinecraftKey var5x = MinecraftKey.a(var0, var4x);
            if (var5x == null) {
               SystemUtils.a(String.format(Locale.ROOT, "Invalid path in pack: %s:%s, ignoring", var0, var4x));
            } else {
               var3.accept(var5x, IoSupplier.create(var3x));
            }
         });
      } catch (NoSuchFileException var10) {
      } catch (IOException var11) {
         a.error("Failed to list path {}", var4, var11);
      }
   }

   @Override
   public Set<String> a(EnumResourcePackType var0) {
      Set<String> var1 = Sets.newHashSet();
      Path var2 = this.e.resolve(var0.a());

      try (DirectoryStream<Path> var3 = Files.newDirectoryStream(var2)) {
         for(Path var5 : var3) {
            String var6 = var5.getFileName().toString();
            if (var6.equals(var6.toLowerCase(Locale.ROOT))) {
               var1.add(var6);
            } else {
               a.warn("Ignored non-lowercase namespace: {} in {}", var6, this.e);
            }
         }
      } catch (NoSuchFileException var10) {
      } catch (IOException var11) {
         a.error("Failed to list path {}", var2, var11);
      }

      return var1;
   }

   @Override
   public void close() {
   }
}
