package net.minecraft.world.entity.ai.goal.target;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.raid.EntityRaider;

public class PathfinderGoalNearestAttackableTargetWitch<T extends EntityLiving> extends PathfinderGoalNearestAttackableTarget<T> {
   private boolean i = true;

   public PathfinderGoalNearestAttackableTargetWitch(
      EntityRaider var0, Class<T> var1, int var2, boolean var3, boolean var4, @Nullable Predicate<EntityLiving> var5
   ) {
      super(var0, var1, var2, var3, var4, var5);
   }

   public void a(boolean var0) {
      this.i = var0;
   }

   @Override
   public boolean a() {
      return this.i && super.a();
   }
}
