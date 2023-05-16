package net.minecraft.world.item;

import java.util.stream.Stream;
import net.minecraft.world.EnumHand;
import net.minecraft.world.InteractionResultWrapper;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;

public class ItemLiquidUtil {
   public static InteractionResultWrapper<ItemStack> a(World var0, EntityHuman var1, EnumHand var2) {
      var1.c(var2);
      return InteractionResultWrapper.b(var1.b(var2));
   }

   public static ItemStack a(ItemStack var0, EntityHuman var1, ItemStack var2, boolean var3) {
      boolean var4 = var1.fK().d;
      if (var3 && var4) {
         if (!var1.fJ().h(var2)) {
            var1.fJ().e(var2);
         }

         return var0;
      } else {
         if (!var4) {
            var0.h(1);
         }

         if (var0.b()) {
            return var2;
         } else {
            if (!var1.fJ().e(var2)) {
               var1.a(var2, false);
            }

            return var0;
         }
      }
   }

   public static ItemStack a(ItemStack var0, EntityHuman var1, ItemStack var2) {
      return a(var0, var1, var2, true);
   }

   public static void a(EntityItem var0, Stream<ItemStack> var1) {
      World var2 = var0.H;
      if (!var2.B) {
         var1.forEach(var2x -> var2.b(new EntityItem(var2, var0.dl(), var0.dn(), var0.dr(), var2x)));
      }
   }
}
