package net.minecraft.world.entity.ai.goal.target;

import java.util.List;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.IEntityAngerable;
import net.minecraft.world.entity.IEntitySelector;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.phys.AxisAlignedBB;

public class PathfinderGoalUniversalAngerReset<T extends EntityInsentient & IEntityAngerable> extends PathfinderGoal {
   private static final int a = 10;
   private final T b;
   private final boolean c;
   private int d;

   public PathfinderGoalUniversalAngerReset(T var0, boolean var1) {
      this.b = var0;
      this.c = var1;
   }

   @Override
   public boolean a() {
      return this.b.H.W().b(GameRules.K) && this.h();
   }

   private boolean h() {
      return this.b.ea() != null && this.b.ea().ae() == EntityTypes.bt && this.b.eb() > this.d;
   }

   @Override
   public void c() {
      this.d = this.b.eb();
      this.b.S_();
      if (this.c) {
         this.i().stream().filter(var0 -> var0 != this.b).map(var0 -> (IEntityAngerable)var0).forEach(IEntityAngerable::S_);
      }

      super.c();
   }

   private List<? extends EntityInsentient> i() {
      double var0 = this.b.b(GenericAttributes.b);
      AxisAlignedBB var2 = AxisAlignedBB.a(this.b.de()).c(var0, 10.0, var0);
      return this.b.H.a(this.b.getClass(), var2, IEntitySelector.f);
   }
}
