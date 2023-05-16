package net.minecraft.world.entity.ai.control;

import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;

public class SmoothSwimmingMoveControl extends ControllerMove {
   private static final float l = 10.0F;
   private static final float m = 60.0F;
   private final int n;
   private final int o;
   private final float p;
   private final float q;
   private final boolean r;

   public SmoothSwimmingMoveControl(EntityInsentient var0, int var1, int var2, float var3, float var4, boolean var5) {
      super(var0);
      this.n = var1;
      this.o = var2;
      this.p = var3;
      this.q = var4;
      this.r = var5;
   }

   @Override
   public void a() {
      if (this.r && this.d.aT()) {
         this.d.f(this.d.dj().b(0.0, 0.005, 0.0));
      }

      if (this.k == ControllerMove.Operation.b && !this.d.G().l()) {
         double var0 = this.e - this.d.dl();
         double var2 = this.f - this.d.dn();
         double var4 = this.g - this.d.dr();
         double var6 = var0 * var0 + var2 * var2 + var4 * var4;
         if (var6 < 2.5000003E-7F) {
            this.d.y(0.0F);
         } else {
            float var8 = (float)(MathHelper.d(var4, var0) * 180.0F / (float)Math.PI) - 90.0F;
            this.d.f(this.a(this.d.dw(), var8, (float)this.o));
            this.d.aT = this.d.dw();
            this.d.aV = this.d.dw();
            float var9 = (float)(this.h * this.d.b(GenericAttributes.d));
            if (this.d.aT()) {
               this.d.h(var9 * this.p);
               double var10 = Math.sqrt(var0 * var0 + var4 * var4);
               if (Math.abs(var2) > 1.0E-5F || Math.abs(var10) > 1.0E-5F) {
                  float var12 = -((float)(MathHelper.d(var2, var10) * 180.0F / (float)Math.PI));
                  var12 = MathHelper.a(MathHelper.g(var12), (float)(-this.n), (float)this.n);
                  this.d.e(this.a(this.d.dy(), var12, 5.0F));
               }

               float var12 = MathHelper.b(this.d.dy() * (float) (Math.PI / 180.0));
               float var13 = MathHelper.a(this.d.dy() * (float) (Math.PI / 180.0));
               this.d.bl = var12 * var9;
               this.d.bk = -var13 * var9;
            } else {
               float var10 = Math.abs(MathHelper.g(this.d.dw() - var8));
               float var11 = a(var10);
               this.d.h(var9 * this.q * var11);
            }
         }
      } else {
         this.d.h(0.0F);
         this.d.A(0.0F);
         this.d.z(0.0F);
         this.d.y(0.0F);
      }
   }

   private static float a(float var0) {
      return 1.0F - MathHelper.a((var0 - 10.0F) / 50.0F, 0.0F, 1.0F);
   }
}
