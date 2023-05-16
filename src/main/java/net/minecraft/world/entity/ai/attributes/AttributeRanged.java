package net.minecraft.world.entity.ai.attributes;

import net.minecraft.util.MathHelper;

public class AttributeRanged extends AttributeBase {
   private final double b;
   public double c;

   public AttributeRanged(String var0, double var1, double var3, double var5) {
      super(var0, var1);
      this.b = var3;
      this.c = var5;
      if (var3 > var5) {
         throw new IllegalArgumentException("Minimum value cannot be bigger than maximum value!");
      } else if (var1 < var3) {
         throw new IllegalArgumentException("Default value cannot be lower than minimum value!");
      } else if (var1 > var5) {
         throw new IllegalArgumentException("Default value cannot be bigger than maximum value!");
      }
   }

   public double d() {
      return this.b;
   }

   public double e() {
      return this.c;
   }

   @Override
   public double a(double var0) {
      return Double.isNaN(var0) ? this.b : MathHelper.a(var0, this.b, this.c);
   }
}
