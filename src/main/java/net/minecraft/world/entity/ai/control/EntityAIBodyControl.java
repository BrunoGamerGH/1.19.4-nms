package net.minecraft.world.entity.ai.control;

import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.EntityInsentient;

public class EntityAIBodyControl implements Control {
   private final EntityInsentient a;
   private static final int b = 15;
   private static final int c = 10;
   private static final int d = 10;
   private int e;
   private float f;

   public EntityAIBodyControl(EntityInsentient var0) {
      this.a = var0;
   }

   public void a() {
      if (this.f()) {
         this.a.aT = this.a.dw();
         this.c();
         this.f = this.a.aV;
         this.e = 0;
      } else {
         if (this.e()) {
            if (Math.abs(this.a.aV - this.f) > 15.0F) {
               this.e = 0;
               this.f = this.a.aV;
               this.b();
            } else {
               ++this.e;
               if (this.e > 10) {
                  this.d();
               }
            }
         }
      }
   }

   private void b() {
      this.a.aT = MathHelper.c(this.a.aT, this.a.aV, (float)this.a.W());
   }

   private void c() {
      this.a.aV = MathHelper.c(this.a.aV, this.a.aT, (float)this.a.W());
   }

   private void d() {
      int var0 = this.e - 10;
      float var1 = MathHelper.a((float)var0 / 10.0F, 0.0F, 1.0F);
      float var2 = (float)this.a.W() * (1.0F - var1);
      this.a.aT = MathHelper.c(this.a.aT, this.a.aV, var2);
   }

   private boolean e() {
      return !(this.a.cN() instanceof EntityInsentient);
   }

   private boolean f() {
      double var0 = this.a.dl() - this.a.I;
      double var2 = this.a.dr() - this.a.K;
      return var0 * var0 + var2 * var2 > 2.5000003E-7F;
   }
}
