package net.minecraft.world.entity;

public enum EnumItemSlot {
   a(EnumItemSlot.Function.a, 0, 0, "mainhand"),
   b(EnumItemSlot.Function.a, 1, 5, "offhand"),
   c(EnumItemSlot.Function.b, 0, 1, "feet"),
   d(EnumItemSlot.Function.b, 1, 2, "legs"),
   e(EnumItemSlot.Function.b, 2, 3, "chest"),
   f(EnumItemSlot.Function.b, 3, 4, "head");

   private final EnumItemSlot.Function g;
   private final int h;
   private final int i;
   private final String j;

   private EnumItemSlot(EnumItemSlot.Function var2, int var3, int var4, String var5) {
      this.g = var2;
      this.h = var3;
      this.i = var4;
      this.j = var5;
   }

   public EnumItemSlot.Function a() {
      return this.g;
   }

   public int b() {
      return this.h;
   }

   public int a(int var0) {
      return var0 + this.h;
   }

   public int c() {
      return this.i;
   }

   public String d() {
      return this.j;
   }

   public boolean e() {
      return this.g == EnumItemSlot.Function.b;
   }

   public static EnumItemSlot a(String var0) {
      for(EnumItemSlot var4 : values()) {
         if (var4.d().equals(var0)) {
            return var4;
         }
      }

      throw new IllegalArgumentException("Invalid slot '" + var0 + "'");
   }

   public static EnumItemSlot a(EnumItemSlot.Function var0, int var1) {
      for(EnumItemSlot var5 : values()) {
         if (var5.a() == var0 && var5.b() == var1) {
            return var5;
         }
      }

      throw new IllegalArgumentException("Invalid slot '" + var0 + "': " + var1);
   }

   public static enum Function {
      a,
      b;
   }
}
