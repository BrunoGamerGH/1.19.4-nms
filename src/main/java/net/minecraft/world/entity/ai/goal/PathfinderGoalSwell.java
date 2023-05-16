package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.monster.EntityCreeper;

public class PathfinderGoalSwell extends PathfinderGoal {
   private final EntityCreeper a;
   @Nullable
   private EntityLiving b;

   public PathfinderGoalSwell(EntityCreeper var0) {
      this.a = var0;
      this.a(EnumSet.of(PathfinderGoal.Type.a));
   }

   @Override
   public boolean a() {
      EntityLiving var0 = this.a.P_();
      return this.a.r() > 0 || var0 != null && this.a.f(var0) < 9.0;
   }

   @Override
   public void c() {
      this.a.G().n();
      this.b = this.a.P_();
   }

   @Override
   public void d() {
      this.b = null;
   }

   @Override
   public boolean J_() {
      return true;
   }

   @Override
   public void e() {
      if (this.b == null) {
         this.a.b(-1);
      } else if (this.a.f(this.b) > 49.0) {
         this.a.b(-1);
      } else if (!this.a.I().a(this.b)) {
         this.a.b(-1);
      } else {
         this.a.b(1);
      }
   }
}
