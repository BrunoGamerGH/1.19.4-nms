package net.minecraft.world.entity.boss.enderdragon.phases;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderDragon;
import net.minecraft.world.entity.projectile.EntityArrow;

public abstract class AbstractDragonControllerLanded extends AbstractDragonController {
   public AbstractDragonControllerLanded(EntityEnderDragon var0) {
      super(var0);
   }

   @Override
   public boolean a() {
      return true;
   }

   @Override
   public float a(DamageSource var0, float var1) {
      if (var0.c() instanceof EntityArrow) {
         var0.c().f(1);
         return 0.0F;
      } else {
         return super.a(var0, var1);
      }
   }
}
