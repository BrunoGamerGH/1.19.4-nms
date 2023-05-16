package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.util.RandomSource;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.EntityArrow;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockPropertyAttachPosition;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.entity.EntityInteractEvent;

public class BlockButtonAbstract extends BlockAttachable {
   public static final BlockStateBoolean a = BlockProperties.w;
   private static final int K = 1;
   private static final int L = 2;
   protected static final int b = 2;
   protected static final int c = 3;
   protected static final VoxelShape d = Block.a(6.0, 14.0, 5.0, 10.0, 16.0, 11.0);
   protected static final VoxelShape e = Block.a(5.0, 14.0, 6.0, 11.0, 16.0, 10.0);
   protected static final VoxelShape f = Block.a(6.0, 0.0, 5.0, 10.0, 2.0, 11.0);
   protected static final VoxelShape g = Block.a(5.0, 0.0, 6.0, 11.0, 2.0, 10.0);
   protected static final VoxelShape h = Block.a(5.0, 6.0, 14.0, 11.0, 10.0, 16.0);
   protected static final VoxelShape i = Block.a(5.0, 6.0, 0.0, 11.0, 10.0, 2.0);
   protected static final VoxelShape j = Block.a(14.0, 6.0, 5.0, 16.0, 10.0, 11.0);
   protected static final VoxelShape k = Block.a(0.0, 6.0, 5.0, 2.0, 10.0, 11.0);
   protected static final VoxelShape l = Block.a(6.0, 15.0, 5.0, 10.0, 16.0, 11.0);
   protected static final VoxelShape m = Block.a(5.0, 15.0, 6.0, 11.0, 16.0, 10.0);
   protected static final VoxelShape n = Block.a(6.0, 0.0, 5.0, 10.0, 1.0, 11.0);
   protected static final VoxelShape E = Block.a(5.0, 0.0, 6.0, 11.0, 1.0, 10.0);
   protected static final VoxelShape F = Block.a(5.0, 6.0, 15.0, 11.0, 10.0, 16.0);
   protected static final VoxelShape G = Block.a(5.0, 6.0, 0.0, 11.0, 10.0, 1.0);
   protected static final VoxelShape H = Block.a(15.0, 6.0, 5.0, 16.0, 10.0, 11.0);
   protected static final VoxelShape I = Block.a(0.0, 6.0, 5.0, 1.0, 10.0, 11.0);
   private final BlockSetType M;
   private final int N;
   private final boolean O;

   protected BlockButtonAbstract(BlockBase.Info blockbase_info, BlockSetType blocksettype, int i, boolean flag) {
      super(blockbase_info.a(blocksettype.c()));
      this.M = blocksettype;
      this.k(this.D.b().a(aD, EnumDirection.c).a(a, Boolean.valueOf(false)).a(J, BlockPropertyAttachPosition.b));
      this.N = i;
      this.O = flag;
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      EnumDirection enumdirection = iblockdata.c(aD);
      boolean flag = iblockdata.c(a);
      switch((BlockPropertyAttachPosition)iblockdata.c(J)) {
         case a:
            if (enumdirection.o() == EnumDirection.EnumAxis.a) {
               return flag ? n : f;
            }

            return flag ? E : g;
         case b:
            return switch(enumdirection) {
               case a, b, c -> flag ? F : h;
               case d -> flag ? G : i;
               case e -> flag ? H : j;
               case f -> flag ? I : k;
            };
         case c:
         default:
            return enumdirection.o() == EnumDirection.EnumAxis.a ? (flag ? l : d) : (flag ? m : e);
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
      if (iblockdata.c(a)) {
         return EnumInteractionResult.b;
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
            this.d(iblockdata, world, blockposition);
            this.a(entityhuman, world, blockposition, true);
            world.a(entityhuman, GameEvent.a, blockposition);
            return EnumInteractionResult.a(world.B);
         }
      }
   }

   public void d(IBlockData iblockdata, World world, BlockPosition blockposition) {
      world.a(blockposition, iblockdata.a(a, Boolean.valueOf(true)), 3);
      this.f(iblockdata, world, blockposition);
      world.a(blockposition, this, this.N);
   }

   protected void a(@Nullable EntityHuman entityhuman, GeneratorAccess generatoraccess, BlockPosition blockposition, boolean flag) {
      generatoraccess.a(flag ? entityhuman : null, blockposition, this.a(flag), SoundCategory.e);
   }

   protected SoundEffect a(boolean flag) {
      return flag ? this.M.k() : this.M.j();
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
      if (!flag && !iblockdata.a(iblockdata1.b())) {
         if (iblockdata.c(a)) {
            this.f(iblockdata, world, blockposition);
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

   @Override
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      if (iblockdata.c(a)) {
         this.e(iblockdata, worldserver, blockposition);
      }
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Entity entity) {
      if (!world.B && this.O && !iblockdata.c(a)) {
         this.e(iblockdata, world, blockposition);
      }
   }

   protected void e(IBlockData iblockdata, World world, BlockPosition blockposition) {
      EntityArrow entityarrow = this.O
         ? world.a(EntityArrow.class, iblockdata.j(world, blockposition).a().a(blockposition)).stream().findFirst().orElse(null)
         : null;
      boolean flag = entityarrow != null;
      boolean flag1 = iblockdata.c(a);
      if (flag1 != flag && flag) {
         org.bukkit.block.Block block = world.getWorld().getBlockAt(blockposition.u(), blockposition.v(), blockposition.w());
         EntityInteractEvent event = new EntityInteractEvent(entityarrow.getBukkitEntity(), block);
         world.getCraftServer().getPluginManager().callEvent(event);
         if (event.isCancelled()) {
            return;
         }
      }

      if (flag != flag1) {
         org.bukkit.block.Block block = world.getWorld().getBlockAt(blockposition.u(), blockposition.v(), blockposition.w());
         int old = flag1 ? 15 : 0;
         int current = !flag1 ? 15 : 0;
         BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, old, current);
         world.getCraftServer().getPluginManager().callEvent(eventRedstone);
         if (flag && eventRedstone.getNewCurrent() <= 0 || !flag && eventRedstone.getNewCurrent() > 0) {
            return;
         }

         world.a(blockposition, iblockdata.a(a, Boolean.valueOf(flag)), 3);
         this.f(iblockdata, world, blockposition);
         this.a(null, world, blockposition, flag);
         world.a(entityarrow, flag ? GameEvent.a : GameEvent.e, blockposition);
      }

      if (flag) {
         world.a(new BlockPosition(blockposition), this, this.N);
      }
   }

   private void f(IBlockData iblockdata, World world, BlockPosition blockposition) {
      world.a(blockposition, this);
      world.a(blockposition.a(h(iblockdata).g()), this);
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(aD, a, J);
   }
}
