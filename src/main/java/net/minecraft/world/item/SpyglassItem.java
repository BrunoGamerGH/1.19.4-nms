package net.minecraft.world.item;

import net.minecraft.sounds.SoundEffects;
import net.minecraft.stats.StatisticList;
import net.minecraft.world.EnumHand;
import net.minecraft.world.InteractionResultWrapper;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;

public class SpyglassItem extends Item {
   public static final int a = 1200;
   public static final float b = 0.1F;

   public SpyglassItem(Item.Info var0) {
      super(var0);
   }

   @Override
   public int b(ItemStack var0) {
      return 1200;
   }

   @Override
   public EnumAnimation c(ItemStack var0) {
      return EnumAnimation.h;
   }

   @Override
   public InteractionResultWrapper<ItemStack> a(World var0, EntityHuman var1, EnumHand var2) {
      var1.a(SoundEffects.wL, 1.0F, 1.0F);
      var1.b(StatisticList.c.b(this));
      return ItemLiquidUtil.a(var0, var1, var2);
   }

   @Override
   public ItemStack a(ItemStack var0, World var1, EntityLiving var2) {
      this.a(var2);
      return var0;
   }

   @Override
   public void a(ItemStack var0, World var1, EntityLiving var2, int var3) {
      this.a(var2);
   }

   private void a(EntityLiving var0) {
      var0.a(SoundEffects.wM, 1.0F, 1.0F);
   }
}
