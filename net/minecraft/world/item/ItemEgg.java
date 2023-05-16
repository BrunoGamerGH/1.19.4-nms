package net.minecraft.world.item;

import net.minecraft.server.level.EntityPlayer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.stats.StatisticList;
import net.minecraft.world.EnumHand;
import net.minecraft.world.InteractionResultWrapper;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.EntityEgg;
import net.minecraft.world.level.World;

public class ItemEgg extends Item {
   public ItemEgg(Item.Info item_info) {
      super(item_info);
   }

   @Override
   public InteractionResultWrapper<ItemStack> a(World world, EntityHuman entityhuman, EnumHand enumhand) {
      ItemStack itemstack = entityhuman.b(enumhand);
      if (!world.B) {
         EntityEgg entityegg = new EntityEgg(world, entityhuman);
         entityegg.a(itemstack);
         entityegg.a(entityhuman, entityhuman.dy(), entityhuman.dw(), 0.0F, 1.5F, 1.0F);
         if (!world.b(entityegg)) {
            if (entityhuman instanceof EntityPlayer) {
               ((EntityPlayer)entityhuman).getBukkitEntity().updateInventory();
            }

            return InteractionResultWrapper.d(itemstack);
         }
      }

      world.a(null, entityhuman.dl(), entityhuman.dn(), entityhuman.dr(), SoundEffects.gC, SoundCategory.h, 0.5F, 0.4F / (world.r_().i() * 0.4F + 0.8F));
      entityhuman.b(StatisticList.c.b(this));
      if (!entityhuman.fK().d) {
         itemstack.h(1);
      }

      return InteractionResultWrapper.a(itemstack, world.k_());
   }
}
