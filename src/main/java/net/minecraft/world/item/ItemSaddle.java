package net.minecraft.world.item;

import net.minecraft.sounds.SoundCategory;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ISaddleable;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.gameevent.GameEvent;

public class ItemSaddle extends Item {
   public ItemSaddle(Item.Info var0) {
      super(var0);
   }

   @Override
   public EnumInteractionResult a(ItemStack var0, EntityHuman var1, EntityLiving var2, EnumHand var3) {
      if (var2 instanceof ISaddleable var4 && var2.bq() && !var4.i() && var4.g()) {
         if (!var1.H.B) {
            var4.a(SoundCategory.g);
            var2.H.a(var2, GameEvent.x, var2.de());
            var0.h(1);
         }

         return EnumInteractionResult.a(var1.H.B);
      }

      return EnumInteractionResult.d;
   }
}
