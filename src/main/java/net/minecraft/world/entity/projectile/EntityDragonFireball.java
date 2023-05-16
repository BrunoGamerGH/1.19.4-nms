package net.minecraft.world.entity.projectile;

import java.util.List;
import net.minecraft.core.particles.ParticleParam;
import net.minecraft.core.particles.Particles;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityAreaEffectCloud;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.MovingObjectPosition;
import net.minecraft.world.phys.MovingObjectPositionEntity;

public class EntityDragonFireball extends EntityFireball {
   public static final float e = 4.0F;

   public EntityDragonFireball(EntityTypes<? extends EntityDragonFireball> var0, World var1) {
      super(var0, var1);
   }

   public EntityDragonFireball(World var0, EntityLiving var1, double var2, double var4, double var6) {
      super(EntityTypes.x, var1, var2, var4, var6, var0);
   }

   @Override
   protected void a(MovingObjectPosition var0) {
      super.a(var0);
      if (var0.c() != MovingObjectPosition.EnumMovingObjectType.c || !this.d(((MovingObjectPositionEntity)var0).a())) {
         if (!this.H.B) {
            List<EntityLiving> var1 = this.H.a(EntityLiving.class, this.cD().c(4.0, 2.0, 4.0));
            EntityAreaEffectCloud var2 = new EntityAreaEffectCloud(this.H, this.dl(), this.dn(), this.dr());
            Entity var3 = this.v();
            if (var3 instanceof EntityLiving) {
               var2.a((EntityLiving)var3);
            }

            var2.a(Particles.i);
            var2.a(3.0F);
            var2.b(600);
            var2.c((7.0F - var2.h()) / (float)var2.m());
            var2.a(new MobEffect(MobEffects.g, 1, 1));
            if (!var1.isEmpty()) {
               for(EntityLiving var5 : var1) {
                  double var6 = this.f(var5);
                  if (var6 < 16.0) {
                     var2.e(var5.dl(), var5.dn(), var5.dr());
                     break;
                  }
               }
            }

            this.H.c(2006, this.dg(), this.aO() ? -1 : 1);
            this.H.b(var2);
            this.ai();
         }
      }
   }

   @Override
   public boolean bm() {
      return false;
   }

   @Override
   public boolean a(DamageSource var0, float var1) {
      return false;
   }

   @Override
   protected ParticleParam j() {
      return Particles.i;
   }

   @Override
   protected boolean Z_() {
      return false;
   }
}
