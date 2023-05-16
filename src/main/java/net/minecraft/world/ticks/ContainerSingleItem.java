package net.minecraft.world.ticks;

import net.minecraft.world.IInventory;
import net.minecraft.world.item.ItemStack;

public interface ContainerSingleItem extends IInventory {
   @Override
   default int b() {
      return 1;
   }

   @Override
   default boolean aa_() {
      return this.at_().b();
   }

   @Override
   default void a() {
      this.j();
   }

   default ItemStack at_() {
      return this.a(0);
   }

   default ItemStack j() {
      return this.b(0);
   }

   default void b(ItemStack var0) {
      this.a(0, var0);
   }

   @Override
   default ItemStack b(int var0) {
      return this.a(var0, this.ab_());
   }
}
