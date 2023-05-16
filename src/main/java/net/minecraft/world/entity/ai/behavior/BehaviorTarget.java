package net.minecraft.world.entity.ai.behavior;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.phys.Vec3D;

public class BehaviorTarget implements BehaviorPosition {
   private final BlockPosition a;
   private final Vec3D b;

   public BehaviorTarget(BlockPosition var0) {
      this.a = var0.i();
      this.b = Vec3D.b(var0);
   }

   public BehaviorTarget(Vec3D var0) {
      this.a = BlockPosition.a(var0);
      this.b = var0;
   }

   @Override
   public Vec3D a() {
      return this.b;
   }

   @Override
   public BlockPosition b() {
      return this.a;
   }

   @Override
   public boolean a(EntityLiving var0) {
      return true;
   }

   @Override
   public String toString() {
      return "BlockPosTracker{blockPos=" + this.a + ", centerPosition=" + this.b + "}";
   }
}
