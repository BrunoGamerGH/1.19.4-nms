package net.minecraft.server.packs;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.SystemUtils;
import org.slf4j.Logger;

public class VanillaPackResourcesBuilder {
   private static final Logger b = LogUtils.getLogger();
   public static Consumer<VanillaPackResourcesBuilder> a = var0 -> {
   };
   private static final Map<EnumResourcePackType, Path> c = SystemUtils.a(() -> {
      synchronized(ResourcePackVanilla.class) {
         Builder<EnumResourcePackType, Path> var1 = ImmutableMap.builder();

         for(EnumResourcePackType var5 : EnumResourcePackType.values()) {
            String var6 = "/" + var5.a() + "/.mcassetsroot";
            URL var7 = ResourcePackVanilla.class.getResource(var6);
            if (var7 == null) {
               b.error("File {} does not exist in classpath", var6);
            } else {
               try {
                  URI var8 = var7.toURI();
                  String var9 = var8.getScheme();
                  if (!"jar".equals(var9) && !"file".equals(var9)) {
                     b.warn("Assets URL '{}' uses unexpected schema", var8);
                  }

                  Path var10 = a(var8);
                  var1.put(var5, var10.getParent());
               } catch (Exception var12) {
                  b.error("Couldn't resolve path to vanilla assets", var12);
               }
            }
         }

         return var1.build();
      }
   });
   private final Set<Path> d = new LinkedHashSet<>();
   private final Map<EnumResourcePackType, Set<Path>> e = new EnumMap<>(EnumResourcePackType.class);
   private BuiltInMetadata f = BuiltInMetadata.a();
   private final Set<String> g = new HashSet<>();

   private static Path a(URI var0) throws IOException {
      try {
         return Paths.get(var0);
      } catch (FileSystemNotFoundException var3) {
      } catch (Throwable var4) {
         b.warn("Unable to get path for: {}", var0, var4);
      }

      try {
         FileSystems.newFileSystem(var0, Collections.emptyMap());
      } catch (FileSystemAlreadyExistsException var2) {
      }

      return Paths.get(var0);
   }

   private boolean b(Path var0) {
      if (!Files.exists(var0)) {
         return false;
      } else if (!Files.isDirectory(var0)) {
         throw new IllegalArgumentException("Path " + var0.toAbsolutePath() + " is not directory");
      } else {
         return true;
      }
   }

   private void c(Path var0) {
      if (this.b(var0)) {
         this.d.add(var0);
      }
   }

   private void b(EnumResourcePackType var0, Path var1) {
      if (this.b(var1)) {
         this.e.computeIfAbsent(var0, var0x -> new LinkedHashSet()).add(var1);
      }
   }

   public VanillaPackResourcesBuilder a() {
      c.forEach((var0, var1) -> {
         this.c(var1.getParent());
         this.b(var0, var1);
      });
      return this;
   }

   public VanillaPackResourcesBuilder a(EnumResourcePackType var0, Class<?> var1) {
      Enumeration<URL> var2 = null;

      try {
         var2 = var1.getClassLoader().getResources(var0.a() + "/");
      } catch (IOException var8) {
      }

      while(var2 != null && var2.hasMoreElements()) {
         URL var3 = var2.nextElement();

         try {
            URI var4 = var3.toURI();
            if ("file".equals(var4.getScheme())) {
               Path var5 = Paths.get(var4);
               this.c(var5.getParent());
               this.b(var0, var5);
            }
         } catch (Exception var7) {
            b.error("Failed to extract path from {}", var3, var7);
         }
      }

      return this;
   }

   public VanillaPackResourcesBuilder b() {
      a.accept(this);
      return this;
   }

   public VanillaPackResourcesBuilder a(Path var0) {
      this.c(var0);

      for(EnumResourcePackType var4 : EnumResourcePackType.values()) {
         this.b(var4, var0.resolve(var4.a()));
      }

      return this;
   }

   public VanillaPackResourcesBuilder a(EnumResourcePackType var0, Path var1) {
      this.c(var1);
      this.b(var0, var1);
      return this;
   }

   public VanillaPackResourcesBuilder a(BuiltInMetadata var0) {
      this.f = var0;
      return this;
   }

   public VanillaPackResourcesBuilder a(String... var0) {
      this.g.addAll(Arrays.asList(var0));
      return this;
   }

   public ResourcePackVanilla c() {
      Map<EnumResourcePackType, List<Path>> var0 = new EnumMap<>(EnumResourcePackType.class);

      for(EnumResourcePackType var4 : EnumResourcePackType.values()) {
         List<Path> var5 = a(this.e.getOrDefault(var4, Set.of()));
         var0.put(var4, var5);
      }

      return new ResourcePackVanilla(this.f, Set.copyOf(this.g), a(this.d), var0);
   }

   private static List<Path> a(Collection<Path> var0) {
      List<Path> var1 = new ArrayList<>(var0);
      Collections.reverse(var1);
      return List.copyOf(var1);
   }
}
