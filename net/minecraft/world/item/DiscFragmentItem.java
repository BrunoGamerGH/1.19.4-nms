package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.EnumChatFormat;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatMutableComponent;
import net.minecraft.world.level.World;

public class DiscFragmentItem extends Item {
   public DiscFragmentItem(Item.Info var0) {
      super(var0);
   }

   @Override
   public void a(ItemStack var0, @Nullable World var1, List<IChatBaseComponent> var2, TooltipFlag var3) {
      var2.add(this.d().a(EnumChatFormat.h));
   }

   public IChatMutableComponent d() {
      return IChatBaseComponent.c(this.a() + ".desc");
   }
}
