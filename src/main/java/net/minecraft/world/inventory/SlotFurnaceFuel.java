package net.minecraft.world.inventory;

import net.minecraft.world.IInventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SlotFurnaceFuel extends Slot {
   private final ContainerFurnace a;

   public SlotFurnaceFuel(ContainerFurnace var0, IInventory var1, int var2, int var3, int var4) {
      super(var1, var2, var3, var4);
      this.a = var0;
   }

   @Override
   public boolean a(ItemStack var0) {
      return this.a.d(var0) || c(var0);
   }

   @Override
   public int a_(ItemStack var0) {
      return c(var0) ? 1 : super.a_(var0);
   }

   public static boolean c(ItemStack var0) {
      return var0.a(Items.pG);
   }
}
