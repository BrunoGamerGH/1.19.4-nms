package net.minecraft.world.entity.boss.enderdragon.phases;

import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderDragon;

public class DragonControllerLandedAttack extends AbstractDragonControllerLanded {
   private static final int b = 40;
   private int c;

   public DragonControllerLandedAttack(EntityEnderDragon var0) {
      super(var0);
   }

   @Override
   public void b() {
      this.a.H.a(this.a.dl(), this.a.dn(), this.a.dr(), SoundEffects.gT, this.a.cX(), 2.5F, 0.8F + this.a.dZ().i() * 0.3F, false);
   }

   @Override
   public void c() {
      if (this.c++ >= 40) {
         this.a.fP().a(DragonControllerPhase.f);
      }
   }

   @Override
   public void d() {
      this.c = 0;
   }

   @Override
   public DragonControllerPhase<DragonControllerLandedAttack> i() {
      return DragonControllerPhase.h;
   }
}
