package net.minecraft.world.item;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.decoration.EntityHanging;
import net.minecraft.world.entity.player.EntityHuman;

public class ItemItemFrame extends ItemHanging {
   public ItemItemFrame(EntityTypes<? extends EntityHanging> var0, Item.Info var1) {
      super(var0, var1);
   }

   @Override
   protected boolean a(EntityHuman var0, EnumDirection var1, ItemStack var2, BlockPosition var3) {
      return !var0.H.u(var3) && var0.a(var3, var1, var2);
   }
}
