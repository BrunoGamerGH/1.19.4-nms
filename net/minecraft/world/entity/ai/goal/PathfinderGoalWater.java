package net.minecraft.world.entity.ai.goal;

import net.minecraft.core.BlockPosition;
import net.minecraft.tags.TagsFluid;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.EntityCreature;

public class PathfinderGoalWater extends PathfinderGoal {
   private final EntityCreature a;

   public PathfinderGoalWater(EntityCreature var0) {
      this.a = var0;
   }

   @Override
   public boolean a() {
      return this.a.ax() && !this.a.H.b_(this.a.dg()).a(TagsFluid.a);
   }

   @Override
   public void c() {
      BlockPosition var0 = null;

      for(BlockPosition var3 : BlockPosition.b(
         MathHelper.a(this.a.dl() - 2.0),
         MathHelper.a(this.a.dn() - 2.0),
         MathHelper.a(this.a.dr() - 2.0),
         MathHelper.a(this.a.dl() + 2.0),
         this.a.dm(),
         MathHelper.a(this.a.dr() + 2.0)
      )) {
         if (this.a.H.b_(var3).a(TagsFluid.a)) {
            var0 = var3;
            break;
         }
      }

      if (var0 != null) {
         this.a.D().a((double)var0.u(), (double)var0.v(), (double)var0.w(), 1.0);
      }
   }
}
