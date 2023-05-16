package net.minecraft.world;

import javax.annotation.Nullable;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.item.ItemStack;

public interface IWorldInventory extends IInventory {
   int[] a(EnumDirection var1);

   boolean a(int var1, ItemStack var2, @Nullable EnumDirection var3);

   boolean b(int var1, ItemStack var2, EnumDirection var3);
}
