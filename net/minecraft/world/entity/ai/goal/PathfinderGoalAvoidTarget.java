package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.IEntitySelector;
import net.minecraft.world.entity.ai.navigation.NavigationAbstract;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.pathfinder.PathEntity;
import net.minecraft.world.phys.Vec3D;

public class PathfinderGoalAvoidTarget<T extends EntityLiving> extends PathfinderGoal {
   protected final EntityCreature a;
   private final double i;
   private final double j;
   @Nullable
   protected T b;
   protected final float c;
   @Nullable
   protected PathEntity d;
   protected final NavigationAbstract e;
   protected final Class<T> f;
   protected final Predicate<EntityLiving> g;
   protected final Predicate<EntityLiving> h;
   private final PathfinderTargetCondition k;

   public PathfinderGoalAvoidTarget(EntityCreature var0, Class<T> var1, float var2, double var3, double var5) {
      this(var0, var1, var0x -> true, var2, var3, var5, IEntitySelector.e::test);
   }

   public PathfinderGoalAvoidTarget(
      EntityCreature var0, Class<T> var1, Predicate<EntityLiving> var2, float var3, double var4, double var6, Predicate<EntityLiving> var8
   ) {
      this.a = var0;
      this.f = var1;
      this.g = var2;
      this.c = var3;
      this.i = var4;
      this.j = var6;
      this.h = var8;
      this.e = var0.G();
      this.a(EnumSet.of(PathfinderGoal.Type.a));
      this.k = PathfinderTargetCondition.a().a((double)var3).a(var8.and(var2));
   }

   public PathfinderGoalAvoidTarget(EntityCreature var0, Class<T> var1, float var2, double var3, double var5, Predicate<EntityLiving> var7) {
      this(var0, var1, var0x -> true, var2, var3, var5, var7);
   }

   @Override
   public boolean a() {
      this.b = this.a
         .H
         .a(this.a.H.a(this.f, this.a.cD().c((double)this.c, 3.0, (double)this.c), var0x -> true), this.k, this.a, this.a.dl(), this.a.dn(), this.a.dr());
      if (this.b == null) {
         return false;
      } else {
         Vec3D var0 = DefaultRandomPos.a(this.a, 16, 7, this.b.de());
         if (var0 == null) {
            return false;
         } else if (this.b.i(var0.c, var0.d, var0.e) < this.b.f(this.a)) {
            return false;
         } else {
            this.d = this.e.a(var0.c, var0.d, var0.e, 0);
            return this.d != null;
         }
      }
   }

   @Override
   public boolean b() {
      return !this.e.l();
   }

   @Override
   public void c() {
      this.e.a(this.d, this.i);
   }

   @Override
   public void d() {
      this.b = null;
   }

   @Override
   public void e() {
      if (this.a.f(this.b) < 49.0) {
         this.a.G().a(this.j);
      } else {
         this.a.G().a(this.i);
      }
   }
}
