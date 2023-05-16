package org.spigotmc;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import net.minecraft.nbt.NBTReadLimiter;

public class LimitStream extends FilterInputStream {
   private final NBTReadLimiter limit;

   public LimitStream(InputStream is, NBTReadLimiter limit) {
      super(is);
      this.limit = limit;
   }

   @Override
   public int read() throws IOException {
      this.limit.a(1L);
      return super.read();
   }

   @Override
   public int read(byte[] b) throws IOException {
      this.limit.a((long)b.length);
      return super.read(b);
   }

   @Override
   public int read(byte[] b, int off, int len) throws IOException {
      this.limit.a((long)len);
      return super.read(b, off, len);
   }
}