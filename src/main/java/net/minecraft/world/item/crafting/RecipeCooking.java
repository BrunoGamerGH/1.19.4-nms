package net.minecraft.world.item.crafting;

import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.IInventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.World;

public abstract class RecipeCooking implements IRecipe<IInventory> {
   protected final Recipes<?> a;
   protected final MinecraftKey b;
   private final CookingBookCategory h;
   protected final String c;
   protected final RecipeItemStack d;
   protected final ItemStack e;
   protected final float f;
   protected final int g;

   public RecipeCooking(Recipes<?> var0, MinecraftKey var1, String var2, CookingBookCategory var3, RecipeItemStack var4, ItemStack var5, float var6, int var7) {
      this.a = var0;
      this.h = var3;
      this.b = var1;
      this.c = var2;
      this.d = var4;
      this.e = var5;
      this.f = var6;
      this.g = var7;
   }

   @Override
   public boolean a(IInventory var0, World var1) {
      return this.d.a(var0.a(0));
   }

   @Override
   public ItemStack a(IInventory var0, IRegistryCustom var1) {
      return this.e.o();
   }

   @Override
   public boolean a(int var0, int var1) {
      return true;
   }

   @Override
   public NonNullList<RecipeItemStack> a() {
      NonNullList<RecipeItemStack> var0 = NonNullList.a();
      var0.add(this.d);
      return var0;
   }

   public float b() {
      return this.f;
   }

   @Override
   public ItemStack a(IRegistryCustom var0) {
      return this.e;
   }

   @Override
   public String c() {
      return this.c;
   }

   public int d() {
      return this.g;
   }

   @Override
   public MinecraftKey e() {
      return this.b;
   }

   @Override
   public Recipes<?> f() {
      return this.a;
   }

   public CookingBookCategory g() {
      return this.h;
   }
}
