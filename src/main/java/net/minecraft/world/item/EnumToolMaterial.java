package net.minecraft.world.item;

import java.util.function.Supplier;
import net.minecraft.tags.TagsItem;
import net.minecraft.util.LazyInitVar;
import net.minecraft.world.item.crafting.RecipeItemStack;

public enum EnumToolMaterial implements ToolMaterial {
   a(0, 59, 2.0F, 0.0F, 15, () -> RecipeItemStack.a(TagsItem.b)),
   b(1, 131, 4.0F, 1.0F, 5, () -> RecipeItemStack.a(TagsItem.aw)),
   c(2, 250, 6.0F, 2.0F, 14, () -> RecipeItemStack.a(Items.nM)),
   d(3, 1561, 8.0F, 3.0F, 10, () -> RecipeItemStack.a(Items.nG)),
   e(0, 32, 12.0F, 0.0F, 22, () -> RecipeItemStack.a(Items.nQ)),
   f(4, 2031, 9.0F, 4.0F, 15, () -> RecipeItemStack.a(Items.nR));

   private final int g;
   private final int h;
   private final float i;
   private final float j;
   private final int k;
   private final LazyInitVar<RecipeItemStack> l;

   private EnumToolMaterial(int var2, int var3, float var4, float var5, int var6, Supplier var7) {
      this.g = var2;
      this.h = var3;
      this.i = var4;
      this.j = var5;
      this.k = var6;
      this.l = new LazyInitVar<>(var7);
   }

   @Override
   public int a() {
      return this.h;
   }

   @Override
   public float b() {
      return this.i;
   }

   @Override
   public float c() {
      return this.j;
   }

   @Override
   public int d() {
      return this.g;
   }

   @Override
   public int e() {
      return this.k;
   }

   @Override
   public RecipeItemStack f() {
      return this.l.a();
   }
}
