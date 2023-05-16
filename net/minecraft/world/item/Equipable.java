package net.minecraft.world.item;

import javax.annotation.Nullable;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.stats.StatisticList;
import net.minecraft.world.EnumHand;
import net.minecraft.world.InteractionResultWrapper;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;

public interface Equipable extends ItemVanishable {
   EnumItemSlot g();

   default SoundEffect ak_() {
      return SoundEffects.ae;
   }

   default InteractionResultWrapper<ItemStack> a(Item var0, World var1, EntityHuman var2, EnumHand var3) {
      ItemStack var4 = var2.b(var3);
      EnumItemSlot var5 = EntityInsentient.h(var4);
      ItemStack var6 = var2.c(var5);
      if (!EnchantmentManager.d(var6) && !ItemStack.b(var4, var6)) {
         var2.a(var5, var4.o());
         if (!var1.k_()) {
            var2.b(StatisticList.c.b(var0));
         }

         if (var6.b()) {
            var4.f(0);
         } else {
            var2.a(var3, var6.o());
         }

         return InteractionResultWrapper.a(var4, var1.k_());
      } else {
         return InteractionResultWrapper.d(var4);
      }
   }

   @Nullable
   static Equipable c_(ItemStack var0) {
      Item var2 = var0.c();
      if (var2 instanceof Equipable var1) {
         return var1;
      } else {
         Item var3 = var0.c();
         if (var3 instanceof ItemBlock var1) {
            Block var6 = var1.e();
            if (var6 instanceof Equipable var2) {
               return var2;
            }
         }

         return null;
      }
   }
}
