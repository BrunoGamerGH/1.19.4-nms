package net.minecraft.world.level.chunk.storage;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;
import javax.annotation.Nullable;
import net.minecraft.util.FastBufferedInputStream;

public class RegionFileCompression {
   private static final Int2ObjectMap<RegionFileCompression> d = new Int2ObjectOpenHashMap();
   public static final RegionFileCompression a = a(
      new RegionFileCompression(
         1, var0 -> new FastBufferedInputStream(new GZIPInputStream(var0)), var0 -> new BufferedOutputStream(new GZIPOutputStream(var0))
      )
   );
   public static final RegionFileCompression b = a(
      new RegionFileCompression(
         2, var0 -> new FastBufferedInputStream(new InflaterInputStream(var0)), var0 -> new BufferedOutputStream(new DeflaterOutputStream(var0))
      )
   );
   public static final RegionFileCompression c = a(new RegionFileCompression(3, var0 -> var0, var0 -> var0));
   private final int e;
   private final RegionFileCompression.a<InputStream> f;
   private final RegionFileCompression.a<OutputStream> g;

   private RegionFileCompression(int var0, RegionFileCompression.a<InputStream> var1, RegionFileCompression.a<OutputStream> var2) {
      this.e = var0;
      this.f = var1;
      this.g = var2;
   }

   private static RegionFileCompression a(RegionFileCompression var0) {
      d.put(var0.e, var0);
      return var0;
   }

   @Nullable
   public static RegionFileCompression a(int var0) {
      return (RegionFileCompression)d.get(var0);
   }

   public static boolean b(int var0) {
      return d.containsKey(var0);
   }

   public int a() {
      return this.e;
   }

   public OutputStream a(OutputStream var0) throws IOException {
      return this.g.wrap(var0);
   }

   public InputStream a(InputStream var0) throws IOException {
      return this.f.wrap(var0);
   }

   @FunctionalInterface
   interface a<O> {
      O wrap(O var1) throws IOException;
   }
}
