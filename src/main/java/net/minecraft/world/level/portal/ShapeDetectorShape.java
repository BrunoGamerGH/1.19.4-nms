package net.minecraft.world.level.portal;

import net.minecraft.server.level.WorldServer;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftPortalEvent;

public class ShapeDetectorShape {
   public final Vec3D a;
   public final Vec3D b;
   public final float c;
   public final float d;
   public final WorldServer world;
   public final CraftPortalEvent portalEventInfo;

   public ShapeDetectorShape(Vec3D vec3d, Vec3D vec3d1, float f, float f1, WorldServer world, CraftPortalEvent portalEventInfo) {
      this.world = world;
      this.portalEventInfo = portalEventInfo;
      this.a = vec3d;
      this.b = vec3d1;
      this.c = f;
      this.d = f1;
   }
}
