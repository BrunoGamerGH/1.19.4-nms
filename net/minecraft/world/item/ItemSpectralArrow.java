package net.minecraft.world.item;

import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.projectile.EntityArrow;
import net.minecraft.world.entity.projectile.EntitySpectralArrow;
import net.minecraft.world.level.World;

public class ItemSpectralArrow extends ItemArrow {
   public ItemSpectralArrow(Item.Info var0) {
      super(var0);
   }

   @Override
   public EntityArrow a(World var0, ItemStack var1, EntityLiving var2) {
      return new EntitySpectralArrow(var0, var2);
   }
}
