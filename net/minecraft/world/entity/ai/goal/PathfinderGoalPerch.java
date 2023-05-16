package net.minecraft.world.entity.ai.goal;

import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.animal.EntityPerchable;

public class PathfinderGoalPerch extends PathfinderGoal {
   private final EntityPerchable a;
   private EntityPlayer b;
   private boolean c;

   public PathfinderGoalPerch(EntityPerchable var0) {
      this.a = var0;
   }

   @Override
   public boolean a() {
      EntityPlayer var0 = (EntityPlayer)this.a.H_();
      boolean var1 = var0 != null && !var0.F_() && !var0.fK().b && !var0.aT() && !var0.az;
      return !this.a.fS() && var1 && this.a.gb();
   }

   @Override
   public boolean I_() {
      return !this.c;
   }

   @Override
   public void c() {
      this.b = (EntityPlayer)this.a.H_();
      this.c = false;
   }

   @Override
   public void e() {
      if (!this.c && !this.a.w() && !this.a.fI()) {
         if (this.a.cD().c(this.b.cD())) {
            this.c = this.a.b(this.b);
         }
      }
   }
}
