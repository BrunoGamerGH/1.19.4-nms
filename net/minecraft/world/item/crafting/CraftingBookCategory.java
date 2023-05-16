package net.minecraft.world.item.crafting;

import net.minecraft.util.INamable;

public enum CraftingBookCategory implements INamable {
   a("building"),
   b("redstone"),
   c("equipment"),
   d("misc");

   public static final INamable.a<CraftingBookCategory> e = INamable.a(CraftingBookCategory::values);
   private final String f;

   private CraftingBookCategory(String var2) {
      this.f = var2;
   }

   @Override
   public String c() {
      return this.f;
   }
}
