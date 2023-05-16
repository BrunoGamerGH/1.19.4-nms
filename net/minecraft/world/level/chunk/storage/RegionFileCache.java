package net.minecraft.world.level.chunk.storage;

import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.annotation.Nullable;
import net.minecraft.FileUtils;
import net.minecraft.nbt.NBTCompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.StreamTagVisitor;
import net.minecraft.util.ExceptionSuppressor;
import net.minecraft.world.level.ChunkCoordIntPair;

public final class RegionFileCache implements AutoCloseable {
   public static final String a = ".mca";
   private static final int b = 256;
   public final Long2ObjectLinkedOpenHashMap<RegionFile> c = new Long2ObjectLinkedOpenHashMap();
   private final Path d;
   private final boolean e;

   RegionFileCache(Path path, boolean flag) {
      this.d = path;
      this.e = flag;
   }

   private RegionFile getRegionFile(ChunkCoordIntPair chunkcoordintpair, boolean existingOnly) throws IOException {
      long i = ChunkCoordIntPair.c(chunkcoordintpair.h(), chunkcoordintpair.i());
      RegionFile regionfile = (RegionFile)this.c.getAndMoveToFirst(i);
      if (regionfile != null) {
         return regionfile;
      } else {
         if (this.c.size() >= 256) {
            ((RegionFile)this.c.removeLast()).close();
         }

         FileUtils.c(this.d);
         Path path = this.d;
         int j = chunkcoordintpair.h();
         Path path1 = path.resolve("r." + j + "." + chunkcoordintpair.i() + ".mca");
         if (existingOnly && !Files.exists(path1)) {
            return null;
         } else {
            RegionFile regionfile1 = new RegionFile(path1, this.d, this.e);
            this.c.putAndMoveToFirst(i, regionfile1);
            return regionfile1;
         }
      }
   }

   @Nullable
   public NBTTagCompound a(ChunkCoordIntPair chunkcoordintpair) throws IOException {
      RegionFile regionfile = this.getRegionFile(chunkcoordintpair, true);
      if (regionfile == null) {
         return null;
      } else {
         NBTTagCompound nbttagcompound;
         try (DataInputStream datainputstream = regionfile.a(chunkcoordintpair)) {
            if (datainputstream != null) {
               return NBTCompressedStreamTools.a((DataInput)datainputstream);
            }

            nbttagcompound = null;
         }

         return nbttagcompound;
      }
   }

   public void a(ChunkCoordIntPair chunkcoordintpair, StreamTagVisitor streamtagvisitor) throws IOException {
      RegionFile regionfile = this.getRegionFile(chunkcoordintpair, true);
      if (regionfile != null) {
         try (DataInputStream datainputstream = regionfile.a(chunkcoordintpair)) {
            if (datainputstream != null) {
               NBTCompressedStreamTools.a((DataInput)datainputstream, streamtagvisitor);
            }
         }
      }
   }

   protected void a(ChunkCoordIntPair chunkcoordintpair, @Nullable NBTTagCompound nbttagcompound) throws IOException {
      RegionFile regionfile = this.getRegionFile(chunkcoordintpair, false);
      if (nbttagcompound == null) {
         regionfile.d(chunkcoordintpair);
      } else {
         try (DataOutputStream dataoutputstream = regionfile.c(chunkcoordintpair)) {
            NBTCompressedStreamTools.a(nbttagcompound, (DataOutput)dataoutputstream);
         }
      }
   }

   @Override
   public void close() throws IOException {
      ExceptionSuppressor<IOException> exceptionsuppressor = new ExceptionSuppressor<>();
      ObjectIterator objectiterator = this.c.values().iterator();

      while(objectiterator.hasNext()) {
         RegionFile regionfile = (RegionFile)objectiterator.next();

         try {
            regionfile.close();
         } catch (IOException var5) {
            exceptionsuppressor.a(var5);
         }
      }

      exceptionsuppressor.a();
   }

   public void a() throws IOException {
      ObjectIterator objectiterator = this.c.values().iterator();

      while(objectiterator.hasNext()) {
         RegionFile regionfile = (RegionFile)objectiterator.next();
         regionfile.a();
      }
   }
}
