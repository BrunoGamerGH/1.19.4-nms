package net.minecraft.world.entity.ai.goal;

import net.minecraft.sounds.SoundEffect;
import net.minecraft.world.entity.animal.horse.EntityHorseAbstract;

public class RandomStandGoal extends PathfinderGoal {
   private final EntityHorseAbstract a;
   private int b;

   public RandomStandGoal(EntityHorseAbstract var0) {
      this.a = var0;
      this.a(var0);
   }

   @Override
   public void c() {
      this.a.gx();
      this.h();
   }

   private void h() {
      SoundEffect var0 = this.a.gw();
      if (var0 != null) {
         this.a.a(var0);
      }
   }

   @Override
   public boolean b() {
      return false;
   }

   @Override
   public boolean a() {
      ++this.b;
      if (this.b > 0 && this.a.dZ().a(1000) < this.b) {
         this.a(this.a);
         return !this.a.eP() && this.a.dZ().a(10) == 0;
      } else {
         return false;
      }
   }

   private void a(EntityHorseAbstract var0) {
      this.b = -var0.gD();
   }

   @Override
   public boolean J_() {
      return true;
   }
}
