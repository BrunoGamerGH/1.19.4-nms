package net.minecraft.world.level.chunk.storage;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.logging.LogUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.world.level.ChunkCoordIntPair;
import org.slf4j.Logger;

public class RegionFile implements AutoCloseable {
   private static final Logger c = LogUtils.getLogger();
   private static final int d = 4096;
   @VisibleForTesting
   protected static final int a = 1024;
   private static final int e = 5;
   private static final int f = 0;
   private static final ByteBuffer g = ByteBuffer.allocateDirect(1);
   private static final String h = ".mcc";
   private static final int i = 128;
   private static final int j = 256;
   private static final int k = 0;
   private final FileChannel l;
   private final Path m;
   final RegionFileCompression n;
   private final ByteBuffer o = ByteBuffer.allocateDirect(8192);
   private final IntBuffer p;
   private final IntBuffer q;
   @VisibleForTesting
   protected final RegionFileBitSet b = new RegionFileBitSet();

   public RegionFile(Path path, Path path1, boolean flag) throws IOException {
      this(path, path1, RegionFileCompression.b, flag);
   }

   public RegionFile(Path path, Path path1, RegionFileCompression regionfilecompression, boolean flag) throws IOException {
      this.n = regionfilecompression;
      if (!Files.isDirectory(path1)) {
         throw new IllegalArgumentException("Expected directory, got " + path1.toAbsolutePath());
      } else {
         this.m = path1;
         this.p = this.o.asIntBuffer();
         ((Buffer)this.p).limit(1024);
         ((Buffer)this.o).position(4096);
         this.q = this.o.asIntBuffer();
         if (flag) {
            this.l = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.DSYNC);
         } else {
            this.l = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE);
         }

