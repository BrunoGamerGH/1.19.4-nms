package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.tags.TagsFluid;
import net.minecraft.world.entity.EntityInsentient;

public class PathfinderGoalFloat extends PathfinderGoal {
   private final EntityInsentient a;

   public PathfinderGoalFloat(EntityInsentient var0) {
      this.a = var0;
      this.a(EnumSet.of(PathfinderGoal.Type.c));
      var0.G().a(true);
   }

   @Override
   public boolean a() {
      return this.a.aT() && this.a.b(TagsFluid.a) > this.a.db() || this.a.bg();
   }

   @Override
   public boolean J_() {
      return true;
   }

   @Override
   public void e() {
      if (this.a.dZ().i() < 0.8F) {
         this.a.E().a();
      }
   }
}
