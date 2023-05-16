package org.bukkit.craftbukkit.v1_19_R3.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class WorldUUID {
   private static final Logger LOGGER = LogManager.getLogger();

   private WorldUUID() {
   }

   public static UUID getUUID(File baseDir) {
      File file1;
      file1 = new File(baseDir, "uid.dat");
      label146:
      if (file1.exists()) {
         DataInputStream dis = null;

         UUID var5;
         try {
            dis = new DataInputStream(new FileInputStream(file1));
            var5 = new UUID(dis.readLong(), dis.readLong());
         } catch (IOException var29) {
            LOGGER.warn("Failed to read " + file1 + ", generating new random UUID", var29);
            break label146;
         } finally {
            if (dis != null) {
               try {
                  dis.close();
               } catch (IOException var26) {
               }
            }
         }

         return var5;
      }

      UUID uuid = UUID.randomUUID();
      DataOutputStream dos = null;

      try {
         dos = new DataOutputStream(new FileOutputStream(file1));
         dos.writeLong(uuid.getMostSignificantBits());
         dos.writeLong(uuid.getLeastSignificantBits());
      } catch (IOException var27) {
         LOGGER.warn("Failed to write " + file1, var27);
      } finally {
         if (dos != null) {
            try {
               dos.close();
            } catch (IOException var25) {
            }
         }
      }

      return uuid;
   }
}
