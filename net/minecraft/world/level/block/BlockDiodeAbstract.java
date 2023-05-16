package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.ticks.TickListPriority;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public abstract class BlockDiodeAbstract extends BlockFacingHorizontal {
   protected static final VoxelShape b = Block.a(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);
   public static final BlockStateBoolean c = BlockProperties.w;

   protected BlockDiodeAbstract(BlockBase.Info blockbase_info) {
      super(blockbase_info);
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return b;
   }

   @Override
   public boolean a(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
      return c(iworldreader, blockposition.d());
   }

   @Override
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      if (!this.a((IWorldReader)worldserver, blockposition, iblockdata)) {
         boolean flag = iblockdata.c(c);
         boolean flag1 = this.a((World)worldserver, blockposition, iblockdata);
         if (flag && !flag1) {
            if (CraftEventFactory.callRedstoneChange(worldserver, blockposition, 15, 0).getNewCurrent() != 0) {
               return;
            }

            worldserver.a(blockposition, iblockdata.a(c, Boolean.valueOf(false)), 2);
         } else if (!flag) {
            if (CraftEventFactory.callRedstoneChange(worldserver, blockposition, 0, 15).getNewCurrent() != 15) {
               return;
            }

            worldserver.a(blockposition, iblockdata.a(c, Boolean.valueOf(true)), 2);
            if (!flag1) {
               worldserver.a(blockposition, this, this.g(iblockdata), TickListPriority.b);
            }
         }
      }
   }

   @Override
   public int b(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
      return iblockdata.b(iblockaccess, blockposition, enumdirection);
   }

   @Override
   public int a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
      return !iblockdata.c(c) ? 0 : (iblockdata.c(aD) == enumdirection ? this.b(iblockaccess, blockposition, iblockdata) : 0);
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Block block, BlockPosition blockposition1, boolean flag) {
      if (iblockdata.a((IWorldReader)world, blockposition)) {
         this.c(world, blockposition, iblockdata);
      } else {
         TileEntity tileentity = iblockdata.q() ? world.c_(blockposition) : null;
         a(iblockdata, world, blockposition, tileentity);
         world.a(blockposition, false);

         for(EnumDirection enumdirection : EnumDirection.values()) {
            world.a(blockposition.a(enumdirection), this);
         }
      }
   }

   protected void c(World world, BlockPosition blockposition, IBlockData iblockdata) {
      if (!this.a((IWorldReader)world, blockposition, iblockdata)) {
         boolean flag = iblockdata.c(c);
         boolean flag1 = this.a(world, blockposition, iblockdata);
         if (flag != flag1 && !world.K().b(blockposition, this)) {
            TickListPriority ticklistpriority = TickListPriority.c;
            if (this.c((IBlockAccess)world, blockposition, iblockdata)) {
               ticklistpriority = TickListPriority.a;
            } else if (flag) {
               ticklistpriority = TickListPriority.b;
            }

            world.a(blockposition, this, this.g(iblockdata), ticklistpriority);
         }
      }
   }

   public boolean a(IWorldReader iworldreader, BlockPosition blockposition, IBlockData iblockdata) {
      return false;
   }

   protected boolean a(World world, BlockPosition blockposition, IBlockData iblockdata) {
      return this.b(world, blockposition, iblockdata) > 0;
   }

   protected int b(World world, BlockPosition blockposition, IBlockData iblockdata) {
      EnumDirection enumdirection = iblockdata.c(aD);
      BlockPosition blockposition1 = blockposition.a(enumdirection);
      int i = world.b(blockposition1, enumdirection);
      if (i >= 15) {
         return i;
      } else {
         IBlockData iblockdata1 = world.a_(blockposition1);
         return Math.max(i, iblockdata1.a(Blocks.cv) ? iblockdata1.c(BlockRedstoneWire.e) : 0);
      }
   }

   protected int b(IWorldReader iworldreader, BlockPosition blockposition, IBlockData iblockdata) {
      EnumDirection enumdirection = iblockdata.c(aD);
      EnumDirection enumdirection1 = enumdirection.h();
      EnumDirection enumdirection2 = enumdirection.i();
      return Math.max(
         this.b(iworldreader, blockposition.a(enumdirection1), enumdirection1), this.b(iworldreader, blockposition.a(enumdirection2), enumdirection2)
      );
   }

   protected int b(IWorldReader iworldreader, BlockPosition blockposition, EnumDirection enumdirection) {
      IBlockData iblockdata = iworldreader.a_(blockposition);
      return this.h(iblockdata)
         ? (iblockdata.a(Blocks.gZ) ? 15 : (iblockdata.a(Blocks.cv) ? iblockdata.c(BlockRedstoneWire.e) : iworldreader.c(blockposition, enumdirection)))
         : 0;
   }

   @Override
   public boolean f_(IBlockData iblockdata) {
      return true;
   }

   @Override
   public IBlockData a(BlockActionContext blockactioncontext) {
      return this.o().a(aD, blockactioncontext.g().g());
   }

   @Override
   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, EntityLiving entityliving, ItemStack itemstack) {
      if (this.a(world, blockposition, iblockdata)) {
         world.a(blockposition, this, 1);
      }
   }

   @Override
   public void b(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
      this.d(world, blockposition, iblockdata);
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
      if (!flag && !iblockdata.a(iblockdata1.b())) {
         super.a(iblockdata, world, blockposition, iblockdata1, flag);
         this.d(world, blockposition, iblockdata);
      }
   }

   protected void d(World world, BlockPosition blockposition, IBlockData iblockdata) {
      EnumDirection enumdirection = iblockdata.c(aD);
      BlockPosition blockposition1 = blockposition.a(enumdirection.g());
      world.a(blockposition1, this, blockposition);
      world.a(blockposition1, this, enumdirection);
   }

   protected boolean h(IBlockData iblockdata) {
      return iblockdata.j();
   }

   protected int b(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata) {
      return 15;
   }

   public static boolean n(IBlockData iblockdata) {
      return iblockdata.b() instanceof BlockDiodeAbstract;
   }

   public boolean c(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata) {
      EnumDirection enumdirection = iblockdata.c(aD).g();
      IBlockData iblockdata1 = iblockaccess.a_(blockposition.a(enumdirection));
      return n(iblockdata1) && iblockdata1.c(aD) != enumdirection;
   }

   protected abstract int g(IBlockData var1);
}
