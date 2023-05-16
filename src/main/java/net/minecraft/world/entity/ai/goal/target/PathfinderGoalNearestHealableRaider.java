package net.minecraft.world.entity.ai.goal.target;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.raid.EntityRaider;

public class PathfinderGoalNearestHealableRaider<T extends EntityLiving> extends PathfinderGoalNearestAttackableTarget<T> {
   private static final int i = 200;
   private int j = 0;

   public PathfinderGoalNearestHealableRaider(EntityRaider var0, Class<T> var1, boolean var2, @Nullable Predicate<EntityLiving> var3) {
      super(var0, var1, 500, var2, false, var3);
   }

   public int i() {
      return this.j;
   }

   public void k() {
      --this.j;
   }

   @Override
   public boolean a() {
      if (this.j > 0 || !this.e.dZ().h()) {
         return false;
      } else if (!((EntityRaider)this.e).gh()) {
         return false;
      } else {
         this.h();
         return this.c != null;
      }
   }

   @Override
   public void c() {
      this.j = b(200);
      super.c();
   }
}
