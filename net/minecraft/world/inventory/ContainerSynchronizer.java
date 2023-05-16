package net.minecraft.world.inventory;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;

public interface ContainerSynchronizer {
   void a(Container var1, NonNullList<ItemStack> var2, ItemStack var3, int[] var4);

   void a(Container var1, int var2, ItemStack var3);

   void a(Container var1, ItemStack var2);

   void a(Container var1, int var2, int var3);
}
