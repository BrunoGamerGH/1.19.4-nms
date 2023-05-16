package net.minecraft.world.inventory;

import net.minecraft.core.NonNullList;
import net.minecraft.world.IInventory;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipes;

public class SlotResult extends Slot {
   private final InventoryCrafting a;
   private final EntityHuman b;
   private int c;

   public SlotResult(EntityHuman var0, InventoryCrafting var1, IInventory var2, int var3, int var4, int var5) {
      super(var2, var3, var4, var5);
      this.b = var0;
      this.a = var1;
   }

   @Override
   public boolean a(ItemStack var0) {
      return false;
   }

   @Override
   public ItemStack a(int var0) {
      if (this.f()) {
         this.c += Math.min(var0, this.e().K());
      }

      return super.a(var0);
   }

   @Override
   protected void a(ItemStack var0, int var1) {
      this.c += var1;
      this.b_(var0);
   }

   @Override
   protected void b(int var0) {
      this.c += var0;
   }

   @Override
   protected void b_(ItemStack var0) {
      if (this.c > 0) {
         var0.a(this.b.H, this.b, this.c);
      }

      if (this.d instanceof RecipeHolder) {
         ((RecipeHolder)this.d).b(this.b);
      }

      this.c = 0;
   }

   @Override
   public void a(EntityHuman var0, ItemStack var1) {
      this.b_(var1);
      NonNullList<ItemStack> var2 = var0.H.q().c(Recipes.a, this.a, var0.H);

      for(int var3 = 0; var3 < var2.size(); ++var3) {
         ItemStack var4 = this.a.a(var3);
         ItemStack var5 = var2.get(var3);
         if (!var4.b()) {
            this.a.a(var3, 1);
            var4 = this.a.a(var3);
         }

         if (!var5.b()) {
            if (var4.b()) {
               this.a.a(var3, var5);
            } else if (ItemStack.c(var4, var5) && ItemStack.a(var4, var5)) {
               var5.g(var4.K());
               this.a.a(var3, var5);
            } else if (!this.b.fJ().e(var5)) {
               this.b.a(var5, false);
            }
         }
      }
   }
}
