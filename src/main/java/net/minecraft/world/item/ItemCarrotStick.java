package net.minecraft.world.item;

import net.minecraft.stats.StatisticList;
import net.minecraft.world.EnumHand;
import net.minecraft.world.InteractionResultWrapper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ISteerable;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;

public class ItemCarrotStick<T extends Entity & ISteerable> extends Item {
   private final EntityTypes<T> a;
   private final int b;

   public ItemCarrotStick(Item.Info var0, EntityTypes<T> var1, int var2) {
      super(var0);
      this.a = var1;
      this.b = var2;
   }

   @Override
   public InteractionResultWrapper<ItemStack> a(World var0, EntityHuman var1, EnumHand var2) {
      ItemStack var3 = var1.b(var2);
      if (var0.B) {
         return InteractionResultWrapper.c(var3);
      } else {
         Entity var4 = var1.cW();
         if (var1.bL() && var4 instanceof ISteerable var5 && var4.ae() == this.a && var5.a()) {
            var3.a(this.b, var1, var1x -> var1x.d(var2));
            if (var3.b()) {
               ItemStack var6 = new ItemStack(Items.qd);
               var6.c(var3.u());
               return InteractionResultWrapper.a(var6);
            }

            return InteractionResultWrapper.a(var3);
         }

         var1.b(StatisticList.c.b(this));
         return InteractionResultWrapper.c(var3);
      }
   }
}
