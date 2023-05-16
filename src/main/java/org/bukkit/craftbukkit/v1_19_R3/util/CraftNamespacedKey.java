package org.bukkit.craftbukkit.v1_19_R3.util;

import net.minecraft.resources.MinecraftKey;
import org.bukkit.NamespacedKey;

public final class CraftNamespacedKey {
   public static NamespacedKey fromStringOrNull(String string) {
      if (string != null && !string.isEmpty()) {
         MinecraftKey minecraft = MinecraftKey.a(string);
         return minecraft == null ? null : fromMinecraft(minecraft);
      } else {
         return null;
      }
   }

   public static NamespacedKey fromString(String string) {
      return fromMinecraft(new MinecraftKey(string));
   }

   public static NamespacedKey fromMinecraft(MinecraftKey minecraft) {
      return new NamespacedKey(minecraft.b(), minecraft.a());
   }

   public static MinecraftKey toMinecraft(NamespacedKey key) {
      return new MinecraftKey(key.getNamespace(), key.getKey());
   }
}
