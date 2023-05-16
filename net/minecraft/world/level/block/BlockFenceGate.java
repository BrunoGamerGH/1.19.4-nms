package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.tags.TagsBlock;
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
import net.minecraft.world.level.block.state.properties.BlockPropertyWood;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockFenceGate extends BlockFacingHorizontal {
   public static final BlockStateBoolean a = BlockProperties.u;
   public static final BlockStateBoolean b = BlockProperties.w;
   public static final BlockStateBoolean c = BlockProperties.q;
   protected static final VoxelShape d = Block.a(0.0, 0.0, 6.0, 16.0, 16.0, 10.0);
   protected static final VoxelShape e = Block.a(6.0, 0.0, 0.0, 10.0, 16.0, 16.0);
   protected static final VoxelShape f = Block.a(0.0, 0.0, 6.0, 16.0, 13.0, 10.0);
   protected static final VoxelShape g = Block.a(6.0, 0.0, 0.0, 10.0, 13.0, 16.0);
   protected static final VoxelShape h = Block.a(0.0, 0.0, 6.0, 16.0, 24.0, 10.0);
   protected static final VoxelShape i = Block.a(6.0, 0.0, 0.0, 10.0, 24.0, 16.0);
   protected static final VoxelShape j = Block.a(0.0, 5.0, 6.0, 16.0, 24.0, 10.0);
   protected static final VoxelShape k = Block.a(6.0, 5.0, 0.0, 10.0, 24.0, 16.0);
   protected static final VoxelShape l = VoxelShapes.a(Block.a(0.0, 5.0, 7.0, 2.0, 16.0, 9.0), Block.a(14.0, 5.0, 7.0, 16.0, 16.0, 9.0));
   protected static final VoxelShape m = VoxelShapes.a(Block.a(7.0, 5.0, 0.0, 9.0, 16.0, 2.0), Block.a(7.0, 5.0, 14.0, 9.0, 16.0, 16.0));
   protected static final VoxelShape n = VoxelShapes.a(Block.a(0.0, 2.0, 7.0, 2.0, 13.0, 9.0), Block.a(14.0, 2.0, 7.0, 16.0, 13.0, 9.0));
   protected static final VoxelShape E = VoxelShapes.a(Block.a(7.0, 2.0, 0.0, 9.0, 13.0, 2.0), Block.a(7.0, 2.0, 14.0, 9.0, 13.0, 16.0));
   private final BlockPropertyWood F;

   public BlockFenceGate(BlockBase.Info blockbase_info, BlockPropertyWood blockpropertywood) {
      super(blockbase_info.a(blockpropertywood.d()));
      this.F = blockpropertywood;
      this.k(this.D.b().a(a, Boolean.valueOf(false)).a(b, Boolean.valueOf(false)).a(c, Boolean.valueOf(false)));
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return iblockdata.c(c) ? (iblockdata.c(aD).o() == EnumDirection.EnumAxis.a ? g : f) : (iblockdata.c(aD).o() == EnumDirection.EnumAxis.a ? e : d);
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
      EnumDirection.EnumAxis enumdirection_enumaxis = enumdirection.o();
      if (iblockdata.c(aD).h().o() != enumdirection_enumaxis) {
         return super.a(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
      } else {
         boolean flag = this.h(iblockdata1) || this.h(generatoraccess.a_(blockposition.a(enumdirection.g())));
         return iblockdata.a(c, Boolean.valueOf(flag));
      }
   }

   @Override
   public VoxelShape b_(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
      return iblockdata.c(a) ? VoxelShapes.a() : (iblockdata.c(aD).o() == EnumDirection.EnumAxis.c ? j : k);
   }

   @Override
   public VoxelShape c(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return iblockdata.c(a) ? VoxelShapes.a() : (iblockdata.c(aD).o() == EnumDirection.EnumAxis.c ? h : i);
   }

   @Override
   public VoxelShape f(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
      return iblockdata.c(c) ? (iblockdata.c(aD).o() == EnumDirection.EnumAxis.a ? E : n) : (iblockdata.c(aD).o() == EnumDirection.EnumAxis.a ? m : l);
   }

   @Override
   public boolean a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, PathMode pathmode) {
      switch(pathmode) {
         case a:
            return iblockdata.c(a);
         case b:
            return false;
         case c:
            return iblockdata.c(a);
         default:
            return false;
      }
   }

   @Override
   public IBlockData a(BlockActionContext blockactioncontext) {
      World world = blockactioncontext.q();
      BlockPosition blockposition = blockactioncontext.a();
      boolean flag = world.r(blockposition);
      EnumDirection enumdirection = blockactioncontext.g();
      EnumDirection.EnumAxis enumdirection_enumaxis = enumdirection.o();
      boolean flag1 = enumdirection_enumaxis == EnumDirection.EnumAxis.c && (this.h(world.a_(blockposition.g())) || this.h(world.a_(blockposition.h())))
         || enumdirection_enumaxis == EnumDirection.EnumAxis.a && (this.h(world.a_(blockposition.e())) || this.h(world.a_(blockposition.f())));
      return this.o().a(aD, enumdirection).a(a, Boolean.valueOf(flag)).a(b, Boolean.valueOf(flag)).a(c, Boolean.valueOf(flag1));
   }

   private boolean h(IBlockData iblockdata) {
      return iblockdata.a(TagsBlock.K);
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
      if (iblockdata.c(a)) {
         iblockdata = iblockdata.a(a, Boolean.valueOf(false));
         world.a(blockposition, iblockdata, 10);
      } else {
         EnumDirection enumdirection = entityhuman.cA();
         if (iblockdata.c(aD) == enumdirection.g()) {
            iblockdata = iblockdata.a(aD, enumdirection);
         }

         iblockdata = iblockdata.a(a, Boolean.valueOf(true));
         world.a(blockposition, iblockdata, 10);
      }

      boolean flag = iblockdata.c(a);
      world.a(entityhuman, blockposition, flag ? this.F.g() : this.F.f(), SoundCategory.e, 1.0F, world.r_().i() * 0.1F + 0.9F);
      world.a(entityhuman, flag ? GameEvent.h : GameEvent.d, blockposition);
      return EnumInteractionResult.a(world.B);
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Block block, BlockPosition blockposition1, boolean flag) {
      if (!world.B) {
         boolean flag1 = world.r(blockposition);
         boolean oldPowered = iblockdata.c(b);
         if (oldPowered != flag1) {
            int newPower = flag1 ? 15 : 0;
            int oldPower = oldPowered ? 15 : 0;
            org.bukkit.block.Block bukkitBlock = CraftBlock.at(world, blockposition);
            BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(bukkitBlock, oldPower, newPower);
            world.getCraftServer().getPluginManager().callEvent(eventRedstone);
            flag1 = eventRedstone.getNewCurrent() > 0;
         }

         if (iblockdata.c(b) != flag1) {
            world.a(blockposition, iblockdata.a(b, Boolean.valueOf(flag1)).a(a, Boolean.valueOf(flag1)), 2);
            if (iblockdata.c(a) != flag1) {
               world.a(null, blockposition, flag1 ? this.F.g() : this.F.f(), SoundCategory.e, 1.0F, world.r_().i() * 0.1F + 0.9F);
               world.a(null, flag1 ? GameEvent.h : GameEvent.d, blockposition);
            }
         }
      }
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(aD, a, b, c);
   }

   public static boolean a(IBlockData iblockdata, EnumDirection enumdirection) {
      return iblockdata.c(aD).o() == enumdirection.h().o();
   }
}
