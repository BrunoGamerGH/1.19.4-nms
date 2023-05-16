package net.minecraft.util;

import com.google.common.base.Charsets;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.AccessDeniedException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import net.minecraft.FileUtils;

public class SessionLock implements AutoCloseable {
   public static final String a = "session.lock";
   private final FileChannel b;
   private final FileLock c;
   private static final ByteBuffer d;

   public static SessionLock a(Path var0) throws IOException {
      Path var1 = var0.resolve("session.lock");
      FileUtils.c(var0);

      try (FileChannel var2 = FileChannel.open(var1, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
         var2.write(d.duplicate());
         var2.force(true);
         FileLock var3 = var2.tryLock();
         if (var3 == null) {
            throw SessionLock.ExceptionWorldConflict.a(var1);
         } else {
            return new SessionLock(var2, var3);
         }
      }
   }

   private SessionLock(FileChannel var0, FileLock var1) {
      this.b = var0;
      this.c = var1;
   }

   @Override
   public void close() throws IOException {
      try {
         if (this.c.isValid()) {
            this.c.release();
         }
      } finally {
         if (this.b.isOpen()) {
            this.b.close();
         }
      }
   }

   public boolean a() {
      return this.c.isValid();
   }

   public static boolean b(Path var0) throws IOException {
      Path var1 = var0.resolve("session.lock");

      try {
         boolean var4;
         try (
            FileChannel var2 = FileChannel.open(var1, StandardOpenOption.WRITE);
            FileLock var3 = var2.tryLock();
         ) {
            var4 = var3 == null;
         }

         return var4;
      } catch (AccessDeniedException var10) {
         return true;
      } catch (NoSuchFileException var11) {
         return false;
      }
   }

   static {
      byte[] var0 = "☃".getBytes(Charsets.UTF_8);
      d = ByteBuffer.allocateDirect(var0.length);
      d.put(var0);
      d.flip();
   }

   public static class ExceptionWorldConflict extends IOException {
      private ExceptionWorldConflict(Path var0, String var1) {
         super(var0.toAbsolutePath() + ": " + var1);
      }

      public static SessionLock.ExceptionWorldConflict a(Path var0) {
         return new SessionLock.ExceptionWorldConflict(var0, "already locked (possibly by other Minecraft instance?)");
      }
   }
}
