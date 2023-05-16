package net.minecraft.world.item;

import java.util.List;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.stats.StatisticList;
import net.minecraft.tags.TagsFluid;
import net.minecraft.world.EnumHand;
import net.minecraft.world.InteractionResultWrapper;
import net.minecraft.world.entity.EntityAreaEffectCloud;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderDragon;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.alchemy.PotionUtil;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.RayTrace;
import net.minecraft.world.level.World;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.MovingObjectPosition;
import net.minecraft.world.phys.MovingObjectPositionBlock;

public class ItemGlassBottle extends Item {
   public ItemGlassBottle(Item.Info var0) {
      super(var0);
   }

   @Override
   public InteractionResultWrapper<ItemStack> a(World var0, EntityHuman var1, EnumHand var2) {
      List<EntityAreaEffectCloud> var3 = var0.a(
         EntityAreaEffectCloud.class, var1.cD().g(2.0), var0x -> var0x != null && var0x.bq() && var0x.s() instanceof EntityEnderDragon
      );
      ItemStack var4 = var1.b(var2);
      if (!var3.isEmpty()) {
         EntityAreaEffectCloud var5 = var3.get(0);
         var5.a(var5.h() - 0.5F);
         var0.a(null, var1.dl(), var1.dn(), var1.dr(), SoundEffects.ci, SoundCategory.g, 1.0F, 1.0F);
         var0.a(var1, GameEvent.A, var1.de());
         if (var1 instanceof EntityPlayer var6) {
            CriterionTriggers.Q.a(var6, var4, var5);
         }

         return InteractionResultWrapper.a(this.a(var4, var1, new ItemStack(Items.uo)), var0.k_());
      } else {
         MovingObjectPosition var5 = a(var0, var1, RayTrace.FluidCollisionOption.b);
         if (var5.c() == MovingObjectPosition.EnumMovingObjectType.a) {
            return InteractionResultWrapper.c(var4);
         } else {
            if (var5.c() == MovingObjectPosition.EnumMovingObjectType.b) {
               BlockPosition var6 = ((MovingObjectPositionBlock)var5).a();
               if (!var0.a(var1, var6)) {
                  return InteractionResultWrapper.c(var4);
               }

               if (var0.b_(var6).a(TagsFluid.a)) {
                  var0.a(var1, var1.dl(), var1.dn(), var1.dr(), SoundEffects.ch, SoundCategory.g, 1.0F, 1.0F);
                  var0.a(var1, GameEvent.A, var6);
                  return InteractionResultWrapper.a(this.a(var4, var1, PotionUtil.a(new ItemStack(Items.rr), Potions.c)), var0.k_());
               }
            }

            return InteractionResultWrapper.c(var4);
         }
      }
   }

   protected ItemStack a(ItemStack var0, EntityHuman var1, ItemStack var2) {
      var1.b(StatisticList.c.b(this));
      return ItemLiquidUtil.a(var0, var1, var2);
   }
}