         this.b.a(0, 2);
         ((Buffer)this.o).position(0);
         int i = this.l.read(this.o, 0L);
         if (i != -1) {
            if (i != 8192) {
               c.warn("Region file {} has truncated header: {}", path, i);
            }

            long j = Files.size(path);

            for(int k = 0; k < 1024; ++k) {
               int l = this.p.get(k);
               if (l != 0) {
                  int i1 = b(l);
                  int j1 = a(l);
                  if (j1 == 255) {
                     ByteBuffer realLen = ByteBuffer.allocate(4);
                     this.l.read(realLen, (long)(i1 * 4096));
                     j1 = (realLen.getInt(0) + 4) / 4096 + 1;
                  }

                  if (i1 < 2) {
                     c.warn("Region file {} has invalid sector at index: {}; sector {} overlaps with header", new Object[]{path, k, i1});
                     this.p.put(k, 0);
                  } else if (j1 == 0) {
                     c.warn("Region file {} has an invalid sector at index: {}; size has to be > 0", path, k);
                     this.p.put(k, 0);
                  } else if ((long)i1 * 4096L > j) {
                     c.warn("Region file {} has an invalid sector at index: {}; sector {} is out of bounds", new Object[]{path, k, i1});
                     this.p.put(k, 0);
                  } else {
                     this.b.a(i1, j1);
                  }
               }
            }
         }
      }
   }

   private Path f(ChunkCoordIntPair chunkcoordintpair) {
      String s = "c." + chunkcoordintpair.e + "." + chunkcoordintpair.f + ".mcc";
      return this.m.resolve(s);
   }

   @Nullable
   public synchronized DataInputStream a(ChunkCoordIntPair chunkcoordintpair) throws IOException {
      int i = this.g(chunkcoordintpair);
      if (i == 0) {
         return null;
      } else {
         int j = b(i);
         int k = a(i);
         if (k == 255) {
            ByteBuffer realLen = ByteBuffer.allocate(4);
            this.l.read(realLen, (long)(j * 4096));
            k = (realLen.getInt(0) + 4) / 4096 + 1;
         }

         int l = k * 4096;
         ByteBuffer bytebuffer = ByteBuffer.allocate(l);
         this.l.read(bytebuffer, (long)(j * 4096));
         ((Buffer)bytebuffer).flip();
         if (bytebuffer.remaining() < 5) {
            c.error("Chunk {} header is truncated: expected {} but read {}", new Object[]{chunkcoordintpair, l, bytebuffer.remaining()});
            return null;
         } else {
            int i1 = bytebuffer.getInt();
            byte b0 = bytebuffer.get();
            if (i1 == 0) {
               c.warn("Chunk {} is allocated, but stream is missing", chunkcoordintpair);
               return null;
            } else {
               int j1 = i1 - 1;
               if (a(b0)) {
                  if (j1 != 0) {
                     c.warn("Chunk has both internal and external streams");
                  }

                  return this.a(chunkcoordintpair, b(b0));
               } else if (j1 > bytebuffer.remaining()) {
                  c.error("Chunk {} stream is truncated: expected {} but read {}", new Object[]{chunkcoordintpair, j1, bytebuffer.remaining()});
                  return null;
               } else if (j1 < 0) {
                  c.error("Declared size {} of chunk {} is negative", i1, chunkcoordintpair);
                  return null;
               } else {
                  return this.a(chunkcoordintpair, b0, a(bytebuffer, j1));
               }
            }
         }
      }
   }

   private static int b() {
      return (int)(SystemUtils.d() / 1000L);
   }

   private static boolean a(byte b0) {
      return (b0 & 128) != 0;
   }

   private static byte b(byte b0) {
      return (byte)(b0 & -129);
   }

   @Nullable
   private DataInputStream a(ChunkCoordIntPair chunkcoordintpair, byte b0, InputStream inputstream) throws IOException {
      RegionFileCompression regionfilecompression = RegionFileCompression.a(b0);
      if (regionfilecompression == null) {
         c.error("Chunk {} has invalid chunk stream version {}", chunkcoordintpair, b0);
         return null;
      } else {
         return new DataInputStream(regionfilecompression.a(inputstream));
      }
   }

   @Nullable
   private DataInputStream a(ChunkCoordIntPair chunkcoordintpair, byte b0) throws IOException {
      Path path = this.f(chunkcoordintpair);
      if (!Files.isRegularFile(path)) {
         c.error("External chunk path {} is not file", path);
         return null;
      } else {
         return this.a(chunkcoordintpair, b0, Files.newInputStream(path));
      }
   }

   private static ByteArrayInputStream a(ByteBuffer bytebuffer, int i) {
      return new ByteArrayInputStream(bytebuffer.array(), bytebuffer.position(), i);
   }

   private int a(int i, int j) {
      return i << 8 | j;
   }

   private static int a(int i) {
      return i & 0xFF;
   }

   private static int b(int i) {
      return i >> 8 & 16777215;
   }

   private static int c(int i) {
      return (i + 4096 - 1) / 4096;
   }

   public boolean b(ChunkCoordIntPair chunkcoordintpair) {
      int i = this.g(chunkcoordintpair);
      if (i == 0) {
         return false;
      } else {
         int j = b(i);
         int k = a(i);
         ByteBuffer bytebuffer = ByteBuffer.allocate(5);

         try {
            this.l.read(bytebuffer, (long)(j * 4096));
            ((Buffer)bytebuffer).flip();
            if (bytebuffer.remaining() != 5) {
               return false;
            } else {
               int l = bytebuffer.getInt();
               byte b0 = bytebuffer.get();
               if (a(b0)) {
                  if (!RegionFileCompression.b(b(b0))) {
                     return false;
                  }

                  if (!Files.isRegularFile(this.f(chunkcoordintpair))) {
                     return false;
                  }
               } else {
                  if (!RegionFileCompression.b(b0)) {
                     return false;
                  }

                  if (l == 0) {
                     return false;
                  }

                  int i1 = l - 1;
                  if (i1 < 0 || i1 > 4096 * k) {
                     return false;
                  }
               }

               return true;
            }
         } catch (IOException var9) {
            return false;
         }
      }
   }

   public DataOutputStream c(ChunkCoordIntPair chunkcoordintpair) throws IOException {
      return new DataOutputStream(this.n.a(new RegionFile.ChunkBuffer(chunkcoordintpair)));
   }

   public void a() throws IOException {
      this.l.force(true);
   }

   public void d(ChunkCoordIntPair chunkcoordintpair) throws IOException {
      int i = h(chunkcoordintpair);
      int j = this.p.get(i);
      if (j != 0) {
         this.p.put(i, 0);
         this.q.put(i, b());
         this.d();
         Files.deleteIfExists(this.f(chunkcoordintpair));
         this.b.b(b(j), a(j));
      }
   }

   protected synchronized void a(ChunkCoordIntPair chunkcoordintpair, ByteBuffer bytebuffer) throws IOException {
      int i = h(chunkcoordintpair);
      int j = this.p.get(i);
      int k = b(j);
      int l = a(j);
      int i1 = bytebuffer.remaining();
      int j1 = c(i1);
      int k1;
      RegionFile.b regionfile_b;
      if (j1 >= 256) {
         Path path = this.f(chunkcoordintpair);
         c.warn("Saving oversized chunk {} ({} bytes} to external file {}", new Object[]{chunkcoordintpair, i1, path});
         j1 = 1;
         k1 = this.b.a(j1);
         regionfile_b = this.a(path, bytebuffer);
         ByteBuffer bytebuffer1 = this.c();
         this.l.write(bytebuffer1, (long)(k1 * 4096));
      } else {
         k1 = this.b.a(j1);
         regionfile_b = () -> Files.deleteIfExists(this.f(chunkcoordintpair));
         this.l.write(bytebuffer, (long)(k1 * 4096));
      }

      this.p.put(i, this.a(k1, j1));
      this.q.put(i, b());
      this.d();
      regionfile_b.run();
      if (k != 0) {
         this.b.b(k, l);
      }
   }

   private ByteBuffer c() {
      ByteBuffer bytebuffer = ByteBuffer.allocate(5);
      bytebuffer.putInt(1);
      bytebuffer.put((byte)(this.n.a() | 128));
      ((Buffer)bytebuffer).flip();
      return bytebuffer;
   }

   private RegionFile.b a(Path path, ByteBuffer bytebuffer) throws IOException {
      Path path1 = Files.createTempFile(this.m, "tmp", null);

      try (FileChannel filechannel = FileChannel.open(path1, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
         ((Buffer)bytebuffer).position(5);
         filechannel.write(bytebuffer);
      }

      return () -> Files.move(path1, path, StandardCopyOption.REPLACE_EXISTING);
   }

   private void d() throws IOException {
      ((Buffer)this.o).position(0);
      this.l.write(this.o, 0L);
   }

   private int g(ChunkCoordIntPair chunkcoordintpair) {
      return this.p.get(h(chunkcoordintpair));
   }

   public boolean e(ChunkCoordIntPair chunkcoordintpair) {
      return this.g(chunkcoordintpair) != 0;
   }

   private static int h(ChunkCoordIntPair chunkcoordintpair) {
      return chunkcoordintpair.j() + chunkcoordintpair.k() * 32;
   }

   @Override
   public void close() throws IOException {
      try {
         this.e();
      } finally {
         try {
            this.l.force(true);
         } finally {
            this.l.close();
         }
      }
   }

   private void e() throws IOException {
      int i = (int)this.l.size();
      int j = c(i) * 4096;
      if (i != j) {
         ByteBuffer bytebuffer = g.duplicate();
         ((Buffer)bytebuffer).position(0);
         this.l.write(bytebuffer, (long)(j - 1));
      }
   }

   private class ChunkBuffer extends ByteArrayOutputStream {
      private final ChunkCoordIntPair b;

      public ChunkBuffer(ChunkCoordIntPair chunkcoordintpair) {
         super(8096);
         super.write(0);
         super.write(0);
         super.write(0);
         super.write(0);
         super.write(RegionFile.this.n.a());
         this.b = chunkcoordintpair;
      }

      @Override
      public void close() throws IOException {
         ByteBuffer bytebuffer = ByteBuffer.wrap(this.buf, 0, this.count);
         bytebuffer.putInt(0, this.count - 5 + 1);
         RegionFile.this.a(this.b, bytebuffer);
      }
   }

   private interface b {
      void run() throws IOException;
   }
}
