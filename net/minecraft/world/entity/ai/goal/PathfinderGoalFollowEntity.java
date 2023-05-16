package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.ai.control.ControllerLook;
import net.minecraft.world.entity.ai.navigation.Navigation;
import net.minecraft.world.entity.ai.navigation.NavigationAbstract;
import net.minecraft.world.entity.ai.navigation.NavigationFlying;
import net.minecraft.world.level.pathfinder.PathType;

public class PathfinderGoalFollowEntity extends PathfinderGoal {
   private final EntityInsentient a;
   private final Predicate<EntityInsentient> b;
   @Nullable
   private EntityInsentient c;
   private final double d;
   private final NavigationAbstract e;
   private int f;
   private final float g;
   private float h;
   private final float i;

   public PathfinderGoalFollowEntity(EntityInsentient var0, double var1, float var3, float var4) {
      this.a = var0;
      this.b = var1x -> var1x != null && var0.getClass() != var1x.getClass();
      this.d = var1;
      this.e = var0.G();
      this.g = var3;
      this.i = var4;
      this.a(EnumSet.of(PathfinderGoal.Type.a, PathfinderGoal.Type.b));
      if (!(var0.G() instanceof Navigation) && !(var0.G() instanceof NavigationFlying)) {
         throw new IllegalArgumentException("Unsupported mob type for FollowMobGoal");
      }
   }

   @Override
   public boolean a() {
      List<EntityInsentient> var0 = this.a.H.a(EntityInsentient.class, this.a.cD().g((double)this.i), this.b);
      if (!var0.isEmpty()) {
         for(EntityInsentient var2 : var0) {
            if (!var2.ca()) {
               this.c = var2;
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public boolean b() {
      return this.c != null && !this.e.l() && this.a.f(this.c) > (double)(this.g * this.g);
   }

   @Override
   public void c() {
      this.f = 0;
      this.h = this.a.a(PathType.j);
      this.a.a(PathType.j, 0.0F);
   }

   @Override
   public void d() {
      this.c = null;
      this.e.n();
      this.a.a(PathType.j, this.h);
   }

   @Override
   public void e() {
      if (this.c != null && !this.a.fI()) {
         this.a.C().a(this.c, 10.0F, (float)this.a.V());
         if (--this.f <= 0) {
            this.f = this.a(10);
            double var0 = this.a.dl() - this.c.dl();
            double var2 = this.a.dn() - this.c.dn();
            double var4 = this.a.dr() - this.c.dr();
            double var6 = var0 * var0 + var2 * var2 + var4 * var4;
            if (!(var6 <= (double)(this.g * this.g))) {
               this.e.a(this.c, this.d);
            } else {
               this.e.n();
               ControllerLook var8 = this.c.C();
               if (var6 <= (double)this.g || var8.e() == this.a.dl() && var8.f() == this.a.dn() && var8.g() == this.a.dr()) {
                  double var9 = this.c.dl() - this.a.dl();
                  double var11 = this.c.dr() - this.a.dr();
                  this.e.a(this.a.dl() - var9, this.a.dn(), this.a.dr() - var11, this.d);
               }
            }
         }
      }
   }
}
