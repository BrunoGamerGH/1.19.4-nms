package net.minecraft.world.entity.boss.enderdragon.phases;

import com.mojang.logging.LogUtils;
import javax.annotation.Nullable;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderDragon;
import net.minecraft.world.entity.projectile.EntityDragonFireball;
import net.minecraft.world.level.pathfinder.PathEntity;
import net.minecraft.world.level.pathfinder.PathPoint;
import net.minecraft.world.phys.Vec3D;
import org.slf4j.Logger;

public class DragonControllerStrafe extends AbstractDragonController {
   private static final Logger b = LogUtils.getLogger();
   private static final int c = 5;
   private int d;
   @Nullable
   private PathEntity e;
   @Nullable
   private Vec3D f;
   @Nullable
   private EntityLiving g;
   private boolean h;

   public DragonControllerStrafe(EntityEnderDragon var0) {
      super(var0);
   }

   @Override
   public void c() {
      if (this.g == null) {
         b.warn("Skipping player strafe phase because no player was found");
         this.a.fP().a(DragonControllerPhase.a);
      } else {
         if (this.e != null && this.e.c()) {
            double var0 = this.g.dl();
            double var2 = this.g.dr();
            double var4 = var0 - this.a.dl();
            double var6 = var2 - this.a.dr();
            double var8 = Math.sqrt(var4 * var4 + var6 * var6);
            double var10 = Math.min(0.4F + var8 / 80.0 - 1.0, 10.0);
            this.f = new Vec3D(var0, this.g.dn() + var10, var2);
         }

         double var0 = this.f == null ? 0.0 : this.f.c(this.a.dl(), this.a.dn(), this.a.dr());
         if (var0 < 100.0 || var0 > 22500.0) {
            this.j();
         }

         double var2 = 64.0;
         if (this.g.f(this.a) < 4096.0) {
            if (this.a.B(this.g)) {
               ++this.d;
               Vec3D var4 = new Vec3D(this.g.dl() - this.a.dl(), 0.0, this.g.dr() - this.a.dr()).d();
               Vec3D var5 = new Vec3D(
                     (double)MathHelper.a(this.a.dw() * (float) (Math.PI / 180.0)), 0.0, (double)(-MathHelper.b(this.a.dw() * (float) (Math.PI / 180.0)))
                  )
                  .d();
               float var6 = (float)var5.b(var4);
               float var7 = (float)(Math.acos((double)var6) * 180.0F / (float)Math.PI);
               var7 += 0.5F;
               if (this.d >= 5 && var7 >= 0.0F && var7 < 10.0F) {
                  double var8 = 1.0;
                  Vec3D var10 = this.a.j(1.0F);
                  double var11 = this.a.e.dl() - var10.c * 1.0;
                  double var13 = this.a.e.e(0.5) + 0.5;
                  double var15 = this.a.e.dr() - var10.e * 1.0;
                  double var17 = this.g.dl() - var11;
                  double var19 = this.g.e(0.5) - var13;
                  double var21 = this.g.dr() - var15;
                  if (!this.a.aO()) {
                     this.a.H.a(null, 1017, this.a.dg(), 0);
                  }

                  EntityDragonFireball var23 = new EntityDragonFireball(this.a.H, this.a, var17, var19, var21);
                  var23.b(var11, var13, var15, 0.0F, 0.0F);
                  this.a.H.b(var23);
                  this.d = 0;
                  if (this.e != null) {
                     while(!this.e.c()) {
                        this.e.a();
                     }
                  }

                  this.a.fP().a(DragonControllerPhase.a);
               }
            } else if (this.d > 0) {
               --this.d;
            }
         } else if (this.d > 0) {
            --this.d;
         }
      }
   }

   private void j() {
      if (this.e == null || this.e.c()) {
         int var0 = this.a.r();
         int var1 = var0;
         if (this.a.dZ().a(8) == 0) {
            this.h = !this.h;
            var1 = var0 + 6;
         }

         if (this.h) {
            ++var1;
         } else {
            --var1;
         }

         if (this.a.fQ() != null && this.a.fQ().c() > 0) {
            var1 %= 12;
            if (var1 < 0) {
               var1 += 12;
            }
         } else {
            var1 -= 12;
            var1 &= 7;
            var1 += 12;
         }

         this.e = this.a.a(var0, var1, null);
         if (this.e != null) {
            this.e.a();
         }
      }

      this.k();
   }

   private void k() {
      if (this.e != null && !this.e.c()) {
         BaseBlockPosition var0 = this.e.g();
         this.e.a();
         double var1 = (double)var0.u();
         double var5 = (double)var0.w();

         double var3;
         do {
            var3 = (double)((float)var0.v() + this.a.dZ().i() * 20.0F);
         } while(var3 < (double)var0.v());

         this.f = new Vec3D(var1, var3, var5);
      }
   }

   @Override
   public void d() {
      this.d = 0;
      this.f = null;
      this.e = null;
      this.g = null;
   }

   public void a(EntityLiving var0) {
      this.g = var0;
      int var1 = this.a.r();
      int var2 = this.a.r(this.g.dl(), this.g.dn(), this.g.dr());
      int var3 = this.g.dk();
      int var4 = this.g.dq();
      double var5 = (double)var3 - this.a.dl();
      double var7 = (double)var4 - this.a.dr();
      double var9 = Math.sqrt(var5 * var5 + var7 * var7);
      double var11 = Math.min(0.4F + var9 / 80.0 - 1.0, 10.0);
      int var13 = MathHelper.a(this.g.dn() + var11);
      PathPoint var14 = new PathPoint(var3, var13, var4);
      this.e = this.a.a(var1, var2, var14);
      if (this.e != null) {
         this.e.a();
         this.k();
      }
   }

   @Nullable
   @Override
   public Vec3D g() {
      return this.f;
   }

   @Override
   public DragonControllerPhase<DragonControllerStrafe> i() {
      return DragonControllerPhase.b;
   }
}
