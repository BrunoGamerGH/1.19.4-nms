package net.minecraft.world.item;

import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.stats.StatisticList;
import net.minecraft.world.EnumHand;
import net.minecraft.world.InteractionResultWrapper;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;

public class ItemMilkBucket extends Item {
   private static final int a = 32;

   public ItemMilkBucket(Item.Info item_info) {
      super(item_info);
   }

   @Override
   public ItemStack a(ItemStack itemstack, World world, EntityLiving entityliving) {
      if (entityliving instanceof EntityPlayer entityplayer) {
         CriterionTriggers.z.a(entityplayer, itemstack);
         entityplayer.b(StatisticList.c.b(this));
      }

      if (entityliving instanceof EntityHuman && !((EntityHuman)entityliving).fK().d) {
         itemstack.h(1);
      }

      if (!world.B) {
         entityliving.removeAllEffects(Cause.MILK);
      }

      return itemstack.b() ? new ItemStack(Items.pG) : itemstack;
   }

   @Override
   public int b(ItemStack itemstack) {
      return 32;
   }

   @Override
   public EnumAnimation c(ItemStack itemstack) {
      return EnumAnimation.c;
   }

   @Override
   public InteractionResultWrapper<ItemStack> a(World world, EntityHuman entityhuman, EnumHand enumhand) {
      return ItemLiquidUtil.a(world, entityhuman, enumhand);
   }
}
