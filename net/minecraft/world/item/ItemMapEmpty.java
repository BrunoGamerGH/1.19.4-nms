package net.minecraft.world.item;

import net.minecraft.sounds.SoundEffects;
import net.minecraft.stats.StatisticList;
import net.minecraft.world.EnumHand;
import net.minecraft.world.InteractionResultWrapper;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;

public class ItemMapEmpty extends ItemWorldMapBase {
   public ItemMapEmpty(Item.Info var0) {
      super(var0);
   }

   @Override
   public InteractionResultWrapper<ItemStack> a(World var0, EntityHuman var1, EnumHand var2) {
      ItemStack var3 = var1.b(var2);
      if (var0.B) {
         return InteractionResultWrapper.a(var3);
      } else {
         if (!var1.fK().d) {
            var3.h(1);
         }

         var1.b(StatisticList.c.b(this));
         var1.H.a(null, var1, SoundEffects.xY, var1.cX(), 1.0F, 1.0F);
         ItemStack var4 = ItemWorldMap.a(var0, var1.dk(), var1.dq(), (byte)0, true, false);
         if (var3.b()) {
            return InteractionResultWrapper.b(var4);
         } else {
            if (!var1.fJ().e(var4.o())) {
               var1.a(var4, false);
            }

            return InteractionResultWrapper.b(var3);
         }
      }
   }
}
