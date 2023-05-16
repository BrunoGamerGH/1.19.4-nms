package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class BlockNetherWart extends BlockPlant {
   public static final int a = 3;
   public static final BlockStateInteger b = BlockProperties.as;
   private static final VoxelShape[] c = new VoxelShape[]{
      Block.a(0.0, 0.0, 0.0, 16.0, 5.0, 16.0),
      Block.a(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
      Block.a(0.0, 0.0, 0.0, 16.0, 11.0, 16.0),
      Block.a(0.0, 0.0, 0.0, 16.0, 14.0, 16.0)
   };

   protected BlockNetherWart(BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.k(this.D.b().a(b, Integer.valueOf(0)));
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return c[iblockdata.c(b)];
   }

   @Override
   protected boolean d(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
      return iblockdata.a(Blocks.dW);
   }

   @Override
   public boolean e_(IBlockData iblockdata) {
      return iblockdata.c(b) < 3;
   }

   @Override
   public void b(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      int i = iblockdata.c(b);
      if (i < 3 && randomsource.i() < (float)worldserver.spigotConfig.wartModifier / 1000.0F) {
         iblockdata = iblockdata.a(b, Integer.valueOf(i + 1));
         CraftEventFactory.handleBlockGrowEvent(worldserver, blockposition, iblockdata, 2);
      }
   }

   @Override
   public ItemStack a(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata) {
      return new ItemStack(Items.rq);
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(b);
   }
}
