package net.minecraft.world.item;

import net.minecraft.core.BlockPosition;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.RandomSource;
import net.minecraft.world.EnumInteractionResult;
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

public class ItemFireball extends Item {
   public ItemFireball(Item.Info item_info) {
      super(item_info);
   }

   @Override
   public EnumInteractionResult a(ItemActionContext itemactioncontext) {
      World world = itemactioncontext.q();
      BlockPosition blockposition = itemactioncontext.a();
      IBlockData iblockdata = world.a_(blockposition);
      boolean flag = false;
      if (!BlockCampfire.h(iblockdata) && !CandleBlock.g(iblockdata) && !CandleCakeBlock.g(iblockdata)) {
         blockposition = blockposition.a(itemactioncontext.k());
         if (BlockFireAbstract.a(world, blockposition, itemactioncontext.g())) {
            if (CraftEventFactory.callBlockIgniteEvent(world, blockposition, IgniteCause.FIREBALL, itemactioncontext.o()).isCancelled()) {
               if (!itemactioncontext.o().fK().d) {
                  itemactioncontext.n().h(1);
               }

               return EnumInteractionResult.d;
            }

            this.a(world, blockposition);
            world.b(blockposition, BlockFireAbstract.a(world, blockposition));
            world.a(itemactioncontext.o(), GameEvent.i, blockposition);
            flag = true;
         }
      } else {
         if (CraftEventFactory.callBlockIgniteEvent(world, blockposition, IgniteCause.FIREBALL, itemactioncontext.o()).isCancelled()) {
            if (!itemactioncontext.o().fK().d) {
               itemactioncontext.n().h(1);
            }

            return EnumInteractionResult.d;
         }

         this.a(world, blockposition);
         world.b(blockposition, iblockdata.a(BlockProperties.r, Boolean.valueOf(true)));
         world.a(itemactioncontext.o(), GameEvent.c, blockposition);
         flag = true;
      }

      if (flag) {
         itemactioncontext.n().h(1);
         return EnumInteractionResult.a(world.B);
      } else {
         return EnumInteractionResult.e;
      }
   }

   private void a(World world, BlockPosition blockposition) {
      RandomSource randomsource = world.r_();
      world.a(null, blockposition, SoundEffects.hz, SoundCategory.e, 1.0F, (randomsource.i() - randomsource.i()) * 0.2F + 1.0F);
   }
}
