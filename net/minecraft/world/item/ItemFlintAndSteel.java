package net.minecraft.world.item;

import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.context.ItemActionContext;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.BlockCampfire;
import net.minecraft.world.level.block.BlockFireAbstract;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;

public class ItemFlintAndSteel extends Item {
   public ItemFlintAndSteel(Item.Info item_info) {
      super(item_info);
   }

   @Override
   public EnumInteractionResult a(ItemActionContext itemactioncontext) {
      EntityHuman entityhuman = itemactioncontext.o();
      World world = itemactioncontext.q();
      BlockPosition blockposition = itemactioncontext.a();
      IBlockData iblockdata = world.a_(blockposition);
      if (!BlockCampfire.h(iblockdata) && !CandleBlock.g(iblockdata) && !CandleCakeBlock.g(iblockdata)) {
         BlockPosition blockposition1 = blockposition.a(itemactioncontext.k());
         if (!BlockFireAbstract.a(world, blockposition1, itemactioncontext.g())) {
            return EnumInteractionResult.e;
         } else if (CraftEventFactory.callBlockIgniteEvent(world, blockposition1, IgniteCause.FLINT_AND_STEEL, entityhuman).isCancelled()) {
            itemactioncontext.n().a(1, entityhuman, entityhuman1 -> entityhuman1.d(itemactioncontext.p()));
            return EnumInteractionResult.d;
         } else {
            world.a(entityhuman, blockposition1, SoundEffects.hO, SoundCategory.e, 1.0F, world.r_().i() * 0.4F + 0.8F);
            IBlockData iblockdata1 = BlockFireAbstract.a(world, blockposition1);
            world.a(blockposition1, iblockdata1, 11);
            world.a(entityhuman, GameEvent.i, blockposition);
            ItemStack itemstack = itemactioncontext.n();
            if (entityhuman instanceof EntityPlayer) {
               CriterionTriggers.y.a((EntityPlayer)entityhuman, blockposition1, itemstack);
               itemstack.a(1, entityhuman, entityhuman1 -> entityhuman1.d(itemactioncontext.p()));
            }

            return EnumInteractionResult.a(world.k_());
         }
      } else if (CraftEventFactory.callBlockIgniteEvent(world, blockposition, IgniteCause.FLINT_AND_STEEL, entityhuman).isCancelled()) {
         itemactioncontext.n().a(1, entityhuman, entityhuman1 -> entityhuman1.d(itemactioncontext.p()));
         return EnumInteractionResult.d;
      } else {
         world.a(entityhuman, blockposition, SoundEffects.hO, SoundCategory.e, 1.0F, world.r_().i() * 0.4F + 0.8F);
         world.a(blockposition, iblockdata.a(BlockProperties.r, Boolean.valueOf(true)), 11);
         world.a(entityhuman, GameEvent.c, blockposition);
         if (entityhuman != null) {
            itemactioncontext.n().a(1, entityhuman, entityhuman1 -> entityhuman1.d(itemactioncontext.p()));
         }

         return EnumInteractionResult.a(world.k_());
      }
   }
}
