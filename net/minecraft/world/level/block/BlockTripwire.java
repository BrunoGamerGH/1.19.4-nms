package net.minecraft.world.level.block;

import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.plugin.PluginManager;

public class BlockTripwire extends Block {
   public static final BlockStateBoolean a = BlockProperties.w;
   public static final BlockStateBoolean b = BlockProperties.a;
   public static final BlockStateBoolean c = BlockProperties.d;
   public static final BlockStateBoolean d = BlockSprawling.a;
   public static final BlockStateBoolean e = BlockSprawling.b;
   public static final BlockStateBoolean f = BlockSprawling.c;
   public static final BlockStateBoolean g = BlockSprawling.d;
   private static final Map<EnumDirection, BlockStateBoolean> j = BlockTall.f;
   protected static final VoxelShape h = Block.a(0.0, 1.0, 0.0, 16.0, 2.5, 16.0);
   protected static final VoxelShape i = Block.a(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
   private static final int k = 10;
   private final BlockTripwireHook l;

   public BlockTripwire(BlockTripwireHook blocktripwirehook, BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.k(
         this.D
            .b()
            .a(a, Boolean.valueOf(false))
            .a(b, Boolean.valueOf(false))
            .a(c, Boolean.valueOf(false))
            .a(d, Boolean.valueOf(false))
            .a(e, Boolean.valueOf(false))
            .a(f, Boolean.valueOf(false))
            .a(g, Boolean.valueOf(false))
      );
      this.l = blocktripwirehook;
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return iblockdata.c(b) ? h : i;
   }

   @Override
   public IBlockData a(BlockActionContext blockactioncontext) {
      World world = blockactioncontext.q();
      BlockPosition blockposition = blockactioncontext.a();
      return this.o()
         .a(d, Boolean.valueOf(this.a(world.a_(blockposition.e()), EnumDirection.c)))
         .a(e, Boolean.valueOf(this.a(world.a_(blockposition.h()), EnumDirection.f)))
         .a(f, Boolean.valueOf(this.a(world.a_(blockposition.f()), EnumDirection.d)))
         .a(g, Boolean.valueOf(this.a(world.a_(blockposition.g()), EnumDirection.e)));
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
      return enumdirection.o().d()
         ? iblockdata.a(j.get(enumdirection), Boolean.valueOf(this.a(iblockdata1, enumdirection)))
         : super.a(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
   }

   @Override
   public void b(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
      if (!iblockdata1.a(iblockdata.b())) {
         this.a(world, blockposition, iblockdata);
      }
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
      if (!flag && !iblockdata.a(iblockdata1.b())) {
         this.a(world, blockposition, iblockdata.a(a, Boolean.valueOf(true)));
      }
   }

   @Override
   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman) {
      if (!world.B && !entityhuman.eK().b() && entityhuman.eK().a(Items.rc)) {
         world.a(blockposition, iblockdata.a(c, Boolean.valueOf(true)), 4);
         world.a(entityhuman, GameEvent.Q, blockposition);
      }

      super.a(world, blockposition, iblockdata, entityhuman);
   }

   private void a(World world, BlockPosition blockposition, IBlockData iblockdata) {
      for(EnumDirection enumdirection : new EnumDirection[]{EnumDirection.d, EnumDirection.e}) {
         for(int k = 1; k < 42; ++k) {
            BlockPosition blockposition1 = blockposition.a(enumdirection, k);
            IBlockData iblockdata1 = world.a_(blockposition1);
            if (iblockdata1.a(this.l)) {
               if (iblockdata1.c(BlockTripwireHook.a) == enumdirection.g()) {
                  this.l.a(world, blockposition1, iblockdata1, false, true, k, iblockdata);
               }
               break;
            }

            if (!iblockdata1.a(this)) {
               break;
            }
         }
      }
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Entity entity) {
      if (!world.B && !iblockdata.c(a)) {
         this.a(world, blockposition);
      }
   }

   @Override
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      if (worldserver.a_(blockposition).c(a)) {
         this.a(worldserver, blockposition);
      }
   }

   private void a(World world, BlockPosition blockposition) {
      IBlockData iblockdata = world.a_(blockposition);
      boolean flag = iblockdata.c(a);
      boolean flag1 = false;
      List<? extends Entity> list = world.a_(null, iblockdata.j(world, blockposition).a().a(blockposition));
      if (!list.isEmpty()) {
         for(Entity entity : list) {
            if (!entity.cq()) {
               flag1 = true;
               break;
            }
         }
      }

      if (flag != flag1 && flag1 && iblockdata.c(b)) {
         org.bukkit.World bworld = world.getWorld();
         PluginManager manager = world.getCraftServer().getPluginManager();
         org.bukkit.block.Block block = bworld.getBlockAt(blockposition.u(), blockposition.v(), blockposition.w());
         boolean allowed = false;

         for(Object object : list) {
            if (object != null) {
               Cancellable cancellable;
               if (object instanceof EntityHuman) {
                  cancellable = CraftEventFactory.callPlayerInteractEvent((EntityHuman)object, Action.PHYSICAL, blockposition, null, null, null);
               } else {
                  if (!(object instanceof Entity)) {
                     continue;
                  }

                  cancellable = new EntityInteractEvent(((Entity)object).getBukkitEntity(), block);
                  manager.callEvent((EntityInteractEvent)cancellable);
               }

               if (!cancellable.isCancelled()) {
                  allowed = true;
                  break;
               }
            }
         }

         if (!allowed) {
            return;
         }
      }

      if (flag1 != flag) {
         iblockdata = iblockdata.a(a, Boolean.valueOf(flag1));
         world.a(blockposition, iblockdata, 3);
         this.a(world, blockposition, iblockdata);
      }

      if (flag1) {
         world.a(new BlockPosition(blockposition), this, 10);
      }
   }

   public boolean a(IBlockData iblockdata, EnumDirection enumdirection) {
      return iblockdata.a(this.l) ? iblockdata.c(BlockTripwireHook.a) == enumdirection.g() : iblockdata.a(this);
   }

   @Override
   public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
      switch(enumblockrotation) {
         case b:
            return iblockdata.a(d, iblockdata.c(g)).a(e, iblockdata.c(d)).a(f, iblockdata.c(e)).a(g, iblockdata.c(f));
         case c:
            return iblockdata.a(d, iblockdata.c(f)).a(e, iblockdata.c(g)).a(f, iblockdata.c(d)).a(g, iblockdata.c(e));
         case d:
            return iblockdata.a(d, iblockdata.c(e)).a(e, iblockdata.c(f)).a(f, iblockdata.c(g)).a(g, iblockdata.c(d));
         default:
            return iblockdata;
      }
   }

   @Override
   public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
      switch(enumblockmirror) {
         case b:
            return iblockdata.a(d, iblockdata.c(f)).a(f, iblockdata.c(d));
         case c:
            return iblockdata.a(e, iblockdata.c(g)).a(g, iblockdata.c(e));
         default:
            return super.a(iblockdata, enumblockmirror);
      }
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(a, b, c, d, e, g, f);
   }
}
