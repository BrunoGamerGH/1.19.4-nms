package net.minecraft.world.item;

import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.EnumHand;
import net.minecraft.world.InteractionResultWrapper;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;

public class ItemSplashPotion extends ItemPotionThrowable {
   public ItemSplashPotion(Item.Info var0) {
      super(var0);
   }

   @Override
   public InteractionResultWrapper<ItemStack> a(World var0, EntityHuman var1, EnumHand var2) {
      var0.a(null, var1.dl(), var1.dn(), var1.dr(), SoundEffects.wK, SoundCategory.h, 0.5F, 0.4F / (var0.r_().i() * 0.4F + 0.8F));
      return super.a(var0, var1, var2);
   }
}
