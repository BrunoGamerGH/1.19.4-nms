package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.EnumChatFormat;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatMutableComponent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.EnumBannerPatternType;

public class ItemBannerPattern extends Item {
   private final TagKey<EnumBannerPatternType> a;

   public ItemBannerPattern(TagKey<EnumBannerPatternType> var0, Item.Info var1) {
      super(var1);
      this.a = var0;
   }

   public TagKey<EnumBannerPatternType> b() {
      return this.a;
   }

   @Override
   public void a(ItemStack var0, @Nullable World var1, List<IChatBaseComponent> var2, TooltipFlag var3) {
      var2.add(this.d().a(EnumChatFormat.h));
   }

   public IChatMutableComponent d() {
      return IChatBaseComponent.c(this.a() + ".desc");
   }
}
