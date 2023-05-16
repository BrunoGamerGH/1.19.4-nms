package net.minecraft.server.packs.linkfs;

import java.io.IOException;
import java.net.URI;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.AccessDeniedException;
import java.nio.file.AccessMode;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.ProviderMismatchException;
import java.nio.file.ReadOnlyFileSystemException;
import java.nio.file.StandardOpenOption;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.spi.FileSystemProvider;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

class LinkFSProvider extends FileSystemProvider {
   public static final String a = "x-mc-link";

   @Override
   public String getScheme() {
      return "x-mc-link";
   }

   @Override
   public FileSystem newFileSystem(URI var0, Map<String, ?> var1) {
      throw new UnsupportedOperationException();
   }

   @Override
   public FileSystem getFileSystem(URI var0) {
      throw new UnsupportedOperationException();
   }

   @Override
   public Path getPath(URI var0) {
      throw new UnsupportedOperationException();
   }

   @Override
   public SeekableByteChannel newByteChannel(Path var0, Set<? extends OpenOption> var1, FileAttribute<?>... var2) throws IOException {
      if (!var1.contains(StandardOpenOption.CREATE_NEW)
         && !var1.contains(StandardOpenOption.CREATE)
         && !var1.contains(StandardOpenOption.APPEND)
         && !var1.contains(StandardOpenOption.WRITE)) {
         Path var3 = a(var0).f().h();
         if (var3 == null) {
            throw new NoSuchFileException(var0.toString());
         } else {
            return Files.newByteChannel(var3, var1, var2);
         }
      } else {
         throw new UnsupportedOperationException();
      }
   }

   @Override
   public DirectoryStream<Path> newDirectoryStream(Path var0, final Filter<? super Path> var1) throws IOException {
      final PathContents.a var2 = a(var0).f().i();
      if (var2 == null) {
         throw new NotDirectoryException(var0.toString());
      } else {
         return new DirectoryStream<Path>() {
            @Override
            public Iterator<Path> iterator() {
               return var2.a().values().stream().filter(var1xx -> {
                  try {
                     return var1.accept(var1xx);
                  } catch (IOException var3) {
                     throw new DirectoryIteratorException(var3);
                  }
               }).map(var0 -> var0).iterator();
            }

            @Override
            public void close() {
            }
         };
      }
   }

   @Override
   public void createDirectory(Path var0, FileAttribute<?>... var1) {
      throw new ReadOnlyFileSystemException();
   }

   @Override
   public void delete(Path var0) {
      throw new ReadOnlyFileSystemException();
   }

   @Override
   public void copy(Path var0, Path var1, CopyOption... var2) {
      throw new ReadOnlyFileSystemException();
   }

   @Override
   public void move(Path var0, Path var1, CopyOption... var2) {
      throw new ReadOnlyFileSystemException();
   }

   @Override
   public boolean isSameFile(Path var0, Path var1) {
      return var0 instanceof LinkFSPath && var1 instanceof LinkFSPath && var0.equals(var1);
   }

   @Override
   public boolean isHidden(Path var0) {
      return false;
   }

   @Override
   public FileStore getFileStore(Path var0) {
      return a(var0).a().a();
   }

   @Override
   public void checkAccess(Path var0, AccessMode... var1) throws IOException {
      if (var1.length == 0 && !a(var0).g()) {
         throw new NoSuchFileException(var0.toString());
      } else {
         AccessMode[] var3 = var1;
         int var4 = var1.length;
         int var5 = 0;

         while(var5 < var4) {
            AccessMode var5x = var3[var5];
            switch(var5x) {
               case READ:
                  if (!a(var0).g()) {
                     throw new NoSuchFileException(var0.toString());
                  }
               default:
                  ++var5;
                  break;
               case EXECUTE:
               case WRITE:
                  throw new AccessDeniedException(var5x.toString());
            }
         }
      }
   }

   @Nullable
   @Override
   public <V extends FileAttributeView> V getFileAttributeView(Path var0, Class<V> var1, LinkOption... var2) {
      LinkFSPath var3 = a(var0);
      return (V)(var1 == BasicFileAttributeView.class ? var3.j() : null);
   }

   @Override
   public <A extends BasicFileAttributes> A readAttributes(Path var0, Class<A> var1, LinkOption... var2) throws IOException {
      LinkFSPath var3 = a(var0).f();
      if (var1 == BasicFileAttributes.class) {
         return (A)var3.k();
      } else {
         throw new UnsupportedOperationException("Attributes of type " + var1.getName() + " not supported");
      }
   }

   @Override
   public Map<String, Object> readAttributes(Path var0, String var1, LinkOption... var2) {
      throw new UnsupportedOperationException();
   }

   @Override
   public void setAttribute(Path var0, String var1, Object var2, LinkOption... var3) {
      throw new ReadOnlyFileSystemException();
   }

   private static LinkFSPath a(@Nullable Path var0) {
      if (var0 == null) {
         throw new NullPointerException();
      } else if (var0 instanceof LinkFSPath) {
         return (LinkFSPath)var0;
      } else {
         throw new ProviderMismatchException();
      }
   }
}
