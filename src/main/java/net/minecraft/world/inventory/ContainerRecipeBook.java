package net.minecraft.world.inventory;

import net.minecraft.recipebook.AutoRecipe;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.IInventory;
import net.minecraft.world.entity.player.AutoRecipeStackManager;
import net.minecraft.world.item.crafting.IRecipe;

public abstract class ContainerRecipeBook<C extends IInventory> extends Container {
   public ContainerRecipeBook(Containers<?> var0, int var1) {
      super(var0, var1);
   }

   public void a(boolean var0, IRecipe<?> var1, EntityPlayer var2) {
      new AutoRecipe<>(this).a(var2, var1, var0);
   }

   public abstract void a(AutoRecipeStackManager var1);

   @Override
   public abstract void l();

   public abstract boolean a(IRecipe<? super C> var1);

   public abstract int m();

   public abstract int n();

   public abstract int o();

   public abstract int p();

   public abstract RecipeBookType t();

   public abstract boolean e(int var1);
}
