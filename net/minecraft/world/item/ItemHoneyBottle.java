package net.minecraft.world.item;

import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.stats.StatisticList;
import net.minecraft.world.EnumHand;
import net.minecraft.world.InteractionResultWrapper;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;

public class ItemHoneyBottle extends Item {
   private static final int a = 40;

   public ItemHoneyBottle(Item.Info var0) {
      super(var0);
   }

   @Override
   public ItemStack a(ItemStack var0, World var1, EntityLiving var2) {
      super.a(var0, var1, var2);
      if (var2 instanceof EntityPlayer var3) {
         CriterionTriggers.z.a(var3, var0);
         var3.b(StatisticList.c.b(this));
      }

      if (!var1.B) {
         var2.d(MobEffects.s);
      }

      if (var0.b()) {
         return new ItemStack(Items.rs);
      } else {
         if (var2 instanceof EntityHuman && !((EntityHuman)var2).fK().d) {
            ItemStack var3 = new ItemStack(Items.rs);
            EntityHuman var4 = (EntityHuman)var2;
            if (!var4.fJ().e(var3)) {
               var4.a(var3, false);
            }
         }

         return var0;
      }
   }

   @Override
   public int b(ItemStack var0) {
      return 40;
   }

   @Override
   public EnumAnimation c(ItemStack var0) {
      return EnumAnimation.c;
   }

   @Override
   public SoundEffect ae_() {
      return SoundEffects.kS;
   }

   @Override
   public SoundEffect af_() {
      return SoundEffects.kS;
   }

   @Override
   public InteractionResultWrapper<ItemStack> a(World var0, EntityHuman var1, EnumHand var2) {
      return ItemLiquidUtil.a(var0, var1, var2);
   }
}
