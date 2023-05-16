package net.minecraft.world.level.block;

import java.util.Collections;
import java.util.function.ToIntFunction;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.event.player.PlayerHarvestBlockEvent;

public interface CaveVines {
   VoxelShape p_ = Block.a(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);
   BlockStateBoolean q_ = BlockProperties.D;

   static EnumInteractionResult a(@Nullable Entity entity, IBlockData iblockdata, World world, BlockPosition blockposition) {
      if (iblockdata.c(q_)) {
         if (CraftEventFactory.callEntityChangeBlockEvent(entity, blockposition, iblockdata.a(q_, Boolean.valueOf(false))).isCancelled()) {
            return EnumInteractionResult.a;
         } else {
            if (entity instanceof EntityHuman) {
               PlayerHarvestBlockEvent event = CraftEventFactory.callPlayerHarvestBlockEvent(
                  world, blockposition, (EntityHuman)entity, EnumHand.a, Collections.singletonList(new ItemStack(Items.vq, 1))
               );
               if (event.isCancelled()) {
                  return EnumInteractionResult.a;
               }

               for(org.bukkit.inventory.ItemStack itemStack : event.getItemsHarvested()) {
                  Block.a(world, blockposition, CraftItemStack.asNMSCopy(itemStack));
               }
            } else {
               Block.a(world, blockposition, new ItemStack(Items.vq, 1));
            }

            float f = MathHelper.b(world.z, 0.8F, 1.2F);
            world.a(null, blockposition, SoundEffects.dt, SoundCategory.e, 1.0F, f);
            IBlockData iblockdata1 = iblockdata.a(q_, Boolean.valueOf(false));
            world.a(blockposition, iblockdata1, 2);
            world.a(GameEvent.c, blockposition, GameEvent.a.a(entity, iblockdata1));
            return EnumInteractionResult.a(world.B);
         }
      } else {
         return EnumInteractionResult.d;
      }
   }

   static boolean a(IBlockData iblockdata) {
      return iblockdata.b(q_) && iblockdata.c(q_);
   }

   static ToIntFunction<IBlockData> h_(int i) {
      return iblockdata -> iblockdata.c(BlockProperties.D) ? i : 0;
   }
}
