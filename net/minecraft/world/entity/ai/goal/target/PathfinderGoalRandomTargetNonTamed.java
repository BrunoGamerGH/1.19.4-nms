package net.minecraft.world.entity.ai.goal.target;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTameableAnimal;

public class PathfinderGoalRandomTargetNonTamed<T extends EntityLiving> extends PathfinderGoalNearestAttackableTarget<T> {
   private final EntityTameableAnimal i;

   public PathfinderGoalRandomTargetNonTamed(EntityTameableAnimal var0, Class<T> var1, boolean var2, @Nullable Predicate<EntityLiving> var3) {
      super(var0, var1, 10, var2, false, var3);
      this.i = var0;
   }

   @Override
   public boolean a() {
      return !this.i.q() && super.a();
   }

   @Override
   public boolean b() {
      return this.d != null ? this.d.a(this.e, this.c) : super.b();
   }
}
