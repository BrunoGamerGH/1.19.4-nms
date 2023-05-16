package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.entity.animal.EntityIronGolem;
import net.minecraft.world.entity.npc.EntityVillager;

public class PathfinderGoalOfferFlower extends PathfinderGoal {
   private static final PathfinderTargetCondition b = PathfinderTargetCondition.b().a(6.0);
   public static final int a = 400;
   private final EntityIronGolem c;
   private EntityVillager d;
   private int e;

   public PathfinderGoalOfferFlower(EntityIronGolem var0) {
      this.c = var0;
      this.a(EnumSet.of(PathfinderGoal.Type.a, PathfinderGoal.Type.b));
   }

   @Override
   public boolean a() {
      if (!this.c.H.M()) {
         return false;
      } else if (this.c.dZ().a(8000) != 0) {
         return false;
      } else {
         this.d = this.c.H.a(EntityVillager.class, b, this.c, this.c.dl(), this.c.dn(), this.c.dr(), this.c.cD().c(6.0, 2.0, 6.0));
         return this.d != null;
      }
   }

   @Override
   public boolean b() {
      return this.e > 0;
   }

   @Override
   public void c() {
      this.e = this.a(400);
      this.c.w(true);
   }

   @Override
   public void d() {
      this.c.w(false);
      this.d = null;
   }

   @Override
   public void e() {
      this.c.C().a(this.d, 30.0F, 30.0F);
      --this.e;
   }
}
