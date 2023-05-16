package net.minecraft.world.inventory;

import net.minecraft.world.IInventory;
import net.minecraft.world.item.ItemStack;

public class SlotShulkerBox extends Slot {
   public SlotShulkerBox(IInventory var0, int var1, int var2, int var3) {
      super(var0, var1, var2, var3);
   }

   @Override
   public boolean a(ItemStack var0) {
      return var0.c().ag_();
   }
}
