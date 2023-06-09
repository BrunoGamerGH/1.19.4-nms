package org.bukkit.craftbukkit.v1_19_R3.entity.memory;

import java.util.UUID;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.GlobalPos;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;

public final class CraftMemoryMapper {
   private CraftMemoryMapper() {
   }

   public static Object fromNms(Object object) {
      if (object instanceof GlobalPos) {
         return fromNms((GlobalPos)object);
      } else if (object instanceof Long) {
         return (Long)object;
      } else if (object instanceof UUID) {
         return (UUID)object;
      } else if (object instanceof Boolean) {
         return (Boolean)object;
      } else if (object instanceof Integer) {
         return (Integer)object;
      } else {
         throw new UnsupportedOperationException("Do not know how to map " + object);
      }
   }

   public static Object toNms(Object object) {
      if (object == null) {
         return null;
      } else if (object instanceof Location) {
         return toNms((Location)object);
      } else if (object instanceof Long) {
         return (Long)object;
      } else if (object instanceof UUID) {
         return (UUID)object;
      } else if (object instanceof Boolean) {
         return (Boolean)object;
      } else if (object instanceof Integer) {
         return (Integer)object;
      } else {
         throw new UnsupportedOperationException("Do not know how to map " + object);
      }
   }

   public static Location fromNms(GlobalPos globalPos) {
      return new Location(
         ((CraftServer)Bukkit.getServer()).getServer().a(globalPos.a()).getWorld(),
         (double)globalPos.b().u(),
         (double)globalPos.b().v(),
         (double)globalPos.b().w()
      );
   }

   public static GlobalPos toNms(Location location) {
      return GlobalPos.a(((CraftWorld)location.getWorld()).getHandle().ab(), BlockPosition.a(location.getX(), location.getY(), location.getZ()));
   }
}
