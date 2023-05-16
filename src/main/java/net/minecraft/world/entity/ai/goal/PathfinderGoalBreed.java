package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.entity.animal.EntityAnimal;
import net.minecraft.world.level.World;

public class PathfinderGoalBreed extends PathfinderGoal {
   private static final PathfinderTargetCondition d = PathfinderTargetCondition.b().a(8.0).d();
   protected final EntityAnimal a;
   private final Class<? extends EntityAnimal> e;
   protected final World b;
   @Nullable
   protected EntityAnimal c;
   private int f;
   private final double g;

   public PathfinderGoalBreed(EntityAnimal var0, double var1) {
      this(var0, var1, var0.getClass());
   }

   public PathfinderGoalBreed(EntityAnimal var0, double var1, Class<? extends EntityAnimal> var3) {
      this.a = var0;
      this.b = var0.H;
      this.e = var3;
      this.g = var1;
      this.a(EnumSet.of(PathfinderGoal.Type.a, PathfinderGoal.Type.b));
   }

   @Override
   public boolean a() {
      if (!this.a.fW()) {
         return false;
      } else {
         this.c = this.h();
         return this.c != null;
      }
   }

   @Override
   public boolean b() {
      return this.c.bq() && this.c.fW() && this.f < 60;
   }

   @Override
   public void d() {
      this.c = null;
      this.f = 0;
   }

   @Override
   public void e() {
      this.a.C().a(this.c, 10.0F, (float)this.a.V());
      this.a.G().a(this.c, this.g);
      ++this.f;
      if (this.f >= this.a(60) && this.a.f(this.c) < 9.0) {
         this.g();
      }
   }

   @Nullable
   private EntityAnimal h() {
      List<? extends EntityAnimal> var0 = this.b.a(this.e, d, this.a, this.a.cD().g(8.0));
      double var1 = Double.MAX_VALUE;
      EntityAnimal var3 = null;

      for(EntityAnimal var5 : var0) {
         if (this.a.a(var5) && this.a.f(var5) < var1) {
            var3 = var5;
            var1 = this.a.f(var5);
         }
      }

      return var3;
   }

   protected void g() {
      this.a.a((WorldServer)this.b, this.c);
   }
}
