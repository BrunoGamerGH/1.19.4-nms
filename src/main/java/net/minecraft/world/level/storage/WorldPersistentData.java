package net.minecraft.world.level.storage;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixer;
import com.mojang.logging.LogUtils;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTCompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.PersistentBase;
import org.slf4j.Logger;

public class WorldPersistentData {
   private static final Logger a = LogUtils.getLogger();
   public final Map<String, PersistentBase> b = Maps.newHashMap();
   private final DataFixer c;
   private final File d;

   public WorldPersistentData(File var0, DataFixer var1) {
      this.c = var1;
      this.d = var0;
   }

   private File a(String var0) {
      return new File(this.d, var0 + ".dat");
   }

   public <T extends PersistentBase> T a(Function<NBTTagCompound, T> var0, Supplier<T> var1, String var2) {
      T var3 = this.a(var0, var2);
      if (var3 != null) {
         return var3;
      } else {
         T var4 = var1.get();
         this.a(var2, var4);
         return var4;
      }
   }

   @Nullable
   public <T extends PersistentBase> T a(Function<NBTTagCompound, T> var0, String var1) {
      PersistentBase var2 = this.b.get(var1);
      if (var2 == null && !this.b.containsKey(var1)) {
         var2 = this.b(var0, var1);
         this.b.put(var1, var2);
      }

      return (T)var2;
   }

   @Nullable
   private <T extends PersistentBase> T b(Function<NBTTagCompound, T> var0, String var1) {
      try {
         File var2 = this.a(var1);
         if (var2.exists()) {
            NBTTagCompound var3 = this.a(var1, SharedConstants.b().d().c());
            return var0.apply(var3.p("data"));
         }
      } catch (Exception var5) {
         a.error("Error loading saved data: {}", var1, var5);
      }

      return null;
   }

   public void a(String var0, PersistentBase var1) {
      this.b.put(var0, var1);
   }

   public NBTTagCompound a(String var0, int var1) throws IOException {
      File var2 = this.a(var0);

      NBTTagCompound var8;
      try (
         FileInputStream var3 = new FileInputStream(var2);
         PushbackInputStream var4 = new PushbackInputStream(var3, 2);
      ) {
         NBTTagCompound var5;
         if (this.a(var4)) {
            var5 = NBTCompressedStreamTools.a(var4);
         } else {
            try (DataInputStream var6 = new DataInputStream(var4)) {
               var5 = NBTCompressedStreamTools.a((DataInput)var6);
            }
         }

         int var6 = GameProfileSerializer.b(var5, 1343);
         var8 = DataFixTypes.h.a(this.c, var5, var6, var1);
      }

      return var8;
   }

   private boolean a(PushbackInputStream var0) throws IOException {
      byte[] var1 = new byte[2];
      boolean var2 = false;
      int var3 = var0.read(var1, 0, 2);
      if (var3 == 2) {
         int var4 = (var1[1] & 255) << 8 | var1[0] & 255;
         if (var4 == 35615) {
            var2 = true;
         }
      }

      if (var3 != 0) {
         var0.unread(var1, 0, var3);
      }

      return var2;
   }

   public void a() {
      this.b.forEach((var0, var1) -> {
         if (var1 != null) {
            var1.a(this.a(var0));
         }
      });
   }
}
