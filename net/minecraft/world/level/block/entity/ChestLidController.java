package net.minecraft.world.level.block.entity;

import net.minecraft.util.MathHelper;

public class ChestLidController {
   private boolean a;
   private float b;
   private float c;

   public void a() {
      this.c = this.b;
      float var0 = 0.1F;
      if (!this.a && this.b > 0.0F) {
         this.b = Math.max(this.b - 0.1F, 0.0F);
      } else if (this.a && this.b < 1.0F) {
         this.b = Math.min(this.b + 0.1F, 1.0F);
      }
   }

   public float a(float var0) {
      return MathHelper.i(var0, this.c, this.b);
   }

   public void a(boolean var0) {
      this.a = var0;
   }
}
