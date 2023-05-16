package net.minecraft.world.entity.ai.goal;

import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.ai.navigation.Navigation;
import net.minecraft.world.entity.ai.util.PathfinderGoalUtil;

public class PathfinderGoalRestrictSun extends PathfinderGoal {
   private final EntityCreature a;

   public PathfinderGoalRestrictSun(EntityCreature var0) {
      this.a = var0;
   }

   @Override
   public boolean a() {
      return this.a.H.M() && this.a.c(EnumItemSlot.f).b() && PathfinderGoalUtil.a(this.a);
   }

   @Override
   public void c() {
      ((Navigation)this.a.G()).d(true);
   }

   @Override
   public void d() {
      if (PathfinderGoalUtil.a(this.a)) {
         ((Navigation)this.a.G()).d(false);
      }
   }
}
