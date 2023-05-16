package net.minecraft.server.packs.linkfs;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.ProviderMismatchException;
import java.nio.file.ReadOnlyFileSystemException;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchEvent.Modifier;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;

class LinkFSPath implements Path {
   private static final BasicFileAttributes a = new DummyFileAttributes() {
      @Override
      public boolean isRegularFile() {
         return false;
      }

      @Override
      public boolean isDirectory() {
         return true;
      }
   };
   private static final BasicFileAttributes b = new DummyFileAttributes() {
      @Override
      public boolean isRegularFile() {
         return true;
      }

      @Override
      public boolean isDirectory() {
         return false;
      }
   };
   private static final Comparator<LinkFSPath> c = Comparator.comparing(LinkFSPath::n);
   private final String d;
   private final LinkFileSystem e;
   @Nullable
   private final LinkFSPath f;
   @Nullable
   private List<String> g;
   @Nullable
   private String h;
   private final PathContents i;

   public LinkFSPath(LinkFileSystem var0, String var1, @Nullable LinkFSPath var2, PathContents var3) {
      this.e = var0;
      this.d = var1;
      this.f = var2;
      this.i = var3;
   }

   private LinkFSPath a(@Nullable LinkFSPath var0, String var1) {
      return new LinkFSPath(this.e, var1, var0, PathContents.b);
   }

   public LinkFileSystem a() {
      return this.e;
   }

   @Override
   public boolean isAbsolute() {
      return this.i != PathContents.b;
   }

   @Override
   public File toFile() {
      PathContents var2 = this.i;
      if (var2 instanceof PathContents.b var0) {
         return var0.a().toFile();
      } else {
         throw new UnsupportedOperationException("Path " + this.n() + " does not represent file");
      }
   }

   @Nullable
   public LinkFSPath b() {
      return this.isAbsolute() ? this.e.b() : null;
   }

   public LinkFSPath c() {
      return this.a(null, this.d);
   }

   @Nullable
   public LinkFSPath d() {
      return this.f;
   }

   @Override
   public int getNameCount() {
      return this.l().size();
   }

   private List<String> l() {
      if (this.d.isEmpty()) {
         return List.of();
      } else {
         if (this.g == null) {
            Builder<String> var0 = ImmutableList.builder();
            if (this.f != null) {
               var0.addAll(this.f.l());
            }

            var0.add(this.d);
            this.g = var0.build();
         }

         return this.g;
      }
   }

   public LinkFSPath a(int var0) {
      List<String> var1 = this.l();
      if (var0 >= 0 && var0 < var1.size()) {
         return this.a(null, var1.get(var0));
      } else {
         throw new IllegalArgumentException("Invalid index: " + var0);
      }
   }

   public LinkFSPath a(int var0, int var1) {
      List<String> var2 = this.l();
      if (var0 >= 0 && var1 <= var2.size() && var0 < var1) {
         LinkFSPath var3 = null;

         for(int var4 = var0; var4 < var1; ++var4) {
            var3 = this.a(var3, var2.get(var4));
         }

         return var3;
      } else {
         throw new IllegalArgumentException();
      }
   }

   @Override
   public boolean startsWith(Path var0) {
      if (var0.isAbsolute() != this.isAbsolute()) {
         return false;
      } else if (var0 instanceof LinkFSPath var1) {
         if (var1.e != this.e) {
            return false;
         } else {
            List<String> var2 = this.l();
            List<String> var3 = var1.l();
            int var4 = var3.size();
            if (var4 > var2.size()) {
               return false;
            } else {
               for(int var5 = 0; var5 < var4; ++var5) {
                  if (!var3.get(var5).equals(var2.get(var5))) {
                     return false;
                  }
               }

               return true;
            }
         }
      } else {
         return false;
      }
   }

   @Override
   public boolean endsWith(Path var0) {
      if (var0.isAbsolute() && !this.isAbsolute()) {
         return false;
      } else if (var0 instanceof LinkFSPath var1) {
         if (var1.e != this.e) {
            return false;
         } else {
            List<String> var2 = this.l();
            List<String> var3 = var1.l();
            int var4 = var3.size();
            int var5 = var2.size() - var4;
            if (var5 < 0) {
               return false;
            } else {
               for(int var6 = var4 - 1; var6 >= 0; --var6) {
                  if (!var3.get(var6).equals(var2.get(var5 + var6))) {
                     return false;
                  }
               }

               return true;
            }
         }
      } else {
         return false;
      }
   }

   public LinkFSPath e() {
      return this;
   }

   public LinkFSPath a(Path var0) {
      LinkFSPath var1 = this.c(var0);
      return var0.isAbsolute() ? var1 : this.a(var1.l());
   }

