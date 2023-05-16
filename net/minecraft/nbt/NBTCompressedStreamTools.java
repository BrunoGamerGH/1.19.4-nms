package net.minecraft.nbt;

import io.netty.buffer.ByteBufInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportSystemDetails;
import net.minecraft.ReportedException;
import net.minecraft.util.FastBufferedInputStream;
import org.spigotmc.LimitStream;

public class NBTCompressedStreamTools {
   public static NBTTagCompound a(File file) throws IOException {
      NBTTagCompound nbttagcompound;
      try (FileInputStream fileinputstream = new FileInputStream(file)) {
         nbttagcompound = a(fileinputstream);
      }

      return nbttagcompound;
   }

   private static DataInputStream b(InputStream inputstream) throws IOException {
      return new DataInputStream(new FastBufferedInputStream(new GZIPInputStream(inputstream)));
   }

   public static NBTTagCompound a(InputStream inputstream) throws IOException {
      NBTTagCompound nbttagcompound;
      try (DataInputStream datainputstream = b(inputstream)) {
         nbttagcompound = a(datainputstream, NBTReadLimiter.a);
      }

      return nbttagcompound;
   }

   public static void a(File file, StreamTagVisitor streamtagvisitor) throws IOException {
      try (FileInputStream fileinputstream = new FileInputStream(file)) {
         a(fileinputstream, streamtagvisitor);
      }
   }

   public static void a(InputStream inputstream, StreamTagVisitor streamtagvisitor) throws IOException {
      try (DataInputStream datainputstream = b(inputstream)) {
         a((DataInput)datainputstream, streamtagvisitor);
      }
   }

   public static void a(NBTTagCompound nbttagcompound, File file) throws IOException {
      try (FileOutputStream fileoutputstream = new FileOutputStream(file)) {
         a(nbttagcompound, fileoutputstream);
      }
   }

   public static void a(NBTTagCompound nbttagcompound, OutputStream outputstream) throws IOException {
      try (DataOutputStream dataoutputstream = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(outputstream)))) {
         a(nbttagcompound, (DataOutput)dataoutputstream);
      }
   }

   public static void b(NBTTagCompound nbttagcompound, File file) throws IOException {
      try (
         FileOutputStream fileoutputstream = new FileOutputStream(file);
         DataOutputStream dataoutputstream = new DataOutputStream(fileoutputstream);
      ) {
         a(nbttagcompound, (DataOutput)dataoutputstream);
      }
   }

   @Nullable
   public static NBTTagCompound b(File file) throws IOException {
      if (!file.exists()) {
         return null;
      } else {
         NBTTagCompound nbttagcompound;
         try (
            FileInputStream fileinputstream = new FileInputStream(file);
            DataInputStream datainputstream = new DataInputStream(fileinputstream);
         ) {
            nbttagcompound = a(datainputstream, NBTReadLimiter.a);
         }

         return nbttagcompound;
      }
   }

   public static NBTTagCompound a(DataInput datainput) throws IOException {
      return a(datainput, NBTReadLimiter.a);
   }

   public static NBTTagCompound a(DataInput datainput, NBTReadLimiter nbtreadlimiter) throws IOException {
      if (datainput instanceof ByteBufInputStream) {
         datainput = new DataInputStream(new LimitStream((InputStream)datainput, nbtreadlimiter));
      }

      NBTBase nbtbase = a(datainput, 0, nbtreadlimiter);
      if (nbtbase instanceof NBTTagCompound) {
         return (NBTTagCompound)nbtbase;
      } else {
         throw new IOException("Root tag must be a named compound tag");
      }
   }

   public static void a(NBTTagCompound nbttagcompound, DataOutput dataoutput) throws IOException {
      a((NBTBase)nbttagcompound, dataoutput);
   }

   public static void a(DataInput datainput, StreamTagVisitor streamtagvisitor) throws IOException {
      NBTTagType<?> nbttagtype = NBTTagTypes.a(datainput.readByte());
      if (nbttagtype == NBTTagEnd.a) {
         if (streamtagvisitor.b(NBTTagEnd.a) == StreamTagVisitor.b.a) {
            streamtagvisitor.a();
         }
      } else {
         switch(streamtagvisitor.b(nbttagtype)) {
            case a:
               NBTTagString.a(datainput);
               nbttagtype.a(datainput, streamtagvisitor);
               break;
            case b:
               NBTTagString.a(datainput);
               nbttagtype.a(datainput);
            case c:
         }
      }
   }

   public static void a(NBTBase nbtbase, DataOutput dataoutput) throws IOException {
      dataoutput.writeByte(nbtbase.b());
      if (nbtbase.b() != 0) {
         dataoutput.writeUTF("");
         nbtbase.a(dataoutput);
      }
   }

   private static NBTBase a(DataInput datainput, int i, NBTReadLimiter nbtreadlimiter) throws IOException {
      byte b0 = datainput.readByte();
      if (b0 == 0) {
         return NBTTagEnd.b;
      } else {
         NBTTagString.a(datainput);

         try {
            return NBTTagTypes.a(b0).b(datainput, i, nbtreadlimiter);
         } catch (IOException var7) {
            CrashReport crashreport = CrashReport.a(var7, "Loading NBT data");
            CrashReportSystemDetails crashreportsystemdetails = crashreport.a("NBT Tag");
            crashreportsystemdetails.a("Tag type", b0);
            throw new ReportedException(crashreport);
         }
      }
   }
}
