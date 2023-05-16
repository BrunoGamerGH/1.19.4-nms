package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlockState;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlockStates;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockFadeEvent;

public class BlockFire extends BlockFireAbstract {
   public static final int c = 15;
   public static final BlockStateInteger d = BlockProperties.aw;
   public static final BlockStateBoolean e = BlockSprawling.a;
   public static final BlockStateBoolean f = BlockSprawling.b;
   public static final BlockStateBoolean g = BlockSprawling.c;
   public static final BlockStateBoolean h = BlockSprawling.d;
   public static final BlockStateBoolean i = BlockSprawling.e;
   private static final Map<EnumDirection, BlockStateBoolean> j = BlockSprawling.g
      .entrySet()
      .stream()
      .filter(entry -> entry.getKey() != EnumDirection.a)
      .collect(SystemUtils.a());
   private static final VoxelShape k = Block.a(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
   private static final VoxelShape l = Block.a(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
   private static final VoxelShape m = Block.a(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   private static final VoxelShape n = Block.a(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
   private static final VoxelShape E = Block.a(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
   private final Map<IBlockData, VoxelShape> F;
   private static final int G = 60;
   private static final int H = 30;
   private static final int I = 15;
   private static final int J = 5;
   private static final int K = 100;
   private static final int L = 60;
   private static final int M = 20;
   private static final int N = 5;
   public final Object2IntMap<Block> O = new Object2IntOpenHashMap();
   private final Object2IntMap<Block> P = new Object2IntOpenHashMap();

   public BlockFire(BlockBase.Info blockbase_info) {
      super(blockbase_info, 1.0F);
      this.k(
         this.D
            .b()
            .a(d, Integer.valueOf(0))
            .a(e, Boolean.valueOf(false))
            .a(f, Boolean.valueOf(false))
            .a(g, Boolean.valueOf(false))
            .a(h, Boolean.valueOf(false))
            .a(i, Boolean.valueOf(false))
      );
      this.F = ImmutableMap.copyOf(this.D.a().stream().filter(iblockdata -> iblockdata.c(d) == 0).collect(Collectors.toMap(Function.identity(), BlockFire::h)));
   }

   private static VoxelShape h(IBlockData iblockdata) {
      VoxelShape voxelshape = VoxelShapes.a();
      if (iblockdata.c(i)) {
         voxelshape = k;
      }

      if (iblockdata.c(e)) {
         voxelshape = VoxelShapes.a(voxelshape, n);
      }

      if (iblockdata.c(g)) {
         voxelshape = VoxelShapes.a(voxelshape, E);
      }

      if (iblockdata.c(f)) {
         voxelshape = VoxelShapes.a(voxelshape, m);
      }

      if (iblockdata.c(h)) {
         voxelshape = VoxelShapes.a(voxelshape, l);
      }

      return voxelshape.b() ? b : voxelshape;
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
      if (!this.a(iblockdata, generatoraccess, blockposition)) {
         if (!(generatoraccess instanceof World)) {
            return Blocks.a.o();
         }

         CraftBlockState blockState = CraftBlockStates.getBlockState(generatoraccess, blockposition);
         blockState.setData(Blocks.a.o());
         BlockFadeEvent event = new BlockFadeEvent(blockState.getBlock(), blockState);
         ((World)generatoraccess).getCraftServer().getPluginManager().callEvent(event);
         if (!event.isCancelled()) {
            return blockState.getHandle();
         }
      }

      return this.a(generatoraccess, blockposition, iblockdata.c(d));
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return this.F.get(iblockdata.a(d, Integer.valueOf(0)));
   }

   @Override
   public IBlockData a(BlockActionContext blockactioncontext) {
      return this.b(blockactioncontext.q(), blockactioncontext.a());
   }

   protected IBlockData b(IBlockAccess iblockaccess, BlockPosition blockposition) {
      BlockPosition blockposition1 = blockposition.d();
      IBlockData iblockdata = iblockaccess.a_(blockposition1);
      if (!this.f(iblockdata) && !iblockdata.d(iblockaccess, blockposition1, EnumDirection.b)) {
         IBlockData iblockdata1 = this.o();

         for(EnumDirection enumdirection : EnumDirection.values()) {
            BlockStateBoolean blockstateboolean = BlockFire.j.get(enumdirection);
            if (blockstateboolean != null) {
               iblockdata1 = iblockdata1.a(blockstateboolean, Boolean.valueOf(this.f(iblockaccess.a_(blockposition.a(enumdirection)))));
            }
         }

         return iblockdata1;
      } else {
         return this.o();
      }
   }

   @Override
   public boolean a(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
      BlockPosition blockposition1 = blockposition.d();
      return iworldreader.a_(blockposition1).d(iworldreader, blockposition1, EnumDirection.b) || this.d(iworldreader, blockposition);
   }

   @Override
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      worldserver.a(blockposition, this, a(worldserver.z));
      if (worldserver.W().b(GameRules.b)) {
         if (!iblockdata.a(worldserver, blockposition)) {
            this.fireExtinguished(worldserver, blockposition);
         }

         IBlockData iblockdata1 = worldserver.a_(blockposition.d());
         boolean flag = iblockdata1.a(worldserver.q_().q());
         int i = iblockdata.c(d);
         if (!flag && worldserver.Y() && this.a((World)worldserver, blockposition) && randomsource.i() < 0.2F + (float)i * 0.03F) {
            this.fireExtinguished(worldserver, blockposition);
         } else {
            int j = Math.min(15, i + randomsource.a(3) / 2);
            if (i != j) {
               iblockdata = iblockdata.a(d, Integer.valueOf(j));
               worldserver.a(blockposition, iblockdata, 4);
            }

            if (!flag) {
               if (!this.d(worldserver, blockposition)) {
                  BlockPosition blockposition1 = blockposition.d();
                  if (!worldserver.a_(blockposition1).d(worldserver, blockposition1, EnumDirection.b) || i > 3) {
                     this.fireExtinguished(worldserver, blockposition);
                  }

                  return;
               }

               if (i == 15 && randomsource.a(4) == 0 && !this.f(worldserver.a_(blockposition.d()))) {
                  this.fireExtinguished(worldserver, blockposition);
                  return;
               }
            }

            boolean flag1 = worldserver.v(blockposition).a(BiomeTags.ab);
            int k = flag1 ? -50 : 0;
            this.trySpread(worldserver, blockposition.h(), 300 + k, randomsource, i, blockposition);
            this.trySpread(worldserver, blockposition.g(), 300 + k, randomsource, i, blockposition);
            this.trySpread(worldserver, blockposition.d(), 250 + k, randomsource, i, blockposition);
            this.trySpread(worldserver, blockposition.c(), 250 + k, randomsource, i, blockposition);
            this.trySpread(worldserver, blockposition.e(), 300 + k, randomsource, i, blockposition);
            this.trySpread(worldserver, blockposition.f(), 300 + k, randomsource, i, blockposition);
            BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();

            for(int l = -1; l <= 1; ++l) {
               for(int i1 = -1; i1 <= 1; ++i1) {
                  for(int j1 = -1; j1 <= 4; ++j1) {
                     if (l != 0 || j1 != 0 || i1 != 0) {
                        int k1 = 100;
                        if (j1 > 1) {
                           k1 += (j1 - 1) * 100;
                        }

                        blockposition_mutableblockposition.a(blockposition, l, j1, i1);
                        int l1 = this.a((IWorldReader)worldserver, blockposition_mutableblockposition);
                        if (l1 > 0) {
                           int i2 = (l1 + 40 + worldserver.ah().a() * 7) / (i + 30);
                           if (flag1) {
                              i2 /= 2;
                           }

                           if (i2 > 0 && randomsource.a(k1) <= i2 && (!worldserver.Y() || !this.a((World)worldserver, blockposition_mutableblockposition))) {
                              int j2 = Math.min(15, i + randomsource.a(5) / 4);
                              if (worldserver.a_(blockposition_mutableblockposition).b() != Blocks.cq
                                 && !CraftEventFactory.callBlockIgniteEvent(worldserver, blockposition_mutableblockposition, blockposition).isCancelled()) {
                                 CraftEventFactory.handleBlockSpreadEvent(
                                    worldserver,
                                    blockposition,
                                    blockposition_mutableblockposition,
                                    this.a(worldserver, blockposition_mutableblockposition, j2),
                                    3
                                 );
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   protected boolean a(World world, BlockPosition blockposition) {
      return world.t(blockposition) || world.t(blockposition.g()) || world.t(blockposition.h()) || world.t(blockposition.e()) || world.t(blockposition.f());
   }

   private int n(IBlockData iblockdata) {
      return iblockdata.b(BlockProperties.C) && iblockdata.c(BlockProperties.C) ? 0 : this.P.getInt(iblockdata.b());
   }

   private int o(IBlockData iblockdata) {
      return iblockdata.b(BlockProperties.C) && iblockdata.c(BlockProperties.C) ? 0 : this.O.getInt(iblockdata.b());
   }

   private void trySpread(World world, BlockPosition blockposition, int i, RandomSource randomsource, int j, BlockPosition sourceposition) {
      int k = this.n(world.a_(blockposition));
      if (randomsource.a(i) < k) {
         IBlockData iblockdata = world.a_(blockposition);
         org.bukkit.block.Block theBlock = world.getWorld().getBlockAt(blockposition.u(), blockposition.v(), blockposition.w());
         org.bukkit.block.Block sourceBlock = world.getWorld().getBlockAt(sourceposition.u(), sourceposition.v(), sourceposition.w());
         BlockBurnEvent event = new BlockBurnEvent(theBlock, sourceBlock);
         world.getCraftServer().getPluginManager().callEvent(event);
         if (event.isCancelled()) {
            return;
         }

         if (randomsource.a(j + 10) < 5 && !world.t(blockposition)) {
            int l = Math.min(j + randomsource.a(5) / 4, 15);
            world.a(blockposition, this.a(world, blockposition, l), 3);
         } else {
            world.a(blockposition, false);
         }

         Block block = iblockdata.b();
         if (block instanceof BlockTNT) {
            BlockTNT.a(world, blockposition);
         }
      }
   }

   private IBlockData a(GeneratorAccess generatoraccess, BlockPosition blockposition, int i) {
      IBlockData iblockdata = a(generatoraccess, blockposition);
      return iblockdata.a(Blocks.cq) ? iblockdata.a(d, Integer.valueOf(i)) : iblockdata;
   }

   private boolean d(IBlockAccess iblockaccess, BlockPosition blockposition) {
      for(EnumDirection enumdirection : EnumDirection.values()) {
         if (this.f(iblockaccess.a_(blockposition.a(enumdirection)))) {
            return true;
         }
      }

      return false;
   }

   private int a(IWorldReader iworldreader, BlockPosition blockposition) {
      if (!iworldreader.w(blockposition)) {
         return 0;
      } else {
         int i = 0;

         for(EnumDirection enumdirection : EnumDirection.values()) {
            IBlockData iblockdata = iworldreader.a_(blockposition.a(enumdirection));
            i = Math.max(this.o(iblockdata), i);
         }

         return i;
      }
   }

   @Override
   protected boolean f(IBlockData iblockdata) {
      return this.o(iblockdata) > 0;
   }

   @Override
   public void b(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
      super.b(iblockdata, world, blockposition, iblockdata1, flag);
      world.a(blockposition, this, a(world.z));
   }

   private static int a(RandomSource randomsource) {
      return 30 + randomsource.a(10);
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(d, e, f, g, h, i);
   }

   private void a(Block block, int i, int j) {
      this.O.put(block, i);
      this.P.put(block, j);
   }

   public static void b() {
      BlockFire blockfire = (BlockFire)Blocks.cq;
      blockfire.a(Blocks.n, 5, 20);
      blockfire.a(Blocks.o, 5, 20);
      blockfire.a(Blocks.p, 5, 20);
      blockfire.a(Blocks.q, 5, 20);
      blockfire.a(Blocks.r, 5, 20);
      blockfire.a(Blocks.s, 5, 20);
      blockfire.a(Blocks.t, 5, 20);
      blockfire.a(Blocks.u, 5, 20);
      blockfire.a(Blocks.v, 5, 20);
      blockfire.a(Blocks.w, 5, 20);
      blockfire.a(Blocks.js, 5, 20);
      blockfire.a(Blocks.jt, 5, 20);
      blockfire.a(Blocks.ju, 5, 20);
      blockfire.a(Blocks.jv, 5, 20);
      blockfire.a(Blocks.jw, 5, 20);
      blockfire.a(Blocks.jx, 5, 20);
      blockfire.a(Blocks.jy, 5, 20);
      blockfire.a(Blocks.jz, 5, 20);
      blockfire.a(Blocks.jA, 5, 20);
      blockfire.a(Blocks.jB, 5, 20);
      blockfire.a(Blocks.fg, 5, 20);
      blockfire.a(Blocks.jU, 5, 20);
      blockfire.a(Blocks.jV, 5, 20);
      blockfire.a(Blocks.jW, 5, 20);
      blockfire.a(Blocks.jX, 5, 20);
      blockfire.a(Blocks.jY, 5, 20);
      blockfire.a(Blocks.jZ, 5, 20);
      blockfire.a(Blocks.ka, 5, 20);
      blockfire.a(Blocks.kb, 5, 20);
      blockfire.a(Blocks.dT, 5, 20);
      blockfire.a(Blocks.kc, 5, 20);
      blockfire.a(Blocks.kd, 5, 20);
      blockfire.a(Blocks.ke, 5, 20);
      blockfire.a(Blocks.kf, 5, 20);
      blockfire.a(Blocks.kg, 5, 20);
      blockfire.a(Blocks.kh, 5, 20);
      blockfire.a(Blocks.ki, 5, 20);
      blockfire.a(Blocks.kj, 5, 20);
      blockfire.a(Blocks.ct, 5, 20);
      blockfire.a(Blocks.fK, 5, 20);
      blockfire.a(Blocks.fJ, 5, 20);
      blockfire.a(Blocks.fL, 5, 20);
      blockfire.a(Blocks.hO, 5, 20);
      blockfire.a(Blocks.hP, 5, 20);
      blockfire.a(Blocks.hQ, 5, 20);
      blockfire.a(Blocks.hR, 5, 20);
      blockfire.a(Blocks.hS, 5, 20);
      blockfire.a(Blocks.hT, 5, 20);
      blockfire.a(Blocks.T, 5, 5);
      blockfire.a(Blocks.U, 5, 5);
      blockfire.a(Blocks.V, 5, 5);
      blockfire.a(Blocks.W, 5, 5);
      blockfire.a(Blocks.X, 5, 5);
      blockfire.a(Blocks.Y, 5, 5);
      blockfire.a(Blocks.Z, 5, 5);
      blockfire.a(Blocks.aa, 5, 5);
      blockfire.a(Blocks.ad, 5, 5);
      blockfire.a(Blocks.ak, 5, 5);
      blockfire.a(Blocks.ae, 5, 5);
      blockfire.a(Blocks.af, 5, 5);
      blockfire.a(Blocks.ag, 5, 5);
      blockfire.a(Blocks.ah, 5, 5);
      blockfire.a(Blocks.ai, 5, 5);
      blockfire.a(Blocks.aj, 5, 5);
      blockfire.a(Blocks.al, 5, 5);
      blockfire.a(Blocks.am, 5, 5);
      blockfire.a(Blocks.av, 5, 5);
      blockfire.a(Blocks.aw, 5, 5);
      blockfire.a(Blocks.ax, 5, 5);
      blockfire.a(Blocks.ay, 5, 5);
      blockfire.a(Blocks.az, 5, 5);
      blockfire.a(Blocks.aA, 5, 5);
      blockfire.a(Blocks.aB, 5, 5);
      blockfire.a(Blocks.aC, 5, 5);
      blockfire.a(Blocks.an, 5, 5);
      blockfire.a(Blocks.ao, 5, 5);
      blockfire.a(Blocks.ap, 5, 5);
      blockfire.a(Blocks.aq, 5, 5);
      blockfire.a(Blocks.ar, 5, 5);
      blockfire.a(Blocks.as, 5, 5);
      blockfire.a(Blocks.at, 5, 5);
      blockfire.a(Blocks.au, 5, 5);
      blockfire.a(Blocks.ab, 5, 20);
      blockfire.a(Blocks.aD, 30, 60);
      blockfire.a(Blocks.aE, 30, 60);
      blockfire.a(Blocks.aF, 30, 60);
      blockfire.a(Blocks.aG, 30, 60);
      blockfire.a(Blocks.aH, 30, 60);
      blockfire.a(Blocks.aI, 30, 60);
      blockfire.a(Blocks.aJ, 30, 60);
      blockfire.a(Blocks.aK, 30, 60);
      blockfire.a(Blocks.ck, 30, 20);
      blockfire.a(Blocks.cj, 15, 100);
      blockfire.a(Blocks.bs, 60, 100);
      blockfire.a(Blocks.bt, 60, 100);
      blockfire.a(Blocks.bu, 60, 100);
      blockfire.a(Blocks.iC, 60, 100);
      blockfire.a(Blocks.iD, 60, 100);
      blockfire.a(Blocks.iE, 60, 100);
      blockfire.a(Blocks.iF, 60, 100);
      blockfire.a(Blocks.iG, 60, 100);
      blockfire.a(Blocks.iH, 60, 100);
      blockfire.a(Blocks.bQ, 60, 100);
      blockfire.a(Blocks.bS, 60, 100);
      blockfire.a(Blocks.bT, 60, 100);
      blockfire.a(Blocks.bU, 60, 100);
      blockfire.a(Blocks.bV, 60, 100);
      blockfire.a(Blocks.bW, 60, 100);
      blockfire.a(Blocks.bX, 60, 100);
      blockfire.a(Blocks.bY, 60, 100);
      blockfire.a(Blocks.bZ, 60, 100);
      blockfire.a(Blocks.ca, 60, 100);
      blockfire.a(Blocks.cb, 60, 100);
      blockfire.a(Blocks.cd, 60, 100);
      blockfire.a(Blocks.cc, 60, 100);
      blockfire.a(Blocks.rv, 60, 100);
      blockfire.a(Blocks.bz, 30, 60);
      blockfire.a(Blocks.bA, 30, 60);
      blockfire.a(Blocks.bB, 30, 60);
      blockfire.a(Blocks.bC, 30, 60);
      blockfire.a(Blocks.bD, 30, 60);
      blockfire.a(Blocks.bE, 30, 60);
      blockfire.a(Blocks.bF, 30, 60);
      blockfire.a(Blocks.bG, 30, 60);
      blockfire.a(Blocks.bH, 30, 60);
      blockfire.a(Blocks.bI, 30, 60);
      blockfire.a(Blocks.bJ, 30, 60);
      blockfire.a(Blocks.bK, 30, 60);
      blockfire.a(Blocks.bL, 30, 60);
      blockfire.a(Blocks.bM, 30, 60);
      blockfire.a(Blocks.bN, 30, 60);
      blockfire.a(Blocks.bO, 30, 60);
      blockfire.a(Blocks.fe, 15, 100);
      blockfire.a(Blocks.iA, 5, 5);
      blockfire.a(Blocks.ii, 60, 20);
      blockfire.a(Blocks.oZ, 15, 20);
      blockfire.a(Blocks.ij, 60, 20);
      blockfire.a(Blocks.ik, 60, 20);
      blockfire.a(Blocks.il, 60, 20);
      blockfire.a(Blocks.im, 60, 20);
      blockfire.a(Blocks.in, 60, 20);
      blockfire.a(Blocks.io, 60, 20);
      blockfire.a(Blocks.ip, 60, 20);
      blockfire.a(Blocks.iq, 60, 20);
      blockfire.a(Blocks.ir, 60, 20);
      blockfire.a(Blocks.is, 60, 20);
      blockfire.a(Blocks.it, 60, 20);
      blockfire.a(Blocks.iu, 60, 20);
      blockfire.a(Blocks.iv, 60, 20);
      blockfire.a(Blocks.iw, 60, 20);
      blockfire.a(Blocks.ix, 60, 20);
      blockfire.a(Blocks.iy, 60, 20);
      blockfire.a(Blocks.mb, 30, 60);
      blockfire.a(Blocks.mV, 60, 60);
      blockfire.a(Blocks.nO, 60, 60);
      blockfire.a(Blocks.nW, 30, 20);
      blockfire.a(Blocks.oY, 5, 20);
      blockfire.a(Blocks.oe, 60, 100);
      blockfire.a(Blocks.pb, 5, 20);
      blockfire.a(Blocks.pa, 30, 20);
      blockfire.a(Blocks.aL, 30, 60);
      blockfire.a(Blocks.aM, 30, 60);
      blockfire.a(Blocks.rp, 15, 60);
      blockfire.a(Blocks.rq, 15, 60);
      blockfire.a(Blocks.rr, 60, 100);
      blockfire.a(Blocks.rs, 30, 60);
      blockfire.a(Blocks.rt, 30, 60);
      blockfire.a(Blocks.rx, 60, 100);
      blockfire.a(Blocks.ry, 60, 100);
      blockfire.a(Blocks.rz, 60, 100);
      blockfire.a(Blocks.rA, 30, 60);
      blockfire.a(Blocks.ff, 15, 100);
   }
}
