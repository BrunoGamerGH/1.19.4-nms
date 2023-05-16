package org.bukkit.craftbukkit.v1_19_R3;

import net.minecraft.world.level.levelgen.HeightMap;

public final class CraftHeightMap {
   private CraftHeightMap() {
   }

   public static HeightMap.Type toNMS(org.bukkit.HeightMap bukkitHeightMap) {
      switch(bukkitHeightMap) {
         case MOTION_BLOCKING:
            return HeightMap.Type.e;
         case MOTION_BLOCKING_NO_LEAVES:
            return HeightMap.Type.f;
         case OCEAN_FLOOR:
            return HeightMap.Type.d;
         case OCEAN_FLOOR_WG:
            return HeightMap.Type.c;
         case WORLD_SURFACE:
            return HeightMap.Type.b;
         case WORLD_SURFACE_WG:
            return HeightMap.Type.a;
         default:
            throw new EnumConstantNotPresentException(HeightMap.Type.class, bukkitHeightMap.name());
      }
   }

   public static org.bukkit.HeightMap fromNMS(HeightMap.Type nmsHeightMapType) {
      switch(nmsHeightMapType) {
         case a:
            return org.bukkit.HeightMap.WORLD_SURFACE_WG;
         case b:
            return org.bukkit.HeightMap.WORLD_SURFACE;
         case c:
            return org.bukkit.HeightMap.OCEAN_FLOOR_WG;
         case d:
            return org.bukkit.HeightMap.OCEAN_FLOOR;
         case e:
            return org.bukkit.HeightMap.MOTION_BLOCKING;
         case f:
            return org.bukkit.HeightMap.MOTION_BLOCKING_NO_LEAVES;
         default:
            throw new EnumConstantNotPresentException(org.bukkit.HeightMap.class, nmsHeightMapType.name());
      }
   }
}
