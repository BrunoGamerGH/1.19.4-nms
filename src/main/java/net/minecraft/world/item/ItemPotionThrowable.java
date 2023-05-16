package net.minecraft.world.item;

import net.minecraft.stats.StatisticList;
import net.minecraft.world.EnumHand;
import net.minecraft.world.InteractionResultWrapper;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.EntityPotion;
import net.minecraft.world.level.World;

public class ItemPotionThrowable extends ItemPotion {
   public ItemPotionThrowable(Item.Info var0) {
      super(var0);
   }

   @Override
   public InteractionResultWrapper<ItemStack> a(World var0, EntityHuman var1, EnumHand var2) {
      ItemStack var3 = var1.b(var2);
      if (!var0.B) {
         EntityPotion var4 = new EntityPotion(var0, var1);
         var4.a(var3);
         var4.a(var1, var1.dy(), var1.dw(), -20.0F, 0.5F, 1.0F);
         var0.b(var4);
      }

      var1.b(StatisticList.c.b(this));
      if (!var1.fK().d) {
         var3.h(1);
      }

      return InteractionResultWrapper.a(var3, var0.k_());
   }
}
