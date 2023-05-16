package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.item.alchemy.PotionUtil;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.World;

public class ItemTippedArrow extends ItemArrow {
   public ItemTippedArrow(Item.Info var0) {
      super(var0);
   }

   @Override
   public ItemStack ad_() {
      return PotionUtil.a(super.ad_(), Potions.E);
   }

   @Override
   public void a(ItemStack var0, @Nullable World var1, List<IChatBaseComponent> var2, TooltipFlag var3) {
      PotionUtil.a(var0, var2, 0.125F);
   }

   @Override
   public String j(ItemStack var0) {
      return PotionUtil.d(var0).b(this.a() + ".effect.");
   }
}
