package net.minecraft.world.entity.ai.attributes;

public class AttributeBase {
   public static final int a = 64;
   private final double b;
   private boolean c;
   private final String d;

   protected AttributeBase(String var0, double var1) {
      this.b = var1;
      this.d = var0;
   }

   public double a() {
      return this.b;
   }

   public boolean b() {
      return this.c;
   }

   public AttributeBase a(boolean var0) {
      this.c = var0;
      return this;
   }

   public double a(double var0) {
      return var0;
   }

   public String c() {
      return this.d;
   }
}
