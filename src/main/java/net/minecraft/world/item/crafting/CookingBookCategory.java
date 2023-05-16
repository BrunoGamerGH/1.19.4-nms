package net.minecraft.world.item.crafting;

import net.minecraft.util.INamable;

public enum CookingBookCategory implements INamable {
   a("food"),
   b("blocks"),
   c("misc");

   public static final INamable.a<CookingBookCategory> d = INamable.a(CookingBookCategory::values);
   private final String e;

   private CookingBookCategory(String var2) {
      this.e = var2;
   }

   @Override
   public String c() {
      return this.e;
   }
}
