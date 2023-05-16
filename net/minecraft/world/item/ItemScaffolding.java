package net.minecraft.world.item;

import javax.annotation.Nullable;
import net.minecraft.EnumChatFormat;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockScaffolding;
import net.minecraft.world.level.block.state.IBlockData;

public class ItemScaffolding extends ItemBlock {
   public ItemScaffolding(Block var0, Item.Info var1) {
      super(var0, var1);
   }

   @Nullable
   @Override
   public BlockActionContext b(BlockActionContext var0) {
      BlockPosition var1 = var0.a();
      World var2 = var0.q();
      IBlockData var3 = var2.a_(var1);
      Block var4 = this.e();
      if (!var3.a(var4)) {
         return BlockScaffolding.a(var2, var1) == 7 ? null : var0;
      } else {
         EnumDirection var5;
         if (var0.h()) {
            var5 = var0.m() ? var0.k().g() : var0.k();
         } else {
            var5 = var0.k() == EnumDirection.b ? var0.g() : EnumDirection.b;
         }

         int var6 = 0;
         BlockPosition.MutableBlockPosition var7 = var1.j().c(var5);

         while(var6 < 7) {
            if (!var2.B && !var2.j(var7)) {
               EntityHuman var8 = var0.o();
               int var9 = var2.ai();
               if (var8 instanceof EntityPlayer && var7.v() >= var9) {
                  ((EntityPlayer)var8).b(IChatBaseComponent.a("build.tooHigh", var9 - 1).a(EnumChatFormat.m), true);
               }
               break;
            }

            var3 = var2.a_(var7);
            if (!var3.a(this.e())) {
               if (var3.a(var0)) {
                  return BlockActionContext.a(var0, var7, var5);
               }
               break;
            }

            var7.c(var5);
            if (var5.o().d()) {
               ++var6;
            }
         }

         return null;
      }
   }

   @Override
   protected boolean d() {
      return false;
   }
}
