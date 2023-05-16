package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockPropertyTrackPosition;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import net.minecraft.world.level.block.state.properties.IBlockState;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class BlockPoweredRail extends BlockMinecartTrackAbstract {
   public static final BlockStateEnum<BlockPropertyTrackPosition> d = BlockProperties.ah;
   public static final BlockStateBoolean e = BlockProperties.w;

   protected BlockPoweredRail(BlockBase.Info blockbase_info) {
      super(true, blockbase_info);
      this.k(this.D.b().a(d, BlockPropertyTrackPosition.a).a(e, Boolean.valueOf(false)).a(c, Boolean.valueOf(false)));
   }

   protected boolean a(World world, BlockPosition blockposition, IBlockData iblockdata, boolean flag, int i) {
      if (i >= 8) {
         return false;
      } else {
         int j = blockposition.u();
         int k = blockposition.v();
         int l = blockposition.w();
         boolean flag1 = true;
         BlockPropertyTrackPosition blockpropertytrackposition = iblockdata.c(d);
         switch(blockpropertytrackposition) {
            case a:
               if (flag) {
                  ++l;
               } else {
                  --l;
               }
               break;
            case b:
               if (flag) {
                  --j;
               } else {
                  ++j;
               }
               break;
            case c:
               if (flag) {
                  --j;
               } else {
                  ++j;
                  ++k;
                  flag1 = false;
               }

               blockpropertytrackposition = BlockPropertyTrackPosition.b;
               break;
            case d:
               if (flag) {
                  --j;
                  ++k;
                  flag1 = false;
               } else {
                  ++j;
               }

               blockpropertytrackposition = BlockPropertyTrackPosition.b;
               break;
            case e:
               if (flag) {
                  ++l;
               } else {
                  --l;
                  ++k;
                  flag1 = false;
               }

               blockpropertytrackposition = BlockPropertyTrackPosition.a;
               break;
            case f:
               if (flag) {
                  ++l;
                  ++k;
                  flag1 = false;
               } else {
                  --l;
               }

               blockpropertytrackposition = BlockPropertyTrackPosition.a;
         }

         return this.a(world, new BlockPosition(j, k, l), flag, i, blockpropertytrackposition)
            ? true
            : flag1 && this.a(world, new BlockPosition(j, k - 1, l), flag, i, blockpropertytrackposition);
      }
   }

   protected boolean a(World world, BlockPosition blockposition, boolean flag, int i, BlockPropertyTrackPosition blockpropertytrackposition) {
      IBlockData iblockdata = world.a_(blockposition);
      if (!iblockdata.a(this)) {
         return false;
      } else {
         BlockPropertyTrackPosition blockpropertytrackposition1 = iblockdata.c(d);
         return blockpropertytrackposition != BlockPropertyTrackPosition.b
               || blockpropertytrackposition1 != BlockPropertyTrackPosition.a
                  && blockpropertytrackposition1 != BlockPropertyTrackPosition.e
                  && blockpropertytrackposition1 != BlockPropertyTrackPosition.f
            ? (
               blockpropertytrackposition != BlockPropertyTrackPosition.a
                     || blockpropertytrackposition1 != BlockPropertyTrackPosition.b
                        && blockpropertytrackposition1 != BlockPropertyTrackPosition.c
                        && blockpropertytrackposition1 != BlockPropertyTrackPosition.d
                  ? (iblockdata.c(e) ? (world.r(blockposition) ? true : this.a(world, blockposition, iblockdata, flag, i + 1)) : false)
                  : false
            )
            : false;
      }
   }

   @Override
   protected void a(IBlockData iblockdata, World world, BlockPosition blockposition, Block block) {
      boolean flag = iblockdata.c(e);
      boolean flag1 = world.r(blockposition) || this.a(world, blockposition, iblockdata, true, 0) || this.a(world, blockposition, iblockdata, false, 0);
      if (flag1 != flag) {
         int power = flag ? 15 : 0;
         int newPower = CraftEventFactory.callRedstoneChange(world, blockposition, power, 15 - power).getNewCurrent();
         if (newPower == power) {
            return;
         }

         world.a(blockposition, iblockdata.a(e, Boolean.valueOf(flag1)), 3);
         world.a(blockposition.d(), this);
         if (iblockdata.c(d).b()) {
            world.a(blockposition.c(), this);
         }
      }
   }

   @Override
   public IBlockState<BlockPropertyTrackPosition> c() {
      return d;
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
