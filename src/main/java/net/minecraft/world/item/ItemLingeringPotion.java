package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.EnumHand;
import net.minecraft.world.InteractionResultWrapper;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.alchemy.PotionUtil;
import net.minecraft.world.level.World;

public class ItemLingeringPotion extends ItemPotionThrowable {
   public ItemLingeringPotion(Item.Info var0) {
      super(var0);
   }

   @Override
   public void a(ItemStack var0, @Nullable World var1, List<IChatBaseComponent> var2, TooltipFlag var3) {
      PotionUtil.a(var0, var2, 0.25F);
   }

   @Override
   public InteractionResultWrapper<ItemStack> a(World var0, EntityHuman var1, EnumHand var2) {
      var0.a(null, var1.dl(), var1.dn(), var1.dr(), SoundEffects.mm, SoundCategory.g, 0.5F, 0.4F / (var0.r_().i() * 0.4F + 0.8F));
      return super.a(var0, var1, var2);
   }
}
