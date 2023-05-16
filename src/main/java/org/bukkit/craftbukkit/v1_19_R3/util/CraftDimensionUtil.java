package org.bukkit.craftbukkit.v1_19_R3.util;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.World;
import net.minecraft.world.level.dimension.WorldDimension;

public class CraftDimensionUtil {
   private CraftDimensionUtil() {
   }

   public static ResourceKey<World> getMainDimensionKey(World world) {
      ResourceKey<WorldDimension> typeKey = world.getTypeKey();
      if (typeKey == WorldDimension.b) {
         return World.h;
      } else if (typeKey == WorldDimension.c) {
         return World.i;
      } else {
         return typeKey == WorldDimension.d ? World.j : world.ab();
      }
   }
}
