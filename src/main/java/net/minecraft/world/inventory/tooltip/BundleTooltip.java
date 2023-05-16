package net.minecraft.world.inventory.tooltip;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;

public class BundleTooltip implements TooltipComponent {
   private final NonNullList<ItemStack> a;
   private final int b;

   public BundleTooltip(NonNullList<ItemStack> var0, int var1) {
      this.a = var0;
      this.b = var1;
   }

   public NonNullList<ItemStack> a() {
      return this.a;
   }

   public int b() {
      return this.b;
   }
}
