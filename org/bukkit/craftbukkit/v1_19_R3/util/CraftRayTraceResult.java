package org.bukkit.craftbukkit.v1_19_R3.util;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.phys.MovingObjectPosition;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.MovingObjectPositionEntity;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.entity.Entity;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public final class CraftRayTraceResult {
   private CraftRayTraceResult() {
   }

   public static RayTraceResult fromNMS(World world, MovingObjectPosition nmsHitResult) {
      if (nmsHitResult != null && nmsHitResult.c() != MovingObjectPosition.EnumMovingObjectType.a) {
         Vec3D nmsHitPos = nmsHitResult.e();
         Vector hitPosition = new Vector(nmsHitPos.c, nmsHitPos.d, nmsHitPos.e);
         BlockFace hitBlockFace = null;
         if (nmsHitResult.c() == MovingObjectPosition.EnumMovingObjectType.c) {
            Entity hitEntity = ((MovingObjectPositionEntity)nmsHitResult).a().getBukkitEntity();
            return new RayTraceResult(hitPosition, hitEntity, null);
         } else {
            Block hitBlock = null;
            BlockPosition nmsBlockPos = null;
            if (nmsHitResult.c() == MovingObjectPosition.EnumMovingObjectType.b) {
               MovingObjectPositionBlock blockHitResult = (MovingObjectPositionBlock)nmsHitResult;
               hitBlockFace = CraftBlock.notchToBlockFace(blockHitResult.b());
               nmsBlockPos = blockHitResult.a();
            }

            if (nmsBlockPos != null && world != null) {
               hitBlock = world.getBlockAt(nmsBlockPos.u(), nmsBlockPos.v(), nmsBlockPos.w());
            }

            return new RayTraceResult(hitPosition, hitBlock, hitBlockFace);
         }
      } else {
         return null;
      }
   }
}
