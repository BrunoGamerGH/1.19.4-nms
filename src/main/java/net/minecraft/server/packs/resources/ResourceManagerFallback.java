package net.minecraft.server.packs.resources;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.packs.EnumResourcePackType;
import net.minecraft.server.packs.IResourcePack;
import org.slf4j.Logger;

public class ResourceManagerFallback implements IResourceManager {
   static final Logger b = LogUtils.getLogger();
   protected final List<ResourceManagerFallback.d> a = Lists.newArrayList();
   private final EnumResourcePackType c;
   private final String d;

   public ResourceManagerFallback(EnumResourcePackType var0, String var1) {
      this.c = var0;
      this.d = var1;
   }

   public void a(IResourcePack var0) {
      this.a(var0.a(), var0, null);
   }

   public void a(IResourcePack var0, Predicate<MinecraftKey> var1) {
      this.a(var0.a(), var0, var1);
   }

   public void a(String var0, Predicate<MinecraftKey> var1) {
      this.a(var0, null, var1);
   }

   private void a(String var0, @Nullable IResourcePack var1, @Nullable Predicate<MinecraftKey> var2) {
      this.a.add(new ResourceManagerFallback.d(var0, var1, var2));
   }

   @Override
   public Set<String> a() {
      return ImmutableSet.of(this.d);
   }

   @Override
   public Optional<IResource> getResource(MinecraftKey var0) {
      for(int var1 = this.a.size() - 1; var1 >= 0; --var1) {
         ResourceManagerFallback.d var2 = this.a.get(var1);
         IResourcePack var3 = var2.b;
         if (var3 != null) {
            IoSupplier<InputStream> var4 = var3.a(this.c, var0);
            if (var4 != null) {
               IoSupplier<ResourceMetadata> var5 = this.a(var0, var1);
               return Optional.of(a(var3, var0, var4, var5));
            }
         }

         if (var2.a(var0)) {
            b.warn("Resource {} not found, but was filtered by pack {}", var0, var2.a);
            return Optional.empty();
         }
      }

      return Optional.empty();
   }

   private static IResource a(IResourcePack var0, MinecraftKey var1, IoSupplier<InputStream> var2, IoSupplier<ResourceMetadata> var3) {
      return new IResource(var0, a(var1, var0, var2), var3);
   }

   private static IoSupplier<InputStream> a(MinecraftKey var0, IResourcePack var1, IoSupplier<InputStream> var2) {
      return b.isDebugEnabled() ? () -> new ResourceManagerFallback.c(var2.get(), var0, var1.a()) : var2;
   }

   @Override
   public List<IResource> a(MinecraftKey var0) {
      MinecraftKey var1 = d(var0);
      List<IResource> var2 = new ArrayList<>();
      boolean var3 = false;
      String var4 = null;

      for(int var5 = this.a.size() - 1; var5 >= 0; --var5) {
         ResourceManagerFallback.d var6 = this.a.get(var5);
         IResourcePack var7 = var6.b;
         if (var7 != null) {
            IoSupplier<InputStream> var8 = var7.a(this.c, var0);
            if (var8 != null) {
               IoSupplier<ResourceMetadata> var9;
               if (var3) {
                  var9 = ResourceMetadata.b;
               } else {
                  var9 = () -> {
                     IoSupplier<InputStream> var2x = var7.a(this.c, var1);
                     return var2x != null ? b(var2x) : ResourceMetadata.a;
                  };
               }

               var2.add(new IResource(var7, var8, var9));
            }
         }

         if (var6.a(var0)) {
            var4 = var6.a;
            break;
         }

         if (var6.a(var1)) {
            var3 = true;
         }
      }

      if (var2.isEmpty() && var4 != null) {
         b.warn("Resource {} not found, but was filtered by pack {}", var0, var4);
      }

      return Lists.reverse(var2);
   }

   private static boolean b(MinecraftKey var0) {
      return var0.a().endsWith(".mcmeta");
   }

