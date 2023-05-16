package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.Particles;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.stats.StatisticList;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.InteractionResultWrapper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.alchemy.PotionUtil;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.context.ItemActionContext;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;

public class ItemPotion extends Item {
   private static final int a = 32;

   public ItemPotion(Item.Info item_info) {
      super(item_info);
   }

   @Override
   public ItemStack ad_() {
      return PotionUtil.a(super.ad_(), Potions.c);
   }

   @Override
   public ItemStack a(ItemStack itemstack, World world, EntityLiving entityliving) {
      EntityHuman entityhuman = entityliving instanceof EntityHuman ? (EntityHuman)entityliving : null;
      if (entityhuman instanceof EntityPlayer) {
         CriterionTriggers.z.a((EntityPlayer)entityhuman, itemstack);
      }

      if (!world.B) {
         for(MobEffect mobeffect : PotionUtil.a(itemstack)) {
            if (mobeffect.c().a()) {
               mobeffect.c().a(entityhuman, entityhuman, entityliving, mobeffect.e(), 1.0);
            } else {
               entityliving.addEffect(new MobEffect(mobeffect), Cause.POTION_DRINK);
            }
         }
      }

      if (entityhuman != null) {
         entityhuman.b(StatisticList.c.b(this));
         if (!entityhuman.fK().d) {
            itemstack.h(1);
         }
      }

      if (entityhuman == null || !entityhuman.fK().d) {
         if (itemstack.b()) {
            return new ItemStack(Items.rs);
         }

         if (entityhuman != null) {
            entityhuman.fJ().e(new ItemStack(Items.rs));
         }
      }

      entityliving.a(GameEvent.m);
      return itemstack;
   }

   @Override
   public EnumInteractionResult a(ItemActionContext itemactioncontext) {
      World world = itemactioncontext.q();
      BlockPosition blockposition = itemactioncontext.a();
      EntityHuman entityhuman = itemactioncontext.o();
      ItemStack itemstack = itemactioncontext.n();
      IBlockData iblockdata = world.a_(blockposition);
      if (itemactioncontext.k() != EnumDirection.a && iblockdata.a(TagsBlock.bV) && PotionUtil.d(itemstack) == Potions.c) {
         world.a(null, blockposition, SoundEffects.iS, SoundCategory.e, 1.0F, 1.0F);
         entityhuman.a(itemactioncontext.p(), ItemLiquidUtil.a(itemstack, entityhuman, new ItemStack(Items.rs)));
         entityhuman.b(StatisticList.c.b(itemstack.c()));
         if (!world.B) {
            WorldServer worldserver = (WorldServer)world;

            for(int i = 0; i < 5; ++i) {
               worldserver.a(
                  Particles.ai,
                  (double)blockposition.u() + world.z.j(),
                  (double)(blockposition.v() + 1),
                  (double)blockposition.w() + world.z.j(),
                  1,
                  0.0,
                  0.0,
                  0.0,
                  1.0
               );
            }
         }

         world.a(null, blockposition, SoundEffects.cg, SoundCategory.e, 1.0F, 1.0F);
         world.a(null, GameEvent.B, blockposition);
         world.b(blockposition, Blocks.rC.o());
         return EnumInteractionResult.a(world.B);
      } else {
         return EnumInteractionResult.d;
      }
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

   @Override
   public String j(ItemStack itemstack) {
      return PotionUtil.d(itemstack).b(this.a() + ".effect.");
   }

   @Override
   public void a(ItemStack itemstack, @Nullable World world, List<IChatBaseComponent> list, TooltipFlag tooltipflag) {
      PotionUtil.a(itemstack, list, 1.0F);
   }
}
