package net.minecraft.world.entity.ai.control;

import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;

public class ControllerMoveFlying extends ControllerMove {
   private final int l;
   private final boolean m;

   public ControllerMoveFlying(EntityInsentient var0, int var1, boolean var2) {
      super(var0);
      this.l = var1;
      this.m = var2;
   }

   @Override
   public void a() {
      if (this.k == ControllerMove.Operation.b) {
         this.k = ControllerMove.Operation.a;
         this.d.e(true);
         double var0 = this.e - this.d.dl();
         double var2 = this.f - this.d.dn();
         double var4 = this.g - this.d.dr();
         double var6 = var0 * var0 + var2 * var2 + var4 * var4;
         if (var6 < 2.5000003E-7F) {
            this.d.z(0.0F);
            this.d.y(0.0F);
            return;
         }

         float var8 = (float)(MathHelper.d(var4, var0) * 180.0F / (float)Math.PI) - 90.0F;
         this.d.f(this.a(this.d.dw(), var8, 90.0F));
         float var9;
         if (this.d.ax()) {
            var9 = (float)(this.h * this.d.b(GenericAttributes.d));
         } else {
            var9 = (float)(this.h * this.d.b(GenericAttributes.e));
         }

         this.d.h(var9);
         double var10 = Math.sqrt(var0 * var0 + var4 * var4);
         if (Math.abs(var2) > 1.0E-5F || Math.abs(var10) > 1.0E-5F) {
            float var12 = (float)(-(MathHelper.d(var2, var10) * 180.0F / (float)Math.PI));
            this.d.e(this.a(this.d.dy(), var12, (float)this.l));
            this.d.z(var2 > 0.0 ? var9 : -var9);
         }
      } else {
         if (!this.m) {
            this.d.e(false);
         }

         this.d.z(0.0F);
         this.d.y(0.0F);
      }
   }
}
