package net.minecraft.world.item;

import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;

public class ItemSoup extends Item {
   public ItemSoup(Item.Info var0) {
      super(var0);
   }

   @Override
   public ItemStack a(ItemStack var0, World var1, EntityLiving var2) {
      ItemStack var3 = super.a(var0, var1, var2);
      return var2 instanceof EntityHuman && ((EntityHuman)var2).fK().d ? var3 : new ItemStack(Items.oy);
   }
}
