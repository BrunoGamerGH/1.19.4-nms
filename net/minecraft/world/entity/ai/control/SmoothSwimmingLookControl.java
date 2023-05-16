package net.minecraft.world.entity.ai.control;

import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.EntityInsentient;

public class SmoothSwimmingLookControl extends ControllerLook {
   private final int h;
   private static final int i = 10;
   private static final int j = 20;

   public SmoothSwimmingLookControl(EntityInsentient var0, int var1) {
      super(var0);
      this.h = var1;
   }

   @Override
   public void a() {
      if (this.d > 0) {
         --this.d;
         this.i().ifPresent(var0x -> this.a.aV = this.a(this.a.aV, var0x + 20.0F, this.b));
         this.h().ifPresent(var0x -> this.a.e(this.a(this.a.dy(), var0x + 10.0F, this.c)));
      } else {
         if (this.a.G().l()) {
            this.a.e(this.a(this.a.dy(), 0.0F, 5.0F));
         }

         this.a.aV = this.a(this.a.aV, this.a.aT, this.b);
      }

      float var0 = MathHelper.g(this.a.aV - this.a.aT);
      if (var0 < (float)(-this.h)) {
         this.a.aT -= 4.0F;
      } else if (var0 > (float)this.h) {
         this.a.aT += 4.0F;
      }
   }
}
