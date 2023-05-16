package net.minecraft.world.entity.ai.memory;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.behavior.BehaviorPosition;
import net.minecraft.world.entity.ai.behavior.BehaviorPositionEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorTarget;
import net.minecraft.world.phys.Vec3D;

public class MemoryTarget {
   private final BehaviorPosition a;
   private final float b;
   private final int c;

   public MemoryTarget(BlockPosition var0, float var1, int var2) {
      this(new BehaviorTarget(var0), var1, var2);
   }

   public MemoryTarget(Vec3D var0, float var1, int var2) {
      this(new BehaviorTarget(BlockPosition.a(var0)), var1, var2);
   }

   public MemoryTarget(Entity var0, float var1, int var2) {
      this(new BehaviorPositionEntity(var0, false), var1, var2);
   }

   public MemoryTarget(BehaviorPosition var0, float var1, int var2) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
   }

   public BehaviorPosition a() {
      return this.a;
   }

   public float b() {
      return this.b;
   }

   public int c() {
      return this.c;
   }
}
