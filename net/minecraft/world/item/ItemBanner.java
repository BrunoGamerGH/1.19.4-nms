package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.EnumChatFormat;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.Holder;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockBannerAbstract;
import net.minecraft.world.level.block.entity.EnumBannerPatternType;
import org.apache.commons.lang3.Validate;

public class ItemBanner extends ItemBlockWallable {
   private static final String c = "block.minecraft.banner.";

   public ItemBanner(Block var0, Block var1, Item.Info var2) {
      super(var0, var1, var2, EnumDirection.a);
      Validate.isInstanceOf(BlockBannerAbstract.class, var0);
      Validate.isInstanceOf(BlockBannerAbstract.class, var1);
   }

   public static void a(ItemStack var0, List<IChatBaseComponent> var1) {
      NBTTagCompound var2 = ItemBlock.a(var0);
      if (var2 != null && var2.e("Patterns")) {
         NBTTagList var3 = var2.c("Patterns", 10);

         for(int var4 = 0; var4 < var3.size() && var4 < 6; ++var4) {
            NBTTagCompound var5 = var3.a(var4);
            EnumColor var6 = EnumColor.a(var5.h("Color"));
            Holder<EnumBannerPatternType> var7 = EnumBannerPatternType.a(var5.l("Pattern"));
            if (var7 != null) {
               var7.e()
                  .map(var0x -> var0x.a().e())
                  .ifPresent(var2x -> var1.add(IChatBaseComponent.c("block.minecraft.banner." + var2x + "." + var6.b()).a(EnumChatFormat.h)));
            }
         }
      }
   }

   public EnumColor b() {
      return ((BlockBannerAbstract)this.e()).b();
   }

   @Override
   public void a(ItemStack var0, @Nullable World var1, List<IChatBaseComponent> var2, TooltipFlag var3) {
      a(var0, var2);
   }
}
