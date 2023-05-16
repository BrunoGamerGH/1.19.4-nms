package net.minecraft.world.item;

import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.projectile.EntityArrow;
import net.minecraft.world.entity.projectile.EntityTippedArrow;
import net.minecraft.world.level.World;

public class ItemArrow extends Item {
   public ItemArrow(Item.Info var0) {
      super(var0);
   }

   public EntityArrow a(World var0, ItemStack var1, EntityLiving var2) {
      EntityTippedArrow var3 = new EntityTippedArrow(var0, var2);
      var3.a(var1);
      return var3;
   }
}
