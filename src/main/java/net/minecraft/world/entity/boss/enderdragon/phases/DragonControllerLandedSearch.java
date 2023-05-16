package net.minecraft.world.entity.boss.enderdragon.phases;

import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderDragon;
import net.minecraft.world.phys.Vec3D;

public class DragonControllerLandedSearch extends AbstractDragonControllerLanded {
   private static final int b = 100;
   private static final int c = 10;
   private static final int d = 20;
   private static final int e = 150;
   private static final PathfinderTargetCondition f = PathfinderTargetCondition.a().a(150.0);
   private final PathfinderTargetCondition g;
   private int h;

   public DragonControllerLandedSearch(EntityEnderDragon var0) {
      super(var0);
      this.g = PathfinderTargetCondition.a().a(20.0).a(var1x -> Math.abs(var1x.dn() - var0.dn()) <= 10.0);
   }

   @Override
   public void c() {
      ++this.h;
      EntityLiving var0 = this.a.H.a(this.g, this.a, this.a.dl(), this.a.dn(), this.a.dr());
      if (var0 != null) {
         if (this.h > 25) {
            this.a.fP().a(DragonControllerPhase.h);
         } else {
            Vec3D var1 = new Vec3D(var0.dl() - this.a.dl(), 0.0, var0.dr() - this.a.dr()).d();
            Vec3D var2 = new Vec3D(
                  (double)MathHelper.a(this.a.dw() * (float) (Math.PI / 180.0)), 0.0, (double)(-MathHelper.b(this.a.dw() * (float) (Math.PI / 180.0)))
               )
               .d();
            float var3 = (float)var2.b(var1);
            float var4 = (float)(Math.acos((double)var3) * 180.0F / (float)Math.PI) + 0.5F;
            if (var4 < 0.0F || var4 > 10.0F) {
               double var5 = var0.dl() - this.a.e.dl();
               double var7 = var0.dr() - this.a.e.dr();
               double var9 = MathHelper.a(MathHelper.d(180.0 - MathHelper.d(var5, var7) * 180.0F / (float)Math.PI - (double)this.a.dw()), -100.0, 100.0);
               this.a.bV *= 0.8F;
               float var11 = (float)Math.sqrt(var5 * var5 + var7 * var7) + 1.0F;
               float var12 = var11;
               if (var11 > 40.0F) {
                  var11 = 40.0F;
               }

               this.a.bV += (float)var9 * (0.7F / var11 / var12);
               this.a.f(this.a.dw() + this.a.bV);
            }
         }
      } else if (this.h >= 100) {
         var0 = this.a.H.a(f, this.a, this.a.dl(), this.a.dn(), this.a.dr());
         this.a.fP().a(DragonControllerPhase.e);
         if (var0 != null) {
            this.a.fP().a(DragonControllerPhase.i);
            this.a.fP().b(DragonControllerPhase.i).a(new Vec3D(var0.dl(), var0.dn(), var0.dr()));
         }
      }
   }

   @Override
   public void d() {
      this.h = 0;
   }

   @Override
   public DragonControllerPhase<DragonControllerLandedSearch> i() {
      return DragonControllerPhase.g;
   }
}
