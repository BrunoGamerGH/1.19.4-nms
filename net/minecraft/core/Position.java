package net.minecraft.core;

public class Position implements IPosition {
   protected final double a;
   protected final double b;
   protected final double c;

   public Position(double var0, double var2, double var4) {
      this.a = var0;
      this.b = var2;
      this.c = var4;
   }

   @Override
   public double a() {
      return this.a;
   }

   @Override
   public double b() {
      return this.b;
   }

   @Override
   public double c() {
      return this.c;
   }
}
