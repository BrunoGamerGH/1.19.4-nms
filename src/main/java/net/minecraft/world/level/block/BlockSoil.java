package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagsFluid;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.piston.BlockPistonMoving;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityInteractEvent;

public class BlockSoil extends Block {
   public static final BlockStateInteger a = BlockProperties.aQ;
   protected static final VoxelShape b = Block.a(0.0, 0.0, 0.0, 16.0, 15.0, 16.0);
   public static final int c = 7;

   protected BlockSoil(BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.k(this.D.b().a(a, Integer.valueOf(0)));
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
      if (enumdirection == EnumDirection.b && !iblockdata.a(generatoraccess, blockposition)) {
         generatoraccess.a(blockposition, this, 1);
      }

      return super.a(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
   }

   @Override
   public boolean a(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
      IBlockData iblockdata1 = iworldreader.a_(blockposition.c());
      return !iblockdata1.d().b() || iblockdata1.b() instanceof BlockFenceGate || iblockdata1.b() instanceof BlockPistonMoving;
   }

   @Override
   public IBlockData a(BlockActionContext blockactioncontext) {
      return !this.o().a((IWorldReader)blockactioncontext.q(), blockactioncontext.a()) ? Blocks.j.o() : super.a(blockactioncontext);
   }

   @Override
   public boolean g_(IBlockData iblockdata) {
      return true;
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return b;
   }

   @Override
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      if (!iblockdata.a(worldserver, blockposition)) {
         a(null, iblockdata, worldserver, blockposition);
      }
   }

   @Override
   public void b(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      int i = iblockdata.c(a);
      if (!a((IWorldReader)worldserver, blockposition) && !worldserver.t(blockposition.c())) {
         if (i > 0) {
            CraftEventFactory.handleMoistureChangeEvent(worldserver, blockposition, iblockdata.a(a, Integer.valueOf(i - 1)), 2);
         } else if (!a((IBlockAccess)worldserver, blockposition)) {
            a(null, iblockdata, worldserver, blockposition);
         }
      } else if (i < 7) {
         CraftEventFactory.handleMoistureChangeEvent(worldserver, blockposition, iblockdata.a(a, Integer.valueOf(7)), 2);
      }
   }

   @Override
   public void a(World world, IBlockData iblockdata, BlockPosition blockposition, Entity entity, float f) {
      super.a(world, iblockdata, blockposition, entity, f);
      if (!world.B
         && world.z.i() < f - 0.5F
         && entity instanceof EntityLiving
         && (entity instanceof EntityHuman || world.W().b(GameRules.c))
         && entity.dc() * entity.dc() * entity.dd() > 0.512F) {
         Cancellable cancellable;
         if (entity instanceof EntityHuman) {
            cancellable = CraftEventFactory.callPlayerInteractEvent((EntityHuman)entity, Action.PHYSICAL, blockposition, null, null, null);
         } else {
            cancellable = new EntityInteractEvent(
               entity.getBukkitEntity(), world.getWorld().getBlockAt(blockposition.u(), blockposition.v(), blockposition.w())
            );
            world.getCraftServer().getPluginManager().callEvent((EntityInteractEvent)cancellable);
         }

         if (cancellable.isCancelled()) {
            return;
         }

         if (CraftEventFactory.callEntityChangeBlockEvent(entity, blockposition, Blocks.j.o()).isCancelled()) {
            return;
         }

         a(entity, iblockdata, world, blockposition);
      }
   }

   public static void a(@Nullable Entity entity, IBlockData iblockdata, World world, BlockPosition blockposition) {
      if (!CraftEventFactory.callBlockFadeEvent(world, blockposition, Blocks.j.o()).isCancelled()) {
         IBlockData iblockdata1 = a(iblockdata, Blocks.j.o(), world, blockposition);
         world.b(blockposition, iblockdata1);
         world.a(GameEvent.c, blockposition, GameEvent.a.a(entity, iblockdata1));
      }
   }

   private static boolean a(IBlockAccess iblockaccess, BlockPosition blockposition) {
      Block block = iblockaccess.a_(blockposition.c()).b();
      return block instanceof BlockCrops || block instanceof BlockStem || block instanceof BlockStemAttached;
   }

   private static boolean a(IWorldReader iworldreader, BlockPosition blockposition) {
      for(BlockPosition blockposition1 : BlockPosition.a(blockposition.b(-4, 0, -4), blockposition.b(4, 1, 4))) {
         if (iworldreader.b_(blockposition1).a(TagsFluid.a)) {
            return true;
         }
      }

      return false;
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(a);
   }

   @Override
   public boolean a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, PathMode pathmode) {
      return false;
   }
}
