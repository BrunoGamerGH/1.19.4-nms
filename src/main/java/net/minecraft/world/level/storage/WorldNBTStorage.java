package net.minecraft.world.level.storage;

import com.mojang.datafixers.DataFixer;
import com.mojang.logging.LogUtils;
import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTCompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.entity.player.EntityHuman;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.slf4j.Logger;
import org.spigotmc.SpigotConfig;

public class WorldNBTStorage {
   private static final Logger b = LogUtils.getLogger();
   private final File c;
   protected final DataFixer a;

   public WorldNBTStorage(Convertable.ConversionSession convertable_conversionsession, DataFixer datafixer) {
      this.a = datafixer;
      this.c = convertable_conversionsession.a(SavedFile.c).toFile();
      this.c.mkdirs();
   }

   public void a(EntityHuman entityhuman) {
      if (!SpigotConfig.disablePlayerDataSaving) {
         try {
            NBTTagCompound nbttagcompound = entityhuman.f(new NBTTagCompound());
            File file = File.createTempFile(entityhuman.ct() + "-", ".dat", this.c);
            NBTCompressedStreamTools.a(nbttagcompound, file);
            File file1 = new File(this.c, entityhuman.ct() + ".dat");
            File file2 = new File(this.c, entityhuman.ct() + ".dat_old");
            SystemUtils.a(file1, file, file2);
         } catch (Exception var6) {
            b.warn("Failed to save player data for {}", entityhuman.Z().getString());
         }
      }
   }

   @Nullable
   public NBTTagCompound b(EntityHuman entityhuman) {
      NBTTagCompound nbttagcompound = null;

      try {
         File file = new File(this.c, entityhuman.ct() + ".dat");
         boolean usingWrongFile = false;
         if (!file.exists()) {
            file = new File(this.c, UUID.nameUUIDFromBytes(("OfflinePlayer:" + entityhuman.cu()).getBytes("UTF-8")).toString() + ".dat");
            if (file.exists()) {
               usingWrongFile = true;
               Bukkit.getServer().getLogger().warning("Using offline mode UUID file for player " + entityhuman.cu() + " as it is the only copy we can find.");
            }
         }

         if (file.exists() && file.isFile()) {
            nbttagcompound = NBTCompressedStreamTools.a(file);
         }

         if (usingWrongFile) {
            file.renameTo(new File(file.getPath() + ".offline-read"));
         }
      } catch (Exception var6) {
         b.warn("Failed to load player data for {}", entityhuman.Z().getString());
      }

      if (nbttagcompound != null) {
         if (entityhuman instanceof EntityPlayer) {
            CraftPlayer player = (CraftPlayer)entityhuman.getBukkitEntity();
            long modified = new File(this.c, entityhuman.cs().toString() + ".dat").lastModified();
            if (modified < player.getFirstPlayed()) {
               player.setFirstPlayed(modified);
            }
         }

         int i = GameProfileSerializer.b(nbttagcompound, -1);
         entityhuman.g(DataFixTypes.b.a(this.a, nbttagcompound, i));
      }

      return nbttagcompound;
   }

   public NBTTagCompound getPlayerData(String s) {
      try {
         File file1 = new File(this.c, s + ".dat");
         if (file1.exists()) {
            return NBTCompressedStreamTools.a(new FileInputStream(file1));
         }
      } catch (Exception var3) {
         b.warn("Failed to load player data for " + s);
      }

      return null;
   }

   public String[] a() {
      String[] astring = this.c.list();
      if (astring == null) {
         astring = new String[0];
      }

      for(int i = 0; i < astring.length; ++i) {
         if (astring[i].endsWith(".dat")) {
            astring[i] = astring[i].substring(0, astring[i].length() - 4);
         }
      }

      return astring;
   }

   public File getPlayerDir() {
      return this.c;
   }
}
