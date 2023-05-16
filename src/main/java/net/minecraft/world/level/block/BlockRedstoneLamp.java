package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class BlockRedstoneLamp extends Block {
   public static final BlockStateBoolean a = BlockRedstoneTorch.a;

   public BlockRedstoneLamp(BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.k(this.o().a(a, Boolean.valueOf(false)));
   }

   @Nullable
   @Override
   public IBlockData a(BlockActionContext blockactioncontext) {
      return this.o().a(a, Boolean.valueOf(blockactioncontext.q().r(blockactioncontext.a())));
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Block block, BlockPosition blockposition1, boolean flag) {
      if (!world.B) {
         boolean flag1 = iblockdata.c(a);
         if (flag1 != world.r(blockposition)) {
            if (flag1) {
               world.a(blockposition, this, 4);
            } else {
               if (CraftEventFactory.callRedstoneChange(world, blockposition, 0, 15).getNewCurrent() != 15) {
                  return;
               }

               world.a(blockposition, iblockdata.a(a), 2);
            }
         }
      }
   }

   @Override
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      if (iblockdata.c(a) && !worldserver.r(blockposition)) {
         if (CraftEventFactory.callRedstoneChange(worldserver, blockposition, 15, 0).getNewCurrent() != 0) {
            return;
         }

         worldserver.a(blockposition, iblockdata.a(a), 2);
      }
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(a);
   }
}
