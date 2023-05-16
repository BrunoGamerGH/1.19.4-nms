package net.minecraft.world.item;

import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.player.EntityHuman;

public class ItemNameTag extends Item {
   public ItemNameTag(Item.Info var0) {
      super(var0);
   }

   @Override
   public EnumInteractionResult a(ItemStack var0, EntityHuman var1, EntityLiving var2, EnumHand var3) {
      if (var0.z() && !(var2 instanceof EntityHuman)) {
         if (!var1.H.B && var2.bq()) {
            var2.b(var0.x());
            if (var2 instanceof EntityInsentient) {
               ((EntityInsentient)var2).fz();
            }

            var0.h(1);
         }

         return EnumInteractionResult.a(var1.H.B);
      } else {
         return EnumInteractionResult.d;
      }
   }
}
