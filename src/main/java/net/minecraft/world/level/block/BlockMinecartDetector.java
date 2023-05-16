package net.minecraft.world.level.block;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.IInventory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.IEntitySelector;
import net.minecraft.world.entity.vehicle.EntityMinecartAbstract;
import net.minecraft.world.entity.vehicle.EntityMinecartCommandBlock;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockPropertyTrackPosition;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import net.minecraft.world.level.block.state.properties.IBlockState;
import net.minecraft.world.phys.AxisAlignedBB;
import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockMinecartDetector extends BlockMinecartTrackAbstract {
   public static final BlockStateEnum<BlockPropertyTrackPosition> d = BlockProperties.ah;
   public static final BlockStateBoolean e = BlockProperties.w;
   private static final int f = 20;

   public BlockMinecartDetector(BlockBase.Info blockbase_info) {
      super(true, blockbase_info);
      this.k(this.D.b().a(e, Boolean.valueOf(false)).a(d, BlockPropertyTrackPosition.a).a(c, Boolean.valueOf(false)));
   }

   @Override
   public boolean f_(IBlockData iblockdata) {
      return true;
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Entity entity) {
      if (!world.B && !iblockdata.c(e)) {
         this.a(world, blockposition, iblockdata);
      }
   }

   @Override
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      if (iblockdata.c(e)) {
         this.a(worldserver, blockposition, iblockdata);
      }
   }

   @Override
   public int a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
      return iblockdata.c(e) ? 15 : 0;
   }

   @Override
   public int b(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
      return !iblockdata.c(e) ? 0 : (enumdirection == EnumDirection.b ? 15 : 0);
   }

   private void a(World world, BlockPosition blockposition, IBlockData iblockdata) {
      if (this.a(iblockdata, (IWorldReader)world, blockposition)) {
         boolean flag = iblockdata.c(e);
         boolean flag1 = false;
         List<EntityMinecartAbstract> list = this.a(world, blockposition, EntityMinecartAbstract.class, entity -> true);
         if (!list.isEmpty()) {
            flag1 = true;
         }

         if (flag != flag1) {
            org.bukkit.block.Block block = world.getWorld().getBlockAt(blockposition.u(), blockposition.v(), blockposition.w());
            BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, flag ? 15 : 0, flag1 ? 15 : 0);
            world.getCraftServer().getPluginManager().callEvent(eventRedstone);
            flag1 = eventRedstone.getNewCurrent() > 0;
         }

         if (flag1 && !flag) {
            IBlockData iblockdata1 = iblockdata.a(e, Boolean.valueOf(true));
            world.a(blockposition, iblockdata1, 3);
            this.b(world, blockposition, iblockdata1, true);
            world.a(blockposition, this);
            world.a(blockposition.d(), this);
            world.b(blockposition, iblockdata, iblockdata1);
         }

         if (!flag1 && flag) {
            IBlockData iblockdata1 = iblockdata.a(e, Boolean.valueOf(false));
            world.a(blockposition, iblockdata1, 3);
            this.b(world, blockposition, iblockdata1, false);
            world.a(blockposition, this);
            world.a(blockposition.d(), this);
            world.b(blockposition, iblockdata, iblockdata1);
         }

         if (flag1) {
            world.a(blockposition, this, 20);
         }

         world.c(blockposition, this);
      }
   }

   protected void b(World world, BlockPosition blockposition, IBlockData iblockdata, boolean flag) {
      MinecartTrackLogic minecarttracklogic = new MinecartTrackLogic(world, blockposition, iblockdata);

      for(BlockPosition blockposition1 : minecarttracklogic.a()) {
         IBlockData iblockdata1 = world.a_(blockposition1);
         world.a(iblockdata1, blockposition1, iblockdata1.b(), blockposition, false);
      }
   }

   @Override
   public void b(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
      if (!iblockdata1.a(iblockdata.b())) {
         IBlockData iblockdata2 = this.a(iblockdata, world, blockposition, flag);
         this.a(world, blockposition, iblockdata2);
      }
   }

   @Override
   public IBlockState<BlockPropertyTrackPosition> c() {
      return d;
   }

   @Override
   public boolean d_(IBlockData iblockdata) {
      return true;
   }

   @Override
   public int a(IBlockData iblockdata, World world, BlockPosition blockposition) {
      if (iblockdata.c(e)) {
         List<EntityMinecartCommandBlock> list = this.a(world, blockposition, EntityMinecartCommandBlock.class, entity -> true);
         if (!list.isEmpty()) {
            return list.get(0).z().j();
         }

         List<EntityMinecartAbstract> list1 = this.a(world, blockposition, EntityMinecartAbstract.class, IEntitySelector.d);
         if (!list1.isEmpty()) {
            return Container.b((IInventory)list1.get(0));
         }
      }

      return 0;
   }

   private <T extends EntityMinecartAbstract> List<T> a(World world, BlockPosition blockposition, Class<T> oclass, Predicate<Entity> predicate) {
      return world.a(oclass, this.a(blockposition), predicate);
   }

   private AxisAlignedBB a(BlockPosition blockposition) {
      double d0 = 0.2;
      return new AxisAlignedBB(
         (double)blockposition.u() + 0.2,
         (double)blockposition.v(),
         (double)blockposition.w() + 0.2,
         (double)(blockposition.u() + 1) - 0.2,
         (double)(blockposition.v() + 1) - 0.2,
         (double)(blockposition.w() + 1) - 0.2
      );
   }

   @Override
   public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
      switch(enumblockrotation) {
         case c:
            switch((BlockPropertyTrackPosition)iblockdata.c(d)) {
               case c:
                  return iblockdata.a(d, BlockPropertyTrackPosition.d);
               case d:
                  return iblockdata.a(d, BlockPropertyTrackPosition.c);
               case e:
                  return iblockdata.a(d, BlockPropertyTrackPosition.f);
               case f:
                  return iblockdata.a(d, BlockPropertyTrackPosition.e);
               case g:
                  return iblockdata.a(d, BlockPropertyTrackPosition.i);
               case h:
                  return iblockdata.a(d, BlockPropertyTrackPosition.j);
               case i:
                  return iblockdata.a(d, BlockPropertyTrackPosition.g);
               case j:
                  return iblockdata.a(d, BlockPropertyTrackPosition.h);
            }
         case d:
            switch((BlockPropertyTrackPosition)iblockdata.c(d)) {
               case a:
                  return iblockdata.a(d, BlockPropertyTrackPosition.b);
               case b:
                  return iblockdata.a(d, BlockPropertyTrackPosition.a);
               case c:
                  return iblockdata.a(d, BlockPropertyTrackPosition.e);
               case d:
                  return iblockdata.a(d, BlockPropertyTrackPosition.f);
               case e:
                  return iblockdata.a(d, BlockPropertyTrackPosition.d);
               case f:
                  return iblockdata.a(d, BlockPropertyTrackPosition.c);
               case g:
                  return iblockdata.a(d, BlockPropertyTrackPosition.j);
               case h:
                  return iblockdata.a(d, BlockPropertyTrackPosition.g);
               case i:
                  return iblockdata.a(d, BlockPropertyTrackPosition.h);
               case j:
                  return iblockdata.a(d, BlockPropertyTrackPosition.i);
            }
         case b:
            switch((BlockPropertyTrackPosition)iblockdata.c(d)) {
               case a:
                  return iblockdata.a(d, BlockPropertyTrackPosition.b);
               case b:
                  return iblockdata.a(d, BlockPropertyTrackPosition.a);
               case c:
                  return iblockdata.a(d, BlockPropertyTrackPosition.f);
               case d:
                  return iblockdata.a(d, BlockPropertyTrackPosition.e);
               case e:
                  return iblockdata.a(d, BlockPropertyTrackPosition.c);
               case f:
                  return iblockdata.a(d, BlockPropertyTrackPosition.d);
               case g:
                  return iblockdata.a(d, BlockPropertyTrackPosition.h);
               case h:
                  return iblockdata.a(d, BlockPropertyTrackPosition.i);
               case i:
                  return iblockdata.a(d, BlockPropertyTrackPosition.j);
               case j:
                  return iblockdata.a(d, BlockPropertyTrackPosition.g);
            }
         default:
            return iblockdata;
      }
   }

   @Override
   public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
      BlockPropertyTrackPosition blockpropertytrackposition = iblockdata.c(d);
      switch(enumblockmirror) {
         case b:
            switch(blockpropertytrackposition) {
               case e:
                  return iblockdata.a(d, BlockPropertyTrackPosition.f);
               case f:
                  return iblockdata.a(d, BlockPropertyTrackPosition.e);
               case g:
                  return iblockdata.a(d, BlockPropertyTrackPosition.j);
               case h:
                  return iblockdata.a(d, BlockPropertyTrackPosition.i);
               case i:
                  return iblockdata.a(d, BlockPropertyTrackPosition.h);
               case j:
                  return iblockdata.a(d, BlockPropertyTrackPosition.g);
               default:
                  return super.a(iblockdata, enumblockmirror);
            }
         case c:
            switch(blockpropertytrackposition) {
               case c:
                  return iblockdata.a(d, BlockPropertyTrackPosition.d);
               case d:
                  return iblockdata.a(d, BlockPropertyTrackPosition.c);
               case e:
               case f:
               default:
                  break;
               case g:
                  return iblockdata.a(d, BlockPropertyTrackPosition.h);
               case h:
                  return iblockdata.a(d, BlockPropertyTrackPosition.g);
               case i:
                  return iblockdata.a(d, BlockPropertyTrackPosition.j);
               case j:
                  return iblockdata.a(d, BlockPropertyTrackPosition.i);
            }
         default:
            return super.a(iblockdata, enumblockmirror);
      }
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(d, e, c);
   }
}
