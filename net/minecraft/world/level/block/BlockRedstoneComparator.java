package net.minecraft.world.level.block;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.RandomSource;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.decoration.EntityItemFrame;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityComparator;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockPropertyComparatorMode;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.ticks.TickListPriority;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class BlockRedstoneComparator extends BlockDiodeAbstract implements ITileEntity {
   public static final BlockStateEnum<BlockPropertyComparatorMode> a = BlockProperties.bd;

   public BlockRedstoneComparator(BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.k(this.D.b().a(aD, EnumDirection.c).a(c, Boolean.valueOf(false)).a(a, BlockPropertyComparatorMode.a));
   }

   @Override
   protected int g(IBlockData iblockdata) {
      return 2;
   }

   @Override
   protected int b(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata) {
      TileEntity tileentity = iblockaccess.c_(blockposition);
      return tileentity instanceof TileEntityComparator ? ((TileEntityComparator)tileentity).c() : 0;
   }

   private int e(World world, BlockPosition blockposition, IBlockData iblockdata) {
      int i = this.b(world, blockposition, iblockdata);
      if (i == 0) {
         return 0;
      } else {
         int j = this.b((IWorldReader)world, blockposition, iblockdata);
         return j > i ? 0 : (iblockdata.c(a) == BlockPropertyComparatorMode.b ? i - j : i);
      }
   }

   @Override
   protected boolean a(World world, BlockPosition blockposition, IBlockData iblockdata) {
      int i = this.b(world, blockposition, iblockdata);
      if (i == 0) {
         return false;
      } else {
         int j = this.b((IWorldReader)world, blockposition, iblockdata);
         return i > j ? true : i == j && iblockdata.c(a) == BlockPropertyComparatorMode.a;
      }
   }

   @Override
   protected int b(World world, BlockPosition blockposition, IBlockData iblockdata) {
      int i = super.b(world, blockposition, iblockdata);
      EnumDirection enumdirection = iblockdata.c(aD);
      BlockPosition blockposition1 = blockposition.a(enumdirection);
      IBlockData iblockdata1 = world.a_(blockposition1);
      if (iblockdata1.k()) {
         i = iblockdata1.a(world, blockposition1);
      } else if (i < 15 && iblockdata1.g(world, blockposition1)) {
         blockposition1 = blockposition1.a(enumdirection);
         iblockdata1 = world.a_(blockposition1);
         EntityItemFrame entityitemframe = this.a(world, enumdirection, blockposition1);
         int j = Math.max(
            entityitemframe == null ? Integer.MIN_VALUE : entityitemframe.D(), iblockdata1.k() ? iblockdata1.a(world, blockposition1) : Integer.MIN_VALUE
         );
         if (j != Integer.MIN_VALUE) {
            i = j;
         }
      }

      return i;
   }

   @Nullable
   private EntityItemFrame a(World world, EnumDirection enumdirection, BlockPosition blockposition) {
      List<EntityItemFrame> list = world.a(
         EntityItemFrame.class,
         new AxisAlignedBB(
            (double)blockposition.u(),
            (double)blockposition.v(),
            (double)blockposition.w(),
            (double)(blockposition.u() + 1),
            (double)(blockposition.v() + 1),
            (double)(blockposition.w() + 1)
         ),
         entityitemframe -> entityitemframe != null && entityitemframe.cA() == enumdirection
      );
      return list.size() == 1 ? list.get(0) : null;
   }

   @Override
   public EnumInteractionResult a(
      IBlockData iblockdata,
      World world,
      BlockPosition blockposition,
      EntityHuman entityhuman,
      EnumHand enumhand,
      MovingObjectPositionBlock movingobjectpositionblock
   ) {
      if (!entityhuman.fK().e) {
         return EnumInteractionResult.d;
      } else {
         iblockdata = iblockdata.a(a);
         float f = iblockdata.c(a) == BlockPropertyComparatorMode.b ? 0.55F : 0.5F;
         world.a(entityhuman, blockposition, SoundEffects.eB, SoundCategory.e, 0.3F, f);
         world.a(blockposition, iblockdata, 2);
         this.f(world, blockposition, iblockdata);
         return EnumInteractionResult.a(world.B);
      }
   }

   @Override
   protected void c(World world, BlockPosition blockposition, IBlockData iblockdata) {
      if (!world.K().b(blockposition, this)) {
         int i = this.e(world, blockposition, iblockdata);
         TileEntity tileentity = world.c_(blockposition);
         int j = tileentity instanceof TileEntityComparator ? ((TileEntityComparator)tileentity).c() : 0;
         if (i != j || iblockdata.c(c) != this.a(world, blockposition, iblockdata)) {
            TickListPriority ticklistpriority = this.c(world, blockposition, iblockdata) ? TickListPriority.c : TickListPriority.d;
            world.a(blockposition, this, 2, ticklistpriority);
         }
      }
   }

   private void f(World world, BlockPosition blockposition, IBlockData iblockdata) {
      int i = this.e(world, blockposition, iblockdata);
      TileEntity tileentity = world.c_(blockposition);
      int j = 0;
      if (tileentity instanceof TileEntityComparator tileentitycomparator) {
         j = tileentitycomparator.c();
         tileentitycomparator.a(i);
      }

      if (j != i || iblockdata.c(a) == BlockPropertyComparatorMode.a) {
         boolean flag = this.a(world, blockposition, iblockdata);
         boolean flag1 = iblockdata.c(c);
         if (flag1 && !flag) {
            if (CraftEventFactory.callRedstoneChange(world, blockposition, 15, 0).getNewCurrent() != 0) {
               return;
            }

            world.a(blockposition, iblockdata.a(c, Boolean.valueOf(false)), 2);
         } else if (!flag1 && flag) {
            if (CraftEventFactory.callRedstoneChange(world, blockposition, 0, 15).getNewCurrent() != 15) {
               return;
            }

            world.a(blockposition, iblockdata.a(c, Boolean.valueOf(true)), 2);
         }

         this.d(world, blockposition, iblockdata);
      }
   }

   @Override
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      this.f(worldserver, blockposition, iblockdata);
   }

   @Override
   public boolean a(IBlockData iblockdata, World world, BlockPosition blockposition, int i, int j) {
      super.a(iblockdata, world, blockposition, i, j);
      TileEntity tileentity = world.c_(blockposition);
      return tileentity != null && tileentity.a_(i, j);
   }

   @Override
   public TileEntity a(BlockPosition blockposition, IBlockData iblockdata) {
      return new TileEntityComparator(blockposition, iblockdata);
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(aD, a, c);
   }
}
