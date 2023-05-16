package org.bukkit.craftbukkit.v1_19_R3.configuration;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

public final class ConfigSerializationUtil {
   public static String getString(Map<?, ?> map, String key, boolean nullable) {
      return getObject(String.class, map, key, nullable);
   }

   public static UUID getUuid(Map<?, ?> map, String key, boolean nullable) {
      String uuidString = getString(map, key, nullable);
      return uuidString == null ? null : UUID.fromString(uuidString);
   }

   public static <T> T getObject(Class<T> clazz, Map<?, ?> map, String key, boolean nullable) {
      Object object = map.get(key);
      if (clazz.isInstance(object)) {
         return clazz.cast(object);
      } else if (object == null) {
         if (!nullable) {
            throw new NoSuchElementException(map + " does not contain " + key);
         } else {
            return null;
         }
      } else {
         throw new IllegalArgumentException(key + "(" + object + ") is not a valid " + clazz);
      }
   }

   private ConfigSerializationUtil() {
   }
}
