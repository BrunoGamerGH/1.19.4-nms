package net.minecraft.server.packs.resources;

import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.packs.EnumResourcePackType;
import net.minecraft.server.packs.IResourcePack;
import org.slf4j.Logger;

public class ResourceManager implements IReloadableResourceManager {
   private static final Logger a = LogUtils.getLogger();
   private final Map<String, ResourceManagerFallback> b;
   private final List<IResourcePack> c;

   public ResourceManager(EnumResourcePackType var0, List<IResourcePack> var1) {
      this.c = List.copyOf(var1);
      Map<String, ResourceManagerFallback> var2 = new HashMap<>();
      List<String> var3 = var1.stream().flatMap(var1x -> var1x.a(var0).stream()).distinct().toList();

      for(IResourcePack var5 : var1) {
         ResourceFilterSection var6 = this.a(var5);
         Set<String> var7 = var5.a(var0);
         Predicate<MinecraftKey> var8 = var6 != null ? var1x -> var6.b(var1x.a()) : null;

         for(String var10 : var3) {
            boolean var11 = var7.contains(var10);
            boolean var12 = var6 != null && var6.a(var10);
            if (var11 || var12) {
               ResourceManagerFallback var13 = var2.get(var10);
               if (var13 == null) {
                  var13 = new ResourceManagerFallback(var0, var10);
                  var2.put(var10, var13);
               }

               if (var11 && var12) {
                  var13.a(var5, var8);
               } else if (var11) {
                  var13.a(var5);
               } else {
                  var13.a(var5.a(), var8);
               }
            }
         }
      }

      this.b = var2;
   }

   @Nullable
   private ResourceFilterSection a(IResourcePack var0) {
      try {
         return var0.a(ResourceFilterSection.a);
      } catch (IOException var3) {
         a.error("Failed to get filter section from pack {}", var0.a());
         return null;
      }
   }

   @Override
   public Set<String> a() {
      return this.b.keySet();
   }

   @Override
   public Optional<IResource> getResource(MinecraftKey var0) {
      IResourceManager var1 = this.b.get(var0.b());
      return var1 != null ? var1.getResource(var0) : Optional.empty();
   }

   @Override
   public List<IResource> a(MinecraftKey var0) {
      IResourceManager var1 = this.b.get(var0.b());
      return var1 != null ? var1.a(var0) : List.of();
   }

   @Override
   public Map<MinecraftKey, IResource> b(String var0, Predicate<MinecraftKey> var1) {
      a(var0);
      Map<MinecraftKey, IResource> var2 = new TreeMap<>();

      for(ResourceManagerFallback var4 : this.b.values()) {
         var2.putAll(var4.b(var0, var1));
      }

      return var2;
   }

   @Override
   public Map<MinecraftKey, List<IResource>> c(String var0, Predicate<MinecraftKey> var1) {
      a(var0);
      Map<MinecraftKey, List<IResource>> var2 = new TreeMap<>();

      for(ResourceManagerFallback var4 : this.b.values()) {
         var2.putAll(var4.c(var0, var1));
      }

      return var2;
   }

   private static void a(String var0) {
      if (var0.endsWith("/")) {
         throw new IllegalArgumentException("Trailing slash in path " + var0);
      }
   }

   @Override
   public Stream<IResourcePack> b() {
      return this.c.stream();
   }

   @Override
   public void close() {
      this.c.forEach(IResourcePack::close);
   }
}
