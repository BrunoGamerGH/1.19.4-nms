package org.bukkit.craftbukkit.v1_19_R3.util;

import net.minecraft.world.phys.Vec3D;
import org.bukkit.util.Vector;

public final class CraftVector {
   private CraftVector() {
   }

   public static Vector toBukkit(Vec3D nms) {
      return new Vector(nms.c, nms.d, nms.e);
   }

   public static Vec3D toNMS(Vector bukkit) {
      return new Vec3D(bukkit.getX(), bukkit.getY(), bukkit.getZ());
   }
}
