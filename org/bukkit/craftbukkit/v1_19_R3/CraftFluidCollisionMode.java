package org.bukkit.craftbukkit.v1_19_R3;

import net.minecraft.world.level.RayTrace;
import org.bukkit.FluidCollisionMode;

public final class CraftFluidCollisionMode {
   private CraftFluidCollisionMode() {
   }

   public static RayTrace.FluidCollisionOption toNMS(FluidCollisionMode fluidCollisionMode) {
      if (fluidCollisionMode == null) {
         return null;
      } else {
         switch(fluidCollisionMode) {
            case NEVER:
               return RayTrace.FluidCollisionOption.a;
            case SOURCE_ONLY:
               return RayTrace.FluidCollisionOption.b;
            case ALWAYS:
               return RayTrace.FluidCollisionOption.c;
            default:
               return null;
         }
      }
   }
}
