package org.bukkit.craftbukkit.v1_19_R3.entity.memory;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.entity.memory.MemoryKey;

public final class CraftMemoryKey {
   private CraftMemoryKey() {
   }

   public static <T, U> MemoryModuleType<U> fromMemoryKey(MemoryKey<T> memoryKey) {
      return (MemoryModuleType<U>)BuiltInRegistries.B.a(CraftNamespacedKey.toMinecraft(memoryKey.getKey()));
   }

   public static <T, U> MemoryKey<U> toMemoryKey(MemoryModuleType<T> memoryModuleType) {
      return MemoryKey.getByKey(CraftNamespacedKey.fromMinecraft(BuiltInRegistries.B.b(memoryModuleType)));
   }
}
