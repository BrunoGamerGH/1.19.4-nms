package net.minecraft.world.level.block;

import com.google.common.base.MoreObjects;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateDirection;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockTripwireHook extends Block {
   public static final BlockStateDirection a = BlockFacingHorizontal.aD;
   public static final BlockStateBoolean b = BlockProperties.w;
   public static final BlockStateBoolean c = BlockProperties.a;
   protected static final int d = 1;
   protected static final int e = 42;
   private static final int k = 10;
   protected static final int f = 3;
   protected static final VoxelShape g = Block.a(5.0, 0.0, 10.0, 11.0, 10.0, 16.0);
   protected static final VoxelShape h = Block.a(5.0, 0.0, 0.0, 11.0, 10.0, 6.0);
   protected static final VoxelShape i = Block.a(10.0, 0.0, 5.0, 16.0, 10.0, 11.0);
   protected static final VoxelShape j = Block.a(0.0, 0.0, 5.0, 6.0, 10.0, 11.0);

   public BlockTripwireHook(BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.k(this.D.b().a(a, EnumDirection.c).a(b, Boolean.valueOf(false)).a(c, Boolean.valueOf(false)));
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      switch((EnumDirection)iblockdata.c(a)) {
         case c:
            return g;
         case d:
            return h;
         case e:
            return i;
         case f:
         default:
            return j;
      }
   }

   @Override
   public boolean a(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
      EnumDirection enumdirection = iblockdata.c(a);
      BlockPosition blockposition1 = blockposition.a(enumdirection.g());
      IBlockData iblockdata1 = iworldreader.a_(blockposition1);
      return enumdirection.o().d() && iblockdata1.d(iworldreader, blockposition1, enumdirection);
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
      return enumdirection.g() == iblockdata.c(a) && !iblockdata.a(generatoraccess, blockposition)
         ? Blocks.a.o()
         : super.a(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
   }

   @Nullable
   @Override
   public IBlockData a(BlockActionContext blockactioncontext) {
      IBlockData iblockdata = this.o().a(b, Boolean.valueOf(false)).a(c, Boolean.valueOf(false));
      World world = blockactioncontext.q();
      BlockPosition blockposition = blockactioncontext.a();
      EnumDirection[] aenumdirection = blockactioncontext.f();

      for(EnumDirection enumdirection : aenumdirection) {
         if (enumdirection.o().d()) {
            EnumDirection enumdirection1 = enumdirection.g();
            iblockdata = iblockdata.a(a, enumdirection1);
            if (iblockdata.a((IWorldReader)world, blockposition)) {
               return iblockdata;
            }
         }
      }

      return null;
   }

   @Override
   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, EntityLiving entityliving, ItemStack itemstack) {
      this.a(world, blockposition, iblockdata, false, false, -1, null);
   }

   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, boolean flag, boolean flag1, int i, @Nullable IBlockData iblockdata1) {
      EnumDirection enumdirection = iblockdata.c(a);
      boolean flag2 = iblockdata.c(c);
      boolean flag3 = iblockdata.c(b);
      boolean flag4 = !flag;
      boolean flag5 = false;
      int j = 0;
      IBlockData[] aiblockdata = new IBlockData[42];

      for(int k = 1; k < 42; ++k) {
         BlockPosition blockposition1 = blockposition.a(enumdirection, k);
         IBlockData iblockdata2 = world.a_(blockposition1);
         if (iblockdata2.a(Blocks.fG)) {
            if (iblockdata2.c(a) == enumdirection.g()) {
               j = k;
            }
            break;
         }

         if (!iblockdata2.a(Blocks.fH) && k != i) {
            aiblockdata[k] = null;
            flag4 = false;
         } else {
            if (k == i) {
               iblockdata2 = (IBlockData)MoreObjects.firstNonNull(iblockdata1, iblockdata2);
            }

            boolean flag6 = !iblockdata2.c(BlockTripwire.c);
            boolean flag7 = iblockdata2.c(BlockTripwire.a);
            flag5 |= flag6 && flag7;
            aiblockdata[k] = iblockdata2;
            if (k == i) {
               world.a(blockposition, this, 10);
               flag4 &= flag6;
            }
         }
      }

      flag4 &= j > 1;
      flag5 &= flag4;
      IBlockData iblockdata3 = this.o().a(c, Boolean.valueOf(flag4)).a(b, Boolean.valueOf(flag5));
      if (j > 0) {
         BlockPosition blockposition1 = blockposition.a(enumdirection, j);
         EnumDirection enumdirection1 = enumdirection.g();
         world.a(blockposition1, iblockdata3.a(a, enumdirection1), 3);
         this.a(world, blockposition1, enumdirection1);
         this.a(world, blockposition1, flag4, flag5, flag2, flag3);
      }

      org.bukkit.block.Block block = world.getWorld().getBlockAt(blockposition.u(), blockposition.v(), blockposition.w());
      BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, 15, 0);
      world.getCraftServer().getPluginManager().callEvent(eventRedstone);
      if (eventRedstone.getNewCurrent() <= 0) {
         this.a(world, blockposition, flag4, flag5, flag2, flag3);
         if (!flag) {
            world.a(blockposition, iblockdata3.a(a, enumdirection), 3);
            if (flag1) {
               this.a(world, blockposition, enumdirection);
            }
         }

         if (flag2 != flag4) {
            for(int l = 1; l < j; ++l) {
               BlockPosition blockposition2 = blockposition.a(enumdirection, l);
               IBlockData iblockdata4 = aiblockdata[l];
               if (iblockdata4 != null) {
                  world.a(blockposition2, iblockdata4.a(c, Boolean.valueOf(flag4)), 3);
                  if (!world.a_(blockposition2).h()) {
                  }
               }
            }
         }
      }
   }

   @Override
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      this.a(worldserver, blockposition, iblockdata, false, true, -1, null);
   }

   private void a(World world, BlockPosition blockposition, boolean flag, boolean flag1, boolean flag2, boolean flag3) {
      if (flag1 && !flag3) {
         world.a(null, blockposition, SoundEffects.xy, SoundCategory.e, 0.4F, 0.6F);
         world.a(null, GameEvent.a, blockposition);
      } else if (!flag1 && flag3) {
         world.a(null, blockposition, SoundEffects.xx, SoundCategory.e, 0.4F, 0.5F);
         world.a(null, GameEvent.e, blockposition);
      } else if (flag && !flag2) {
         world.a(null, blockposition, SoundEffects.xw, SoundCategory.e, 0.4F, 0.7F);
         world.a(null, GameEvent.b, blockposition);
      } else if (!flag && flag2) {
         world.a(null, blockposition, SoundEffects.xz, SoundCategory.e, 0.4F, 1.2F / (world.z.i() * 0.2F + 0.9F));
         world.a(null, GameEvent.g, blockposition);
      }
   }

   private void a(World world, BlockPosition blockposition, EnumDirection enumdirection) {
      world.a(blockposition, this);
      world.a(blockposition.a(enumdirection.g()), this);
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
      if (!flag && !iblockdata.a(iblockdata1.b())) {
         boolean flag1 = iblockdata.c(c);
         boolean flag2 = iblockdata.c(b);
         if (flag1 || flag2) {
            this.a(world, blockposition, iblockdata, true, false, -1, null);
         }

         if (flag2) {
            world.a(blockposition, this);
            world.a(blockposition.a(iblockdata.c(a).g()), this);
         }

         super.a(iblockdata, world, blockposition, iblockdata1, flag);
      }
   }

   @Override
   public int a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
      return iblockdata.c(b) ? 15 : 0;
   }

   @Override
   public int b(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
      return !iblockdata.c(b) ? 0 : (iblockdata.c(a) == enumdirection ? 15 : 0);
   }

   @Override
   public boolean f_(IBlockData iblockdata) {
      return true;
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
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(a, b, c);
   }
}
