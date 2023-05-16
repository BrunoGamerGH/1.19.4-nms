package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.player.EntityHuman;
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
import net.minecraft.world.level.block.state.properties.BlockPropertyDoorHinge;
import net.minecraft.world.level.block.state.properties.BlockPropertyDoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateDirection;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.EnumPistonReaction;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockDoor extends Block {
   public static final BlockStateDirection a = BlockFacingHorizontal.aD;
   public static final BlockStateBoolean b = BlockProperties.u;
   public static final BlockStateEnum<BlockPropertyDoorHinge> c = BlockProperties.be;
   public static final BlockStateBoolean d = BlockProperties.w;
   public static final BlockStateEnum<BlockPropertyDoubleBlockHalf> e = BlockProperties.ae;
   protected static final float f = 3.0F;
   protected static final VoxelShape g = Block.a(0.0, 0.0, 0.0, 16.0, 16.0, 3.0);
   protected static final VoxelShape h = Block.a(0.0, 0.0, 13.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape i = Block.a(13.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape j = Block.a(0.0, 0.0, 0.0, 3.0, 16.0, 16.0);
   private final BlockSetType k;

   protected BlockDoor(BlockBase.Info blockbase_info, BlockSetType blocksettype) {
      super(blockbase_info.a(blocksettype.c()));
      this.k = blocksettype;
      this.k(
         this.D
            .b()
            .a(a, EnumDirection.c)
            .a(b, Boolean.valueOf(false))
            .a(c, BlockPropertyDoorHinge.a)
            .a(d, Boolean.valueOf(false))
            .a(e, BlockPropertyDoubleBlockHalf.b)
      );
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      EnumDirection enumdirection = iblockdata.c(a);
      boolean flag = !iblockdata.c(b);
      boolean flag1 = iblockdata.c(c) == BlockPropertyDoorHinge.b;
      switch(enumdirection) {
         case c:
            return flag ? h : (flag1 ? i : j);
         case d:
            return flag ? g : (flag1 ? j : i);
         case e:
            return flag ? i : (flag1 ? g : h);
         case f:
         default:
            return flag ? j : (flag1 ? h : g);
      }
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
      BlockPropertyDoubleBlockHalf blockpropertydoubleblockhalf = iblockdata.c(e);
      return enumdirection.o() != EnumDirection.EnumAxis.b
            || blockpropertydoubleblockhalf == BlockPropertyDoubleBlockHalf.b != (enumdirection == EnumDirection.b)
         ? (
            blockpropertydoubleblockhalf == BlockPropertyDoubleBlockHalf.b
                  && enumdirection == EnumDirection.a
                  && !iblockdata.a(generatoraccess, blockposition)
               ? Blocks.a.o()
               : super.a(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1)
         )
         : (
            iblockdata1.a(this) && iblockdata1.c(e) != blockpropertydoubleblockhalf
               ? iblockdata.a(a, iblockdata1.c(a)).a(b, iblockdata1.c(b)).a(c, iblockdata1.c(c)).a(d, iblockdata1.c(d))
               : Blocks.a.o()
         );
   }

   @Override
   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman) {
      if (!world.B && entityhuman.f()) {
         BlockTallPlant.b(world, blockposition, iblockdata, entityhuman);
      }

      super.a(world, blockposition, iblockdata, entityhuman);
   }

   @Override
   public boolean a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, PathMode pathmode) {
      switch(pathmode) {
         case a:
            return iblockdata.c(b);
         case b:
            return false;
         case c:
            return iblockdata.c(b);
         default:
            return false;
      }
   }

   @Nullable
   @Override
   public IBlockData a(BlockActionContext blockactioncontext) {
      BlockPosition blockposition = blockactioncontext.a();
      World world = blockactioncontext.q();
      if (blockposition.v() < world.ai() - 1 && world.a_(blockposition.c()).a(blockactioncontext)) {
         boolean flag = world.r(blockposition) || world.r(blockposition.c());
         return this.o()
            .a(a, blockactioncontext.g())
            .a(c, this.b(blockactioncontext))
            .a(d, Boolean.valueOf(flag))
            .a(b, Boolean.valueOf(flag))
            .a(e, BlockPropertyDoubleBlockHalf.b);
      } else {
         return null;
      }
   }

   @Override
   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, EntityLiving entityliving, ItemStack itemstack) {
      world.a(blockposition.c(), iblockdata.a(e, BlockPropertyDoubleBlockHalf.a), 3);
   }

   private BlockPropertyDoorHinge b(BlockActionContext blockactioncontext) {
      World world = blockactioncontext.q();
      BlockPosition blockposition = blockactioncontext.a();
      EnumDirection enumdirection = blockactioncontext.g();
      BlockPosition blockposition1 = blockposition.c();
      EnumDirection enumdirection1 = enumdirection.i();
      BlockPosition blockposition2 = blockposition.a(enumdirection1);
      IBlockData iblockdata = world.a_(blockposition2);
      BlockPosition blockposition3 = blockposition1.a(enumdirection1);
      IBlockData iblockdata1 = world.a_(blockposition3);
      EnumDirection enumdirection2 = enumdirection.h();
      BlockPosition blockposition4 = blockposition.a(enumdirection2);
      IBlockData iblockdata2 = world.a_(blockposition4);
      BlockPosition blockposition5 = blockposition1.a(enumdirection2);
      IBlockData iblockdata3 = world.a_(blockposition5);
      int i = (iblockdata.r(world, blockposition2) ? -1 : 0)
         + (iblockdata1.r(world, blockposition3) ? -1 : 0)
         + (iblockdata2.r(world, blockposition4) ? 1 : 0)
         + (iblockdata3.r(world, blockposition5) ? 1 : 0);
      boolean flag = iblockdata.a(this) && iblockdata.c(e) == BlockPropertyDoubleBlockHalf.b;
      boolean flag1 = iblockdata2.a(this) && iblockdata2.c(e) == BlockPropertyDoubleBlockHalf.b;
      if ((!flag || flag1) && i <= 0) {
         if ((!flag1 || flag) && i >= 0) {
            int j = enumdirection.j();
            int k = enumdirection.l();
            Vec3D vec3d = blockactioncontext.l();
            double d0 = vec3d.c - (double)blockposition.u();
            double d1 = vec3d.e - (double)blockposition.w();
            return j < 0 && !(d1 >= 0.5) || j > 0 && !(d1 <= 0.5) || k < 0 && !(d0 <= 0.5) || k > 0 && !(d0 >= 0.5)
               ? BlockPropertyDoorHinge.b
               : BlockPropertyDoorHinge.a;
         } else {
            return BlockPropertyDoorHinge.a;
         }
      } else {
         return BlockPropertyDoorHinge.b;
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
         iblockdata = iblockdata.a(b);
         world.a(blockposition, iblockdata, 10);
         this.a(entityhuman, world, blockposition, iblockdata.c(b));
         world.a(entityhuman, this.h(iblockdata) ? GameEvent.h : GameEvent.d, blockposition);
         return EnumInteractionResult.a(world.B);
      }
   }

   public boolean h(IBlockData iblockdata) {
      return iblockdata.c(b);
   }

   public void a(@Nullable Entity entity, World world, IBlockData iblockdata, BlockPosition blockposition, boolean flag) {
      if (iblockdata.a(this) && iblockdata.c(b) != flag) {
         world.a(blockposition, iblockdata.a(b, Boolean.valueOf(flag)), 10);
         this.a(entity, world, blockposition, flag);
         world.a(entity, flag ? GameEvent.h : GameEvent.d, blockposition);
      }
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Block block, BlockPosition blockposition1, boolean flag) {
      BlockPosition otherHalf = blockposition.a(iblockdata.c(e) == BlockPropertyDoubleBlockHalf.b ? EnumDirection.b : EnumDirection.a);
      org.bukkit.World bworld = world.getWorld();
      org.bukkit.block.Block bukkitBlock = bworld.getBlockAt(blockposition.u(), blockposition.v(), blockposition.w());
      org.bukkit.block.Block blockTop = bworld.getBlockAt(otherHalf.u(), otherHalf.v(), otherHalf.w());
      int power = bukkitBlock.getBlockPower();
      int powerTop = blockTop.getBlockPower();
      if (powerTop > power) {
         power = powerTop;
      }

      int oldPower = iblockdata.c(d) ? 15 : 0;
      if (oldPower == 0 ^ power == 0) {
         BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(bukkitBlock, oldPower, power);
         world.getCraftServer().getPluginManager().callEvent(eventRedstone);
         boolean flag1 = eventRedstone.getNewCurrent() > 0;
         if (flag1 != iblockdata.c(b)) {
            this.a(null, world, blockposition, flag1);
            world.a(null, flag1 ? GameEvent.h : GameEvent.d, blockposition);
         }

         world.a(blockposition, iblockdata.a(d, Boolean.valueOf(flag1)).a(b, Boolean.valueOf(flag1)), 2);
      }
   }

   @Override
   public boolean a(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
      BlockPosition blockposition1 = blockposition.d();
      IBlockData iblockdata1 = iworldreader.a_(blockposition1);
      return iblockdata.c(e) == BlockPropertyDoubleBlockHalf.b ? iblockdata1.d(iworldreader, blockposition1, EnumDirection.b) : iblockdata1.a(this);
   }

   private void a(@Nullable Entity entity, World world, BlockPosition blockposition, boolean flag) {
      world.a(entity, blockposition, flag ? this.k.e() : this.k.d(), SoundCategory.e, 1.0F, world.r_().i() * 0.1F + 0.9F);
   }

   @Override
   public EnumPistonReaction d(IBlockData iblockdata) {
      return EnumPistonReaction.b;
   }

   @Override
   public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
      return iblockdata.a(a, enumblockrotation.a(iblockdata.c(a)));
   }

   @Override
   public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
      return enumblockmirror == EnumBlockMirror.a ? iblockdata : iblockdata.a(enumblockmirror.a(iblockdata.c(a))).a(c);
   }

   @Override
   public long a(IBlockData iblockdata, BlockPosition blockposition) {
      return MathHelper.b(blockposition.u(), blockposition.c(iblockdata.c(e) == BlockPropertyDoubleBlockHalf.b ? 0 : 1).v(), blockposition.w());
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(e, a, b, c, d);
   }

   public static boolean a(World world, BlockPosition blockposition) {
      return n(world.a_(blockposition));
   }

   public static boolean n(IBlockData iblockdata) {
      return iblockdata.b() instanceof BlockDoor && (iblockdata.d() == Material.z || iblockdata.d() == Material.A);
   }
}
