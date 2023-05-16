package net.minecraft.world.level.saveddata;

import com.mojang.logging.LogUtils;
import java.io.File;
import java.io.IOException;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTCompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import org.slf4j.Logger;

public abstract class PersistentBase {
   private static final Logger a = LogUtils.getLogger();
   private boolean b;

   public abstract NBTTagCompound a(NBTTagCompound var1);

   public void b() {
      this.a(true);
   }

   public void a(boolean var0) {
      this.b = var0;
   }

   public boolean c() {
      return this.b;
   }

   public void a(File var0) {
      if (this.c()) {
         NBTTagCompound var1 = new NBTTagCompound();
         var1.a("data", this.a(new NBTTagCompound()));
         GameProfileSerializer.g(var1);

         try {
            NBTCompressedStreamTools.a(var1, var0);
         } catch (IOException var4) {
            a.error("Could not save data {}", this, var4);
         }

         this.a(false);
      }
   }
}
