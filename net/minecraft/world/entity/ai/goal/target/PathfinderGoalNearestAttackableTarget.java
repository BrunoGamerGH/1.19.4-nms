package net.minecraft.world.entity.ai.goal.target;

import java.util.EnumSet;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.phys.AxisAlignedBB;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class PathfinderGoalNearestAttackableTarget<T extends EntityLiving> extends PathfinderGoalTarget {
   private static final int i = 10;
   protected final Class<T> a;
   protected final int b;
   @Nullable
   protected EntityLiving c;
   protected PathfinderTargetCondition d;

   public PathfinderGoalNearestAttackableTarget(EntityInsentient entityinsentient, Class<T> oclass, boolean flag) {
      this(entityinsentient, oclass, 10, flag, false, null);
   }

   public PathfinderGoalNearestAttackableTarget(EntityInsentient entityinsentient, Class<T> oclass, boolean flag, Predicate<EntityLiving> predicate) {
      this(entityinsentient, oclass, 10, flag, false, predicate);
   }

   public PathfinderGoalNearestAttackableTarget(EntityInsentient entityinsentient, Class<T> oclass, boolean flag, boolean flag1) {
      this(entityinsentient, oclass, 10, flag, flag1, null);
   }

   public PathfinderGoalNearestAttackableTarget(
      EntityInsentient entityinsentient, Class<T> oclass, int i, boolean flag, boolean flag1, @Nullable Predicate<EntityLiving> predicate
   ) {
      super(entityinsentient, flag, flag1);
      this.a = oclass;
      this.b = b(i);
      this.a(EnumSet.of(PathfinderGoal.Type.d));
      this.d = PathfinderTargetCondition.a().a(this.l()).a(predicate);
   }

   @Override
   public boolean a() {
      if (this.b > 0 && this.e.dZ().a(this.b) != 0) {
         return false;
      } else {
         this.h();
         return this.c != null;
      }
   }

   protected AxisAlignedBB a(double d0) {
      return this.e.cD().c(d0, 4.0, d0);
   }

   protected void h() {
      if (this.a != EntityHuman.class && this.a != EntityPlayer.class) {
         this.c = this.e.H.a(this.e.H.a(this.a, this.a(this.l()), entityliving -> true), this.d, this.e, this.e.dl(), this.e.dp(), this.e.dr());
      } else {
         this.c = this.e.H.a(this.d, this.e, this.e.dl(), this.e.dp(), this.e.dr());
      }
   }

   @Override
   public void c() {
      this.e.setTarget(this.c, this.c instanceof EntityPlayer ? TargetReason.CLOSEST_PLAYER : TargetReason.CLOSEST_ENTITY, true);
      super.c();
   }

   public void a(@Nullable EntityLiving entityliving) {
      this.c = entityliving;
   }
}