   private LinkFSPath a(List<String> var0) {
      LinkFSPath var1 = this;

      for(String var3 : var0) {
         var1 = var1.a(var3);
      }

      return var1;
   }

   LinkFSPath a(String var0) {
      if (a(this.i)) {
         return new LinkFSPath(this.e, var0, this, this.i);
      } else {
         PathContents var2 = this.i;
         if (var2 instanceof PathContents.a var1) {
            LinkFSPath var2x = var1.a().get(var0);
            return var2x != null ? var2x : new LinkFSPath(this.e, var0, this, PathContents.a);
         } else if (this.i instanceof PathContents.b) {
            return new LinkFSPath(this.e, var0, this, PathContents.a);
         } else {
            throw new AssertionError("All content types should be already handled");
         }
      }
   }

   private static boolean a(PathContents var0) {
      return var0 == PathContents.a || var0 == PathContents.b;
   }

   public LinkFSPath b(Path var0) {
      LinkFSPath var1 = this.c(var0);
      if (this.isAbsolute() != var1.isAbsolute()) {
         throw new IllegalArgumentException("absolute mismatch");
      } else {
         List<String> var2 = this.l();
         List<String> var3 = var1.l();
         if (var2.size() >= var3.size()) {
            throw new IllegalArgumentException();
         } else {
            for(int var4 = 0; var4 < var2.size(); ++var4) {
               if (!var2.get(var4).equals(var3.get(var4))) {
                  throw new IllegalArgumentException();
               }
            }

            return var1.a(var2.size(), var3.size());
         }
      }
   }

   @Override
   public URI toUri() {
      try {
         return new URI("x-mc-link", this.e.a().name(), this.n(), null);
      } catch (URISyntaxException var2) {
         throw new AssertionError("Failed to create URI", var2);
      }
   }

   public LinkFSPath f() {
      return this.isAbsolute() ? this : this.e.b().a(this);
   }

   public LinkFSPath a(LinkOption... var0) {
      return this.f();
   }

   @Override
   public WatchKey register(WatchService var0, Kind<?>[] var1, Modifier... var2) {
      throw new UnsupportedOperationException();
   }

   @Override
   public int compareTo(Path var0) {
      LinkFSPath var1 = this.c(var0);
      return c.compare(this, var1);
   }

   @Override
   public boolean equals(Object var0) {
      if (var0 == this) {
         return true;
      } else if (var0 instanceof LinkFSPath var1) {
         if (this.e != var1.e) {
            return false;
         } else {
            boolean var2 = this.m();
            if (var2 != var1.m()) {
               return false;
            } else if (var2) {
               return this.i == var1.i;
            } else {
               return Objects.equals(this.f, var1.f) && Objects.equals(this.d, var1.d);
            }
         }
      } else {
         return false;
      }
   }

   private boolean m() {
      return !a(this.i);
   }

   @Override
   public int hashCode() {
      return this.m() ? this.i.hashCode() : this.d.hashCode();
   }

   @Override
   public String toString() {
      return this.n();
   }

   private String n() {
      if (this.h == null) {
         StringBuilder var0 = new StringBuilder();
         if (this.isAbsolute()) {
            var0.append("/");
         }

         Joiner.on("/").appendTo(var0, this.l());
         this.h = var0.toString();
      }

      return this.h;
   }

   private LinkFSPath c(@Nullable Path var0) {
      if (var0 == null) {
         throw new NullPointerException();
      } else {
         if (var0 instanceof LinkFSPath var1 && var1.e == this.e) {
            return var1;
         }

         throw new ProviderMismatchException();
      }
   }

   public boolean g() {
      return this.m();
   }

   @Nullable
   public Path h() {
      PathContents var2 = this.i;
      return var2 instanceof PathContents.b var0 ? var0.a() : null;
   }

   @Nullable
   public PathContents.a i() {
      PathContents var2 = this.i;
      return var2 instanceof PathContents.a var0 ? var0 : null;
   }

   public BasicFileAttributeView j() {
      return new BasicFileAttributeView() {
         @Override
         public String name() {
            return "basic";
         }

         @Override
         public BasicFileAttributes readAttributes() throws IOException {
            return LinkFSPath.this.k();
         }

         @Override
         public void setTimes(FileTime var0, FileTime var1, FileTime var2) {
            throw new ReadOnlyFileSystemException();
         }
      };
   }

   public BasicFileAttributes k() throws IOException {
      if (this.i instanceof PathContents.a) {
         return a;
      } else if (this.i instanceof PathContents.b) {
         return b;
      } else {
         throw new NoSuchFileException(this.n());
      }
   }
}
