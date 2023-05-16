package net.minecraft.world.entity.boss.enderdragon.phases;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.Particles;
import net.minecraft.util.MathHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityAreaEffectCloud;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderDragon;
import net.minecraft.world.phys.Vec3D;

public class DragonControllerLandedFlame extends AbstractDragonControllerLanded {
   private static final int b = 200;
   private static final int c = 4;
   private static final int d = 10;
   private int e;
   private int f;
   @Nullable
   private EntityAreaEffectCloud g;

   public DragonControllerLandedFlame(EntityEnderDragon var0) {
      super(var0);
   }

   @Override
   public void b() {
      ++this.e;
      if (this.e % 2 == 0 && this.e < 10) {
         Vec3D var0 = this.a.B(1.0F).d();
         var0.b((float) (-Math.PI / 4));
         double var1 = this.a.e.dl();
         double var3 = this.a.e.e(0.5);
         double var5 = this.a.e.dr();

         for(int var7 = 0; var7 < 8; ++var7) {
            double var8 = var1 + this.a.dZ().k() / 2.0;
            double var10 = var3 + this.a.dZ().k() / 2.0;
            double var12 = var5 + this.a.dZ().k() / 2.0;

            for(int var14 = 0; var14 < 6; ++var14) {
               this.a.H.a(Particles.i, var8, var10, var12, -var0.c * 0.08F * (double)var14, -var0.d * 0.6F, -var0.e * 0.08F * (double)var14);
            }

            var0.b((float) (Math.PI / 16));
         }
      }
   }

   @Override
   public void c() {
      ++this.e;
      if (this.e >= 200) {
         if (this.f >= 4) {
            this.a.fP().a(DragonControllerPhase.e);
         } else {
            this.a.fP().a(DragonControllerPhase.g);
         }
      } else if (this.e == 10) {
         Vec3D var0 = new Vec3D(this.a.e.dl() - this.a.dl(), 0.0, this.a.e.dr() - this.a.dr()).d();
         float var1 = 5.0F;
         double var2 = this.a.e.dl() + var0.c * 5.0 / 2.0;
         double var4 = this.a.e.dr() + var0.e * 5.0 / 2.0;
         double var6 = this.a.e.e(0.5);
         double var8 = var6;
         BlockPosition.MutableBlockPosition var10 = new BlockPosition.MutableBlockPosition(var2, var6, var4);

         while(this.a.H.w(var10)) {
            if (--var8 < 0.0) {
               var8 = var6;
               break;
            }

            var10.b(var2, var8, var4);
         }

         var8 = (double)(MathHelper.a(var8) + 1);
         this.g = new EntityAreaEffectCloud(this.a.H, var2, var8, var4);
         this.g.a(this.a);
         this.g.a(5.0F);
         this.g.b(200);
         this.g.a(Particles.i);
         this.g.a(new MobEffect(MobEffects.g));
         this.a.H.b(this.g);
      }
   }

   @Override
   public void d() {
      this.e = 0;
      ++this.f;
   }

   @Override
   public void e() {
      if (this.g != null) {
         this.g.ai();
         this.g = null;
      }
   }

   @Override
   public DragonControllerPhase<DragonControllerLandedFlame> i() {
      return DragonControllerPhase.f;
   }

   public void j() {
      this.f = 0;
   }
}
