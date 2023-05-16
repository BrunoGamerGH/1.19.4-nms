package org.bukkit.craftbukkit.v1_19_R3.util;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.world.phys.AxisAlignedBB;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.VoxelShape;

public final class CraftVoxelShape implements VoxelShape {
   private final net.minecraft.world.phys.shapes.VoxelShape shape;

   public CraftVoxelShape(net.minecraft.world.phys.shapes.VoxelShape shape) {
      this.shape = shape;
   }

   public Collection<BoundingBox> getBoundingBoxes() {
      List<AxisAlignedBB> boxes = this.shape.d();
      List<BoundingBox> craftBoxes = new ArrayList(boxes.size());

      for(AxisAlignedBB aabb : boxes) {
         craftBoxes.add(new BoundingBox(aabb.a, aabb.b, aabb.c, aabb.d, aabb.e, aabb.f));
      }

      return craftBoxes;
   }

   public boolean overlaps(BoundingBox other) {
      Preconditions.checkArgument(other != null, "Other cannot be null");

      for(BoundingBox box : this.getBoundingBoxes()) {
         if (box.overlaps(other)) {
            return true;
         }
      }

      return false;
   }
}
