package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.core.BlockPosition;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.animal.EntityCat;
import net.minecraft.world.level.IWorldReader;

public class PathfinderGoalCatSitOnBed extends PathfinderGoalGotoTarget {
   private final EntityCat g;

   public PathfinderGoalCatSitOnBed(EntityCat var0, double var1, int var3) {
      super(var0, var1, var3, 6);
      this.g = var0;
      this.f = -2;
      this.a(EnumSet.of(PathfinderGoal.Type.c, PathfinderGoal.Type.a));
   }

   @Override
   public boolean a() {
      return this.g.q() && !this.g.fS() && !this.g.ga() && super.a();
   }

   @Override
   public void c() {
      super.c();
      this.g.y(false);
   }

   @Override
   protected int a(EntityCreature var0) {
      return 40;
   }

   @Override
   public void d() {
      super.d();
      this.g.A(false);
   }

   @Override
   public void e() {
      super.e();
      this.g.y(false);
      if (!this.m()) {
         this.g.A(false);
      } else if (!this.g.ga()) {
         this.g.A(true);
      }
   }

   @Override
   protected boolean a(IWorldReader var0, BlockPosition var1) {
      return var0.w(var1.c()) && var0.a_(var1).a(TagsBlock.Q);
   }
}
