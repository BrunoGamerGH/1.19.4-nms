package net.minecraft.world;

public enum EnumInteractionResult {
   a,
   b,
   c,
   d,
   e;

   public boolean a() {
      return this == a || this == b || this == c;
   }

   public boolean b() {
      return this == a;
   }

   public boolean c() {
      return this == a || this == b;
   }

   public static EnumInteractionResult a(boolean var0) {
      return var0 ? a : b;
   }
}
