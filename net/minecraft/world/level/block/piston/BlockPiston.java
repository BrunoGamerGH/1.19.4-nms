package net.minecraft.world.level.block.piston;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.AbstractList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.BlockDirectional;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EnumBlockMirror;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockPropertyPistonType;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.EnumPistonReaction;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.event.block.BlockPistonEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;

public class BlockPiston extends BlockDirectional {
   public static final BlockStateBoolean b = BlockProperties.g;
   public static final int c = 0;
   public static final int d = 1;
   public static final int e = 2;
   public static final float f = 4.0F;
   protected static final VoxelShape g = net.minecraft.world.level.block.Block.a(0.0, 0.0, 0.0, 12.0, 16.0, 16.0);
   protected static final VoxelShape h = net.minecraft.world.level.block.Block.a(4.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape i = net.minecraft.world.level.block.Block.a(0.0, 0.0, 0.0, 16.0, 16.0, 12.0);
   protected static final VoxelShape j = net.minecraft.world.level.block.Block.a(0.0, 0.0, 4.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape k = net.minecraft.world.level.block.Block.a(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);
   protected static final VoxelShape l = net.minecraft.world.level.block.Block.a(0.0, 4.0, 0.0, 16.0, 16.0, 16.0);
   private final boolean m;

   public BlockPiston(boolean flag, BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.k(this.D.b().a(a, EnumDirection.c).a(b, Boolean.valueOf(false)));
      this.m = flag;
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      if (iblockdata.c(b)) {
         switch((EnumDirection)iblockdata.c(a)) {
            case a:
               return l;
            case b:
            default:
               return k;
            case c:
               return j;
            case d:
               return i;
            case e:
               return h;
            case f:
               return g;
         }
      } else {
         return VoxelShapes.b();
      }
   }

   @Override
   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, EntityLiving entityliving, ItemStack itemstack) {
      if (!world.B) {
         this.a(world, blockposition, iblockdata);
      }
   }

   @Override
   public void a(
      IBlockData iblockdata, World world, BlockPosition blockposition, net.minecraft.world.level.block.Block block, BlockPosition blockposition1, boolean flag
   ) {
      if (!world.B) {
         this.a(world, blockposition, iblockdata);
      }
   }

   @Override
   public void b(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
      if (!iblockdata1.a(iblockdata.b()) && !world.B && world.c_(blockposition) == null) {
         this.a(world, blockposition, iblockdata);
      }
   }

   @Override
   public IBlockData a(BlockActionContext blockactioncontext) {
      return this.o().a(a, blockactioncontext.d().g()).a(b, Boolean.valueOf(false));
   }

   private void a(World world, BlockPosition blockposition, IBlockData iblockdata) {
      EnumDirection enumdirection = iblockdata.c(a);
      boolean flag = this.a(world, blockposition, enumdirection);
      if (flag && !iblockdata.c(b)) {
         if (new PistonExtendsChecker(world, blockposition, enumdirection, true).a()) {
            world.a(blockposition, this, 0, enumdirection.d());
         }
      } else if (!flag && iblockdata.c(b)) {
         BlockPosition blockposition1 = blockposition.a(enumdirection, 2);
         IBlockData iblockdata1 = world.a_(blockposition1);
         byte b0 = 1;
         if (iblockdata1.a(Blocks.bP) && iblockdata1.c(a) == enumdirection) {
            TileEntity tileentity = world.c_(blockposition1);
            if (tileentity instanceof TileEntityPiston tileentitypiston
               && tileentitypiston.c()
               && (tileentitypiston.a(0.0F) < 0.5F || world.U() == tileentitypiston.v() || ((WorldServer)world).c())) {
               b0 = 2;
            }
         }

         if (!this.m) {
            Block block = world.getWorld().getBlockAt(blockposition.u(), blockposition.v(), blockposition.w());
            BlockPistonRetractEvent event = new BlockPistonRetractEvent(block, ImmutableList.of(), CraftBlock.notchToBlockFace(enumdirection));
            world.getCraftServer().getPluginManager().callEvent(event);
            if (event.isCancelled()) {
               return;
            }
         }

         world.a(blockposition, this, b0, enumdirection.d());
      }
   }

   private boolean a(World world, BlockPosition blockposition, EnumDirection enumdirection) {
      for(EnumDirection enumdirection1 : EnumDirection.values()) {
         if (enumdirection1 != enumdirection && world.a(blockposition.a(enumdirection1), enumdirection1)) {
            return true;
         }
      }

      if (world.a(blockposition, EnumDirection.a)) {
         return true;
      } else {
         BlockPosition blockposition1 = blockposition.c();

         for(EnumDirection enumdirection2 : EnumDirection.values()) {
            if (enumdirection2 != EnumDirection.a && world.a(blockposition1.a(enumdirection2), enumdirection2)) {
               return true;
            }
         }

         return false;
      }
   }

   @Override
   public boolean a(IBlockData iblockdata, World world, BlockPosition blockposition, int i, int j) {
      EnumDirection enumdirection = iblockdata.c(a);
      if (!world.B) {
         boolean flag = this.a(world, blockposition, enumdirection);
         if (flag && (i == 1 || i == 2)) {
            world.a(blockposition, iblockdata.a(b, Boolean.valueOf(true)), 2);
            return false;
         }

         if (!flag && i == 0) {
            return false;
         }
      }

      if (i == 0) {
         if (!this.a(world, blockposition, enumdirection, true)) {
            return false;
         }

         world.a(blockposition, iblockdata.a(b, Boolean.valueOf(true)), 67);
         world.a(null, blockposition, SoundEffects.rY, SoundCategory.e, 0.5F, world.z.i() * 0.25F + 0.6F);
         world.a(null, GameEvent.L, blockposition);
      } else if (i == 1 || i == 2) {
         TileEntity tileentity = world.c_(blockposition.a(enumdirection));
         if (tileentity instanceof TileEntityPiston) {
            ((TileEntityPiston)tileentity).j();
         }

         IBlockData iblockdata1 = Blocks.bP
            .o()
            .a(BlockPistonMoving.a, enumdirection)
            .a(BlockPistonMoving.b, this.m ? BlockPropertyPistonType.b : BlockPropertyPistonType.a);
         world.a(blockposition, iblockdata1, 20);
         world.a(BlockPistonMoving.a(blockposition, iblockdata1, this.o().a(a, EnumDirection.a(j & 7)), enumdirection, false, true));
         world.b(blockposition, iblockdata1.b());
         iblockdata1.a(world, blockposition, 2);
         if (this.m) {
            BlockPosition blockposition1 = blockposition.b(enumdirection.j() * 2, enumdirection.k() * 2, enumdirection.l() * 2);
            IBlockData iblockdata2 = world.a_(blockposition1);
            boolean flag1 = false;
            if (iblockdata2.a(Blocks.bP)) {
               TileEntity tileentity1 = world.c_(blockposition1);
               if (tileentity1 instanceof TileEntityPiston tileentitypiston && tileentitypiston.d() == enumdirection && tileentitypiston.c()) {
                  tileentitypiston.j();
                  flag1 = true;
               }
            }

            if (!flag1) {
               if (i != 1
                  || iblockdata2.h()
                  || !a(iblockdata2, world, blockposition1, enumdirection.g(), false, enumdirection)
                  || iblockdata2.l() != EnumPistonReaction.a && !iblockdata2.a(Blocks.bx) && !iblockdata2.a(Blocks.bq)) {
                  world.a(blockposition.a(enumdirection), false);
               } else {
                  this.a(world, blockposition, enumdirection, false);
               }
            }
         } else {
            world.a(blockposition.a(enumdirection), false);
         }

         world.a(null, blockposition, SoundEffects.rX, SoundCategory.e, 0.5F, world.z.i() * 0.15F + 0.6F);
         world.a(null, GameEvent.K, blockposition);
      }

      return true;
   }

   public static boolean a(
      IBlockData iblockdata, World world, BlockPosition blockposition, EnumDirection enumdirection, boolean flag, EnumDirection enumdirection1
   ) {
      if (blockposition.v() < world.v_() || blockposition.v() > world.ai() - 1 || !world.p_().a(blockposition)) {
         return false;
      } else if (iblockdata.h()) {
         return true;
      } else if (iblockdata.a(Blocks.cn) || iblockdata.a(Blocks.pg) || iblockdata.a(Blocks.ph) || iblockdata.a(Blocks.si)) {
         return false;
      } else if (enumdirection == EnumDirection.a && blockposition.v() == world.v_()) {
         return false;
      } else if (enumdirection == EnumDirection.b && blockposition.v() == world.ai() - 1) {
         return false;
      } else {
         if (!iblockdata.a(Blocks.bx) && !iblockdata.a(Blocks.bq)) {
            if (iblockdata.h(world, blockposition) == -1.0F) {
               return false;
            }

            switch(iblockdata.l()) {
               case b:
                  return flag;
               case c:
                  return false;
               case d:
               default:
                  break;
               case e:
                  if (enumdirection == enumdirection1) {
                     return true;
                  }

                  return false;
            }
         } else if (iblockdata.c(b)) {
            return false;
         }

         return !iblockdata.q();
      }
   }

   private boolean a(World world, BlockPosition blockposition, EnumDirection enumdirection, boolean flag) {
      BlockPosition blockposition1 = blockposition.a(enumdirection);
      if (!flag && world.a_(blockposition1).a(Blocks.by)) {
         world.a(blockposition1, Blocks.a.o(), 20);
      }

      PistonExtendsChecker pistonextendschecker = new PistonExtendsChecker(world, blockposition, enumdirection, flag);
      if (!pistonextendschecker.a()) {
         return false;
      } else {
         Map<BlockPosition, IBlockData> map = Maps.newHashMap();
         List<BlockPosition> list = pistonextendschecker.c();
         List<IBlockData> list1 = Lists.newArrayList();

         for(int i = 0; i < list.size(); ++i) {
            BlockPosition blockposition2 = list.get(i);
            IBlockData iblockdata = world.a_(blockposition2);
            list1.add(iblockdata);
            map.put(blockposition2, iblockdata);
         }

         List<BlockPosition> list2 = pistonextendschecker.d();
         IBlockData[] aiblockdata = new IBlockData[list.size() + list2.size()];
         EnumDirection enumdirection1 = flag ? enumdirection : enumdirection.g();
         int j = 0;
         final Block bblock = world.getWorld().getBlockAt(blockposition.u(), blockposition.v(), blockposition.w());
         final List<BlockPosition> moved = pistonextendschecker.c();
         final List<BlockPosition> broken = pistonextendschecker.d();
         List<Block> blocks = new AbstractList<Block>() {
            @Override
            public int size() {
               return moved.size() + broken.size();
            }

            public Block get(int index) {
               if (index < this.size() && index >= 0) {
                  BlockPosition pos = index < moved.size() ? moved.get(index) : broken.get(index - moved.size());
                  return bblock.getWorld().getBlockAt(pos.u(), pos.v(), pos.w());
               } else {
                  throw new ArrayIndexOutOfBoundsException(index);
               }
            }
         };
         BlockPistonEvent event;
         if (flag) {
            event = new BlockPistonExtendEvent(bblock, blocks, CraftBlock.notchToBlockFace(enumdirection1));
         } else {
            event = new BlockPistonRetractEvent(bblock, blocks, CraftBlock.notchToBlockFace(enumdirection1));
         }

         world.getCraftServer().getPluginManager().callEvent(event);
         if (event.isCancelled()) {
            for(BlockPosition b : broken) {
               world.a(b, Blocks.a.o(), world.a_(b), 3);
            }

            for(BlockPosition b : moved) {
               world.a(b, Blocks.a.o(), world.a_(b), 3);
               b = b.a(enumdirection1);
               world.a(b, Blocks.a.o(), world.a_(b), 3);
            }

            return false;
         } else {
            for(int k = list2.size() - 1; k >= 0; --k) {
               BlockPosition blockposition3 = list2.get(k);
               IBlockData iblockdata1 = world.a_(blockposition3);
               TileEntity tileentity = iblockdata1.q() ? world.c_(blockposition3) : null;
               a(iblockdata1, world, blockposition3, tileentity);
               world.a(blockposition3, Blocks.a.o(), 18);
               world.a(GameEvent.f, blockposition3, GameEvent.a.a(iblockdata1));
               if (!iblockdata1.a(TagsBlock.aH)) {
                  world.a(blockposition3, iblockdata1);
               }

               aiblockdata[j++] = iblockdata1;
            }

            for(int var36 = list.size() - 1; var36 >= 0; --var36) {
               BlockPosition blockposition3 = list.get(var36);
               IBlockData iblockdata1 = world.a_(blockposition3);
               blockposition3 = blockposition3.a(enumdirection1);
               map.remove(blockposition3);
               IBlockData iblockdata2 = Blocks.bP.o().a(a, enumdirection);
               world.a(blockposition3, iblockdata2, 68);
               world.a(BlockPistonMoving.a(blockposition3, iblockdata2, list1.get(var36), enumdirection, flag, false));
               aiblockdata[j++] = iblockdata1;
            }

            if (flag) {
               BlockPropertyPistonType blockpropertypistontype = this.m ? BlockPropertyPistonType.b : BlockPropertyPistonType.a;
               IBlockData iblockdata3 = Blocks.by.o().a(BlockPistonExtension.a, enumdirection).a(BlockPistonExtension.b, blockpropertypistontype);
               IBlockData iblockdata1 = Blocks.bP
                  .o()
                  .a(BlockPistonMoving.a, enumdirection)
                  .a(BlockPistonMoving.b, this.m ? BlockPropertyPistonType.b : BlockPropertyPistonType.a);
               map.remove(blockposition1);
               world.a(blockposition1, iblockdata1, 68);
               world.a(BlockPistonMoving.a(blockposition1, iblockdata1, iblockdata3, enumdirection, true, true));
            }

            IBlockData iblockdata4 = Blocks.a.o();

            for(BlockPosition blockposition4 : map.keySet()) {
               world.a(blockposition4, iblockdata4, 82);
            }

            for(Entry<BlockPosition, IBlockData> entry : map.entrySet()) {
               BlockPosition blockposition5 = entry.getKey();
               IBlockData iblockdata5 = entry.getValue();
               iblockdata5.b(world, blockposition5, 2);
               iblockdata4.a(world, blockposition5, 2);
               iblockdata4.b(world, blockposition5, 2);
            }

            j = 0;

            for(int l = list2.size() - 1; l >= 0; --l) {
               IBlockData iblockdata1 = aiblockdata[j++];
               BlockPosition blockposition5 = list2.get(l);
               iblockdata1.b(world, blockposition5, 2);
               world.a(blockposition5, iblockdata1.b());
            }

            for(int var50 = list.size() - 1; var50 >= 0; --var50) {
               world.a(list.get(var50), aiblockdata[j++].b());
            }

            if (flag) {
               world.a(blockposition1, Blocks.by);
            }

            return true;
         }
      }
   }

   @Override
   public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
      return iblockdata.a(a, enumblockrotation.a(iblockdata.c(a)));
   }

   @Override
   public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
      return iblockdata.a(enumblockmirror.a(iblockdata.c(a)));
   }

   @Override
   protected void a(BlockStateList.a<net.minecraft.world.level.block.Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(a, b);
   }

   @Override
   public boolean g_(IBlockData iblockdata) {
      return iblockdata.c(b);
   }

   @Override
   public boolean a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, PathMode pathmode) {
      return false;
   }
}