   private static MinecraftKey c(MinecraftKey var0) {
      String var1 = var0.a().substring(0, var0.a().length() - ".mcmeta".length());
      return var0.c(var1);
   }

   static MinecraftKey d(MinecraftKey var0) {
      return var0.c(var0.a() + ".mcmeta");
   }

   @Override
   public Map<MinecraftKey, IResource> b(String var0, Predicate<MinecraftKey> var1) {
      record a(IResourcePack packResources, IoSupplier<InputStream> resource, int packIndex) {
         final IResourcePack a;
         final IoSupplier<InputStream> b;
         final int c;

         a(IResourcePack var0, IoSupplier<InputStream> var1, int var2) {
            this.a = var0;
            this.b = var1;
            this.c = var2;
         }
      }

      Map<MinecraftKey, a> var2 = new HashMap<>();
      Map<MinecraftKey, a> var3 = new HashMap<>();
      int var4 = this.a.size();

      for(int var5 = 0; var5 < var4; ++var5) {
         ResourceManagerFallback.d var6 = this.a.get(var5);
         var6.a(var2.keySet());
         var6.a(var3.keySet());
         IResourcePack var7 = var6.b;
         if (var7 != null) {
            int var8 = var5;
            var7.a(this.c, this.d, var0, (var5x, var6x) -> {
               if (b(var5x)) {
                  if (var1.test(c(var5x))) {
                     var3.put(var5x, new a(var7, var6x, var8));
                  }
               } else if (var1.test(var5x)) {
                  var2.put(var5x, new a(var7, var6x, var8));
               }
            });
         }
      }

      Map<MinecraftKey, IResource> var5 = Maps.newTreeMap();
      var2.forEach((var2x, var3x) -> {
         MinecraftKey var5x = d(var2x);
         a var6x = var3.get(var5x);
         IoSupplier var4x;
         if (var6x != null && var6x.c >= var3x.c) {
            var4x = a(var6x.b);
         } else {
            var4x = ResourceMetadata.b;
         }

         var5.put(var2x, a(var3x.a, var2x, var3x.b, var4x));
      });
      return var5;
   }

   private IoSupplier<ResourceMetadata> a(MinecraftKey var0, int var1) {
      return () -> {
         MinecraftKey var2x = d(var0);

         for(int var3 = this.a.size() - 1; var3 >= var1; --var3) {
            ResourceManagerFallback.d var4 = this.a.get(var3);
            IResourcePack var5 = var4.b;
            if (var5 != null) {
               IoSupplier<InputStream> var6 = var5.a(this.c, var2x);
               if (var6 != null) {
                  return b(var6);
               }
            }

            if (var4.a(var2x)) {
               break;
            }
         }

         return ResourceMetadata.a;
      };
   }

   private static IoSupplier<ResourceMetadata> a(IoSupplier<InputStream> var0) {
      return () -> b(var0);
   }

   private static ResourceMetadata b(IoSupplier<InputStream> var0) throws IOException {
      ResourceMetadata var2;
      try (InputStream var1 = var0.get()) {
         var2 = ResourceMetadata.a(var1);
      }

      return var2;
   }

   private static void a(ResourceManagerFallback.d var0, Map<MinecraftKey, ResourceManagerFallback.b> var1) {
      for(ResourceManagerFallback.b var3 : var1.values()) {
         if (var0.a(var3.a)) {
            var3.c.clear();
         } else if (var0.a(var3.b())) {
            var3.d.clear();
         }
      }
   }

   private void a(ResourceManagerFallback.d var0, String var1, Predicate<MinecraftKey> var2, Map<MinecraftKey, ResourceManagerFallback.b> var3) {
      IResourcePack var4 = var0.b;
      if (var4 != null) {
         var4.a(this.c, this.d, var1, (var3x, var4x) -> {
            if (b(var3x)) {
               MinecraftKey var5x = c(var3x);
               if (!var2.test(var5x)) {
                  return;
               }

               var3.computeIfAbsent(var5x, ResourceManagerFallback.b::new).d.put(var4, var4x);
            } else {
               if (!var2.test(var3x)) {
                  return;
               }

               var3.computeIfAbsent(var3x, ResourceManagerFallback.b::new).c.add(new ResourceManagerFallback.e(var4, var4x));
            }
         });
      }
   }

