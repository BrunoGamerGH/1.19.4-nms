package net.minecraft.world.level.block;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.ParticleParamRedstone;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.plugin.PluginManager;

public class BlockRedstoneTorch extends BlockTorch {
   public static final BlockStateBoolean a = BlockProperties.r;
   private static final Map<IBlockAccess, List<BlockRedstoneTorch.RedstoneUpdateInfo>> e = new WeakHashMap<>();
   public static final int b = 60;
   public static final int c = 8;
   public static final int d = 160;
   private static final int f = 2;

   protected BlockRedstoneTorch(BlockBase.Info blockbase_info) {
      super(blockbase_info, ParticleParamRedstone.b);
      this.k(this.D.b().a(a, Boolean.valueOf(true)));
   }

   @Override
   public void b(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
      for(EnumDirection enumdirection : EnumDirection.values()) {
         world.a(blockposition.a(enumdirection), this);
      }
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
      if (!flag) {
         for(EnumDirection enumdirection : EnumDirection.values()) {
            world.a(blockposition.a(enumdirection), this);
         }
      }
   }

   @Override
   public int a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
      return iblockdata.c(a) && EnumDirection.b != enumdirection ? 15 : 0;
   }

   protected boolean a(World world, BlockPosition blockposition, IBlockData iblockdata) {
      return world.a(blockposition.d(), EnumDirection.a);
   }

   @Override
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      boolean flag = this.a(worldserver, blockposition, iblockdata);
      List list = e.get(worldserver);

      while(list != null && !list.isEmpty() && worldserver.U() - ((BlockRedstoneTorch.RedstoneUpdateInfo)list.get(0)).b > 60L) {
         list.remove(0);
      }

      PluginManager manager = worldserver.getCraftServer().getPluginManager();
      org.bukkit.block.Block block = worldserver.getWorld().getBlockAt(blockposition.u(), blockposition.v(), blockposition.w());
      int oldCurrent = iblockdata.c(a) ? 15 : 0;
      BlockRedstoneEvent event = new BlockRedstoneEvent(block, oldCurrent, oldCurrent);
      if (iblockdata.c(a)) {
         if (flag) {
            if (oldCurrent != 0) {
               event.setNewCurrent(0);
               manager.callEvent(event);
               if (event.getNewCurrent() != 0) {
                  return;
               }
            }

            worldserver.a(blockposition, iblockdata.a(a, Boolean.valueOf(false)), 3);
            if (a(worldserver, blockposition, true)) {
               worldserver.c(1502, blockposition, 0);
               worldserver.a(blockposition, worldserver.a_(blockposition).b(), 160);
            }
         }
      } else if (!flag && !a(worldserver, blockposition, false)) {
         if (oldCurrent != 15) {
            event.setNewCurrent(15);
            manager.callEvent(event);
            if (event.getNewCurrent() != 15) {
               return;
            }
         }

         worldserver.a(blockposition, iblockdata.a(a, Boolean.valueOf(true)), 3);
      }
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Block block, BlockPosition blockposition1, boolean flag) {
      if (iblockdata.c(a) == this.a(world, blockposition, iblockdata) && !world.K().b(blockposition, this)) {
         world.a(blockposition, this, 2);
      }
   }

   @Override
   public int b(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
      return enumdirection == EnumDirection.a ? iblockdata.b(iblockaccess, blockposition, enumdirection) : 0;
   }

   @Override
   public boolean f_(IBlockData iblockdata) {
      return true;
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, RandomSource randomsource) {
      if (iblockdata.c(a)) {
         double d0 = (double)blockposition.u() + 0.5 + (randomsource.j() - 0.5) * 0.2;
         double d1 = (double)blockposition.v() + 0.7 + (randomsource.j() - 0.5) * 0.2;
         double d2 = (double)blockposition.w() + 0.5 + (randomsource.j() - 0.5) * 0.2;
         world.a(this.i, d0, d1, d2, 0.0, 0.0, 0.0);
      }
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(a);
   }

   private static boolean a(World world, BlockPosition blockposition, boolean flag) {
      List<BlockRedstoneTorch.RedstoneUpdateInfo> list = e.computeIfAbsent(world, iblockaccess -> Lists.newArrayList());
      if (flag) {
         list.add(new BlockRedstoneTorch.RedstoneUpdateInfo(blockposition.i(), world.U()));
      }

      int i = 0;

      for(int j = 0; j < list.size(); ++j) {
         BlockRedstoneTorch.RedstoneUpdateInfo blockredstonetorch_redstoneupdateinfo = list.get(j);
         if (blockredstonetorch_redstoneupdateinfo.a.equals(blockposition)) {
            if (++i >= 8) {
               return true;
            }
         }
      }

      return false;
   }

   public static class RedstoneUpdateInfo {
      final BlockPosition a;
      final long b;

      public RedstoneUpdateInfo(BlockPosition blockposition, long i) {
         this.a = blockposition;
         this.b = i;
      }
   }
}
