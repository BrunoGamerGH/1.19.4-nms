package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockPropertyDoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import net.minecraft.world.level.material.FluidTypes;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class BlockTallPlant extends BlockPlant {
   public static final BlockStateEnum<BlockPropertyDoubleBlockHalf> a = BlockProperties.ae;

   public BlockTallPlant(BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.k(this.D.b().a(a, BlockPropertyDoubleBlockHalf.b));
   }

   @Override
   public IBlockData a(
      IBlockData iblockdata,
      EnumDirection enumdirection,
      IBlockData iblockdata1,
      GeneratorAccess generatoraccess,
      BlockPosition blockposition,
      BlockPosition blockposition1
   ) {
      BlockPropertyDoubleBlockHalf blockpropertydoubleblockhalf = iblockdata.c(a);
      return enumdirection.o() != EnumDirection.EnumAxis.b
            || blockpropertydoubleblockhalf == BlockPropertyDoubleBlockHalf.b != (enumdirection == EnumDirection.b)
            || iblockdata1.a(this) && iblockdata1.c(a) != blockpropertydoubleblockhalf
         ? (
            blockpropertydoubleblockhalf == BlockPropertyDoubleBlockHalf.b
                  && enumdirection == EnumDirection.a
                  && !iblockdata.a(generatoraccess, blockposition)
               ? Blocks.a.o()
               : super.a(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1)
         )
         : Blocks.a.o();
   }

   @Nullable
   @Override
   public IBlockData a(BlockActionContext blockactioncontext) {
      BlockPosition blockposition = blockactioncontext.a();
      World world = blockactioncontext.q();
      return blockposition.v() < world.ai() - 1 && world.a_(blockposition.c()).a(blockactioncontext) ? super.a(blockactioncontext) : null;
   }

   @Override
   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, EntityLiving entityliving, ItemStack itemstack) {
      BlockPosition blockposition1 = blockposition.c();
      world.a(blockposition1, a(world, blockposition1, this.o().a(a, BlockPropertyDoubleBlockHalf.a)), 3);
   }

   @Override
   public boolean a(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
      if (iblockdata.c(a) != BlockPropertyDoubleBlockHalf.a) {
         return super.a(iblockdata, iworldreader, blockposition);
      } else {
         IBlockData iblockdata1 = iworldreader.a_(blockposition.d());
         return iblockdata1.a(this) && iblockdata1.c(a) == BlockPropertyDoubleBlockHalf.b;
      }
   }

   public static void a(GeneratorAccess generatoraccess, IBlockData iblockdata, BlockPosition blockposition, int i) {
      BlockPosition blockposition1 = blockposition.c();
      generatoraccess.a(blockposition, a(generatoraccess, blockposition, iblockdata.a(a, BlockPropertyDoubleBlockHalf.b)), i);
      generatoraccess.a(blockposition1, a(generatoraccess, blockposition1, iblockdata.a(a, BlockPropertyDoubleBlockHalf.a)), i);
   }

   public static IBlockData a(IWorldReader iworldreader, BlockPosition blockposition, IBlockData iblockdata) {
      return iblockdata.b(BlockProperties.C) ? iblockdata.a(BlockProperties.C, Boolean.valueOf(iworldreader.B(blockposition))) : iblockdata;
   }

   @Override
   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman) {
      if (!world.B) {
         if (entityhuman.f()) {
            b(world, blockposition, iblockdata, entityhuman);
         } else {
            a(iblockdata, world, blockposition, null, entityhuman, entityhuman.eK());
         }
      }

      super.a(world, blockposition, iblockdata, entityhuman);
   }

   @Override
   public void a(
      World world, EntityHuman entityhuman, BlockPosition blockposition, IBlockData iblockdata, @Nullable TileEntity tileentity, ItemStack itemstack
   ) {
      super.a(world, entityhuman, blockposition, Blocks.a.o(), tileentity, itemstack);
   }

   protected static void b(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman) {
      if (!CraftEventFactory.callBlockPhysicsEvent(world, blockposition).isCancelled()) {
         BlockPropertyDoubleBlockHalf blockpropertydoubleblockhalf = iblockdata.c(a);
         if (blockpropertydoubleblockhalf == BlockPropertyDoubleBlockHalf.a) {
            BlockPosition blockposition1 = blockposition.d();
            IBlockData iblockdata1 = world.a_(blockposition1);
            if (iblockdata1.a(iblockdata.b()) && iblockdata1.c(a) == BlockPropertyDoubleBlockHalf.b) {
               IBlockData iblockdata2 = iblockdata1.r().b(FluidTypes.c) ? Blocks.G.o() : Blocks.a.o();
               world.a(blockposition1, iblockdata2, 35);
               world.a(entityhuman, 2001, blockposition1, Block.i(iblockdata1));
            }
         }
      }
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(a);
   }

   @Override
   public long a(IBlockData iblockdata, BlockPosition blockposition) {
      return MathHelper.b(blockposition.u(), blockposition.c(iblockdata.c(a) == BlockPropertyDoubleBlockHalf.b ? 0 : 1).v(), blockposition.w());
   }
}
