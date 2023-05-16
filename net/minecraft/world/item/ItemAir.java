package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;

public class ItemAir extends Item {
   private final Block a;

   public ItemAir(Block var0, Item.Info var1) {
      super(var1);
      this.a = var0;
   }

   @Override
   public String a() {
      return this.a.h();
   }

   @Override
   public void a(ItemStack var0, @Nullable World var1, List<IChatBaseComponent> var2, TooltipFlag var3) {
      super.a(var0, var1, var2, var3);
      this.a.a(var0, var1, var2, var3);
   }
}
