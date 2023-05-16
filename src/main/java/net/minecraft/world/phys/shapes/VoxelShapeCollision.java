package net.minecraft.world.phys.shapes;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;

public interface VoxelShapeCollision {
   static VoxelShapeCollision a() {
      return VoxelShapeCollisionEntity.a;
   }

   static VoxelShapeCollision a(Entity var0) {
      return new VoxelShapeCollisionEntity(var0);
   }

   boolean b();

   boolean a(VoxelShape var1, BlockPosition var2, boolean var3);

   boolean a(Item var1);

   boolean a(Fluid var1, Fluid var2);
}
