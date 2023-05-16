package net.minecraft.server.packs;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.annotation.Nullable;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.packs.resources.IoSupplier;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;

public class ResourcePackFile extends ResourcePackAbstract {
   private static final Logger d = LogUtils.getLogger();
   public static final Splitter a = Splitter.on('/').omitEmptyStrings().limit(3);
   private final File e;
   @Nullable
   private ZipFile f;
   private boolean g;

   public ResourcePackFile(String var0, File var1, boolean var2) {
      super(var0, var2);
      this.e = var1;
   }

   @Nullable
   private ZipFile c() {
      if (this.g) {
         return null;
      } else {
         if (this.f == null) {
            try {
               this.f = new ZipFile(this.e);
            } catch (IOException var2) {
               d.error("Failed to open pack {}", this.e, var2);
               this.g = true;
               return null;
            }
         }

         return this.f;
      }
   }

   private static String b(EnumResourcePackType var0, MinecraftKey var1) {
      return String.format(Locale.ROOT, "%s/%s/%s", var0.a(), var1.b(), var1.a());
   }

   @Nullable
   @Override
   public IoSupplier<InputStream> a(String... var0) {
      return this.a(String.join("/", var0));
   }

   @Override
   public IoSupplier<InputStream> a(EnumResourcePackType var0, MinecraftKey var1) {
      return this.a(b(var0, var1));
   }

   @Nullable
   private IoSupplier<InputStream> a(String var0) {
      ZipFile var1 = this.c();
      if (var1 == null) {
         return null;
      } else {
         ZipEntry var2 = var1.getEntry(var0);
         return var2 == null ? null : IoSupplier.create(var1, var2);
      }
   }

   @Override
   public Set<String> a(EnumResourcePackType var0) {
      ZipFile var1 = this.c();
      if (var1 == null) {
         return Set.of();
      } else {
         Enumeration<? extends ZipEntry> var2 = var1.entries();
         Set<String> var3 = Sets.newHashSet();

         while(var2.hasMoreElements()) {
            ZipEntry var4 = var2.nextElement();
            String var5 = var4.getName();
            if (var5.startsWith(var0.a() + "/")) {
               List<String> var6 = Lists.newArrayList(a.split(var5));
               if (var6.size() > 1) {
                  String var7 = var6.get(1);
                  if (var7.equals(var7.toLowerCase(Locale.ROOT))) {
                     var3.add(var7);
                  } else {
                     d.warn("Ignored non-lowercase namespace: {} in {}", var7, this.e);
                  }
               }
            }
         }

         return var3;
      }
   }

   @Override
   protected void finalize() throws Throwable {
      this.close();
      super.finalize();
   }

   @Override
   public void close() {
      if (this.f != null) {
         IOUtils.closeQuietly(this.f);
         this.f = null;
      }
   }

   @Override
   public void a(EnumResourcePackType var0, String var1, String var2, IResourcePack.a var3) {
      ZipFile var4 = this.c();
      if (var4 != null) {
         Enumeration<? extends ZipEntry> var5 = var4.entries();
         String var6 = var0.a() + "/" + var1 + "/";
         String var7 = var6 + var2 + "/";

         while(var5.hasMoreElements()) {
            ZipEntry var8 = var5.nextElement();
            if (!var8.isDirectory()) {
               String var9 = var8.getName();
               if (var9.startsWith(var7)) {
                  String var10 = var9.substring(var6.length());
                  MinecraftKey var11 = MinecraftKey.a(var1, var10);
                  if (var11 != null) {
                     var3.accept(var11, IoSupplier.create(var4, var8));
                  } else {
                     d.warn("Invalid path in datapack: {}:{}, ignoring", var1, var10);
                  }
               }
            }
         }
      }
   }
}
