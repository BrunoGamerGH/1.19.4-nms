package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockPropertyHalf;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockTrapdoor extends BlockFacingHorizontal implements IBlockWaterlogged {
   public static final BlockStateBoolean a = BlockProperties.u;
   public static final BlockStateEnum<BlockPropertyHalf> b = BlockProperties.af;
   public static final BlockStateBoolean c = BlockProperties.w;
   public static final BlockStateBoolean d = BlockProperties.C;
   protected static final int e = 3;
   protected static final VoxelShape f = Block.a(0.0, 0.0, 0.0, 3.0, 16.0, 16.0);
   protected static final VoxelShape g = Block.a(13.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape h = Block.a(0.0, 0.0, 0.0, 16.0, 16.0, 3.0);
   protected static final VoxelShape i = Block.a(0.0, 0.0, 13.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape j = Block.a(0.0, 0.0, 0.0, 16.0, 3.0, 16.0);
   protected static final VoxelShape k = Block.a(0.0, 13.0, 0.0, 16.0, 16.0, 16.0);
   private final BlockSetType l;

   protected BlockTrapdoor(BlockBase.Info blockbase_info, BlockSetType blocksettype) {
      super(blockbase_info.a(blocksettype.c()));
      this.l = blocksettype;
      this.k(
         this.D.b().a(aD, EnumDirection.c).a(a, Boolean.valueOf(false)).a(b, BlockPropertyHalf.b).a(c, Boolean.valueOf(false)).a(d, Boolean.valueOf(false))
      );
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      if (!iblockdata.c(a)) {
         return iblockdata.c(b) == BlockPropertyHalf.a ? k : j;
      } else {
         switch((EnumDirection)iblockdata.c(aD)) {
            case c:
            default:
               return i;
            case d:
               return h;
            case e:
               return g;
            case f:
               return f;
         }
      }
   }

   @Override
   public boolean a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, PathMode pathmode) {
      switch(pathmode) {
         case a:
            return iblockdata.c(a);
         case b:
            return iblockdata.c(d);
         case c:
            return iblockdata.c(a);
         default:
            return false;
      }
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
      if (this.aF == Material.K) {
         return EnumInteractionResult.d;
      } else {
         iblockdata = iblockdata.a(a);
         world.a(blockposition, iblockdata, 2);
         if (iblockdata.c(d)) {
            world.a(blockposition, FluidTypes.c, FluidTypes.c.a(world));
         }

         this.a(entityhuman, world, blockposition, iblockdata.c(a));
         return EnumInteractionResult.a(world.B);
      }
   }

   protected void a(@Nullable EntityHuman entityhuman, World world, BlockPosition blockposition, boolean flag) {
      world.a(entityhuman, blockposition, flag ? this.l.g() : this.l.f(), SoundCategory.e, 1.0F, world.r_().i() * 0.1F + 0.9F);
      world.a(entityhuman, flag ? GameEvent.h : GameEvent.d, blockposition);
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Block block, BlockPosition blockposition1, boolean flag) {
      if (!world.B) {
         boolean flag1 = world.r(blockposition);
         if (flag1 != iblockdata.c(c)) {
            org.bukkit.World bworld = world.getWorld();
            org.bukkit.block.Block bblock = bworld.getBlockAt(blockposition.u(), blockposition.v(), blockposition.w());
            int power = bblock.getBlockPower();
            int oldPower = iblockdata.c(a) ? 15 : 0;
            if (oldPower == 0 ^ power == 0 || block.o().j()) {
               BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(bblock, oldPower, power);
               world.getCraftServer().getPluginManager().callEvent(eventRedstone);
               flag1 = eventRedstone.getNewCurrent() > 0;
            }

            if (iblockdata.c(a) != flag1) {
               iblockdata = iblockdata.a(a, Boolean.valueOf(flag1));
               this.a(null, world, blockposition, flag1);
            }

            world.a(blockposition, iblockdata.a(c, Boolean.valueOf(flag1)), 2);
            if (iblockdata.c(d)) {
               world.a(blockposition, FluidTypes.c, FluidTypes.c.a(world));
            }
         }
      }
   }

   @Override
   public IBlockData a(BlockActionContext blockactioncontext) {
      IBlockData iblockdata = this.o();
      Fluid fluid = blockactioncontext.q().b_(blockactioncontext.a());
      EnumDirection enumdirection = blockactioncontext.k();
      if (!blockactioncontext.c() && enumdirection.o().d()) {
         iblockdata = iblockdata.a(aD, enumdirection)
            .a(b, blockactioncontext.l().d - (double)blockactioncontext.a().v() > 0.5 ? BlockPropertyHalf.a : BlockPropertyHalf.b);
      } else {
         iblockdata = iblockdata.a(aD, blockactioncontext.g().g()).a(b, enumdirection == EnumDirection.b ? BlockPropertyHalf.b : BlockPropertyHalf.a);
      }

      if (blockactioncontext.q().r(blockactioncontext.a())) {
         iblockdata = iblockdata.a(a, Boolean.valueOf(true)).a(c, Boolean.valueOf(true));
      }

      return iblockdata.a(d, Boolean.valueOf(fluid.a() == FluidTypes.c));
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(aD, a, b, c, d);
   }

   @Override
   public Fluid c_(IBlockData iblockdata) {
      return iblockdata.c(d) ? FluidTypes.c.a(false) : super.c_(iblockdata);
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
      if (iblockdata.c(d)) {
         generatoraccess.a(blockposition, FluidTypes.c, FluidTypes.c.a(generatoraccess));
      }

      return super.a(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
   }
}
