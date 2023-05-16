package net.minecraft.world.item;

import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.stats.StatisticList;
import net.minecraft.world.EnumHand;
import net.minecraft.world.InteractionResultWrapper;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.EntityThrownExpBottle;
import net.minecraft.world.level.World;

public class ItemExpBottle extends Item {
   public ItemExpBottle(Item.Info var0) {
      super(var0);
   }

   @Override
   public boolean i(ItemStack var0) {
      return true;
   }

   @Override
   public InteractionResultWrapper<ItemStack> a(World var0, EntityHuman var1, EnumHand var2) {
      ItemStack var3 = var1.b(var2);
      var0.a(null, var1.dl(), var1.dn(), var1.dr(), SoundEffects.hv, SoundCategory.g, 0.5F, 0.4F / (var0.r_().i() * 0.4F + 0.8F));
      if (!var0.B) {
         EntityThrownExpBottle var4 = new EntityThrownExpBottle(var0, var1);
         var4.a(var3);
         var4.a(var1, var1.dy(), var1.dw(), -20.0F, 0.7F, 1.0F);
         var0.b(var4);
      }

      var1.b(StatisticList.c.b(this));
      if (!var1.fK().d) {
         var3.h(1);
      }

      return InteractionResultWrapper.a(var3, var0.k_());
   }
}
