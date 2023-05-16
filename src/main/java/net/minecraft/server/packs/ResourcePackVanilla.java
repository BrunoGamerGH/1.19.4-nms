package net.minecraft.server.packs;

import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.FileUtils;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.packs.metadata.ResourcePackMetaParser;
import net.minecraft.server.packs.resources.IResource;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.server.packs.resources.ResourceProvider;
import org.slf4j.Logger;

public class ResourcePackVanilla implements IResourcePack {
   private static final Logger a = LogUtils.getLogger();
   private final BuiltInMetadata d;
   private final Set<String> e;
   private final List<Path> f;
   private final Map<EnumResourcePackType, List<Path>> g;

   ResourcePackVanilla(BuiltInMetadata var0, Set<String> var1, List<Path> var2, Map<EnumResourcePackType, List<Path>> var3) {
      this.d = var0;
      this.e = var1;
      this.f = var2;
      this.g = var3;
   }

   @Nullable
   @Override
   public IoSupplier<InputStream> a(String... var0) {
      FileUtils.a(var0);
      List<String> var1 = List.of(var0);

      for(Path var3 : this.f) {
         Path var4 = FileUtils.a(var3, var1);
         if (Files.exists(var4) && PathPackResources.a(var4)) {
            return IoSupplier.create(var4);
         }
      }

      return null;
   }

   public void a(EnumResourcePackType var0, MinecraftKey var1, Consumer<Path> var2) {
      FileUtils.c(var1.a()).get().ifLeft(var3x -> {
         String var4 = var1.b();

         for(Path var6 : this.g.get(var0)) {
            Path var7 = var6.resolve(var4);
            var2.accept(FileUtils.a(var7, var3x));
         }
      }).ifRight(var1x -> a.error("Invalid path {}: {}", var1, var1x.message()));
   }

   @Override
   public void a(EnumResourcePackType var0, String var1, String var2, IResourcePack.a var3) {
      FileUtils.c(var2).get().ifLeft(var3x -> {
         List<Path> var4x = this.g.get(var0);
         int var5 = var4x.size();
         if (var5 == 1) {
            a(var3, var1, (Path)var4x.get(0), var3x);
         } else if (var5 > 1) {
            Map<MinecraftKey, IoSupplier<InputStream>> var6 = new HashMap<>();

            for(int var7 = 0; var7 < var5 - 1; ++var7) {
               a(var6::putIfAbsent, var1, (Path)var4x.get(var7), var3x);
            }

            Path var7 = (Path)var4x.get(var5 - 1);
            if (var6.isEmpty()) {
               a(var3, var1, var7, var3x);
            } else {
               a(var6::putIfAbsent, var1, var7, var3x);
               var6.forEach(var3);
            }
         }
      }).ifRight(var1x -> a.error("Invalid path {}: {}", var2, var1x.message()));
   }

   private static void a(IResourcePack.a var0, String var1, Path var2, List<String> var3) {
      Path var4 = var2.resolve(var1);
      PathPackResources.a(var1, var4, var3, var0);
   }

   @Nullable
   @Override
   public IoSupplier<InputStream> a(EnumResourcePackType var0, MinecraftKey var1) {
      return (IoSupplier<InputStream>)FileUtils.c(var1.a()).get().map(var2x -> {
         String var3 = var1.b();

         for(Path var5 : this.g.get(var0)) {
            Path var6 = FileUtils.a(var5.resolve(var3), var2x);
            if (Files.exists(var6) && PathPackResources.a(var6)) {
               return IoSupplier.create(var6);
            }
         }

         return null;
      }, var1x -> {
         a.error("Invalid path {}: {}", var1, var1x.message());
         return null;
      });
   }

   @Override
   public Set<String> a(EnumResourcePackType var0) {
      return this.e;
   }

   @Nullable
   @Override
   public <T> T a(ResourcePackMetaParser<T> var0) {
      IoSupplier<InputStream> var1 = this.a("pack.mcmeta");
      if (var1 != null) {
         try (InputStream var2 = var1.get()) {
            T var3 = ResourcePackAbstract.a(var0, var2);
            if (var3 != null) {
               return var3;
            }

            return this.d.a(var0);
         } catch (IOException var8) {
         }
      }

      return this.d.a(var0);
   }

   @Override
   public String a() {
      return "vanilla";
   }

   @Override
   public boolean b() {
      return true;
   }

   @Override
   public void close() {
   }

   public ResourceProvider c() {
      return var0 -> Optional.ofNullable(this.a(EnumResourcePackType.a, var0)).map(var0x -> new IResource(this, var0x));
   }
}
