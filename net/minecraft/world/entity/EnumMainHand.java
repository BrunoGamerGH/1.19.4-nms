package net.minecraft.world.entity;

import net.minecraft.util.OptionEnum;

public enum EnumMainHand implements OptionEnum {
   a(0, "options.mainHand.left"),
   b(1, "options.mainHand.right");

   private final int c;
   private final String d;

   private EnumMainHand(int var2, String var3) {
      this.c = var2;
      this.d = var3;
   }

   public EnumMainHand d() {
      return this == a ? b : a;
   }

   @Override
   public int a() {
      return this.c;
   }

   @Override
   public String b() {
      return this.d;
   }
}
