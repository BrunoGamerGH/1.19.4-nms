package net.minecraft.world.entity.ai.navigation;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.level.World;
import net.minecraft.world.level.pathfinder.PathEntity;

public class NavigationSpider extends Navigation {
   @Nullable
   private BlockPosition p;

   public NavigationSpider(EntityInsentient var0, World var1) {
      super(var0, var1);
   }

   @Override
   public PathEntity a(BlockPosition var0, int var1) {
      this.p = var0;
      return super.a(var0, var1);
   }

   @Override
   public PathEntity a(Entity var0, int var1) {
      this.p = var0.dg();
      return super.a(var0, var1);
   }

   @Override
   public boolean a(Entity var0, double var1) {
      PathEntity var3 = this.a(var0, 0);
      if (var3 != null) {
         return this.a(var3, var1);
      } else {
         this.p = var0.dg();
         this.d = var1;
         return true;
      }
   }

   @Override
   public void c() {
      if (!this.l()) {
         super.c();
      } else {
         if (this.p != null) {
            if (!this.p.a(this.a.de(), (double)this.a.dc())
               && (
                  !(this.a.dn() > (double)this.p.v())
                     || !BlockPosition.a((double)this.p.u(), this.a.dn(), (double)this.p.w()).a(this.a.de(), (double)this.a.dc())
               )) {
               this.a.D().a((double)this.p.u(), (double)this.p.v(), (double)this.p.w(), this.d);
            } else {
               this.p = null;
            }
         }
      }
   }
}