   @Override
   public Map<MinecraftKey, List<IResource>> c(String var0, Predicate<MinecraftKey> var1) {
      Map<MinecraftKey, ResourceManagerFallback.b> var2 = Maps.newHashMap();

      for(ResourceManagerFallback.d var4 : this.a) {
         a(var4, var2);
         this.a(var4, var0, var1, var2);
      }

      TreeMap<MinecraftKey, List<IResource>> var3 = Maps.newTreeMap();

      for(ResourceManagerFallback.b var5 : var2.values()) {
         if (!var5.c.isEmpty()) {
            List<IResource> var6 = new ArrayList<>();

            for(ResourceManagerFallback.e var8 : var5.c) {
               IResourcePack var9 = var8.a;
               IoSupplier<InputStream> var10 = var5.d.get(var9);
               IoSupplier<ResourceMetadata> var11 = var10 != null ? a(var10) : ResourceMetadata.b;
               var6.add(a(var9, var5.a, var8.b, var11));
            }

            var3.put(var5.a, var6);
         }
      }

      return var3;
   }

   @Override
   public Stream<IResourcePack> b() {
      return this.a.stream().map(var0 -> var0.b).filter(Objects::nonNull);
   }

   static record b(
      MinecraftKey fileLocation,
      MinecraftKey metadataLocation,
      List<ResourceManagerFallback.e> fileSources,
      Map<IResourcePack, IoSupplier<InputStream>> metaSources
   ) {
      final MinecraftKey a;
      private final MinecraftKey b;
      final List<ResourceManagerFallback.e> c;
      final Map<IResourcePack, IoSupplier<InputStream>> d;

      b(MinecraftKey var0) {
         this(var0, ResourceManagerFallback.d(var0), new ArrayList<>(), new Object2ObjectArrayMap());
      }

      private b(MinecraftKey var0, MinecraftKey var1, List<ResourceManagerFallback.e> var2, Map<IResourcePack, IoSupplier<InputStream>> var3) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
         this.d = var3;
      }
   }

   static class c extends FilterInputStream {
      private final Supplier<String> a;
      private boolean b;

      public c(InputStream var0, MinecraftKey var1, String var2) {
         super(var0);
         Exception var3 = new Exception("Stacktrace");
         this.a = () -> {
            StringWriter var3x = new StringWriter();
            var3.printStackTrace(new PrintWriter(var3x));
            return "Leaked resource: '" + var1 + "' loaded from pack: '" + var2 + "'\n" + var3x;
         };
      }

      @Override
      public void close() throws IOException {
         super.close();
         this.b = true;
      }

      @Override
      protected void finalize() throws Throwable {
         if (!this.b) {
            ResourceManagerFallback.b.warn("{}", this.a.get());
         }

         super.finalize();
      }
   }

   static record d(String name, @Nullable IResourcePack resources, @Nullable Predicate<MinecraftKey> filter) {
      final String a;
      @Nullable
      final IResourcePack b;
      @Nullable
      private final Predicate<MinecraftKey> c;

      d(String var0, @Nullable IResourcePack var1, @Nullable Predicate<MinecraftKey> var2) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
      }

      public void a(Collection<MinecraftKey> var0) {
         if (this.c != null) {
            var0.removeIf(this.c);
         }
      }

      public boolean a(MinecraftKey var0) {
         return this.c != null && this.c.test(var0);
      }
   }

   static record e(IResourcePack source, IoSupplier<InputStream> resource) {
      final IResourcePack a;
      final IoSupplier<InputStream> b;

      e(IResourcePack var0, IoSupplier<InputStream> var1) {
         this.a = var0;
         this.b = var1;
      }
   }
}
