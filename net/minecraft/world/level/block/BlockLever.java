package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.ParticleParamRedstone;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.RandomSource;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockPropertyAttachPosition;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockLever extends BlockAttachable {
   public static final BlockStateBoolean a = BlockProperties.w;
   protected static final int b = 6;
   protected static final int c = 6;
   protected static final int d = 8;
   protected static final VoxelShape e = Block.a(5.0, 4.0, 10.0, 11.0, 12.0, 16.0);
   protected static final VoxelShape f = Block.a(5.0, 4.0, 0.0, 11.0, 12.0, 6.0);
   protected static final VoxelShape g = Block.a(10.0, 4.0, 5.0, 16.0, 12.0, 11.0);
   protected static final VoxelShape h = Block.a(0.0, 4.0, 5.0, 6.0, 12.0, 11.0);
   protected static final VoxelShape i = Block.a(5.0, 0.0, 4.0, 11.0, 6.0, 12.0);
   protected static final VoxelShape j = Block.a(4.0, 0.0, 5.0, 12.0, 6.0, 11.0);
   protected static final VoxelShape k = Block.a(5.0, 10.0, 4.0, 11.0, 16.0, 12.0);
   protected static final VoxelShape l = Block.a(4.0, 10.0, 5.0, 12.0, 16.0, 11.0);

   protected BlockLever(BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.k(this.D.b().a(aD, EnumDirection.c).a(a, Boolean.valueOf(false)).a(J, BlockPropertyAttachPosition.b));
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      switch((BlockPropertyAttachPosition)iblockdata.c(J)) {
         case a:
            switch(iblockdata.c(aD).o()) {
               case a:
                  return j;
               case b:
               case c:
               default:
                  return i;
            }
         case b:
            switch((EnumDirection)iblockdata.c(aD)) {
               case c:
               default:
                  return e;
               case d:
                  return f;
               case e:
                  return g;
               case f:
                  return h;
            }
         case c:
         default:
            switch(iblockdata.c(aD).o()) {
               case a:
                  return l;
               case b:
               case c:
               default:
                  return k;
            }
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
      if (world.B) {
         IBlockData iblockdata1 = iblockdata.a(a);
         if (iblockdata1.c(a)) {
            a(iblockdata1, world, blockposition, 1.0F);
         }

         return EnumInteractionResult.a;
      } else {
         boolean powered = iblockdata.c(a);
         org.bukkit.block.Block block = world.getWorld().getBlockAt(blockposition.u(), blockposition.v(), blockposition.w());
         int old = powered ? 15 : 0;
         int current = !powered ? 15 : 0;
         BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, old, current);
         world.getCraftServer().getPluginManager().callEvent(eventRedstone);
         if (eventRedstone.getNewCurrent() > 0 != !powered) {
            return EnumInteractionResult.a;
         } else {
            IBlockData iblockdata1 = this.d(iblockdata, world, blockposition);
            float f = iblockdata1.c(a) ? 0.6F : 0.5F;
            world.a(null, blockposition, SoundEffects.mj, SoundCategory.e, 0.3F, f);
            world.a(entityhuman, iblockdata1.c(a) ? GameEvent.a : GameEvent.e, blockposition);
            return EnumInteractionResult.b;
         }
      }
   }

   public IBlockData d(IBlockData iblockdata, World world, BlockPosition blockposition) {
      iblockdata = iblockdata.a(a);
      world.a(blockposition, iblockdata, 3);
      this.e(iblockdata, world, blockposition);
      return iblockdata;
   }

   private static void a(IBlockData iblockdata, GeneratorAccess generatoraccess, BlockPosition blockposition, float f) {
      EnumDirection enumdirection = iblockdata.c(aD).g();
      EnumDirection enumdirection1 = h(iblockdata).g();
      double d0 = (double)blockposition.u() + 0.5 + 0.1 * (double)enumdirection.j() + 0.2 * (double)enumdirection1.j();
      double d1 = (double)blockposition.v() + 0.5 + 0.1 * (double)enumdirection.k() + 0.2 * (double)enumdirection1.k();
      double d2 = (double)blockposition.w() + 0.5 + 0.1 * (double)enumdirection.l() + 0.2 * (double)enumdirection1.l();
      generatoraccess.a(new ParticleParamRedstone(ParticleParamRedstone.a, f), d0, d1, d2, 0.0, 0.0, 0.0);
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, RandomSource randomsource) {
      if (iblockdata.c(a) && randomsource.i() < 0.25F) {
         a(iblockdata, world, blockposition, 0.5F);
      }
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
      if (!flag && !iblockdata.a(iblockdata1.b())) {
         if (iblockdata.c(a)) {
            this.e(iblockdata, world, blockposition);
         }

         super.a(iblockdata, world, blockposition, iblockdata1, flag);
      }
   }

   @Override
   public int a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
      return iblockdata.c(a) ? 15 : 0;
   }

   @Override
   public int b(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
      return iblockdata.c(a) && h(iblockdata) == enumdirection ? 15 : 0;
   }

   @Override
   public boolean f_(IBlockData iblockdata) {
      return true;
   }

   private void e(IBlockData iblockdata, World world, BlockPosition blockposition) {
      world.a(blockposition, this);
      world.a(blockposition.a(h(iblockdata).g()), this);
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(J, aD, a);
   }
}
