package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ambient.EntityBat;
import net.minecraft.world.entity.animal.EntityTurtle;
import net.minecraft.world.entity.monster.EntityZombie;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class BlockTurtleEgg extends Block {
   public static final int a = 2;
   public static final int b = 1;
   public static final int c = 4;
   private static final VoxelShape f = Block.a(3.0, 0.0, 3.0, 12.0, 7.0, 12.0);
   private static final VoxelShape g = Block.a(1.0, 0.0, 1.0, 15.0, 7.0, 15.0);
   public static final BlockStateInteger d = BlockProperties.aE;
   public static final BlockStateInteger e = BlockProperties.aD;

   public BlockTurtleEgg(BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.k(this.D.b().a(d, Integer.valueOf(0)).a(e, Integer.valueOf(1)));
   }

   @Override
   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, Entity entity) {
      if (!entity.bP()) {
         this.a(world, iblockdata, blockposition, entity, 100);
      }

      super.a(world, blockposition, iblockdata, entity);
   }

   @Override
   public void a(World world, IBlockData iblockdata, BlockPosition blockposition, Entity entity, float f) {
      if (!(entity instanceof EntityZombie)) {
         this.a(world, iblockdata, blockposition, entity, 3);
      }

      super.a(world, iblockdata, blockposition, entity, f);
   }

   private void a(World world, IBlockData iblockdata, BlockPosition blockposition, Entity entity, int i) {
      if (this.a(world, entity) && !world.B && world.z.a(i) == 0 && iblockdata.a(Blocks.mc)) {
         Cancellable cancellable;
         if (entity instanceof EntityHuman) {
            cancellable = CraftEventFactory.callPlayerInteractEvent((EntityHuman)entity, Action.PHYSICAL, blockposition, null, null, null);
         } else {
            cancellable = new EntityInteractEvent(entity.getBukkitEntity(), CraftBlock.at(world, blockposition));
            world.getCraftServer().getPluginManager().callEvent((EntityInteractEvent)cancellable);
         }

         if (cancellable.isCancelled()) {
            return;
         }

         this.a(world, blockposition, iblockdata);
      }
   }

   private void a(World world, BlockPosition blockposition, IBlockData iblockdata) {
      world.a(null, blockposition, SoundEffects.xM, SoundCategory.e, 0.7F, 0.9F + world.z.i() * 0.2F);
      int i = iblockdata.c(e);
      if (i <= 1) {
         world.b(blockposition, false);
      } else {
         world.a(blockposition, iblockdata.a(e, Integer.valueOf(i - 1)), 2);
         world.a(GameEvent.f, blockposition, GameEvent.a.a(iblockdata));
         world.c(2001, blockposition, Block.i(iblockdata));
      }
   }

   @Override
   public void b(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      if (this.a(worldserver) && a(worldserver, blockposition)) {
         int i = iblockdata.c(d);
         if (i < 2) {
            if (!CraftEventFactory.handleBlockGrowEvent(worldserver, blockposition, iblockdata.a(d, Integer.valueOf(i + 1)), 2)) {
               return;
            }

            worldserver.a(null, blockposition, SoundEffects.xN, SoundCategory.e, 0.7F, 0.9F + randomsource.i() * 0.2F);
         } else {
            if (CraftEventFactory.callBlockFadeEvent(worldserver, blockposition, Blocks.a.o()).isCancelled()) {
               return;
            }

            worldserver.a(null, blockposition, SoundEffects.xO, SoundCategory.e, 0.7F, 0.9F + randomsource.i() * 0.2F);
            worldserver.a(blockposition, false);

            for(int j = 0; j < iblockdata.c(e); ++j) {
               worldserver.c(2001, blockposition, Block.i(iblockdata));
               EntityTurtle entityturtle = EntityTypes.bd.a((World)worldserver);
               if (entityturtle != null) {
                  entityturtle.c_(-24000);
                  entityturtle.g(blockposition);
                  entityturtle.b((double)blockposition.u() + 0.3 + (double)j * 0.2, (double)blockposition.v(), (double)blockposition.w() + 0.3, 0.0F, 0.0F);
                  worldserver.addFreshEntity(entityturtle, SpawnReason.EGG);
               }
            }
         }
      }
   }

   public static boolean a(IBlockAccess iblockaccess, BlockPosition blockposition) {
      return b(iblockaccess, blockposition.d());
   }

   public static boolean b(IBlockAccess iblockaccess, BlockPosition blockposition) {
      return iblockaccess.a_(blockposition).a(TagsBlock.G);
   }

   @Override
   public void b(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
      if (a(world, blockposition) && !world.B) {
         world.c(2005, blockposition, 0);
      }
   }

   private boolean a(World world) {
      float f = world.f(1.0F);
      return (double)f < 0.69 && (double)f > 0.65 ? true : world.z.a(500) == 0;
   }

   @Override
   public void a(
      World world, EntityHuman entityhuman, BlockPosition blockposition, IBlockData iblockdata, @Nullable TileEntity tileentity, ItemStack itemstack
   ) {
      super.a(world, entityhuman, blockposition, iblockdata, tileentity, itemstack);
      this.a(world, blockposition, iblockdata);
   }

   @Override
   public boolean a(IBlockData iblockdata, BlockActionContext blockactioncontext) {
      return !blockactioncontext.h() && blockactioncontext.n().a(this.k()) && iblockdata.c(e) < 4 ? true : super.a(iblockdata, blockactioncontext);
   }

   @Nullable
   @Override
   public IBlockData a(BlockActionContext blockactioncontext) {
      IBlockData iblockdata = blockactioncontext.q().a_(blockactioncontext.a());
      return iblockdata.a(this) ? iblockdata.a(e, Integer.valueOf(Math.min(4, iblockdata.c(e) + 1))) : super.a(blockactioncontext);
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return iblockdata.c(e) > 1 ? g : f;
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(d, e);
   }

   private boolean a(World world, Entity entity) {
      return entity instanceof EntityTurtle || entity instanceof EntityBat
         ? false
         : (!(entity instanceof EntityLiving) ? false : entity instanceof EntityHuman || world.W().b(GameRules.c));
   }
}
