package net.minecraft.core;

public enum EnumAxisCycle {
   a {
      @Override
      public int a(int var0, int var1, int var2, EnumDirection.EnumAxis var3) {
         return var3.a(var0, var1, var2);
      }

      @Override
      public double a(double var0, double var2, double var4, EnumDirection.EnumAxis var6) {
         return var6.a(var0, var2, var4);
      }

      @Override
      public EnumDirection.EnumAxis a(EnumDirection.EnumAxis var0) {
         return var0;
      }

      @Override
      public EnumAxisCycle a() {
         return this;
      }
   },
   b {
      @Override
      public int a(int var0, int var1, int var2, EnumDirection.EnumAxis var3) {
         return var3.a(var2, var0, var1);
      }

      @Override
      public double a(double var0, double var2, double var4, EnumDirection.EnumAxis var6) {
         return var6.a(var4, var0, var2);
      }

      @Override
      public EnumDirection.EnumAxis a(EnumDirection.EnumAxis var0) {
         return d[Math.floorMod(var0.ordinal() + 1, 3)];
      }

      @Override
      public EnumAxisCycle a() {
         return c;
      }
   },
   c {
      @Override
      public int a(int var0, int var1, int var2, EnumDirection.EnumAxis var3) {
         return var3.a(var1, var2, var0);
      }

      @Override
      public double a(double var0, double var2, double var4, EnumDirection.EnumAxis var6) {
         return var6.a(var2, var4, var0);
      }

      @Override
      public EnumDirection.EnumAxis a(EnumDirection.EnumAxis var0) {
         return d[Math.floorMod(var0.ordinal() - 1, 3)];
      }

      @Override
      public EnumAxisCycle a() {
         return b;
      }
   };

   public static final EnumDirection.EnumAxis[] d = EnumDirection.EnumAxis.values();
   public static final EnumAxisCycle[] e = values();

   public abstract int a(int var1, int var2, int var3, EnumDirection.EnumAxis var4);

   public abstract double a(double var1, double var3, double var5, EnumDirection.EnumAxis var7);

   public abstract EnumDirection.EnumAxis a(EnumDirection.EnumAxis var1);

   public abstract EnumAxisCycle a();

   public static EnumAxisCycle a(EnumDirection.EnumAxis var0, EnumDirection.EnumAxis var1) {
      return e[Math.floorMod(var1.ordinal() - var0.ordinal(), 3)];
   }
}
