package net.minecraft.world.phys;

import net.minecraft.world.entity.Entity;

public class MovingObjectPositionEntity extends MovingObjectPosition {
   private final Entity b;

   public MovingObjectPositionEntity(Entity var0) {
      this(var0, var0.de());
   }

   public MovingObjectPositionEntity(Entity var0, Vec3D var1) {
      super(var1);
      this.b = var0;
   }

   public Entity a() {
      return this.b;
   }

   @Override
   public MovingObjectPosition.EnumMovingObjectType c() {
      return MovingObjectPosition.EnumMovingObjectType.c;
   }
}
