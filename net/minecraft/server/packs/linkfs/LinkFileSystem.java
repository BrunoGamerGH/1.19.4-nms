package net.minecraft.server.packs.linkfs;

import com.google.common.base.Splitter;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.WatchService;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.nio.file.spi.FileSystemProvider;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public class LinkFileSystem extends FileSystem {
   private static final Set<String> b = Set.of("basic");
   public static final String a = "/";
   private static final Splitter c = Splitter.on('/');
   private final FileStore d;
   private final FileSystemProvider e = new LinkFSProvider();
   private final LinkFSPath f;

   LinkFileSystem(String var0, LinkFileSystem.b var1) {
      this.d = new LinkFSFileStore(var0);
      this.f = a(var1, this, "", null);
   }

   private static LinkFSPath a(LinkFileSystem.b var0, LinkFileSystem var1, String var2, @Nullable LinkFSPath var3) {
      Object2ObjectOpenHashMap<String, LinkFSPath> var4 = new Object2ObjectOpenHashMap();
      LinkFSPath var5 = new LinkFSPath(var1, var2, var3, new PathContents.a(var4));
      var0.b.forEach((var3x, var4x) -> var4.put(var3x, new LinkFSPath(var1, var3x, var5, new PathContents.b(var4x))));
      var0.a.forEach((var3x, var4x) -> var4.put(var3x, a(var4x, var1, var3x, var5)));
      var4.trim();
      return var5;
   }

   @Override
   public FileSystemProvider provider() {
      return this.e;
   }

   @Override
   public void close() {
   }

   @Override
   public boolean isOpen() {
      return true;
   }

   @Override
   public boolean isReadOnly() {
      return true;
   }

   @Override
   public String getSeparator() {
      return "/";
   }

   @Override
   public Iterable<Path> getRootDirectories() {
      return List.of(this.f);
   }

   @Override
   public Iterable<FileStore> getFileStores() {
      return List.of(this.d);
   }

   @Override
   public Set<String> supportedFileAttributeViews() {
      return b;
   }

   @Override
   public Path getPath(String var0, String... var1) {
      Stream<String> var2 = Stream.of(var0);
      if (var1.length > 0) {
         var2 = Stream.concat(var2, Stream.of(var1));
      }

      String var3 = var2.collect(Collectors.joining("/"));
      if (var3.equals("/")) {
         return this.f;
      } else if (var3.startsWith("/")) {
         LinkFSPath var4 = this.f;

         for(String var6 : c.split(var3.substring(1))) {
            if (var6.isEmpty()) {
               throw new IllegalArgumentException("Empty paths not allowed");
            }

            var4 = var4.a(var6);
         }

         return var4;
      } else {
         LinkFSPath var4 = null;

         for(String var6 : c.split(var3)) {
            if (var6.isEmpty()) {
               throw new IllegalArgumentException("Empty paths not allowed");
            }

            var4 = new LinkFSPath(this, var6, var4, PathContents.b);
         }

         if (var4 == null) {
            throw new IllegalArgumentException("Empty paths not allowed");
         } else {
            return var4;
         }
      }
   }

   @Override
   public PathMatcher getPathMatcher(String var0) {
      throw new UnsupportedOperationException();
   }

   @Override
   public UserPrincipalLookupService getUserPrincipalLookupService() {
      throw new UnsupportedOperationException();
   }

   @Override
   public WatchService newWatchService() {
      throw new UnsupportedOperationException();
   }

   public FileStore a() {
      return this.d;
   }

   public LinkFSPath b() {
      return this.f;
   }

   public static LinkFileSystem.a c() {
      return new LinkFileSystem.a();
   }

   public static class a {
      private final LinkFileSystem.b a = new LinkFileSystem.b();

      public LinkFileSystem.a a(List<String> var0, String var1, Path var2) {
         LinkFileSystem.b var3 = this.a;

         for(String var5 : var0) {
            var3 = var3.a.computeIfAbsent(var5, var0x -> new LinkFileSystem.b());
         }

         var3.b.put(var1, var2);
         return this;
      }

      public LinkFileSystem.a a(List<String> var0, Path var1) {
         if (var0.isEmpty()) {
            throw new IllegalArgumentException("Path can't be empty");
         } else {
            int var2 = var0.size() - 1;
            return this.a(var0.subList(0, var2), var0.get(var2), var1);
         }
      }

      public FileSystem a(String var0) {
         return new LinkFileSystem(var0, this.a);
      }
   }

   static record b(Map<String, LinkFileSystem.b> children, Map<String, Path> files) {
      final Map<String, LinkFileSystem.b> a;
      final Map<String, Path> b;

      public b() {
         this(new HashMap<>(), new HashMap<>());
      }

      private b(Map<String, LinkFileSystem.b> var0, Map<String, Path> var1) {
         this.a = var0;
         this.b = var1;
      }
   }
}
