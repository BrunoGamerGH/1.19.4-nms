package net.minecraft.world.entity.ai.goal;

import net.minecraft.world.entity.EntityInsentient;

public class PathfinderGoalDoorOpen extends PathfinderGoalDoorInteract {
   private final boolean a;
   private int b;

   public PathfinderGoalDoorOpen(EntityInsentient var0, boolean var1) {
      super(var0);
      this.d = var0;
      this.a = var1;
   }

   @Override
   public boolean b() {
      return this.a && this.b > 0 && super.b();
   }

   @Override
   public void c() {
      this.b = 20;
      this.a(true);
   }

   @Override
   public void d() {
      this.a(false);
   }

   @Override
   public void e() {
      --this.b;
      super.e();
   }
}
