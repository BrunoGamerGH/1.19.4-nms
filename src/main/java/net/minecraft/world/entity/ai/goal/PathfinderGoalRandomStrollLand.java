package net.minecraft.world.entity.ai.goal;

import javax.annotation.Nullable;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3D;

public class PathfinderGoalRandomStrollLand extends PathfinderGoalRandomStroll {
   public static final float i = 0.001F;
   protected final float j;

   public PathfinderGoalRandomStrollLand(EntityCreature var0, double var1) {
      this(var0, var1, 0.001F);
   }

   public PathfinderGoalRandomStrollLand(EntityCreature var0, double var1, float var3) {
      super(var0, var1);
      this.j = var3;
   }

   @Nullable
   @Override
   protected Vec3D h() {
      if (this.b.aW()) {
         Vec3D var0 = LandRandomPos.a(this.b, 15, 7);
         return var0 == null ? super.h() : var0;
      } else {
         return this.b.dZ().i() >= this.j ? LandRandomPos.a(this.b, 10, 7) : super.h();
      }
   }
}
