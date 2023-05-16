package net.minecraft.world.level.block;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Queue;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.tags.TagsFluid;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlockState;
import org.bukkit.craftbukkit.v1_19_R3.util.BlockStateListPopulator;
import org.bukkit.event.block.SpongeAbsorbEvent;

public class BlockSponge extends Block {
   public static final int a = 6;
   public static final int b = 64;

   protected BlockSponge(BlockBase.Info blockbase_info) {
      super(blockbase_info);
   }

   @Override
   public void b(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
      if (!iblockdata1.a(iblockdata.b())) {
         this.a(world, blockposition);
      }
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Block block, BlockPosition blockposition1, boolean flag) {
      this.a(world, blockposition);
      super.a(iblockdata, world, blockposition, block, blockposition1, flag);
   }

   protected void a(World world, BlockPosition blockposition) {
      if (this.b(world, blockposition)) {
         world.a(blockposition, Blocks.aO.o(), 2);
         world.c(2001, blockposition, Block.i(Blocks.G.o()));
      }
   }

   private boolean b(World world, BlockPosition blockposition) {
      Queue<Tuple<BlockPosition, Integer>> queue = Lists.newLinkedList();
      queue.add(new Tuple<>(blockposition, 0));
      int i = 0;
      BlockStateListPopulator blockList = new BlockStateListPopulator(world);

      while(!queue.isEmpty()) {
         Tuple<BlockPosition, Integer> tuple = queue.poll();
         BlockPosition blockposition1 = tuple.a();
         int j = tuple.b();

         for(EnumDirection enumdirection : EnumDirection.values()) {
            BlockPosition blockposition2 = blockposition1.a(enumdirection);
            IBlockData iblockdata = blockList.a_(blockposition2);
            Fluid fluid = blockList.b_(blockposition2);
            Material material = iblockdata.d();
            if (fluid.a(TagsFluid.a)) {
               if (iblockdata.b() instanceof IFluidSource && !((IFluidSource)iblockdata.b()).c(blockList, blockposition2, iblockdata).b()) {
                  ++i;
                  if (j < 6) {
                     queue.add(new Tuple<>(blockposition2, j + 1));
                  }
               } else if (iblockdata.b() instanceof BlockFluids) {
                  blockList.a(blockposition2, Blocks.a.o(), 3);
                  ++i;
                  if (j < 6) {
                     queue.add(new Tuple<>(blockposition2, j + 1));
                  }
               } else if (material == Material.f || material == Material.i) {
                  blockList.a(blockposition2, Blocks.a.o(), 3);
                  ++i;
                  if (j < 6) {
                     queue.add(new Tuple<>(blockposition2, j + 1));
                  }
               }
            }
         }

         if (i > 64) {
            break;
         }
      }

      List<CraftBlockState> blocks = blockList.getList();
      if (!blocks.isEmpty()) {
         org.bukkit.block.Block bblock = world.getWorld().getBlockAt(blockposition.u(), blockposition.v(), blockposition.w());
         SpongeAbsorbEvent event = new SpongeAbsorbEvent(bblock, blocks);
         world.getCraftServer().getPluginManager().callEvent(event);
         if (event.isCancelled()) {
            return false;
         }

         for(CraftBlockState block : blocks) {
            BlockPosition blockposition2 = block.getPosition();
            IBlockData iblockdata = world.a_(blockposition2);
            Fluid fluid = world.b_(blockposition2);
            Material material = iblockdata.d();
            if (fluid.a(TagsFluid.a)
               && (!(iblockdata.b() instanceof IFluidSource) || ((IFluidSource)iblockdata.b()).c(blockList, blockposition2, iblockdata).b())
               && !(iblockdata.b() instanceof BlockFluids)
               && (material == Material.f || material == Material.i)) {
               TileEntity tileentity = iblockdata.q() ? world.c_(blockposition2) : null;
               a(iblockdata, world, blockposition2, tileentity);
            }

            world.a(blockposition2, block.getHandle(), block.getFlag());
         }
      }

      return i > 0;
   }
}
