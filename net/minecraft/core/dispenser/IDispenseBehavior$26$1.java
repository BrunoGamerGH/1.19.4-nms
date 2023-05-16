package net.minecraft.core.dispenser;

import net.minecraft.SystemUtils;
import net.minecraft.core.IPosition;
import net.minecraft.world.entity.projectile.EntityPotion;
import net.minecraft.world.entity.projectile.IProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.World;

class IDispenseBehavior$26$1 extends DispenseBehaviorProjectile {
   IDispenseBehavior$26$1(Object var0) {
      this.c = var0;
   }

   @Override
   protected IProjectile a(World var0, IPosition var1, ItemStack var2) {
      return SystemUtils.a(new EntityPotion(var0, var1.a(), var1.b(), var1.c()), var1x -> var1x.a(var2));
   }

   @Override
   protected float a() {
      return super.a() * 0.5F;
   }

   @Override
   protected float b() {
      return super.b() * 1.25F;
   }
}
