package net.minecraft.world.level.block;

import java.util.function.Supplier;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class BlockStem extends BlockPlant implements IBlockFragilePlantElement {
   public static final int a = 7;
   public static final BlockStateInteger b = BlockProperties.av;
   protected static final float c = 1.0F;
   protected static final VoxelShape[] d = new VoxelShape[]{
      Block.a(7.0, 0.0, 7.0, 9.0, 2.0, 9.0),
      Block.a(7.0, 0.0, 7.0, 9.0, 4.0, 9.0),
      Block.a(7.0, 0.0, 7.0, 9.0, 6.0, 9.0),
      Block.a(7.0, 0.0, 7.0, 9.0, 8.0, 9.0),
      Block.a(7.0, 0.0, 7.0, 9.0, 10.0, 9.0),
      Block.a(7.0, 0.0, 7.0, 9.0, 12.0, 9.0),
      Block.a(7.0, 0.0, 7.0, 9.0, 14.0, 9.0),
      Block.a(7.0, 0.0, 7.0, 9.0, 16.0, 9.0)
   };
   private final BlockStemmed e;
   private final Supplier<Item> f;

   protected BlockStem(BlockStemmed blockstemmed, Supplier<Item> supplier, BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.e = blockstemmed;
      this.f = supplier;
      this.k(this.D.b().a(b, Integer.valueOf(0)));
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return d[iblockdata.c(b)];
   }

   @Override
   protected boolean d(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
      return iblockdata.a(Blocks.cB);
   }

   @Override
   public void b(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      if (worldserver.b(blockposition, 0) >= 9) {
         float f = BlockCrops.a(this, worldserver, blockposition);
         if ((double)randomsource.i()
            < (double)(this == Blocks.fc ? worldserver.spigotConfig.pumpkinModifier : worldserver.spigotConfig.melonModifier)
               / (100.0 * Math.floor((double)(25.0F / f + 1.0F)))) {
            int i = iblockdata.c(b);
            if (i < 7) {
               iblockdata = iblockdata.a(b, Integer.valueOf(i + 1));
               CraftEventFactory.handleBlockGrowEvent(worldserver, blockposition, iblockdata, 2);
            } else {
               EnumDirection enumdirection = EnumDirection.EnumDirectionLimit.a.a(randomsource);
               BlockPosition blockposition1 = blockposition.a(enumdirection);
               IBlockData iblockdata1 = worldserver.a_(blockposition1.d());
               if (worldserver.a_(blockposition1).h() && (iblockdata1.a(Blocks.cB) || iblockdata1.a(TagsBlock.ae))) {
                  if (!CraftEventFactory.handleBlockGrowEvent(worldserver, blockposition1, this.e.o())) {
                     return;
                  }

                  worldserver.b(blockposition, this.e.c().o().a(BlockFacingHorizontal.aD, enumdirection));
               }
            }
         }
      }
   }

   @Override
   public ItemStack a(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata) {
      return new ItemStack(this.f.get());
   }

   @Override
   public boolean a(IWorldReader iworldreader, BlockPosition blockposition, IBlockData iblockdata, boolean flag) {
      return iblockdata.c(b) != 7;
   }

   @Override
   public boolean a(World world, RandomSource randomsource, BlockPosition blockposition, IBlockData iblockdata) {
      return true;
   }

   @Override
   public void a(WorldServer worldserver, RandomSource randomsource, BlockPosition blockposition, IBlockData iblockdata) {
      int i = Math.min(7, iblockdata.c(b) + MathHelper.a(worldserver.z, 2, 5));
      IBlockData iblockdata1 = iblockdata.a(b, Integer.valueOf(i));
      CraftEventFactory.handleBlockGrowEvent(worldserver, blockposition, iblockdata1, 2);
      if (i == 7) {
         iblockdata1.b(worldserver, blockposition, worldserver.z);
      }
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(b);
   }

   public BlockStemmed b() {
      return this.e;
   }
}
